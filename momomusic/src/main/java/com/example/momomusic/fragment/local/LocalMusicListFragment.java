package com.example.momomusic.fragment.local;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.momomusic.R;
import com.example.momomusic.fragment.MusicMultipleSelectFragment;
import com.example.momomusic.fragment.ParentFragment;
import com.example.momomusic.model.Music;
import com.example.momomusic.servie.LocalMusicIndexUtil;
import com.example.momomusic.servie.PlayServiceBindService;
import com.example.momomusic.tool.Tools;
import com.example.momomusic.tool.UiThread;
import com.example.momomusic.view.Adapter.MyFragmentPageAdapter;
import com.google.android.material.tabs.TabLayout;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

public class LocalMusicListFragment extends ParentFragment {


    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    private List<Fragment> fragments;

    private String[] tabTitle;

    private MyFragmentPageAdapter myPageAdapter;

    private int defaultSelectIndex;


    @BindView(R.id.randomPlay)
    Button randomPlay;

    @BindView(R.id.randomBar)
    FrameLayout randomBar;

    @BindView(R.id.back)
    ImageButton back;


    @BindView(R.id.multiSelect)
    Button multiSelect;//跳转进入新的Fragment中


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragments = new ArrayList<>();
        fragments.add(new LocalMusicGQFragment());
        fragments.add(new LocalMUsicGSFragment());//精选
        fragments.add(new LocalMusicZJFragment());
        fragments.add(new LocalMusicWJJFragment());


        tabTitle = getResources().getStringArray(R.array.localmusic);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        /**
         * 记住，这里只能使用getChildFragmentManager()
         */
        myPageAdapter = new MyFragmentPageAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(myPageAdapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setCurrentItem(defaultSelectIndex);
        for (int i = 0; i < tabTitle.length; i++) {
            tabLayout.addTab(tabLayout.newTab());
        }


        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabTitle.length; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setText(tabTitle[i]);
        }


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        randomBar.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                    case 2:
                    case 3:
                        randomBar.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    @OnClick({R.id.multiSelect, R.id.randomPlay, R.id.back})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.multiSelect:
                Tools.startActivity(getContext(), MusicMultipleSelectFragment.class);
                break;
            case R.id.randomPlay:
                List<Music> selectedMusic = DataSupport.findAll(Music.class);

                LocalMusicIndexUtil localMusicIndexUtil;

                if (selectedMusic.size() == 0) {
                    localMusicIndexUtil = LocalMusicIndexUtil.getInstance();
                    localMusicIndexUtil.setMusicScaleListener(new LocalMusicIndexUtil.MusicScaleListener() {
                        @Override
                        public void scaling(Music music) {
                            music.save();
                            //这里需要注意的是不能添加相同的歌曲进去，会出现你问题
                            if (!selectedMusic.contains(music)) {
                                selectedMusic.add(music);
                            }
                        }

                        @Override
                        public void scaleComplate() {
                            UiThread.getUiThread().post(new Runnable() {
                                @Override
                                public void run() {
                                    //随机播放
                                    PlayServiceBindService.randomPlayFun(selectedMusic, getContext());
                                }
                            });
                        }
                    });
                    localMusicIndexUtil.indexLocalMusicI(getActivity());
                } else {
                    //随机播放
                    PlayServiceBindService.randomPlayFun(selectedMusic, getContext());
                }

                break;

            case R.id.back://返回
                getMyActivity().finish();
                break;
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.local_musiclist, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void loadData() {

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


}
