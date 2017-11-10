package com.fva.asteroides;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button bAcercaDe;
    private Button bSalir;
    private Button bJugar;
    private Button bConfig;
    MediaPlayer mp;

    public static AlmacenPuntuaciones almacen = new AlmacenPuntuacionesArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       //configuramo el objeto Media player
        // mp = MediaPlayer.create(this, R.raw.audio);

        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();

        super.onCreate(savedInstanceState);
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
        bConfig.startAnimation(animAccelerateDecelerate);


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
        //Iniciamos el audio
     //   mp.start();
        //Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

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
       // Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
       //Paramos el player
        // mp.stop();
        super.onStop();
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
