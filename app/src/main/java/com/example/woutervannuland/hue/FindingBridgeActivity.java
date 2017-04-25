package com.example.woutervannuland.hue;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.woutervannuland.hue.bridgelist.AccessPointPresenter;
import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHLight;

import java.util.ArrayList;
import java.util.List;

import nl.rwslinkman.presentable.PresentableAdapter;

public class FindingBridgeActivity extends AppCompatActivity implements ActivityChecker {
    private static final String TAG = "FindingBridgeActivity";
    TextView textView3;
    TextView textViewSetTimer;
    TextView textView1;
    CountDownTimer afteller;


    private PHHueSDK phHueSDK;
    private WouterHueListener myListener;
    private List<PHAccessPoint> connectedHueList;
    private PresentableAdapter<PHAccessPoint> accessPointAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finding_bridge);

        textView1 = (TextView) findViewById(R.id.textView1);
        textView3 = (TextView) findViewById(R.id.textViewSetIp);
        textView3.setText("Komt nog");
        textViewSetTimer = (TextView) findViewById(R.id.textViewSetTimer);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_of_accesspoints);
        recyclerView.setLayoutManager(llm);

        // Create adapter
        List<PHAccessPoint> accessPoints = new ArrayList<>();
        accessPointAdapter = new PresentableAdapter<>(new AccessPointPresenter(), accessPoints);
        recyclerView.setAdapter(accessPointAdapter);
//      https://github.com/rwslinkman/presentablelibrary [RTFM]
    }

    @Override
    protected void onResume() {
        super.onResume();

            afteller = new CountDownTimer(30000, 1000) {

                public void onTick(long millisUntilFinished) {
                    textViewSetTimer.setText("Seconds remaining to connect: " + millisUntilFinished / 1000);
                }

                public void onFinish() {

                    Log.e(TAG, "onFinish: Timer afgelopen maar geen bridge!");
//                    toAskIPActivity();
                }
            }.start();



        phHueSDK = PHHueSDK.getInstance();
        myListener = new WouterHueListener(phHueSDK, this);
        phHueSDK.getNotificationManager().registerSDKListener(myListener);



        PHBridgeSearchManager searchManager = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
        searchManager.search(true, true);
        Log.d(TAG, "onResume: Search for Hue Bridge started");
    }

    @Override
    public void ikHebAccessPointsGevonden(final List<PHAccessPoint> dezeVondIk) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                // Met de lijst kun je nog meer doen!
                // dezeVondIk.size(); --> Ik heb er X gevonden! in een TextView bijv.

                accessPointAdapter.setData(dezeVondIk);
                accessPointAdapter.notifyDataSetChanged();
            }
        });
    }

    public void showHueOnConnectedBridge(final PHBridge verbondenBridge) {
        Log.d(TAG, "watIkWildeDoen:");

        String verbondenBridgeIP = verbondenBridge.getResourceCache().getBridgeConfiguration().getIpAddress().toString();

        System.out.println("DE HUE IS VERBONDEN MET HET VOLGENDE IP ADRES: " + verbondenBridgeIP);

     //   textView3.setText();
//        if (verbondenBridgeIP.equals(Constant.EIGEN_LAMPEN_IP)) {
//            textView3.setText(verbondenBridgeIP);
//        } else if (verbondenBridgeIP.equals(Constant.FOURTRESS_LAMPEN_IP)) {
//            textView3.setText(verbondenBridgeIP);
//        } else if (verbondenBridgeIP.equals(Constant.RICK_LAMPEN_IP)) {
//            textView3.setText(verbondenBridgeIP);
//
//        }

        //Receive all lights from bridge
        List<PHLight> gevondenLampen = verbondenBridge.getResourceCache().getAllLights();
        for (PHLight lamp : gevondenLampen) {
            Log.d(TAG, "watIkWildeDoen: " + lamp.getName());
        }
        afteller.cancel();
        toConnectedLampActivity();
    }

    public void toConnectedLampActivity() {
        // doe ik dat hier of doe ik dat in de main? (Antwoord = HIER!)
        Intent startAct = new Intent(this, LampActivity.class);

        startActivity(startAct);
    }

}


