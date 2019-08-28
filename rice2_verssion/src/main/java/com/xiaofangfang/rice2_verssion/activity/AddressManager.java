package com.xiaofangfang.rice2_verssion.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.xiaofangfang.rice2_verssion.ParentActivity;
import com.xiaofangfang.rice2_verssion.R;
import com.xiaofangfang.rice2_verssion.dao.OprateDb;
import com.xiaofangfang.rice2_verssion.model.AddressMode;
import com.xiaofangfang.rice2_verssion.tool.DialogTool;
import com.xiaofangfang.rice2_verssion.tool.Looger;
import com.xiaofangfang.rice2_verssion.view.CommandBar;
import com.xiaofangfang.rice2_verssion.view.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class AddressManager extends ParentActivity {


    private OprateDb oprateDb;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.address_manager);
        oprateDb = OprateDb.getInstance(this);
        super.onCreate(savedInstanceState);
        initData();
    }


    /**
     * 用来加载数据
     */
    private void initData() {


    }


    MyAdapter<AddressMode> addressModeMyAdapter;

    List<AddressMode> addressModes;

    @Override

    public void initView() {
        addressModes = oprateDb.getAllData();

        ListView listView = findViewById(R.id.listView);

        CommandBar commandBar = findViewById(R.id.commandBar);
        commandBar.setTitle("地址管理");


        listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {

            Intent intent = getIntent();
            String value = intent.getStringExtra(SubmitCardProgress.class.getSimpleName());
            if (value != null) {

                Intent intent1 = new Intent();
                intent1.putExtra(AddressMode.class.getSimpleName(), addressModes.get(position));
                setResult(RESULT_OK, intent1);
                finish();
            }
        });

        addressModeMyAdapter = new MyAdapter<AddressMode>((ArrayList<AddressMode>) addressModes, R.layout.address_manager_sub_list) {
            @Override
            public void bindView(ViewHolder holder, AddressMode obj) {
                holder.setVisibility(R.id.selected, obj.isDefault() ? View.VISIBLE : View.INVISIBLE);
                holder.setText(R.id.name, obj.getName());
                holder.setText(R.id.tel, obj.getTel());
                holder.setText(R.id.address, obj.getAddress());
                holder.setOnClickListener(R.id.del, (v) -> {

                    DialogTool<String> dt = new DialogTool<String>() {

                        @Override
                        public void bindView(DialogTool<String> d, Dialog dialog, String... s) {
                            d.setText(R.id.title, s[0]);
                            d.setText(R.id.content, s[1]);
                            d.setClickListener(R.id.cancel, (v) -> {
                                dialog.cancel();
                            });
                            d.setClickListener(R.id.yes, (v) -> {
                                //确定删除
                                if (oprateDb.removeAddress((int) obj.getId()) == -1) {
                                    Toast.makeText(AddressManager.this, "删除出错", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                addressModes.clear();
                                List<AddressMode> addressModesInner = oprateDb.getAllData();
                                //小心这里有坑,指针的传递问题,
                                for (AddressMode addressMode : addressModesInner) {
                                    addressModes.add(addressMode);
                                }
                                //刷新数据
                                addressModeMyAdapter.notifyDataSetChanged();
                                dialog.cancel();
                            });
                        }
                    };
                    //弹出弹窗,询问是否进行删除
                    Dialog dialog = dt.getDialog(AddressManager.this, R.layout.warn_dialog_template, "删除警告", "你将要删除当前的地址,确认请继续");
                    dialog.show();
                });
                holder.setOnClickListener(R.id.edit, (v) -> {
                    //进入添加地址  ,传递数据过去
                    //使用startActivityResult启动 .如果下一个界面点击了确定,就去刷新,反之不去刷新

                    Intent intent = new Intent(AddressManager.this, AddAddress.class);
                    intent.putExtra(AddressMode.class.getSimpleName(), obj);
                    startActivity(intent);
                });

            }

            @Override
            public int getCount() {
                return addressModes.size();
            }
        };

        listView.setAdapter(addressModeMyAdapter);

        findViewById(R.id.submit).setOnClickListener((v) -> {
            Intent intent = new Intent(this, AddAddress.class);
            startActivity(intent);
        });

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        addressModes.clear();
        List<AddressMode> addressModesInner = oprateDb.getAllData();
        //小心这里有坑,指针的传递问题,
        for (AddressMode addressMode : addressModesInner) {
            addressModes.add(addressMode);
        }

        for (int i = 0; i < addressModes.size(); i++) {
            Looger.D("数据=" + addressModes.get(i));
        }
        //刷新数据
        addressModeMyAdapter.notifyDataSetChanged();
    }

}
