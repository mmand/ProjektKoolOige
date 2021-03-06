package com.example.markusmnd.alarmclockv2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    AlarmManager alarm_manager;
    TimePicker alarm_timepicker;
    TextView update_text;
    Context context;
    PendingIntent pending_intent;
    int choose_alarm_sound;



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.context = this;


        // initializing alarm manager
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //initializing timepicker
        alarm_timepicker = (TimePicker) findViewById(R.id.timePicker);

        //changing timepicker into 24H format
        alarm_timepicker.setIs24HourView(true);

        //initializing text update box
        update_text = (TextView) findViewById(R.id.update_text);

        //creating an instance of calendar
        final Calendar calendar = Calendar.getInstance();

        // creating an intent to the Alarm Receiver class
        final Intent my_intent = new Intent(this.context, Alarm_Receiver.class);

        // creating the spinner in the main UI
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        //creating an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.alarm_array, android.R.layout.simple_spinner_item);
        // Specifying the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to the spinner
        spinner.setAdapter(adapter);

        //Setting an onclick listener to the onItemSelected method
        spinner.setOnItemSelectedListener(this);

        //initializing buttons

        final Button alarm_on = (Button) findViewById(R.id.alarm_on);

        //creating an onClick listener to start the alarm
        alarm_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // setting calendar instance with the hour and minute that we picked
                // on the time picker
                calendar.set(Calendar.HOUR_OF_DAY, alarm_timepicker.getHour());
                calendar.set(Calendar.MINUTE, alarm_timepicker.getMinute());

                // getting the int values of the hour and minute
                int hour = alarm_timepicker.getHour();
                int minute = alarm_timepicker.getMinute();

                //converting the int values to strings
                String hour_string = String.valueOf(hour);
                String minute_string = String.valueOf(minute);

                // 10:4 -> 10:04
                if (minute < 10) {
                    minute_string = "0" + String.valueOf(minute);
                }

                // method that changes the update text Textbox
                set_alarm_text("Alarm set to: " + hour_string + ":" + minute_string);


                // put in extra string into my_intent
                // tells the clock that you pressed the "alarm on" button
                my_intent.putExtra("extra", "alarm on");

                //put in an extra long value into my intent
                //tells the clock that you want a certain value from the
                // dropdown menu/spinners
                my_intent.putExtra("alarm_choice", choose_alarm_sound);

                // create a pending intent that delays the intent
                // until the specified calendar time
                pending_intent = PendingIntent.getBroadcast(MainActivity.this, 0, my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

                //set the alarm manager
                alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending_intent);

            }


        });



        // initializing the stop button
        Button alarm_off = (Button) findViewById(R.id.alarm_off);

        // creating an onClick listener to stop the alarm or undo an alarm set
        alarm_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PendingIntent pending_intent;

                pending_intent = PendingIntent.getBroadcast(MainActivity.this, 0, my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

                // method that changes the update text Textbox
                set_alarm_text("Alarm off!");

                // cancel the alarm
                alarm_manager.cancel (pending_intent);

                // putting an extra string into my_intent that
                // tells the clock that you pressed the "alarm off" button
                my_intent.putExtra("extra", "alarm off");
                //also putting an extra int into the alarm off section
                // to prevent crashes and Null Point Exceptions
                my_intent.putExtra("alarm_choice", choose_alarm_sound);

                //stopping the ringtone
                sendBroadcast(my_intent);
            }
        });


    }

    private void set_alarm_text(String output) {
        update_text.setText(output);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos

        //outputing  whatever id the user has selected
        //Toast.makeText(parent.getContext(), "The dropdown menu item is "
        //        + id, Toast.LENGTH_SHORT).show();
        choose_alarm_sound = (int) id;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback


    }
}
