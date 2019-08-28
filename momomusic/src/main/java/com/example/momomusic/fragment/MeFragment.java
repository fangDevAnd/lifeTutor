package com.example.momomusic.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.momomusic.R;
import com.example.momomusic.activity.ui.MeFragmentView;
import com.example.momomusic.fragment.local.LocalMusicListFragment;
import com.example.momomusic.fragment.local.LocalMusicSingerZJMusicFragment;
import com.example.momomusic.model.Music;
import com.example.momomusic.model.NearPlay;
import com.example.momomusic.precenter.MeFragmentPresenter;
import com.example.momomusic.servie.LocalMusicIndexUtil;
import com.example.momomusic.servie.PlayServiceBindService;
import com.example.momomusic.tool.Tools;
import com.example.momomusic.tool.UiThread;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

public class MeFragment extends BaseFragment<MeFragmentView, MeFragmentPresenter<MeFragmentView>> implements MeFragmentView {


    private static final String TAG = "test";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @BindView(R.id.localMusic)
    Button localMusic;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public MeFragmentView createView() {
        return this;
    }


    @OnClick({R.id.localMusic, R.id.suiJiBoFang, R.id.zuiJinBoFang})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.localMusic://本地音乐
                Log.d(TAG, "onClick: ");
                Tools.startActivity(getActivity(), LocalMusicListFragment.class);
                break;
            case R.id.suiJiBoFang://随机播放

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
                                    PlayServiceBindService.randomPlayFun(selectedMusic, getContext());
                                }
                            });
                        }
                    });
                    localMusicIndexUtil.indexLocalMusicI(getActivity());
                } else {
                    PlayServiceBindService.randomPlayFun(selectedMusic, getContext());
                }
                break;

            case R.id.zuiJinBoFang://最近播放

                Bundle bundle = new Bundle();
                bundle.putSerializable(LocalMusicSingerZJMusicFragment.SOURCE, NearPlay.class.getSimpleName());
                bundle.putSerializable(LocalMusicSingerZJMusicFragment.CONDITIONS, null);
                Tools.startActivity(getContext(), LocalMusicSingerZJMusicFragment.class, bundle);
                break;
        }
    }


    /**
     * 加载数据
     */
    @Override
    protected void loadData() {
        Log.d(TAG, "loadData: MeFragment");
    }


    @Override
    public MeFragmentPresenter<MeFragmentView> createPresenter() {
        return null;
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
