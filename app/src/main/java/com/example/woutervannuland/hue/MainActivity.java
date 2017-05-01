package com.example.woutervannuland.hue;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;

import java.io.IOException;
import java.io.OutputStreamWriter;

import static android.R.attr.data;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    // create all variables that i need in my Main.
    private PHHueSDK phHueSDK;
    private WouterHueListener myListener;
    private PHBridge verbondenBridge;
    int insertedIp = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phHueSDK = PHHueSDK.getInstance();
        phHueSDK.setAppName("WouterHue");
        phHueSDK.setDeviceName(android.os.Build.MODEL);

        toFindingBridgeActivity();
    }

    private void writeToFile(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            Log.d("OutputWriter:", "Ik heb iets geschreven!!");
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // TODO uitzoeken of er een bridge was waar ooit mee geconnect is.
        writeToFile("Gekke test", this);


        PHBridge selectedBridge = phHueSDK.getSelectedBridge();
        System.out.println("No bridges found! So we need to find one in FindingBridgeActivity");
        if (selectedBridge == null) {

            //
            // als er geen bridge is opnieuw naar het zoek bridge scherm.
            //toWouterHueListener();
              toFindingBridgeActivity();
        } else {
            // hier heb je al een bridge
            toLampActivity();
        }

    }

    public void checkConnectedBridge(PHBridge receivedBridge) {

        // DE BRIDGE DIE GEVONDEN IS, LOKAAL MAKEN IN DE MAIN CLASS
        this.verbondenBridge = receivedBridge;
        // TODO daarna dus bepalen naar welk scherm de user wordt doorgeleid: Meteen naar lampen?
    }

    private void toFindingBridgeActivity() {
        Intent i = new Intent(this, FindingBridgeActivity.class);
        startActivity(i);
    }

    private void toLampActivity() {
        Intent j = new Intent(this, LampActivity.class);
        startActivity(j);
    }


    private void toWouterHueListener() {
        Intent k = new Intent(this, WouterHueListener.class);
        startActivity(k);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String userIP = data.getStringExtra("UserIP");
        toFindingBridgeActivity();
    }
}
