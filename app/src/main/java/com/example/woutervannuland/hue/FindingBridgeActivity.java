package com.example.woutervannuland.hue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHLight;

import java.util.List;

public class FindingBridgeActivity extends AppCompatActivity implements ActivityChecker {
    private static final String TAG = "FindingBridgeActivity";
    private String ip = "";
    TextView textView3;
    TextView textView1;

    private PHHueSDK phHueSDK;
    private WouterHueListener myListener;
    private List<PHAccessPoint> connectedHueList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finding_bridge);

        textView1 = (TextView) findViewById(R.id.textView1);
        textView3 = (TextView) findViewById(R.id.textView3);

    }



    @Override
    protected void onResume() {
        super.onResume();

        phHueSDK = PHHueSDK.getInstance();
        myListener = new WouterHueListener(phHueSDK, this);
        phHueSDK.getNotificationManager().registerSDKListener(myListener);

        PHBridgeSearchManager searchManager = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
        searchManager.search(true, true);
        Log.d(TAG, "onResume: Search for Hue Bridge started");
    }

    public void showHueOnConnectedBridge(final PHBridge verbondenBridge) {
        Log.d(TAG, "watIkWildeDoen:");

        runOnUiThread(new Runnable() {


            @Override
            public void run() {
                String verbondenBridgeIP = verbondenBridge.getResourceCache().getBridgeConfiguration().getIpAddress().toString();
                textView3.setText(verbondenBridgeIP);
            }
        });



        //Receive all lights from bridge
        List<PHLight> gevondenLampen = verbondenBridge.getResourceCache().getAllLights();

        // lijst nog tonen aan user.

        for (PHLight lamp : gevondenLampen) {
            Log.d(TAG, "watIkWildeDoen: " + lamp.getName());
            Log.d(TAG, "showHueOnConnectedBridge: " + lamp.getUniqueId());
            Log.d(TAG, "showHueOnConnectedBridge: " + lamp.getIdentifier());

        }

        toConnectedLampActivity();
    }

    @Override
    public void changingLightColors(PHBridge receivedBridge) {

    }

    public void toConnectedLampActivity() {
        // doe ik dat hier of doe ik dat in de main? (Antwoord = HIER!)
        Intent startAct = new Intent(this, LampActivity.class);

        startActivity(startAct);
    }
}


