package com.example.woutervannuland.hue;

import android.util.Log;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueParsingError;




import java.util.List;

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
        activityChecker.changingLightColors(phBridge);

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
        for (PHAccessPoint ap : list) {
            // Print alle AP informatie (bridge basic info)
            Log.d(TAG, "onAccessPointsFound: " + ap.getIpAddress());
            Log.d(TAG, "onAccessPointsFound: " + ap.getMacAddress());
            Log.d(TAG, "onAccessPointsFound: " + ap.getBridgeId());
            Log.d(TAG, "onAccessPointsFound: " + ap.getUsername());

            // Connect met degene die we zoeken (juiste IP)

            Constant.getHistoryIp();
            System.out.println(Constant.getHistoryIp().toString());

            if (ap.getIpAddress().equals(Constant.EIGEN_LAMPEN_IP)) {
                Log.d(TAG, "onAccessPointsFound: Connecting to " + ap.getIpAddress());
                phHueSdk.connect(ap);
            } else if (ap.getIpAddress().equals(Constant.FOURTRESS_LAMPEN_IP1)) {
                Log.d(TAG, "onAccessPointsFound: Connecting to " + ap.getIpAddress());
                phHueSdk.connect(ap);
            } else if (ap.getIpAddress().equals(Constant.RICK_LAMPEN_IP)) {
                Log.d(TAG, "onAccessPointsFound: Connecting to " + ap.getIpAddress());
                phHueSdk.connect(ap);
            }
        }
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
