package com.example.momomusic.fragment.zhibo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.momomusic.R;
import com.example.momomusic.adapter.MyCommandAdapter;
import com.example.momomusic.fragment.ParentFragment;
import com.example.momomusic.model.ZhiBoHouse;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;

public class ZhiBoBaseFragment extends ParentFragment {


    /**
     * 我们并没有去实现getClassname()方法，我们希望子类实现这个方法，然后实现不同的数据请求
     *
     * @param e
     * @param what
     */

    private int page;

    private String url = "http://www.baidu.com?name=" + getClassName() + "&page=" + page;

    private String what = "9";

    ArrayList<ZhiBoHouse> zhiBoHouses = new ArrayList<>();


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        zhiBoHouses.add(new ZhiBoHouse());
        zhiBoHouses.add(new ZhiBoHouse());
        zhiBoHouses.add(new ZhiBoHouse());
        zhiBoHouses.add(new ZhiBoHouse());
        zhiBoHouses.add(new ZhiBoHouse());
        zhiBoHouses.add(new ZhiBoHouse());
        zhiBoHouses.add(new ZhiBoHouse());
        zhiBoHouses.add(new ZhiBoHouse());
        zhiBoHouses.add(new ZhiBoHouse());
        zhiBoHouses.add(new ZhiBoHouse());
        zhiBoHouses.add(new ZhiBoHouse());


        myCommandAdapter = new MyCommandAdapter<ZhiBoHouse>(getContext(), R.layout.recyclerview_zhibo_tuijian, zhiBoHouses) {

            @Override
            public void bind(MyViewHolder holder, ZhiBoHouse zhiBoHouse) {




            }
        };
        recyclerView.setAdapter(myCommandAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(llm);
    }

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    MyCommandAdapter<ZhiBoHouse> myCommandAdapter;


    /**
     * 当前的请求的数据的数量
     */
    private int size = 10;
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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhibo_tuijian, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onSucess(Response response, String what, String... backData) throws IOException {
        if (what.equals(this.what)) {

        }
    }

    @Override
    public void onError(IOException e, String what) {

    }

    @Override
    protected void loadData() {
        Logger.d("加载数据" + url);
        loadData(url, what);
    }


    @Override
    public Class getClassName() {
        return this.getClass();
    }
}
