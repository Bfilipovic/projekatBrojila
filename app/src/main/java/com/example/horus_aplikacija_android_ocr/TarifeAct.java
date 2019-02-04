package com.example.horus_aplikacija_android_ocr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TarifeAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String prvi = "", drugi = "", kurcekanja[];
        kurcekanja = getIntent().getStringExtra("tuta").split(",");
        prvi = kurcekanja[0];
        drugi = kurcekanja[1];
        Toast.makeText(getApplicationContext(), prvi.toString() + " " + drugi.toString(), Toast.LENGTH_LONG).show();

        setContentView(R.layout.activity_tarife);
        EditText tbPrvi = (EditText) findViewById(R.id.tbTarifa1);
        EditText tbDrugi = (EditText) findViewById(R.id.tbTarifa2);
        tbDrugi.setText(drugi);
        tbPrvi.setText(prvi);
    }
}
