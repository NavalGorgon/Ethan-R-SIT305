package com.example.task71p;

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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.task71p.data.Advert;
import com.example.task71p.data.DatabaseHelper;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class AdvertPopup extends AppCompatActivity {

    DatabaseHelper db;
    TextView popName;
    TextView popTime;
    TextView popLoc;
    TextView popPhone;
    TextView popDetails;
    Button deleteButton;
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
        deleteButton = findViewById(R.id.deleteButton);
        db = new DatabaseHelper(this);

        //Bundle extras
        Log.d("ISSUE", "BEFORE OPENING BUNDLE");
        Bundle extras = getIntent().getExtras();
        Integer id = extras.getInt("ID");
        Log.d("ISSUE", "BEFORE BUNDLE FETCH");
        Advert advert = db.fetchSingle(id);
        popName.setText(advert.getName());
        popTime.setText(advert.getType() + " " + daysAgo(advert.getDate()));
        popLoc.setText(advert.getType() + " at " + advert.getLocation());
        popPhone.setText("Contact at: " + advert.getPhone());
        popDetails.setText(advert.getDetails());

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean result = db.deleteAdvert(id);
                if(result){
                    finish();
                }
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