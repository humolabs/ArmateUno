package com.humolabs.armateuno;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Christus on 5/3/2017.
 */

public class LandingActivity extends Activity {

    TextView btnProxPartidos;
    TextView btnPanelAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.landing_activity);
        super.onCreate(savedInstanceState);

        btnProxPartidos = (TextView) findViewById(R.id.btnProxPartidos);
        btnPanelAdmin = (TextView) findViewById(R.id.btnPanelAdmin);

        btnPanelAdmin.setOnClickListener(showPanelAdmin);
        btnProxPartidos.setOnClickListener(showProximosPartidos);
    }

    View.OnClickListener showPanelAdmin = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(LandingActivity.this, AdminPanelActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener showProximosPartidos = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(LandingActivity.this, CardViewActivity.class);
            LandingActivity.this.startActivity(intent);
        }
    };
}
