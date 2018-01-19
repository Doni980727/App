package com.example.dinofelarca.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    TextView tv_clicks;
    Button b_click, b_reset;

    int clicks  = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_clicks = (TextView) findViewById(R.id.tv_clicks);
        b_click = (Button) findViewById(R.id.b_click);
        b_reset = (Button) findViewById(R.id.b_reset);

        b_click.setEnabled(true);

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

                clicks = 0;
                tv_clicks.setText("Clicks: 0");

            }

        });

    }
}
