package com.rcs.nchumanity.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.rcs.nchumanity.R;
import com.rcs.nchumanity.entity.complexModel.ComplexModelSet;
import com.rcs.nchumanity.entity.model.SpecificInfo;
import com.rcs.nchumanity.entity.model.SpecificInfoClassification;

import java.util.List;

/**
 * 这个一个基本的选项，构建当前组件的时候需要
 * 下面几个内容
 * 1，标题文本
 * 2.更多的点击链接
 * 3.信息列表，传递一个list
 */
public class BasicItem extends LinearLayout {


    private static final String TAG = "test";

    public BasicItem(Context context) {
        this(context, null);
    }

    public BasicItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(getContext()).inflate(R.layout.include_basic_info_item, this, true);
        initView();
    }

    private void initView() {
        title = findViewById(R.id.title);
        infoArea = findViewById(R.id.infoArea);
    }

    private TextView title, more;
    private LinearLayout infoArea;


    /**
     * 设置标题
     */
    private void setTitle(String title) {
        this.title.setText(title);

    }

    /**
     * 多更多按钮设置点击事件
     *
     * @param onClickListener
     * @param classification
     */
    private void setMoreClick(OnClickListener onClickListener, String classification) {
        if (onClickListener != null) {
            more = findViewById(R.id.more);
            more.setTag(classification);
            more.setOnClickListener(onClickListener);
        }
    }


    private void setDataList(List<SpecificInfo> list, OnClickListener onClickListener) {

        for (SpecificInfo specificInfo : list) {
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_img_text, null);
            itemView.setOnClickListener(onClickListener);
            itemView.setTag(specificInfo);//使用tag存放具体的信息
            Glide.with(getContext()).load(specificInfo.getIcon()).into((ImageView) itemView.findViewById(R.id.itemImg));
            ((TextView) itemView.findViewById(R.id.itemText)).setText(specificInfo.getTitle());
            ((TextView) itemView.findViewById(R.id.content)).setText(specificInfo.getContent());
            infoArea.addView(itemView);
        }
    }

    /**
     * 设置所有的数据
     *
     * @param onClickListener    点击事件
     * @param m_speinf_speinfCla 封装对象
     */
    public void setAllSet(OnClickListener onClickListener, ComplexModelSet.M_speinf_speinfCla m_speinf_speinfCla) {
        if (m_speinf_speinfCla == null) {
            return;
        }
        setTitle(m_speinf_speinfCla.title);
        setMoreClick(onClickListener, m_speinf_speinfCla.title);
        if (m_speinf_speinfCla.specificInfos == null) {
            return;
        }
        setDataList(m_speinf_speinfCla.specificInfos, onClickListener);
    }


}
