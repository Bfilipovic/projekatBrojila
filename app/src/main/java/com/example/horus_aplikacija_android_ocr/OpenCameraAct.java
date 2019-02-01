package com.example.horus_aplikacija_android_ocr;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;


public class OpenCameraAct extends AppCompatActivity {

    public static final int CAMERA_REQUEST=9999;
    ImageView image;
    Bitmap bmimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this , new String[] {Manifest.permission.CAMERA}, CAMERA_REQUEST);
        setContentView(R.layout.activity_open_camera);
        image = (ImageView) findViewById(R.id.imageViewSlika);
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
                TextRecognizer recognizer = new TextRecognizer.Builder(getApplicationContext()).build();
                if(!recognizer.isOperational())
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                else {
                    Frame frame = new Frame.Builder().setBitmap(bmimage).build();
                    SparseArray<TextBlock> items = recognizer.detect(frame);
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < items.size(); i ++) {
                        TextBlock myItem = items.valueAt(i);
                        sb.append(myItem.getValue());
                        sb.append('\n');
                    }
                    TextView test = (TextView) findViewById(R.id.tvTest);
                    test.setText(sb.toString());
                }
                //Intent goToTarife = new Intent(getApplicationContext(),TarifeAct.class);
                //startActivity(goToTarife);
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
            bmimage = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(bmimage);

        }
    }

}
