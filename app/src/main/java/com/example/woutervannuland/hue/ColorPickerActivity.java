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

    Button colorLoopButton;
    Button stopLoopButton;

    ImageView colorPickerHd;

    TextView setColor;

    SeekBar setHueValueSeekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);

        colorLoopButton = (Button) findViewById(R.id.ColorLoopButton);
        colorLoopButton.setOnClickListener(this);

        stopLoopButton = (Button) findViewById(R.id.StopLoopButton);
        stopLoopButton.setOnClickListener(this);

        colorPickerHd = (ImageView) findViewById(R.id.colorPickerHd);

        setColor = (TextView) findViewById(R.id.connectedIpTextView);

        setHueValueSeekBar = (SeekBar) findViewById(R.id.setHueValueSeekBar);
        setHueValueSeekBar.setOnSeekBarChangeListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        verbondenBridge = PHHueSDK.getInstance().getSelectedBridge();

        connectedHueList = verbondenBridge.getResourceCache().getAllLights();

    }

    private PHLightState hueAdjuster() {

        PHLightState hueLightState = new PHLightState();
        hueLightState.setHue(setHueValueSeekBar.getProgress());
        return hueLightState;
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

            default:
                break;

        }
    }
}