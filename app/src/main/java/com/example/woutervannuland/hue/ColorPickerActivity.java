package com.example.woutervannuland.hue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

import java.util.List;

public class ColorPickerActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private PHBridge verbondenBridge;
    private List<PHLight> connectedHueList;

    ImageView colorPickerHd;
    ImageView saturationImageView;
    ImageView brightnessImageView;

    TextView setColor;
    TextView setSaturationTextView;
    TextView setBrightnessTextView;

    SeekBar setSaturationSeekBar;
    SeekBar setHueValueSeekBar;
    SeekBar setBrightnessSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);

        colorPickerHd = (ImageView) findViewById(R.id.colorPickerHd);
        saturationImageView = (ImageView) findViewById(R.id.saturationImageView);
        brightnessImageView = (ImageView) findViewById(R.id.brightnessImageView);


        setColor = (TextView) findViewById(R.id.connectedIpTextView);
        setSaturationTextView = (TextView) findViewById(R.id.setSaturationTextView);
        setBrightnessTextView = (TextView) findViewById(R.id.brightnessTextView);


        setHueValueSeekBar = (SeekBar) findViewById(R.id.setHueValueSeekBar);
        setHueValueSeekBar.setOnSeekBarChangeListener(this);

        setSaturationSeekBar = (SeekBar) findViewById(R.id.setSaturationSeekBar);
        setSaturationSeekBar.setOnSeekBarChangeListener(this);

        setBrightnessSeekBar = (SeekBar) findViewById(R.id.setBrightnessSeekBar);
        setBrightnessSeekBar.setOnSeekBarChangeListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        verbondenBridge = PHHueSDK.getInstance().getSelectedBridge();

        connectedHueList = verbondenBridge.getResourceCache().getAllLights();

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ColorLoopButton:

        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.setHueValueSeekBar:

                // progress meegeven aan een functie die de lightstate.setbrightness aanstuurt
                PHLightState hueColorState = hueAdjuster();

                for (PHLight thisConnectedHueList : connectedHueList) {
                    verbondenBridge.updateLightState(thisConnectedHueList, hueColorState);
                }

                break;

            case R.id.saturationSeekbar:

                PHLightState saturationState = saturationAdjuster();

                for (PHLight thisConnectedHueList : connectedHueList) {
                    verbondenBridge.updateLightState(thisConnectedHueList, saturationState);
                }
            case R.id.setBrightnessSeekBar:

                PHLightState brightState = brightnessAdjuster();

                for (PHLight thisConnectedHueList : connectedHueList) {
                    verbondenBridge.updateLightState(thisConnectedHueList, brightState);
                }

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
            case R.id.setHueValueSeekBar:

                // progress meegeven aan een functie die de lightstate.setbrightness aanstuurt
                PHLightState hueColorState = hueAdjuster();
                onProgressChanged(seekBar, seekBar.getProgress(), true);


                for (PHLight thisConnectedHueList : connectedHueList) {
                    verbondenBridge.updateLightState(thisConnectedHueList, hueColorState);
                }

                break;

            case R.id.setSaturationSeekBar:

                PHLightState saturationState = saturationAdjuster();
                onProgressChanged(seekBar, seekBar.getProgress(), true);

                for (PHLight thisConnectedHueList : connectedHueList) {
                    verbondenBridge.updateLightState(thisConnectedHueList, saturationState);
                }

            case R.id.setBrightnessSeekBar:

                PHLightState brightState = brightnessAdjuster();
                onProgressChanged(seekBar, seekBar.getProgress(), true);

                for (PHLight thisConnectedHueList : connectedHueList) {
                    verbondenBridge.updateLightState(thisConnectedHueList, brightState);
                }


            default:
                break;

        }
    }

    private PHLightState hueAdjuster() {

        PHLightState hueLightState = new PHLightState();
        hueLightState.setHue(setHueValueSeekBar.getProgress());
        return hueLightState;
    }

    public PHLightState saturationAdjuster() {
        PHLightState saturationState = new PHLightState();
        saturationState.setSaturation(setSaturationSeekBar.getProgress());

        return saturationState;

    }

    public PHLightState brightnessAdjuster() {
        PHLightState brightnessState = new PHLightState();
        brightnessState.setBrightness(setBrightnessSeekBar.getProgress());

        return  brightnessState;
    }
}