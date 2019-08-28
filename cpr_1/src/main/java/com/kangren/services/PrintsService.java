package com.kangren.services;

import com.kangren.cpr.MainActivity;
import com.kangren.cpr.entity.Score;
import com.lyc.bluetooth.Print;

import java.io.File;

import btmanager.Pos;

public class PrintsService {


    private static PrintsService printService;
    public static PrintsService getInstance(){
        if(printService==null)
            printService=new PrintsService();
        return printService;
    }
    private Print print;
    public Print getPrint() {
        return print;
    }
    public void setPrint(Print print) {
        this.print = print;
    }

    private String addSpace(Object o,int len){
        String res=o.toString();
        //	while(res.length()<len){
        //	res="��"+res;
        //	}
        return res;
    }

    public void print(Score score){
        print.write(addSpace("�༶:",5)+score.stu_class);
        print.write(addSpace("����:",5)+score.stu_name);
        print.write(addSpace("����:",5)+score.exam_date);
        print.write(addSpace("ģʽ:",5)+score.exam_type);
        for(int i=0;i<score.tables[0].records.length;i++){
            print.write(addSpace(score.tables[0].records[i].name,5)+":"+addSpace(score.tables[0].records[i].count,5));
        }
        for(int i=0;i<score.tables[1].records.length;i++){
            print.write(addSpace(score.tables[1].records[i].name,5)+":"+addSpace(score.tables[1].records[i].count,5));
        }

        print.write("����:");
        print.write(score.tables[score.tables.length-1].pingyu);

        print.write("");
        print.write("");

    }
    public void printScore(Score score){
        print(score);
    }
    public void printScorePic(Score score){


        if(score.path!=null&&score.path!=""){
            File f=new File(score.path);
            if(!f.exists()){
                ((MainActivity)print.context).showMsg("file not exist");
                return;
            }
            print.writePic(score.path);
        }
    }
    public void isConnect(){
        boolean b=Pos.POS_isConnected();
        int i=0;
    }



}
