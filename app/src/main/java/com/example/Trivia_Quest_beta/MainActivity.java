package com.example.Trivia_Quest_beta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT=3000;
    private ImageView showlogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_main);

        //splash screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //load gif image
        //showlogo= findViewById(R.id.mylogo);
        //Glide.with(this).load(R.drawable.loading).into(showlogo);

        //handler to run splash then direct to another activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //we can create explicit intent
                Intent intent = new Intent(MainActivity.this,StartActivity.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, 0);
                        finish();

            }
        },SPLASH_TIME_OUT);
    }
}