package com.rcs.nchumanity.fragment;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DebugUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.rcs.nchumanity.R;
import com.rcs.nchumanity.tool.DensityConvertUtil;
import com.rcs.nchumanity.view.MyVideoView;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.AUDIO_SERVICE;

/**
 * 实现音频播放的Fragment
 * <p>
 * 1，需要暴露的接口有 视频的播放设置url
 * 2.设置当前的view的可见性
 * <p>
 * 当前的fragment支持横屏的操作
 * <p>
 * 通过在  ativity.
 *
 * @Override public void onConfigurationChanged(Configuration newConfig) {
 * super.onConfigurationChanged(newConfig);
 * Log.d("test1", "onConfigurationChanged: ===----");
 * videoPlayFragment.changeLayoutParams();
 * }
 * <p>
 * 可以实现横屏的操作
 * <p>
 * 当然对应的Activity需要设置
 * <activity android:name=".VideoPlayActivity"
 * android:configChanges="orientation|screenSize|keyboard|keyboardHidden" >
 */
public class VideoPlayFragment extends Fragment {


    //需要竖屏隐藏的音量title
    @BindView(R.id.tv_vol_name)
    TextView mTvVolName;
    //徐奥竖屏隐藏的音量分割线
    @BindView(R.id.v_line)
    View mVLine;
    //最外层的布局
    @BindView(R.id.rl_videolayout)
    RelativeLayout mRlVideolayout;
    //VideoView
    @BindView(R.id.vv_videoView)
    MyVideoView mVvVideoView;
    //进程进度条
    @BindView(R.id.sb_progress_seekbar)
    SeekBar mSbProgressSeekbar;
    //播放 暂停
    @BindView(R.id.bt_start_pause)
    ImageButton mBtStartPause;
    //现在的时间
    @BindView(R.id.tv_time_current)
    TextView mTvTimeCurrent;
    //总共的时间
    @BindView(R.id.tv_time_total)
    TextView mTvTimeTotal;
    //音量进度条
    @BindView(R.id.sb_vol_seekbar)
    SeekBar mSbVolSeekbar;
    //全屏切换开关
    @BindView(R.id.bt_switch)
    ImageButton mBtSwitch;
    //控制区域
    @BindView(R.id.ll_controllerBar_layout)
    LinearLayout mLlControllerBarLayout;
    //控制区域左半边
    @BindView(R.id.ll_left_layout)
    LinearLayout mLlLeftLayout;
    //控制区域右半边
    @BindView(R.id.ll_right_layout)
    LinearLayout mLlRightLayout;
    @BindView(R.id.operation_bg)
    ImageView mOperationBg;
    @BindView(R.id.operation_percent)
    View mOperationPercent;
    @BindView(R.id.fl_content)
    LinearLayout mFlContent;
    //butterknife
    Unbinder mUnbinder;
    //定义两个变量：代表当前屏幕的宽和屏幕的高
    private int screen_width, screen_height;
    //刷新机制的标志
    private static final int UPDATE_UI = 1;
    //初始化音频管理器
    private AudioManager mAudioManager;
    //横竖屏变量
    private boolean isFullScreen = false;
    //是否错误触摸的变量
    private boolean isEMove = false;
    //错误触摸临界值
    private int Num = 5;
    //上次的坐标值
    private float lastX;
    private float lastY;
    //生命一个亮度值
    private float mBrightness;
    //预览图
    @BindView(R.id.review)
    ImageView reView;

    public int controlHeight = 40;

    public int playerHeight = 300;


    private static String url;


