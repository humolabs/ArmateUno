package com.humolabs.armateuno.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.humolabs.armateuno.R;
import com.humolabs.armateuno.domain.User;


/**
 * Created by Christus on 5/3/2017.
 */

public class LandingActivity extends AppCompatActivity {

    TextView btnProxPartidos;
    TextView btnPanelAdmin;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_activity);

        user.setUserName("usuario1");
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        btnProxPartidos = (TextView) findViewById(R.id.btnProxPartidos);
        btnPanelAdmin = (TextView) findViewById(R.id.btnPanelAdmin);

        btnPanelAdmin.setOnClickListener(showPanelAdmin);
        btnProxPartidos.setOnClickListener(showProximosPartidos);
    }

    View.OnClickListener showPanelAdmin = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(LandingActivity.this, AdminPanelActivity.class);
            intent.putExtra("USERNAME", user.getUserName());
            startActivity(intent);
        }
    };

    View.OnClickListener showProximosPartidos = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(LandingActivity.this, CardViewActivity.class);
            intent.putExtra("USERNAME", user.getUserName());
            LandingActivity.this.startActivity(intent);
        }
    };
}
