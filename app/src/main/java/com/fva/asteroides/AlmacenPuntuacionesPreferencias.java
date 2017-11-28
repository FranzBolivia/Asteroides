package com.fva.asteroides;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.ArrayList;
import java.util.List;

public class AlmacenPuntuacionesPreferencias implements AlmacenPuntuaciones {
    private static String PREFERENCIAS = "puntuacionesPREF";
    private Context context;

    public AlmacenPuntuacionesPreferencias(Context context) {
        this.context = context;
    }

    public void guardarPuntuacion(int puntos, String nombre, long fecha) {
        SharedPreferences preferencias = this.context.getSharedPreferences(PREFERENCIAS,Context.MODE_PRIVATE);
        Editor editor = preferencias.edit();
        for (int n = 9; n >= 1; n--) {
            editor.putString("puntuacion" + n, preferencias.getString("puntuacion" + (n - 1), ""));
        }
        editor.putString("puntuacion0", puntos + " " + nombre);
        editor.apply();
    }

    public List<String> listaPuntuaciones(int cantidad) {
        List<String> result = new ArrayList();
        SharedPreferences preferencias = this.context.getSharedPreferences(PREFERENCIAS, 0);
        for (int n = 0; n <= 9; n++) {
            String s = preferencias.getString("puntuacion" + n, "");
            if (!s.isEmpty()) {
                result.add(s);
            }
        }
        return result;
    }
}