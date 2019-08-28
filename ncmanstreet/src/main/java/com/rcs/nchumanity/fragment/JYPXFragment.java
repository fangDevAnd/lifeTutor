package com.rcs.nchumanity.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.google.zxing.activity.CaptureActivity;
import com.google.zxing.util.Constant;
import com.rcs.nchumanity.R;
import com.rcs.nchumanity.dialog.DialogTool;
import com.rcs.nchumanity.entity.BasicResponse;
import com.rcs.nchumanity.entity.NetConnectionUrl;
import com.rcs.nchumanity.entity.PersistenceData;
import com.rcs.nchumanity.entity.StudyStatus;
import com.rcs.nchumanity.tool.LBSallocation;
import com.rcs.nchumanity.tool.Tool;
import com.rcs.nchumanity.ul.IdentityInfoRecordActivity;
import com.rcs.nchumanity.ul.OnlineAssessmentActivity;
import com.rcs.nchumanity.ul.list.CourseComplexListActivity;
import com.rcs.nchumanity.ul.list.OfflineTrainClassListActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;
import okhttp3.Response;

public class JYPXFragment extends BasicResponseProcessHandleFragment {


    private static final int REQUEST_CODE_SCAN = 14;

    public static final int requestPermissionCode_DW = 12;

    public static final int requestPermissionCode_SCAN = 13;

    @Override
    protected void onViewCreated(View view, Bundle savedInstanceState, boolean isViewCreated) {
        super.onViewCreated(view, savedInstanceState, isViewCreated);

    }


    @Override
    protected int getCreateView() {
        return R.layout.fragment_jhpx;
    }

    @OnClick({R.id.one, R.id.two, R.id.three, R.id.four, R.id.five_signUp,
            R.id.five_signIn, R.id.five_query,
            R.id.six_signUp, R.id.sixSignIn, R.id.sixQuery})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.one:

                //懒加载用户的状态

                String param = NetConnectionUrl.getSignInStatus;


                clickStep = R.id.one;

                loadDataGet(param, "getSignInStatus");


                break;

            case R.id.two:

                String param1 = NetConnectionUrl.getSignInStatus;
                clickStep = R.id.two;
                loadDataGet(param1, "getSignInStatus");

                break;

            case R.id.three:

                clickStep = R.id.three;
                loadDataGet(NetConnectionUrl.getSignInStatus, "getSignInStatus");

                break;

            case R.id.four:


                clickStep = R.id.four;

                loadDataGet(NetConnectionUrl.getSignInStatus, "getSignInStatus");


                break;

            case R.id.five_signUp:

                Bundle bundle2 = new Bundle();
                bundle2.putString(OfflineTrainClassListActivity.URL, NetConnectionUrl.getCPRClassList);
                Tool.startActivity(getContext(), OfflineTrainClassListActivity.class, bundle2);

                break;

            case R.id.five_signIn:

                clickStep = R.id.five_signIn;

                signInOprate(NetConnectionUrl.signInCPRClass);

                break;

            case R.id.five_query:

                clickStep = R.id.five_query;

                loadDataGet(NetConnectionUrl.getSignInStatus, "getSignInStatus");


                break;

            case R.id.six_signUp:

                Bundle bundle3 = new Bundle();
                bundle3.putString(OfflineTrainClassListActivity.URL, NetConnectionUrl.getTraumaClassList);
                Tool.startActivity(getContext(), OfflineTrainClassListActivity.class, bundle3);

                break;

            case R.id.sixSignIn:

                signInOprate(NetConnectionUrl.signInTraumaClass);

                break;

            case R.id.sixQuery:

                queryScoreTrauma();

