package com.example.dinofelarca.starcollector2;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private SensorManager SM;
    private Sensor gSensor;
    private SensorEventListener gEventListener;
    private ImageView ship;
    private static ImageView star;
    private RelativeLayout relLay;
    private TranslateAnimation animation;
    private Animation.AnimationListener aListener;
    private Button btn;
    static Boolean isUp = true;
    private TextView time, points;
    int[] firstPosition = new int[2];
    int[] secondPosition = new int[2];
    int wLayout, wStar, hLayout, hStar, starX, starY, score;

    int timee = 60;
    CountDownTimer countDown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        time = (TextView) findViewById(R.id.time);
        points = (TextView) findViewById(R.id.points);
        star = (ImageView) findViewById(R.id.star);
        ship = (ImageView) findViewById(R.id.ship);
        relLay = (RelativeLayout) findViewById(R.id.layout);

        SM = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gSensor = SM.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        countDown = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timee--;
                time.setText("" + timee);
                boolean isOverlap = isViewOverlapping(ship, star);
                displayScore(score, isOverlap);
            }

            @Override
            public void onFinish() {

                btn.setEnabled(true);
                btn.setVisibility(View.VISIBLE);
                timee = 60;

            }
        };


        if (gSensor == null) {

            Toast.makeText(this, "The device has no Gyroscope", Toast.LENGTH_SHORT).show();
            finish();

        }

        gEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                float dX = ship.getX();
                int wShip = ship.getWidth();
                int wLayout = relLay.getWidth();

                float pShip = dX + wShip;

                if (sensorEvent.values[1] > 0.1f) {
                    ship.setX(dX + 4);
                    if (pShip >= wLayout) {
                        ship.setX(dX + 0);
                    }


                } else if (sensorEvent.values[1] < -0.1f){
                    ship.setX(dX - 4);
                    if (dX <= 0) {
                        ship.setX(dX - 0);
                    }

                }


            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };


        btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                        star.startAnimation(MainActivity.getVerticalSlideAnimation(0, ship.getY(), 3000, 0));


                /*animation = new TranslateAnimation(
                        TranslateAnimation.ABSOLUTE, 0f,
                        TranslateAnimation.ABSOLUTE, 0f,
                        TranslateAnimation.RELATIVE_TO_PARENT, star.getY(),
                        TranslateAnimation.RELATIVE_TO_PARENT, 0.5f);
                animation.setDuration(5000);
                animation.setRepeatCount(-1);
                star.setAnimation(animation);*/
                countDown.start();
                btn.setEnabled(false);
                btn.setVisibility(View.GONE);
            }
        });

    }

    private boolean isViewOverlapping(View firstView, View secondView) {
        firstView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        firstView.getLocationOnScreen(firstPosition);
        secondView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        secondView.getLocationOnScreen(secondPosition);

        return firstPosition[0] < secondPosition[0] + secondView.getMeasuredWidth()
                && firstPosition[0] + firstView.getMeasuredWidth() > secondPosition[0]
                && firstPosition[1] < secondPosition[1] + secondView.getMeasuredHeight()
                && firstPosition[1] + firstView.getMeasuredHeight() > secondPosition[1];
    }

    private void displayScore(int number, boolean isOverlap){
        wLayout = relLay.getWidth();
        hLayout = relLay.getHeight();
        wStar = star.getWidth();
        hStar = star.getHeight();
        Random rand = new Random();
        starX = rand.nextInt(wLayout - wStar);
        starY = rand.nextInt(hLayout - hStar);
        if(isOverlap == true) {
            score = score + 1;
            points.setText("" + score);
            star.setY(200);
            star.setX(starX);
            star.startAnimation(getVerticalSlideAnimation(0, ship.getY(), 3000, 0));
        }
    }

    public static Animation getVerticalSlideAnimation(float fromYPosition, final float toYPosition, int duration, int startOffset)
    {
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0F, 1, 0.0F, 0, fromYPosition, 0, toYPosition);
        translateAnimation.setDuration(duration);
        translateAnimation.setStartOffset(startOffset);


        //Stop animation after finishing.
        //translateAnimation.setFillAfter(true);

        translateAnimation.setAnimationListener(new Animation.AnimationListener()
        {
            public void onAnimationStart(Animation animation) { }
            public void onAnimationRepeat(Animation animation) { }
            public void onAnimationEnd(Animation animation) {
                star.setY(toYPosition);
            }
        });

        return translateAnimation;
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
