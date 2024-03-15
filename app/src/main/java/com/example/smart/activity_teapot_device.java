package com.example.smart;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class activity_teapot_device extends AppCompatActivity {

    SeekBar seekBar;
    TextView powerTexts;
    Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teapot_device);

        aSwitch = findViewById(R.id.idSwitch);

        seekBar = findViewById(R.id.seekBar);
        powerTexts = findViewById(R.id.powerTexts);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // seek bar
            @Override
            public void onProgressChanged(SeekBar seekBar, int power, boolean fromUser) {
                powerTexts.setText(power + "°");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int savedProgress = prefs.getInt("degr", 0);
        seekBar.setProgress(savedProgress);
        powerTexts.setText(savedProgress + "°");

    }


    @SuppressLint("ClickableViewAccessibility")
    public void onSwitchClick(View v) {
        if (aSwitch.isChecked()) {
            Toasty.error(this, "Устройство недоступно", Toast.LENGTH_SHORT, true).show();
        }

        aSwitch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true; // Запретить свайп пальцами
            }
        });


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                aSwitch.setChecked(!aSwitch.isChecked());
                aSwitch.setOnTouchListener(null); // Восстановить свайп пальцами после задержки
            }
        }, 500);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Сохраняем значение переменной
        SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
        editor.putInt("degr", seekBar.getProgress());
        editor.apply();
    }

    public void pageMainActivity(View V){
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent); // блокирует кнопку назад
    }

    public void goToMap(View V){
        Intent intent = new Intent(this,map_device.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void blackt(View V){
        seekBar.setProgress(95);
        powerTexts.setText(95 + "°");
        Toasty.success(this, "Приготовка \"Черного чая\"", Toast.LENGTH_SHORT, true).show();
    }

    public void greent(View V){
        seekBar.setProgress(65);
        powerTexts.setText(65 + "°");
        Toasty.success(this, "Приготовка \"Зеленого чая\"", Toast.LENGTH_SHORT, true).show();
    }


    public void ulunt(View V){
        seekBar.setProgress(70);
        Toasty.success(this, "Приготовка \"Чая Улун\"", Toast.LENGTH_SHORT, true).show();
    }

    public void matchat(View V){
        seekBar.setProgress(80);
        powerTexts.setText(80 + "°");
        Toasty.success(this, "Приготовка \"Чая Матча\"", Toast.LENGTH_SHORT, true).show();
    }

    public void onemode(View V){
        seekBar.setProgress(60);
        powerTexts.setText(60 + "°");
        Toasty.success(this, "Установлен режим \"Подогрева\"", Toast.LENGTH_SHORT, true).show();
    }

    public void twomode(View V){
        seekBar.setProgress(100);
        powerTexts.setText(100 + "°");
        Toasty.success(this, "Установлен режим \"Кипячения\"", Toast.LENGTH_SHORT, true).show();
    }

}