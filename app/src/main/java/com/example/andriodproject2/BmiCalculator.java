package com.example.andriodproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class BmiCalculator extends AppCompatActivity {
    private EditText weight, height;
    private TextView output,stringer;
    private Button button_calculate;
    private Button NotiButton,timeButton;

    private float Enter_weight, Enter_height;
    private float answer;
// change value formate to 2 decimal placed
    DecimalFormat formater = new DecimalFormat("#.##");
// calculation fucntion

    public float calculation() {
        String getHeight = height.getText().toString().trim();
        String getWeight = weight.getText().toString().trim();

        if (getHeight != null && !"".equals(getHeight) && getWeight != null & !"".equals(getWeight)) {

            Enter_weight = Float.parseFloat(getWeight);
            Enter_height = Float.parseFloat(getHeight) / 100;

            answer = Enter_weight / (Enter_height * Enter_height);
        }
        return answer;
    }
// display string statement based on vBMI value calculated
    public void bmiDisplyStringValue(float answer) {
        String BMIanswer = formater.format(answer);
        if(answer < 1){
            stringer.setText("Error BMI too low");
        }else{
            if(answer < 15 && answer >=1){
                stringer.setText("you are severly Underweight");
            }else if(answer >=15 && answer <18.5){
                stringer.setText("you are Underweight");
            }else if(answer >=18.5 && answer <25){
                stringer.setText("Normal weight for a person");
            }else if(answer >= 25 && answer <=29){
                stringer.setText("you are overweight");
            }
            else if(answer >=30 && answer<=50){
                stringer.setText("You are Obese");
            }else if(answer >50){
                stringer.setText("Error value Bmi too high");
            }
        }
        output.setText(BMIanswer);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator);


        weight = (EditText) findViewById(R.id.Weight);
        height = (EditText) findViewById(R.id.Height);
        output = (TextView) findViewById(R.id.output);
        stringer =(TextView) findViewById(R.id.string_output);
        button_calculate = (Button) findViewById(R.id.buttonCal);

        NotiButton =(Button) findViewById(R.id.buttonNoti);
        timeButton =(Button)findViewById(R.id.buttonStop);

// when on click calculate button
        button_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float value = calculation();
                bmiDisplyStringValue(value);
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BmiCalculator.this,MainActivity.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(),"Stopwatch",Toast.LENGTH_SHORT).show();

            }
        });

        NotiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BmiCalculator.this,notification.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(),"Make a reminder",Toast.LENGTH_SHORT).show();

            }
        });



    }


    }

