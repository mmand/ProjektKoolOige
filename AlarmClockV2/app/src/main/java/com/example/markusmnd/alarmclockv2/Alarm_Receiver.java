package com.example.markusmnd.alarmclockv2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;



public class Alarm_Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("We are in the receiver", "Yea boi");

        String get_your_string = intent.getExtras().getString("extra");

        Log.e("what is the key? ", get_your_string);

        //fetch the extra longs from  the intent
        //this tells us which value the user picked
        //from the dropdown menu/spinner

        Integer get_your_alarm_choice = intent.getExtras().getInt("alarm_choice");

        Log.e("The alarm choice is ", get_your_alarm_choice.toString());


        Intent service_intent = new Intent(context, RingtonePlayingService.class);

        service_intent.putExtra("extra", get_your_string);

        //pass the extra integer from the Receiver to the RingtonePlayingService

        service_intent.putExtra("alarm_choice", get_your_alarm_choice);

        context.startService(service_intent);

    }
}
