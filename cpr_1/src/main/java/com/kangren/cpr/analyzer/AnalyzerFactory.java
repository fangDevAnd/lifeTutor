package com.kangren.cpr.analyzer;

import java.util.HashMap;


public class AnalyzerFactory{



    private static HashMap<String, IAnalyzer> historyMap=new HashMap<String, IAnalyzer>();
    public static IAnalyzer createFactory(Class<?> type){
        String name=type.getName();
        if(!historyMap.containsKey(name)){
            IAnalyzer an = null;
            try {
                an = (IAnalyzer)type.newInstance();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            historyMap.put(name, an);
        }
        return historyMap.get(name);

    }
}