    /**
     * 定义Handler刷新时间
     * 得到并设置当前视频播放的时间
     * 得到并设置视频播放的总时间
     * 设置SeekBar总进度和当前视频播放的进度
     * 并反复执行Handler刷新时间
     * 指定标识用于关闭Handler
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == UPDATE_UI) {

                int currentPosition = mVvVideoView.getCurrentPosition();
                int totalduration = mVvVideoView.getDuration();

                updateTime(mTvTimeCurrent, currentPosition);
                updateTime(mTvTimeTotal, totalduration);

                mSbProgressSeekbar.setMax(totalduration);
                mSbProgressSeekbar.setProgress(currentPosition);

                mHandler.sendEmptyMessageDelayed(UPDATE_UI, 500);

            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_video, null);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mVvVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mBtStartPause.setImageResource(R.drawable.ic_bofang3);
                if (listener != null) {
                    listener.onCall(mVvVideoView.isPlaying());
                }
            }
        });

        initScreenWidthAndHeight();
        initAudioManager();
        synchScrollSeekBarAndVol();

        //这个路径需要传递过来，
        synchScrollSeekBarAndTime();
        initGesture();
    }

    /**
     * 设置 视频播放的URl
     *
     * @param url
     */
    public void setUrl(String url) {
        Log.d("test", "setUrl: ==视频的url=" + url);
        this.url = url;
        if (null == url) {
            return;
        }
        initNetVideoPath();
        initVideoPlay();

    }

    /**
     * 初始化手势
     */
    private void initGesture() {
        mVvVideoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //现在的x,y坐标
                float x = event.getX();
                float y = event.getY();

                switch (event.getAction()) {
                    //手指按下:
                    case MotionEvent.ACTION_DOWN:
                        lastX = x;
                        lastY = y;
                        break;
                    //手指移动:
                    case MotionEvent.ACTION_MOVE:
                        //偏移量
                        float moveX = x - lastX;
                        float moveY = y - lastY;
                        //计算绝对值
                        float absMoveX = Math.abs(moveX);
                        float absMoveY = Math.abs(moveY);
                        //手势合法性的验证
                        if (absMoveX > Num && absMoveY > Num) {
                            if (absMoveX < absMoveY) {
                                isEMove = true;
                            } else {
                                isEMove = false;
                            }
                        } else if (absMoveX < Num && absMoveY > Num) {
                            isEMove = true;
                        } else if (absMoveX > Num && absMoveY < Num) {
                            isEMove = false;
                        }
                        /**
                         * 区分手势合法的情况下，区分是去调节亮度还是去调节声音
                         */
                        if (isEMove) {
                            //手势在左边
                            if (x < screen_width / 2) {
                                /**
                                 * 调节亮度
                                 */
                                if (moveY > 0) {
                                    //降低亮度
                                } else {
                                    //升高亮度
                                }
                                changeBright(-moveY);
                                //手势在右边
                            } else {
                                Log.e("Emove", "onTouch: " + "手势在右边");
                                /**
                                 * 调节音量
                                 */
                                if (moveY > 0) {
                                    //减小音量
                                } else {
                                    //增大音量
                                }
                                changeVolume(-moveY);
                            }
                        }
                        lastX = x;
                        lastY = y;
                        break;
                    //手指抬起:
                    case MotionEvent.ACTION_UP:
                        mFlContent.setVisibility(View.GONE);
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 获取屏幕的宽和屏幕的高
     */
    private void initScreenWidthAndHeight() {
        screen_width = getResources().getDisplayMetrics().widthPixels;
        screen_height = getResources().getDisplayMetrics().heightPixels;
    }


    /**
     * 初始化网络路径
     */
    private void initNetVideoPath() {
        mVvVideoView.setVideoURI(Uri.parse(url));
    }


    /**
     * 初始化本地路径
     */
//    private void initLocalVideoPath() {
//        File file = new File(Environment.getExternalStorageDirectory(), "video.mp4");
//        mVvVideoView.setVideoPath(file.getPath());
//    }

    /**
     * 初始化音频管理器;获取设备最大音量和当前音量并设置
     */
    private void initAudioManager() {
        mAudioManager = (AudioManager) getContext().getSystemService(AUDIO_SERVICE);
        int streamMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mSbVolSeekbar.setMax(streamMaxVolume);
        mSbVolSeekbar.setProgress(streamVolume);
    }

    private Bitmap bitmap = null;

    /**
     * 初始化播放以及开始刷新时间机制
     */
    private void initVideoPlay() {
        //设置一个预览图片
        Executors.newSingleThreadExecutor().execute(() -> {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            try {
                //这里要用FileProvider获取的Uri
                if (url.contains("http")) {
                    retriever.setDataSource(url, new HashMap<String, String>());
                } else {
                    retriever.setDataSource(url);
                }
                bitmap = retriever.getFrameAtTime();
            } catch (Exception ex) {
                ex.printStackTrace();


            } finally {
                try {
                    retriever.release();
                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                }
            }
            new Handler(Looper.getMainLooper()).post(() -> {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                if (bitmap != null) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] bytes = baos.toByteArray();
                    if (getContext() != null) {
                        Glide.with(this.getContext()).load(bytes).into(reView);
                    }
                }
            });
        });

        //第一个参数是标志，第二个参数是刷新间隔时间
        mHandler.sendEmptyMessageDelayed(UPDATE_UI, 500);
    }

