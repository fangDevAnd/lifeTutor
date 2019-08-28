package com.rcs.nchumanity.ul;

import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.gson.Gson;
import com.rcs.nchumanity.entity.BasicResponse;
import com.rcs.nchumanity.entity.PersistenceData;
import com.rcs.nchumanity.tool.Tool;
import com.rcs.nchumanity.ul.ParentActivity;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;


/**
 * 处理数据请求的操作
 * <p>
 * 当前的activity的处理过程 与当前的软件逻辑存在耦合，不具有可抽取作为独立公共代码的能力
 */
public class BasicResponseProcessHandleActivity extends ParentActivity {


    @Override
    public void onSucessful(Response response, String what, String... backData) throws IOException {
        super.onSucessful(response, what, backData);

        BasicResponse br = new Gson().fromJson(backData[0], BasicResponse.class);

        try {
            JSONObject brJ = new JSONObject(backData[0]);
            String message = brJ.has("msg") ? brJ.getString("msg") : brJ.getString("message");

            if (br.code == BasicResponse.RESPONSE_SUCCESS) {
                responseDataSuccess(what, backData[0], response, br);
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

            } else if (br.code == BasicResponse.CHANGE_PASSWORD_SUCCESS_11) {
                br.message = message;
                responseWith11(what, br);
            } else if (br.code == BasicResponse.SignUpSuccess) {
                br.message = message;
                responseWith4(what, br, response, backData[0]);
            } else if (br.code == BasicResponse.REGISTED_SUCCESS) {
                br.message = message;
                responseWith3(what, br);

            } else if (br.code == BasicResponse.VALIDATE_CODE_ERROR) {
                br.message = message;
                responseWith6(what, br);

            } else if (br.code == BasicResponse.NOT_REGISTER) {
                br.message = message;
                responseWith1(what, br);
            } else if (br.code == BasicResponse.NOT_CANCEL) {

                br.message = message;
                responseWith501(what, br);
            } else if (br.code == BasicResponse.CANCEL_FAIL) {
                br.message = message;
                responseWith500(what, br);

            } else if (br.code == BasicResponse.REGISTED) {
                br.message = message;
                responseWith2(what, br);
            } else if (br.code == BasicResponse.NOT_TRANSPORT) {
                br.message = message;
                responseWith301(what, br);
            } else if (br.code == BasicResponse.TRANSPORT) {
                br.message = message;
                responseWith302(what, br);
            } else if(br.code==BasicResponse.NOT_REQUIRED_204) {
                br.message = message;
                responseWith204(what, br);
            }else {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void responseWith500(String what, BasicResponse br) {
        responseWith201_202(what, br);
    }

    protected void responseWith501(String what, BasicResponse br) {

    }


    protected void responseWith2(String what, BasicResponse br) {

    }


    protected  void responseWith204(String what,BasicResponse br){
        responseWith201_202(what,br);
    }

    /**
     * 响应码为1 的操作
     *
     * @param what
     * @param br
     */
    protected void responseWith1(String what, BasicResponse br) {

    }


    /**
     * 响应吗为6 的操作
     *
     * @param what
     * @param br
     */
    protected void responseWith6(String what, BasicResponse br) {

    }


    protected void responseWith3(String what, BasicResponse br) {

    }

    protected void responseWith4(String what, BasicResponse br, Response res, String resData) {

    }

    protected void responseWith11(String what, BasicResponse br) {

    }

    protected void responseWith207(String what, BasicResponse br) {

    }


    protected void responseWith301(String what, BasicResponse br) {
        responseWith201_202(what, br);
    }

    protected void responseWith302(String what, BasicResponse br) {
        responseWith201_202(what, br);
    }

    protected void responseDataSuccess(String what, String backData, Response response, BasicResponse... br) throws Exception {
        Log.d("test", "responseDataSuccess: ");
    }


    protected void responseWith401(String what, BasicResponse br) {
        PersistenceData.clear(this);
        Tool.loginCheck(this);
    }

    protected void responseWith201_202(String what, BasicResponse br) {
        new AlertDialog.Builder(this)
                .setTitle("温馨提示")
                .setMessage(br.message)
                .setPositiveButton("确定", (dialog, which) -> {
                    dialog.dismiss();
                }).create().show();
    }


}
