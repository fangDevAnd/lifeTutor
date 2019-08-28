package com.kangren.cpr.viewModel;

/**
 * 单次循环的统计操作
 * 用来统计操作的正确性等。。。。。。
 */
public class CprOneCycleData {


    public static final String PRESS_ZONG_SHU = "按压";
    public static final String Press_GUO_DA = "����";
    public static final String PRESS_GUO_XIAO = "��С";
    public static final String PRESS_ZHENG_QUE = "��ȷ";
    public static final String PRESS_CUO_WU = "����";
    public static final String PRESS_DUO_AN = "�ఴ";
    public static final String PRESS_SHAO_AN = "�ٰ�";
    public static final String PRESS_PIN_LV = "Ƶ�ʴ���";

    public static final String BLOW_ZONG_SHU = "����";
    public static final String BLOW_GUO_DA = "����";
    public static final String BLOW_GUO_XIAO = "��С";
    public static final String BLOW_ZHENG_QUE = "��ȷ";
    public static final String BLOW_CUO_WU = "����";
    public static final String BLOW_DUO_CHUI = "�വ";
    public static final String BLOW_SHAO_CHUI = "�ٴ�";
    public static final String BLOW_JIN_WEI = "��θ";

    public CprOneCycleData() {
    }

    public CprOneCycleData(CprOneCycleData data) {
        this.press_zongshu = data.press_zongshu;
        this.press_guoda = data.press_guoda;
        this.press_guoxiao = data.press_guoxiao;
        this.press_zhengque = data.press_zhengque;
        this.press_cuowu = data.press_cuowu;
        this.press_duoan = data.press_duoan;
        this.press_shaoan = data.press_shaoan;
        this.press_pinlv = data.press_pinlv;
        this.press_huitanbuzu = data.press_huitanbuzu;


        this.blow_zongshu = data.blow_zongshu;
        this.blow_guoda = data.blow_guoda;
        this.blow_guoxiao = data.blow_guoxiao;
        this.blow_zhengque = data.blow_zhengque;
        this.blow_cuowu = data.blow_cuowu;
        this.blow_duochui = data.blow_duochui;
        this.blow_shaochui = data.blow_shaochui;
        this.blow_jinwei = data.blow_jinwei;
        this.blow_qidaoweidakai = data.blow_qidaoweidakai;
    }

    public int press_zongshu = 0;
    public int press_guoda = 0;
    public int press_guoxiao = 0;
    public int press_zhengque = 0;
    public int press_cuowu = 0;
    public int press_duoan = 0;
    public int press_shaoan = 0;
    public int press_pinlv = 0;
    public int press_huitanbuzu = 0;


    public int blow_zongshu = 0;
    public int blow_guoda = 0;
    public int blow_guoxiao = 0;
    public int blow_zhengque = 0;
    public int blow_cuowu = 0;
    public int blow_duochui = 0;
    public int blow_shaochui = 0;
    public int blow_jinwei = 0;
    public int blow_qidaoweidakai = 0;

}
