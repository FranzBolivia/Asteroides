package com.fva.asteroides;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AlmacenPuntuacionesFicheroExterno implements AlmacenPuntuaciones {
    private static String FICHERO = (Environment.getExternalStorageDirectory() + "/puntuacionesExt.txt");
    private Context context;

    public AlmacenPuntuacionesFicheroExterno(Context context) {
        this.context = context;
    }

    public void guardarPuntuacion(int puntos, String nombre, long fecha) {
        try {
            if (Environment.getExternalStorageState().equals("mounted")) {
                FileOutputStream f = new FileOutputStream(FICHERO, true);
                f.write((puntos + " " + nombre + "\n").getBytes());
                f.close();
                return;
            }
            Toast.makeText(this.context, "No puedo escribir en la memoria externa", 1).show();
        } catch (Exception e) {
            Log.e("Asteroides", e.getMessage(), e);
        }
    }

    public List<String> listaPuntuaciones(int cantidad) {
        String stadoSD = Environment.getExternalStorageState();
        if (stadoSD.equals("mounted") || stadoSD.equals("mounted_ro")) {
            List<String> result = new ArrayList();
            try {
                FileInputStream f = new FileInputStream(FICHERO);
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
                return result;
            } catch (Exception e) {
                Log.e("Asteroides", e.getMessage(), e);
                return result;
            }
        }
        Toast.makeText(this.context, "No puedo leer en la memoria externa", 1).show();
        return null;
    }
}