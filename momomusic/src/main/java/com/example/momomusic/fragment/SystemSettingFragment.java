package com.example.momomusic.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.example.momomusic.R;
import com.example.momomusic.model.Music;
import com.example.momomusic.servie.SystemSettingService;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * 系统设置的fragment 需要传递的是Music的实例
 */
public class SystemSettingFragment extends ParentFragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_system_setting, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @BindView(R.id.autoEntryPlayPageSwitch)
    Switch autoEntryPlayPageSwitch;//自动进入播放界面

    @BindView(R.id.desktopLyricSwitch)
    Switch desktopLyricSwitch;//桌面歌词显示

    @BindView(R.id.personalConteRecomSwit)
    Switch personalConteRecomSwit;//个性化内容推荐

    @BindView(R.id.intellRicPhoOptimiSwit)
    Switch intellRicPhoOptimiSwit;//智能词图优化

    @BindView(R.id.notiSet)
    Button notiSet;//消息设置

    @BindView(R.id.asLisAsDown)
    Switch asLisAsDown;//边听边下载

    @BindView(R.id.collectToDown)
    Switch collectToDown;//收藏就下载


    @BindView(R.id.musicAutoClose)
    Switch musicAutoClose;//音乐定时自动关闭

    @BindView(R.id.onlyWlanDown)
    Switch onlyWlanDown;//只有在wlan下下载

    @BindView(R.id.bigOrSmallFilter)
    Switch bigOrSmallFilter;//文件大小过滤

    @BindView(R.id.timeLengthFilter)
    Switch timeLengthFilter;//音乐时长过滤

    @BindView(R.id.folderFilter)
    RelativeLayout folderFilter;//文件夹过滤

    @BindView(R.id.policy)
    Button policy;//隐私政策
    /**
     *
     */
    private SharedPreferences sp;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //读取配置文件

        sp = SystemSettingService.getInstace(getContext()).getSharedPreferences();
        sp.getBoolean(SystemSettingService.AUTO_ENTRY_PLAY_PAGE, SystemSettingService.DEFAULT_BOOL);
        sp.getBoolean(SystemSettingService.DESKTOP_RLC, SystemSettingService.DEFAULT_BOOL);
        sp.getBoolean(SystemSettingService.PERSONAL_RECOMMEND, SystemSettingService.DEFAULT_BOOL);
        sp.getBoolean(SystemSettingService.INTELLIGENCE_OPTIMIZATION, SystemSettingService.DEFAULT_BOOL);

        //这个不在这里读取，而是在一个新的界面
//        sp.getBoolean(SystemSettingService.NOTIFICATION_TIP, SystemSettingService.DEFAULT_BOOL);
        sp.getBoolean(SystemSettingService.LISTENER_DOWNLOAD, SystemSettingService.DEFAULT_BOOL);
        sp.getBoolean(SystemSettingService.SINGLE_MUSIC_COLLECT_DOWNLOAD, SystemSettingService.DEFAULT_BOOL);
        sp.getBoolean(SystemSettingService.AUTO_CLOSE, SystemSettingService.DEFAULT_BOOL);
        sp.getBoolean(SystemSettingService.WIFI_AUTO_DOWNLOAD, SystemSettingService.DEFAULT_BOOL);
        sp.getBoolean(SystemSettingService.BIG_OR_SMALL_FILETER, SystemSettingService.DEFAULT_BOOL);
        sp.getBoolean(SystemSettingService.TIME_LENGTH_FILTER, SystemSettingService.DEFAULT_BOOL);
        sp.getBoolean(SystemSettingService.FOLDER_FILTER, SystemSettingService.DEFAULT_BOOL);

    }


    @OnClick({R.id.autoEntryPlayPage, R.id.desktopLyricSwitch,
            R.id.personalConteRecomSwit, R.id.intellRicPhoOptimiSwit,
            R.id.notiSet, R.id.asLisAsDown,
            R.id.collectToDown, R.id.musicAutoClose, R.id.onlyWlanDown,
            R.id.bigOrSmallFilter, R.id.timeLengthFilter, R.id.folderFilter,
            R.id.policy
    })
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.autoEntryPlayPage:

                /**
                 *
                 * 实现原理  在打开应用的时候通过 ActivityManager实现
                 * 如果开启了这个功能，就迅速跳转到播放的界面上面，
                 */


                break;

            case R.id.desktopLyricSwitch:

                break;

            case R.id.personalConteRecomSwit:

                break;

            case R.id.intellRicPhoOptimiSwit:

                break;

            case R.id.notiSet:

                break;

            case R.id.asLisAsDown:

                break;

            case R.id.collectToDown:

                break;
            case R.id.musicAutoClose:
                break;
            case R.id.onlyWlanDown:

                break;

            case R.id.bigOrSmallFilter:

                break;

            case R.id.timeLengthFilter:

                break;

            case R.id.folderFilter:

                break;

            case R.id.policy:

                break;
        }

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
