package com.example.imusicz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    // creating seek bar and controlling volume
    MediaPlayer player;
    AudioManager audioManager;

    public void play(View view)
    {
        player.start();
        TextView statusBar = findViewById(R.id.statusBar);
        statusBar.setText("Playing");

    }
    public void pause(View view)
    {
        player.pause();
        TextView statusBar = findViewById(R.id.statusBar);
        statusBar.setText("Pause");
    }
    public void stop(View view)
    {
        player.stop();
        TextView statusBar = findViewById(R.id.statusBar);
        statusBar.setText("");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // my code
        player = MediaPlayer.create(this,R.raw.audio);
        audioManager  = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currVol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        SeekBar seekVol = findViewById(R.id.seekVol);
        seekVol.setMax(maxVol);
        seekVol.setProgress(currVol);
        seekVol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
       final SeekBar seekProgress = findViewById(R.id.seekProg);
        seekProgress.setMax(player.getDuration());
        // creating timer to update seek progress

        seekProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                player.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seekProgress.setProgress(player.getCurrentPosition());
            }
        },0,1000);
    }
}