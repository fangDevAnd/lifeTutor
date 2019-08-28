package com.example.momomusic.fragment.local;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.momomusic.R;
import com.example.momomusic.adapter.MyAdapter;
import com.example.momomusic.fragment.ParentFragment;
import com.example.momomusic.model.Music;
import com.example.momomusic.servie.PlayService;
import com.orhanobut.logger.Logger;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;

public class LocalMusicSingerGQFragment extends ParentFragment implements AdapterView.OnItemClickListener {


    @BindView(R.id.listView)
    ListView listView;

    private MyAdapter<Music> musicMyAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        musicMyAdapter = new MyAdapter<Music>((ArrayList<Music>) musics, R.layout.listview_music_list) {
            @Override
            public void bindView(ViewHolder holder, Music obj) {
                holder.setText(R.id.musicName, obj.getTitle());
                holder.setText(R.id.singerAndAlbumName, obj.getArtist() + " | " + obj.getAlbumName());
                holder.setText(R.id.title, obj.getTitle());
                holder.setOnClickListener(R.id.menu, (v) -> {

                });
            }

            @Override
            public int getCount() {
                return musics.size();
            }
        };

        listView.setAdapter(musicMyAdapter);

        listView.setOnItemClickListener(this);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_localmusic_singer_gq, null);
        ButterKnife.bind(this, view);
        return view;
    }


    private List<Music> musics;

    @Override
    protected void loadData() {

        String artist = getMyActivity().getBundle().getString(LocalMusicSingerFragment.ARTIST);

        musics = DataSupport.where("artist=?", artist).find(Music.class);

        Logger.d("当前music.size" + musics.size());

    }

    @Override
    public void onError(IOException e, String what) {

    }

    @Override
    public void onSucess(Response response, String what, String... backData) throws IOException {

    }

    @Override
    public Class getClassName() {
        return null;
    }

    private PlayService.MyBinder myBinder;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Music music = musics.get(position);
        //绑定
        Intent intent = new Intent(getActivity(), PlayService.class);

        getActivity().bindService(intent, getMyActivity().conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                myBinder = (PlayService.MyBinder) service;
                myBinder.setDataSources(musics);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        }, Context.BIND_AUTO_CREATE);
        /**
         * 这里我们看出,startService通过这个方式直接进行交互,
         */
        intent.putExtra(PlayService.DATA, music.getDataUrl());
        intent.putExtra(PlayService.ACTION, PlayService.WITH_DATA_PLAY);
        getActivity().startService(intent);
    }
}
