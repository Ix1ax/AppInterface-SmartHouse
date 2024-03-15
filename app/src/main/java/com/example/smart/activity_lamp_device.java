package com.example.smart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import yuku.ambilwarna.AmbilWarnaDialog;


public class activity_lamp_device extends AppCompatActivity {


    SeekBar seekBar;
    TextView powerTexts;

    Switch aSwitch;
    private ImageView imageView;


    int defaultColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp_device);

        aSwitch = findViewById(R.id.idSwitch);

        imageView = findViewById(R.id.imageView);

        seekBar = findViewById(R.id.seekBar);
        powerTexts = findViewById(R.id.powerTexts);

        defaultColor = ContextCompat.getColor(this, com.google.android.material.R.color.material_blue_grey_800);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker();

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // seek bar
            @Override
            public void onProgressChanged(SeekBar seekBar, int power, boolean fromUser) {
                powerTexts.setText(power + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int savedProgress = prefs.getInt("power", 0);
        seekBar.setProgress(savedProgress);
        powerTexts.setText(savedProgress + "%");

    }

    public void openColorPicker() {
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                defaultColor = color;
                imageView.setColorFilter(defaultColor);
            }
        });
        ambilWarnaDialog.show();
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

    public void pageMainActivity(View V) {
        Intent intent = new Intent(this, MainActivity.class);
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

    @Override
    protected void onPause() {
        super.onPause();
        // Сохраняем значение переменной
        SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
        editor.putInt("power", seekBar.getProgress());
        editor.apply();
    }

    public void hot(View V){
        Toasty.success(this, "Режим \"Теплый свет\" включен", Toast.LENGTH_SHORT, true).show();
    }

    public void cold(View V){
        Toasty.success(this, "Режим \"Холодный свет\" включен", Toast.LENGTH_SHORT, true).show();
    }


    public void auto(View V){
        Toasty.success(this, "Режим \"Авто\" включен", Toast.LENGTH_SHORT, true).show();
    }

    public void alarm(View V){
        SharedPreferences preferences = getSharedPreferences("AlarmTime", MODE_PRIVATE);
        int defaultHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int defaultMinute = Calendar.getInstance().get(Calendar.MINUTE);
        int savedHour = preferences.getInt("hour", defaultHour);
        int savedMinute = preferences.getInt("minute", defaultMinute);

        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Сохраняем выбранное время
                SharedPreferences.Editor editor = getSharedPreferences("AlarmTime", MODE_PRIVATE).edit();
                editor.putInt("hour", hourOfDay);
                editor.putInt("minute", minute);
                editor.apply();

                // Обработка выбранного времени
                String time = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                Toasty.success(activity_lamp_device.this, "Выбрано время: " + time, Toast.LENGTH_SHORT,true ).show();
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(new ContextThemeWrapper(this, R.style.BlackTimePickerDialogTheme),
                timeSetListener,
                savedHour, savedMinute, true);
        timePickerDialog.show();

        Button positiveButton = timePickerDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button negativeButton = timePickerDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
    }


}