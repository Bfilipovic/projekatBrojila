package com.example.horus_aplikacija_android_ocr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SessionManager sessionManager = new SessionManager(this);

        Button btnNastavi = (Button) findViewById(R.id.btnPrijava);
        btnNastavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText tbPin = (EditText) findViewById(R.id.tbPIN);
                String pin = tbPin.getText().toString();
                BackgroundWorker backgroundWorker = new BackgroundWorker(MainActivity.this);
                String type = "login";
                try {
                    backgroundWorker.execute(type,pin).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent UlogujSeIntent = new Intent(getApplicationContext(),ListaBrojilaAct.class);

                String status = backgroundWorker.getStatus().toString();

                if (backgroundWorker.result.equals("Login success!")) {
                    sessionManager.createSession(pin);
                    startActivity(UlogujSeIntent);
                }

                /*if(pin.equals("1234")){
                    Intent UlogujSeIntent = new Intent(getApplicationContext(),ListaBrojilaAct.class);
                    TextView tvUpozorenje = (TextView) findViewById(R.id.tvUpozorenje);
                    startActivity(UlogujSeIntent);
                    tvUpozorenje.setVisibility(View.INVISIBLE);
                }
                else {
                    TextView tvUpozorenje = (TextView) findViewById(R.id.tvUpozorenje);
                    tvUpozorenje.setVisibility(View.VISIBLE);
                }*/

            }
        });
    }
}
