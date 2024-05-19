package com.example.intent_service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MyService extends Service {

    //Khai bao doi tuong service quan ly
    MediaPlayer myMedia;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    //Goi ham OnCreate de tao doi tuong ma service quan ly

    @Override
    public void onCreate() {
        super.onCreate();
        myMedia = MediaPlayer.create(MyService.this, R.raw.trentinhbanduoitinhyeu);
        myMedia.setLooping(true); //Cho phep lap lai bai hat
    }

    // Goi ham onStartCommand de khoi chay doi tuong ma service quan ly
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(myMedia.isPlaying()){
            myMedia.pause();
        }
        else{
            myMedia.start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    //Goi ham onDestroy de dung doi tuong ma Service quan ly

    @Override
    public void onDestroy() {
        super.onDestroy();
        myMedia.stop();
    }
}