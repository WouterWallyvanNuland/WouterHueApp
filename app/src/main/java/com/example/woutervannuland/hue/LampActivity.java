package com.example.woutervannuland.hue;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.concurrent.TimeUnit;

import static com.example.woutervannuland.hue.FindingBridgeActivity.PREFS_NAME;

public class LampActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private PHBridge connectedBridge;
    private List<PHLight> connectedHueList;

    Button colorLoopButton;
    Button stopLoopButton;
    ImageView coloredHueImage;
    Button redButton;
    Button greenButton;
    Button blueButton;
    Button yellowButton;
    Button clearButton;
    Button lightDimmButton;
    TextView ipConnectedBridgeTextView;
    TextView connectedAmountOfLamps;
    SeekBar saturationSeekbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamps);

        // first create all buttons and catch them by ID.
        colorLoopButton = (Button) findViewById(R.id.ColorLoopButton);
        colorLoopButton.setOnClickListener(this);

        stopLoopButton = (Button) findViewById(R.id.StopLoopButton);
        stopLoopButton.setOnClickListener(this);

        coloredHueImage = (ImageView) findViewById(R.id.coloredHueImage);
        coloredHueImage.setOnClickListener(this);

        redButton = (Button) findViewById(R.id.redButton);
        redButton.setOnClickListener(this);

        greenButton = (Button) findViewById(R.id.greenButton);
        greenButton.setOnClickListener(this);

        blueButton = (Button) findViewById(R.id.blueButton);
        blueButton.setOnClickListener(this);

        yellowButton = (Button) findViewById(R.id.yellowButton);
        yellowButton.setOnClickListener (this);

        clearButton = (Button) findViewById(R.id.clearBridgeButton);
        clearButton.setOnClickListener (this);

        lightDimmButton = (Button) findViewById(R.id.lightDimmButton);
        lightDimmButton.setOnClickListener(this);

        ipConnectedBridgeTextView = (TextView) findViewById(R.id.ipConnectedBridgeTextView);
        connectedAmountOfLamps = (TextView) findViewById(R.id.connectedAmountOfLamps);

        saturationSeekbar = (SeekBar) findViewById(R.id.saturationSeekbar);
        saturationSeekbar.setOnSeekBarChangeListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // hier heb je de bridge weer
        connectedBridge = PHHueSDK.getInstance().getSelectedBridge();

        // en hier alle lampen
        List<PHLight> allLights = connectedBridge.getResourceCache().getAllLights();
        connectedHueList = allLights;

        String connectedIP = connectedBridge.getResourceCache().getBridgeConfiguration().getIpAddress();
        int tmp = connectedHueList.size();
        ipConnectedBridgeTextView.setText(("Ip-adres: " + connectedIP));
        connectedAmountOfLamps.setText("Verbonden met " + tmp + " lampen..");
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.blueButton:
                // both lights to BLUE
                if (connectedBridge != null) {
                    for (PHLight thisConnectedHueList : connectedHueList) {
                        connectedBridge.updateLightState(thisConnectedHueList, changeHue0ToColorBlue());
                    }
                    System.out.println("Changed all lights to color Blue via button!");
                }
                break;

            case R.id.redButton:
                // both lights to RED
                if (connectedBridge != null) {
                    for (PHLight thisConnectedHueList : connectedHueList) {
                        connectedBridge.updateLightState(thisConnectedHueList, changeHue0ToColorRed());
                    }
                    System.out.println("Changed all lights to color Red via button!");

                }
                break;

            case R.id.greenButton:
                // both lights to GREEN
                if (connectedBridge != null) {
                    for (PHLight thisConnectedHueList : connectedHueList) {
                        connectedBridge.updateLightState(thisConnectedHueList, changeHue0ToColorGreen());
                    }
                    System.out.println("Changed all lights to color Green via button!");
                }
                break;


            case R.id.StopLoopButton:
                // both lights need to stop with their colorLoop. Change back to last known color.
                if (connectedBridge != null) {
                    for (PHLight thisConnectedHueList : connectedHueList) {
                        connectedBridge.updateLightState(thisConnectedHueList, loopStop0());
                    }
                    System.out.println("Quit loop and go back to last known color before loop started ");
                }
                break;

            case R.id.ColorLoopButton:
                // both lights to ColorLoop mode!
                if (connectedBridge != null) {
                    for (PHLight thisConnectedHueList : connectedHueList) {
                        connectedBridge.updateLightState(thisConnectedHueList, loopEffectHue0());
                    }
                    System.out.println("HoopdiLoop! Demonstrating all the colors in a loop on all lights ");
                }
                break;

            case R.id.yellowButton:
                // both ligths to YELLOW!
                if (connectedBridge != null) {
                    for (PHLight thisConnectedHueList : connectedHueList) {
                        connectedBridge.updateLightState(thisConnectedHueList, changeHue0ToColorYellow());
                    }
                }
                break;

            case R.id.clearBridgeButton:
                SharedPreferences preferences = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();

                break;

            case R.id.lightDimmButton:
               // toSceneActivity();
                if (connectedBridge != null) {
                    for (PHLight thisConnectedHueList : connectedHueList) {
                        brightnessFadeOut(thisConnectedHueList);
                        //verbondenBridge.updateLightState(thisConnectedHueList, brightnessFadeOut(thisConnectedHueList));
                    }

                }


                break;

            case R.id.coloredHueImage:
                toColorPickerActivity();

            default:
                break;
            }
        }

    public PHLightState loopEffectHue0() {
        final PHLightState lightState0 = new PHLightState();
        lightState0.setEffectMode(PHLight.PHLightEffectMode.EFFECT_COLORLOOP);

        return lightState0;
    }

    public PHLightState loopStop0() {
        PHLightState lightState0 = new PHLightState();
        lightState0.setEffectMode(PHLight.PHLightEffectMode.EFFECT_NONE);

        return lightState0;
    }

    public PHLightState changeHue0ToColorYellow() {
        final PHLightState yellowState0 = new PHLightState();
        yellowState0.setHue(12750, true);
    //        yellowState.setX(0.5425f);
    //        yellowState.setY(0.4196f);

        return yellowState0;
    }

    public PHLightState changeHue0ToColorGreen() {
        final PHLightState greenState0 = new PHLightState();
        greenState0.setX(0.4100f);
        greenState0.setY(0.51721f);

        return greenState0;
    }

    public PHLightState changeHue0ToColorBlue() {
        final PHLightState blueState0 = new PHLightState();
          blueState0.setHue(46920, true);

    //          blueState0.setX(0.1691f);
    //          blueState0.setY(0.0441f);

        return blueState0;
    }

    public PHLightState changeHue0ToColorRed() {
        final PHLightState redState0 = new PHLightState();
        redState0.setHue(0, true);

    //        redState.setX(0.6750f);
    //        redState.setY(0.3223f);

        return redState0;
    }



    private PHLightState brightnessFadeOut(PHLight light) {

        PHLightState lightState = light.getLastKnownLightState();

        int brightness = lightState.getBrightness();

        if (brightness <= 1) {
            return lightState;
        }

        lightState.setBrightness(brightness -5);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        for (PHLight lights : connectedHueList) {
            connectedBridge.updateLightState(light, lightState);
       // }



        return brightnessFadeOut(light);


    }

    private PHLightState saturationAdjuster()
    {
        PHLightState lightStateSaturation = new PHLightState();
        lightStateSaturation.setSaturation(saturationSeekbar.getProgress());
        return lightStateSaturation;
    }


    private void toColorPickerActivity() {
        // now you go there when you push the image. Dont know if this will work.

        Intent i = new Intent(this, ColorPickerActivity.class);
        startActivity(i);

    }
    // underneath are override methods for the Seekbar.




    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        PHLightState lightStateSaturation = saturationAdjuster();
        System.out.println("Seekbar Saturation used");
        for (PHLight thisConnectedHueList : connectedHueList)
        {
            connectedBridge.updateLightState(thisConnectedHueList, lightStateSaturation);

        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
