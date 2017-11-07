package com.fva.asteroides;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.RectShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DTIC-Dir on 26/10/2017.
 */

public class VistaJuego extends View implements SensorEventListener{



    // Thread encargado de procesar el juego
    private ThreadJuego thread = new ThreadJuego();
    // Cada cuanto queremos procesar cambios (ms)
    private static int PERIODO_PROCESO = 50;
    // Cuando se realizó el último proceso
    private long ultimoProceso = 0;

    private Drawable miImagen;
    // //// NAVE //////
    private Grafico nave; // Gráfico de la nave2
    private int giroNave; // Incremento de dirección
    private double aceleracionNave; // aumento de velocidad
    private static final int MAX_VELOCIDAD_NAVE = 20;
    // Incremento estándar de giro y aceleración
    private static final int PASO_GIRO_NAVE = 5;
    private static final float PASO_ACELERACION_NAVE = 0.5f;

    // //// ASTEROIDES //////
    private List<Grafico> asteroides; // Lista con los Asteroides
    private int numAsteroides = 5; // Número inicial de asteroides
    private int numFragmentos = 3; // Fragmentos en que se divide

    // //// MISIL //////
    private Grafico misil;
    private static int PASO_VELOCIDAD_MISIL = 12;
    private boolean misilActivo = false;
    private int tiempoMisil;

    public VistaJuego(Context context, AttributeSet attrs) {
        super(context, attrs);

        Drawable drawableNave, drawableAsteroide, drawableMisil;

        SharedPreferences pref = PreferenceManager.
                getDefaultSharedPreferences(getContext());


// Se habilita el sensor para detectar el giro....



        if (pref.getBoolean ("controles", true)) {
            SensorManager mSensorManager = (SensorManager)
                    context.getSystemService(Context.SENSOR_SERVICE);
            List<Sensor> listSensors = mSensorManager.getSensorList(
                    Sensor.TYPE_ACCELEROMETER);
            if (!listSensors.isEmpty()) {
                Sensor orientationSensor = listSensors.get(0);
                mSensorManager.registerListener(this, orientationSensor,
                        SensorManager.SENSOR_DELAY_GAME);
            }

        }
//Dibujando los Asteorides

        drawableAsteroide = context.getResources().getDrawable(R.drawable.asteroide1);
        ContextCompat.getDrawable(context, R.drawable.asteroide1);
        //DIbujando el Asteriode REtro

//        SharedPreferences pref = PreferenceManager.
  //              getDefaultSharedPreferences(getContext());

//Dibujando la nave2
        drawableNave = context.getResources().getDrawable(R.drawable.nave);
        ContextCompat.getDrawable(context, R.drawable.nave);

        drawableMisil = context.getResources().getDrawable(R.drawable.misil1);
        ContextCompat.getDrawable(context, R.drawable.misil1);


        if (pref.getString("graficos", "1").equals("0")) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            Path pathAsteroide = new Path();
            pathAsteroide.moveTo((float) 0.3, (float) 0.0);
            pathAsteroide.lineTo((float) 0.6, (float) 0.0);
            pathAsteroide.lineTo((float) 0.6, (float) 0.3);
            pathAsteroide.lineTo((float) 0.8, (float) 0.2);
            pathAsteroide.lineTo((float) 1.0, (float) 0.4);
            pathAsteroide.lineTo((float) 0.8, (float) 0.6);
            pathAsteroide.lineTo((float) 0.9, (float) 0.9);
            pathAsteroide.lineTo((float) 0.8, (float) 1.0);
            pathAsteroide.lineTo((float) 0.4, (float) 1.0);
            pathAsteroide.lineTo((float) 0.0, (float) 0.6);
            pathAsteroide.lineTo((float) 0.0, (float) 0.2);
            pathAsteroide.lineTo((float) 0.3, (float) 0.0);
            ShapeDrawable dAsteroide = new ShapeDrawable(
                    new PathShape(pathAsteroide, 1, 1));
            dAsteroide.getPaint().setColor(Color.WHITE);
            dAsteroide.getPaint().setStyle(Paint.Style.STROKE);
            dAsteroide.setIntrinsicWidth(50);
            dAsteroide.setIntrinsicHeight(50);
            drawableAsteroide = dAsteroide;
            setBackgroundColor(Color.BLACK);


            Path pathNave = new Path();
            pathNave.moveTo((float) 0.0, (float) 0.0);
            pathNave.lineTo((float) 1, (float) 0.0);
            pathNave.lineTo((float) 0.5, (float) -1);
            pathNave.lineTo((float) 0, (float) 0);


            ShapeDrawable dNave = new ShapeDrawable(
                    new PathShape(pathNave, 1, 1));
            dNave.getPaint().setColor(Color.WHITE);
            dNave.getPaint().setStyle(Paint.Style.STROKE);
            dNave.setIntrinsicWidth(50);
            dNave.setIntrinsicHeight(50);
            drawableNave = dNave;
            setBackgroundColor(Color.BLACK);

            ShapeDrawable dMisil = new ShapeDrawable(new RectShape());
            dMisil.getPaint().setColor(Color.WHITE);
            dMisil.getPaint().setStyle(Paint.Style.STROKE);
            dMisil.setIntrinsicWidth(15);
            dMisil.setIntrinsicHeight(3);
            drawableMisil = dMisil;


        }
        ;
        if (pref.getString("graficos", "1").equals("1")) {
            setLayerType(View.LAYER_TYPE_HARDWARE, null);
            drawableAsteroide =
                    context.getResources().getDrawable(R.drawable.asteroide1);
            ContextCompat.getDrawable(context, R.drawable.asteroide1);


        }
        if (pref.getString("graficos", "1").equals("2")||pref.getString("graficos", "1").equals("3") )

