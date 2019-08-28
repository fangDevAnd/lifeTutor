package com.rcs.nchumanity.ul.list;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.rcs.nchumanity.R;
import com.rcs.nchumanity.adapter.ListViewCommonsAdapter;
import com.rcs.nchumanity.dialog.DialogTool;
import com.rcs.nchumanity.entity.BasicResponse;
import com.rcs.nchumanity.entity.PersistenceData;
import com.rcs.nchumanity.entity.complexModel.ComplexModelSet;
import com.rcs.nchumanity.entity.model.OfflineTrainClass;
import com.rcs.nchumanity.tool.DateProce;
import com.rcs.nchumanity.tool.Tool;
import com.rcs.nchumanity.ul.BasicResponseProcessHandleActivity;
import com.rcs.nchumanity.ul.MyCourseActivity;
import com.rcs.nchumanity.ul.ParentActivity;
import com.rcs.nchumanity.ul.detail.OfflineTrainClassDetailActivity;
import com.rcs.nchumanity.view.CommandBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * 用于实现线下培训班列表的Activity
 */
public class OfflineTrainClassListActivity extends BasicResponseProcessHandleActivity {

    private static final String NC = "南昌";
    @BindView(R.id.changePosition)
    TextView changePosition;

    @BindView(R.id.toolbar)
    CommandBar toolbar;

    @BindView(R.id.classList)
    ListView classList;

    public static final String FUNC = "func";

    /**
     * 心肺复苏
     */
    public static final String FUNC_XF = "xf";

    /**
     * 创伤救护
     */
    public static final String FUNC_CS = "cs";

    public static String URL = "url";


    /**
     * 代表的是默认的选中
     */
    private int defIndex = 0;


