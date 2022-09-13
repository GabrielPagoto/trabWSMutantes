package com.example.trabwsmutantes.Controller;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;

import com.example.trabwsmutantes.R;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent it = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(it);
                finish();
            }
        }, 3000);


    }
}