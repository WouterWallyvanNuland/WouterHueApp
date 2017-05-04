package com.example.woutervannuland.hue;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.woutervannuland.hue.bridgelist.AccessPointPresenter;
import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeConfiguration;
import com.philips.lighting.model.PHLight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nl.rwslinkman.presentable.PresentableAdapter;
import nl.rwslinkman.presentable.interaction.PresentableItemClickListener;

public class FindingBridgeActivity extends AppCompatActivity implements ActivityChecker {
    private static final String TAG = "FindingBridgeActivity";
    TextView textView3;
    TextView textViewSetTimer;
    TextView textView1;
    TextView textHint;
    RecyclerView iplijst;

    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String KEY_IP = "userIp";
    public static final String KEY_MAC = "userMacAddress";
    public static final String KEY_USERNAME = "userUsername";
    public CountDownTimer afteller;
    public MediaPlayer mp;

    private PHHueSDK phHueSDK;
    private WouterHueListener myListener;
    private PresentableAdapter<PHAccessPoint> accessPointAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finding_bridge);

        textView1 = (TextView) findViewById(R.id.connectedIpTextView);
        textView3 = (TextView) findViewById(R.id.textViewAskUserToSelectBridge);
        textView3.setText("Zoeken naar bridges.. ");
        textViewSetTimer = (TextView) findViewById(R.id.textViewSetTimer);
        textHint = (TextView) findViewById(R.id.hintText);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        iplijst = (RecyclerView) findViewById(R.id.list_of_accesspoints);
        iplijst.setLayoutManager(llm);

        // Create adapter
        List<PHAccessPoint> accessPoints = new ArrayList<>();
        accessPointAdapter = new PresentableAdapter<>(new AccessPointPresenter(), accessPoints);
        iplijst.setAdapter(accessPointAdapter);

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

                    mp = MediaPlayer.create(FindingBridgeActivity.this, R.raw.shortfailsoundhorn);
                    mp.start();

                    Log.e(TAG, "onFinish: Timer afgelopen maar geen bridge! Ask user again to select a MacAddress");

                    toMainActivity();

                }
            };

        phHueSDK = PHHueSDK.getInstance();
        myListener = new WouterHueListener(phHueSDK, this);
        phHueSDK.getNotificationManager().registerSDKListener(myListener);

        Log.d(TAG, "onResume: Search for Hue Bridge starting.. ");
        PHBridgeSearchManager searchManager = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
        searchManager.search(true, true);

    }

    @Override
    public void ikHebAccessPointsGevonden(final List<PHAccessPoint> dezeVondIk) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                // Met de lijst kun je nog meer doen!

                accessPointAdapter.setData(dezeVondIk);
                accessPointAdapter.notifyDataSetChanged();
                accessPointAdapter.setItemClickListener(new PresentableItemClickListener<PHAccessPoint>() {
                    @Override
                    public void onItemClicked(PHAccessPoint item) {

                        myListener.onAuthenticationRequired(item);

                        // James Brown is coming to FunkyTown!
                        mp = MediaPlayer.create(FindingBridgeActivity.this, R.raw.jbsampleshort);
                        mp.start();

                        iplijst.setVisibility(View.GONE);
                        textViewSetTimer.setVisibility(View.VISIBLE);
                        ImageView bridge = (ImageView) findViewById(R.id.bridgeImageView) ;
                        bridge.setVisibility(View.VISIBLE);
                        afteller.start();
                    }
                });


                if(dezeVondIk.size() <= 1)
                textView3.setText("Er is " + dezeVondIk.size() + " bridge gevonden:");
                else {
                    textView3.setText("Er zijn " + dezeVondIk.size() + " bridges gevonden:");
                }
            }
        });
    }


    public void showHueOnConnectedBridge(final PHBridge verbondenBridge) {
        Log.d(TAG, "watIkWildeDoen:");

        PHBridgeConfiguration bridgeConfiguration = verbondenBridge.getResourceCache().getBridgeConfiguration();
        String verbondenBridgeIP = bridgeConfiguration.getIpAddress();
        String naamBridge = bridgeConfiguration.getUsername();
        String macAdres = bridgeConfiguration.getMacAddress();

        Log.d(TAG, "DE HUE IS VERBONDEN MET HET VOLGENDE IP ADRES  :  " + verbondenBridgeIP);
        Log.d(TAG, "DE HUE IS VERBONDEN MET HET VOLGENDE IP ADRES  :  " + macAdres);
        Log.d(TAG, "DE HUE IS VERBONDEN MET DE VOLGENDE BRIDGE  :  " + naamBridge);
        Log.d(TAG, "Stop de afteller nu. En ga naar ConnectedLampActivity");

        // sharedpreferences om preferences te lezen
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        // editor om preferences te schrijven
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_IP, bridgeConfiguration.getIpAddress());
        editor.putString(KEY_MAC, bridgeConfiguration.getMacAddress());
        editor.putString(KEY_USERNAME, bridgeConfiguration.getUsername());
        editor.apply();

        //Receive all lights from bridge
        List<PHLight> gevondenLampen = verbondenBridge.getResourceCache().getAllLights();
        for (PHLight lamp : gevondenLampen) {
            Log.d(TAG, "watIkWildeDoen: Er is een connectie met de volgende lamp: " + lamp.getName());
        }

        mp.stop();
        afteller.cancel();
        toConnectedLampActivity();
    }

    public void toConnectedLampActivity() {
        Intent startAct = new Intent(this, LampActivity.class);

        startActivity(startAct);
    }

    public void toMainActivity() {
        Intent startAct = new Intent(this, MainActivity.class);

        startActivity(startAct);
    }

}


