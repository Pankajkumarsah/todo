package com.example.pankajnotereminder.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.pankajnotereminder.R;

import static java.lang.Thread.sleep;

public class Splash extends AppCompatActivity {

    ImageView splash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splash = (ImageView) findViewById(R.id.splash_img);

        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.myanimation);
        splash.startAnimation(myanim);

        Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    sleep(1000);
                    Intent i = new Intent(Splash.this, MainActivity.class);
                    startActivity(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        myThread.start();


    }
}
