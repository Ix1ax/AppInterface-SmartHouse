package com.example.smart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class activity_fridge_device extends AppCompatActivity {

    Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge_device);

        aSwitch = findViewById(R.id.idSwitch);

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

    public void pageMainActivity(View V){
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void goToMap(View V){
        Intent intent = new Intent(this,map_device.class);
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

    public void antifreeze(View V){
        Toasty.success(this, "Режим \"Разморозка\" включен", Toast.LENGTH_SHORT, true).show();
    }

    public void freeze(View V){
        Toasty.success(this, "Режим \"Заморозка\" включен", Toast.LENGTH_SHORT, true).show();
    }

    public void recype(View V){
        Toasty.info(this, "Переход к рецептам", Toast.LENGTH_SHORT, true).show();
        Intent intent = new Intent(this,activity_fridge_recype.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}