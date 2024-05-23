package com.example.task91p;

import static java.lang.Math.abs;
import static java.lang.Math.ceil;
import static java.lang.Math.floor;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import com.example.task91p.data.Advert;
import com.example.task91p.data.DatabaseHelper;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;

import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AdvertPopup extends AppCompatActivity {

    DatabaseHelper db;
    TextView popName;
    TextView popTime;
    TextView popLoc;
    TextView popPhone;
    TextView popDetails;
    Button deleteButton;
    Button cancelButton;
    String location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_advert_popup);

        popName = findViewById(R.id.popName);
        popTime = findViewById(R.id.popTime);
        popLoc = findViewById(R.id.popLoc);
        popPhone = findViewById(R.id.popPhone);
        popDetails = findViewById(R.id.popDetails);
        cancelButton = findViewById(R.id.popCancel);
        deleteButton = findViewById(R.id.deleteButton);
        db = new DatabaseHelper(this);



        //Bundle extras
        Bundle extras = getIntent().getExtras();
        Integer id = extras.getInt("ID");

        //Fetch Advert
        Advert advert = db.fetchSingle(id);

        //Set Text
        popName.setText(advert.getName());
        popTime.setText(advert.getType() + " " + daysAgo(advert.getDate()));
        popLoc.setText(advert.getType() + " at " + advert.getLocation());
        popPhone.setText("Contact at: " + advert.getPhone());
        popDetails.setText(advert.getDetails());

        //Cancel button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean result = db.deleteAdvert(id);
                //If deleted succesfully
                if(result){
                    finish();
                }
                //Make toast if not deleted
                else
                {
                    Toast.makeText(AdvertPopup.this, "Advert could not be deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public String daysAgo(String date)
    {
        Calendar date1 = Calendar.getInstance();
        date1.set(Integer.parseInt(date.substring(4)), Integer.parseInt(date.substring(2,4)) - 1,
                Integer.parseInt(date.substring(0,2)));

        Calendar date2 = Calendar.getInstance();

        Integer diff = (int) floor((date2.getTimeInMillis() - date1.getTimeInMillis())/(24*60*60*1000));

        return diff + " days ago";
    }
}