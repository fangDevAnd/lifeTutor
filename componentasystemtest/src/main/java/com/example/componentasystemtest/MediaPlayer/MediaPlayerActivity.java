package com.example.componentasystemtest.MediaPlayer;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.componentasystemtest.R;

import java.io.File;
import java.io.IOException;

public class MediaPlayerActivity extends BasePermissiontActivity implements MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener {


    private static final String TAG = "test";
    MediaPlayer mediaPlayer;
    File file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_media_player);
        requestPermission(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
        }, 12);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnPreparedListener(this);
        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "yujian.mp3");
        Log.d(TAG, "onCreate: " + file.exists() + "\t" + file.getAbsolutePath());
    }

    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.reset:
                mediaPlayer.reset();
                break;
            case R.id.setDataSource:
                try {
                    mediaPlayer.setDataSource(file.getPath());

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "文件找不到", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.prepareAsync:
                mediaPlayer.prepareAsync();

                break;
            case R.id.prepare:
                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.start:
                mediaPlayer.start();
                break;

            case R.id.seekTo:
                mediaPlayer.seekTo(20000);
                break;

            case R.id.stop:
                mediaPlayer.stop();

                break;

            case R.id.pause:
                mediaPlayer.pause();

                break;
        }


    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Toast.makeText(this, "发生错误了", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Toast.makeText(this, "音乐装备好了", Toast.LENGTH_SHORT).show();
    }


}
