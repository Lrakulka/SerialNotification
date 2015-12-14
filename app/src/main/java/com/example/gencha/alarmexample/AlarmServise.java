package com.example.gencha.alarmexample;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import Entities.Season;
import Entities.Seria;
import Entities.Serial;
import parser.DAO;
import parser.Parser;

public class AlarmServise extends Service {
    final String LOG_TAG = "myLogs";
    private static int NOTIFY_ID = 0;
    private static volatile boolean start = true;

    public void onCreate() {
        super.onCreate();
        //Toast.makeText(this, "Created", Toast.LENGTH_SHORT).show();
        Log.d(LOG_TAG, "onCreate");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public int onStartCommand(Intent intent, int flags, int startId) {
        final Parser parser = new Parser();
        final ArrayList<Serial> serials = new ArrayList<>();//new DAO().getFollowSerials(this);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serials.addAll(parser.getAll());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            thread.start();
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (start) {
            start = false;
            NOTIFY_ID = 0;
            new Thread(new Runnable() {
                @Override
                public void run() {

                    if (serials != null) {
                        /*Thread downloadThread = new Thread() {
                            public void run() {
                                try {
                                    for (Serial serial : serials) {
                                        parser.getSerial(serial);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        downloadThread.start();
                        try {
                            downloadThread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }*/
                        ArrayList<Seria> serias = new ArrayList<>();
                        ArrayList<Season> seasons = new ArrayList<>();
                        for (Serial serial : serials) {
                            seasons.clear();
                            for (Season season : serial.getSeasons()) {
                                serias.clear();
                                for (Seria seria : season.getSerias()) {
                                    if (!seria.getIsWatched()) {
                                        serias.add(seria);
                                    }
                                }
                                if (!serias.isEmpty()) {
                                    seasons.add(new Season(season.getName(), serias));
                                }
                            }
                            StringBuilder mas = new StringBuilder();
                            if (!seasons.isEmpty()) {
                                for (Season season : seasons) {
                                    mas.append(season.getName());
                                    for (Seria seria : serias) {
                                        mas.append(" ").append(seria.getName());

                                    }
                                }
                                final Bitmap[] bitmap = new Bitmap[1];
                                final String imageUrl = serial.getImagePath();
                                Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {

                                        URL url = null;
                                        try {
                                            url = new URL(imageUrl);

                                            bitmap[0] = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                thread.start();
                                try {
                                    thread.join();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if (seasons.size() == 1 && seasons.get(0).getSerias().size() == 1) {
                                    if (bitmap[0] == null) {
                                        showNotification(BitmapFactory.decodeFile(serial.getImagePath()), serial.getName(),
                                                mas.toString(), seasons.get(0).getSerias().get(0).getUrl());
                                    } else {
                                        showNotification(bitmap[0], serial.getName(),
                                                mas.toString(), seasons.get(0).getSerias().get(0).getUrl());
                                    }
                                } else {
                                    if (bitmap[0] == null) {
                                        showNotification(BitmapFactory.decodeFile(serial.getImagePath()), serial.getName(),
                                                mas.toString(), serial.getUrl());
                                    } else {
                                        showNotification(bitmap[0], serial.getName(),
                                                mas.toString(), serial.getUrl());
                                    }
                                }
                                mas.setLength(0);
                            }
                        }
                    }

                }
            }).start();
            start = true;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        //Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
    }

    public IBinder onBind(Intent intent) {
        //Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    void showNotification(Bitmap img, String serial, String seria, String seriaURL) {
        Context context = getApplicationContext();

        Intent notificationIntent = new Intent(Intent.ACTION_VIEW);
        notificationIntent.setData(Uri.parse(seriaURL));
        //startActivity(notificationIntent);

        //Intent notificationIntent = new Intent(context, MainActivity.class);

        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Resources res = context.getResources();

        Notification.Builder builder = new Notification.Builder(context);

        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_launcher)
                        // большая картинка
                .setLargeIcon(img)
                        //.setTicker(res.getString(R.string.warning)) // текст в строке состояния
                .setTicker("Нова серія серіалу!")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                        //.setContentTitle(res.getString(R.string.notifytitle)) // Заголовок уведомления
                .setContentTitle(serial)
                        //.setContentText(res.getString(R.string.notifytext))
                .setContentText("Вийшов " + seria); // Текст уведомления

        // Notification notification = builder.getNotification(); // до API 16
        Notification notification = builder.build();

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFY_ID++, notification);
    }}
