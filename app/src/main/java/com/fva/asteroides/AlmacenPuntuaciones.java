package com.fva.asteroides;

import java.util.Vector;

/**
 * Created by DTIC-Dir on 19/10/2017.
 */

public interface AlmacenPuntuaciones {
    public void guardarPuntuacion(int puntos,String nombre,long fecha);
    public Vector<String> listaPuntuaciones(int cantidad);
}
