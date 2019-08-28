package com.example.momomusic.fragment;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.momomusic.R;
import com.example.momomusic.model.Music;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * 修改音乐信息的Fragment
 */
public class ModifyMusicInfoFragment extends ParentFragment {

    /**
     * 存放的是传递过来的数据  是一个music的实例
     */
    public static final String SOURCE = "source";


    private Music music;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_music_info, null);
        ButterKnife.bind(this, view);

        return view;
    }


    @BindView(R.id.musicNameEdit)
    EditText musicNameEdit;

    @BindView(R.id.artistEdit)
    EditText artistEdit;

    @BindView(R.id.albumNameEdit)
    EditText albumNameEdit;


    @Override
    protected void loadData() {

        Bundle bundle = getMyActivity().getBundle();

        music = bundle.getParcelable(SOURCE);

        String albumName = music.getAlbumName();
        String artistName = music.getArtist();
        String musicName = music.getTitle();

        albumName = TextUtils.isEmpty(albumName) ? "" : albumName;
        artistName = TextUtils.isEmpty(artistName) ? "" : artistName;
        musicName = TextUtils.isEmpty(musicName) ? "" : musicName;


        musicNameEdit.setText(musicName);
        artistEdit.setText(artistName);
        albumNameEdit.setText(albumName);


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

    @OnClick({R.id.cancel, R.id.save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                //取消操作
                getMyActivity().finish();
                break;

            case R.id.save:

                if (TextUtils.isEmpty(musicNameEdit.getText().toString().trim())
                        || TextUtils.isEmpty(artistEdit.getText().toString().trim())
                        || TextUtils.isEmpty(albumNameEdit.getText().toString().trim())) {
                    Toast.makeText(getContext(), "请填写完整的信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                music.setTitle(musicNameEdit.getText().toString().trim());
                music.setArtist(artistEdit.getText().toString().trim());
                music.setAlbumName(albumNameEdit.getText().toString().trim());
                music.save();
                getMyActivity().finish();
                break;
        }
    }

}
