package com.example.andriodproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class notification extends AppCompatActivity implements View.OnClickListener {

    private int notificationID = 1;
    private Button bmiButton,buttonTIme;

    ImageButton steps,btnViewData;

    public void setbuttton_function(TimePicker getTime,PendingIntent alarmInfomation,AlarmManager Managealarm){
        int hour = getTime.getCurrentHour();
        int minutes = getTime.getCurrentMinute();

        //get the time
        Calendar getStartTime = Calendar.getInstance();
        getStartTime.set(Calendar.HOUR_OF_DAY,hour);
        getStartTime.set(Calendar.MINUTE,minutes);
        getStartTime.set(Calendar.SECOND,0);

        Long alarmStartTIme = getStartTime.getTimeInMillis();

        //set the time
        Managealarm.set(AlarmManager.RTC_WAKEUP,alarmStartTIme,alarmInfomation);

        Toast.makeText(this,"Notification is set!",Toast.LENGTH_SHORT).show();

    }
    public void onClick(View view){
        EditText editText = findViewById(R.id.edit_text);
        TimePicker getTime = findViewById(R.id.timePicker);


        Intent intenter = new Intent(notification.this, broadCastFunction.class);
        intenter.putExtra("NotificationID",notificationID);
        intenter.putExtra("StringValue",editText.getText().toString()); // get and set string value that is type by the user in the edit text

//get broadcast service
        PendingIntent alarmInfo = PendingIntent.getBroadcast(notification.this,0, intenter,PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);

        switch(view.getId()){
            case R.id.setter_Btn:
                setbuttton_function(getTime,alarmInfo,alarm);
                break;

            case R.id.Remove_btn:
                //remove notification set
                alarm.cancel(alarmInfo);
                Toast.makeText(this,"Notification is remove!",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        //set on click listener on the button
        findViewById(R.id.setter_Btn).setOnClickListener(this);
        findViewById(R.id.Remove_btn).setOnClickListener(this);

        bmiButton = (Button) findViewById(R.id.BMI_button);
        buttonTIme =(Button) findViewById(R.id.buttonStop);
        steps =(ImageButton) findViewById(R.id.steps_button);
        btnViewData =(ImageButton) findViewById(R.id.view_button);


        bmiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(notification.this,BmiCalculator.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(),"BMI Calculator",Toast.LENGTH_SHORT).show();
            }
        });
        buttonTIme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(notification.this,MainActivity.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(),"Stopwatch",Toast.LENGTH_SHORT).show();
            }
        });
        steps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(notification.this,StepDetector.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(),"Pedometer",Toast.LENGTH_SHORT).show();
            }
        });
        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(notification.this,DatabasedLister.class);
                startActivity(i);
            }
        });

    }
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onRestoreInstanceState(savedInstanceState);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
    }
}
