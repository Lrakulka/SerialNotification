package com.example.gencha.alarmexample;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import Entities.Serial;
import parser.DAO;
import parser.LoadImages;
import parser.Parser;

public class MainActivity extends AppCompatActivity {

    private PendingIntent pendingIntent;
    private ArrayAdapter<Serial> adapter;
    private ListView mListView;
    private ArrayList<Serial> serials;
    private Parser parser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parser = new Parser();
        serials = new ArrayList<>();
        //new MyAsynk().execute(null, null, null);

        /* Retrieve a PendingIntent that will perform a broadcast */

        TextView textView = (TextView) findViewById(R.id.text1View);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityTwo.class);
                startActivity(intent);
            }
        });
        Intent alarmIntent = new Intent(MainActivity.this, AlarmReciever.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);


        start();

        findViewById(R.id.stopAlarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

    }



    public void start() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 10*1000;

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
    }

    public void cancel() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
    }

    public void onCnClick(View w) {
        final Parser parser = new Parser();
        final ArrayList<Serial> serials = new ArrayList<>();
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
        new DAO().setFollowSerials(serials, this);

    }
    /*class MyAsynk extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            mListView = ListView.class.cast(findViewById(R.id.main_list));
            try {
                serials.addAll(parser.getAll());
            } catch (IOException e) {
                e.printStackTrace();
            }
            adapter = new ArrayAdapter<>(MainActivity.this, R.layout.main_list_item, serials);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            mListView.setAdapter(adapter);
        }
    }*/
}
