package com.fva.asteroides;

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AlmacenPuntuacionesRecursoAssets implements AlmacenPuntuaciones {
    private static String FICHERO = "puntuaciones.txt";
    private Context context;
    private String justificacion = "Requerido Acceso a Memoria Externa";

    public AlmacenPuntuacionesRecursoAssets(Context context) {
        this.context = context;
    }

    public void guardarPuntuacion(int puntos, String nombre, long fecha) {
    }

    public List<String> listaPuntuaciones(int cantidad) {
        List<String> result = new ArrayList();
        try {
            InputStream f = this.context.getAssets().open("Carpeta/puntuaciones.txt");
            BufferedReader entrada = new BufferedReader(new InputStreamReader(f));
            int n = 0;
            String linea;
            do {
                linea = entrada.readLine();
                if (linea != null) {
                    result.add(linea);
                    n++;
                }
                if (n >= cantidad) {
                    break;
                }
            } while (linea != null);
            f.close();
        } catch (Exception e) {
            Log.e("Asteroides", e.getMessage(), e);
        }
        return result;
    }
}