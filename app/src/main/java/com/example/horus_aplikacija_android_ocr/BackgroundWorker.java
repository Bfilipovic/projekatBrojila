package com.example.horus_aplikacija_android_ocr;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.app.AlertDialog;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    String result = "";
    BackgroundWorker(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String login_url = "http://185.183.182.68/login.php";
        String serverIp = "http://185.183.182.68/";
        if (type.equals("login")) {
            try {
                String pin = params[1];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("pin", "UTF-8") + "=" + URLEncoder.encode(pin, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                result = e.getMessage().toString();
            } catch (IOException e) {
                e.printStackTrace();
                result = e.getMessage().toString();
            }
        }

        if (type.equals("UnosTarifa")) {
            try {
                int brTarifa =Integer.parseInt(params[1]);
                String id = params[7];
                String tarife[]=new String[5];
                for (int i =0; i<=4;i++)
                {
                    tarife[i]=params[i+2];
                }
                URL url = new URL(serverIp+"tarife.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data="";
                for(int i = 0; i<=4;i++) {
                    int t=i+1;
                    post_data += URLEncoder.encode("tarifa"+t, "UTF-8") + "=" + URLEncoder.encode(tarife[i], "UTF-8");
                    post_data +="&";

                }
                post_data += URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");

                //post_data=post_data.substring(0,post_data.length()-1);
               // result+=post_data;
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String line = "";
               while ((line = bufferedReader.readLine()) != null) {
                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                result = e.getMessage().toString();
            } catch (IOException e) {
                e.printStackTrace();
                result = e.getMessage().toString();
            }
        }

        if (type.equals("getDeviceIds")) {
            try {
                URL url = new URL(serverIp+"getDevices.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
               // httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                result = e.getMessage().toString();
            } catch (IOException e) {
                e.printStackTrace();
                result = e.getMessage().toString();
            }
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Status");
    }

    @Override
    protected void onPostExecute(String result) {
        if(!result.contains("regex")) {
            alertDialog.setMessage(result);
            alertDialog.show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}