        {
            drawableAsteroide =
                    context.getResources().getDrawable(R.drawable.estrella);
            ContextCompat.getDrawable(context, R.drawable.estrella);

            drawableNave = context.getResources().getDrawable(R.drawable.nave2);
            ContextCompat.getDrawable(context,R.drawable.nave2);
            setBackgroundColor(Color.BLACK);
        }

        // Dibujando la Nave el misil y los asteorides

        nave = new Grafico(this, drawableNave);
        misil =  new Grafico(this,drawableMisil);
        asteroides = new ArrayList<Grafico>();


        for (int i = 0; i < numAsteroides; i++) {
            Grafico asteroide = new Grafico(this, drawableAsteroide);
            asteroide.setIncY(Math.random() * 4 - 2);
            asteroide.setIncX(Math.random() * 4 - 2);
            asteroide.setAngulo((int) (Math.random() * 360));
            asteroide.setRotacion((int) (Math.random() * 8 - 4));
            asteroides.add(asteroide);
        }
    }




    //Desde aqui se llama al hilo que se crea..
    @Override
    protected void onSizeChanged(int ancho, int alto,
                                 int ancho_anter, int alto_anter) {
        super.onSizeChanged(ancho, alto, ancho_anter, alto_anter);
// Una vez que conocemos nuestro ancho y alto.
        nave.setCenX((int) ancho / 2);
        nave.setCenY((int) alto / 2);

        for (Grafico asteroide : asteroides) {
            do {
                asteroide.setCenX((int) (Math.random() * ancho));
                asteroide.setCenY((int) (Math.random() * alto));
            } while (asteroide.distancia(nave) < (ancho + alto) / 5);
        }
        ultimoProceso =System.currentTimeMillis();
        thread.start();

    }

    @Override
    protected  void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        nave.dibujaGrafico(canvas);
        if (misilActivo) misil.dibujaGrafico(canvas);

        //miImagen.setBounds(30,30,200,200 );

        //miImagen.draw(canvas);

synchronized (asteroides){
        for (Grafico asteroide : asteroides) {
            asteroide.dibujaGrafico(canvas);
        }
    }}
