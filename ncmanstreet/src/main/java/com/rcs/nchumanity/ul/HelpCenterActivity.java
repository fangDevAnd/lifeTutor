package com.rcs.nchumanity.ul;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.Nullable;

import com.rcs.nchumanity.R;
import com.rcs.nchumanity.adapter.ListViewCommonsAdapter;
import com.rcs.nchumanity.entity.model.SpecificInfo;
import com.rcs.nchumanity.ul.list.ComplexListActivity;

/**
 * 帮助中心
 */
public class HelpCenterActivity extends ComplexListActivity<SpecificInfo> {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    @Override
    protected void bindViewValue(ListViewCommonsAdapter.ViewHolder holder, SpecificInfo obj) {

    }

    @Override
    protected void itemClick(AdapterView<?> parent, View view, int position, long id, SpecificInfo item) {

    }

    @Override
    protected int getLayout() {
        return R.layout.item_help;
    }
}
