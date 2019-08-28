package com.example.momomusic.fragment.local;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.momomusic.R;
import com.example.momomusic.fragment.ParentFragment;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * 这个fragment就是在播放listView里面添加了一个toolbar和一个全部播放的按钮
 */
public class LocalMusicWJJMusicFragment extends ParentFragment {

    public static final String PATH = "FRAGMENT_PATH";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_localmusic_wjj_music, null);
        ButterKnife.bind(this, view);
        return view;
    }


    FragmentTransaction ft;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        FragmentManager fm = getChildFragmentManager();
        ft = fm.beginTransaction();
        Bundle bundle = getMyActivity().getBundle();
        String path = bundle.getString(PATH);
        Class fragmentClass = null;
        try {
            fragmentClass = Class.forName(path);
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            ft.replace(R.id.replaceArea, fragment);
            ft.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onViewCreated(view, savedInstanceState);
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
