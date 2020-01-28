package com.dev.firstapp;

import android.content.Intent;
import android.os.Bundle;

import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceStat){
        super.onCreate(savedInstanceStat);
        setContentView(R.layout.activity_sub);

        Intent intent = new Intent(this.getIntent());
        String s = intent.getStringExtra("text");
        EditText editText = (EditText) findViewById(R.id.title);
        editText.getText();
    }
}
