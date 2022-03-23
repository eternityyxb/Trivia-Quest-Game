package com.example.Trivia_Quest_beta;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class StartActivity extends AppCompatActivity {

    private Button btnstarting;
    private VideoView startBG;
    ImageView title;
    MediaPlayer mMediaPlayer;
    int mCurrentVideoPosition;
    ObjectAnimator textColorAnim;
    Animation Fadein, Fadeout;
    private Boolean isClick = false;

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
        setContentView(R.layout.activity_start);

        btnstarting = findViewById(R.id.btnstart);
        startBG = (VideoView) findViewById(R.id.startBackground);
        title= (ImageView) findViewById(R.id.GameTitle);
        final TextView blinkText = (TextView) findViewById(R.id.btninstruction);
        Fadein=AnimationUtils.loadAnimation(this, R.anim.fadein);
        Fadeout=AnimationUtils.loadAnimation(this, R.anim.fadeout);

        textColorAnim = ObjectAnimator.ofInt(blinkText, "textColor", Color.WHITE, Color.TRANSPARENT);
        textColorAnim.setDuration(1000);
        textColorAnim.setEvaluator(new ArgbEvaluator());
        textColorAnim.setRepeatCount(ValueAnimator.INFINITE);
        textColorAnim.setRepeatMode(ValueAnimator.REVERSE);
        textColorAnim.start();

        if(isClick == false) {
            btnstarting.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    isClick = true;
                    btnstarting.setEnabled(false);
                    title.startAnimation(Fadeout);
                    blinkText.startAnimation(Fadeout);
                    Fadeout.setFillAfter(true);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            overridePendingTransition(0, android.R.anim.fade_out);
                        }
                    }, 3000);
                }
            });
        }



        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.startgame);
        startBG.setVideoURI(uri);
        startBG.start();
        startBG.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mMediaPlayer = mediaPlayer;
                mMediaPlayer.setLooping(true);
                if (mCurrentVideoPosition != 0) {
                    mMediaPlayer.seekTo(mCurrentVideoPosition);
                    mMediaPlayer.start();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCurrentVideoPosition = mMediaPlayer.getCurrentPosition();
        startBG.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startBG.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayer.release();
        mMediaPlayer = null;
    }
}