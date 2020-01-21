package com.dev.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button data = (Button)findViewById(R.id.sendData);
        data.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                try{
                    String result;
                    CustomTask task = new CustomTask();
                    result = task.execute("cdy", "123").get();
                    Log.i("리턴값", result);
                }catch (Exception e){

                }
            }
        });
        CustomTask task = new CustomTask();
    }
}
class CustomTask extends AsyncTask<String, Void, String>{
    String sendMsg, receiveMsg;

    @Override
    protected String doInBackground(String... strings){
        try{
            String str;
            URL url = new URL("http://15.164.251.191:8080/FirstApp/Test.jsp");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-rulencoded");
            conn.setRequestMethod("POST");
            OutputStreamWriter osw = new OutputStreamWriter((conn.getOutputStream()));
            sendMsg = "id= "+strings[0]+"&pwd="+strings[1];
            osw.write(sendMsg);
            osw.flush();
            if(conn.getResponseCode()==conn.HTTP_OK){
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();

                while ((str=reader.readLine())!=null){
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
            }else {
                Log.i("통신결과", conn.getResponseCode()+"에러");
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return receiveMsg;
    }
}
