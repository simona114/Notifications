package com.example.notifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnStart, btnStop;
    int notificationId = 78789878;
    String CHANNEL_ID = "nbu_lectures";
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnOpenNotification);
        btnStop = findViewById(R.id.btnCloseNotification);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMyNotification();
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelMyNotification();
            }
        });

        createNotificationChannel();
    }

    private void cancelMyNotification() {
        //todo
    }

    /*
    Creates an implicit intent for opening a webpage by clicking on the notification
     */
    private void sendMyNotification() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.nbu.bg"));

        /* Deletes the previous notification before sending a new one */
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        /*  Checks current device version */
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle("Notification Title")
                .setContentText("Notification text")
                .setSubText("Tap to view the NBU website")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Your notification content"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Sets the intent that will be fired when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId, builder.build());

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channel_name = getString(R.string.channel_name);
            String channel_description = getString(R.string.channel_description);
            int channel_importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channel_name, channel_importance);
            channel.setDescription(channel_description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}