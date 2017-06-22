package com.example.woutervannuland.hue;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import static android.R.attr.data;
import static com.example.woutervannuland.hue.FindingBridgeActivity.KEY_IP;
import static com.example.woutervannuland.hue.FindingBridgeActivity.KEY_MAC;
import static com.example.woutervannuland.hue.FindingBridgeActivity.KEY_USERNAME;
import static com.example.woutervannuland.hue.FindingBridgeActivity.PREFS_NAME;


public class MainActivity extends AppCompatActivity implements ActivityChecker {
    private static final String TAG = "MainActivity";

    // create all variables that i need in my Main.
    private PHHueSDK phHueSDK;
    private WouterHueListener myListener;

    //SharedPref data
    private String ip;
    private String username;
    private String macaddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        ip = settings.getString(KEY_IP, ""); // "" indien niks geset
        macaddress = settings.getString(KEY_MAC, "");
        username = settings.getString(KEY_USERNAME, "");

        phHueSDK = PHHueSDK.getInstance();
        phHueSDK.setAppName("WouterHue");
        phHueSDK.setDeviceName(android.os.Build.MODEL);

        toFindingBridgeActivity();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // uitzoeken of er een bridge was waar ooit mee geconnect is.
        // Eerst preferences laden


        if (ip.isEmpty() || username.isEmpty()){
            // Nog geen IP
            toFindingBridgeActivity();
        }
        else {
            // Al wel een IP
            myListener = new WouterHueListener(phHueSDK, this);
            phHueSDK.getNotificationManager().registerSDKListener(myListener);

            PHAccessPoint APpref = new PHAccessPoint(ip, username, macaddress);
            APpref.setIpAddress(ip);
            APpref.setUsername(username);
            phHueSDK.connect(APpref);
        }
    }

    private void toFindingBridgeActivity() {
        Intent i = new Intent(this, FindingBridgeActivity.class);
        startActivity(i);
    }

    private void toLampActivity() {
        Intent j = new Intent(this, LampActivity.class);
        startActivity(j);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        toFindingBridgeActivity();
    }


    @Override
    public void ikHebAccessPointsGevonden(List<PHAccessPoint> dezeVondIk) {

    }

    @Override
    public void showHueOnConnectedBridge(PHBridge verbondenBridge) {
        toLampActivity();
    }

    public PHHueSDK getPhHueSDK() {
        return phHueSDK;
    }

}
