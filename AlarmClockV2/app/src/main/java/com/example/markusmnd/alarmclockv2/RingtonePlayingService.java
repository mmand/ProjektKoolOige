package com.example.markusmnd.alarmclockv2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;


public class RingtonePlayingService extends Service{

    MediaPlayer media_song;
    int startId;
    boolean isRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        String state = intent.getExtras().getString("extra");

        //fetch the alarm choice integer values
        Integer alarm_id = intent.getExtras().getInt("alarm_choice");

        Log.e("Alarm choice is ", alarm_id.toString());
        //notifications
        //setting up up the notification manager
        NotificationManager notify_manager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        //set up an intent that goes to the main activity
        Intent intent_main_activity = new Intent(this.getApplicationContext(), MainActivity.class);
        //set up a pending intent
        PendingIntent pending_intent_main_activity = PendingIntent.getActivity(this, 0,
                intent_main_activity, 0);

        //make the notification parameters
        Notification notification_popup = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("An alarm is going off!")
                .setContentText("Click me")
                .setContentIntent(pending_intent_main_activity)
                .setAutoCancel(true)
                .build();


        Log.e("Ringtone extra is", state);

        assert state != null;
        switch (state) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }

        if (!this.isRunning && startId == 1) {

            Log.e("there is no music, ", "and you want start");

            this.isRunning = true;
            this.startId = 0;

            notify_manager.notify(0, notification_popup);

            //play the alarm sound depending on the past alarm id

            if (alarm_id == 0) {
                media_song = MediaPlayer.create(this, R.raw.vile);
                media_song.start();
                media_song.setLooping(true);
            } else if (alarm_id == 1) {
                media_song = MediaPlayer.create(this, R.raw.ship_bell);
                media_song.start();
                media_song.setLooping(true);
            } else if (alarm_id == 2) {
                media_song = MediaPlayer.create(this, R.raw.tornado_siren);
                media_song.start();
                media_song.setLooping(true);
            } else if (alarm_id == 3) {
                media_song = MediaPlayer.create(this, R.raw.metronome);
                media_song.start();
                media_song.setLooping(true);
            } else if (alarm_id == 4) {
                media_song = MediaPlayer.create(this, R.raw.vehicle_alarm);
                media_song.start();
                media_song.setLooping(true);
            } else if (alarm_id == 5) {
                media_song = MediaPlayer.create(this, R.raw.dog_bark);
                media_song.start();
                media_song.setLooping(true);
            } else {

            }
            }
            else if (this.isRunning && startId == 0) {
                Log.e("there is music, ", "and you want end");

                // stop the ringtone
                media_song.stop();
                media_song.reset();

                this.isRunning = false;
                this.startId = 0;

            } else if (!this.isRunning && startId == 0) {
                Log.e("there is no music, ", "and you want end");

                media_song.stop();
                media_song.reset();

            } else if (this.isRunning && startId == 1) {
                Log.e("there is music, ", "and you want start");

                this.isRunning = true;
                this.startId = 1;

            } else {
                Log.e("else ", "somehow you reached this");

            }


            return START_NOT_STICKY;
        }

    @Override
    public void onDestroy() {
        Log.e("on Destroy called", "ta da");

        super.onDestroy();
        this.isRunning = false;
    }
}
