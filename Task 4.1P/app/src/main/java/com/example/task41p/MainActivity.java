package com.example.task41p;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.task41p.data.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Declare variables
    Button buttonCreate;
    DatabaseHelper db;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    List<Task> taskList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Assign Variables
        buttonCreate =findViewById(R.id.button);
        db = new DatabaseHelper(this);

        //Set recyclerview
        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(taskList, this);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        //Click on buttonCreate
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open CreateTask Activity
                Intent editTask = new Intent(MainActivity.this, CreateTask.class);
                startActivity(editTask);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Update recycler upon resuming Main Activity
        refresh();
    }

    public void refresh()
    {
        //Clear the list
        taskList.clear();
        Cursor cursor = db.fetchTask();

        //Iterate SQL and refill list sorted by date
        while(cursor.moveToNext())
        {
            Task task = new Task(cursor.getString(1), cursor.getString(2), cursor.getString(3) );
            taskList.add(task);
        }
        cursor.close();
        //Update recycler
        recyclerViewAdapter.notifyDataSetChanged();
    }
}