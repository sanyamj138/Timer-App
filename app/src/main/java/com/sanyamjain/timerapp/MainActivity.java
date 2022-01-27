package com.sanyamjain.timerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    TextView textView;
    Boolean counterIsActive = false;
    Button stopButton;
    CountDownTimer countDownTimer;

    public void buttonClicked(View view) {
        if (counterIsActive) {
            textView.setText("0:00");
            seekBar.setProgress(30);
            seekBar.setVisibility(View.VISIBLE);
            countDownTimer.cancel();
            stopButton.setText("Start");
            counterIsActive = false;
        } else {
            counterIsActive = true;
            seekBar.setVisibility(View.INVISIBLE);
            stopButton.setText("Stop");


            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long l) {
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.tick);
                    mplayer.start();
                    updateTimer((int) l / 1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.horn);
                    mplayer.start();
                    seekBar.setVisibility(View.VISIBLE);
                    stopButton.setText("Start");
                    counterIsActive = false;
                }
            }.start();
        }
    }

    public void updateTimer(int secondsLeft)
    {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - (minutes * 60);

        String secondString = Integer.toString(seconds);
        if (seconds <= 9)
        {
            textView.setText(Integer.toString(minutes) + ":0" + secondString);
        }
        else
        {
            textView.setText(Integer.toString(minutes) + ":" + secondString);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stopButton = findViewById(R.id.button);
        seekBar = findViewById(R.id.seekBar);
        textView = findViewById(R.id.textView);

        seekBar.setMax(600);
        seekBar.setProgress(30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}