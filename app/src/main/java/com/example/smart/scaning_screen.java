package com.example.smart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

public class scaning_screen extends AppCompatActivity {
    ProgressBar progressBar;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scaning_screen);

        ProgressBar progressBar = findViewById(R.id.progressBar);

        handler = new Handler();
        handler.postDelayed(new Runnable() {

        @Override
        public void run() {
            Intent intent = new Intent(scaning_screen.this, addDevice.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            finish();
        }
    },4000);
    }

    @Override
    public void onBackPressed(){ // блокирует кнопку назад
    }

}