                break;
        }
    }

    /**
     * 查询CPR的分数
     */
    private void queryScoreCPR() {

        DialogTool<String> dialogTool = new DialogTool<String>() {
            @Override
            public void bindView(DialogTool<String> d, Dialog dialog, String... t) {
                EditText input = d.getView().findViewById(R.id.input);
                d.setClickListener(R.id.cancel, (v) -> {
                    dialog.dismiss();
                });
                d.setClickListener(R.id.yes, (v) -> {
                    dialog.dismiss();

                    if (input.length() < 1) {
                        Toast.makeText(getContext(), "请输入查询码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String param = String.format(NetConnectionUrl.queryXFScore, input.getText());
                    loadDataGet(param, "queryXFScore");
                });

            }
        };

        Dialog dialog = dialogTool.getDialog(getContext(), R.layout.dialog_input);
        dialog.show();

    }

    /**
     * 查询创伤救护的分数
     */
    private void queryScoreTrauma() {

        //调用 扫描二维码将解析数据回传

        String[] permission = {
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        requestPermission(permission, requestPermissionCode_SCAN);

    }


    private String courseSignInUrl;

    private void signInOprate(String url) {

        this.courseSignInUrl = url;
        /**
         * 开启定位授权
         * 调用百度定位api
         *
         *
         */

        LBSallocation.startLocationWithFragment(this, requestPermissionCode_DW);

    }


    @Override
    public void permissionSuccess(int requestCode) {
        super.permissionSuccess(requestCode);
        if (requestCode == requestPermissionCode_DW) {

            LBSallocation.getCurrentLocation(getContext(), new BDAbstractLocationListener() {
                @Override
                public void onReceiveLocation(BDLocation bdLocation) {
                    String latitute = bdLocation.getLatitude() + "";
                    String longitute = bdLocation.getLongitude() + "";

                    Map<String, String> param = new HashMap<>();
                    param.put("latitude", latitute);
                    param.put("longitude", longitute);
                    param.put("name", "线下培训签到");
                    loadDataPostForce(courseSignInUrl, "courseSignInUrl", param);
                }
            });

        } else if (requestCode == requestPermissionCode_SCAN) {
            Intent intent = new Intent(getContext(), CaptureActivity.class);
            startActivityForResult(intent, REQUEST_CODE_SCAN);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == getActivity().RESULT_OK) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                String scanResult = bundle.getString(Constant.INTENT_EXTRA_KEY_QR_SCAN);
                String param = String.format(NetConnectionUrl.queryCSJHScore, scanResult, "心肺复苏");
                loadDataGet(param, "queryScoreTrauma");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private int clickStep;


    @Override
    protected void responseDataSuccess(String what, String backData, Response response, BasicResponse... br) throws Exception {
        super.responseDataSuccess(what, backData, response, br);

        if (what.equals("getSignInStatus")) {
            JSONObject jsonObject = new JSONObject(backData);

            JSONObject data = null;

            if (jsonObject.has("object")) {
                data = jsonObject.getJSONObject("object");
            } else if (jsonObject.has("data")) {
                data = jsonObject.getJSONObject("data");
            }

            int studyStatus = data.getInt("studyStatus");

            switch (clickStep) {

                case R.id.one:
                    if (studyStatus == 1 || studyStatus == 8) {
                        //进入报名
                        Tool.startActivity(getContext(), IdentityInfoRecordActivity.class);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                                .setTitle("温馨提示")
                                .setMessage("你已报名")
                                .setPositiveButton("确定", (dialog, which) -> {
                                    dialog.dismiss();
                                });
                        builder.create().show();
                    }
                    break;

                case R.id.four:

                    if (studyStatus == 3) {
                        Tool.startActivity(getContext(), OnlineAssessmentActivity.class);
                    } else if (studyStatus > 3) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                                .setTitle("温馨提示")
                                .setMessage("不能重复考核")
                                .setPositiveButton("确定", (dialog, which) -> {
                                    dialog.dismiss();
                                });
                        builder.create().show();
                    } else if (studyStatus < 3) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                                .setTitle("温馨提示")
                                .setMessage("未达到考试条件，请完成线上课程学习")
                                .setPositiveButton("确定", (dialog, which) -> {
                                    dialog.dismiss();
                                });
                        builder.create().show();
                    }
                    break;
                case R.id.two:

                    //可能返回值 401 。----》调用 默认加载的接口
                    //如果登录 返回状态码
                    /**
                     * 代表的是已经登录的情况
                     */

                    if (studyStatus >= 2) {

                        entryOnlineTrain(NetConnectionUrl.getOnlineClassAndRecordByUser,
                                NetConnectionUrl.getOnlineClass, studyStatus);
                    } else {
                        entryOnlineTrain(NetConnectionUrl.getOnlineClass, NetConnectionUrl.getOnlineClass, studyStatus);
                    }

                    break;

                case R.id.three:

                    if (studyStatus >= 2) {
                        entryOnlineSelectTrain(NetConnectionUrl.getNotRequiredOnlineClassAndRecordByUser
                                , NetConnectionUrl.getNotRequiredOnlineClass, studyStatus);
                    } else {
                        entryOnlineSelectTrain(
                                NetConnectionUrl.getNotRequiredOnlineClass,
                                NetConnectionUrl.getNotRequiredOnlineClass
                                , studyStatus
                        );
                    }
                    break;

                case R.id.five_query:
                    /**
                     * 代表
                     */
                    if (studyStatus >= 5 && studyStatus < 7) {
                        queryScoreCPR();
                    } else if (studyStatus >= 7) {
                        super.responseWith201_202(what, new BasicResponse(201, "已经完成考核", null));
                    } else if (studyStatus < 5) {
                        super.responseWith201_202(what, new BasicResponse(201, "没有达到操作要求", null));
                    }
                    break;
            }

        } else if (what.equals("courseSignInUrl")) {

            responseWith201_202(what, new BasicResponse(201, "签到成功", null));

        } else if (what.equals("queryXFScore")) {

            responseWith201_202(what, new BasicResponse(201, "成绩绑定成功", null));

        }
        if (what.equals("queryScoreTrauma")) {

            responseWith201_202(what, new BasicResponse(201, "成绩绑定成功", null));
        }
    }


    @Override
    protected void responseWith401(String what, BasicResponse br) {
        Log.d("test", "responseWith401: ");
        if (what.equals("courseSignInUrl")) {

            if (br.message.equals(BasicResponse.MESSAGE_OTHER)) {
                super.responseWith401(what, br);
            } else {
                new AlertDialog.Builder(getContext())
                        .setTitle("温馨提示")
                        .setMessage(br.message)
                        .setPositiveButton("确定", (dialog, which) -> {
                            dialog.dismiss();
                        }).create().show();
            }
        } else if (what.equals("getSignInStatus")) {
            /**
             * 点击线上培训，没有登录的情况
             */
            if (clickStep == R.id.two) {
                /**
                 * 没有登录的情况   没有报名 学习状态为1
                 */
                entryOnlineTrain(NetConnectionUrl.getOnlineClass, NetConnectionUrl.getOnlineClass, StudyStatus.STATUS_NOT_STUDY);
            }
            /**
             * 线上选修课成没有登录的情况
             */
            if (clickStep == R.id.three) {
                /**
                 * 没有登录的情况
                 */
                entryOnlineSelectTrain(NetConnectionUrl.getNotRequiredOnlineClass,
                        NetConnectionUrl.getNotRequiredOnlineClass, StudyStatus.STATUS_NOT_STUDY);
            }

            if (clickStep == R.id.four || clickStep == R.id.one || clickStep == R.id.five_query) {
                super.responseWith401(what, br);
            }
        }
    }

    /**
     * 进入线下培训课程
     *
     * @param url
     * @param backUrl
     */
    private void entryOnlineSelectTrain(String url, String backUrl, int studyStatus) {

        Bundle bundle1 = new Bundle();
        bundle1.putString(CourseComplexListActivity.FUN, CourseComplexListActivity.FUN_SELECT);
        bundle1.putString(CourseComplexListActivity.NET_URL,
                url);
        bundle1.putString(CourseComplexListActivity.BACKUP_URL, backUrl);
        bundle1.putInt(CourseComplexListActivity.STUDY_STATUS, studyStatus);
        Tool.startActivity(getContext(), CourseComplexListActivity.class, bundle1);
    }


    /**
     * 进入线上培训课程
     *
     * @param before
     * @param back
     */
    private void entryOnlineTrain(String before, String back, int studyStatus) {

        Bundle bundle = new Bundle();
        bundle.putString(CourseComplexListActivity.NET_URL, before);
        bundle.putString(CourseComplexListActivity.BACKUP_URL, back);
        bundle.putInt(CourseComplexListActivity.STUDY_STATUS, studyStatus);
        bundle.putString(CourseComplexListActivity.FUN, CourseComplexListActivity.FUN_NEED);
        Tool.startActivity(getContext(), CourseComplexListActivity.class, bundle);

    }


    @Override
    protected void responseWith207(String what, BasicResponse br) {
        super.responseWith207(what, br);
        new AlertDialog.Builder(getContext())
                .setTitle("温馨提示")
                .setMessage("还未到签到时间，无法签到。")
                .setPositiveButton("确定", (dialog, which) -> {
                    dialog.dismiss();
                }).create().show();
    }
}
