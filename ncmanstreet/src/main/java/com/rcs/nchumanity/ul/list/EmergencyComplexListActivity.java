package com.rcs.nchumanity.ul.list;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.rcs.nchumanity.R;
import com.rcs.nchumanity.adapter.ListViewCommonsAdapter;
import com.rcs.nchumanity.entity.BasicResponse;
import com.rcs.nchumanity.entity.NetConnectionUrl;
import com.rcs.nchumanity.entity.model.EmergencyInfo;
import com.rcs.nchumanity.tool.DateProce;
import com.rcs.nchumanity.tool.JsonDataParse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class EmergencyComplexListActivity extends ComplexListActivity<EmergencyInfo> {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        toolbar.setTitle("历史记录");
    }

    @Override
    protected void bindViewValue(ListViewCommonsAdapter.ViewHolder holder, EmergencyInfo obj) {

        holder.setText(R.id.title, obj.getTitle());
        holder.setText(R.id.content, obj.getContent());
        holder.setText(R.id.phone, obj.getMobilePhone());
        holder.setText(R.id.count, obj.getReadCount() + "次");

    }

    @Override
    protected void itemClick(AdapterView<?> parent, View view, int position, long id, EmergencyInfo item) {

    }

    @Override
    protected int getLayout() {
        return R.layout.item_emergency;
    }


    private int page = 0;

    private int size = 20;

    /**
     * 代表的是没有数据
     */
    private boolean notData;

    /**
     * 代表的是否能够进行刷新，默认的状态下是true
     * 之所以采用这个的判断是源于我们滑动到底层的时候会一直触发loadData()事件，这时会产生多线程问题
     * 当isFlush=true,代表的是数据刷新成功，也就代表这我们可以在一次的进行刷新
     */
    private boolean isFlush = true;


    @Override
    protected void scrollToBottom() {
        if (isFlush) {
            isFlush = false;
            page++;
            //加载数据
            String param = String.format(NetConnectionUrl.selectInfoSplitPage, size, page);

            loadDataGetSilence(param, "loadData");
        } else {
            Toast.makeText(this, "没有多余的数据了", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String param = String.format(NetConnectionUrl.selectInfoSplitPage, size, page);

        loadDataGetSilence(param, "loadData");

    }


    @Override
    protected void responseDataSuccess(String what, String backData, Response response, BasicResponse... br) throws Exception {
        super.responseDataSuccess(what, backData, response, br);

        if (what.equals("loadData")) {

            try {

                List<EmergencyInfo> emergencyInfos = JsonDataParse.parseEmergencyData(backData);

                Log.d("test", "onSucessful: emergencyInfos.size=" + size);

                if (emergencyInfos.size() < size) {
                    //代表的是没有数据了
                    notData = true;
                } else {
                    notData = false;
                }

                //当数据加载完成后，判断是否是进行的刷新，如果是就代表刷新结束
                if (!notData) {
                    isFlush = true;//代表我们已经可以再一次的刷新了
                } else {
                    isFlush = false;//如果没有数据，就不能在刷新
                }
                addDataList(emergencyInfos);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


//    @Override
//    public void onSucessful(Response response, String what, String... backData) throws IOException {
//
//        BasicResponse br = new Gson().fromJson(backData[0], BasicResponse.class);
//
//        String message = null;
//        JSONObject br1 = null;
//        try {
//            br1 = new JSONObject(backData[0]);
//            message = br1.has("msg") ? br1.getString("msg") : br1.getString("message");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (br.code == BasicResponse.RESPONSE_SUCCESS) {
//
//            switch (what) {
//                case "loadData":
//
//                    try {
//
//                        List<EmergencyInfo> emergencyInfos = JsonDataParse.parseEmergencyData(backData[0]);
//
//                        Log.d("test", "onSucessful: emergencyInfos.size=" + size);
//
//                        if (emergencyInfos.size() < size) {
//                            //代表的是没有数据了
//                            notData = true;
//                        } else {
//                            notData = false;
//                        }
//
//                        //当数据加载完成后，判断是否是进行的刷新，如果是就代表刷新结束
//                        if (!notData) {
//                            isFlush = true;//代表我们已经可以再一次的刷新了
//                        } else {
//                            isFlush = false;//如果没有数据，就不能在刷新
//                        }
//                        addDataList(emergencyInfos);
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    break;
//            }
//        } else {
//            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//        }
//    }


}
