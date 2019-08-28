package com.example.componentasystemtest.musicPlay.simple3;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.componentasystemtest.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MediaPlayerActivity extends AppCompatActivity {


    private static final String TAG = "test";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_media_player_net);


        findViewById(R.id.button2).setOnClickListener((v) -> {


            Uri uri = new Uri.Builder()
                    .scheme("http")
                    .authority("10.109.3.230")
                    .path("/GF").build();//http://10.109.3.230%3A8080/GF 由于编码问题,不能使用 %3a是


            MediaPlayer mediaPlayer = new MediaPlayer();

            mediaPlayer.reset();

            try {
                mediaPlayer.setDataSource(this, Uri.parse("http://10.109.3.230:8080/GF/music/yujian.mp3"));
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        });


    }
}
