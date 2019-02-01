package com.example.horus_aplikacija_android_ocr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class OpenCameraAct extends AppCompatActivity {

    public static final int CAMERA_REQUEST=9999;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_camera);
        image =(ImageView) findViewById(R.id.imageViewSlika);
        Button dugme=(Button) findViewById(R.id.btnTakePic);
        dugme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent UseCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(UseCamera,CAMERA_REQUEST);
            }
        });

        Button btnConfrim=(Button) findViewById(R.id.btnConfrim);
        btnConfrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToTarife=new Intent(getApplicationContext(),TarifeAct.class);
                startActivity(goToTarife);
            }
        });
        Button btnEdit= (Button) findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToTarife=new Intent(getApplicationContext(),TarifeAct.class);
                startActivity(goToTarife);
            }
        });

    }


    /*public void OpenCamera(View view)
    {

        Intent UseCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(UseCamera,CAMERA_REQUEST);
    }*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if( requestCode==CAMERA_REQUEST)
        {
            Bitmap bitmap=(Bitmap) data.getExtras().get("data");
            image.setImageBitmap(bitmap);

        }
    }

}
