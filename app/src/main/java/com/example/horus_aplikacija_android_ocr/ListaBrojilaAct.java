package com.example.horus_aplikacija_android_ocr;

import android.app.AlertDialog;
import android.app.LauncherActivity;
import android.content.ClipData;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.text.Line;

import java.util.concurrent.ExecutionException;

public class ListaBrojilaAct extends AppCompatActivity {

    int i,brojUredjaja;
    long idPritisnut = -1;
    boolean popunjeni[];
    int zelena = Color.rgb(158,244,139);
    int crvena = Color.rgb(249,129,129);
    int plava = Color.rgb(140, 179, 242);
    int prethodnaBojaSelektovanog = crvena;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //SESIJA ZA LOGOVANJE, PIN ULOGOVANOG SE NALAZI U sessionManager.getUserDetail()

        SessionManager sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        idPritisnut=-1;

        //BACKGROUND WORKER UCITAVA BROJILA IZ POVEZUJE SE SA BAZOM
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
        Toast.makeText(getApplicationContext(), "Number of devices: "+brojUredjaja, Toast.LENGTH_LONG).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_brojila);


        final Intent opnCameraAct = new Intent(getApplicationContext(), OpenCameraAct.class);

        // *******************PRAVI DUGMICE*********************
        LinearLayout llDugmad = findViewById(R.id.llDugmad);
        for(i = 1; i <= 5; i ++) {
            Button nb = new Button(getApplicationContext());
            nb.setText(Integer.toString(i));
            nb.setId(i);
            nb.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
            nb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(idPritisnut > -1) {

                        ListView list=(ListView) findViewById(R.id.ListaBr);
                        idPritisnut=list.getSelectedItemId();
                        opnCameraAct.putExtra("brTarifa", Integer.parseInt(((Button) findViewById(v.getId())).getText().toString()));
                        startActivity(opnCameraAct);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "You must select a device from the list \n If a device is already selected, just click on it again", Toast.LENGTH_LONG).show();
                    }
                }
            });
            llDugmad.addView(nb);
        }

        //STAVLJA ITEME U LISTU
        final ListView list=(ListView) findViewById(R.id.ListaBr);
        ListAdapter adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,items);
        idPritisnut=list.getSelectedItemId();
        list.setAdapter(adapter);

        //MENJA selektovaniDeviceId i dodaje ga u opnCameraIntent
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (idPritisnut != id) {
                    if (idPritisnut != -1) {
                        View nv = list.getChildAt((int) idPritisnut);
                    }
                    idPritisnut = id;
                    //Toast.makeText(getApplicationContext(), items[(int)idPritisnut]+" poz: "+idPritisnut, Toast.LENGTH_LONG).show();
                    String selektovaniDeviceId=items[(int)idPritisnut];
                    opnCameraAct.putExtra("deviceId", selektovaniDeviceId);
                    opnCameraAct.putExtra("pozicijaPritisnutog", ""+idPritisnut);
                } else {
                    idPritisnut = -1;
                }
            }

        });

     //   obojSveCrveno();
    }


    //IZVRSAVA SE SVAKI PUT KAD SE AKTIVITI PRIKAZE, BOJI PROCITANO POLJE U ZELENO
   @Override
    protected void onResume() {
        super.onResume();
        SessionManager sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        try {

            AlertDialog alertDialog;
            alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("onResume");
            int poslednjiPopunjen = sessionManager.getPosljednjiOcitan();
            //alertDialog.setMessage(""+ sessionManager.getPosljednjiOcitan());

            // BOJI U ZELENO
            if(poslednjiPopunjen!=-1) {
                //alertDialog.show();
                ListView list = (ListView) findViewById(R.id.ListaBr);
                View nv = list.getChildAt(poslednjiPopunjen);
                nv.setBackgroundColor(zelena);
            }


            ListView list = (ListView) findViewById(R.id.ListaBr);
            int selektovani = list.getSelectedItemPosition();
            sessionManager.postaviPoslednjiOcitan(selektovani);
            idPritisnut = selektovani;



        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error "+e.getMessage(), Toast.LENGTH_LONG).show();
        }

        //Toast.makeText(getApplicationContext(), "OnResume", Toast.LENGTH_LONG).show();
    }

    void obojSveCrveno()
    {
        AlertDialog alertDialog;
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("ObojSveCrveno");

        try {
            ListView list=(ListView) findViewById(R.id.ListaBr);
            View nv = list.getChildAt(0);
            for(int i =0;i<brojUredjaja;i++)
            {

                nv.setBackgroundColor(crvena);
            }
        }
        catch (Exception e) {
            alertDialog.setMessage(e.getMessage());
            alertDialog.show();
        }
    }
    String[] popuniListu(String rezultat)
    {
        String deviceIds[]=rezultat.split("regex");
        return deviceIds;
    }


}
