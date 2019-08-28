package com.kangren.cpr.analyzer;

import android.util.Log;

import com.kangren.cpr.receiveMessage.BatteryReceiveMessage;
import com.kangren.cpr.receiveMessage.BlowReceiveMessage;
import com.kangren.cpr.receiveMessage.IReceiveMessage;
import com.kangren.cpr.receiveMessage.OperationBeforeReceiveMessage;
import com.kangren.cpr.receiveMessage.OperationBeforeType;
import com.kangren.cpr.receiveMessage.PressReceiveMessage;
import com.kangren.cpr.receiveMessage.RaiseHeadReceiveMessage;
import com.kangren.cpr.sendMessage.CprSendMessage;
import com.kangren.cpr.sendMessage.ISendMessage;
import com.kangren.cpr.sendMessage.SendMessageType;

import java.util.ArrayList;
import java.util.List;


/**
 * 数据解析方案  实现的是对 F2800的数据进行解析的操作
 */
public class F2800Analyzer implements IAnalyzer {

    static float lastPress = 0;
    static float lastBlow = 0;
    static float pressUnit = 8.0f / 15500f;
    static float blowUnit = 1400.0f / 960f;

    static int hToInt(int high, int low) {


        /**
         * 取低位的一个字节
         */
        high = high & 0xff;
        low = low & 0xff;

        String h = Integer.toHexString(high);
        String l = Integer.toHexString(low);//0

        if (l.length() == 1)
            l = '0' + l;

        int value = Integer.parseInt(h + l, 16);
        return value;

        /**
         * [-16, -16, -16, -96, -120, 0, 0, 17, -118, 0, 0, 17, 0, 0, 0, 0, 0, 0, 0, 15]
         *
         */
    }

    protected F2800Analyzer() {
    }


    /**
     * 将一个 接收到的byte[]消息解析为一个List的消息类型
     *
     * @param buffer
     * @return
     */
    public List<IReceiveMessage> parse(byte[] buffer) {
        // TODO Auto-generated method stub


        Parse parse = new Parse();
        List<IReceiveMessage> res = new ArrayList<IReceiveMessage>();
        int flag = buffer[3] & 0xff;
        IReceiveMessage rmsg;
        switch (flag) {
            //����
            case 0x0c://-93
                rmsg = parse.toBattery(buffer[4], buffer[5]);
                rmsg.setTime(System.currentTimeMillis());
                res.add(rmsg);
                break;
            //ͷ��״̬
            case 0xA2://-94
                rmsg = parse.toRaiseHead(buffer[4]);
                rmsg.setTime(System.currentTimeMillis());
                res.add(rmsg);
                break;
            //����ǰ�ж�
            case 0xA1://-95
                rmsg = parse.toOperationBefore(buffer[4]);
                rmsg.setTime(System.currentTimeMillis());
                res.add(rmsg);
                break;
            case 0xa0:   // -96
                long time = System.currentTimeMillis();


                PressReceiveMessage msg = parse.toPress(buffer[4], buffer[5]);
                if (!(msg.isIn && msg.num == 0)) {
                    msg.setTime(time);
                    res.add(msg);
                }

                msg = parse.toPress(buffer[9], buffer[10]);
                if (!(msg.isIn && msg.num == 0)) {

                    msg.setTime(time + 25);
                    res.add(msg);

                }
                rmsg = parse.toBlow(buffer[7], buffer[8]);
                rmsg.setTime(time);
                res.add(rmsg);

                rmsg = parse.toBlow(buffer[12], buffer[13]);
                rmsg.setTime(time + 25);
                res.add(rmsg);


                break;
        }
        return res;


    }


    /**
     * @param msg
     * @return
     */
    public byte[] unParse(ISendMessage msg) {
        // TODO Auto-generated method stub

        byte[] buffer = new byte[]{
                (byte) 0xF0, (byte) 0xF0, (byte) 0xF0,
                (byte) 0x01,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x0F
        };
        if (msg.getMsgType() == SendMessageType.Cpr) {
            CprSendMessage cpr = (CprSendMessage) msg;
            byte xx = (byte) (cpr.cprflag == 0 ? 0x11 : cpr.cprflag == 1 ? 0xAA : 0x22);
            byte yy = (byte) (cpr.neckflag == 0 ? 0x11 : 0xAA);
            byte zz = (byte) (cpr.eyeflag == 0 ? 0xAA : 0x11);
            buffer[3] = 0x11;
            buffer[4] = xx;
            buffer[5] = yy;
            buffer[6] = zz;
        }
        return buffer;
    }


