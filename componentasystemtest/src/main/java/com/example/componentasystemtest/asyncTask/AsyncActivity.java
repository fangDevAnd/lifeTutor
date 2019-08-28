package com.example.componentasystemtest.asyncTask;

import android.Manifest;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.componentasystemtest.R;
import com.example.componentasystemtest.musicPlay.simple2.Music;

import java.util.ArrayList;
import java.util.List;

public class AsyncActivity extends AppCompatActivity {

    private static final String TAG = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);

        AsyncTask.handlerThread1Process(this);


        findViewById(R.id.newCall).setOnClickListener((v) -> {


            new android.os.AsyncTask<String, Integer, Character>() {

                @Override
                protected void onPostExecute(Character character) {
                    super.onPostExecute(character);
                }

                @Override
                protected void onProgressUpdate(Integer... values) {
                    super.onProgressUpdate(values);
                }

                @Override
                protected Character doInBackground(String... strings) {


                    /**
                     * 下面执行的语句，会自动调用onProgressUpdate
                     */
                    publishProgress(12);

                    return null;
                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }
            };

        });


        findViewById(R.id.button).setOnClickListener((v) -> {
//            AsyncTask.handler.sendEmptyMessage(20);

            AsyncTask.asyncQueryHandlerProcess(this);
        });

        findViewById(R.id.query).setOnClickListener((v) -> {
            Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String[]{
                    MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.DISPLAY_NAME,
                    MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.SIZE, MediaStore.Audio.Media.ALBUM,
            }, null, null, null);

            List<Music> musics = new ArrayList<>();
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                String albumName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                int size = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE)) / 1024;
                Music music = new Music(title, data, displayName, albumName, artist, size);
                musics.add(music);
                Log.d(TAG, music.toString());
            }

            MediaPlayer mediaPlayer = new MediaPlayer();

            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(musics.get(0).getDataUrl());
                mediaPlayer.prepare();
                mediaPlayer.start();


            } catch (Exception e) {
                e.printStackTrace();
            }


        });


        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 *
                 * 我们使用asyncTask默认是在主线程进行创建的，根据对源码的了解，我们发现，如果在子线程创建handler，将创建一个Handler传递给AsyncTask
                 *遗憾的是，AsyncTask的构造函数对外是隐藏的，
                 * 但是通过源码的分析，我们在子线程创建AsyncTask，在内部会使用Handler，
                 * 这个Handler同样使用的是MainLooper，所以消息同样能够回调到主线程中
                 *
                 *
                 */

                new Thread(new Runnable() {
                    @Override
                    public void run() {

//
//                        Looper.prepare();
//
//                        handler = new Handler();
//                        Looper.loop();

                        new android.os.AsyncTask<Void, Integer, String>() {

                            @Override
                            protected String doInBackground(Void... voids) {
                                SystemClock.sleep(2000);
                                return "你好";
                            }

                            @Override
                            protected void onPostExecute(String s) {
                                Toast.makeText(AsyncActivity.this, "执行结束", Toast.LENGTH_SHORT).show();
                            }
                        }.execute();
                    }
                }).start();
            }
        });
    }


    Handler handler;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
