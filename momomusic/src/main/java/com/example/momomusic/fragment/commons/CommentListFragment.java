package com.example.momomusic.fragment.commons;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.momomusic.R;
import com.example.momomusic.activity.ui.CommentListView;
import com.example.momomusic.fragment.BaseFragment;
import com.example.momomusic.precenter.CommentListPresenter;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import okhttp3.Response;

public class CommentListFragment extends BaseFragment<CommentListView, CommentListPresenter> {

    /**
     * 是不是回复别人
     */
    public static final String IS_HUIFU = "huifu";
    /**
     * 被评论用户的key 值是一个#{{@link com.example.momomusic.model.Comment}}
     */
    public static final String COMMENT = "comment";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comment_list, null);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public CommentListPresenter createPresenter() {
        return null;
    }

    @Override
    public CommentListView createView() {
        return null;
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
