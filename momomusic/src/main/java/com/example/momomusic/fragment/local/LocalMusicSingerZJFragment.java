package com.example.momomusic.fragment.local;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.momomusic.R;
import com.example.momomusic.adapter.MyCommandAdapter;
import com.example.momomusic.fragment.ParentFragment;
import com.example.momomusic.model.Music;
import com.example.momomusic.tool.Tools;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;

public class LocalMusicSingerZJFragment extends ParentFragment {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private MyCommandAdapter<MyZJ> myCommandAdapter;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_localmusic_singer_zj, null);
        ButterKnife.bind(this, view);
        return view;
    }

    private List<Music> musics;
    private List<MyZJ> albumNames;
    private String artist;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        albumNames = new ArrayList<>();
        /**
         *
         * 使用的方式如下
         * @param context 上下文
         * @param int layout  布局文件
         * @ 数据
         *
         * 传递的参数有
         */
        myCommandAdapter = new MyCommandAdapter<MyZJ>(getActivity(), R.layout.recyclerview_album, (ArrayList<MyZJ>) albumNames) {
            @Override
            public void bind(MyViewHolder holder, MyZJ s) {
                if (s.albumName1 != null) {
                    holder.setText(R.id.albumName, s.albumName1);
                    holder.setText(R.id.singer, artist);
                    holder.setVisibility(R.id.itemAlbum1, View.VISIBLE);
                    holder.setClickListener(R.id.itemAlbum1, (v) -> {
                        Bundle bundle = new Bundle();
//                        bundle.putString(LocalMusicSingerZJMusicFragment.ALBUM, s.albumName1);

                        HashMap<String, String[]> map = new HashMap<>();
                        map.put("album=?", new String[]{s.albumName1});
                        bundle.putSerializable(LocalMusicSingerZJMusicFragment.CONDITIONS, map);
                        getMyActivity().setBundle(bundle);
                        Tools.startActivity(getActivity(), LocalMusicSingerZJMusicFragment.class, bundle);
                    });
                }
                if (s.albumName2 != null) {
                    holder.setText(R.id.albumName1, s.albumName2);
                    holder.setText(R.id.singer, artist);
                    holder.setVisibility(R.id.itemAlbum2, View.VISIBLE);
                    holder.setClickListener(R.id.itemAlbum2, (v) -> {
                        Bundle bundle = new Bundle();
//                        bundle.putString(LocalMusicSingerZJMusicFragment.ALBUM, s.albumName2);
                        HashMap<String, String[]> map = new HashMap<>();
                        map.put("album=?", new String[]{s.albumName2});
                        bundle.putSerializable(LocalMusicSingerZJMusicFragment.CONDITIONS, map);
                        getMyActivity().setBundle(bundle);
                        Tools.startActivity(getActivity(), LocalMusicSingerZJMusicFragment.class, bundle);
                    });
                }


            }
        };

        recyclerView.setAdapter(myCommandAdapter);

        /**
         * recyclerView在设置的过程中一定要添加布局管理器
         */
        LinearLayoutManager lm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(lm);


        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    protected void loadData() {

        artist = getMyActivity().getBundle().getString(LocalMusicSingerFragment.ARTIST);

        Cursor cursor = DataSupport.findBySQL("select DISTINCT albumName from music where artist=?", artist);


        int rows = cursor.getCount() % 2 == 0 ? cursor.getCount() / 2 : cursor.getCount() + 1;

        for (int i = 0; i < rows; i++) {
            MyZJ myZJ = new MyZJ();
            while (cursor.moveToNext()) {//0
                String albumName = cursor.getString(0);
                if (myZJ.albumName1 != null) {
                    myZJ.albumName2 = albumName;
                } else {
                    myZJ.albumName1 = albumName;
                }
                if (myZJ.albumName2 != null) {
                    break;
                }
            }
            albumNames.add(myZJ);
        }


        myCommandAdapter.notifyDataSetChanged();

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


    class MyZJ {
        private String albumName1;
        private String albumName2;

    }


}
