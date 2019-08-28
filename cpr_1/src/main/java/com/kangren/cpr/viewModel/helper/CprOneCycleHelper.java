package com.kangren.cpr.viewModel.helper;

import com.kangren.cpr.CprType;
import com.kangren.cpr.entity.Setting;
import com.kangren.cpr.viewModel.CprOneCycleData;
import com.kangren.cpr.viewModel.OneBlowCycle;
import com.kangren.cpr.viewModel.OnePressCycle;
import com.lyc.utils.LogUtil;

public class CprOneCycleHelper {
    public CprType getCprType() {
        return cprType;
    }

    public void setCprType(CprType cprType) {
        this.cprType = cprType;
    }


    private boolean lastOperIsBlow;
    private CprType cprType;
    private Setting cprSetting;
    private CprOneCycleData currentData;
    private IOneCycle iOneCycle;

    public void SetIOneCycleCompleted(IOneCycle iOneCycle) {
        this.iOneCycle = iOneCycle;
    }

    public CprOneCycleHelper(CprType cprType, Setting cprSetting) {
        lastOperIsBlow = cprSetting.anchuishunxu == 0 ? true : false;
        this.cprType = cprType;
        this.cprSetting = cprSetting;
        currentData = new CprOneCycleData();
    }

    public void updatePressRecord(OnePressCycle pressCycle) {

        if (pressCycle.endTime - pressCycle.startTime <= 60) {
            LogUtil.WriteLog("eeeeeeee50");
            return;
        }
        SoundType st = SoundType.None;

        if (lastOperIsBlow) {
            lastOperIsBlow = false;
            if (cprType == CprType.shizhan) {

                currentData.blow_shaochui = cprSetting.chuiqicishu - currentData.blow_zongshu;
                if (currentData.blow_shaochui < 0)
                    currentData.blow_shaochui = 0;
                currentData.blow_zongshu += currentData.blow_shaochui;
            }
            currentData.blow_cuowu += currentData.blow_shaochui;

            if (cprSetting.anchuishunxu == 1) {
                this.iOneCycle.completed(currentData);
                currentData = new CprOneCycleData();
                // updatePressRecord(pressCycle);
                //
                // return;
            }

        }

        int error = 0;
        int pinglv = pressCycle.pinlv;
        //  LogUtil.WriteLog("Ƶ��"+pinglv+"");


        if (cprType == CprType.shizhan && currentData.press_zongshu >= cprSetting.anyacishu) {
            currentData.press_duoan++;
            error = 1;
            st = SoundType.Anyaguoduo;
        } else if (cprType == CprType.kaohe && currentData.press_zhengque >= cprSetting.anyacishu) {
            currentData.press_duoan++;
            error = 1;
            st = SoundType.Anyaguoduo;
        } else if (pressCycle.endNum > 0) {
            currentData.press_huitanbuzu++;
            error = 1;
            st = SoundType.huitanbuzu;
        } else if (pinglv > cprSetting.anyapinlvMax) {
            currentData.press_pinlv++;
            error = 1;
            st = SoundType.Pinlvguokuai;
        } else if (pinglv < cprSetting.anyapinlvMin) {
            currentData.press_pinlv++;
            error = 1;
            st = SoundType.Pinlvguoman;
        } else if (pressCycle.deepth > cprSetting.anyashenduMax) {
            currentData.press_guoda++;
            error = 1;
            st = SoundType.Anyaguoda;
        } else if (pressCycle.deepth < cprSetting.anyashenduMin) {
            currentData.press_guoxiao++;
            error = 1;
            st = SoundType.Anyaguoxiao;
        }


        currentData.press_zongshu++;
        if (error == 1)
            currentData.press_cuowu += error;
        else
            currentData.press_zhengque++;

        lastOperIsBlow = false;
        this.iOneCycle.pressUpdate(currentData, st);
    }

    public void updateBlowRecord(OneBlowCycle blowCycle) {


        if (blowCycle.endTime - blowCycle.startTime <= 60) {
            LogUtil.WriteLog("eeeeeeee50");
            return;
        }
        SoundType st = SoundType.None;

        if (!lastOperIsBlow) {
            lastOperIsBlow = true;

            if (cprType == CprType.shizhan) {
                currentData.press_shaoan = cprSetting.anyacishu - currentData.press_zongshu;
                if (currentData.press_shaoan < 0)
                    currentData.press_shaoan = 0;

                currentData.press_zongshu += currentData.press_shaoan;
                currentData.press_cuowu += currentData.press_shaoan;
            }

            if (cprSetting.anchuishunxu == 0) {
                this.iOneCycle.completed(currentData);

                // updateBlowRecord(blowCycle);
                currentData = new CprOneCycleData();
                // return;
            }

        }
        int error = 0;


        if (!blowCycle.lastBlow.flag) {
            currentData.blow_qidaoweidakai++;
            error = 1;
            st = SoundType.Qidaoweidakai;
        } else if (cprType == CprType.shizhan && currentData.blow_zongshu >= cprSetting.chuiqicishu) {
            currentData.blow_duochui++;
            error = 1;
            st = SoundType.Chuiqiguoduo;
        }
        //
        else if (cprType == CprType.kaohe && currentData.blow_zhengque >= cprSetting.chuiqicishu) {
            currentData.blow_duochui++;
            st = SoundType.Chuiqiguoduo;
            error = 1;
        } else if (blowCycle.deepth > (cprSetting.chuiqiliangMax + 200)) {
            currentData.blow_jinwei++;
            error = 1;
            st = SoundType.chuiqijinwei;
        } else if (blowCycle.deepth > cprSetting.chuiqiliangMax) {
            currentData.blow_guoda++;
            error = 1;
            st = SoundType.Chuiqiguoda;
        } else if (blowCycle.deepth < cprSetting.chuiqiliangMin) {
            currentData.blow_guoxiao++;
            error = 1;
            st = SoundType.Chuiqiguoxiao;
        }


        currentData.blow_zongshu++;
        if (error == 1)
            currentData.blow_cuowu += error;
        else
            currentData.blow_zhengque++;

        lastOperIsBlow = true;

        this.iOneCycle.blowUpdate(currentData, st);
    }

    public void reset() {
        lastOperIsBlow = cprSetting.anchuishunxu == 0 ? true : false;
        currentData = new CprOneCycleData();
    }
}
