package com.theakatsuki.hiredevelopers.ActivityPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.theakatsuki.hiredevelopers.R;

public class SplashScreenActivity extends AppCompatActivity {

    private TextView textagain, textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        textView = findViewById(R.id.noInternet);
        textagain = findViewById(R.id.tryAgain);

        final boolean wifi = checkConnection();
        refresh(wifi);

        textagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SplashScreenActivity.this, "Cheching Connection", Toast.LENGTH_SHORT).show();
                boolean checkConnection = checkConnection();
                refresh(checkConnection);
            }
        });
    }

    private void refresh(boolean wifi) {
        if (wifi) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 2000);
        } else {
            textView.setVisibility(View.VISIBLE);
            textagain.setVisibility(View.VISIBLE);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
                }
            });
            Toast.makeText(this, "No Connection Found", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkConnection(){
        ConnectivityManager connectivityManager=(ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi=connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);
        NetworkInfo network=connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);

        if(wifi.isConnected()){
            return true;
        }
        else if(network.isConnected()){
            return true;
        }else{
            return false;
        }
    }
}
