package com.example.myapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import java.util.Calendar;
import android.app.TimePickerDialog;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateEvent extends AppCompatActivity {

    String nameOfEvent;
    String dateOfEvent;
    String timeOfEvent;
    String locationOfEvent;
    boolean typeOfEventTemp;
    String typeOfEvent;
    String priceOfEvent="";

    EditText eText;

    DatePickerDialog picker1;
    EditText eText1;

    TimePickerDialog picker2;
    EditText eText2;

    EditText eText3;

    EditText eText4;

    ImageButton btn;
    ImageButton btn1;

    Button btnGet;

    TextView tvProgressLabel;

    DatabaseReference reff;
    Event event = new Event();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        FirebaseApp.initializeApp(this);


        reff = FirebaseDatabase.getInstance().getReference().child("Event");



        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        getSupportActionBar().setTitle("Create Event");

        //EventName Code
        eText = (EditText) findViewById(R.id.editText0);
        eText.setInputType(InputType.TYPE_CLASS_TEXT);


        //Date Picker Code
        eText1 = (EditText) findViewById(R.id.editText1);
        eText1.setInputType(InputType.TYPE_NULL);
        eText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker1 = new DatePickerDialog(CreateEvent.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText1.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker1.show();

            }
        });

        //Time Picker Code
        eText2 = (EditText) findViewById(R.id.editText2);
        eText2.setInputType(InputType.TYPE_NULL);
        eText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                picker2 = new TimePickerDialog(CreateEvent.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                eText2.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                picker2.show();
            }
        });

        //Address Code
        eText3 = (EditText) findViewById(R.id.editText3);
        eText3.setInputType(InputType.TYPE_CLASS_TEXT);

        //Ticket Code
        eText4 = (EditText) findViewById(R.id.editText4);

        //Personal Code
        btn = findViewById(R.id.imageButton0);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeOfEventTemp = true;
                btn1.setColorFilter(null);
                btn.setColorFilter(Color.argb(255, 254, 191, 8));
            }
        });

        //Education Code
        btn1 = findViewById(R.id.imageButton1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeOfEventTemp = false;
                btn.setColorFilter(null);
                btn1.setColorFilter(Color.argb(255, 254, 191, 8));
            }
        });


        //Submit Button
        btnGet = (Button) findViewById(R.id.button0);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (eText.getText().toString().isEmpty()) {
                    eText.setError("Event name should not be blank!");
                }

                if (eText1.getText().toString().isEmpty()) {
                    eText1.setError("Event date should not be blank!");
                }

                if (eText2.getText().toString().isEmpty()) {
                    eText2.setError("Event time should not be blank!");
                }

                if (eText3.getText().toString().isEmpty()) {
                    eText3.setError("Event location should not be blank!");
                }

                if (!eText.getText().toString().isEmpty() && !eText1.getText().toString().isEmpty()
                    && !eText2.getText().toString().isEmpty()
                        && !eText3.getText().toString().isEmpty()){

                    nameOfEvent = eText.getText().toString();
                    dateOfEvent = eText1.getText().toString();
                    timeOfEvent = eText2.getText().toString();
                    priceOfEvent = eText4.getText().toString();
                    locationOfEvent = eText3.getText().toString();
                    if(typeOfEventTemp) typeOfEvent = "Personal";
                    else typeOfEvent="Education";

                    event.setTitle(nameOfEvent);
                    event.setLocation(locationOfEvent);
                    event.setDate(dateOfEvent);
                    event.setType(typeOfEvent);
                    event.setHost("kirill");
                    event.setTime(timeOfEvent);
                    event.setPrice(priceOfEvent);
                    reff.push().setValue(event);

                    finish();

                }



            }
        });


    }

}
