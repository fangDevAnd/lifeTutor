package com.rcs.nchumanity.tool;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringDef;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;

import com.rcs.nchumanity.entity.PersistenceData;
import com.rcs.nchumanity.ul.MainActivity;
import com.rcs.nchumanity.ul.ParentActivity;
import com.rcs.nchumanity.ul.RegisterUserActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;
import java.util.Date;

import kotlin.text.Regex;

public class Tool {

    public static final int LOGIN_REQUEST_CODE = 12;


    /**
     * @param classObj
     */
    public static void startActivity(Context context, Class<?> classObj) {
        startActivity(context, classObj, null);
    }


    public static void startActivity(Context context, Class<?> classObj, Bundle bundle) {
        Intent intent = new Intent(context, classObj);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }


    public static void startActivityForResult(Context context, int requestCode, Class<?> classObj) {
        Intent intent = new Intent(context, classObj);
        ((AppCompatActivity) context).startActivityForResult(intent, requestCode);


    }


    public static boolean loginCheck(Context context) {
        if (PersistenceData.getUserId(context) == PersistenceData.DEF_USER) {
            Tool.startActivityForResult(context, LOGIN_REQUEST_CODE, RegisterUserActivity.class);
            return false;
        }
        return true;
    }


    /**
     * 获得屏幕的宽高
     *
     * @param context
     * @return
     */
    public static int[] getScreenDimension(Context context) {

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int[] val = {
                displayMetrics.widthPixels,
                displayMetrics.heightPixels
        };
        return val;
    }


    /**
     * 这个方法实现的是传递相对应的view设置对应的compoundDrwable的颜色
     *
     * @param color
     * @param t
     * @param <T>
     */
    public static <T extends TextView> void setDrawableColor(int color, T t) {
        Drawable[] dws = t.getCompoundDrawables();
        for (Drawable dw : dws) {
            if (dw != null) {
                dw.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dw.setTint(color);
                }
            }
        }

    }


    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    /**
     * 渲染Drawable
     *
     * @param drawable
     * @param colors
     * @return
     */
    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        if (drawable == null) {
            return null;
        }
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    /**
     * 对textview的drawable进行渲染
     *
     * @param t
     * @param colorStateList
     * @param <T>
     */
    public static <T extends TextView> void tintDrawable(T t, ColorStateList colorStateList, @PosiMode String posi) {
        Drawable[] dws = t.getCompoundDrawables();
        Drawable drawable = null;
        for (int i = 0; i < dws.length; i++) {
            switch (posi) {
                case POSI_LEFT:
                    drawable = tintDrawable(dws[0], colorStateList);
                    t.setCompoundDrawables(drawable, null, null, null);
                    break;
                case POSI_TOP:
                    drawable = tintDrawable(dws[1], colorStateList);
                    t.setCompoundDrawables(null, drawable, null, null);
                    break;
                case POSI_RIGHT:
                    drawable = tintDrawable(dws[2], colorStateList);
                    t.setCompoundDrawables(null, null, drawable, null);
                    break;
                case POSI_BOTTOM:
                    drawable = tintDrawable(dws[3], colorStateList);
                    t.setCompoundDrawables(null, null, null, drawable);
                    break;
            }
        }
    }


    public static final String POSI_LEFT = "left";

    public static final String POSI_TOP = "top";

    public static final String POSI_RIGHT = "right";

    public static final String POSI_BOTTOM = "bottom";


    /**
     * 登录的响应操作
     *
     * @param activity
     * @param jsonData
     */

    public static <T extends ParentActivity> void loginResponse(T activity, String jsonData, String sessionId) {

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonData);

            JSONObject userAccountJson = jsonObject.getJSONObject("object");

            JSONObject userAccount = userAccountJson.getJSONObject("userAccount");

            String user_id = userAccount.getInt("userId") + "";

            String picture = null;
            if (userAccount.has("picUrl")) {
                picture = userAccount.getString("picUrl");
            }
            String nickName = null;
            if (userAccount.has("nickname")) {
                nickName = userAccount.getString("nickname");
            }
            PersistenceData.loginSuccess(activity, picture, nickName, user_id, sessionId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        activity.backStackLower();
//        Tool.startActivity(activity, MainActivity.class);

    }


    public static boolean isUrl(String url) {
        return url.startsWith("https") || url.startsWith("https");
    }


    @StringDef(value = {
            POSI_LEFT,
            POSI_TOP,
            POSI_RIGHT,
            POSI_BOTTOM,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface PosiMode {

    }

    /**
     * 将px转为  dp
     *
     * @param value
     * @param context
     * @return
     */
    public static int px2dp(int value, Context context) {

        float scale = (int) context.getResources().getDisplayMetrics().density;
        return (int) (value / scale + 0.5f);
    }


    /**
     * 获得软件的版本
     *
     * @return
     */
    public static long getSoftWareVersion(Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        long versionCode;
        if (android.os.Build.VERSION.SDK_INT >= 28) {
            versionCode = pi.getLongVersionCode();
        } else {
            versionCode = pi.versionCode;
        }
        return versionCode;
    }

    /**
     * "HH:mm
     * 格式化时间
     *
     * @param param
     * @return
     */
    public static String dateFormat(String param) {
        SimpleDateFormat sdf = new SimpleDateFormat(param);
        Date date = new Date(System.currentTimeMillis());
        String value = sdf.format(date);
        return value;
    }


    /**
     * 设置drawableLeft
     *
     * @param imgRes
     * @param context
     * @param textView
     */
    public static void drawableChangeLeft(@DrawableRes int imgRes, Context context, TextView textView) {
        Drawable drawable = context.getResources().getDrawable(imgRes);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(drawable, null, null, null);
    }

    /**
     * 设置drawableRight
     *
     * @param imgRes
     * @param context
     * @param textView
     */
    public static void drawableChangeRight(@DrawableRes int imgRes, Context context, TextView textView) {
        Drawable drawable = context.getResources().getDrawable(imgRes);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, null, drawable, null);
    }

    /**
     * 设置drawableRight
     *
     * @param context
     * @param textView
     */
    public static void drawableChangeRight(Drawable drawable, Context context, TextView textView) {
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, null, drawable, null);
    }


    /**
     * 设置drawableTop
     *
     * @param imgRes
     * @param context
     * @param textView
     */
    public static void drawableChangeTop(@DrawableRes int imgRes, Context context, TextView textView) {
        Drawable drawable = context.getResources().getDrawable(imgRes);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, drawable, null, null);
    }


}
