package com.example.momomusic.fragment.person;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.momomusic.R;
import com.example.momomusic.activity.ui.PersonalCenterView;
import com.example.momomusic.fragment.BaseFragment;
import com.example.momomusic.precenter.PersonalCnterPresenter;
import com.example.momomusic.tool.Tools;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

public class PersonalCenterFragment extends BaseFragment<PersonalCenterView, PersonalCnterPresenter> {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_center, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @BindView(R.id.gerenzhuye)
    TextView gerenzhuye;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public PersonalCnterPresenter createPresenter() {
        return null;
    }

    @Override
    public PersonalCenterView createView() {
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

    @OnClick({R.id.gerenzhuye})
    public void OnClick(View view) {
        Tools.startActivity(getActivity(), PersonalIndexPageFragment.class);
    }


}
