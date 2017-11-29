package com.fva.asteroides;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DTIC-Dir on 28/11/2017.
 */

public class AlmacenPuntuacionesJSon implements AlmacenPuntuaciones {
    private Context context;
    private String string; //Almacena puntuaciones en formato JSON

    public AlmacenPuntuacionesJSon(Context context) {
        this.context = context;
        //guardarPuntuacion(45000, "Mi nombre", System.currentTimeMillis());
        //guardarPuntuacion(31000, "Otro nombre", System.currentTimeMillis());
    }


    @Override
    public void guardarPuntuacion(int puntos, String nombre, long fecha) {
        string = leerString();
        List<Puntuacion> puntuaciones = leerJSon(string);
        puntuaciones.add(new Puntuacion(puntos, nombre, fecha));
        string = guardarJSon(puntuaciones);
        guardarString(string);
    }

    @Override
    public List<String> listaPuntuaciones(int cantidad) {
         //string = leerFichero();
        List<Puntuacion> puntuaciones = leerJSon(string);
        List<String> salida = new ArrayList<>();
        for (Puntuacion puntuacion : puntuaciones) {
            salida.add(puntuacion.getPuntos() + " " + puntuacion.getNombre());
        }
        return salida;
    }

    private String guardarJSon(List<Puntuacion> puntuaciones) {
        String string = "";
        try {
            JSONArray jsonArray = new JSONArray();
            for (Puntuacion puntuacion : puntuaciones) {
                JSONObject objeto = new JSONObject();
                objeto.put("puntos", puntuacion.getPuntos());
                objeto.put("nombre", puntuacion.getNombre());
                objeto.put("fecha", puntuacion.getFecha());
                jsonArray.put(objeto);
            }
            string = jsonArray.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return string;
    }

    private List<Puntuacion> leerJSon(String string) {
        List<Puntuacion> puntuaciones = new ArrayList<>();
        try {
            JSONArray json_array = new JSONArray(string);
            for (int i = 0; i < json_array.length(); i++) {
                JSONObject objeto = json_array.getJSONObject(i);
                puntuaciones.add(new Puntuacion(objeto.getInt("puntos"), objeto.getString("nombre"), objeto.getLong("fecha")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return puntuaciones;
    }

    public void guardarString(String texto) {
        try {
            FileOutputStream f = this.context.openFileOutput("FicheroJson.txt", 0);
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