package com.xiaofangfang.lifetatuor.Activity.fragment.weather.Fragment.weatherInfoRichuViewPagerFragment;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.xiaofangfang.lifetatuor.R;
import com.xiaofangfang.lifetatuor.tools.DataConvert;
import com.xiaofangfang.lifetatuor.tools.Looger;
import com.xiaofangfang.lifetatuor.tools.UiThread;
import com.xiaofangfang.lifetatuor.view.RichuView;

import java.util.List;


public class RichuPagerFragment extends Fragment {


    private  DaySunTime dst;

    public RichuPagerFragment() {
        super();
    }

    @DrawableRes
    private int imgResId;
    /**
     * 传递的还有使用的图标,因为使用的是两套图标,一套是白天的,一套是黑夜的
     * @param imgResId
     */
    @SuppressLint("ValidFragment")
    public RichuPagerFragment(@DrawableRes  int imgResId,DaySunTime dst) {
        super();
        this.imgResId=imgResId;
        this.dst=dst;
        this.dst=DataConvert.dateCal(dst);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       ViewGroup viewGroup= (ViewGroup) inflater.inflate(R.layout.richupager_fragment,
               container,false);
       initView(viewGroup);
        return viewGroup;
    }

    ImageView sunImg;
    RichuView richuView;
    private Thread thread;
    private boolean isRun=false;

    private void initView(ViewGroup viewGroup) {
        richuView=viewGroup.findViewById(R.id.richuView);
        sunImg=viewGroup.findViewById(R.id.sunImg);
        sunImg.setImageResource(imgResId);
        richuView.setDaySunTime(dst);
        richuView.setViewDeminsionListener(new RichuView.ViewDeminsionListener() {
            @Override
            public void getPointList(List<Point> points) {
                //在这里将会获得三个位置   开始位置   控制点位置   结束位置
                Point startPoint=points.get(0);
                Point controllPoint=points.get(1);
                Point endPoint=points.get(2);
                Looger.d("得到位置"+startPoint.x+";;;;y="+startPoint.y);
                Looger.d("得到位置"+controllPoint.x+";;;;y="+controllPoint.y);
                Looger.d("得到位置"+endPoint.x+";;;;y="+endPoint.y);
               // sunImg.scrollBy(-startPoint.x,-startPoint.y);
                final FrameLayout.MarginLayoutParams mlp= (FrameLayout.MarginLayoutParams) sunImg.getLayoutParams();

                mlp.leftMargin=startPoint.x-45;
                mlp.topMargin=startPoint.y-40;
                //这里将边距进行减,主要的原因是我们在使用的时候,图片之间是有边距的
                //同事自定义圆的坐使用的是中心点进行定位的,但是我们在设置布局参数的时候使用是是
                //最左边的坐标
                sunImg.setLayoutParams(mlp);

                //下面是执行动画的实现过程
                //起始点的x坐标
                final float[] xPoints = new float[] { 0,controllPoint.x, endPoint.x };
                final float[] yPoints = new float[] {  0,controllPoint.y, endPoint.y };

                final float fps = 300 ;
               thread= new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(100);
                        if(!isRun) {
                            float t;
                            for (int i = 0; i <= fps; i++) {
                                t = i / fps;
                                float rate;
                                if((rate=calculateTime())!=1){
                                   if(i>=rate*fps){
                                       Looger.d(rate*fps+"");
                                       break;
                                   }
                                }

                                final float x = DataConvert.calculateBuzier(t, xPoints);
                                final float y = DataConvert.calculateBuzier(t, yPoints);
                                // 使用链接的方式，当小变动足够小的情况，就是平滑曲线
                                mlp.leftMargin = (int) (x - 45);
                                mlp.topMargin = (int) (y - 40);

                                Looger.d("动画执行的x="+x+"   y:"+y);
                                UiThread.getUiThread(getContext()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        sunImg.setLayoutParams(mlp);

                                        //执行结束后
                                        Looger.d("执行动画结束");
                                      //  isRun=false;
                                    }
                                });

                            }
                        }
                        isRun=false;
                    }
                });
            thread.start();
            }
        });


        //下面是对自定义视图的设置

    }

    private float calculateTime() {
        //获得当前的时间
        float rate;
       DaySunTime daySunTime= DataConvert.obtainCurrentTime();
       int luohours= Integer.parseInt(daySunTime.luoTime.split(":")[0]);
       int shenghours=Integer.parseInt(dst.shengTime.split(":")[0]);
        int luomhours=Integer.parseInt(dst.luoTime.split(":")[0]);
       if(luohours<shenghours){
           rate=1;
       }else if(luohours>luomhours){
           rate=1;
       }else {
           daySunTime.shengTime = dst.shengTime;
           daySunTime = DataConvert.dateCal(daySunTime);
           rate = dst.totalTime / (float) daySunTime.totalTime;
       }
        return rate;
    }




    /**
     * 执行太阳运动的动画
     */
    public void startSunAnimation(){
        if(thread!=null){
            thread.start();
        }
    }



    /**
     * 当进入前台时,我们执行动画特效
     */
    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


   public static class DaySunTime{
        /**
         *升的天气
         */
      public  String shengTime;
        /**
         * 落的天气
         */
       public String luoTime;
       /**
        * 两时间的时差 计算的值是按照分钟进行统计的
        */
       public int totalTime;

       @Override
       public String toString() {
           return "DaySunTime{" +
                   "shengTime='" + shengTime + '\'' +
                   ", luoTime='" + luoTime + '\'' +
                   '}';
       }
   }







}
