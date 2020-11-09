package com.turing.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Contract implements Serializable {
    private Long id;

    private String contNum;

    private String buyer;

    private String seller;

    private String supplierNum;

    private Date contDate;

    private String contType;

    private String stockType;

    private String contItem;

    private String money;

    private String taxRate;

    private String wrapBack;

    private String verifyStand;

    private String mode;

    private String payCond;

    private Date verifyDate;

    private String qualityStand;

    private String standardNo;

    private String place;

    private BigDecimal contPrice;

    private String transport;

    private String payWay;

    private String outPerm;

    private String outDay;

    private String remark;


    private IdMapping idMapping;

    public IdMapping getIdMapping() {
        return idMapping;
    }

    public void setIdMapping(IdMapping idMapping) {
        this.idMapping = idMapping;
    }

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContNum() {
        return contNum;
    }

    public void setContNum(String contNum) {
        this.contNum = contNum == null ? null : contNum.trim();
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer == null ? null : buyer.trim();
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller == null ? null : seller.trim();
    }

    public String getSupplierNum() {
        return supplierNum;
    }

    public void setSupplierNum(String supplierNum) {
        this.supplierNum = supplierNum == null ? null : supplierNum.trim();
    }

    public Date getContDate() {
        return contDate;
    }

    public void setContDate(Date contDate) {
        this.contDate = contDate;
    }

    public String getContType() {
        return contType;
    }

    public void setContType(String contType) {
        this.contType = contType == null ? null : contType.trim();
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType == null ? null : stockType.trim();
    }

    public String getContItem() {
        return contItem;
    }

    public void setContItem(String contItem) {
        this.contItem = contItem == null ? null : contItem.trim();
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money == null ? null : money.trim();
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate == null ? null : taxRate.trim();
    }

    public String getWrapBack() {
        return wrapBack;
    }

    public void setWrapBack(String wrapBack) {
        this.wrapBack = wrapBack == null ? null : wrapBack.trim();
    }

    public String getVerifyStand() {
        return verifyStand;
    }

    public void setVerifyStand(String verifyStand) {
        this.verifyStand = verifyStand == null ? null : verifyStand.trim();
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode == null ? null : mode.trim();
    }

    public String getPayCond() {
        return payCond;
    }

    public void setPayCond(String payCond) {
        this.payCond = payCond == null ? null : payCond.trim();
    }

    public Date getVerifyDate() {
        return verifyDate;
    }

    public void setVerifyDate(Date verifyDate) {
        this.verifyDate = verifyDate;
    }

    public String getQualityStand() {
        return qualityStand;
    }

    public void setQualityStand(String qualityStand) {
        this.qualityStand = qualityStand == null ? null : qualityStand.trim();
    }

    public String getStandardNo() {
        return standardNo;
    }

    public void setStandardNo(String standardNo) {
        this.standardNo = standardNo == null ? null : standardNo.trim();
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public BigDecimal getContPrice() {
        return contPrice;
    }

    public void setContPrice(BigDecimal contPrice) {
        this.contPrice = contPrice;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport == null ? null : transport.trim();
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay == null ? null : payWay.trim();
    }

    public String getOutPerm() {
        return outPerm;
    }

    public void setOutPerm(String outPerm) {
        this.outPerm = outPerm == null ? null : outPerm.trim();
    }

    public String getOutDay() {
        return outDay;
    }

    public void setOutDay(String outDay) {
        this.outDay = outDay == null ? null : outDay.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}