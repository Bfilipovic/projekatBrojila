package com.example.horus_aplikacija_android_ocr;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.text.Line;

public class TarifeAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String tarife[];
        setContentView(R.layout.activity_tarife);
        LinearLayout llTarife = findViewById(R.id.llTarife);

        int brTarifa = getIntent().getIntExtra("brTarifa", 4) - 1;
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


        }
       // Toast.makeText(getApplicationContext(), prvi.toString() + " " + drugi.toString(), Toast.LENGTH_LONG).show();
    }
}
