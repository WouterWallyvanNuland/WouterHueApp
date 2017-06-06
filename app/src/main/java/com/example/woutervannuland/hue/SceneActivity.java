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
    Button hueValueButton;

    SeekBar seekBarArcticGreen;
    SeekBar seekBarSpringBlossom;
    SeekBar seekBarSunsetScene;
    SeekBar hueValueSeekbar;

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

        hueValueButton = (Button) findViewById(R.id.hueValueButton);
        hueValueButton.setOnClickListener(this);

        seekBarArcticGreen = (SeekBar) findViewById(R.id.seekBarArcticGreen);
        seekBarArcticGreen.setOnSeekBarChangeListener(this);

        seekBarSpringBlossom = (SeekBar) findViewById(R.id.seekBarSpringBlossom);
        seekBarSpringBlossom.setOnSeekBarChangeListener(this);

        seekBarSunsetScene = (SeekBar) findViewById(R.id.seekBarSunsetScene);
        seekBarSunsetScene.setOnSeekBarChangeListener(this);

        connectedIpTextView = (TextView) findViewById(R.id.connectedIpTextView);
        connectedAmountOfLampsTextView = (TextView) findViewById(R.id.connectedAmountOfLampsTextView);

        hueValueSeekbar = (SeekBar) findViewById(R.id.hueValueSeekbar);
        hueValueSeekbar.setOnSeekBarChangeListener(this);

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

        Map<String, PHScene> scenesMap = verbondenBridge.getResourceCache().getScenes();
        for (Map.Entry<String, PHScene> entry : scenesMap.entrySet())
        {
            Log.d(TAG, "ScenesMap: " + (entry.getKey() + "/" + entry.getValue()));
            String sceneIdentifier = entry.getKey();
            PHScene sceneValue = entry.getValue();

        }



    }

    //// TODO: als er geklikt wordt dan moet de scene openen en dan moet men de brightness kunnen schalen.
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.arcticGreenButton:
                Log.d(TAG, "onClick: " + verbondenBridge.getResourceCache().getScenes().values());

//                verbondenBridge.getScene();
//                verbondenBridge.activateScene("ArcticGreen", "Green", );

                // set scene arctic green
                //verbondenBridge.activateScene();

//                PHScene sceneToUpdate = verbondenBridge.getResourceCache().getScenes().get();
//                sceneToUpdate.setLightIdentifiers(new ArrayList());

                break;

            case R.id.springBlossomButton:
                Log.d(TAG, "onClick: springblossombutton clicked");
                Log.d(TAG, "onClick: springblossom clicked : get-colormode. : " + brightnessAdjuster().getColorMode());
                Log.d(TAG, "onClick: springblossom clicked : get-ct. : " + brightnessAdjuster().getCt());
                Log.d(TAG, "onClick: springblossom clicked : get-effectmode. : " + brightnessAdjuster().getEffectMode());
                Log.d(TAG, "onClick: springblossom clicked : get-hue. : " + brightnessAdjuster().getHue());



                // set scene spring blossom
                break;

            case R.id.sunsetSceneButton:
                Log.d(TAG, "onClick: sunsetscenebutton clicked");

                // set scene sunset
                break;

            case R.id.hueValueButton:
                Log.d(TAG, "onClick: savannahScenebutton clicked");


                break;

            default:
                break;

        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seekBarArcticGreen:

                Log.d(TAG, "onProgressChanged: progress in seekBarArcticGreen is now " + progress);
                // progress meegeven aan een functie die de lightstate.setbrightness aanstuurt
                PHLightState briState = brightnessAdjuster();

                for (PHLight thisConnectedHueList : connectedHueList) {
                    verbondenBridge.updateLightState(thisConnectedHueList, briState);
                    Log.d(TAG, "brightnessAdjuster:  Adjusting brightnessing for you while sliding on Seekbar");
                }

                break;

            case R.id.hueValueSeekbar:

                PHLightState hueState = hueAdjuster();

                for (PHLight thisConnectedHueList : connectedHueList) {
                    verbondenBridge.updateLightState(thisConnectedHueList, hueState);
                    Log.d(TAG, "brightnessAdjuster:  Adjusting brightnessing for you while sliding on Seekbar");
                }

                break;

            default:

                break;


        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        switch (seekBar.getId()) {
            case R.id.seekBarArcticGreen:

                // lampen bleven flikkeren na grote verplaatsing op de de seekbar
                // door de onprogressChanged methode, dus vandaar onderstaande code.
                onProgressChanged(seekBar, seekBar.getProgress(), true);

                // lampen zetten naar de waarde die de user instelt op de Seekbar.
                // eerst PHLightstate aanmaken, en die gelijkstellen aan de returnwaarde van de SeekbarArcticGreen brightnessAdjuster

                PHLightState briState = brightnessAdjuster();
                Log.d(TAG, "SceneActivity: onStopTrackingTouch: " + seekBar.getProgress());
                for (PHLight thisConnectedHueList : connectedHueList) {
                    verbondenBridge.updateLightState(thisConnectedHueList, briState);
                    Log.d(TAG, "brightnessAdjuster:  now adjust brightness for you");
                }

                break;

            case R.id.hueValueSeekbar:

                PHLightState hueState = hueAdjuster();
                hueState.setHue(hueValueSeekbar.getProgress());

                for (PHLight thisConnectedHueList : connectedHueList) {
                    verbondenBridge.updateLightState(thisConnectedHueList, hueState);
                    Log.d(TAG, "brightnessAdjuster:  Adjusting brightnessing for you while sliding on Seekbar");
                }

                break;

            default:

                break;


        }
    }

    private PHLightState brightnessAdjuster() {
        Log.d(TAG, "brightnessAdjuster: " +  seekBarArcticGreen.getProgress() );

        PHLightState lightStateBright = new PHLightState();
        lightStateBright.setBrightness(seekBarArcticGreen.getProgress());
        return lightStateBright;
    }

    private PHLightState hueAdjuster()
    {
        Log.d(TAG, "hueAdjuster: " + hueValueSeekbar.getProgress());
        PHLightState hueLightState = new PHLightState();
        hueLightState.setHue(hueValueSeekbar.getProgress());
        return hueLightState;
    }




}