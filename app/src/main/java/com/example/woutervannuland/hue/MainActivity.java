package com.example.woutervannuland.hue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHLight;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private PHHueSDK phHueSDK;
    private WouterHueListener myListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        phHueSDK = PHHueSDK.getInstance();
        phHueSDK.setAppName("WouterHue");
        phHueSDK.setDeviceName(android.os.Build.MODEL);

    }

    @Override
    protected void onResume() {
        super.onResume();

        myListener = new WouterHueListener(phHueSDK, this);
        phHueSDK.getNotificationManager().registerSDKListener(myListener);

        PHBridgeSearchManager searchManager = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
        searchManager.search(true, true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        phHueSDK.getNotificationManager().unregisterSDKListener(myListener);
    }

    public void watIkWildeDoen(PHBridge verbondenBridge) {
        Log.d(TAG, "watIkWildeDoen:");
        List<PHLight> ricksLampe = verbondenBridge.getResourceCache().getAllLights();
        for(PHLight lamp : ricksLampe) {
            Log.d(TAG, "watIkWildeDoen: " + lamp.getName());
            

        }
    }
}
