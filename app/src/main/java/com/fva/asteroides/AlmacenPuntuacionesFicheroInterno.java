package com.fva.asteroides;

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AlmacenPuntuacionesFicheroInterno implements AlmacenPuntuaciones {
    private static String FICHERO = "puntuaciones.txt";
    private Context context;
    private String justificacion = "Requerido Acceso a Memoria Externa";

    public AlmacenPuntuacionesFicheroInterno(Context context) {
        this.context = context;
    }

    public void guardarPuntuacion(int puntos, String nombre, long fecha) {
        try {
            FileOutputStream f = this.context.openFileOutput(FICHERO, 32768);
            f.write((puntos + " " + nombre + "\n").getBytes());
            f.close();
        } catch (Exception e) {
            Log.e("Asteroides", e.getMessage(), e);
        }
    }

    public List<String> listaPuntuaciones(int cantidad) {
        List<String> result = new ArrayList();
        try {
            FileInputStream f = this.context.openFileInput(FICHERO);
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