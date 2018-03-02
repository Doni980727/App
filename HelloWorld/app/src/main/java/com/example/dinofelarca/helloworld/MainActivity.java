package com.example.dinofelarca.helloworld;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    TextView tv_clicks, tv_time;
    Button b_click, b_reset;
    private SensorManager SM;
    private Sensor gSensor;
    private SensorEventListener gEventListener;

    CountDownTimer timer;
    int time = 25;
    int clicks  = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_clicks = (TextView) findViewById(R.id.tv_clicks);
        tv_time= (TextView) findViewById(R.id.tv_time);
        b_click = (Button) findViewById(R.id.b_click);
        b_reset = (Button) findViewById(R.id.b_reset);
        SM = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gSensor = SM.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        b_click.setEnabled(false);

        timer = new CountDownTimer(25000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time--;
                tv_time.setText("Time: " + time);
            }

            @Override
            public void onFinish() {
                b_click.setEnabled(false);
                b_reset.setEnabled(true);
            }
        };

        if (gSensor == null) {

            Toast.makeText(this, "The device has no Gyroscope", Toast.LENGTH_SHORT).show();
            finish();

        }

        gEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                if (sensorEvent.values[1] > 2f) {
                    clicks++;
                } else if (sensorEvent.values[1] < 2f){
                    clicks++;
                }
                tv_clicks.setText("Clicks: " + clicks);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        b_click.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                clicks++;
                tv_clicks.setText("Clicks: " + clicks);

            }

        });

        b_reset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                time = 25;
                b_click.setEnabled(true);
                b_reset.setEnabled(false);
                timer.start();
                clicks = 0;
                tv_clicks.setText("Clicks: 0");

            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        SM.registerListener(gEventListener, gSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SM.unregisterListener(gEventListener);
    }
}


