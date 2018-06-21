package com.berkay22demirel.sadece7dakikadazayfla;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by BerkayDemirel on 19.06.2018.
 */

public class AyarlarFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.ayarlar);
    }
}
