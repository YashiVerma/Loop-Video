package com.example.loopvideo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import android.app.ActivityManager;

public class MainActivity extends AppCompatActivity {
    private long mLongBackPressedTime;
    private Toast mToastBack;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VideoView videoView = (VideoView) findViewById(R.id.VideoView);

        //Screen Pinning
        startLockTask();


        //video loop
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer pMediaPlayer) {
                videoView.start(); //need to make transition seamless.
            }
        });
        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.test);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        videoView.start();
    }

    // Double tap back button to exit
    @Override
    public void onBackPressed() {
        if (mLongBackPressedTime + 2000 > System.currentTimeMillis()) {
            mToastBack.cancel();
            super.onBackPressed();
            return;
        } else {
            mToastBack = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            mToastBack.show();
        }
        mLongBackPressedTime = System.currentTimeMillis();
    }


}