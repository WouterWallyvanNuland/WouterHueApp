package com.example.woutervannuland.hue;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

import java.util.List;

import static com.example.woutervannuland.hue.FindingBridgeActivity.PREFS_NAME;

public class LampActivity extends AppCompatActivity implements View.OnClickListener {
    private PHBridge verbondenBridge;
    private List<PHLight> connectedHueList;

    Button colorLoopButton;
    Button stopLoopButton;
    Button redButton;
    Button greenButton;
    Button blueButton;
    Button yellowButton;
    Button clearButton;
    TextView connectedIpTextView;
    TextView connectedAmountOfLampsTextView;

    // Tip van de dag: Activities mogen geen constructor hebben

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp);

        // first create all buttons and catch them by ID.
        colorLoopButton = (Button) findViewById(R.id.ColorLoopButton);
        colorLoopButton.setOnClickListener(this);

        stopLoopButton = (Button) findViewById(R.id.StopLoopButton);
        stopLoopButton.setOnClickListener(this);

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

        connectedIpTextView = (TextView) findViewById(R.id.connectedIpTextView);
        connectedAmountOfLampsTextView = (TextView) findViewById(R.id.connectedAmountOfLampsTextView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // hier heb je de bridge weer
        verbondenBridge = PHHueSDK.getInstance().getSelectedBridge();

        // en hier alle lampen
        List<PHLight> allLights = verbondenBridge.getResourceCache().getAllLights();
        connectedHueList = allLights;

        String connectedIP = verbondenBridge.getResourceCache().getBridgeConfiguration().getIpAddress();
        int tmp = connectedHueList.size();
        connectedIpTextView.setText(("Ip-adres: " + connectedIP));
        connectedAmountOfLampsTextView.setText("Verbonden met " + tmp + " lampen..");
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.blueButton:
                // both lights to BLUE
                if (verbondenBridge != null) {
                    for (PHLight thisConnectedHueList : connectedHueList) {
                        verbondenBridge.updateLightState(thisConnectedHueList, changeHue0ToColorBlue());
                    }
                    System.out.println("Changed all lights to color Blue via button!");
                }
                break;

            case R.id.redButton:
                // both lights to RED
                if (verbondenBridge != null) {
                    for (PHLight thisConnectedHueList : connectedHueList) {
                        verbondenBridge.updateLightState(thisConnectedHueList, changeHue0ToColorRed());
                    }
                    System.out.println("Changed all lights to color Red via button!");

                }
                break;

            case R.id.greenButton:
                // both lights to GREEN
                if (verbondenBridge != null) {
                    for (PHLight thisConnectedHueList : connectedHueList) {
                        verbondenBridge.updateLightState(thisConnectedHueList, changeHue0ToColorGreen());
                    }
                    System.out.println("Changed all lights to color Green via button!");
                }
                break;


            case R.id.StopLoopButton:
                // both lights need to stop with their colorLoop. Change back to last known color.
                if (verbondenBridge != null) {
                    for (PHLight thisConnectedHueList : connectedHueList) {
                        verbondenBridge.updateLightState(thisConnectedHueList, loopStop0());
                    }
                    System.out.println("Quit loop and go back to last known color before loop started ");
                }
                break;

            case R.id.ColorLoopButton:
                // both lights to ColorLoop mode!
                if (verbondenBridge != null) {
                    for (PHLight thisConnectedHueList : connectedHueList) {
                        verbondenBridge.updateLightState(thisConnectedHueList, loopEffectHue0());
                    }
                    System.out.println("HoopdiLoop! Demonstrating all the colors in a loop on all lights ");
                }
                break;

            case R.id.yellowButton:
                // both ligths to YELLOW!
                if (verbondenBridge != null) {
                    for (PHLight thisConnectedHueList : connectedHueList) {
                        verbondenBridge.updateLightState(thisConnectedHueList, changeHue0ToColorYellow());
                    }
                }
                break;

            case R.id.clearBridgeButton:
                SharedPreferences preferences = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                break;

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


}
