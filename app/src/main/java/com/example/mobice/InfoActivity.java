package com.example.mobice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        //Toolbar toolbar = findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);




    }


    public void openMain(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
