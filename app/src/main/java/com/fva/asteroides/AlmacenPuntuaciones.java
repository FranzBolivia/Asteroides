package com.fva.asteroides;

import java.util.List;

public interface AlmacenPuntuaciones {
    void guardarPuntuacion(int i, String str, long j);

    List<String> listaPuntuaciones(int i);
}