package com.example.task41p;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.task41p.data.DatabaseHelper;

import java.util.Calendar;

public class CreateTask extends AppCompatActivity {

    EditText TitleInput;
    EditText DetailsInput;
    String dateString;
    Button dateButton;
    Button saveButton;
    Button cancelButton;
    DatePickerDialog datePickerDialog;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_task);

        //Set Values
        TitleInput = findViewById(R.id.createTaskTitle);
        DetailsInput = findViewById(R.id.createTaskDesc);
        dateString = getTodaysDateString();
        saveButton = findViewById(R.id.createSave);
        cancelButton = findViewById(R.id.createCancel);
        db = new DatabaseHelper(this);



        //Initiate Date picker
        initDatePicker();
        dateButton = findViewById(R.id.createDatePicker);
        dateButton.setText(getTodaysDate());

        //Open Date Picker
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = TitleInput.getText().toString();
                String details = DetailsInput.getText().toString();
                //Check if there is a title put in
                if(!title.isEmpty()) {
                    //Check if there is an existing task with that title
                    if (!db.checkTask(title)) {
                        //Insert new task into SQLite
                        long result = db.insertTask(new Task(title, details, dateString));
                        //Check if task was adding successfully
                        if (result > 0) {
                            Toast.makeText(CreateTask.this, "Task saved!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CreateTask.this, "Error occurred, task not saved!", Toast.LENGTH_SHORT).show();
                        }

                        finish();
                    }else{
                        Toast.makeText(CreateTask.this, "Task already exists", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(CreateTask.this, "Title is required", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Close out when pressing cancel
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }
//region Date Picker Methods
    //Return today's date as a string
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month += 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return makeDateString(day,month, year);
    };
    //Return dateString for SQL
    private String getTodaysDateString()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month += 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return setDateString(year, month, day);
    };
    //Initiate Date Picker
    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                //Increase month by 1 because Jan starts as 0
                month = month + 1;
                //Convert to a string format
                String date = makeDateString(day, month, year);
                //Update buttons text
                dateButton.setText(date);
                dateString = setDateString(year, month, day);
            }
        };

        //Get today's date
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener,year, month, day);

    }

    //Create a string for the date
    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    };

    //Convert month to letters
    private String getMonthFormat(int month)
    {
        switch (month)
        {
            case 1:
                return "JAN";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AUG";
            case 9:
                return "OCT";
            case 10:
                return "SEP";
            case 11:
                return "NOV";
            case 12:
                return "DEC";
            default:
                return "JAN";
        }

    };

    //Make date string to save in SQL
    private String setDateString(int year, int month, int day)
    {
        return year + toDoubleDigits(month) + toDoubleDigits(day);
    }

    //Change day or month to two digits for saving in SQL
    private String toDoubleDigits(int num)
    {
        if(num < 10)
        {
            return "0" + num;
        }
        else
        {
            return Integer.toString(num);
        }
    }
    //Open the date picker
    public void openDatePicker()
    {
        datePickerDialog.show();
    }
//endregion
}