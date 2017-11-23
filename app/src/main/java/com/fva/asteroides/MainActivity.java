package com.fva.asteroides;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Button bAcercaDe;
    private Button bSalir;
    private Button bJugar;
    private Button bConfig;
    MediaPlayer mp;
    //Bundle temporal;

    public static AlmacenPuntuaciones almacen = new AlmacenPuntuacionesArray();
    String var;
    int pos;
    @Override protected void onSaveInstanceState(Bundle estadoGuardado){
        super.onSaveInstanceState(estadoGuardado);
        if (mp != null) {
            int pos = mp.getCurrentPosition();
            estadoGuardado.putInt("posicion", pos);
        }
    }
    @Override protected void onRestoreInstanceState(Bundle estadoGuardado){
        super.onRestoreInstanceState(estadoGuardado);
        if (estadoGuardado != null && mp != null) {
            int pos = estadoGuardado.getInt("posicion");
            mp.seekTo(pos);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        //configuramo el objeto Media player
        mp = MediaPlayer.create(this, R.raw.audio);

        //setContentView(R.layout.activity_main_con_linearlayout);
        setContentView(R.layout.activity_main);

        TextView texto = (TextView) findViewById(R.id.textView);
        Animation animacion = AnimationUtils.loadAnimation(this, R.anim.giro_con_zoom);
        texto.startAnimation(animacion);


        bJugar = (Button) findViewById(R.id.button5);
        Animation aparecer = AnimationUtils.loadAnimation(this, R.anim.aparecer);
        bJugar.startAnimation(aparecer);


        bConfig = (Button) findViewById(R.id.button6);

        Animation animAccelerateDecelerate = AnimationUtils.loadAnimation(this, R.anim.interpolator_decelerate);
        //bConfig.startAnimation(animAccelerateDecelerate);
        bConfig.startAnimation(aparecer);


        bAcercaDe = (Button) findViewById(R.id.button7);
        bAcercaDe.setBackgroundResource(R.drawable.degradado);
        Animation desplazar = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_derecha);
        bAcercaDe.startAnimation(desplazar);


        bSalir = (Button) findViewById(R.id.button8);
        Animation desplazarIzquierda = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_izquierda);
        bSalir.startAnimation(desplazarIzquierda);


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

    @Override
    protected void onStart() {
        super.onStart();
     }

    @Override
    protected void onResume() {
        super.onResume();
        mp.start();
       // Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        //Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
        super.onPause();
        //mp.stop();
    }

    @Override
    protected void onStop() {
       //
       //Paramos el player

        super.onStop();

     //   pos=mp.getCurrentPosition();

        mp.stop();


        //temporal.putInt("posicion",5);

        //onSaveInstanceState();
        Toast.makeText(this, "Para la musica", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
       // Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
       // Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
        super.onDestroy();
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
        Animation animacion2 = AnimationUtils.loadAnimation(this, R.anim.giro_con_zoom);
        bAcercaDe.startAnimation(animacion2);
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
