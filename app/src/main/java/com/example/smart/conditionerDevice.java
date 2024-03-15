package com.example.smart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.dmoral.toasty.Toasty;


public class conditionerDevice extends AppCompatActivity {

    SeekBar seekBar;
    TextView powerText;
    Switch aSwitch;
    ProgressBar progressBar;
    TextView textView;
    int valueDegree = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conditioner_device);
        progressBar = findViewById(R.id.degreeBar);
        textView = findViewById(R.id.degree);

        aSwitch = findViewById(R.id.idSwitch);

        seekBar = findViewById(R.id.seekBar);
        powerText = findViewById(R.id.powerText);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // seek bar
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                powerText.setText(progress+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int savedProgress = prefs.getInt("progress", 0);
        seekBar.setProgress(savedProgress);
        powerText.setText(savedProgress + "%");
    }



    public void onSwitchClick(View v){
        if(aSwitch.isChecked()) {
            Toasty.error(this, "Устройство недоступно", Toast.LENGTH_SHORT, true).show();
        }

        aSwitch.setClickable(false); // запретить клики для предотвращения изменения значения во время задержки

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                aSwitch.setChecked(!aSwitch.isChecked());
                aSwitch.setClickable(true); // восстановить возможность кликов после восстановления значения
            }
        }, 500); // установить задержку в полсекунды (или другое нужное время)
    }




    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("degreeValue", valueDegree); // Сохраняем значение переменной degreeValue
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        valueDegree = savedInstanceState.getInt("degreeValue"); // Восстанавливаем значение переменной degreeValue
        // Здесь можно провести дополнительные операции для восстановления состояния
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Сохраняем значение переменной valueDegree при уходе со страницы
        SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
        editor.putInt("savedDegreeValue", valueDegree);
        editor.apply();
        editor.putInt("progress", seekBar.getProgress());
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Восстанавливаем значение переменной valueDegree при возвращении на страницу
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        valueDegree = prefs.getInt("savedDegreeValue", 0);
        updateDegree();
    }

    public void goToMap(View V){
        Intent intent = new Intent(this,map_device.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



    @SuppressLint("SetTextI18n")
    private void updateDegree(){
        progressBar.setProgress(valueDegree);
        textView.setText(valueDegree+"°");
    }

    public void degreeDown(View V) {
        if (valueDegree >=11){
            valueDegree-=1;
            updateDegree();
        }
    }

    public void degreeUp(View V) {
        if (valueDegree <=34){
            valueDegree+=1;
            updateDegree();
        }
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

    public void auto(View V){
        Toasty.success(this, "Режим \"Авто\" включен", Toast.LENGTH_SHORT, true).show();
    }

    public void hot(View V){
        Toasty.success(this, "Режим \"Теплый воздух\" включен", Toast.LENGTH_SHORT, true).show();
        valueDegree = 24;
        updateDegree();
    }


    public void cold(View V){
        Toasty.success(this, "Режим \"Прохладный воздух\" включен", Toast.LENGTH_SHORT, true).show();
        valueDegree = 16;
        updateDegree();
    }

    public void dry(View V){
        Toasty.success(this, "Режим \"Сухой воздух\" включен", Toast.LENGTH_SHORT, true).show();
    }
}