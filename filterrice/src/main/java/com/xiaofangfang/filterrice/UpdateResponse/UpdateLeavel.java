package com.xiaofangfang.filterrice.UpdateResponse;

public enum UpdateLeavel {

    updateText(0),
    updateImgAndText(1),
    updateImgAndLink(2),
    updateImgAndTextAndLink(3);

    private int leavel;

    public int getLeavle() {
        return leavel;
    }

    UpdateLeavel(int leavel){
        this.leavel=leavel;
    }

}
