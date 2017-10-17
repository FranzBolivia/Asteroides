package com.fva.asteroides;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button bAcercaDe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_con_linearlayout);
        setContentView(R.layout.activity_main);
        bAcercaDe = (Button) findViewById(R.id.button7);
        bAcercaDe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                lanzarAcercaDe(null);
            }
        });
    }
        public void lanzarAcercaDe(View view) {
        Intent i = new Intent(this, acercaDeActivity.class);
        startActivity(i);
    }
}
