package com.example.horus_aplikacija_android_ocr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ListaBrojilaAct extends AppCompatActivity {
    String items [] =new String[] {"Milica Nikolic, 102554","Milutin Milicevic, 15874", "Bratislav Filipovic, 11111"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_brojila);
       /* Button btnPisi = (Button) findViewById(R.id.btnPisi);
        btnPisi.setEnabled(false);
        Button btnSlikaj = (Button) findViewById(R.id.btnSlikaj);
        btnSlikaj.setEnabled(false);*/
       ListView list=(ListView) findViewById(R.id.ListaBr);
        ListAdapter adapter= new ArrayAdapter<String>(this,android.R.layout.simple_selectable_list_item,items);
       list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent opnCameraAct= new Intent(getApplicationContext(),OpenCameraAct.class);
                startActivity(opnCameraAct);
            }
        });

    }
}
