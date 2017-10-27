package com.fva.asteroides;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button bAcercaDe;
    private Button bSalir;
    private Button bJugar;
    public static AlmacenPuntuaciones almacen = new AlmacenPuntuacionesArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_con_linearlayout);
        setContentView(R.layout.activity_main);
        bJugar = (Button) findViewById(R.id.button5);


        bAcercaDe = (Button) findViewById(R.id.button7);
        bAcercaDe.setBackgroundResource(R.drawable.degradado);
        bSalir = (Button) findViewById(R.id.button8);

        bJugar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                lanzarJuego(null);

            }
        });


        bAcercaDe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                lanzarAcercaDe(null);
            }
        });
        bSalir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                lanzarPuntuaciones(null);

            }
        });
    }

    public void lanzarJuego(View view) {
        Intent i = new Intent(this, Juego.class);
        startActivity(i);
    }

    public void lanzarPuntuaciones(View view) {
        Intent i = new Intent(this, Puntuaciones.class);
        startActivity(i);
    }

    public void lanzarAcercaDe(View view) {
        Intent i = new Intent(this, acercaDeActivity.class);
        startActivity(i);
    }

    public void lanzarpreferencias(View view) {
        Intent i = new Intent(this, PreferenciasActivity.class);
        startActivity(i);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menun_main, menu);
        return true; /** true -> el menú ya está visible */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            lanzarpreferencias(null);
            return true;
        }
        if (id == R.id.acercaDe) {
            lanzarAcercaDe(null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
