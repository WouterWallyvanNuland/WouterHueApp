package com.example.woutervannuland.hue;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Wouter.van.Nuland on 14/04/2017.
 */

public class AskIPActivity extends AppCompatActivity implements View.OnClickListener {

    String UserIP = "";

    EditText ip_entry_textField;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_ip);

        Button buttonSendIP = (Button) findViewById(R.id.button_sendIp);
        buttonSendIP.setOnClickListener(this);
        ip_entry_textField = (EditText) findViewById((R.id.ip_entry_textField));



    }

    @Override
    public void onClick(View v) {
        UserIP = ip_entry_textField.getText().toString();

        Intent Luuk = new Intent();
        Luuk.putExtra("UserIP", UserIP);
        setResult(7, Luuk);
        finish();
    }


}
