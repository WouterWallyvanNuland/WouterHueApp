package com.example.woutervannuland.hue;

import android.util.Log;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueParsingError;


import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

public class WouterHueListener implements PHSDKListener{
    private static final String TAG = "WouterHueListener";
    private final PHHueSDK phHueSdk;
    private final ActivityChecker activityChecker;

    public WouterHueListener(PHHueSDK philipsDing, ActivityChecker myActivity) {
        this.phHueSdk = philipsDing;
        this.activityChecker = myActivity;
    }

    @Override
    public void onCacheUpdated(List<Integer> list, PHBridge phBridge) {
        Log.d(TAG, "onCacheUpdated: ");
    }

    @Override
    public void onBridgeConnected(PHBridge phBridge, String username) {
        phHueSdk.setSelectedBridge(phBridge);
//        phHueSdk.enableHeartbeat(phBridge, PHHueSDK.HB_INTERVAL / 2);
        Log.d(TAG, "onBridgeConnected: ");

        activityChecker.showHueOnConnectedBridge(phBridge);

    }

    @Override
    public void onAuthenticationRequired(PHAccessPoint phAccessPoint) {
        phHueSdk.startPushlinkAuthentication(phAccessPoint);

        // TODO show pushlink image and timer.

        Log.d(TAG, "onAuthenticationRequired: WOUTER TAKE IT TO THE BRIDGE!");
        Log.d(TAG, "onAuthenticationRequired: " + phAccessPoint.getIpAddress());
    }



    @Override
    public void onAccessPointsFound(List<PHAccessPoint> list) {
        Log.d(TAG, "onAccessPointsFound: ");

        // E ik heb  een lijst
        activityChecker.ikHebAccessPointsGevonden(list);
    }

    @Override
    public void onError(int code, String message) {
        Log.d(TAG, "onError: " + code + " " + message);
    }

    @Override
    public void onConnectionResumed(PHBridge phBridge) {
        Log.d(TAG, "onConnectionResumed: ");
    }

    @Override
    public void onConnectionLost(PHAccessPoint phAccessPoint) {
        Log.d(TAG, "onConnectionLost: ");
    }

    @Override
    public void onParsingErrors(List<PHHueParsingError> list) {
        Log.d(TAG, "onParsingErrors: ");
    }
}
