package com.example.woutervannuland.hue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;


import java.util.List;
import java.util.Timer;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private PHHueSDK phHueSDK;
    private WouterHueListener myListener;
    private PHBridge verbondenBridge;
    private List<PHLight> connectedHueList;

    Button button2;
    Button button5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);

        button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                if(verbondenBridge != null){
                    verbondenBridge.updateLightState(connectedHueList.get(0), changeHue0ToColorRed());
                    verbondenBridge.updateLightState(connectedHueList.get(1), changeHue0ToColorRed());
                    System.out.println("Changed Huelight 0 to color Red via button!");
                }
                break;
            case R.id.button5:
                verbondenBridge.updateLightState(connectedHueList.get(0), loopEffectHue0());
                System.out.println("Changed Huelight 0 to colorLoopEffect via button!");
        }
    }

    public void showHueOnConnectedBridge(PHBridge verbondenBridge) {
        Log.d(TAG, "watIkWildeDoen:");

        //Receive all lights from bridge
        List<PHLight> gevondenLampen = verbondenBridge.getResourceCache().getAllLights();

        for (PHLight lamp : gevondenLampen) {
            Log.d(TAG,           "watIkWildeDoen: " + lamp.getName());
            Log.d(TAG, "showHueOnConnectedBridge: " + lamp.getUniqueId());
            Log.d(TAG, "showHueOnConnectedBridge: " + lamp.getIdentifier());

        }
    }

    public void changingLightColors(PHBridge receivedBridge) {

        // om alle HUE lampen uit de bridge te lezen.
        this.verbondenBridge = receivedBridge;
        connectedHueList = receivedBridge.getResourceCache().getAllLights();

//        verbondenBridge.updateLightState(connectedHueList.get(0), changeHue0ToColorBlue());
//        System.out.println("Changed Huelight 0 to color Blue!");
//
//        verbondenBridge.updateLightState(connectedHueList.get(0), changeHue0ToColorRed());
//        System.out.println("Changed Huelight 0 to color Red!");
//
//        verbondenBridge.updateLightState(connectedHueList.get(0), changeHue0ToColorYellow());
//        System.out.println("Changed Huelight 0 to color Yellow!");

//        verbondenBridge.updateLightState(connectedHueList.get(0), changeHue0ToColorGreen());
//        System.out.println("Changed Huelight 0 to color Green!");









        // set first lamp to Red - with log
//        PHLightState redState = changeHue2ToColorRed();
//        verbondenBridge.updateLightState(connectedHueList.get(1), redState);
//        System.out.println("Changed second light to color Red!");




     //   verbondenBridge.updateLightState(connectedHueList.get(1), loopEffectHue1());
     //   System.out.println("Changed second light to loopeffect!");

        //verbondenBridge.updateLightState(connectedHueList.get(0), yellowState);
//        System.out.println("Changed first light to color Yellow!");
//        verbondenBridge.updateLightState(connectedHueList.get(0), blueState);
//        System.out.println("Changed first light to collor Blue!");

        // to stop the loop
//        verbondenBridge.updateLightState(connectedHueList.get(1), loopStop2());
//        System.out.println("Changed second light, stopped loop!");




    }


    public PHLightState loopEffectHue0 () {
        final PHLightState lightState0 = new PHLightState();
        lightState0.setEffectMode(PHLight.PHLightEffectMode.EFFECT_COLORLOOP);

        return lightState0;
    }

    public PHLightState loopEffectHue1 () {
        final PHLightState lightState1 = new PHLightState();
        lightState1.setEffectMode(PHLight.PHLightEffectMode.EFFECT_COLORLOOP);

        return lightState1;
    }

    public PHLightState loopStop0 () {
        PHLightState lightState0 = new PHLightState();
        lightState0.setEffectMode(PHLight.PHLightEffectMode.EFFECT_NONE);

        return lightState0;
    }

    public PHLightState loopStop1 () {
        PHLightState lightState1 = new PHLightState();
        lightState1.setEffectMode(PHLight.PHLightEffectMode.EFFECT_NONE);

        return lightState1;
    }


    public PHLightState changeHue0ToColorYellow () {
        final PHLightState yellowState0 = new PHLightState();
        yellowState0.setHue(12750, true);
//        yellowState.setX(0.5425f);
//        yellowState.setY(0.4196f);

        return yellowState0;
    }

    public PHLightState changeHue0ToColorGreen () {
        final PHLightState greenState0 = new PHLightState();
        greenState0.setX(0.4100f);
        greenState0.setY(0.51721f);

        return greenState0;
    }

    public PHLightState changeHue0ToColorBlue () {
        final PHLightState blueState0 = new PHLightState();
        blueState0.setHue(46920, true);

     //   blueState.setX(0.1691f);
     //   blueState.setY(0.0441f);

        return blueState0;
    }

    public PHLightState changeHue0ToColorRed () {
        final PHLightState redState0 = new PHLightState();
        redState0.setHue(0, true);

//        redState.setX(0.6750f);
//        redState.setY(0.3223f);

        return redState0;
    }

    public PHLightState changeHue1ToColorYellow () {
        final PHLightState yellowState1 = new PHLightState();
        yellowState1.setHue(12750, true);
//        yellowState.setX(0.5425f);
//        yellowState.setY(0.4196f);

        return yellowState1;
    }
    public PHLightState changeHue1ToColorBlue () {
        final PHLightState blueState1 = new PHLightState();
        blueState1.setHue(46920, true);
      //  blueState.setX(0.1691f);
      //  blueState.setY(0.0441f);

        return blueState1;
    }

    public PHLightState changeHue1ToColorRed () {
        final PHLightState redState1 = new PHLightState();
        redState1.setHue(0, true);
       // redState.setX(0.6750f);
       // redState.setY(0.3223f);

        return redState1;
    }


    public void changeHue1ToColorRedviaButton(View button2 ,PHBridge verbondenBridge, List<PHLight> connectedHueList) {
        PHLightState redState = new PHLightState();
        redState.setX(0.6750f);
        redState.setY(0.3223f);
        verbondenBridge.updateLightState(connectedHueList.get(0), redState);
        Log.d(TAG, "changeHue1 To Color Red: with RedBUTTON ");


    }
}

