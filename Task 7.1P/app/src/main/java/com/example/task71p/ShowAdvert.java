package com.example.task71p;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task71p.data.Advert;
import com.example.task71p.data.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ShowAdvert extends AppCompatActivity {

    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    List<Advert> advertList = new ArrayList<>();
    DatabaseHelper db;

    Button cancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_advert);

        cancelButton = findViewById(R.id.showCancel);
        //Cancel button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Set recyclerview
        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(advertList, this);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = new DatabaseHelper(this);
        Log.d("ISSUE", "BEFORE REFRESH");
        refresh();

    }


    @Override
    protected void onResume() {
        super.onResume();
        //Update recycler upon resuming ShowAdvert
        refresh();
    }

    public void refresh()
    {
        Log.d("ISSUE", "BEFORE START LOOP");
        //Clear the list
        advertList.clear();
        Cursor cursor = db.fetchAdvert();
        Log.d("ISSUE", "AFTER FETCH");

        //Iterate SQL and refill list sorted by date
        while(cursor.moveToNext())
        {
            Log.d("ISSUE", "BEFORE NEW ADVERT");
            Advert advert = new Advert(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
            advertList.add(advert);
        }
        cursor.close();
        //Update recycler
        recyclerViewAdapter.notifyDataSetChanged();
    }
}