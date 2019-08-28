package com.rcs.nchumanity.fragment;

import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.gson.Gson;
import com.rcs.nchumanity.entity.BasicResponse;
import com.rcs.nchumanity.entity.PersistenceData;
import com.rcs.nchumanity.tool.Tool;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

public abstract class BasicResponseProcessHandleFragment extends ParentFragment {


    @Override
    public void onSucess(Response response, String what, String... backData) throws IOException {
        super.onSucess(response, what, backData);

        BasicResponse br = new Gson().fromJson(backData[0], BasicResponse.class);

        try {
            JSONObject brJ = new JSONObject(backData[0]);
            String message = brJ.has("msg") ? brJ.getString("msg") : brJ.getString("message");

            if (br.code == BasicResponse.RESPONSE_SUCCESS) {
                responseDataSuccess(what, backData[0], response);
            } else if (br.code == BasicResponse.NOT_LOGIN) {
                    br.message = message;
                    responseWith401(what, br);
            } else if (br.code == BasicResponse.NOT_REQUIRED) {
                br.message = message;
                responseWith201_202(what, br);
            } else if (br.code == BasicResponse.NOT_REQUIRED_201) {
                br.message = message;
                responseWith201_202(what, br);

            } else if (br.code == BasicResponse.NOT_SIGNIN) {
                br.message = message;
                responseWith207(what, br);

            } else if (br.code == BasicResponse.RESPONSE_FAIL) {
                br.message = message;
                responseWith500(what, br);
            } else if(br.code==BasicResponse.NOT_REQUIRED_204) {
                br.message = message;
                responseWith204(what,br);

            }else {
                Toast.makeText(getContext(), "" + message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected  void responseWith204(String what,BasicResponse br){
        responseWith201_202(what,br);
    }

    /**
     * 响应码为 500的回调 ，500的状态为 系统服务器异常
     * 默认的情况下，我们直接去dialog
     * @param what
     * @param br
     */
    protected void responseWith500(String what, BasicResponse br) {
            responseWith201_202(what,br);
    }

    protected void responseWith207(String what, BasicResponse br) {

    }

    protected void responseDataSuccess(String what, String backData, Response response, BasicResponse... br) throws Exception {
        Log.d("test", "responseDataSuccess: ");
    }

    protected void responseWith401(String what, BasicResponse br) {
        PersistenceData.clear(getContext());
        Tool.loginCheck(getContext());
    }

    protected void responseWith201_202(String what, BasicResponse br) {
        new AlertDialog.Builder(getContext())
                .setTitle("温馨提示")
                .setMessage(br.message)
                .setPositiveButton("确定", (dialog, which) -> {
                    dialog.dismiss();
                }).create().show();
    }

}
