package com.example.momomusic.fragment.local;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.momomusic.R;
import com.example.momomusic.exception.ParamNotBindException;
import com.example.momomusic.fragment.ParentFragment;
import com.example.momomusic.view.Adapter.MyFragmentPageAdapter;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * 这个fragment是歌手页面的fragment  里面放置了两个fragment
 * <p>
 * 1:  LocalMusicSingerMusicFragment
 * 2.  LocalMusicSingerAlbumFragment
 */
public class LocalMusicSingerFragment extends ParentFragment {


    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    public static final String ARTIST = "artist";

    private List<Fragment> fragments;

    private String[] tabTitle;

    private MyFragmentPageAdapter myPageAdapter;

    private int defaultSelectIndex;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        String artist = getMyActivity().getBundle().getString(ARTIST);

        if (artist == null) {
            try {
                throw new ParamNotBindException("跳转到" + this.getClass().getSimpleName() + "没有绑定参数异常");
            } catch (ParamNotBindException e) {
                e.printStackTrace();
            }
        }

        LocalMusicSingerGQFragment lmsgqf = new LocalMusicSingerGQFragment();
        LocalMusicSingerZJFragment lmszjf = new LocalMusicSingerZJFragment();

        fragments = new ArrayList<>();
        fragments.add(lmsgqf);
        fragments.add(lmszjf);//精选


        tabTitle = getResources().getStringArray(R.array.singer_zhuanlan);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        /**
         * 记住，这里只能使用getChildFragmentManager()
         */
        myPageAdapter = new MyFragmentPageAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(myPageAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(defaultSelectIndex);
        for (int i = 0; i < tabTitle.length; i++) {
            tabLayout.addTab(tabLayout.newTab());
        }


        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabTitle.length; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setText(tabTitle[i]);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_music_singer, null);
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
