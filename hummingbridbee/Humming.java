package com.ecs193.hummingbridbee;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import static com.ecs193.hummingbridbee.R.id.button1;

public class Humming extends AppCompatActivity {

    public Button capture_info, aging, animal_band_data;


    public void init2() {

        capture_info = (Button)findViewById(R.id.button1);
        aging = (Button)findViewById(R.id.button2);
        animal_band_data= (Button)findViewById(R.id.button3);

        aging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy = new Intent(Humming.this, aging.class);
                startActivity(toy);
            }
        });
        animal_band_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy = new Intent(Humming.this, animal_band_data.class);
                startActivity(toy);
            }
        });

        capture_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy = new Intent(Humming.this, capture_info.class);
                startActivity(toy);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humming);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        init2();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_humming, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Humming Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }


    public Action getIndexApiAction0() {
        Thing object = new Thing.Builder()
                .setName("Humming Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

}
