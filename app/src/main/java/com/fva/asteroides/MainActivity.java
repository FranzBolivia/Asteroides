package com.fva.asteroides;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
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
    private static final int SOLICITUD_PERMISO_WRITE_EXTERNAL_STORAGE = 0;

    public static AlmacenPuntuaciones almacen = new AlmacenPuntuacionesArray();




    String var;
    int pos;

    @Override
    protected void onSaveInstanceState(Bundle estadoGuardado) {
        super.onSaveInstanceState(estadoGuardado);
        if (mp != null) {
            int pos = mp.getCurrentPosition();
            estadoGuardado.putInt("posicion", pos);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle estadoGuardado) {
        super.onRestoreInstanceState(estadoGuardado);
        if (estadoGuardado != null && mp != null) {
            int pos = estadoGuardado.getInt("posicion");
            mp.seekTo(pos);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences pref = PreferenceManager.
                getDefaultSharedPreferences(this);


        if (pref.getString("almacenamiento", "1").equals("1")) {

            Log.i("Preferencias..","Entro Preferencias");
            almacen = new AlmacenPuntuacionesPreferencias(this);
        }

        if (pref.getString("almacenamiento", "1").equals("2")) {
            Log.i("Preferencias..","Fichero");
            almacen = new AlmacenPuntuacionesFicheroInterno(this);
        }

        if (pref.getString("almacenamiento", "1").equals("3")) {
            Log.i("Preferencias..","Entro Almacenamiento");
            solicitarPermiso(Manifest.permission.WRITE_EXTERNAL_STORAGE, "El modo de almacenamiento" +
                            " requiere acceso a la memoria.",
                    SOLICITUD_PERMISO_WRITE_EXTERNAL_STORAGE, this);
            almacen = new AlmacenPuntuacionesFicheroExterno(this);
        }
        if (pref.getString("almacenamiento", "1").equals("4")) {
            Log.i("Preferencias..","Entro RAW");
            almacen = new AlmacenPuntuacionesRecursoRaw(this);
        }

        if (pref.getString("almacenamiento", "1").equals("5")) {
            Log.i("Preferencias..","Entro Asset");
            almacen = new AlmacenPuntuacionesRecursoAssets(this);
        }
        if (pref.getString("almacenamiento", "1").equals("6")) {
            Log.i("Preferencias..","Entro SAX");
            almacen = new AlmacenPuntuacionesXML_SAX(this);
        }
        if (pref.getString("almacenamiento", "1").equals("7")) {
            Log.i("Preferencias..","Entro GSON");
            almacen = new AlmacenPuntuacionesGson(this);
        }

        if (pref.getString("almacenamiento", "1").equals("8")) {
            Log.i("Preferencias..","Entro GSON");
            almacen = new AlmacenPuntuacionesJSon(this);
        }
        if (pref.getString("almacenamiento", "1").equals("9")) {
            Log.i("Preferencias..","Entro SQLite");
            almacen = new AlmacenPuntuacionesSQLite(this);
        }

        if (pref.getString("almacenamiento", "1").equals("10")) {
            Log.i("Preferencias..","Entro Provider");
                    almacen = new AlmacenPuntuacionesProvider(this);
        }

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


    public static void solicitarPermiso(final String permiso, String
            justificacion, final int requestCode, final Activity actividad) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(actividad,
                permiso)) {
            new AlertDialog.Builder(actividad)
                    .setTitle("Solicitud de permiso")
                    .setMessage(justificacion)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            ActivityCompat.requestPermissions(actividad,
                                    new String[]{permiso}, requestCode);
                        }
                    })
                    .show();
        } else {
            ActivityCompat.requestPermissions(actividad,
                    new String[]{permiso}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode == SOLICITUD_PERMISO_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permiso Otorgado", Toast.LENGTH_SHORT).show();

              //  onMapReady(mapa);


            } else {
                Toast.makeText(this, "Sin el permiso, no se habilita Escritura ", Toast.LENGTH_SHORT).show();
            }
        }
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

    static final int ACTIV_JUEGO = 0;

    public void lanzarJuego(View view) {
        Intent i = new Intent(this, Juego.class);
        startActivityForResult(i, ACTIV_JUEGO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == ACTIV_JUEGO && resultCode == RESULT_OK && data != null) {
            int puntuacion = data.getExtras().getInt("puntuacion");
            String nombre = "Yo";
// Mejor leer nombre desde un AlertDialog.Builder o preferencias
            almacen.guardarPuntuacion(puntuacion, nombre,
                    System.currentTimeMillis());
            lanzarPuntuaciones(null);
        }
        if (requestCode == 1234) {


            SharedPreferences pref = PreferenceManager.
                    getDefaultSharedPreferences(this);

            if (pref.getString("almacenamiento", "1").equals("0")) {

                Log.i("Preferencias..","Entro Array");
                almacen = new AlmacenPuntuacionesArray();
            }

            if (pref.getString("almacenamiento", "1").equals("1")) {

                Log.i("Preferencias..","Entro Preferencias");
                almacen = new AlmacenPuntuacionesPreferencias(this);
            }

            if (pref.getString("almacenamiento", "1").equals("2")) {
                Log.i("Preferencias..","Entro Fichero");
                almacen = new AlmacenPuntuacionesFicheroInterno(this);
            }
            if (pref.getString("almacenamiento", "1").equals("3")) {
                Log.i("Preferencias..","Entro Fichero Externo");
                almacen = new AlmacenPuntuacionesFicheroExterno(this);
            }
            if (pref.getString("almacenamiento", "1").equals("4")) {
                Log.i("Preferencias..","Entro RAW");
                almacen = new AlmacenPuntuacionesRecursoRaw(this);
            }
            if (pref.getString("almacenamiento", "1").equals("5")) {
                Log.i("Preferencias..","Entro ASSET");
                almacen = new AlmacenPuntuacionesRecursoAssets(this);
            }
            if (pref.getString("almacenamiento", "1").equals("6")) {
                Log.i("Preferencias..","Entro SAX");
                almacen = new AlmacenPuntuacionesXML_SAX(this);
            }
            if (pref.getString("almacenamiento", "1").equals("7")) {
                Log.i("Preferencias..","Entro GSON");
                almacen = new AlmacenPuntuacionesGson(this);
            }
            if (pref.getString("almacenamiento", "1").equals("8")) {
                Log.i("Preferencias..","Entro GSON");
                almacen = new AlmacenPuntuacionesJSon(this);
            }
            if (pref.getString("almacenamiento", "1").equals("9")) {
                Log.i("Preferencias..","Entro SQLite");
                almacen = new AlmacenPuntuacionesSQLiteRel(this);
            }
            if (pref.getString("almacenamiento", "1").equals("10")) {
                Log.i("Preferencias..","Entro Provider");
                almacen = new AlmacenPuntuacionesProvider(this);
            }

        }
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
        startActivityForResult(i, 1234);


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
