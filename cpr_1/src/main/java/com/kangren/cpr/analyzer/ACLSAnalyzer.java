package com.kangren.cpr.analyzer;

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

public class ACLSAnalyzer  implements IAnalyzer{

    protected ACLSAnalyzer(){}
    public List<IReceiveMessage> parse(byte[] buffer) {
        // TODO Auto-generated method stub


        Parse parse=new Parse();
        List<IReceiveMessage> res=new ArrayList<IReceiveMessage>();
        int flag=buffer[3]&0xff;
        IReceiveMessage  rmsg;
        switch(flag){
            //����
            case 0x0c:
                rmsg=parse.toBattery(buffer[4],buffer[5]);
                rmsg.setTime(System.currentTimeMillis());
                res.add(rmsg);
                break;
            //ͷ��״̬
            case 0xA2:
                rmsg=parse.toRaiseHead(buffer[4]);
                rmsg.setTime(System.currentTimeMillis());
                res.add(rmsg);
                break;
            //����ǰ�ж�
            case 0xA1:
                rmsg=parse.toOperationBefore(buffer[4]);
                rmsg.setTime(System.currentTimeMillis());
                res.add(rmsg);
                break;
            case 0xa0:
                long time=System.currentTimeMillis();



                PressReceiveMessage msg=parse.toPress(buffer[4]);
                if(!(msg.isIn&&msg.num==0)){
                    msg.setTime(time);
                    res.add(msg);
                }

                msg=parse.toPress(buffer[8]);
                if(!(msg.isIn&&msg.num==0)){
                    msg=parse.toPress(buffer[8]);
                    msg.setTime(time+25);
                    res.add(msg);

                }
                rmsg=parse.toBlow(buffer[6]);
                rmsg.setTime(time);
                res.add(rmsg);

                rmsg=parse.toBlow(buffer[10]);
                rmsg.setTime(time+25);
                res.add(rmsg);


                break;
        }
        return res ;


    }
    public byte[] unParse(ISendMessage msg) {
        // TODO Auto-generated method stub

        byte[] buffer=new byte[]{
                (byte) 0xF0,(byte) 0xF0,(byte) 0xF0,
                (byte)0x01,
                (byte)0x00,	(byte)0x00,	(byte)0x00,	(byte)0x00,	(byte)0x00,	(byte)0x00,	(byte)0x00,
                (byte)0x00,	(byte)0x00,	(byte)0x00,	(byte)0x00,	(byte)0x00,	(byte)0x00,	(byte)0x00,
                (byte)0x00,(byte)0x0F
        };
        if(msg.getMsgType()==SendMessageType.Cpr){
            CprSendMessage cpr=(CprSendMessage)msg;
            byte xx=(byte) (cpr.cprflag==0?0x11:cpr.cprflag==1?0xAA:0x22);
            byte yy=(byte) (cpr.neckflag==0?0x11:0xAA);
            byte zz=(byte) (cpr.eyeflag==0?0xAA:0x11);
            buffer[3]=0x11;
            buffer[4]=xx;
            buffer[5]=yy;
            buffer[6]=zz;
        }
        return buffer;
    }


    class Parse{
        /*
         * D7�����λ���ǰ�ѹ����  d6-d0 ����λ
         */
        PressReceiveMessage toPress(int num){
            PressReceiveMessage res=new PressReceiveMessage();
            byte flag=(byte)(num&0x80);//��ʾλ
            byte you =(byte)(num<<1);
            you =(byte)(you>>1);
            res.isIn=flag==-0x80;
            res.lights=you*100/140;
            if(you==0x01){
                res.num=1;
            }
            if(res.lights>1&&res.lights<=8){
                res.num=2+(res.lights*3/8f);
            }
            if(res.lights>8&&res.lights<=16){
                res.num=5+(res.lights-8)*1/8f;
            }
            if(res.lights==20){
                res.num=6+(res.lights-16)*1/4f;
            }


            return res;
        }
        BlowReceiveMessage toBlow(int num){
            BlowReceiveMessage res=new BlowReceiveMessage();
            byte flag=(byte)(num&0x80);//��ʾλ
            byte you =(byte)(num<<1);
            you =(byte)(you>>1);
            res.isIn=flag==-0x80;

            res.lights=you*100/30;

            if(res.lights<=16){
                res.num=(int)(res.lights*1000/16f);
            }
            else{
                res.num=(int)(1000+(res.lights-16)*200/4f);
            }
            return res;

        }

        BatteryReceiveMessage toBattery(int h,int l){
            //��Сֵ1c2/240   280 ���ֵ
            BatteryReceiveMessage res=new BatteryReceiveMessage();

            String hSt=Integer.toHexString(h&0xff);
            String lSt=Integer.toHexString(l&0xff);
            if(lSt.length()==1)
                lSt="0"+lSt;
            float adc=  Integer.valueOf(hSt+lSt,16);
            // adc-=0x01c2;

            float temp=(5f /1024f) * (201f / 51f);
            //	 adc=adc/(0x0280-0x01c2)*100;
            adc=adc* temp;
            res.num=(int) ((adc-10.8)/(12.6-10.8)*100);
            //	 adc=(adc/);
            // res.num=(int)adc;	//st+=Integer.toHexString(temp);
            return res;
        }

        RaiseHeadReceiveMessage toRaiseHead(int flag){
            flag=flag&0xFF;
            RaiseHeadReceiveMessage res=new RaiseHeadReceiveMessage();
            if(flag==0xAA)
                res.headState=true;
            else
                res.headState=false;
            return res;

        }

        OperationBeforeReceiveMessage toOperationBefore(int flag){
            OperationBeforeReceiveMessage res=new OperationBeforeReceiveMessage();

            if(((byte)(flag&0x80))==0x80)
                res.operationBeforeType=OperationBeforeType.Yiwu;
            if(((byte)(flag&0x40))==0x40)
                res.operationBeforeType=OperationBeforeType.Yiwu;

            if(((byte)(flag&0x20))==0x20)
                res.operationBeforeType=OperationBeforeType.Maibo;
            if(((byte)(flag&0x10))==0x10)
                res.operationBeforeType=OperationBeforeType.Maibo;

            if(((byte)(flag&0x8))==0x8)
                res.operationBeforeType=OperationBeforeType.Hujiao;
            if(((byte)(flag&0x4))==0x4)
                res.operationBeforeType=OperationBeforeType.Huxi;

            if(((byte)(flag&0x2))==0x2)
                res.operationBeforeType=OperationBeforeType.Yishi;
            if(((byte)(flag&0x1))==0x1)
                res.operationBeforeType=OperationBeforeType.Yishi;

            return res;
        }
    }
}
