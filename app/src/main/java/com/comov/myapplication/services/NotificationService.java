package com.comov.myapplication.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.os.Process;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.comov.myapplication.R;
import com.comov.myapplication.apiTools.APIService;
import com.comov.myapplication.apiTools.APIUtils;
import com.comov.myapplication.datamodel.Channel;
import com.comov.myapplication.datamodel.ChannelResponse;
import com.comov.myapplication.datamodel.MessageResponse;
import com.comov.myapplication.views.MainView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationService extends Service {

    public static final String TAG = NotificationService.class.getName();
    public static final String CANONICAL_NAME = NotificationService.class.getCanonicalName();
    private static final String ACTION_NEW_MESSAGES = CANONICAL_NAME + ".MESSAGE_TO";
    public static final String EXTRA_MESSAGES_TARGET = CANONICAL_NAME + ".MESSAGE_TARGET";
    public static final String EVENT_MESSAGES_FINISHED = CANONICAL_NAME + ".MESSAGE_FINISHED";
    private static int[] COUNT_NOTIFICATION_ID = {1000,1001,1002,1003};//cada uno de estos es un mensaje nuevo y no requiere de final
    private volatile int counter=0;

    private int getNextCounter(){
        counter = (counter+1);

        return counter;
    }
    private NotificationHandler mServiceHandler;
    protected String username;
    protected String token;
    private APIService APIservice;
    private volatile HashMap<String, Values> mapaNotificaciones; //key channelID
    private volatile HashMap<String, Integer> notChannel; //key channelID value channelNotification
    private volatile Boolean service;

    private class Values {
        private Boolean update; //messages seen
        private Integer lastValue; //message number of channel last time see
        private Integer notificationMsg; //messages shown in notification dif between currentMsg and lastValue
        private Channel channel; //channel

        public Values(boolean b, int i, Channel channel) {
            update = b;
            lastValue = i;
            notificationMsg = i;
            this.channel = channel;

        }
    }

    private final class NotificationHandler extends Handler{



        private NotificationCompat.Builder mNotificationBuilder;
        private NotificationManager mNotificationManager;


        private NotificationCompat.Builder createNotification(){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationService.this, getString(R.string.channelID))
                    .setSmallIcon(R.drawable.ic_chat_black_24dp)
                    .setContentTitle("Nuevo mensaje")
                    .setContentText("Content ")
                    .setAutoCancel(true);
            Log.i(TAG,"Se crea notificación");
            return builder;
        }

        NotificationHandler(Looper looper){
            super(looper);
            mNotificationBuilder = createNotification();
            mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Log.i(TAG, "Current thread is " + Thread.currentThread().getName());

            Intent request = (Intent) msg.obj;
            if (ACTION_NEW_MESSAGES.equals(request.getAction())) {
                Log.i(TAG,"Go loop");
                while(service){
                    getNewMessages(3);
                }
            }
        }


        private void showNotification(int target) {
            mNotificationManager.notify(
                    target,
                    mNotificationBuilder.build()
            );
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        private void getNewMessages(int target) {
                try {
                    Thread.sleep(1000);
                    getChannelsFromUser();
                    Thread.sleep(1000);
                    for (String key:
                         mapaNotificaciones.keySet()) {
                        processMessages(mapaNotificaciones.get(key));
                    }
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }


        }

        public void getChannelsFromUser(){
            APIservice.getChannel(token,username).enqueue(new retrofit2.Callback<ChannelResponse>(){
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(Call<ChannelResponse> call, Response<ChannelResponse> response) {
                    if (response.code() == 200) {
                        List<Channel> channels1 = response.body().getChannels();
                        updateChannel(channels1);
                    } else if (response.code() == 404 ){ Log.i(TAG,"Not found");}
                }
                @Override
                public void onFailure(Call<ChannelResponse> call, Throwable t){
                    Log.i(TAG,"Fallo en la peticion no conocido");
                }
            });
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void updateChannel(List<Channel> channel){
            HashMap<String, Values> auxiliary = new HashMap<>();

            channel.forEach( c -> {
                if(mapaNotificaciones.get(c.get_id())!=null){
                    auxiliary.put(c.get_id(),mapaNotificaciones.get(c.get_id()));
                } else{
                    notChannel.put(c.get_id(),getNextCounter());
                    auxiliary.put(c.get_id(),new Values(false,0, c));
                }
            });
            mapaNotificaciones.clear();
            mapaNotificaciones = auxiliary;
        }

        /**
         * pasamos channelID, esta funcion debe finiquitar las notificaciones, tiene toda la informacion
         * @param value
         */
        public void processMessages(Values value){
            APIservice.getMessage(token,value.channel.get_id()).enqueue(new retrofit2.Callback<MessageResponse>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                    if(response.code() == 200){
                        List<com.comov.myapplication.datamodel.Message> messages1 = response.body().getMessages();
                        if(!value.update || MainView.getCurrent_channel().equals(value.channel.get_id())){//si es falso entonces first time o current Channel
                            value.update = true;
                            value.notificationMsg = messages1.size();
                            value.lastValue = messages1.size();
                            mapaNotificaciones.put(value.channel.get_id(),value);
                        }
                        else{
                            if(!value.lastValue.equals(messages1.size())){
                                if(!value.notificationMsg.equals(messages1.size())){
                                    value.notificationMsg = messages1.size();
                                    mapaNotificaciones.put(value.channel.get_id(),value);
                                    notifyMessage(messages1,value.notificationMsg-value.lastValue);
                                    mNotificationBuilder.setContentTitle(value.channel.getTitle());
                                    showNotification(notChannel.get(value.channel.get_id()));
                                }
                            }
                        }

                    }
                    else if (response.code() == 404){
                        Toast.makeText(getApplicationContext(), "Messsages not found." +
                                "", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<MessageResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Fail "+ t, Toast.LENGTH_LONG).show();
                }
            });
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        private void notifyMessage(List<com.comov.myapplication.datamodel.Message> messages1, Integer diffMessages) {
            ArrayList<com.comov.myapplication.datamodel.Message> messages = new ArrayList<>();
            messages.addAll(messages1);
            String mNotify = "";
            for (int i=0;i<diffMessages;i++){
                mNotify+=printMessage(messages.get(i));
            }
            mNotificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(mNotify));

        }

        private String printMessage(com.comov.myapplication.datamodel.Message message) {
            if(message.isImage())
                return message.getUsername() +": (Photo)\n";
            if(message.isLocation())
                return message.getUsername() +": (Location)\n";
            return message.getUsername()+": "+message.getTitle()+"\n";
        }
    }


    public static void startNotificationService(int countTarget, Context clientContext, Intent requestIntent
    , String user, String token ) {//pasar token y usuarios y esas cosas

        requestIntent.setAction(ACTION_NEW_MESSAGES);
        requestIntent.putExtra(EXTRA_MESSAGES_TARGET, countTarget);
        requestIntent.putExtra("user", user);
        requestIntent.putExtra("token", token);
        clientContext.startService(requestIntent);
    }


    private HandlerThread backgroundThread;
    @Override
    public void onCreate() {
        Log.i("TAG", "Creating...");

       backgroundThread = new HandlerThread(//esto hace que no dependamos del hilo principal
                "NotificationThread",
                Process.THREAD_PRIORITY_BACKGROUND
        );
       service =true;
       backgroundThread.start();
       APIservice = APIUtils.getAPIService();
       mapaNotificaciones = new HashMap<>();
       notChannel = new HashMap<>();
       mServiceHandler = new NotificationHandler(backgroundThread.getLooper());
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Received start id " + startId + ": " + intent);
        Log.i(TAG, "Current thread is " + Thread.currentThread().getName());

        username = intent.getStringExtra("user");
        token = intent.getStringExtra("token");
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        msg.obj = intent;
        mServiceHandler.sendMessage(msg);

        return START_NOT_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {// no vamos a llamar a otros servicios
        //no binding
        return null;
    }


    @Override
    public void onDestroy() {
        service = false;
        Log.i(TAG,"Se termina el servicio de notificaciones");
        mServiceHandler.removeCallbacks(backgroundThread);
        super.onDestroy();
    }
}
