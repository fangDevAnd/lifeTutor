package com.xiaofangfang.rice2_verssion.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

public class Card implements Serializable {

    private int cardId;
    private int cityCode;
    private int provinceSetmealId;
    private String cardName;
    private int price;
    private int rank;
    private String oprator;
    private int talkTime;
    private String netFlowCount;
    private String detribute;
    private String action;
    private String roughImageAddress;
    private String saleTitle;
    private List<String> image;
    private String monthFee;
    private String mark;
    private String offhook;
    private String valueAddSer;
    private String voiceTariff;
    private String smsAmmsTariff;
    private String flowTariff;
    private String otherSer;
    private String brandBand;
    private String tv;
    private String promotPeriod;
    private String redPacket;
    private int flowCount;
    private float totalPrice;//设置默认值

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getFlowCount() {
        return flowCount;
    }

    public void setFlowCount(int flowCount) {
        this.flowCount = flowCount;
    }


    //下面添加新的属性，存放的是商品详情页面的图片的地址
    private List<String> cardDetailPageImg;

    public List<String> getCardDetailPageImg() {
        return cardDetailPageImg;
    }

    public void setCardDetailPageImg(List<String> cardDetailPageImg) {
        this.cardDetailPageImg = cardDetailPageImg;
    }

    public Card(int cardId, int cityCode, int provinceSetmealId, String cardName, int price, int rank,
                String oprator, int talkTime, String netFlowCount, String detribute, String action,
                String roughImageAddress, String saleTitle, List<String> image, String monthFee, String mark,
                String offhook, String valueAddSer, String voiceTariff, String smsAmmsTariff, String flowTariff,
                String otherSer, String brandBand, String tv, String promotPeriod, String redPacket, String classfy,
                List<String> cardDetailPageImg) {
        this.cardId = cardId;
        this.cityCode = cityCode;
        this.provinceSetmealId = provinceSetmealId;
        this.cardName = cardName;
        this.price = price;
        this.rank = rank;
        this.oprator = oprator;
        this.talkTime = talkTime;
        this.netFlowCount = netFlowCount;
        this.detribute = detribute;
        this.action = action;
        this.roughImageAddress = roughImageAddress;
        this.saleTitle = saleTitle;
        this.image = image;
        this.monthFee = monthFee;
        this.mark = mark;
        this.offhook = offhook;
        this.valueAddSer = valueAddSer;
        this.voiceTariff = voiceTariff;
        this.smsAmmsTariff = smsAmmsTariff;
        this.flowTariff = flowTariff;
        this.otherSer = otherSer;
        this.brandBand = brandBand;
        this.tv = tv;
        this.promotPeriod = promotPeriod;
        this.redPacket = redPacket;
        this.classfy = classfy;
        this.cardDetailPageImg = cardDetailPageImg;
    }


    public Card(int cardId, int cityCode, int provinceSetmealId, String cardName, int price, int rank, String oprator, int talkTime, String netFlowTime, String destribute, int saleVolumn) {

        this.cardId = cardId;
        this.cityCode = cityCode;
        this.provinceSetmealId = provinceSetmealId;
        this.cardName = cardName;
        this.price = price;
        this.rank = rank;
        this.oprator = oprator;
        this.talkTime = talkTime;
        this.netFlowCount = netFlowTime;
        this.detribute = destribute;

    }


    public Card(int cardId, int cityCode, String oprator, int talkTime, String netFlowCount, String action, String roughImageAddress, String saleTitle, String setmeal, List<String> imageAddress, int price) {

        this.cardId = cardId;
        this.cityCode = cityCode;
        this.oprator = oprator;
        this.talkTime = talkTime;
        this.netFlowCount = netFlowCount;
        this.action = action;
        this.roughImageAddress = roughImageAddress;
        this.saleTitle = saleTitle;
        this.image = imageAddress;
        this.price = price;
    }


    public Card(int cardId, String monthFee, String oprator, String roughImageAddress, int price, String desctribute) {
        this.cardId = cardId;
        this.monthFee = monthFee;
        this.oprator = oprator;
        this.roughImageAddress = roughImageAddress;
        this.price = price;
        this.detribute = desctribute;
    }


    public Card(String saleTitle, int cardId, String monthFee, String oprator, String roughImageAddress, int price) {
        this.cardId = cardId;
        this.monthFee = monthFee;
        this.oprator = oprator;
        this.roughImageAddress = roughImageAddress;
        this.price = price;
        this.saleTitle = saleTitle;
    }


