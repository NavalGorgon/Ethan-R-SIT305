package com.example.task91p;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.task91p.data.Advert;
import com.example.task91p.data.DatabaseHelper;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    Button newButton;
    Button showButton;
    Button mapButton;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Assign names and variables
        newButton = findViewById(R.id.newButton);
        showButton = findViewById(R.id.showButton);
        mapButton = findViewById(R.id.mapButton);

        db = new DatabaseHelper(this);



        //Set Button Listeners
        //Open Create Advert activity
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open CreateAdvert activity
                Intent intent = new Intent(MainActivity.this, CreateAdvert.class);
                startActivity(intent);
            }
        });


        //Open Show Advert activity
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open ShowAdvert activity
                Intent intent = new Intent(MainActivity.this, ShowAdvert.class);
                startActivity(intent);
            }
        });


        //Open Maps Activity
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });


    }
}