//Actualiza fisica pone en movimiento los asteroides y la nave..
    protected  void  actualizaFisica() {
        long ahora = System.currentTimeMillis();
        if (ultimoProceso + PERIODO_PROCESO > ahora) {
            return; // Salir si el período de proceso no se ha cumplido.
        }
// Para una ejecución en tiempo real calculamos el factor de movimiento
        double factorMov = (ahora - ultimoProceso) / PERIODO_PROCESO;
        ultimoProceso = ahora; // Para la próxima vez
// Actualizamos velocidad y dirección de la nave a partir de
// giroNave y aceleracionNave (según la entrada del jugador)
        nave.setAngulo((int) (nave.getAngulo() + giroNave * factorMov));
        double nIncX = nave.getIncX() + aceleracionNave *
                Math.cos(Math.toRadians(nave.getAngulo())) * factorMov;
        double nIncY = nave.getIncY() + aceleracionNave *
                Math.sin(Math.toRadians(nave.getAngulo())) * factorMov;
// Actualizamos si el módulo de la velocidad no excede el máximo
        if (Math.hypot(nIncX,nIncY) <= MAX_VELOCIDAD_NAVE){
            nave.setIncX(nIncX);
            nave.setIncY(nIncY);
        }
        nave.incrementaPos(factorMov); // Actualizamos posición

        for (Grafico asteroide : asteroides) {
            asteroide.incrementaPos(factorMov);
        }
        // Actualizamos posición de misil
        if (misilActivo) {
            misil.incrementaPos(factorMov);
            tiempoMisil-=factorMov;
            if (tiempoMisil < 0) {
                misilActivo = false;
            } else {
                for (int i = 0; i < asteroides.size(); i++)
                    if (misil.verificaColision(asteroides.get(i))) {
                        destruyeAsteroide(i);
                        break;
                    }
            }
        }
    }

    private void destruyeAsteroide(int i) {
        synchronized (asteroides){
        asteroides.remove(i);
        misilActivo = false;}
        this.postInvalidate();
    }
    private void activaMisil() {
        misil.setCenX(nave.getCenX());
        misil.setCenY(nave.getCenY());
        misil.setAngulo(nave.getAngulo());
        misil.setIncX(Math.cos(Math.toRadians(misil.getAngulo())) *
                PASO_VELOCIDAD_MISIL);
        misil.setIncY(Math.sin(Math.toRadians(misil.getAngulo())) *
                PASO_VELOCIDAD_MISIL);
        tiempoMisil = (int) Math.min(this.getWidth() / Math.abs( misil.
                getIncX()), this.getHeight() / Math.abs(misil.getIncY())) - 2;
        misilActivo = true;
    }

    @Override public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    private boolean hayValorInicial = false;
    private float valorInicial;
    private float temporal;

    @Override public void onSensorChanged(SensorEvent event) {

        float valor = event.values[1];
        float acele = event.values[0];
        if (!hayValorInicial){
            //aceleracionNave= acele;
            valorInicial = valor;
            hayValorInicial = true;
        }
        giroNave=(int) (valor-valorInicial)/1 ;

        if (acele<temporal)
        aceleracionNave = +PASO_ACELERACION_NAVE;
        //    aceleracionNave= temporal-acele;
        else aceleracionNave = -PASO_ACELERACION_NAVE;
       // else aceleracionNave= temporal+acele;

        temporal=acele;
        //Log.d("variable X", Float.toString((float) acele)+"...."+Float.toString((float)aceleracionNave ));


    }

// genera un ciclo infinito para llamar al actualiza mfisica..

    class ThreadJuego extends Thread {
        @Override
        public void run() {
            while (true) {
                actualizaFisica();
            }
        }
    }

    // Se crea el manejador para el teclado ...
    // Cuando se presiona
    @Override
    public boolean onKeyDown(int codigoTecla, KeyEvent evento) {
        super.onKeyDown(codigoTecla, evento);
// Suponemos que vamos a procesar la pulsación
        boolean procesada = true;
        switch (codigoTecla) {
            case KeyEvent.KEYCODE_DPAD_UP:
                aceleracionNave = +PASO_ACELERACION_NAVE;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                giroNave = -PASO_GIRO_NAVE;
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                giroNave = +PASO_GIRO_NAVE;
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                aceleracionNave= -PASO_ACELERACION_NAVE;
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:


            case KeyEvent.KEYCODE_ENTER:
                activaMisil();
                break;
            default:
// Si estamos aquí, no hay pulsación que nos interese
                procesada = false;
                break;
        }
        return procesada;
    }
    // Cuando se suelta
    @Override public boolean onKeyUp(int codigoTecla, KeyEvent evento) {
        super.onKeyUp(codigoTecla, evento);
// Suponemos que vamos a procesar la pulsación
        boolean procesada = true;
        switch (codigoTecla) {
            case KeyEvent.KEYCODE_DPAD_UP:
                aceleracionNave = 0;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                giroNave = 0;
                break;
            default:
// Si estamos aquí, no hay pulsación que nos interese
                procesada = false;
                break;
        }
        return procesada;
    }

//Manipulando la nave....
    private float mX=0, mY=0;
    private boolean disparo=false;
    @Override
    public boolean onTouchEvent (MotionEvent event) {
        super.onTouchEvent(event);
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                disparo=true;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - mX);
                float dy = Math.abs(y - mY);
                if (dy<6 && dx>6){
                    giroNave = Math.round((x - mX) / 2);
                    disparo = false;
                } else if (dx<6 && dy>6){
                    aceleracionNave = Math.round((mY - y) / 25);
                    disparo = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                giroNave = 0;
                aceleracionNave = 0;
                if (disparo){
                    activaMisil();
                }
                break;
        }
        mX=x; mY=y;
        return true;
    }

}
