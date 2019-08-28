package com.kangren.cpr;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kanren.cpr.R;
import com.kangren.cpr.entity.OprateRecordGroup;
import com.kangren.cpr.entity.OprateRecordTable;
import com.kangren.cpr.entity.Score;
import com.kangren.cpr.entity.ScoreReceiveMessage;
import com.kangren.cpr.entity.Setting;
import com.kangren.cpr.receiveMessage.BatteryReceiveMessage;
import com.kangren.cpr.receiveMessage.BlowReceiveMessage;
import com.kangren.cpr.receiveMessage.OperationBeforeReceiveMessage;
import com.kangren.cpr.receiveMessage.OperationBeforeType;
import com.kangren.cpr.receiveMessage.PlayBackEndOrStartMessage;
import com.kangren.cpr.receiveMessage.PressReceiveMessage;
import com.kangren.cpr.receiveMessage.RaiseHeadReceiveMessage;
import com.kangren.cpr.sendMessage.CprSendMessage;
import com.kangren.cpr.viewModel.CprFlow;
import com.kangren.cpr.viewModel.CprOneCycleData;
import com.kangren.cpr.viewModel.OneBlowCycle;
import com.kangren.cpr.viewModel.OnePressCycle;
import com.kangren.cpr.viewModel.helper.CprOneCycleHelper;
import com.kangren.cpr.viewModel.helper.IOneCycle;
import com.kangren.cpr.viewModel.helper.SoundType;
import com.kangren.draw.DrawBlowLineView;
import com.kangren.draw.DrawLineView;
import com.kangren.draw.DrawPrintForBlow;
import com.kangren.draw.DrawPrintForPress;
import com.kangren.draw.DrawRectView;
import com.kangren.language.Chinese;
import com.kangren.language.English;
import com.kangren.language.Language;
import com.kangren.services.DataService;
import com.kangren.services.DataService.IReceiver;
import com.kangren.services.PlayBackDataService;
import com.lyc.hybird.ExtWebView;
import com.lyc.utils.BitmapUtil;
import com.lyc.utils.JsonUtil;
import com.lyc.utils.LogUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@SuppressLint("NewApi")
public class CprFragment extends Fragment implements OnClickListener {


    //����״̬
    private boolean neckFlag;
    private boolean isLive;
    private DrawPrintForBlow printBlow;
    private DrawPrintForPress printPress;
    private boolean playBackStarted = false;
    private boolean playBackPauseStarted = false;
    private boolean cprStarted = false;
    private boolean showCpr = false;
    private CprFlow cprFlow;
    private EditText pingyuEdit;
    private TimeThread timeThread = new TimeThread();
    private AppConfig appConfig;

    /**
     * startBtn  开始按钮
     * flowBtn CPR详情
     * saveBtn saveBtn
     */
    private Button startBtn, flowBtn, saveBtn, playBackStartBtn, playBackPauseBtn, playBackFlowBtn;
    private MainActivity ac;
    private Setting cprSetting;
    private IReceiver iReceiver;

    private boolean canBeBlow = true;
    private boolean canBePress = true;

    private CprOneCycleHelper cprHelper;

    private CprType cprType = CprType.shizhan;
    private OnePressCycle pressCycle, lastCycle;//��ѹһ��

    private OneBlowCycle blowCycle;//����һ��
    //���� ��ǰ ����
    private CprOneCycleData totalData;

    private Language lan;
    private CprOneCycleData cData, lastData;
    private List<CprOneCycleData> history;//���β�������

    private LayoutInflater inflater;
    private ViewGroup container;
    private DataService ds;
    private View moxingren;
    private ExtWebView cprWebView;
    private SoundPool soundPool;
    private HashMap<Integer, Integer> spMap;
    private Score playBackScore;

    private TextView anyapinlv;
    private Score lastScore;
    private ScoreReceiveMessage lastScoreReceiveMessage;
    private DrawRectView blowDrawRectView, pressDrawRectView;
    private DrawLineView pressDrawLineView;
    private DrawBlowLineView blowDrawLineView;

    //�ط�
    private ScoreReceiveMessage playBackSrm;
    private boolean isPlayBack = false;


    public CprFragment() {

    }

    @SuppressLint("ValidFragment")
    public CprFragment(CprType cprType) {
        this.cprType = cprType;


    }

    @SuppressLint("ValidFragment")
    public CprFragment(Score score, ScoreReceiveMessage srm) {
        this.playBackScore = score;
        this.playBackSrm = srm;
        this.isPlayBack = true;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;

        View view = inflater.inflate(R.layout.fragment_cpr, container, false);


        initVar();
        findViews(view);
        cprWebView.loadUrl("file:///android_asset/cprForDetail.html#/cpr/cprDataDetail");
        startBtn.setOnClickListener(this);
        flowBtn.setOnClickListener(this);
        flowBtn.setVisibility(View.GONE);
        saveBtn.setOnClickListener(this);
        playBackPauseBtn.setOnClickListener(this);
        playBackStartBtn.setOnClickListener(this);
        playBackFlowBtn.setOnClickListener(this);
        blowDrawRectView.setOrientation(1);
        blowDrawLineView.setPass(cprSetting.chuiqiliangMin);
        blowDrawLineView.setErrorPass(cprSetting.chuiqiliangMax);
        pressDrawLineView.setPass(cprSetting.anyashenduMin);
        pressDrawLineView.setErrorPass(cprSetting.anyashenduMax);
        //�ж��Ƿ���Դ������߰�ѹ
        checkChuianshunxu();


        //��ֹ�ϴο���δֹͣCPR
        stopCpr();
        ac.updateTitle(getCprTypeName());
        ac.updateTitle2(cprSetting.time + "");

        if (isPlayBack) {
            view.findViewById(R.id.cprGroup).setVisibility(View.GONE);
            view.findViewById(R.id.playBackGroup).setVisibility(View.VISIBLE);
            initPlayBackDataService();
            PlayBackDataService.getInstance().setReceiveMsg(playBackSrm);

            ac.updateTitle(this.playBackScore.exam_type);
            ac.updateElapsedTimeTitle(this.lan.haoshi + this.playBackSrm.elapsedTime + this.lan.miao);
            this.cprType = getCprType(this.playBackScore.exam_type);
            cprHelper.setCprType(this.cprType);
        } else {
            /**
             * 初始化数据连接服务，用来实现套接字向数据库发送数据
             */
            initDataService();
        }
        return view;
    }


