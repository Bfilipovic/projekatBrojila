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

import java.util.concurrent.ExecutionException;

public class ListaBrojilaAct extends AppCompatActivity {

    int i,brojUredjaja;
    long idPritisnut = -1;
    boolean popunjeni[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SessionManager sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        try {
            backgroundWorker.execute("getDeviceIds").get(); //uzima deviceId iz base i cuva ih u result
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final String items[] = popuniListu(backgroundWorker.result);
        brojUredjaja = items.length;
        Toast.makeText(getApplicationContext(), "Br uredjaja "+brojUredjaja, Toast.LENGTH_LONG).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_brojila);
        LinearLayout llDugmad = findViewById(R.id.llDugmad);
        for(i = 1; i <= 5; i ++) {
            Button nb = new Button(getApplicationContext());
            nb.setText(Integer.toString(i));
            nb.setId(i);
            nb.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
            nb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(idPritisnut != -1) {
                        String selektovaniDeviceId = items[(int)idPritisnut];
                        Intent opnCameraAct = new Intent(getApplicationContext(), OpenCameraAct.class);
                        opnCameraAct.putExtra("brTarifa", Integer.parseInt(((Button) findViewById(v.getId())).getText().toString()));
                        opnCameraAct.putExtra("deviceId", selektovaniDeviceId);
                        startActivity(opnCameraAct);
                    }
                }
            });
            llDugmad.addView(nb);
        }

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
                   // Toast.makeText(getApplicationContext(), Long.toString(idPritisnut), Toast.LENGTH_LONG).show();

                    view.setBackgroundColor(Color.rgb(140, 179, 242));
                } else {
                    view.setBackgroundColor(Color.WHITE);
                    idPritisnut = -1;
                }
            }
        });
    }

 /*
   BOJI POPUNENE U ZELENO OSTALE U CRVENO
   @Override
    protected void onResume() {
        super.onResume();
        SessionManager sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        try {


            popunjeni = sessionManager.getPopunjeni();
            ListView list=(ListView) findViewById(R.id.ListaBr);
            for(int i =0;i<brojUredjaja-1;i++) {
                View nv = list.getChildAt(i);
                if (popunjeni[i] == true) {
                    nv.setBackgroundColor(Color.green(1));
                } else {
                    nv.setBackgroundColor(Color.red(1));
                }
            }

        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "OnResume "+e.getMessage(), Toast.LENGTH_LONG).show();
        }

        //Toast.makeText(getApplicationContext(), "OnResume", Toast.LENGTH_LONG).show();
    }
*/
    String[] popuniListu(String rezultat)
    {
        String deviceIds[]=rezultat.split("regex");
        return deviceIds;
    }




}
