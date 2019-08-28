package com.example.momomusic.fragment.local;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.momomusic.R;
import com.example.momomusic.adapter.MyAdapter;
import com.example.momomusic.fragment.ParentFragment;
import com.example.momomusic.model.Music;
import com.example.momomusic.servie.AnimationControllService;
import com.example.momomusic.tool.Tools;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * 本地音乐的专辑列表
 */
public class LocalMusicZJFragment extends ParentFragment implements AdapterView.OnItemClickListener {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_localmusic_zj, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @BindView(R.id.listView)
    ListView listView;

    private MyAdapter<Music> myAdapter;

    private List<Music> musics;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        musics = new ArrayList<>();
        myAdapter = new MyAdapter<Music>((ArrayList<Music>) musics, R.layout.listview_album_item) {
            @Override
            public void bindView(ViewHolder holder, Music obj) {
                holder.setText(R.id.title, obj.getTitle());
                holder.setText(R.id.singer, obj.getArtist());
                holder.setText(R.id.count, obj.getCount() + "首歌");
                holder.setOnClickListener(R.id.menu, (v) -> {

                });
            }

            @Override
            public int getCount() {
                return musics.size();
            }
        };

        listView.setAdapter(myAdapter);
        listView.setLayoutAnimation(AnimationControllService.setLayoutAnim(R.anim.anim_item, 0.2f, LayoutAnimationController.ORDER_NORMAL, getContext()));
        listView.setOnItemClickListener(this);


        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void loadData() {

        Cursor cursor = DataSupport.findBySQL("select DISTINCT albumName,artist from music");
        while (cursor.moveToNext()) {
            String album = cursor.getString(0);
            String artist = cursor.getString(1);

            musics.add(new Music(album, artist, 00));
        }


        for (int i = 0; i < musics.size(); i++) {
            int count = DataSupport.where("albumName=?", musics.get(i).getAlbumName()).count(Music.class);
            musics.get(i).setCount(count);
        }
        myAdapter.notifyDataSetChanged();


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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Music music = musics.get(position);
        Bundle bundle = new Bundle();


        HashMap<String, String[]> map = new HashMap<>();
        map.put("albumName=?", new String[]{music.getAlbumName()});

//        bundle.putString(LocalMusicSingerZJMusicFragment.ALBUM, music.getAlbumName());
        bundle.putSerializable(LocalMusicSingerZJMusicFragment.CONDITIONS, map);
        bundle.putString(LocalMusicSingerZJMusicFragment.SOURCE, Music.class.getSimpleName());
        getMyActivity().setBundle(bundle);
        Tools.startActivity(getActivity(), LocalMusicSingerZJMusicFragment.class, bundle);
    }
}