    private String getCprTypeName() {
        if (this.cprType == CprType.kaohe)
            return this.lan.kaohemoshi;
        else if (this.cprType == CprType.xunlian)
            return this.lan.xunlianmoshi;
        else
            return this.lan.shizhanmoshi;
    }

    private CprType getCprType(String name) {
        if (name.equals(this.lan.kaohemoshi))
            return CprType.kaohe;
        else if (name.equals(this.lan.xunlianmoshi))
            return CprType.xunlian;
        else
            return CprType.shizhan;


    }


    /**
     * 检查吹按顺序
     */
    private void checkChuianshunxu() {
        if (cprType == CprType.kaohe) {
            if (cprSetting.anchuishunxu == 0) {
                canBeBlow = true;
                canBePress = false;
            } else {
                canBeBlow = false;
                canBePress = true;
            }
        }
    }

    private void updateCanBePress() {
        if (cprType == cprType.kaohe) {
            if (cData.blow_zhengque >= cprSetting.chuiqicishu)
                canBePress = true;
            else

                canBePress = false;
        }
		/*	if(cprType==cprType.shizhan){
				if(cData.blow_zongshu>=cprSetting.chuiqicishu)
					canBePress=true;
				else

					canBePress=false;
			}*/
    }

    private void updateCanBeBlow() {
        if (cprType == CprType.kaohe) {
            if (cData.press_zhengque >= cprSetting.anyacishu)
                canBeBlow = true;
            else
                canBeBlow = false;
        }
		/*	if(cprType==CprType.shizhan){
				if(cData.press_zongshu>=cprSetting.anyacishu)
					canBeBlow=true;
				else
					canBeBlow=false;
			}*/
    }

