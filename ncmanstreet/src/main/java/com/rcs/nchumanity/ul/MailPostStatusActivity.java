package com.rcs.nchumanity.ul;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.rcs.nchumanity.R;
import com.rcs.nchumanity.entity.complexModel.ComplexModelSet;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MailPostStatusActivity extends ParentActivity {


    public static final String DATA = "data";


    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.mobilePhone)
    TextView mobilePhone;

    @BindView(R.id.address)
    TextView address;

    @BindView(R.id.expressNo)
    TextView expressNo;

    @BindView(R.id.expressCompany)
    TextView expressCompany;

    @BindView(R.id.expressStatus)
    TextView expressStauts;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mailport_status);
        ButterKnife.bind(this);

        ComplexModelSet.PostInfo postInfo = (ComplexModelSet.PostInfo) getIntent().getSerializableExtra(DATA);

        name.setText(postInfo.name);
        mobilePhone.setText(postInfo.mobilephone);
        address.setText(postInfo.address);
        expressNo.setText(postInfo.expressNo);
        expressCompany.setText(postInfo.expressCompany);
        expressStauts.setText(postInfo.expressStatus);

    }


}
