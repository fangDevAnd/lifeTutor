package com.example.componentasystemtest.MediaPlayer;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.componentasystemtest.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 发现测试不通，通过本地的情况可以实现，
 * 但是通过网络，无法正常加载，原因不详
 */
public class VideoPlayerActivity extends BasePermissiontActivity implements View.OnClickListener {


    protected String[] permissionas = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_video_player);
        ButterKnife.bind(this);
        requestPermission(permissionas, REQUEST_CODE_PERMISSION);
        super.onCreate(savedInstanceState);
    }

    VideoView vvVideoView;

    Button play, pause, replay;

    @Override
    public void initView() {

        vvVideoView = findViewById(R.id.vv_VideoView);
        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);
        replay = findViewById(R.id.replay);

        play.setOnClickListener(this);


        initVideoPath();
//        initBind();
    }

    private void initVideoPath() {
        File file = new File(Environment.getExternalStorageDirectory(), "video.mp4");
        //      vvVideoView.setVideoPath(file.getPath());
//        vvVideoView.setVideoURI(Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"));
//        String url="http://ips.ifeng.com/video19.ifeng.com/video09/2014/06/16/1989823-102-086-0009.mp4";
        vvVideoView.setVideoPath("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
        vvVideoView.requestFocus();
        vvVideoView.start();
    }


    /**
     * videoView和MediaController绑定
     */
    private void initBind() {
        MediaController mediaController = new MediaController(this);
        vvVideoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(vvVideoView);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                if (!vvVideoView.isPlaying()) {
                    vvVideoView.start();
                }
                break;
            case R.id.pause:
                if (vvVideoView.isPlaying()) {
                    vvVideoView.pause();
                }
                break;
            case R.id.replay:
                if (vvVideoView.isPlaying()) {
                    vvVideoView.resume();
                }
                break;
        }
    }
}
