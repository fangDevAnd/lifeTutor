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
import com.example.momomusic.servie.AnimationControllService;
import com.example.momomusic.tool.Tools;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;

public class LocalMusicWJJFragment extends ParentFragment implements AdapterView.OnItemClickListener {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_localmusic_wjj, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @BindView(R.id.listView)
    ListView listView;

    private MyAdapter<String> fileMyAdapter;

    private List<String> strings;

    private Map<String, Integer> map;


    @Override
    protected void loadData() {

        map = new HashMap();
        strings = new ArrayList<>();

        Cursor cursor = DataSupport.findBySQL("select dataUrl from music");
        while (cursor.moveToNext()) {
            String path = cursor.getString(0);
            path = path.substring(0, path.lastIndexOf("/"));
            path += File.separator;
            if (strings.contains(path)) {
                int value = map.get(path);
                value++;
                map.put(path, value);
            } else {
                strings.add(path);
                map.put(path, 0);
            }
        }

        fileMyAdapter = new MyAdapter<String>((ArrayList<String>) strings, R.layout.listview_folder) {
            @Override
            public void bindView(ViewHolder holder, String obj) {
                holder.setText(R.id.count, map.get(obj) + "首歌");
                holder.setText(R.id.path, obj);
                holder.setOnClickListener(R.id.menu, (v) -> {
                });
            }

            @Override
            public int getCount() {
                return strings.size();
            }
        };

        listView.setAdapter(fileMyAdapter);
        listView.setLayoutAnimation(AnimationControllService.setLayoutAnim(R.anim.anim_item, 0.2f, LayoutAnimationController.ORDER_NORMAL, getContext()));
        listView.setOnItemClickListener(this);

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

        String path = strings.get(position);
        //在这里打开新的activity,
        Bundle bundle = new Bundle();
        bundle.putString(LocalMusicWJJMusicFragment.PATH, "com.example.momomusic.fragment.local.LocalMusicGQFragment");
        bundle.putString(LocalMusicGQFragment.FIEL_PATH, path);
        Tools.startActivity(getContext(), LocalMusicWJJMusicFragment.class, bundle);

    }
}
