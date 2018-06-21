package com.berkay22demirel.sadece7dakikadazayfla;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button buttonBilgi;
    Button buttonEgzersizYap;
    Button buttonTalimatlar;
    Button buttonAyarlar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StartAppSDK.init(this, "205201750", true);
        setContentView(R.layout.activity_main);
        StartAppAd.disableSplash();
        getSupportActionBar().hide();

        buttonBilgi = (Button) findViewById(R.id.buttonMainBilgi);
        buttonEgzersizYap = (Button) findViewById(R.id.buttonMainEgzersizYap);
        buttonTalimatlar = (Button) findViewById(R.id.buttonMainTalimatlar);
        buttonAyarlar = (Button) findViewById(R.id.buttonMainAyarlar);


        buttonBilgi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBilgi = new Intent(MainActivity.this,BilgiActivity.class);
                startActivity(intentBilgi);
            }
        });

        buttonEgzersizYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEgzersizYap = new Intent(MainActivity.this,EgzersizYapActivity.class);
                startActivity(intentEgzersizYap);
            }
        });

        buttonTalimatlar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTalimatlar = new Intent(MainActivity.this,TalimatlarActivity.class);
                startActivity(intentTalimatlar);
            }
        });

        buttonAyarlar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAyarlar = new Intent(MainActivity.this,AyarlarActivity.class);
                startActivity(intentAyarlar);
            }
        });
    }
}
