package com.example.woutervannuland.hue;

import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHLight;
import java.util.Timer;

import java.util.List;

public class FindingBridgeActivititties extends AppCompatActivity implements View.OnClickListener, ActivityChecker {

    TextView textview;
    TextView textview2;
    TextView textview3;
    Button hiddenButton;

    private PHHueSDK phHueSDK;
    private WouterHueListener myListener;
    private PHBridge verbondenBridge;
    private List<PHLight> connectedHueList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finding_bridge);


        TextView textview = (TextView) findViewById(R.id.textView1);

        TextView textview2 = (TextView) findViewById(R.id.textView2);
        textview2.setOnClickListener(this);

        TextView textview3 = (TextView) findViewById(R.id.textView3);

        Button hiddenButton = (Button) findViewById(R.id.hiddenButton);
        hiddenButton.setOnClickListener(this);


    }


//        textview2.setOnClickListener((View.OnClickListener) this);


    // onClick methode voor de image

    //    public void onClick(TextView t) {
//
//    }
    @Override
    protected void onResume() {
        super.onResume();

        phHueSDK.getNotificationManager().registerSDKListener(myListener);

        PHBridgeSearchManager searchManager = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
        searchManager.search(true, true);

        myListener = new WouterHueListener(phHueSDK, this);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView2:
                // text moet het aantal resterende seconden worden van de timer.
//                if ( image is pushed, and bridge is not connected. Start timer.){
//                break;
//            }
            case R.id.hiddenButton:



        }
    }

    @Override
    public void showHueOnConnectedBridge(PHBridge verbondenBridge) {
        //
    }

    @Override
    public void changingLightColors(PHBridge receivedBridge) {
        //
    }
}


