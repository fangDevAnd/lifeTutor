package com.example.momomusic.fragment.shiping;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.momomusic.R;
import com.example.momomusic.adapter.MyCommandAdapter;
import com.example.momomusic.fragment.ParentFragment;
import com.example.momomusic.model.Video;
import com.example.momomusic.model.ZhiBoHouse;

import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;

public class ShiPinBaseFragment extends ParentFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shipin_base, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private MyCommandAdapter<Video> myCommandAdapter;

    private ArrayList<Video> videos = new ArrayList<>();


    private int page;

    private int size = 10;

    private String what = "12";


    private String url = "http://www.baidu.com?page" + page + "&size=" + size;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        videos.add(new Video());
        videos.add(new Video());
        videos.add(new Video());
        videos.add(new Video());
        videos.add(new Video());
        videos.add(new Video());
        videos.add(new Video());

        /**
         会照成界面的卡顿
         下面我们提出新的实现方案

         如果用户点击了里面的项，就动态的替换里面的video，并播放，因为在播放的情况下，用户一般不会滑动，当然也是存在滑动的
         当在播放的情况下，如果产生了滑动，同时当前播放的界面的视频不可见的情况下，就去释放掉内存

         <!--<VideoView-->
         <!--android:id="@+id/video"-->
         <!--android:layout_width="match_parent"-->
         <!--android:layout_height="match_parent" />-->
         **/


        myCommandAdapter = new MyCommandAdapter<Video>(getContext(), R.layout.recyclerview_shipin_base, videos) {
            @Override
            public void bind(MyViewHolder holder, Video video) {

//                holder.setClickListener(R.id.);

            }
        };
        recyclerView.setAdapter(myCommandAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(llm);
    }

    @Override
    protected void loadData() {
        loadData(url, what);
    }

    @Override
    public void onError(IOException e, String what) {

    }

    @Override
    public void onSucess(Response response, String what, String... backData) throws IOException {
        if (what.equals(this.what)) {

        }
    }

    @Override
    public Class getClassName() {
        return this.getClass();
    }
}
