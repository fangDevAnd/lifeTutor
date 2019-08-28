package com.xiaofangfang.rice2_verssion.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaofangfang.rice2_verssion.ParentActivity;
import com.xiaofangfang.rice2_verssion.R;
import com.xiaofangfang.rice2_verssion.dao.OprateDb;
import com.xiaofangfang.rice2_verssion.model.AddressMode;
import com.xiaofangfang.rice2_verssion.model.City;
import com.xiaofangfang.rice2_verssion.model.Province;
import com.xiaofangfang.rice2_verssion.tool.DialogTool;

public class AddAddress extends ParentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.add_address);
        super.onCreate(savedInstanceState);
        initData();
    }

    /**
     * 用于实现选择的地区数据到自己填写的地址的分割符号
     */
    public static final String SPLIT_TOKEN = ";";
    private AddressMode addressMode;
    private Intent backIntent;
    private OprateDb oprateDb;
    private DialogTool.AddressSelectDialog addressSelectDialog;


    private void initData() {

        oprateDb = OprateDb.getInstance(this);

        backIntent = getIntent();
        addressMode = (AddressMode) backIntent.getSerializableExtra(AddressMode.class.getSimpleName());
        if (addressMode != null) {
            //代表的是通过编辑模式进来的
            String[] adds = addressMode.getAddress().split(SPLIT_TOKEN);
            area.setText(adds[0]);
            detailAddress.setText(adds[1]);
            name.setText(addressMode.getName());
            tel.setText(addressMode.getTel());
            defAddress.setChecked(addressMode.isDefault());
        }
    }

    private Button area, save;
    private Switch defAddress;
    private EditText detailAddress, name, tel;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initView() {

        name = findViewById(R.id.name);
        tel = findViewById(R.id.tel);
        area = findViewById(R.id.area);
        detailAddress = findViewById(R.id.detailAddress);
        defAddress = findViewById(R.id.defAddress);


        area.setOnClickListener((v) -> {

            addressSelectDialog = new DialogTool.AddressSelectDialog(AddAddress.this);
            addressSelectDialog.setLocationChangeListener((City city, Province province) -> {
                area.setText(province.getName() + " " + city.getName());
            });
            //进行处理
            addressSelectDialog.progress();

        });

        save = findViewById(R.id.save);

        save.setOnClickListener((v) -> {
            if (addressMode != null) {

                //在这里判断是否是默认的地址,如果是,就要清掉之前的默认地址选项
                if (defAddress.isChecked()) {
                    oprateDb.updateOtherStatus();
                }

                //代表的是修改数据的
                if (isPass(name) && isPass(tel) && isPass(area) && isPass(detailAddress)) {
                    long id = addressMode.getId();
                    AddressMode addressMode = new AddressMode(id,
                            name.getText().toString(),
                            tel.getText().toString(),
                            area.getText() + SPLIT_TOKEN + detailAddress.getText(),
                            defAddress.isChecked()
                    );
                    if (-1 == oprateDb.updateAddress(addressMode)) {
                        Toast.makeText(this, "修改出错", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    Toast.makeText(this, "修改数据成功", Toast.LENGTH_SHORT).show();

                    finish();
                } else {
                    Toast.makeText(this, "请输入完整数据", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            //下面代表的是直接的保存
            if (isPass(name) && isPass(tel) && isPass(area) && isPass(detailAddress)) {

                //在这里判断是否是默认的地址,如果是,就要清掉之前的默认地址选项
                if (defAddress.isChecked()) {
                    oprateDb.updateOtherStatus();
                }

                AddressMode addressMode = new AddressMode(name.getText().toString(),
                        tel.getText().toString(),
                        area.getText() + SPLIT_TOKEN + detailAddress.getText(),
                        defAddress.isChecked()
                );
                if (-1 == oprateDb.writeAddress(addressMode)) {
                    Toast.makeText(this, "修改出错", Toast.LENGTH_SHORT).show();
                    return;
                }


                Toast.makeText(this, "保存数据成功", Toast.LENGTH_SHORT).show();
                finish();//结束当前的界面,回到上一个界面
            } else {
                Toast.makeText(this, "请输入完整数据", Toast.LENGTH_SHORT).show();
            }
            return;

        });
    }

    private <T extends TextView> boolean isPass(T t) {
        return t.getText().equals(t.getTag()) ? false : TextUtils.isEmpty(t.getText()) == true ? false : true;
    }


}
