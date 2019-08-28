package com.xiaofangfang.filterrice.InsertResponse;

public enum ViewOprateCode {

    update(0),
    delete(1),
    insert(2);

    private int oprateCode;

    ViewOprateCode(int oprateCode) {
        this.oprateCode = oprateCode;
    }

    public int getOprateCode() {
        return oprateCode;
    }
}
