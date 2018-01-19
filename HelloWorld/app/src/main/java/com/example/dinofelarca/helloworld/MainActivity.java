package com.example.dinofelarca.helloworld;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    TextView tv_clicks, tv_time;
    Button b_click, b_reset;

    CountDownTimer timer;
    int time = 10;
    int clicks  = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_clicks = (TextView) findViewById(R.id.tv_clicks);
        tv_time= (TextView) findViewById(R.id.tv_time);
        b_click = (Button) findViewById(R.id.b_click);
        b_reset = (Button) findViewById(R.id.b_reset);

        b_click.setEnabled(false);

        timer = new CountDownTimer(10000, 1000) {
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

                time = 10;
                b_click.setEnabled(true);
                b_reset.setEnabled(false);
                timer.start();
                clicks = 0;
                tv_clicks.setText("Clicks: 0");

            }

        });

    }
}
