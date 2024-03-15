package com.example.smart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class activity_multicooker_device extends AppCompatActivity {

    private TextView timerTextView;
    private int minutes = 0;
    private int hours = 0;

    Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multicooker_device);

        timerTextView = findViewById(R.id.textView);
        ImageView button1 = findViewById(R.id.button1);
        ImageView button2 = findViewById(R.id.button2);

        aSwitch = findViewById(R.id.idSwitch);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (minutes >= 15) {
                    minutes -= 15;
                } else { minutes = 0;
                    if (hours > 0) {
                        hours--;
                        minutes = 60 - (15 - minutes);
                    }
                }
                updateTimer();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minutes += 15;
                if (minutes >= 60) {
                    hours++;
                    minutes -= 60;
                }
                updateTimer();
            }
        });
    }

    private void updateTimer() {
        String time = String.format(Locale.getDefault(), "%02d:%02d", hours, minutes);
        timerTextView.setText(time);
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

    public void onSwitchClick(View v){
        if(aSwitch.isChecked()) {
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

    //  Коды для режимов

    public void bakery(View V){
        Toasty.success(this, "Режим \"Выпечка\" включен", Toast.LENGTH_SHORT, true).show();
        minutes = 30;
        hours = 1;
        updateTimer();

    }

    public void soup(View V){
        Toasty.success(this, "Режим \"Супы\" включен", Toast.LENGTH_SHORT, true).show();
        minutes = 40;
        hours = 0;
        updateTimer();

    }

    public void exting(View V){
        Toasty.success(this, "Режим \"Тушение\" включен", Toast.LENGTH_SHORT, true).show();
        minutes = 0;
        hours = 1;
        updateTimer();

    }

    public void frying(View V){
        Toasty.success(this, "Режим \"Жарка\" включен", Toast.LENGTH_SHORT, true).show();
        minutes = 45;
        hours = 0;
        updateTimer();

    }

    public void baking(View V){
        Toasty.success(this, "Режим \"Запекание\" включен", Toast.LENGTH_SHORT, true).show();
        minutes = 15;
        hours = 1;
        updateTimer();

    }

    public void pasta(View V){
        Toasty.success(this, "Режим \"Паста\" включен", Toast.LENGTH_SHORT, true).show();
        minutes = 15;
        hours = 0;
        updateTimer();

    }

    public void yougurt(View V){
        Toasty.success(this, "Режим \"Йогурт\" включен", Toast.LENGTH_SHORT, true).show();
        minutes = 0;
        hours = 8;
        updateTimer();

    }

    public void jam(View V){
        Toasty.success(this, "Режим \"Варенье\" включен", Toast.LENGTH_SHORT, true).show();
        minutes = 30;
        hours = 1;
        updateTimer();
    }

    public void steaming(View V){
        Toasty.success(this, "Режим \"Варка на пару\" включен", Toast.LENGTH_SHORT, true).show();
        minutes = 45;
        hours = 0;
        updateTimer();
    }

    public void cereal(View V){
        Toasty.success(this, "Режим \"Крупа\" включен", Toast.LENGTH_SHORT, true).show();
        minutes = 35;
        hours = 0;
        updateTimer();
    }

    public void heating(View V){
        Toasty.success(this, "Режим \"Подогрев\" включен", Toast.LENGTH_SHORT, true).show();
        minutes = 15;
        hours = 0;
        updateTimer();

    }

    public void pilaf(View V){
        Toasty.success(this, "Режим \"Плов\" включен", Toast.LENGTH_SHORT, true).show();
        minutes = 0;
        hours = 1;
        updateTimer();

    }

    public void pizza(View V){
        Toasty.success(this, "Режим \"Пицца\" включен", Toast.LENGTH_SHORT, true).show();
        minutes = 45;
        hours = 0;
        updateTimer();

    }

    public void longing(View V){
        Toasty.success(this, "Режим \"Томление\" включен", Toast.LENGTH_SHORT, true).show();
        minutes = 0;
        hours = 1;
        updateTimer();

    }

    public void bread(View V){
        Toasty.success(this, "Режим \"Хлеб\" включен", Toast.LENGTH_SHORT, true).show();
        minutes = 30;
        hours = 2;
        updateTimer();

    }

}