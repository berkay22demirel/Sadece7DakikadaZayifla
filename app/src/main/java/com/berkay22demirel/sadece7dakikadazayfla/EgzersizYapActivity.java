package com.berkay22demirel.sadece7dakikadazayfla;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;

import java.util.concurrent.TimeUnit;


public class EgzersizYapActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    TextView textViewGeriSayim;
    ImageButton imageButtonDuraklat;
    ImageView imageViewHareket;
    TextView textViewAciklamaBir;
    TextView textViewAciklamaIki;
    TextView textViewAciklamaUc;

    //Başlangıç Dailog Elemanları
    Button buttonCustomDialogBasla;
    Button buttonCustomDialogGeriDon;

    //Bitiş dailog Elemanları
    Button buttonCustomDialogTamamla;
    TextView textViewCustomDialogSayi;

    int hareket = 1;   //şuan hangi hareket yapılıyor
    int buttonKontrol = 0;      //duraklat devam et button unun durumu 0 oynatılıyor 1 duraklatılmış
    long suankiZaman = 0;       //timer duraklatıldığında o anki zamanı tutan değişken
    CountDownTimer countDownTimer = null;

    MediaPlayer dudukSesi;
    MediaPlayer muzik = null;

    SharedPreferences preferences;
    boolean muzikTercihi;
    boolean dudukTercihi;
    int setSayisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StartAppSDK.init(this, "205201750", true);
        setContentView(R.layout.activity_egzersiz_yap);
        StartAppAd.disableSplash();
        getSupportActionBar().setTitle("EGZERSİZ YAP");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textViewGeriSayim = (TextView) findViewById(R.id.textViewEgzersizYapGeriSayim);
        imageButtonDuraklat = (ImageButton) findViewById(R.id.imageButtonEgzersizYapDuraklat);
        imageViewHareket = (ImageView) findViewById(R.id.imageViewEgzersizYapHareket);
        textViewAciklamaBir = (TextView) findViewById(R.id.textViewEgzersizYapAciklama1);
        textViewAciklamaIki = (TextView) findViewById(R.id.textViewEgzersizYapAciklama2);
        textViewAciklamaUc = (TextView) findViewById(R.id.textViewEgzersizYapAciklama3);

        dudukSesi = MediaPlayer.create(this, R.raw.hakemduduksesi);
        muzik = MediaPlayer.create(this,R.raw.muzik);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(this);
        muzikTercihi = preferences.getBoolean("switch_preference_muzik",true);
        dudukTercihi = preferences.getBoolean("switch_preference_duduk",true);
        setSayisi = preferences.getInt("set_sayisi",0);


        showBaslaCustomDailog();

        imageButtonDuraklat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonKontrol == 0){
                    countDownTimer.cancel();
                    buttonKontrol = 1;
                    imageButtonDuraklat.setImageResource(R.drawable.devamet);
                    if(muzikTercihi){
                        muzik.pause();
                    }
                }else{
                    buttonKontrol = 0;
                    imageButtonDuraklat.setImageResource(R.drawable.duraklat);

                    countDownTimer = new CountDownTimer(suankiZaman, 1000) {
                        public void onTick(long millisUntilFinished) {
                            textViewGeriSayim.setText(""+String.format("%d",
                                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                                    toMinutes(millisUntilFinished))));
                            suankiZaman = millisUntilFinished;

                        }
                        public void onFinish() {
                            hareket += 1;
                            if(hareket == 25){
                                setSayisi += 1;
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putInt("set_sayisi",setSayisi);
                                editor.apply();
                                showBitisCustomDailog();
                            }else{
                                if(dudukTercihi){
                                    dudukSesi.start();
                                }
                                zamanla();
                            }
                        }

                    }.start();
                    if(muzikTercihi){
                        muzik.start();
                    }
                }
            }
        });


    }

    public void zamanla(){
        int gerisayim;
        if((hareket % 2) == 1){
            gerisayim = 31000;
        }else{
            gerisayim = 11000;
        }

        if((hareket % 2) == 0){
            imageViewHareket.setImageResource(R.drawable.dinlenme);
            textViewAciklamaBir.setText("* - 10 saniye boyunca dinlenme.");
            textViewAciklamaIki.setText("");
            textViewAciklamaUc.setText("");
        }
        if(hareket == 3){
            imageViewHareket.setImageResource(R.drawable.iki);
            textViewAciklamaBir.setText("1 - Eller bağlı bir şekilde oturma pozisyonunda durma.");
            textViewAciklamaIki.setText("* - 30 saniye boyunca aynı pozisyonda durulmalıdır.");
            textViewAciklamaUc.setText("");
        }else if(hareket == 5){
            imageViewHareket.setImageResource(R.drawable.uc);
            textViewAciklamaBir.setText("1 - Standart şınav çekme hareketi.");
            textViewAciklamaIki.setText("* - 30 saniye boyunca mümkün olan en hızlı şekilde tekrar.");
            textViewAciklamaUc.setText("");
        }else if(hareket == 7){
            imageViewHareket.setImageResource(R.drawable.dort);
            textViewAciklamaBir.setText("1 - Standart mekik hareketi.");
            textViewAciklamaIki.setText("* - 30 saniye boyunca mümkün olan en hızlı şekilde tekrar.");
            textViewAciklamaUc.setText("");
        }else if(hareket == 9){
            imageViewHareket.setImageResource(R.drawable.bes);
            textViewAciklamaBir.setText("1 - Bir ayak sandalye üzerine konulur.");
            textViewAciklamaIki.setText("2 - Diğer ayak sandalye üzerine konularak sandalye üzerine çıkılır.");
            textViewAciklamaUc.setText("* - 30 saniye boyunca mümkün olan en hızlı şekilde tekrar.");
        }else if(hareket == 11){
            imageViewHareket.setImageResource(R.drawable.alti);
            textViewAciklamaBir.setText("1 - Eller iki yanda dik bir duruş.");
            textViewAciklamaIki.setText("2 - Eller öne doğru ve çömelme hareketi.");
            textViewAciklamaUc.setText("* - 30 saniye boyunca mümkün olan en hızlı şekilde tekrar.");
        }else if(hareket == 13){
            imageViewHareket.setImageResource(R.drawable.yedi);
            textViewAciklamaBir.setText("1 - Sandalyeye doğru ters bir duruş ve iki elin sandalyeye konulması.");
            textViewAciklamaIki.setText("2 - Sandalyeden güç alarak vücudu aşağıya doğru indirme hareketi.");
            textViewAciklamaUc.setText("* - 30 saniye boyunca mümkün olan en hızlı şekilde tekrar.");
        }else if(hareket == 15){
            imageViewHareket.setImageResource(R.drawable.sekiz);
            textViewAciklamaBir.setText("1 - Dirsekler üzerinde durularak şınav hareketi.");
            textViewAciklamaIki.setText("* - 30 saniye boyunca mümkün olan en hızlı şekilde tekrar.");
            textViewAciklamaUc.setText("");
        }else if(hareket == 17){
            imageViewHareket.setImageResource(R.drawable.dokuz);
            textViewAciklamaBir.setText("1 - Dizler kaldırılarak olunan yerde koşma hareketi.");
            textViewAciklamaIki.setText("* - 30 saniye boyunca mümkün olan en hızlı şekilde tekrar.");
            textViewAciklamaUc.setText("");
        }else if(hareket == 19){
            imageViewHareket.setImageResource(R.drawable.on);
            textViewAciklamaBir.setText("1 - Eller belde olacak şekilde düz bir duruş.");
            textViewAciklamaIki.setText("2 - Bir diz yere değecek şekilde eğilme.");
            textViewAciklamaUc.setText("* - 30 saniye boyunca mümkün olan en hızlı şekilde tekrar.");
        }else if(hareket == 21){
            imageViewHareket.setImageResource(R.drawable.onbir);
            textViewAciklamaBir.setText("1 - Şınav pozisyonunda eğilme.");
            textViewAciklamaIki.setText("2 - Kalkış pozisyonunda tek el üzerinde durularak diğer elin havaya kaldırılması hareketi.");
            textViewAciklamaUc.setText("* - 30 saniye boyunca mümkün olan en hızlı şekilde tekrar.");
        }else if(hareket == 23){
            imageViewHareket.setImageResource(R.drawable.oniki);
            textViewAciklamaBir.setText("1 - Yan pozisyonda dirsek üzerinde durma.");
            textViewAciklamaIki.setText("2 - Dirsek üzerinde şınav hareketi.");
            textViewAciklamaUc.setText("* - 30 saniye boyunca mümkün olan en hızlı şekilde tekrar.");
        }
        countDownTimer = new CountDownTimer(gerisayim, 1000) {
            public void onTick(long millisUntilFinished) {
                textViewGeriSayim.setText(""+String.format("%d",
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                        toMinutes(millisUntilFinished))));
                suankiZaman = millisUntilFinished;

            }
            public void onFinish() {
                hareket += 1;
                if(hareket == 25){
                    setSayisi += 1;
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("set_sayisi",setSayisi);
                    editor.apply();
                    showBitisCustomDailog();
                }else{
                    if(dudukTercihi){
                        dudukSesi.start();
                    }
                    zamanla();
                }

            }

        }.start();
    }


    public void showBaslaCustomDailog(){
        final Dialog dialog = new Dialog(EgzersizYapActivity.this);
        dialog.setContentView(R.layout.custom_dialog_egzersiz_yap_baslangic);

        buttonCustomDialogBasla = (Button) dialog.findViewById(R.id.buttonCustomDialogEgzersizBasla);
        buttonCustomDialogGeriDon = (Button) dialog.findViewById(R.id.buttonCustomDialogEgzersizGeriDon);

        buttonCustomDialogGeriDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });

        buttonCustomDialogBasla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(dudukTercihi){
                    dudukSesi.start();
                }
                zamanla();
                if(muzikTercihi){
                    muzik.start();
                }

            }
        });

        dialog.show();
    }

    public void showBitisCustomDailog(){
        final Dialog dialogBitis = new Dialog(EgzersizYapActivity.this);
        dialogBitis.setContentView(R.layout.custom_dialog_egzersiz_yap_bitis);

        buttonCustomDialogTamamla = (Button) dialogBitis.findViewById(R.id.buttonCustomDialogEgzersizBitis);
        textViewCustomDialogSayi = (TextView) dialogBitis.findViewById(R.id.textViewCustonDialogEgzersizBitisSayi);

        textViewCustomDialogSayi.setText(""+String.format("Toplamda tamamladığınız %d. set!", setSayisi ));

        buttonCustomDialogTamamla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBitis.dismiss();
                countDownTimer.cancel();
                finish();
                if(muzikTercihi){
                    muzik.stop();
                }

            }
        });

        dialogBitis.show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                countDownTimer.cancel();
                if(muzikTercihi){
                    muzik.stop();
                }
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        muzikTercihi = sharedPreferences.getBoolean("switch_preference_muzik",true);
        dudukTercihi = sharedPreferences.getBoolean("switch_preference_duduk",true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
        if(muzikTercihi){
            muzik.stop();
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
        if(muzik != null){
            if(muzikTercihi){
                muzik.stop();
            }
        }

    }
}
