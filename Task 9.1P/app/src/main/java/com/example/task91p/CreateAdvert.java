package com.example.task91p;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.example.task91p.data.Advert;
import com.example.task91p.data.DatabaseHelper;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;

import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class CreateAdvert extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;
    EditText createName;
    EditText createPhone;
    EditText createDetails;
    EditText createDate;
    TextView createLocation;
    RadioButton rb;
    RadioGroup radioGroup;
    Button saveButton;
    Button createCancel;
    Button locationButton;
    DatabaseHelper db;
    LatLng placeLatLng;
    Location currentLocation;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_advert);

        //region Objects in layout
        createName = findViewById(R.id.createName);
        createPhone = findViewById(R.id.createPhone);
        createDetails = findViewById(R.id.createDetails);
        createDate = findViewById(R.id.createDate);
        createLocation = findViewById(R.id.createLocation);
        createCancel = findViewById(R.id.createCancel);
        saveButton = findViewById(R.id.saveButton);
        locationButton = findViewById(R.id.locationButton);
        radioGroup = findViewById(R.id.radioGroup);
        rb = findViewById(R.id.radioLost);
        //endregion

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
                //Read input variables
                String type = rb.getText().toString();
                String name = createName.getText().toString();
                String phone = createPhone.getText().toString();
                String details = createDetails.getText().toString();
                String date = dateToString(createDate.getText().toString());
                //Set latitude and longitude for the advert
                Double Lat = placeLatLng.latitude;
                Double Lng = placeLatLng.longitude;

                Log.i("TROUBLE", "Lat: " + Lat + " Lng: " + Lng);
                if (!dateIsValid(date)) {
                    //Check if date value is valid
                    Toast.makeText(CreateAdvert.this, "Date not valid, task not saved!", Toast.LENGTH_SHORT).show();
                } else {
                    if (!phoneIsValid(phone)) {
                        //Check if phone is valid
                        Toast.makeText(CreateAdvert.this, "Phone not valid, task not saved!" + phone, Toast.LENGTH_SHORT).show();
                    } else {
                        if (type.isEmpty() || name.isEmpty() || details.isEmpty() || address.isEmpty() || date.isEmpty() || phone.isEmpty()) {
                            //Check if any value is empty
                            Log.i("TROUBLE", "INVALID THINGS");

                            Toast.makeText(CreateAdvert.this, "Not all components are filled, task not saved!", Toast.LENGTH_SHORT).show();
                        } else {
                            Advert advert = new Advert(0, type, name, phone, details, date, address, Lat, Lng);
                            long result = db.insertAdvert(advert);
                            if (result > 0) {
                                Toast.makeText(CreateAdvert.this, "Saved", Toast.LENGTH_SHORT).show();
                                Log.d("ADD THING", "DID WORK");
                                finish();
                            } else {
                                Log.d("ADD THING", "DIDN'T WORK");
                                Toast.makeText(CreateAdvert.this, "Not saved", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });

        //region AUTOCOMPLETE
        //OPEN AUTOCOMPLETE ACTIVITY
        Places.initialize(getApplicationContext(), "AIzaSyB1M322onwO3_i89ZIzmmmMwUX4SzgLSb4");
        PlacesClient placesClient = Places.createClient(this);
        //endregion

        //Set on click listener
        createLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set the fields to specify which types of place data to
                // return after the user has made a selection.
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
                //Limit to addresses within Australia
                List<String> countries = Arrays.asList("AU");

                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                        .setCountries(countries)
                        .build(CreateAdvert.this);
                startAutocomplete.launch(intent);
                //Log for debugging
                Log.i("LOCO", "AFTER WIDGET STARTS");

            }
        });


        //region LOCATION SERVICES
        // Initialise location manager and listener
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                //Update location
                currentLocation = location;
            }
        };

        //Request permissions
        if (ActivityCompat.checkSelfPermission(CreateAdvert.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CreateAdvert.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CreateAdvert.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
        // Set onClickListener for GET CURRENT LOCATION
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // GET LOCATION
                if(currentLocation != null) {
                    //Set location name to custom
                    address = "Custom Location";
                    //Show text
                    createLocation.setText(address);
                    //Set LatLng variable to save in advert
                    placeLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                    //Log
                    Log.i("LOCO", "Lat: " + currentLocation);
                }else{
                    //Make toast if not found correctly
                    Toast.makeText(CreateAdvert.this, "Location not fetched! Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //endregion

    }


    //Request permission results
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.checkSelfPermission(CreateAdvert.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
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

    private final ActivityResultLauncher<Intent> startAutocomplete = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Log.i("LOCO", "IN THE THING");
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = result.getData();
                    if (intent != null) {
                        Place place = Autocomplete.getPlaceFromIntent(intent);
                        Log.i("LOCO", "Place: " + place.getName() + ", " + place.getId()
                        + ", " + place.getAddress());
                        createLocation.setText(place.getAddress());
                        address = place.getAddress();
                        placeLatLng = place.getLatLng();
                    }
                } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    // The user canceled the operation.
                    Log.i("LOCO", "User canceled autocomplete");
                } else {
                    Log.i("LOCO", String.valueOf(result.getResultCode()));
                }
            });
}