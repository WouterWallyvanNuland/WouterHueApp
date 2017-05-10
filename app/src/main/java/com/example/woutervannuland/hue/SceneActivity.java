package com.example.woutervannuland.hue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;
import com.philips.lighting.model.PHScene;

import java.util.List;
import java.util.Map;

public class SceneActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private static final String TAG = "SceneActivity";
    private PHBridge verbondenBridge;
    private List<PHLight> connectedHueList;

    TextView connectedIpTextView;
    TextView connectedAmountOfLampsTextView;

    Button arcticGreenButton;
    Button springBlossomButton;
    Button sunsetSceneButton;
    Button savannahSceneButton;

    SeekBar seekBarArcticGreen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene);

        arcticGreenButton = (Button) findViewById(R.id.arcticGreenButton);
        arcticGreenButton.setOnClickListener(this);

        springBlossomButton = (Button) findViewById(R.id.springBlossomButton);
        springBlossomButton.setOnClickListener(this);

        sunsetSceneButton = (Button) findViewById(R.id.sunsetSceneButton);
        sunsetSceneButton.setOnClickListener(this);

        savannahSceneButton = (Button) findViewById(R.id.savannahSceneButton);
        savannahSceneButton.setOnClickListener(this);

        seekBarArcticGreen = (SeekBar) findViewById(R.id.seekBarArcticGreen);
        seekBarArcticGreen.setOnSeekBarChangeListener(this);

        connectedIpTextView = (TextView) findViewById(R.id.connectedIpTextView);
        connectedAmountOfLampsTextView = (TextView) findViewById(R.id.connectedAmountOfLampsTextView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // hier heb je de bridge weer
        verbondenBridge = PHHueSDK.getInstance().getSelectedBridge();
        String connectedIP = verbondenBridge.getResourceCache().getBridgeConfiguration().getIpAddress();
        connectedIpTextView.setText(("Ip-adres: " + connectedIP));

        // en hier alle lampen
        connectedHueList = verbondenBridge.getResourceCache().getAllLights();
        int tmp = connectedHueList.size();
        connectedAmountOfLampsTextView.setText("Verbonden met " + tmp + " lampen..");
    }

    //// TODO: als er geklikt wordt dan moet de scene openen en dan moet men de brightness kunnen schalen.
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.arcticGreenButton:
                Log.d(TAG, "onClick: " + verbondenBridge.getResourceCache().getScenes().values());
            case R.id.springBlossomButton:
//                Log.d(TAG, "onClick: " + verbondenBridge.getResourceCache().getScenes().values());


            default:
                break;

        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.d(TAG, "onProgressChanged: progress in seekBarArcticGreen is now " + progress);
        // progress meegeven aan een functie die de lightstate.setbrightness aanstuurt

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

        PHLightState briState = brightnessAdjuster();
        Log.d(TAG, "SceneActivity: onStopTrackingTouch: " + seekBar.getProgress());
        for (PHLight thisConnectedHueList : connectedHueList)
        {
            verbondenBridge.updateLightState(thisConnectedHueList, briState);
            Log.d(TAG, "brightnessAdjuster:  now adjust brightnessing for you");
        }
    }

    private PHLightState brightnessAdjuster() {
        Log.d(TAG, "brightnessAdjuster: " +  seekBarArcticGreen.getProgress() );

        PHLightState lightStateBright = new PHLightState();
        lightStateBright.setBrightness(seekBarArcticGreen.getProgress());
        return lightStateBright;
    }
}