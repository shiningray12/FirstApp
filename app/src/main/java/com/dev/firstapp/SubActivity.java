package com.dev.firstapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity{
    EditText title , content, ddate;
    Button insert, modify, delete;
    @Override
    protected void onCreate(Bundle savedInstanceStat){
        super.onCreate(savedInstanceStat);
        setContentView(R.layout.activity_sub);

        title = (EditText) findViewById(R.id.title);
        content  = (EditText) findViewById(R.id.content);
        ddate = (EditText) findViewById(R.id.ddate);

        insert = (Button) findViewById(R.id.insert);
        modify = (Button) findViewById(R.id.modify);
        delete = (Button) findViewById(R.id.delete);

        insert.setOnClickListener(btnListener2);
        modify.setOnClickListener(btnListener2);
        delete.setOnClickListener(btnListener2);
    }

class CustomTask extends AsyncTask<String, Void, String> {
    String sendMsg, receiveMsg;

    @Override
    protected String doInBackground(String... strings){
        try{
            String str;
            URL url = new URL("http://52.78.173.167:8080/FirstApp/board/write.jsp");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
            sendMsg = "title= "+strings[0]+"&content="+strings[1]+"&ddate="+strings[2]+"&type="+strings[3];
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
    View.OnClickListener btnListener2 = new View.OnClickListener(){
        @Override
        public void onClick (View view){
            switch(view.getId()){
                case R.id.insert:
                    String insertTitle = title.getText().toString();
                    String insertContent = content.getText().toString();
                    String insertDdate = ddate.getText().toString();

                    try{
                        String result = new CustomTask().execute(insertTitle, insertContent, insertDdate, "add").get();
                        if(result.equals("ok")){
                            Toast.makeText(SubActivity.this,"아니옵니다.",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SubActivity.this, SubActivity.class);
                        }else {
                            Toast.makeText(SubActivity.this, "등록", Toast.LENGTH_SHORT).show();
                            title.setText("제목입력");
                            content.setText("내용 입력");
                            ddate.setText("날짜입력");
                        }
                    }catch (Exception e){   e.printStackTrace();     }
                    break;
            }



        }


    };
}