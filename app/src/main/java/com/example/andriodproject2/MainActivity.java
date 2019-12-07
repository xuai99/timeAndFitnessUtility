package com.example.andriodproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

import java.util.TimeZone;


public class MainActivity extends AppCompatActivity {
    private Button bmiButton,buttonNoti;
    TextView stopwatch, displayTimeZone;
    ImageButton start_button, pause_button, reset_button;
    long getMiliseconds, StartTimer, TimeBuffer, UpdateTime = 0 ; // hold time for calculation
    Handler handler;
    int Seconds, Minutes, MilliSeconds,hour ; // time is store in integer

    private  static  final String KEY_INDEX="index";
    private  static  final String TAG="MainActivity";


    // runnable function for the stop watch
    public Runnable stopWatchRunning = new Runnable() {

        public void run() {

            getMiliseconds = SystemClock.uptimeMillis() - StartTimer; //uses systemClock function to get miliseconds.

            UpdateTime = TimeBuffer + getMiliseconds;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
            hour = Seconds/3600;
            MilliSeconds = (int) (UpdateTime % 1000);

            stopwatch.setText(String.format("%02d",hour)+":"+String.format("%02d",Minutes)+":"+String.format("%02d",Seconds)+":"+String.format("%02d",MilliSeconds));

            handler.postDelayed(this, 0); // no delay
        }

    };
// reset function
    public void resetFunction(){ // function to reset stopwatch back 00:00:00

        getMiliseconds = 0;StartTimer = 0;TimeBuffer = 0;UpdateTime = 0;

        Seconds = 0; Minutes = 0 ;MilliSeconds = 0 ;hour =0;

        stopwatch.setText("00:00:00:00");
    }
    //start function
    public void startFunction(){
        StartTimer = SystemClock.uptimeMillis();
        handler.postDelayed(stopWatchRunning, 0);
        reset_button.setEnabled(false);

    }
    // pause function
    public void pauseFunction(){
        TimeBuffer += getMiliseconds;
        handler.removeCallbacks(stopWatchRunning);
        reset_button.setEnabled(true);

    }
    // get location and gmt of the device
    public void setTImeZone(){
        TimeZone mTimeZone = TimeZone.getDefault();
        String getGMT = TimeZone.getTimeZone(mTimeZone.getID()).getDisplayName(true,TimeZone.SHORT);
        String getLocation = mTimeZone.getID();

        displayTimeZone.setText(getGMT+" "+getLocation);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find the id;
        stopwatch = (TextView)findViewById(R.id.textView);
        displayTimeZone =(TextView) findViewById(R.id.timeZone_display);
        start_button = (ImageButton) findViewById(R.id.button_start);
        pause_button = (ImageButton) findViewById(R.id.button_pause);
        reset_button = (ImageButton) findViewById(R.id.button_reset);

        bmiButton = (Button) findViewById(R.id.BMI_button);
        buttonNoti =(Button)findViewById(R.id.buttonNoti);

        handler = new Handler();

        setTImeZone();

        //when click on start button
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startFunction();
                Toast.makeText(getApplicationContext(),"Play",Toast.LENGTH_SHORT).show();

            }
        });
        //when click on pause button
        pause_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseFunction();
                Toast.makeText(getApplicationContext(),"Pause",Toast.LENGTH_SHORT).show();

            }
        });
        //when click on reset button
        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetFunction();
                Toast.makeText(getApplicationContext(),"Stop",Toast.LENGTH_SHORT).show();

            }
        });

        bmiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,BmiCalculator.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(),"BMI Calculator",Toast.LENGTH_SHORT).show();
            }
        });
        buttonNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,notification.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(),"Make a reminder",Toast.LENGTH_SHORT).show();
            }
        });

    }


    

}
