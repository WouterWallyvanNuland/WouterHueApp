package com.example.woutervannuland.hue;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHLight;

import org.w3c.dom.Text;

import java.util.List;

public class FindingBridgeActivititties extends AppCompatActivity implements View.OnClickListener {

    TextView textview;
    TextView textview2;
    TextView textview3;

    private PHHueSDK phHueSDK;
    private WouterHueListener myListener;
    private PHBridge verbondenBridge;
    private List<PHLight> connectedHueList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finding_bridge);


        TextView textview = (TextView) findViewById(R.id.textView);

        TextView textview2 = (TextView) findViewById(R.id.textView2);
        textview2.setOnClickListener(this);

        TextView textview3 = (TextView) findViewById(R.id.textView3);

    }
//        textview2.setOnClickListener((View.OnClickListener) this);


        // onClick methode voor de image

    public void onClick(TextView t) {

    }

//        new CountDownTimer(30000, 1000) {
//
//            public void onTick(long millisUntilFinished) {
//                textview2.setText("seconds remaining: " + millisUntilFinished / 1000);
//            }
//
//            public void onFinish() {
//                textview2.setText("Time is up, try again!");
//            }

//        }.start();


    @Override
    public void onClick(View v) {

    }
}


