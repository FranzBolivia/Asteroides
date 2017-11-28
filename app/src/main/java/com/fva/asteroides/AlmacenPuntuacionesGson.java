package com.fva.asteroides;

/**
 * Created by DTIC-Dir on 27/11/2017.
 */
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class AlmacenPuntuacionesGson implements AlmacenPuntuaciones {
    private Context context;
    private Gson gson = new Gson();
    private String string;
    private Type type = new TypeToken<List<Puntuacion>>() {}.getType();

    public AlmacenPuntuacionesGson(Context context) {
        this.context = context;
    }

    public void guardarPuntuacion(int puntos, String nombre, long fecha) {
        ArrayList<Puntuacion> puntuaciones;
        this.string = leerString();
        if (this.string == null) {
            puntuaciones = new ArrayList();
        } else {
            puntuaciones = (ArrayList) this.gson.fromJson(this.string, this.type);
        }
        puntuaciones.add(new Puntuacion(puntos, "Franz", fecha));
        this.string = this.gson.toJson(puntuaciones, this.type);
        Log.i("Valor del string....", this.string);
        guardarString(this.string);
    }

    public List<String> listaPuntuaciones(int cantidad) {
        ArrayList<Puntuacion> puntuaciones;
        this.string = leerString();
        Log.i("Valor del string....", this.string);
        if (this.string == null) {
            puntuaciones = new ArrayList();
        } else {
            puntuaciones = (ArrayList) this.gson.fromJson(this.string, this.type);
        }
        List<String> salida = new ArrayList();
        Iterator it = puntuaciones.iterator();
        while (it.hasNext()) {
            Puntuacion puntuacion = (Puntuacion) it.next();
            salida.add(puntuacion.getPuntos() + " " + puntuacion.getNombre());
        }
        return salida;
    }

    public void guardarString(String texto) {
        try {
            FileOutputStream f = this.context.openFileOutput("FicheroGson.txt", 0);
            f.write(texto.getBytes());
            f.close();
        } catch (Exception e) {
            Log.e("Asteorides Gson", e.getMessage(), e);
        }
    }

    public String leerString() {
        try {
            return new BufferedReader(new InputStreamReader(this.context.openFileInput("FicheroGson.txt"))).readLine();
        } catch (Exception e) {
            Log.e("Asteroides", e.getMessage(), e);
            return null;
        }
    }
}


