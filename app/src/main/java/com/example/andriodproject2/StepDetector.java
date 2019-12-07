package com.example.andriodproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StepDetector extends AppCompatActivity implements SensorEventListener {
    SensorManager getSensorManager;

    DatabaseHelper createdDatabased;
    private Button btnAdd;
    private ImageButton back;
    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    String answer;

    TextView showSteps,showOutput,meters;
    Button btn_reset;
    int steps =0;
    double distance =0;
    DecimalFormat format = new DecimalFormat("#.##"); // to only show two decimal points
    boolean isRunning = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detector);


        showSteps = (TextView) findViewById(R.id.tv_steps);
        showOutput =(TextView) findViewById(R.id.showOutput);
        meters =(TextView)findViewById(R.id.distance_show);
        btn_reset =(Button) findViewById(R.id.function_button);
        btnAdd =(Button) findViewById(R.id.set_button);
        back = (ImageButton) findViewById(R.id.back_imagebutton);

        createdDatabased = new DatabaseHelper(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(steps >=1){
                    steps = steps -1;
                    String holder = String.valueOf(steps);
                    final String answer ="Steps: "+ holder+"  Date: "+date;
                    createdDatabased.insertData(answer);
                    Toast.makeText(getApplicationContext(),"Data inserted",Toast.LENGTH_SHORT).show();
                }else if(steps == 0) {
                    String holder = String.valueOf(steps);
                    final String answer = "Steps: "+ holder+"  Date: "+date;
                    createdDatabased.insertData(answer);
                    Toast.makeText(getApplicationContext(),"Data inserted",Toast.LENGTH_SHORT).show();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StepDetector.this,notification.class);
                startActivity(i);
            }
        });

        getSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // saved value instanced when on landscape view
        if(savedInstanceState !=null){

            steps = savedInstanceState.getInt("steps");
            steps = steps -3;
            showSteps.setText(String.valueOf(steps));
            //saved instance for distance

            distance = savedInstanceState.getDouble("distance");
            distance = distance - 2.1;
            answer = format.format(distance);
            meters.setText(answer+" meters");



        }


    }

    protected void onResume(){
        super.onResume();
        isRunning = true;

        Sensor countSensor = getSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor != null){
            getSensorManager.registerListener(this,countSensor,SensorManager.SENSOR_DELAY_GAME); // get sensor as fast as possible
        }else{
            // if sensor is not available or found it will prompt user of it
            Toast.makeText(this,"Sensor is not found or available",Toast.LENGTH_SHORT).show();
        }

    }

    protected void onPause(){
        super.onPause();
        isRunning =false;
        getSensorManager.unregisterListener(this);


    }

    @Override
    public void onSensorChanged(final SensorEvent event) {
        // if sensor is detected
        //first step is count as 0
        if(isRunning){
            showSteps.setText(String.valueOf(steps));
            steps++;
            if(steps < 0 || distance <0){ //ensure that it would not show -1 or less value  no negative value is present
                steps = 0;
                showSteps.setText(String.valueOf(steps));
                distance = 0;
                answer = format.format(distance);
                meters.setText(answer+" meters");
            }
            meters.setText(answer+" meters");
            distance = distance + 0.7; //average distance of a  steps by a person which is around 2.5 feet
            answer = format.format(distance);

            // number of steps taken specifically
            if(steps == 11){
                showOutput.setText("You have taken 10 steps!");
            }else if(steps == 101){
                showOutput.setText("you taken 100 steps! ");
            }else if(steps == 1001){
                showOutput.setText("You have taken 2000 steps! around 1 miles in distance");

            }else if(steps == 10001){
                showOutput.setText("You just completed 10000 steps!around 5 miles in distance!");
            }
            //reset steps counter,distance and milestone to default.
            btn_reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    steps =0;
                    distance =0;
                    showSteps.setText(String.valueOf(steps));
                    showOutput.setText("Milestone");
                    meters.setText("0");
                }
            });

        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public void onSaveInstanceState(Bundle outstate){
        super.onSaveInstanceState(outstate);

        outstate.putInt("steps",steps);
        outstate.putDouble("distance",distance);
    }
}
