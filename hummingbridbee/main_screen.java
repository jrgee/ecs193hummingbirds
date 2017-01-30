package com.ecs193.hummingbridbee;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;

public class main_screen extends AppCompatActivity {

    public Button list_view;

    public void init() {
        list_view = (Button) findViewById(R.id.button2);
        list_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy = new Intent(main_screen.this, Humming.class);
                startActivity(toy);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        init();
    }
}