    class Parse {
        /*
         * D7�����λ���ǰ�ѹ����  d6-d0 ����λ
         */
        PressReceiveMessage toPress(int h, int l) {
            PressReceiveMessage res = new PressReceiveMessage();
            float num = hToInt(h, l) * pressUnit;
            float lights = 0;
            res.isIn = num > lastPress;
            res.num = num;
            Log.d("test", "toPress:===== " + num);
            if (num > 6) {
                lights += (num - 6) / (2.0f / 4);
                num = 6;
            }
            if (num > 5) {
                lights += (num - 5) / (1.0f / 8);
                num = 5;
            }
            if (num > 0) {
                lights += (num) / (5.0f / 8);

            }
            res.lights = (int) Math.floor(lights);
            lastPress = res.num;
            return res;
        }

        BlowReceiveMessage toBlow(int h, int l) {
            BlowReceiveMessage res = new BlowReceiveMessage();
            float lights = 0;
            float num = hToInt(h, l) * blowUnit;
            res.num = (int) num;
            res.isIn = num < lastBlow;
            if (num > 1000) {
                lights += (num - 1000) / (400f / 4);
                num = 1000;
            }
            if (num > 500) {
                lights += (num - 500) / (500f / 8);
                num = 500;
            }
            if (num > 0) {
                lights += (num) / (500f / 8);

            }
            lastBlow = num;
            return res;

        }

        BatteryReceiveMessage toBattery(int h, int l) {
            //��Сֵ1c2/240   280 ���ֵ
            BatteryReceiveMessage res = new BatteryReceiveMessage();

            String hSt = Integer.toHexString(h & 0xff);
            String lSt = Integer.toHexString(l & 0xff);
            if (lSt.length() == 1)
                lSt = "0" + lSt;
            float adc = Integer.valueOf(hSt + lSt, 16);
            // adc-=0x01c2;

            float temp = (5f / 1024f) * (201f / 51f);
            //	 adc=adc/(0x0280-0x01c2)*100;
            adc = adc * temp;
            res.num = (int) ((adc - 10.8) / (12.6 - 10.8) * 100);
            //	 adc=(adc/);
            // res.num=(int)adc;	//st+=Integer.toHexString(temp);
            return res;
        }

        RaiseHeadReceiveMessage toRaiseHead(int flag) {
            flag = flag & 0xFF;
            RaiseHeadReceiveMessage res = new RaiseHeadReceiveMessage();
            if (flag == 0xAA)
                res.headState = true;
            else
                res.headState = false;
            return res;

        }

        OperationBeforeReceiveMessage toOperationBefore(int flag) {
            OperationBeforeReceiveMessage res = new OperationBeforeReceiveMessage();

            if (((byte) (flag & 0x80)) == 0x80)
                res.operationBeforeType = OperationBeforeType.Yiwu;
            if (((byte) (flag & 0x40)) == 0x40)
                res.operationBeforeType = OperationBeforeType.Yiwu;

            if (((byte) (flag & 0x20)) == 0x20)
                res.operationBeforeType = OperationBeforeType.Maibo;
            if (((byte) (flag & 0x10)) == 0x10)
                res.operationBeforeType = OperationBeforeType.Maibo;

            if (((byte) (flag & 0x8)) == 0x8)
                res.operationBeforeType = OperationBeforeType.Hujiao;
            if (((byte) (flag & 0x4)) == 0x4)
                res.operationBeforeType = OperationBeforeType.Huxi;

            if (((byte) (flag & 0x2)) == 0x2)
                res.operationBeforeType = OperationBeforeType.Yishi;
            if (((byte) (flag & 0x1)) == 0x1)
                res.operationBeforeType = OperationBeforeType.Yishi;

            return res;
        }
    }
}
