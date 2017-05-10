package com.example.woutervannuland.hue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHLight;

import java.util.List;

public class SceneActivity extends AppCompatActivity implements View.OnClickListener {

    private PHBridge verbondenBridge;
    private List<PHLight> connectedHueList;

    TextView connectedIpTextView;
    TextView connectedAmountOfLampsTextView;

    Button arcticGreenButton;
    Button springBlossomButton;
    Button sunsetSceneButton;
    Button savannahSceneButton;


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


     @Override
     public void onClick(View view) {
         //// TODO: als er geklikt wordt dan moet de scene openen en dan moet men de brightness kunnen schalen.
     }
 }
