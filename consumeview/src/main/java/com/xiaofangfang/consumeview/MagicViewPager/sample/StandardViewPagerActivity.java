package com.xiaofangfang.consumeview.MagicViewPager.sample;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xiaofangfang.consumeview.MagicViewPager.AlphaPageTransformer;
import com.xiaofangfang.consumeview.MagicViewPager.NonPageTransformer;
import com.xiaofangfang.consumeview.MagicViewPager.RotateDownPageTransformer;
import com.xiaofangfang.consumeview.MagicViewPager.RotateUpPageTransformer;
import com.xiaofangfang.consumeview.MagicViewPager.RotateYTransformer;
import com.xiaofangfang.consumeview.MagicViewPager.ScaleInTransformer;
import com.xiaofangfang.consumeview.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class StandardViewPagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private PagerAdapter mAdapter;

    int[] imgRes = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d,
            R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_viewpager);

        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

        mViewPager.setPageMargin(40);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mAdapter = new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView view = new ImageView(StandardViewPagerActivity.this);
//                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                view.setLayoutParams(lp);
//                view.setText(position + ":" + view);
                view.setScaleType(ImageView.ScaleType.FIT_XY);
//                view.setBackgroundColor(Color.parseColor("#44ff0000"));
                view.setImageResource(imgRes[position]);
                container.addView(view);
//                view.setAdjustViewBounds(true);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public int getCount() {
                return imgRes.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }
        });
        mViewPager.setPageTransformer(true, new AlphaPageTransformer());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        String[] effects = this.getResources().getStringArray(R.array.magic_effect);
        for (String effect : effects)
            menu.add(effect);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String title = item.getTitle().toString();
        mViewPager.setAdapter(mAdapter);

        if ("RotateDown".equals(title)) {
            mViewPager.setPageTransformer(true, new RotateDownPageTransformer());
        } else if ("RotateUp".equals(title)) {
            mViewPager.setPageTransformer(true, new RotateUpPageTransformer());
        } else if ("RotateY".equals(title)) {
            mViewPager.setPageTransformer(true, new RotateYTransformer(45));
        } else if ("Standard".equals(title)) {
//            mViewPager.setClipChildren(false);
            mViewPager.setPageTransformer(true, NonPageTransformer.INSTANCE);
        } else if ("Alpha".equals(title)) {
//            mViewPager.setClipChildren(false);
            mViewPager.setPageTransformer(true, new AlphaPageTransformer());
        } else if ("ScaleIn".equals(title)) {
            mViewPager.setPageTransformer(true, new ScaleInTransformer());
        } else if ("RotateDown and Alpha".equals(title)) {
            mViewPager.setPageTransformer(true, new RotateDownPageTransformer(new AlphaPageTransformer()));
        } else if ("RotateDown and Alpha And ScaleIn".equals(title)) {
            mViewPager.setPageTransformer(true, new RotateDownPageTransformer(new AlphaPageTransformer(new ScaleInTransformer())));
        }

        setTitle(title);

        return true;
    }
}
