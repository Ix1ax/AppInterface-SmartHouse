package com.example.smart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class activity_vacuum_cleaner_device extends AppCompatActivity {

    Spinner spinner;
    Switch aSwitch;
    int selectedPosition;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacuum_cleaner_device);

        aSwitch = findViewById(R.id.idSwitch);
        spinner = findViewById(R.id.spinner);

        sharedPrefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        editor = sharedPrefs.edit();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putInt("selectedPosition", position);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        selectedPosition = sharedPrefs.getInt("selectedPosition", 0);
        spinner.setSelection(selectedPosition);
    }

    public void onSwitchClick(View v){
        if(aSwitch.isChecked()) {
            Toast.makeText(getApplicationContext(), "Устройство недоступно", Toast.LENGTH_SHORT).show();
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

    public void auto(View V){
        Toasty.success(this, "Режим \"Авто\" включен", Toast.LENGTH_SHORT, true).show();
        spinner.setSelection(11); // позиция в списке начиная с 0
    }

    public void balance(View V){
        Toasty.success(this, "Режим \"Сбалансированный\" включен", Toast.LENGTH_SHORT, true).show();
        spinner.setSelection(8); // позиция в списке начиная с 0
    }

    public void turbo(View V){
        Toasty.success(this, "Режим \"Турбо\" включен", Toast.LENGTH_SHORT, true).show();
        spinner.setSelection(4); // позиция в списке начиная с 0
    }

    public void max(View V){
        Toasty.success(this, "Режим \"Максимальный\" включен", Toast.LENGTH_SHORT, true).show();
        spinner.setSelection(3); // позиция в списке начиная с 0
    }

}

