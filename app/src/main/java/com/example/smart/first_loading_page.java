package com.example.smart;

import androidx.appcompat.app.AppCompatActivity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class first_loading_page extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_loading_page);
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        NetworkInfo wifiNetwork = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileNetwork = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (bluetoothAdapter == null) {
            Toasty.error(this, "Bluetooth не поддерживается", Toasty.LENGTH_SHORT).show();
        } else {
            if (!bluetoothAdapter.isEnabled()) {
                Toasty.error(this, "Пожалуйста включите Bluetooth", Toasty.LENGTH_SHORT,true).show();

                Intent intent = new Intent(this, restart_app_page.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                finish();
            }

            else {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(first_loading_page.this, activity_enter_page.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                        finish();
                    }
                }, 2000);
            }
        }
    }

    @Override
    public void onBackPressed() {
    }
}