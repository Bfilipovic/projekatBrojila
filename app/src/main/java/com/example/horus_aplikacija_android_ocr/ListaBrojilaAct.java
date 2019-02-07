package com.example.horus_aplikacija_android_ocr;

import android.app.LauncherActivity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ListMenuItemView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.vision.text.Line;

public class ListaBrojilaAct extends AppCompatActivity {
    String items [] =new String[] {"Milica Nikolic, 102554","Milutin Milicevic, 15874", "Bratislav Filipovic, 11111"};
    int i;
    long idPritisnut = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_brojila);
        LinearLayout llDugmad = findViewById(R.id.llDugmad);
        for(i = 2; i <= 5; i ++) {
            Button nb = new Button(getApplicationContext());
            nb.setText(Integer.toString(i));
            nb.setId(i);
            nb.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
            nb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(idPritisnut != -1) {
                        Intent opnCameraAct = new Intent(getApplicationContext(), OpenCameraAct.class);
                        opnCameraAct.putExtra("brTarifa", Integer.parseInt(((Button) findViewById(v.getId())).getText().toString()));
                        startActivity(opnCameraAct);
                    }
                }
            });
            llDugmad.addView(nb);
        }
       /* Button btnPisi = (Button) findViewById(R.id.btnPisi);
        btnPisi.setEnabled(false);
        Button btnSlikaj = (Button) findViewById(R.id.btnSlikaj);
        btnSlikaj.setEnabled(false);*/
       final ListView list=(ListView) findViewById(R.id.ListaBr);
        ListAdapter adapter= new ArrayAdapter<String>(this,android.R.layout.simple_selectable_list_item,items);
       list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (idPritisnut != id) {
                    if (idPritisnut != -1) {
                        View nv = list.getChildAt((int) idPritisnut);
                        nv.setBackgroundColor(Color.WHITE);
                    }
                    idPritisnut = id;
                    Toast.makeText(getApplicationContext(), Long.toString(idPritisnut), Toast.LENGTH_LONG).show();

                    view.setBackgroundColor(Color.rgb(140, 179, 242));
                } else {
                    view.setBackgroundColor(Color.WHITE);
                    idPritisnut = -1;
                }
            }
        });
    }
}