    /**
     * 拖动音量SeekBar同步视频的音量
     */
    private void synchScrollSeekBarAndVol() {
        mSbVolSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //设置当前设备的音量;参数：类型、进程、标志
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /**
     * 拖动SeekBar同步SeekBar和Time和VideoView
     */
    private void synchScrollSeekBarAndTime() {
        mSbProgressSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //进步改变的时候同步Time
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTime(mTvTimeCurrent, progress);
            }

            //拖动的时候关闭刷新机制
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mHandler.removeMessages(UPDATE_UI);
            }

            //同步VideoView和拖动停止开启刷新机制
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                mVvVideoView.seekTo(progress);
                mHandler.sendEmptyMessage(UPDATE_UI);
            }
        });
    }


    @OnClick({R.id.bt_start_pause, R.id.bt_switch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //控制视频的播放和暂停
            case R.id.bt_start_pause:
                if (mVvVideoView.isPlaying()) {
                    mBtStartPause.setImageResource(R.drawable.ic_bofang3);
                    mVvVideoView.pause();
                    //停止刷新UI
                    mHandler.removeMessages(UPDATE_UI);

                    if (listener != null) {
                        listener.onCall(false);
                    }

                } else {
                    mBtStartPause.setImageResource(R.drawable.ic_pause);
                    mVvVideoView.start();
                    //开启刷新UI
                    mHandler.sendEmptyMessage(UPDATE_UI);
                    //如果预览图没有被隐藏，就去隐藏
                    if (reView.getVisibility() == View.VISIBLE) {
                        reView.setVisibility(View.GONE);
                    }

                    if (listener != null) {
                        listener.onCall(true);
                    }
                }

                break;
            //手动横竖屏切换
            case R.id.bt_switch:
                if (isFullScreen) {
                    //切换为竖屏
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else {
                    //切换为横屏
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
                break;
        }
    }

    /**
     * 改变布局参数
     */
    public void changeLayoutParams() {
        //当屏幕方向是横屏的时候,我们应该对VideoView以及包裹VideoView的布局（也就是对整体）进行拉伸
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {


            setVideoViewScale(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            //横屏的时候显示
            mTvVolName.setVisibility(View.VISIBLE);
            mVLine.setVisibility(View.VISIBLE);
            mSbVolSeekbar.setVisibility(View.VISIBLE);
            //横屏的时候为true
            isFullScreen = true;
            //主动取消半屏，该设置为全屏
            getActivity().getWindow().clearFlags((WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN));
            getActivity().getWindow().addFlags((WindowManager.LayoutParams.FLAG_FULLSCREEN));

        }
        //当屏幕方向是竖屏的时候，竖屏的时候的高我们需要把dp转为px
        else {
            setVideoViewScale(ViewGroup.LayoutParams.MATCH_PARENT, DensityConvertUtil.dpi2px(getContext(), playerHeight));
            //竖屏的时候吟唱
            mTvVolName.setVisibility(View.GONE);
            mVLine.setVisibility(View.GONE);
            mSbVolSeekbar.setVisibility(View.GONE);
            //竖屏的时候为
            isFullScreen = false;
            //主动取消全屏，该设置为半屏
            getActivity().getWindow().clearFlags((WindowManager.LayoutParams.FLAG_FULLSCREEN));
            getActivity().getWindow().addFlags((WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN));
        }
    }

    /**
     * 设置VideoView和最外层相对布局的宽和高
     *
     * @param width  : 像素的单位
     * @param height : 像素的单位
     */
    private void setVideoViewScale(int width, int height) {
        //获取VideoView宽和高
        ViewGroup.LayoutParams layoutParams = mVvVideoView.getLayoutParams();
        //赋值给VideoView的宽和高
        layoutParams.width = width;
        if (height == -1) {
            height = getResources().getDisplayMetrics().heightPixels;
        }
        layoutParams.height = height - DensityConvertUtil.dpi2px(getContext(), controlHeight);

        //设置VideoView的宽和高
        mVvVideoView.setLayoutParams(layoutParams);
        //定义预览图的布局
        reView.setLayoutParams(layoutParams);


        //同上
        ViewGroup.LayoutParams layoutParams1 = mRlVideolayout.getLayoutParams();
        layoutParams1.width = width;
        layoutParams1.height = height;

        Log.d("test", "setVideoViewScale: " + width + "\t\t" + height);

        mRlVideolayout.setLayoutParams(layoutParams1);
    }

    /**
     * 调节音量:偏移量和音量值的换算
     */
    private void changeVolume(float moveY) {
        int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int current = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int index = (int) (moveY / screen_height * max * 3);
        int volume = Math.max(current + index, 0);
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
        if (mFlContent.getVisibility() == View.GONE) mFlContent.setVisibility(View.VISIBLE);
        mOperationBg.setImageResource(R.mipmap.ic_vol);
        ViewGroup.LayoutParams layoutParams = mOperationPercent.getLayoutParams();
        layoutParams.width = (int) (DensityConvertUtil.dpi2px(getContext(), 94) * (float) volume / max);
        mOperationPercent.setLayoutParams(layoutParams);
        mSbVolSeekbar.setProgress(volume);
    }

    /**
     * 调节亮度:
     */
    private void changeBright(float moveY) {
        WindowManager.LayoutParams attributes = getActivity().getWindow().getAttributes();
        mBrightness = attributes.screenBrightness;
        float index = moveY / screen_height / 3;
        mBrightness += index;
        //做临界值的判断
        if (mBrightness > 1.0f) {
            mBrightness = 1.0f;
        }
        if (mBrightness < 0.01) {
            mBrightness = 0.01f;
        }
        attributes.screenBrightness = mBrightness;
        if (mFlContent.getVisibility() == View.GONE) mFlContent.setVisibility(View.VISIBLE);
        mOperationBg.setImageResource(R.mipmap.bright);
        ViewGroup.LayoutParams layoutParams = mOperationPercent.getLayoutParams();
        layoutParams.width = (int) (DensityConvertUtil.dpi2px(getContext(), 94) * mBrightness);
        mOperationPercent.setLayoutParams(layoutParams);
        getActivity().getWindow().setAttributes(attributes);
    }

    /**
     * 时间的格式化并更新时间
     *
     * @param textView
     * @param millisecond
     */
    public void updateTime(TextView textView, int millisecond) {
        int second = millisecond / 1000; //总共换算的秒
        int hh = second / 3600;  //小时
        int mm = second % 3600 / 60; //分钟
        int ss = second % 60; //时分秒中的秒的得数

        String str = null;
        if (hh != 0) {
            //如果是个位数的话，前面可以加0  时分秒
            str = String.format("%02d:%02d:%02d", hh, mm, ss);
        } else {
            str = String.format("%02d:%02d", mm, ss);
        }
        textView.setText(str);
    }


    @Override
    public void onPause() {
        super.onPause();
        //停止刷新UI
        mHandler.removeMessages(UPDATE_UI);
    }

    /**
     * 释放资源
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        if (mVvVideoView != null) {
            mVvVideoView.suspend();
        }
    }

    public void setVisiblity(int visiblity) {
        mRlVideolayout.setVisibility(visiblity);
    }


    public void setPlayListener(PlayingListener listener) {
        this.listener = listener;
    }

    private PlayingListener listener;


    public interface PlayingListener {
        void onCall(boolean isplay);
    }
}
