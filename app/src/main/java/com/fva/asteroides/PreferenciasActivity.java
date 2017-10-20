package com.fva.asteroides;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by DTIC-Dir on 19/10/2017.
 */

public class PreferenciasActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PreferenciasFragment()).commit();
    }


}
