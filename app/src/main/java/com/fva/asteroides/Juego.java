package com.fva.asteroides;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by DTIC-Dir on 26/10/2017.
 */

public class Juego extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.juego);
        vistaJuego = (VistaJuego) findViewById(R.id.VistaJuego);



    }

    private VistaJuego vistaJuego;

    @Override
    protected void onPause() {
        vistaJuego.getThread().pausar();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        vistaJuego.getThread().reanudar();
        vistaJuego.activarSensores();
        Toast.makeText(this, "Activa Sensores", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        vistaJuego.getThread().detener();
        super.onDestroy();
    }




    @Override
    protected void onStop() {

        //Paramos el player
        // mp.stop();
        super.onStop();

        vistaJuego.desactivarSensores();
        Toast.makeText(this, "Desactiva Sensores", Toast.LENGTH_SHORT).show();
    }






}