    private ArrayList<ComplexModelSet.M_traiCla_areaInf> traiCla_areaInfs;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offlinetrain);
        ButterKnife.bind(this);
        toolbar.setTitle("培训班选择");
        initPopWindow();

        String url = getIntent().getExtras().getString(URL);

        if (url == null) {
            throw new IllegalArgumentException("please transport load url to this activity");
        }
        loadDataGetForForce(url, "load");


        traiCla_areaInfs = new ArrayList<>();


        classListAdapter =
                new ListViewCommonsAdapter<ComplexModelSet.M_traiCla_areaInf>
                        (traiCla_areaInfs, R.layout.item_train_class) {
                    @Override
                    public void bindView(ViewHolder holder, ComplexModelSet.M_traiCla_areaInf obj) {
                        holder.setText(R.id.area, obj.area);
                        holder.setText(R.id.dateTime, DateProce.formatDate(DateProce.parseDate(obj.starTime)));
                        holder.setText(R.id.address, obj.position + " " + (obj.vrClass ? "VR" : "非VR"));
                        holder.setText(R.id.trainCount, obj.maxNum + "人");
                        holder.setText(R.id.leftCount, obj.leftNum + "人");
                        holder.setText(R.id.trainOrg, obj.org);
                        holder.setText(R.id.trainer, obj.trainer);
                    }

                    @Override
                    public int getCount() {
                        return traiCla_areaInfs.size();
                    }
                };


        classList.setAdapter(classListAdapter);

        classList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                boolean forResult = getIntent().getExtras().getBoolean(MyCourseActivity.FOR_RESULT);

                //进入详情界面
                String classId = traiCla_areaInfs.get(position).classId + "";
                Bundle bundle = new Bundle();
                bundle.putString(OfflineTrainClassDetailActivity.CLASS_ID, classId);
                bundle.putBoolean(MyCourseActivity.FOR_RESULT, forResult);
                if (forResult) {
                    Intent intent = new Intent(OfflineTrainClassListActivity.this, OfflineTrainClassDetailActivity.class);
                    startActivityForResult(intent, MyCourseActivity.CODE_RE, bundle);
                } else {
                    Tool.startActivity(OfflineTrainClassListActivity.this, OfflineTrainClassDetailActivity.class, bundle);
                }
            }
        });

    }


    private ListViewCommonsAdapter<ComplexModelSet.M_traiCla_areaInf> classListAdapter;


    private void initPopWindow() {

        View inner = LayoutInflater.from(this).inflate(R.layout.popwindow_item, null);

        ListView listView = inner.findViewById(R.id.rootList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                setContentList(classListAreaMap.get(areaInfos.get(position)));
                changePosition.setText(areaInfos.get(position));
                //点击切换
                popupWindow.dismiss();
            }
        });

        adapter = new ListViewCommonsAdapter<String>((ArrayList<String>) areaInfos, R.layout.item_area) {
            @Override
            public void bindView(ViewHolder holder, String obj) {
                holder.setText(R.id.area, obj);
            }

            @Override
            public int getCount() {
                return areaInfos.size();
            }
        };
        listView.setAdapter(adapter);

        popupWindow = new PopupWindow(inner, -1, -2);
        // 外部点击事件
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);//要先让popupwindow获得焦点，才能正确获取popupwindow的状态


    }

    /**
     * 设置下拉菜单的列表数据
     *
     * @param areaInfos
     */
    public void setHeadData(List<String> areaInfos) {
        this.areaInfos.clear();
        this.areaInfos.addAll(areaInfos);
        adapter.notifyDataSetChanged();
    }

    private List<String> areaInfos = new ArrayList<>();

    private ListViewCommonsAdapter<String> adapter;

    private PopupWindow popupWindow;


    @OnClick(R.id.changePosition)
    public void onClick(View view) {
        Log.d("test", "onClick: ");
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAsDropDown(changePosition);
        }
    }


    @Override
    protected void onDestroy() {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }

        super.onDestroy();
    }

    private HashMap<String, List<ComplexModelSet.M_traiCla_areaInf>> classListAreaMap;


    @Override
    protected void responseDataSuccess(String what, String backData, Response response, BasicResponse... br) throws Exception {
        super.responseDataSuccess(what, backData, response, br);

        if (what.equals("load")) {
            try {

                classListAreaMap = new HashMap<>();

                JSONObject brJ = new JSONObject(backData);

                JSONArray courseList = brJ.getJSONArray("object");

                List<ComplexModelSet.M_traiCla_areaInf> list1 = new ArrayList<>();


                for (int i = 0; i < courseList.length(); i++) {

                        /*
                        area
                        vrClass
                        vrAttr
                        org
                        leftNum
                        maxNum
                        position
                        startTime
                        classId
                         */
                    JSONObject course = courseList.getJSONObject(i);
                    int classId = course.getInt("classId");
                    String startme = course.getString("startTime");
                    String position = course.getString("position");
                    String maxNum = course.getString("maxNum");
                    String leftNum = course.getString("leftNum");
                    String org = course.getString("org");
                    int vrAttr = course.getInt("vrAttr");
                    boolean vrClass = course.getBoolean("vrClass");
                    String area = course.getString("area");
                    String trainer = null;
                    if (course.has("trainer")) {
                        trainer = course.getString("trainer");
                    }

                    ComplexModelSet.M_traiCla_areaInf traiCla_areaInf
                            = new ComplexModelSet.M_traiCla_areaInf(classId, startme, position, maxNum, leftNum, org, vrAttr, vrClass, area);

                    traiCla_areaInf.trainer = trainer;

                    if (classListAreaMap.get(area) == null) {
                        List<ComplexModelSet.M_traiCla_areaInf> backList = new ArrayList<>();
                        backList.add(traiCla_areaInf);
                        classListAreaMap.put(area, backList);
                    } else {
                        classListAreaMap.get(area).add(traiCla_areaInf);
                    }
                    list1.add(traiCla_areaInf);
                }

                classListAreaMap.put(NC, list1);


                Set<String> stringSet = classListAreaMap.keySet();

                setHeadData(new ArrayList<>(stringSet));

                if (areaInfos.size() > 0) {
                    defIndex = areaInfos.size() - 1;
                    changePosition.setText(areaInfos.get(defIndex));
                    setContentList(classListAreaMap.get(changePosition.getText()));
                } else if (areaInfos.size() == 0) {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 设置实体的数据
     *
     * @param list
     */
    private void setContentList(List list) {
        traiCla_areaInfs.clear();
        traiCla_areaInfs.addAll(list);
        classListAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MyCourseActivity.CODE_RE && resultCode == RESULT_OK) {
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
            finish();
        }

    }
}
