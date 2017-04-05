package com.example.woutervannuland.hue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHLight;

import java.util.List;

public class LampActivity extends AppCompatActivity {
    private PHBridge verbondenBridge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // hier heb je de bridge weer
        verbondenBridge = PHHueSDK.getInstance().getSelectedBridge();

        // en hier alle lampen
        List<PHLight> allLights = verbondenBridge.getResourceCache().getAllLights();


    }
}