    private void addTotalData() {

        if (lastData == null) {
            lastData = new CprOneCycleData();

        }

        totalData.blow_cuowu = lastData.blow_cuowu + cData.blow_cuowu;
        totalData.blow_duochui = lastData.blow_duochui + cData.blow_duochui;
        totalData.blow_guoda = lastData.blow_guoda + cData.blow_guoda;
        totalData.blow_guoxiao = lastData.blow_guoxiao + cData.blow_guoxiao;
        totalData.blow_shaochui = lastData.blow_shaochui + cData.blow_shaochui;
        totalData.blow_zhengque = lastData.blow_zhengque + cData.blow_zhengque;
        totalData.blow_zongshu = lastData.blow_zongshu + cData.blow_zongshu;
        totalData.blow_jinwei = lastData.blow_jinwei + cData.blow_jinwei;
        totalData.blow_qidaoweidakai = lastData.blow_qidaoweidakai + cData.blow_qidaoweidakai;


        totalData.press_cuowu = lastData.press_cuowu + cData.press_cuowu;
        totalData.press_duoan = lastData.press_duoan + cData.press_duoan;
        totalData.press_guoda = lastData.press_guoda + cData.press_guoda;
        totalData.press_guoxiao = lastData.press_guoxiao + cData.press_guoxiao;
        totalData.press_pinlv = lastData.press_pinlv + cData.press_pinlv;
        totalData.press_shaoan = lastData.press_shaoan + cData.press_shaoan;
        totalData.press_zhengque = lastData.press_zhengque + cData.press_zhengque;
        totalData.press_zongshu = lastData.press_zongshu + cData.press_zongshu;
        totalData.press_huitanbuzu = lastData.press_huitanbuzu + cData.press_huitanbuzu;


    }

    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        ac.updateTitle("");
        ac.updateTitle2("");
        timeThread.goStop();
        DataService.getInstance().removeIreceiver(iReceiver);
        PlayBackDataService.getInstance().removeIreceiver(iReceiver);
        stopCpr();
        super.onDestroyView();
    }


    /**
     * 初始化变量值
     */
    private void initVar() {

        final Resources res = this.getResources();
        appConfig = AppConfig.getInstance();
        ac = (MainActivity) getActivity();
        cprSetting = appConfig.db.getFirst(new Setting());
        if (cprSetting == null) {
            cprSetting = new Setting();
            appConfig.db.add(cprSetting);
        }

        if (cprSetting.language == 0)
            this.lan = new Chinese();
        else
            this.lan = new English();
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        spMap = new HashMap<Integer, Integer>();
        if (cprSetting.language == 0) {
            spMap.put(0, soundPool.load(this.ac, R.raw.anyaguoda, 1));
            spMap.put(1, soundPool.load(this.ac, R.raw.anyabuzu, 1));
            spMap.put(2, soundPool.load(this.ac, R.raw.anyaguoduo, 1));
            spMap.put(3, soundPool.load(this.ac, R.raw.chuiqiguoda, 1));
            spMap.put(4, soundPool.load(this.ac, R.raw.chuiqibuzu, 1));
            spMap.put(5, soundPool.load(this.ac, R.raw.chuiqiguoduo, 1));
            spMap.put(6, soundPool.load(this.ac, R.raw.pinlvguokuai, 1));
            spMap.put(7, soundPool.load(this.ac, R.raw.pinlvguoman, 1));
            spMap.put(8, soundPool.load(this.ac, R.raw.huitanbuzu, 1));
            spMap.put(9, soundPool.load(this.ac, R.raw.chuiqijinwei, 1));
            spMap.put(10, soundPool.load(this.ac, R.raw.qidaoweikai, 1));
        } else {
            spMap.put(0, soundPool.load(this.ac, R.raw.anyaguodaen, 1));
            spMap.put(1, soundPool.load(this.ac, R.raw.anyaguoxiaoen, 1));
            spMap.put(2, soundPool.load(this.ac, R.raw.anyaguoduoen, 1));
            spMap.put(3, soundPool.load(this.ac, R.raw.chuiqiguodaen, 1));
            spMap.put(4, soundPool.load(this.ac, R.raw.chuiqiguoxiaoen, 1));
            spMap.put(5, soundPool.load(this.ac, R.raw.chuiqiguoduoen, 1));
            spMap.put(6, soundPool.load(this.ac, R.raw.pinlvguokuaien, 1));
            spMap.put(7, soundPool.load(this.ac, R.raw.pinlvguomanen, 1));
            spMap.put(8, soundPool.load(this.ac, R.raw.huitanbuzuen, 1));
            spMap.put(9, soundPool.load(this.ac, R.raw.chuiqijinweien, 1));
            spMap.put(10, soundPool.load(this.ac, R.raw.qidaoweikaien, 1));
        }

        totalData = new CprOneCycleData();
        cprFlow = new CprFlow();
        cData = new CprOneCycleData();
        history = new ArrayList<CprOneCycleData>();
        pressCycle = new OnePressCycle();
        blowCycle = new OneBlowCycle();
        cprHelper = new CprOneCycleHelper(cprType, cprSetting);
        cprHelper.SetIOneCycleCompleted(new IOneCycle() {

            /**
             * 按压更新数据
             * @param currentData
             * @param st
             */
            @Override
            public void pressUpdate(CprOneCycleData currentData, final SoundType st) {
                // TODO Auto-generated method stub

                cData = currentData;
                addTotalData();
                ac.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        if (!showCpr)
                            onClick(flowBtn);
                        playSound(st);
                        String st = "javascript:setdata('" + JsonUtil.ToJson(cData) + "','" + JsonUtil.ToJson(totalData) + "')";
                        cprWebView.loadUrl(st);
                        //
                        if (!isPlayBack && cprType != CprType.xunlian && history.size() >= cprSetting.xunhuancishu - 1 && cprSetting.anchuishunxu == 0) {
                            if (cprType == CprType.kaohe && cData.press_zhengque >= cprSetting.anyacishu) {
                                notifyStop();
                                return;
                            } else if (cprType == CprType.shizhan && cData.press_zongshu >= cprSetting.anyacishu) {
                                notifyStop();
                                return;
                            }

                        }

                    }
                });
                updateCanBeBlow();


            }

            /**
             * 完成一个循环
             * @param currentData
             */
            @Override
            public void completed(CprOneCycleData currentData) {
                // TODO Auto-generated method stub

                history.add(currentData);
                cData = currentData;
                addTotalData();
                lastData = new CprOneCycleData(totalData);

                ac.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        if (cprType != CprType.xunlian && !isPlayBack && history.size() >= cprSetting.xunhuancishu) {
                            notifyStop();
                            return;
                        }

                        String st = "javascript:setCycle(" + history.size() + ")";
                        cprWebView.loadUrl(st);
                    }
                });

            }

            @Override
            public void blowUpdate(CprOneCycleData currentData, final SoundType st) {
                // TODO Auto-generated method stub
                cData = currentData;
                addTotalData();
                ac.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        //�а�ѹ���� �Զ��л�
                        if (!showCpr)
                            onClick(flowBtn);
                        lastCycle = null;
                        playSound(st);
                        String st = "javascript:setdata('" + JsonUtil.ToJson(cData) + "','" + JsonUtil.ToJson(totalData) + "')";
                        cprWebView.loadUrl(st);
                        //�ж��Զ��������Ȱ��� ��ʵս��ǰѭ��Ϊ5 ����Ϊ2 ������Ϊ��ȷ2
                        if (!isPlayBack && cprType != CprType.xunlian && history.size() >= cprSetting.xunhuancishu - 1 && cprSetting.anchuishunxu == 1) {
                            if (cprType == CprType.kaohe && cData.blow_zhengque >= cprSetting.chuiqicishu) {
                                notifyStop();
                                return;
                            }
                            if (cprType == CprType.shizhan && cData.blow_zongshu >= cprSetting.chuiqicishu) {
                                notifyStop();
                                return;
                            }
                        }


                    }
                });
                updateCanBePress();
            }
        });

        /**
         *
         */
        iReceiver = new IReceiver() {

            @Override
            public void raiseHead(final RaiseHeadReceiveMessage raiseHead) {

                if (raiseHead == null)
                    return;
                neckFlag = raiseHead.headState;
                if (lastScoreReceiveMessage != null)
                    lastScoreReceiveMessage.msgList.add(raiseHead);
                // TODO Auto-generated method stub
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        if (raiseHead.headState)
                            moxingren.setBackground(res.getDrawable(R.drawable.moxingren_yangtou));
                        else
                            moxingren.setBackground(res.getDrawable(R.drawable.moxingren));
                    }
                });

            }

            /**
             * 当接收到按压数据的时候 ，接受到 的消息会回调到当前的函数
             * @param press
             */
            @Override
            public void press(PressReceiveMessage press) {
                // TODO Auto-generated method stub
                if (!canBePress) {
                    pressDrawRectView.updateY(0);
                    pressDrawLineView.updateY(0);
                    return;
                }
                if (lastScoreReceiveMessage != null)
                    lastScoreReceiveMessage.msgList.add(press);
                //ȥ�����Ų�
                if (press.isIn && press.num < pressCycle.lastPress.num)
                    return;
                if (!press.isIn && press.num > pressCycle.lastPress.num)
                    return;
                if (press.lights < 9) {
                    pressDrawRectView.setLineColor(Color.YELLOW);
                } else if (press.lights < 17) {
                    pressDrawRectView.setLineColor(Color.GREEN);
                } else {
                    pressDrawRectView.setLineColor(Color.RED);
                }
                pressDrawRectView.updateY(press.lights);
                pressDrawLineView.updateY(press.num);
                LogUtil.WriteLog(press.num + "" + press.isIn);
//                Log.d("test", "press: =" + press.isIn + "\t" + press.num);
                if (pressCycle.lastPress.isIn != press.isIn) {

                    //��һ�°���
                    if (press.isIn && pressCycle.startNum == -1) {
                        pressCycle.startTime = press.getTime();
                        pressCycle.startNum = 0;
                    }
                    //�ص�
                    else if (!press.isIn) {
                        pressCycle.deepth = pressCycle.lastPress.num;
                    }
                    //
                    else if (press.isIn) {
                        pressCycle.endNum = pressCycle.lastPress.num;
                        pressCycle.endTime = pressCycle.lastPress.getTime();
                        OnePressCycle one = pressCycle;
                        LogUtil.WriteLog("eeeeee");
                        int pinglv;
                        if (lastCycle == null || pressCycle.endTime - lastCycle.endTime == 0) {
                            pinglv = 100;
                        } else {
                            pinglv = (int) (60 * 1000 / (pressCycle.endTime - lastCycle.endTime));
                        }
                        if (pinglv > cprSetting.anyapinlvMax)
                            pinglv -= 15;
                        if (pinglv < cprSetting.anyapinlvMin)
                            pinglv += 15;
                        pressCycle.pinlv = pinglv;
                        showPinlv(pinglv);
                        //����UI
                        cprHelper.updatePressRecord(pressCycle);
                        pressCycle = new OnePressCycle();
                        pressCycle.startTime = press.getTime();
                        pressCycle.startNum = 0;
                        pressCycle.lastPress = press;
                        lastCycle = pressCycle;
                        return;
                    }
                }
                //�ص���0 ֻ��״̬�л��� ���Ÿ���UI
                if (pressCycle.startNum > -1 && press.num == 0 && !press.isIn) {
                    pressCycle.endNum = 0;
                    pressCycle.endTime = press.getTime();

                    int pinglv;
                    if (lastCycle == null || pressCycle.endTime - lastCycle.endTime == 0) {
                        pinglv = 100;
                    } else {
                        pinglv = (int) (60 * 1000 / (pressCycle.endTime - lastCycle.endTime));
                    }
                    //֮ǰ��Ƶ���㷨	 pinglv=(int) (60*1000/(pressCycle.endTime-pressCycle.startTime))*0.75;
                    if (pinglv > cprSetting.anyapinlvMax)
                        pinglv -= 15;
                    if (pinglv < cprSetting.anyapinlvMin)
                        pinglv += 15;
                    showPinlv(pinglv);
                    pressCycle.pinlv = pinglv;
                    //����UI
                    cprHelper.updatePressRecord(pressCycle);
                    lastCycle = pressCycle;
                    pressCycle = new OnePressCycle();
                    return;
                }

                pressCycle.lastPress = press;


            }

            @Override
            public void operationBefore(OperationBeforeReceiveMessage operationBefore) {
                if (lastScoreReceiveMessage != null)
                    lastScoreReceiveMessage.msgList.add(operationBefore);
                // TODO Auto-generated method stub
                if (operationBefore.operationBeforeType == OperationBeforeType.Yiwu)
                    cprFlow.yiwu = true;

                if (operationBefore.operationBeforeType == OperationBeforeType.Maibo)
                    cprFlow.maibo = true;

                if (operationBefore.operationBeforeType == OperationBeforeType.Hujiao)
                    cprFlow.hujiao = true;

                if (operationBefore.operationBeforeType == OperationBeforeType.Huxi)
                    cprFlow.huxi = true;

                if (operationBefore.operationBeforeType == OperationBeforeType.Yishi)
                    cprFlow.yishi = true;
                ac.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        String st = "javascript:setFlow('" + JsonUtil.ToJson(cprFlow) + "')";
                        cprWebView.loadUrl(st);
                    }
                });
            }

            @Override
            public void blow(BlowReceiveMessage blow) {
                // TODO Auto-generated method stub

                if (!canBeBlow) {
                    blowDrawRectView.updateY(0);
                    blowDrawLineView.updateY(0);
                    return;
                }

                blow.flag = neckFlag;
                if (lastScoreReceiveMessage != null)
                    lastScoreReceiveMessage.msgList.add(blow);
                if (blow.lights < 9) {
                    blowDrawRectView.setLineColor(Color.YELLOW);
                } else if (blow.lights < 17) {
                    blowDrawRectView.setLineColor(Color.GREEN);
                } else {
                    blowDrawRectView.setLineColor(Color.RED);
                }
                blowDrawRectView.updateY(blow.lights);
                blowDrawLineView.updateY(blow.num);
                if (blowCycle.lastBlow.isIn != blow.isIn) {
                    if (blow.isIn && blowCycle.startNum == -1) {
                        blowCycle.startNum = 0;
                        blowCycle.startTime = blow.getTime();
                    }
                    //�ص�
                    else if (!blow.isIn) {
                        blowCycle.deepth = blowCycle.lastBlow.num;
                    }
                    //�ص�����
                    else if (blow.isIn) {
                        blowCycle.endNum = pressCycle.lastPress.num;
                        blowCycle.endTime = pressCycle.lastPress.getTime();

                        //����UI
                        cprHelper.updateBlowRecord(blowCycle);
                        blowCycle = new OneBlowCycle();
                        blowCycle.startTime = pressCycle.lastPress.getTime();
                        blowCycle.startNum = 0;
                        blowCycle.lastBlow = blow;

                        return;
                    }
                }
                //�ص���0 ֻ��״̬�л��� ���Ÿ���UI
                if (blowCycle.startNum > -1 && blow.num == 0 && !blow.isIn) {
                    blowCycle.endNum = 0;
                    blowCycle.endTime = blow.getTime();
                    //����UI
                    cprHelper.updateBlowRecord(blowCycle);
                    blowCycle = new OneBlowCycle();
                    return;
                }

                blowCycle.lastBlow = blow;
            }

            @Override
            public void battery(BatteryReceiveMessage battery) {
                // TODO Auto-generated method stub

            }

            @Override
            public void palyBackCompeled() {
                // TODO Auto-generated method stub
                ac.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        //ȷ�����ڿ�ʼ��״̬�� �����������ʼ����ʱ���Ѿ����ֶ���ֹͣ�� ���߱��ԭ�򣬾���ȷ�����ʱ�� ��ֹͣ��//���overflowstatckerror
                        playBackStarted = true;
                        onClick(playBackStartBtn);
                    }
                });
            }
        };


    }

    private void notifyStop() {
        onClick(startBtn);
        pressDrawLineView.updateY(0);
        blowDrawLineView.updateY(0);
        pressDrawRectView.updateY(0);
        blowDrawRectView.updateY(0);
    }

    private void playSound(SoundType st) {
        //	if(st==SoundType.None|| cprType!=CprType.xunlian)
        //		return;
        if (st == SoundType.None)
            return;
        soundPool.play(spMap.get(st.ordinal() - 1), 1, 1, 0, 0, 1);
    }

    private void initDataService() {

        ds = DataService.getInstance();
        ds.addIreceiver(iReceiver);
    }

    private void initPlayBackDataService() {

        PlayBackDataService.getInstance().addIreceiver(iReceiver);
    }

    private void showPinlv(final int pinlv) {
        ac.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (pinlv > cprSetting.anyapinlvMax || pinlv < cprSetting.anyapinlvMin) {
                    anyapinlv.setTextColor(Color.RED);
                } else {
                    anyapinlv.setTextColor(Color.GREEN);
                }
                anyapinlv.setText(pinlv + "");
            }
        });


    }

    private void findViews(View root) {

        pressDrawLineView = (DrawLineView) root.findViewById(R.id.press);
        blowDrawRectView = (DrawRectView) root.findViewById(R.id.blowRect);
        pressDrawRectView = (DrawRectView) root.findViewById(R.id.pressRect);
        blowDrawLineView = (DrawBlowLineView) root.findViewById(R.id.blow);


        moxingren = root.findViewById(R.id.moxingren);

        cprWebView = (ExtWebView) root.findViewById(R.id.cprView);

        startBtn = (Button) root.findViewById(R.id.startBtn);
        flowBtn = (Button) root.findViewById(R.id.flowBtn);
        saveBtn = (Button) root.findViewById(R.id.saveBtn);
        pingyuEdit = (EditText) root.findViewById(R.id.pingyu);
        anyapinlv = (TextView) root.findViewById(R.id.anyapinlv);

        playBackStartBtn = (Button) root.findViewById(R.id.playBackStartBtn);
        playBackPauseBtn = (Button) root.findViewById(R.id.playBackPauseBtn);

        playBackFlowBtn = (Button) root.findViewById(R.id.playBackFlowBtn);
        //new rectTest().start();

        startBtn.setText(lan.kaishi);
        flowBtn.setText(lan.cprXiangQing);
        saveBtn.setText(lan.baocunchengji);
        TextView pinglv = ((TextView) root.findViewById(R.id.pinglv));
        pinglv.setText(lan.anyapinlv);
        if (cprSetting.language == 1)
            pinglv.setTextSize(14);
        pingyuEdit.setHint(lan.qingshurupingyu);
    }


    class rectTest extends Thread {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            super.run();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            boolean oreing = true;
            int i = 0;
            while (true) {
                pressDrawLineView.updateY(i / 5);
                blowDrawLineView.updateY(i * 20);
                pressDrawRectView.updateY(i);
                blowDrawRectView.updateY(i);
                if (oreing) {
                    i += 5;
                } else
                    i -= 5;
                if (i == 50)
                    oreing = false;
                if (i == 0)
                    oreing = true;
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }

    }


    /**
     * 保存成绩的操作
     */
    private void saveBtnClick() {

        List<OprateRecordTable> tables = new ArrayList<OprateRecordTable>();
        Date date = new Date();
        java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm");


        if (cprType == CprType.kaohe)
            lastScore.exam_type = this.lan.kaohemoshi;
        else if (cprType == CprType.xunlian)
            lastScore.exam_type = this.lan.xunlianmoshi;
        else
            lastScore.exam_type = this.lan.shizhanmoshi;
        lastScore.exam_date = dateFormat.format(date);
        lastScore.exam_data_long = System.currentTimeMillis();


        /**
         * 封装操作记录表
         */
        OprateRecordTable scoreTable = createScoreTable();
        tables.add(scoreTable);
        //获得最后一个记录
        lastScore.score = Integer.parseInt(scoreTable.records[scoreTable.records.length - 1].count);

        /**
         * 第一条记录保存 合计的数据
         */
        tables.add(createHistory(totalData, this.lan.hejicaozuo));
        for (int i = 0; i < history.size(); i++) {
            /**
             * 循环保存5次数据
             */
            CprOneCycleData one = history.get(i);
            tables.add(createHistory(one, this.lan.xunhuan + (i + 1) + ""));
        }
        //添加评语
        tables.add(createPingyu());

        OprateRecordTable[] tablesAr = new OprateRecordTable[tables.size()];
        tables.toArray(tablesAr);
        /**
         *   保存最终的数据
         */
        lastScore.tables = tablesAr;
        ac.lastScore = lastScore;
        ac.lastReceiveMessage = lastScoreReceiveMessage;

        lastScore.path = appConfig.BaseDir + "/print/" + UUID.randomUUID().toString() + ".jpg";
//        savePrintBm(lastScore.path);
        ac.goWebView("oprateRecord");

    }

    private void savePrintBm(String path) {
        Bitmap printBm;
        Bitmap pressBm = printPress.getBitmap();
        Bitmap blowBm = printBlow.getBitmap();
        pressBm = BitmapUtil.scaleByHeight(pressBm, 370 / 2);
        blowBm = BitmapUtil.scaleByHeight(blowBm, 370 / 2);

        printBm = BitmapUtil.compairVertical(pressBm, blowBm);
        printBm = BitmapUtil.rotate(printBm, 90);
        //	printBm=BitmapUtil.scaleByHeight(printBm, 370);
        BitmapUtil.saveBitmap(printBm, path);
    }


    /**
     * 创建评语
     *
     * @return
     */
    private OprateRecordTable createPingyu() {
        OprateRecordTable res = new OprateRecordTable();
        res.title = this.lan.pingyu;
        res.type = 0;
        res.pingyu = pingyuEdit.getText().toString();

        return res;
    }

    /**
     * 一次循环的历史记录
     *
     * @param one
     * @param title
     * @return
     */
    private OprateRecordTable createHistory(CprOneCycleData one, String title) {
        OprateRecordTable res = new OprateRecordTable();
        res.title = title;
        res.type = 1;
        res.records = new OprateRecordGroup[]{
                new OprateRecordGroup(this.lan.anya.replace("<br>", "") + this.lan.zhengquelv, ((int) (one.press_zhengque * 1f / one.press_zongshu * 100)) + "%"),
                new OprateRecordGroup(this.lan.anya.replace("<br>", "") + this.lan.zongshu, one.press_zongshu + ""),
                new OprateRecordGroup(this.lan.anya.replace("<br>", "") + this.lan.zhengque, one.press_zhengque + ""),
                new OprateRecordGroup(this.lan.anya.replace("<br>", "") + this.lan.cuowu, one.press_cuowu + ""),

                new OprateRecordGroup(this.lan.anya.replace("<br>", "") + this.lan.guoda, one.press_guoda + ""),
                new OprateRecordGroup(this.lan.anya.replace("<br>", "") + this.lan.guoxiao, one.press_guoxiao + ""),
                new OprateRecordGroup(this.lan.anya.replace("<br>", "") + this.lan.duoan, one.press_duoan + ""),
                new OprateRecordGroup(this.lan.anya.replace("<br>", "") + this.lan.shaoan, one.press_shaoan + ""),
                new OprateRecordGroup(this.lan.anya.replace("<br>", "") + this.lan.pinlvcuowu.replace("<br>", ""), one.press_pinlv + ""),
                new OprateRecordGroup(this.lan.anya.replace("<br>", "") + this.lan.huitanbuzu.replace("<br>", ""), one.press_huitanbuzu + ""),

                new OprateRecordGroup(this.lan.chuiqi.replace("<br>", "") + this.lan.zhengquelv, ((int) (one.blow_zhengque * 1f / one.blow_zongshu * 100)) + "%"),
                new OprateRecordGroup(this.lan.chuiqi.replace("<br>", "") + this.lan.zongshu, one.blow_zongshu + ""),
                new OprateRecordGroup(this.lan.chuiqi.replace("<br>", "") + this.lan.zhengque, one.blow_zhengque + ""),
                new OprateRecordGroup(this.lan.chuiqi.replace("<br>", "") + this.lan.cuowu, one.blow_cuowu + ""),
                new OprateRecordGroup(this.lan.chuiqi.replace("<br>", "") + this.lan.guoda, one.blow_guoda + ""),
                new OprateRecordGroup(this.lan.chuiqi.replace("<br>", "") + this.lan.guoxiao, one.blow_guoxiao + ""),
                new OprateRecordGroup(this.lan.chuiqi.replace("<br>", "") + this.lan.duochui, one.blow_duochui + ""),
                new OprateRecordGroup(this.lan.chuiqi.replace("<br>", "") + this.lan.shaochui, one.blow_shaochui + ""),
                new OprateRecordGroup(this.lan.chuiqi.replace("<br>", "") + this.lan.chuiqijinwei.replace("<br>", ""), one.blow_jinwei + ""),
                new OprateRecordGroup(this.lan.chuiqi.replace("<br>", "") + this.lan.qidaoweikai.replace("<br>", ""), one.blow_qidaoweidakai + ""),

        };


        return res;
    }

    /**
     * 一个循环  统计的是合计操作信息
     *
     * @return
     */
    private OprateRecordTable createScoreTable() {

        OprateRecordTable res = new OprateRecordTable();
        res.type = 2;
        int yiwu = cprFlow.yiwu ? cprSetting.flowYiwu : 0;
        int maibo = cprFlow.maibo ? cprSetting.flowMaibo : 0;
        int hujiao = cprFlow.hujiao ? cprSetting.flowHujiao : 0;
        int huxi = cprFlow.huxi ? cprSetting.flowHuxi : 0;
        int yishi = cprFlow.yishi ? cprSetting.flowYishi : 0;
        int cpr = isLive ? cprSetting.flowCPR : 0;
        int score = cpr + yiwu + maibo + hujiao + huxi + yishi;


        //������table
        res.title = this.lan.chengjixiangqing;
        res.records = new OprateRecordGroup[]{
                /*new OprateRecordGroup(this.lan.qingchuyiwu, yiwu+""),
                new OprateRecordGroup(this.lan.maibojiancha, maibo+""),
                new OprateRecordGroup(this.lan.jijiuhujiao, hujiao+""),
                new OprateRecordGroup(this.lan.huxijiancha, huxi+""),
                new OprateRecordGroup(this.lan.yishipanduan, yishi+""),	*/
                new OprateRecordGroup(this.lan.CprDefen, cpr + ""),
                new OprateRecordGroup(this.lan.shifoujiuhuo, isLive ? this.lan.shi : this.lan.fou),
                new OprateRecordGroup(this.lan.zongfen, cpr + "")
        };
        return res;

    }

    /**
     * crp详情的显示操作
     */
    private void flowBtnClick() {
        if (showCpr) {
            flowBtn.setText(this.lan.cprXiangQing);
        } else {
            flowBtn.setText(this.lan.pingugxiangqing);
        }

        String st = "javascript:changeShow()";
        cprWebView.loadUrl(st);
        showCpr = !showCpr;
    }

    /**
     * crp详情的显示操作
     */
    private void playBackFlowBtnClick() {
        if (showCpr) {
            playBackFlowBtn.setText(this.lan.cprXiangQing);
        } else {
            playBackFlowBtn.setText(this.lan.pingugxiangqing);
        }

        String st = "javascript:changeShow()";
        cprWebView.loadUrl(st);
        showCpr = !showCpr;
    }

    /**
     * 重置数据
     */
    private void resetVar() {

        lastCycle = null;
        cprFlow = new CprFlow();
        lastScore = new Score();
        lastScoreReceiveMessage = new ScoreReceiveMessage(lastScore.id);
        cprHelper.reset();
        //
        lastData = new CprOneCycleData();
        totalData = new CprOneCycleData();
        cData = new CprOneCycleData();
        history.clear();

        String st = "javascript:reset()";
        cprWebView.loadUrl(st);
        showCpr = false;
        checkChuianshunxu();
    }

    private void startBtnClick() {
        CprSendMessage cpr;

        /**
         * 如果模型人未连接 直接返回
         */
        if (!appConfig.ModelConnected) {
            ((MainActivity) getActivity()).showMsg(this.lan.moxingweilianjie);
            return;
        }
        /**
         * 如果没有开始
         * 重置变量
         *
         */
        if (!cprStarted) {
            resetVar();

            /**
             * 开始记时间
             */
            timeThread = new TimeThread();
            timeThread.start();

            startBtn.setText(this.lan.tingzhi);
            cpr = new CprSendMessage();
            cpr.cprflag = 1;
            cpr.neckflag = 0;
            cpr.eyeflag = 0;
            /**
             * 更新名称为 考试中
             */
            ac.updateTitle(getCprTypeName() + "-" + this.lan.kaoshizhong);
            flowBtn.setBackground(getResources().getDrawable(R.drawable.btgreen));
            flowBtn.setEnabled(true);

            saveBtn.setEnabled(false);
            saveBtn.setBackground(getResources().getDrawable(R.drawable.unactive));
            printBlow = new DrawPrintForBlow(blowDrawLineView);
            printPress = new DrawPrintForPress(pressDrawLineView);
            /**
             * 进行动画的播放展示
             */
            printPress.start();
            printBlow.start();
            //添加一个开始消息
            PlayBackEndOrStartMessage endMsg = new PlayBackEndOrStartMessage();
            endMsg.setTime(System.currentTimeMillis());
            lastScoreReceiveMessage.msgList.add(endMsg);
        } else {
            //�Ƿ�Ȼ�

            printPress.stop();
            printBlow.stop();
            //	BitmapUtil.saveBitmap(printBlow.getBitmap(), Environment.getExternalStorageDirectory()+"/ttt.png");
            //	BitmapUtil.saveBitmap(printPress.getBitmap(), Environment.getExternalStorageDirectory()+"/ttt2.png");

            if (history.size() >= cprSetting.xunhuancishu - 1 && totalData.press_zhengque * 1f / totalData.press_zongshu * 100 >= cprSetting.anyahegelv
                    && totalData.blow_zhengque * 1f / totalData.blow_zongshu * 100 >= cprSetting.chuiqihegelv)
                isLive = true;
            //��ӻطŽ����ź�
            PlayBackEndOrStartMessage endMsg = new PlayBackEndOrStartMessage();
            endMsg.setTime(System.currentTimeMillis());
            lastScoreReceiveMessage.msgList.add(endMsg);
            lastScoreReceiveMessage.elapsedTime = cprSetting.time - timeThread.time;
            ac.updateTitle(getCprTypeName());
            history.add(cData);
            startBtn.setText(this.lan.kaishi);
            cpr = new CprSendMessage();
            cpr.cprflag = 0;
            cpr.neckflag = isLive ? 1 : 0;
            cpr.eyeflag = isLive ? 1 : 0;
            saveBtn.setEnabled(true);
            saveBtn.setBackground(getResources().getDrawable(R.drawable.btgreen));
            timeThread.goStop();
            ac.showMsg(isLive ? this.lan.chenggongjiuhuo : this.lan.weijiuhuo);

        }
        /**
         * 向服务器发送一个基本的消息
         *
         * 代表 开始
         */
        DataService.getInstance().send(cpr);
        cprStarted = !cprStarted;
    }


    private void stopCpr() {
        if (!appConfig.ModelConnected)
            return;
        CprSendMessage cpr = new CprSendMessage();
        cpr.cprflag = 0;
        cpr.neckflag = 0;
        cpr.eyeflag = 0;
        DataService.getInstance().send(cpr);
    }

    private void playBackStart() {
        if (!playBackStarted) {
            resetVar();

            playBackFlowBtn.setEnabled(true);
            playBackFlowBtn.setBackground(getResources().getDrawable(R.drawable.btgreen));
            playBackPauseBtn.setEnabled(true);
            playBackPauseBtn.setBackground(getResources().getDrawable(R.drawable.btgreen));
            ac.updateTitle(playBackScore.exam_type + "-" + this.lan.huifangzhong);
            playBackStartBtn.setText(this.lan.tingzhi);
            PlayBackDataService.getInstance().start();
        } else {

            //	BitmapUtil.saveBitmap(pressDrawLineView.getBlowBitmap(), Environment.getExternalStorageDirectory()+"/ttt2.png");
            playBackFlowBtn.setEnabled(false);
            playBackFlowBtn.setBackground(getResources().getDrawable(R.drawable.unactive));
            playBackPauseBtn.setEnabled(false);
            playBackPauseBtn.setBackground(getResources().getDrawable(R.drawable.unactive));
            playBackPauseBtn.setText(this.lan.zanting);
            playBackPauseStarted = false;
            playBackPauseBtn.setEnabled(false);
            playBackStartBtn.setText(this.lan.kaishi);
            ac.updateTitle(playBackScore.exam_type);
            PlayBackDataService.getInstance().stop();
        }
        playBackStarted = !playBackStarted;
    }

    private void playBackPause() {
        if (!playBackPauseStarted) {
            playBackPauseBtn.setText(this.lan.jixu);
            PlayBackDataService.getInstance().pause();
        } else {
            playBackPauseBtn.setText(this.lan.zanting);
            PlayBackDataService.getInstance().resume();
        }
        playBackPauseStarted = !playBackPauseStarted;
    }

    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub

        /**
         * 开始按钮的点击
         */
        if (view.getId() == R.id.startBtn) {
            //PrintsService.getInstance().isConnect();
            startBtnClick();
            /**
             * CPR详情的点击
             *
             */
        } else if (view.getId() == R.id.flowBtn) {
            flowBtnClick();
            /**
             *保存成绩的点击
             */
        } else if (view.getId() == R.id.saveBtn) {
            saveBtnClick();
            /**
             * 回放开始的点击
             */
        } else if (view.getId() == R.id.playBackStartBtn) {
            playBackStart();
            /**
             * 回放暂停的点击
             */
        } else if (view.getId() == R.id.playBackPauseBtn) {
            playBackPause();
            /**
             *回放详情的点击
             */
        } else if (view.getId() == R.id.playBackFlowBtn) {
            playBackFlowBtnClick();
        }
        return;
    }

    class TimeThread extends Thread {

        private int time;
        private boolean isRun = false;

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (time > 0 && isRun) {
                time--;
                ac.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        ac.updateTitle2(time + "");
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }

            if (time == 0) {
                ac.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        onClick(startBtn);
                    }
                });
            }

        }

        @Override
        public void start() {

            isRun = true;
            time = cprSetting.time + 1;
            super.start();
        }

        public void goStop() {
            this.interrupt();
            isRun = false;
        }


    }


}
