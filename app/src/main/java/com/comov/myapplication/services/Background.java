package com.comov.myapplication.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.comov.myapplication.R;
import com.comov.myapplication.datamodel.Channel;

import java.util.List;

public class Background extends Service {
    public static NotificationManagerCompat notificationManagerCompat;
    private String username;
    private List<Channel> channelList;
    private String token;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //obtengo username y channelList de aqui
        notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(0,createNotification("Canales con mensajes","Nuevo mensaje", NotificationCompat.PRIORITY_DEFAULT));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Notification createNotification(String title, String content, int priority){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.channelID))
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(content)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                .setPriority(priority)
                .setAutoCancel(true);
        return builder.build();
    }
}
