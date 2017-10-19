package com.fva.asteroides;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by DTIC-Dir on 19/10/2017.
 */

public class PreferenciasFragment extends PreferenceFragment {
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);
    }
}
