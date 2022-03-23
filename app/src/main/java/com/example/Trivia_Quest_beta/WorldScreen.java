package com.example.Trivia_Quest_beta;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class WorldScreen extends AppCompatActivity {
    ImageButton World1;
    ImageButton World2;
    ImageButton World3;
    ImageButton World4;
    ImageButton World5;
    ImageButton back_home;
    private String Username;

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
        setContentView(R.layout.activity_world_screen);


        SharedPreferences sp = getSharedPreferences("TriviaPrefs", Activity.MODE_PRIVATE);
        Username = sp.getString("username", "");

        back_home = (ImageButton)findViewById(R.id.btnbackHome);
        back_home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("username", Username);
               startActivity(intent);

            }
        });

        World1 = (ImageButton)findViewById(R.id.World_1_Button);
        World1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final Animation myAnim = AnimationUtils.loadAnimation(WorldScreen.this, R.anim.bounce);
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 10);
                myAnim.setInterpolator(interpolator);
                World1.startAnimation(myAnim);
                Intent intent = new Intent(getApplicationContext(), AreaScreen.class);
                startActivity(intent);
            }
        });

        World2 = (ImageButton) findViewById(R.id.World_2_Button);
        World2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(WorldScreen.this, "It's Locked", Toast.LENGTH_LONG).show();
                final Animation myAnim = AnimationUtils.loadAnimation(WorldScreen.this, R.anim.bounce);
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 10);
                myAnim.setInterpolator(interpolator);
                World2.startAnimation(myAnim);
            }
        });

        World3 = (ImageButton) findViewById(R.id.World_3_Button);
        World3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(WorldScreen.this, "It's Locked", Toast.LENGTH_LONG).show();
                final Animation myAnim = AnimationUtils.loadAnimation(WorldScreen.this, R.anim.bounce);
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 10);
                myAnim.setInterpolator(interpolator);
                World3.startAnimation(myAnim);

            }
        });

        World4 = (ImageButton) findViewById(R.id.World_4_Button);
        World4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(WorldScreen.this, "It's Locked", Toast.LENGTH_LONG).show();
                final Animation myAnim = AnimationUtils.loadAnimation(WorldScreen.this, R.anim.bounce);
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 10);
                myAnim.setInterpolator(interpolator);
                World4.startAnimation(myAnim);

            }
        });

        World5 = (ImageButton) findViewById(R.id.World_5_Button);
        World5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(WorldScreen.this, "It's Locked", Toast.LENGTH_LONG).show();
                final Animation myAnim = AnimationUtils.loadAnimation(WorldScreen.this, R.anim.bounce);
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 10);
                myAnim.setInterpolator(interpolator);
                World5.startAnimation(myAnim);

            }
        });

    }
}
