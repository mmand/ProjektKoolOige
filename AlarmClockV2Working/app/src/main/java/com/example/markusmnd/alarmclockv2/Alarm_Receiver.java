package com.example.markusmnd.alarmclockv2;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;



public class Alarm_Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("We are in the receiver", "Yea boi");

        // fetching extra strings from the intent
        // tells the app whether the user pressed the alarm on button or the alarm off button
        String get_your_string = intent.getExtras().getString("extra");

        Log.e("what is the key? ", get_your_string);

        //fetching the extra longs from  the intent
        //this tells us which value the user picked
        //from the dropdown menu/spinner
        Integer get_your_alarm_choice = intent.getExtras().getInt("alarm_choice");

        Log.e("The alarm choice is ", get_your_alarm_choice.toString());

        // creating an intent to the ringtone service
        Intent service_intent = new Intent(context, RingtonePlayingService.class);

        // passing the extra string from Receiver to the Ringtone Playing Service
        service_intent.putExtra("extra", get_your_string);

        //passing the extra integer from the Receiver to the RingtonePlayingService
        service_intent.putExtra("alarm_choice", get_your_alarm_choice);

        //starting the ringtone service
        context.startService(service_intent);

    }
}
