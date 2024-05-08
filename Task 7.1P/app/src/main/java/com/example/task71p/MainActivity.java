package com.example.task71p;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.task71p.data.Advert;
import com.example.task71p.data.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    Button newButton;
    Button showButton;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Assign names and variables
        newButton = findViewById(R.id.newButton);
        showButton = findViewById(R.id.showButton);

        db = new DatabaseHelper(this);



        //Set Button Listeners
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open CreateAdvert activity
                Intent intent = new Intent(MainActivity.this, CreateAdvert.class);
                startActivity(intent);
            }
        });

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open ShowAdvert activity
                Intent intent = new Intent(MainActivity.this, ShowAdvert.class);
                startActivity(intent);
            }
        });
    }
}