package com.example.Trivia_Quest_beta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

public class LibraryActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_library);

        ImageView cavernbook= findViewById(R.id.cavernbook);
        ImageButton libraryback = findViewById(R.id.btn_back_library);

        cavernbook.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final Animation myAnim4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 10);
                myAnim4.setInterpolator(interpolator);
                cavernbook.startAnimation(myAnim4);
                Intent intent = new Intent(getApplicationContext(), CavernLibraryActivity.class);
                startActivity(intent);
            }
        });

        libraryback.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }
}