package com.example.momomusic.fragment.local;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.momomusic.R;
import com.example.momomusic.fragment.ParentFragment;
import com.example.momomusic.model.Music;
import com.example.momomusic.servie.AnimationControllService;
import com.example.momomusic.servie.ChineseCharacterUtil;
import com.example.momomusic.servie.LocalMusicIndexUtil;
import com.example.momomusic.tool.Tools;
import com.example.momomusic.tool.UiThread;

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

public class LocalMUsicGSFragment extends ParentFragment implements AdapterView.OnItemClickListener {


    private static final String TAG = "test";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_localmusic_gs, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @BindView(R.id.singerListView)
    ListView singerListView;

    private List<Music> musics = new ArrayList<>();

    private LocalMusicIndexUtil localMusicIndexUtil;

    private ListAdapter listAdapter;

    /**
     * 存放的是标题
     */
    private List<String> titles;

    /**
     * 存放的是标题对应的music,当然里面保存了歌手的信息
     */
    private HashMap<String, List<Music>> mapping;

    private List<Music> musicTitle;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titles = new ArrayList<>();
        mapping = new HashMap<>();
        musicTitle = new ArrayList<>();

        listAdapter = new ListAdapter();
        singerListView.setAdapter(listAdapter);
        singerListView.setLayoutAnimation(AnimationControllService.setLayoutAnim(R.anim.anim_item, 0.2f, LayoutAnimationController.ORDER_NORMAL, getContext()));
        singerListView.setOnItemClickListener(this);
    }

    @Override
    protected void loadData() {
        musics = DataSupport.findAll(Music.class);
        if (musics.size() == 0) {
            localMusicIndexUtil = LocalMusicIndexUtil.getInstance();
            localMusicIndexUtil.setMusicScaleListener(new LocalMusicIndexUtil.MusicScaleListener() {
                @Override
                public void scaling(Music music) {
                    music.save();
                    musics.add(music);
                }

                @Override
                public void scaleComplate() {
                    UiThread.getUiThread().post(new Runnable() {
                        @Override
                        public void run() {
                            prepareData();
                        }
                    });
                }
            });
            localMusicIndexUtil.indexLocalMusicI(getActivity());
        } else {
            prepareData();
        }

    }


    private void prepareData() {
        //添加字母匹配
        for (int i = 0; i < musics.size(); i++) {

            String artist = musics.get(i).getArtist();

            String value = ChineseCharacterUtil.convertHanzi2Pinyin(artist, false).substring(0, 1);
            if (!titles.contains(value)) {
                titles.add(value);
                List<Music> sortMusics = new ArrayList<>();
                sortMusics.add(musics.get(i));
                mapping.put(value, sortMusics);
            } else {
                mapping.get(value).add(musics.get(i));
            }
        }


        for (int i = 0; i < titles.size(); i++) {
            List<Music> music = mapping.get(titles.get(i));
            musicTitle.add(new Music(titles.get(i)));
            musicTitle.addAll(music);
        }
        mapping = null;
        titles = null;
        musics = null;

        for (Music music : musicTitle) {
            Log.d(TAG, music.toString());
        }
        listAdapter.notifyDataSetChanged();
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

        Music music = (Music) listAdapter.getItem(position);
        if (music.getPinyin() == null) {//代表点击的是实际的歌曲  ---->跳转到LocalMusicSingerFragment   传递的歌手的歌手名称.我们通过这个进行数据库的检索
            Bundle bundle = new Bundle();
            bundle.putString(LocalMusicSingerFragment.ARTIST, music.getArtist());
            Tools.startActivity(getActivity(), LocalMusicSingerFragment.class, bundle);
            return;
        }

    }


    /**
     * 带字幕的listVIew的适配器
     */
    class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return musicTitle.size();
        }

        @Override
        public Object getItem(int position) {
            return musicTitle.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHolder myViewHolder;

            Music music = musicTitle.get(position);

            if (convertView != null) {
                myViewHolder = (MyViewHolder) convertView.getTag();
            } else {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item, null);
                myViewHolder = new MyViewHolder();
                myViewHolder.name = convertView.findViewById(R.id.name);
                myViewHolder.title = convertView.findViewById(R.id.title);
                myViewHolder.touxiang = convertView.findViewById(R.id.touxiang);
                myViewHolder.down = convertView.findViewById(R.id.down);
                convertView.setTag(myViewHolder);
            }


            if (music.getPinyin() != null) {//代表是字母标题
                myViewHolder.down.setVisibility(View.INVISIBLE);
                myViewHolder.name.setVisibility(View.INVISIBLE);
                myViewHolder.touxiang.setVisibility(View.INVISIBLE);
                myViewHolder.title.setVisibility(View.VISIBLE);
                myViewHolder.title.setText(music.getPinyin());

            } else {//代表的是显示列表
                myViewHolder.down.setVisibility(View.VISIBLE);
                myViewHolder.name.setVisibility(View.VISIBLE);
                myViewHolder.touxiang.setVisibility(View.VISIBLE);
                myViewHolder.title.setVisibility(View.INVISIBLE);
                myViewHolder.name.setText(music.getArtist());
            }
            return convertView;
        }


        class MyViewHolder {
            ImageView touxiang;
            TextView name;
            TextView title;
            ImageView down;
        }
    }


}
