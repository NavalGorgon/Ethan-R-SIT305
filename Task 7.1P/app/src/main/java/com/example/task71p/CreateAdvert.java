package com.example.task71p;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.task71p.data.Advert;
import com.example.task71p.data.DatabaseHelper;

public class CreateAdvert extends AppCompatActivity {

    EditText createName;
    EditText createPhone;
    EditText createDetails;
    EditText createDate;
    EditText createLocation;
    RadioButton rb;
    RadioGroup radioGroup;
    Button saveButton;
    Button createCancel;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_advert);

        //Assign names and variables
        createName = findViewById(R.id.createName);
        createPhone = findViewById(R.id.createPhone);
        createDetails = findViewById(R.id.createDetails);
        createDate = findViewById(R.id.createDate);
        createLocation = findViewById(R.id.createLocation);
        createCancel = findViewById(R.id.createCancel);
        saveButton = findViewById(R.id.saveButton);

        radioGroup = findViewById(R.id.radioGroup);
        rb = findViewById(R.id.radioLost);

        db = new DatabaseHelper(this);

        //Set Change Listener for radioGroup
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rb = findViewById(checkedId);
            }
        });

        //Cancel button
        createCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Set on click listener for saveButton
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CREATE ADVERT IN SQL
                String type = rb.getText().toString();
                String name = createName.getText().toString();
                String phone = createPhone.getText().toString();
                String details = createDetails.getText().toString();
                String date = dateToString(createDate.getText().toString());
                String location = createLocation.getText().toString();

                if(!dateIsValid(date)){
                    //Check if date value is valid
                    Toast.makeText(CreateAdvert.this, "Date not valid, task not saved!", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(!phoneIsValid(phone)){
                        //Check if phone is valid
                        Toast.makeText(CreateAdvert.this, "Phone not valid, task not saved!" + phone, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(type.isEmpty() || name.isEmpty() || details.isEmpty() || location.isEmpty() || date.isEmpty() || phone.isEmpty()){
                            //Check if any value is empty
                            Toast.makeText(CreateAdvert.this, "Not all components are filled, task not saved!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Advert advert = new Advert(0, type, name, phone, details, date, location);
                            long result = db.insertAdvert(advert);
                            if(result > 0){
                                Toast.makeText(CreateAdvert.this, "Saved", Toast.LENGTH_SHORT).show();
                                Log.d("ADD THING", "DID WORK");
                                finish();
                            }
                            else{
                                Log.d("ADD THING", "DIDNT WORK");
                                Toast.makeText(CreateAdvert.this, "Not saved", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });
    }

    //Check is phone is valid input
    public boolean phoneIsValid(String phone){
        return (phone.length() == 10) && isNumeric(phone);
    }
    public boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    //Check date is valid
    public String dateToString(String date){
        return date.substring(0,2) + date.substring(3,5) + date.substring(6);
    }
    public boolean dateIsValid(String date){
        return ((date.length() == 8) && isNumeric(date));
    }
}