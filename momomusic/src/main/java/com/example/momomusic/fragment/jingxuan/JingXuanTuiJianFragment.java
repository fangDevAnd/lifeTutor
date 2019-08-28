package com.example.momomusic.fragment.jingxuan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.momomusic.R;
import com.example.momomusic.activity.ui.JingXuanTuiJianView;
import com.example.momomusic.fragment.BaseFragment;
import com.example.momomusic.precenter.JingXuanTuiJianPresenter;
import com.example.momomusic.view.BannerFlip;
import com.example.momomusic.view.HorizonalScrollMusicView;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;

public class JingXuanTuiJianFragment extends BaseFragment<JingXuanTuiJianView, JingXuanTuiJianPresenter> {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_jingxuan_tuijian, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @BindView(R.id.banner)
    public BannerFlip bannerFlip;


    @BindView(R.id.musicList)
    HorizonalScrollMusicView hsmv;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Integer[] value = {
                R.drawable.music_banner,
                R.drawable.music_banner1,
                R.drawable.music_banner2,
                R.drawable.music_banner3,
                R.drawable.music_banner4,
                R.drawable.music_banner5
        };
        bannerFlip.setImageUrl(Arrays.asList(value));
        bannerFlip.setBannerHeight(600);
        bannerFlip.startAutoRoll(2000);


        List<HorizonalScrollMusicView.Album> albumList = new ArrayList<>();
//        albumList.add(new HorizonalScrollMusicView.Album())
        hsmv.setDataSource(null);

    }

    @Override
    public JingXuanTuiJianPresenter createPresenter() {
        return null;
    }

    @Override
    public JingXuanTuiJianView createView() {
        return null;
    }

    @Override
    protected void loadData() {
        Logger.d("开始加载数据  JingXuanTuiJianFragment");
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
