package com.example.momomusic.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.momomusic.activity.ParentActivity;
import com.example.momomusic.servie.NetRequest;
import com.example.momomusic.servie.PlayService;
import com.example.momomusic.tool.LoadProgress;
import com.example.momomusic.tool.UiThread;


import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import okhttp3.Call;
import okhttp3.Response;


/**
 * fragment的父集，用来实现一些通用的操作
 * <p>
 * 我们在lazyLoad()函数里面实现数据加载---->调用 loadData()加载数据，然后将街而过返回到success和error其中，然后根据烦恼返回的数据进行数据的处理
 */
public abstract class ParentFragment extends Fragment implements FramgentOprate {


    /**
     * 当view创建成功后的回调
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoad();
    }


    private ProgressBar progressBar;

    private MyCallHandler myCallHandler;


    /**
     * 这个方法是用来加载网络资源的
     *
     * @param url
     * @param what
     * @param <T>
     */
    public <T> void loadData(final String url, final String what) {
        progressBar = LoadProgress.loadProgress(getMyActivity());
        new Thread(new Runnable() {
            @Override
            public void run() {
                myCallHandler = new MyCallHandler(what);
                NetRequest.requestUrl(url, myCallHandler);
            }
        }).start();
    }

    public <T extends ParentActivity> T getMyActivity() {
        return (T) getActivity();
    }


    public class MyCallHandler implements okhttp3.Callback {

        private String what;

        public MyCallHandler(String what) {
            this.what = what;
        }

        @Override
        public void onFailure(Call call, final IOException e) {
            UiThread.getUiThread().post(new Runnable() {
                @Override
                public void run() {
                    LoadProgress.removeLoadProgress(getMyActivity(), progressBar);
                    Toast.makeText(getMyActivity(), "加载数据出错,请稍后再试", Toast.LENGTH_SHORT).show();
                    onError(e, what);
                }
            });
        }

        @Override
        public void onResponse(Call call, final Response response) throws IOException {
            final String value = response.body().string();
            UiThread.getUiThread().post(new Runnable() {
                @Override
                public void run() {
                    LoadProgress.removeLoadProgress(getMyActivity(), progressBar);
                    try {
                        //这里返回的数据比较复杂,需要解析
                        onSucess(response, what, value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    /**
     * 下面的变量是来控制fragment的加载数据的
     */
    //Fragment的View加载完毕的标记
    private boolean isViewCreated;

    //Fragment对用户可见的标记
    private boolean isUIVisible;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }


    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            loadData();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
        }
    }


    protected abstract void loadData();


    public abstract void onError(IOException e, String what);

    public abstract void onSucess(Response response, String what, String... backData) throws IOException;

    private Bundle bundle;

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public Bundle getBundle() {
        return bundle;
    }


    public PlayService.MyBinder myBinder;

}
