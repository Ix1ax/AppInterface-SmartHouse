package com.example.smart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class activity_alarmsystem_device extends AppCompatActivity {

    Dialog dialog;
    Button btnDialogCancel;
    ImageView key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarmsystem_device);

        key = findViewById(R.id.key);

        dialog = new Dialog(activity_alarmsystem_device.this);
        dialog.setContentView(R.layout.camera_view);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_bg));
        dialog.setCancelable(true); // Позволяет закрыть диалог при нажатии на пустое пространство

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dialog.dismiss();
            }
        });

        btnDialogCancel = dialog.findViewById(R.id.btnDialogDCancel);

        btnDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        final boolean isFirstImage = false;

        key.setOnClickListener(new View.OnClickListener() {
            boolean isFirst = isFirstImage;
            @Override
            public void onClick(View v) {
                if (isFirst) {
                    key.setImageResource(R.drawable.lockkey);
                    isFirst = false;
                    Toasty.warning(activity_alarmsystem_device.this, "Входная дверь закрыта!", Toast.LENGTH_SHORT, true).show();
                    showNotification("Входная дверь закрыта");
                } else {
                    key.setImageResource(R.drawable.unlockkey);
                    isFirst = true;
                    Toasty.warning(activity_alarmsystem_device.this, "Входная дверь открыта!", Toast.LENGTH_SHORT, true).show();
                    showNotification("Входная дверь открыта");
                }
            }
        });
    }
    private void showNotification(String message) {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Создание канала уведомлений
            String channelId = "channel_id";
            CharSequence channelName = "Channel Name";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            manager.createNotificationChannel(channel);
        }

        // Отправка уведомления
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.drawable.logowithbg)
                .setContentTitle("Уведомление")
                .setContentText(message)
                .setColor(Color.BLACK)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        manager.notify(1, builder.build());
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

    public void CameraDialog(View V){
        dialog.show();
    }
}





