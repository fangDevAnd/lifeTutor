package com.example.componentasystemtest.musicPlay.simple2;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.componentasystemtest.R;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

public class MusicListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private static final String TAG = "test";


    static {
        LitePal.getDatabase();
    }

    /**
     * 设置为静态的全局变量
     */
    public List<Music> musics = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);

        setContentView(R.layout.activity_music_list);

        musics = DataSupport.findAll(Music.class);

        Log.d(TAG, "onCreate: " + musics.size());

        initView();

//        new String[]{"_id", "title", "artist", "album", "duration", "track", "artist_id", "album_id"};

        if (musics.size() == 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String[]{
                            MediaStore.Audio.Media.DATA,
                            MediaStore.Audio.Media.ALBUM,
                            MediaStore.Audio.Media.DISPLAY_NAME,
                            MediaStore.Audio.Media.ARTIST,
                            MediaStore.Audio.Media.SIZE,
                            MediaStore.Audio.Media.DURATION,
                            MediaStore.Audio.Media.TRACK,
                            MediaStore.Audio.Media.ARTIST_ID,
                            MediaStore.Audio.Media.ALBUM_ID,
                            MediaStore.Audio.Media.ALBUM_KEY,
                            MediaStore.Audio.Media.BOOKMARK,
                            MediaStore.Audio.Media.DATE_ADDED,
                            MediaStore.Audio.Media.COMPOSER,
                            MediaStore.Audio.Media.TITLE,
                    }, null, null, null);

                    if (cursor == null) {
                        return;
                    }
                    while (cursor.moveToNext()) {
                        String data = cursor.getString(0);
                        String displayName = cursor.getString(1);
                        String albumName = cursor.getString(2);
                        String artist = cursor.getString(3);
                        float size = cursor.getInt(4) / 1024f / 1024f;
                        int duration = cursor.getInt(5);
                        int track = cursor.getInt(6);
                        int artist_id = cursor.getInt(7);
                        int albumId = cursor.getInt(8);
                        String albumKey = cursor.getString(9);
                        int bookMark = cursor.getInt(10);
                        int dateAdded = cursor.getInt(11);
                        String composer = cursor.getString(12);
                        String title = cursor.getString(13);


                        Music music = new Music(duration, track, artist_id, albumId, albumKey, bookMark, dateAdded, composer, title, data, displayName, albumName, artist, size);
                        music.save();
                        Log.d(TAG, music.toString() + "\n");
                        musics.add(music);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            musicMyAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }).start();
        }
    }


    private ListView listView;


    private TextView musicName;

    private Button up, down, pauseOrPlay;


    private MyAdapter<Music> musicMyAdapter;

    private void initView() {

        listView = findViewById(R.id.listView);

        musicMyAdapter = new MyAdapter<Music>((ArrayList<Music>) musics, R.layout.listview_music_list) {
            @Override
            public void bindView(ViewHolder holder, Music obj) {
                holder.setText(R.id.artist, obj.getArtist());
                holder.setText(R.id.title, obj.getTitle());

                /**
                 * 下面的代码出现问题,尽管自己使用了异步,但是程序依旧会被卡住
                 */
//                MusicBitmapIndex.getInstance(MusicListActivity.this).fetchBitmapAsync(Uri.fromFile(new File(obj.getDataUrl())), holder, R.id.albumImg);
            }

            @Override
            public int getCount() {
                return musics.size();
            }
        };

        listView.setAdapter(musicMyAdapter);

        listView.setOnItemClickListener(this);


        musicName = findViewById(R.id.musicName);
        up = findViewById(R.id.up);
        down = findViewById(R.id.down);
        pauseOrPlay = findViewById(R.id.pauseOrPlay);

        up.setOnClickListener((v) -> {
            myBinder.upMusic();
        });

        down.setOnClickListener((v) -> {
            myBinder.downMusic();
        });

        pauseOrPlay.setOnClickListener((v) -> {
            myBinder.pauseOrPlayMusic();
        });


    }


    PlayService.MyBinder myBinder;

    boolean isBind = false;


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Music music = musics.get(position);

        if (!isBind) {
            //绑定
            Intent intent = new Intent(this, PlayService.class);
            isBind = !isBind;
            bindService(intent, new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    myBinder = (PlayService.MyBinder) service;
                    myBinder.setDataSources(musics);
                    myBinder.playMusic(music.getDataUrl());
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {

                }
            }, Context.BIND_AUTO_CREATE);

            startService(intent);

        } else {
            myBinder.playMusic(music.getDataUrl());
        }
        musicName.setText(music.getTitle());
    }
}
