package com.example.smart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView categoryRecycler, deviceRecycler;

    private HorizontalScrollView horizontalScrollView;
    private int scrollPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        horizontalScrollView = findViewById(R.id.horizontalScrollView);


        SharedPreferences sharedPreferences = getSharedPreferences("scrollPosition", MODE_PRIVATE);
        int scrollX = sharedPreferences.getInt("scrollX", 0);
        horizontalScrollView.post(() -> horizontalScrollView.scrollTo(scrollX, 0));

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = getSharedPreferences("scrollPosition", MODE_PRIVATE).edit();
        editor.putInt("scrollX", horizontalScrollView.getScrollX());
        editor.apply();
    }


    public void addDeviceActivity(View V) { // переход на страницу добавления устройства
        Intent intent = new Intent(this, scaning_screen.class); // указываем с какой страницы на какую , this находимся в public classe activity
        startActivity(intent);
    }

    public void pageHelpActivity(View V){
        Intent intent = new Intent(this,MainHelp.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void goToCond(View v) { // переход к кондею
        Intent intent = new Intent(this, conditionerDevice.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void goToLamp(View v) { // переход к лампе
        Intent intent = new Intent(this, activity_lamp_device.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void goToFridge(View v) { // переход к холодосу
        Intent intent = new Intent(this, activity_fridge_device.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void goToVacuumCleaner(View v) { // переход к пылесос
        Intent intent = new Intent(this, activity_vacuum_cleaner_device.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void goToTeapot(View v) { // переход к чайнику
        Intent intent = new Intent(this, activity_teapot_device.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void goToMulticooker(View v) { // переход к мультиварке
        Intent intent = new Intent(this, activity_multicooker_device.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void goToAlarmSystem(View v) { // переход к сигнализации
        Intent intent = new Intent(this, activity_alarmsystem_device.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    public void onBackPressed(){ // блокирует кнопку назад
    }


    public void goToProfile(View v) { // переход к холодосу
        Intent intent = new Intent(this, activity_profile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void goToMap(View v) { // переход к холодосу
        Intent intent = new Intent(this, map_device.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}