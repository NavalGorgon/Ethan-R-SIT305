package com.example.myapplication;

import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    TextView Result;
    float Input_Value;
    Button Convert;
    Spinner input_unit;
    Spinner output_unit;
    int choice1;
    int choice2;
    EditText Words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Declare variables
        Convert = findViewById(R.id.button);
        Result = findViewById(R.id.result);
        Words = findViewById(R.id.value);

        //region Spinners
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.length_units,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //declare values
        input_unit= findViewById(R.id.input_Spinner);
        output_unit= findViewById(R.id.output_Spinner);
        input_unit.setAdapter(adapter);
        output_unit.setAdapter(adapter);

        input_unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                choice1 = parent.getSelectedItemPosition();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        output_unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                choice2 = parent.getSelectedItemPosition();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //endregion

        //Click on CONVERT button
        Convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Input_Value = Float.parseFloat(Words.getText().toString());
                switch (choice1) {
                    //------INCHES FEET YARDS MILES------//
                    //INCHES
                    case 0:
                        switch (choice2) {
                            case 0:
                                Result.setText(Float.toString(Input_Value));
                                break;
                            case 1:
                                Result.setText(Float.toString((Input_Value*2.54f)/30.48f));
                                break;
                            case 2:
                                Result.setText(Float.toString((Input_Value*2.54f)/91.44f));
                                break;
                            case 3:
                                Result.setText(Float.toString((Input_Value*2.54f)/1609.34f));
                                break;
                            default:
                                Result.setText(R.string.fail);
                                break;
                        }
                        break;
                    //FEET
                    case 1:
                        switch (choice2) {
                            case 0:
                                Result.setText(Float.toString((Input_Value*30.48f)/2.54f));
                                break;
                            case 1:
                                Result.setText(Float.toString(Input_Value));
                                break;
                            case 2:
                                Result.setText(Float.toString((Input_Value*30.48f)/91.44f));
                                break;
                            case 3:
                                Result.setText(Float.toString((Input_Value*30.48f)/1609.34f));
                                break;
                            default:
                                Result.setText(R.string.fail);
                                break;
                        }
                        break;
                    //YARD
                    case 2:
                        switch (choice2) {
                            case 0:
                                Result.setText(Float.toString((Input_Value*91.44f)/2.54f));
                                break;
                            case 1:
                                Result.setText(Float.toString((Input_Value*91.44f)/30.48f));
                                break;
                            case 2:
                                Result.setText(Float.toString(Input_Value));
                                break;
                            case 3:
                                Result.setText(Float.toString((Input_Value*91.44f)/1609.34f));
                                break;
                            default:
                                Result.setText(R.string.fail);
                                break;
                        }
                        break;
                    //MILE
                    case 3:
                        switch (choice2) {
                            case 0:
                                Result.setText(Float.toString((Input_Value*1609.34f)/2.54f));
                                break;
                            case 1:
                                Result.setText(Float.toString((Input_Value*1609.34f)/30.48f));
                                break;
                            case 2:
                                Result.setText(Float.toString((Input_Value*1609.34f)/91.44f));
                                break;
                            case 3:
                                Result.setText(Float.toString(Input_Value));
                                break;
                            default:
                                Result.setText(R.string.fail);
                                break;
                        }
                        break;
                    //------ POUNDS OUNCES TONS ------//
                    //POUNDS
                    case 4:
                        switch (choice2) {
                            case 4:
                                Result.setText(Float.toString(Input_Value));
                                break;
                            case 5:
                                Result.setText(Float.toString((Input_Value*0.453592f)/0.0283495f));
                                break;
                            case 6:
                                Result.setText(Float.toString((Input_Value*0.453592f)/907.185f));
                                break;
                            default:
                                Result.setText(R.string.fail);
                                break;
                        }
                        break;
                    //OUNCE
                    case 5:
                        switch (choice2) {
                            case 4:
                                Result.setText(Float.toString((Input_Value*0.0283495f)/0.453592f));
                                break;
                            case 5:
                                Result.setText(Float.toString(Input_Value));
                                break;
                            case 6:
                                Result.setText(Float.toString((Input_Value*0.0283495f)/907.185f));
                                break;
                            default:
                                Result.setText(R.string.fail);
                                break;
                        }
                        break;
                    //TON
                    case 6:
                        switch (choice2) {
                            case 4:
                                Result.setText(Float.toString((Input_Value*907.185f)/0.453592f));
                                break;
                            case 5:
                                Result.setText(Float.toString((Input_Value*907.185f)/0.0283495f));
                                break;
                            case 6:
                                Result.setText(Float.toString(Input_Value));
                                break;
                            default:
                                Result.setText(R.string.fail);
                                break;
                        }
                        break;
                    // ------ CELSIUS FAHRENHEIT KELVIN ------ //
                    //CELSIUS
                    case 7:
                        switch (choice2) {
                            case 7:
                                Result.setText(Float.toString(Input_Value));
                                break;
                            case 8:
                                Result.setText(Float.toString((Input_Value*1.8f)+32));
                                break;
                            case 9:
                                Result.setText(Float.toString(Input_Value+273.15f));
                                break;
                            default:
                                Result.setText(R.string.fail);
                                break;
                        }
                        break;
                    //FAHRENHEIT
                    case 8:
                        switch (choice2) {
                            case 7:
                                Result.setText(Float.toString((Input_Value-32)/1.8f));
                                break;
                            case 8:
                                Result.setText(Float.toString(Input_Value));
                                break;
                            case 9:
                                Result.setText(Float.toString(((Input_Value-32)/1.8f) + 273.15f));
                                break;
                            default:
                                Result.setText(R.string.fail);
                                break;
                        }
                        break;
                    //KELVIN
                    case 9:
                        switch (choice2) {
                            case 7:
                                Result.setText(Float.toString(Input_Value - 273.15f));
                                break;
                            case 8:
                                Result.setText(Float.toString(((Input_Value-273.15f)*1.8f) + 32));
                                break;
                            case 9:
                                Result.setText(Float.toString(Input_Value));
                                break;
                            default:
                                Result.setText(R.string.fail);
                                break;
                        }
                        break;
                    default:
                        break;
                }

            }
        });
    }
}