    @Override
    public String toString() {
        return "Card{" +
                "cardId=" + cardId +
                ", cityCode=" + cityCode +
                ", provinceSetmealId=" + provinceSetmealId +
                ", cardName='" + cardName + '\'' +
                ", price=" + price +
                ", rank=" + rank +
                ", oprator='" + oprator + '\'' +
                ", talkTime=" + talkTime +
                ", netFlowCount='" + netFlowCount + '\'' +
                ", detribute='" + detribute + '\'' +
                ", action='" + action + '\'' +
                ", roughImageAddress='" + roughImageAddress + '\'' +
                ", saleTitle='" + saleTitle + '\'' +
                ", image=" + image +
                ", monthFee='" + monthFee + '\'' +
                ", mark='" + mark + '\'' +
                ", offhook='" + offhook + '\'' +
                ", valueAddSer='" + valueAddSer + '\'' +
                ", voiceTariff='" + voiceTariff + '\'' +
                ", smsAmmsTariff='" + smsAmmsTariff + '\'' +
                ", flowTariff='" + flowTariff + '\'' +
                ", otherSer='" + otherSer + '\'' +
                ", brandBand='" + brandBand + '\'' +
                ", tv='" + tv + '\'' +
                ", promotPeriod='" + promotPeriod + '\'' +
                ", redPacket='" + redPacket + '\'' +
                '}';
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public int getProvinceSetmealId() {
        return provinceSetmealId;
    }

    public void setProvinceSetmealId(int provinceSetmealId) {
        this.provinceSetmealId = provinceSetmealId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getOprator() {
        return oprator;
    }

    public void setOprator(String oprator) {
        this.oprator = oprator;
    }

    public int getTalkTime() {
        return talkTime;
    }

    public void setTalkTime(int talkTime) {
        this.talkTime = talkTime;
    }

    public String getNetFlowCount() {
        return netFlowCount;
    }

    public void setNetFlowCount(String netFlowCount) {
        this.netFlowCount = netFlowCount;
    }

    public String getDetribute() {
        return detribute;
    }

    public void setDetribute(String detribute) {
        this.detribute = detribute;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getRoughImageAddress() {
        return roughImageAddress;
    }

    public void setRoughImageAddress(String roughImageAddress) {
        this.roughImageAddress = roughImageAddress;
    }

    public String getSaleTitle() {
        return saleTitle;
    }

    public void setSaleTitle(String saleTitle) {
        this.saleTitle = saleTitle;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public String getMonthFee() {
        return monthFee;
    }

    public void setMonthFee(String monthFee) {
        this.monthFee = monthFee;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getOffhook() {
        return offhook;
    }

    public void setOffhook(String offhook) {
        this.offhook = offhook;
    }

    public String getValueAddSer() {
        return valueAddSer;
    }

    public void setValueAddSer(String valueAddSer) {
        this.valueAddSer = valueAddSer;
    }

    public String getVoiceTariff() {
        return voiceTariff;
    }

    public void setVoiceTariff(String voiceTariff) {
        this.voiceTariff = voiceTariff;
    }

    public String getSmsAmmsTariff() {
        return smsAmmsTariff;
    }

    public void setSmsAmmsTariff(String smsAmmsTariff) {
        this.smsAmmsTariff = smsAmmsTariff;
    }

    public String getFlowTariff() {
        return flowTariff;
    }

    public void setFlowTariff(String flowTariff) {
        this.flowTariff = flowTariff;
    }

    public String getOtherSer() {
        return otherSer;
    }

    public void setOtherSer(String otherSer) {
        this.otherSer = otherSer;
    }

    public String getBrandBand() {
        return brandBand;
    }

    public void setBrandBand(String brandBand) {
        this.brandBand = brandBand;
    }

    public String getTv() {
        return tv;
    }

    public void setTv(String tv) {
        this.tv = tv;
    }

    public String getPromotPeriod() {
        return promotPeriod;
    }

    public void setPromotPeriod(String promotPeriod) {
        this.promotPeriod = promotPeriod;
    }

    public String getRedPacket() {
        return redPacket;
    }

    public void setRedPacket(String redPacket) {
        this.redPacket = redPacket;
    }

    public Card(int cardId, int cityCode, int provinceSetmealId, String cardName, int price, int rank, String oprator, int talkTime, String netFlowCount, String detribute, String action, String roughImageAddress, String saleTitle, List<String> image, String monthFee, String mark, String offhook, String valueAddSer, String voiceTariff, String smsAmmsTariff, String flowTariff, String otherSer, String brandBand, String tv, String promotPeriod, String redPacket) {
        this.cardId = cardId;
        this.cityCode = cityCode;
        this.provinceSetmealId = provinceSetmealId;
        this.cardName = cardName;
        this.price = price;
        this.rank = rank;
        this.oprator = oprator;
        this.talkTime = talkTime;
        this.netFlowCount = netFlowCount;
        this.detribute = detribute;
        this.action = action;
        this.roughImageAddress = roughImageAddress;
        this.saleTitle = saleTitle;
        this.image = image;
        this.monthFee = monthFee;
        this.mark = mark;
        this.offhook = offhook;
        this.valueAddSer = valueAddSer;
        this.voiceTariff = voiceTariff;
        this.smsAmmsTariff = smsAmmsTariff;
        this.flowTariff = flowTariff;
        this.otherSer = otherSer;
        this.brandBand = brandBand;
        this.tv = tv;
        this.promotPeriod = promotPeriod;
        this.redPacket = redPacket;
    }


    public Card(int cardId, int cityCode, int provinceSetmealId, String cardName, int price, int rank,
                String oprator, int talkTime, String netFlowCount, String detribute, String action,
                String roughImageAddress, String saleTitle, List<String> image, String monthFee, String mark,
                String offhook, String valueAddSer, String voiceTariff, String smsAmmsTariff, String flowTariff,
                String otherSer, String brandBand, String tv, String promotPeriod, String redPacket, String classfy) {
        this.cardId = cardId;
        this.cityCode = cityCode;
        this.provinceSetmealId = provinceSetmealId;
        this.cardName = cardName;
        this.price = price;
        this.rank = rank;
        this.oprator = oprator;
        this.talkTime = talkTime;
        this.netFlowCount = netFlowCount;
        this.detribute = detribute;
        this.action = action;
        this.roughImageAddress = roughImageAddress;
        this.saleTitle = saleTitle;
        this.image = image;
        this.monthFee = monthFee;
        this.mark = mark;
        this.offhook = offhook;
        this.valueAddSer = valueAddSer;
        this.voiceTariff = voiceTariff;
        this.smsAmmsTariff = smsAmmsTariff;
        this.flowTariff = flowTariff;
        this.otherSer = otherSer;
        this.brandBand = brandBand;
        this.tv = tv;
        this.promotPeriod = promotPeriod;
        this.redPacket = redPacket;
        this.classfy = classfy;
    }

    private String classfy;


    public String getClassfy() {
        return classfy;
    }

    public void setClassfy(String classfy) {
        this.classfy = classfy;
    }


}
