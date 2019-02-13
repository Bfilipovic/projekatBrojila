package com.example.horus_aplikacija_android_ocr;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.text.Line;

public class TarifeAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SessionManager sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        String deviceId = getIntent().getStringExtra("deviceId");
        Toast.makeText(getApplicationContext(), deviceId, Toast.LENGTH_LONG).show();
       /* EditText etId = (EditText) findViewById(R.id.tbIdBrojila);
        etId.setText(deviceId);*/
        String tarife[] = new String[0];
        setContentView(R.layout.activity_tarife);
        LinearLayout llTarife = findViewById(R.id.llTarife);
        final EditText tarifeEt[] = new EditText[5];
        final int brTarifa = getIntent().getIntExtra("brTarifa", 4) - 1;
        Toast.makeText(getApplicationContext(), Integer.toString(brTarifa), Toast.LENGTH_LONG).show();
        try {
            tarife = getIntent().getStringExtra("tuta").split(",");
        } catch (Exception e) {
            tarife = new String[]{};
        }
        for(int i = brTarifa; i >= 0; i --) {
            TextView tarifa = new TextView(this);
            EditText etTarifa = new EditText(this);
            etTarifa.setInputType(InputType.TYPE_CLASS_NUMBER);
            etTarifa.setId(i);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams etparams = new LinearLayout.LayoutParams(900, LinearLayout.LayoutParams.WRAP_CONTENT);
            tarifa.setText("Tarifa " + (i + 1) + ":");
            tarifa.setTextSize(18);
            tarifa.setId(i);
            tarifa.setLayoutParams(params);
            etTarifa.setLayoutParams(etparams);
            if(i <= tarife.length - 1) {
                etTarifa.setText(tarife[i]);
            }
            llTarife.addView(etTarifa, 0);
            llTarife.addView(tarifa, 0);
            tarifeEt[i] = etTarifa;


        }

        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackgroundWorker backgroundWorker = new BackgroundWorker(TarifeAct.this);
                String tarifeLok[] = new String[5];
                for(int j =0; j<=4;j++) tarifeLok[j]="0";
                for (int i=0;i<=brTarifa;i++)
                {

                    try {
                        tarifeLok[i]=tarifeEt[i].getText().toString();
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(TarifeAct.this, "Popunite sva polja",
                                Toast.LENGTH_LONG).show();
                    }
                }


                String type = "UnosTarifa";
                EditText etId = (EditText) findViewById(R.id.tbIdBrojila);
                String id = etId.getText().toString();

                backgroundWorker.execute(type,""+brTarifa,tarifeLok[0],tarifeLok[1],tarifeLok[2],tarifeLok[3],tarifeLok[4],id);
            }


        });
       // Toast.makeText(getApplicationContext(), prvi.toString() + " " + drugi.toString(), Toast.LENGTH_LONG).show();
    }
}
