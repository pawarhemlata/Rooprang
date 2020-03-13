package com.tsi.rooprang.DTO.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cart_Total_amount_DTO {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("totalSalePrice")
    @Expose
    private String totalSalePrice;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("promoCodeDiscount")
    @Expose
    private String promoCodeDiscount;
    @SerializedName("totalDiscount")
    @Expose
    private String totalDiscount;
    @SerializedName("payAmount")
    @Expose
    private String payAmount;
    @SerializedName("shipping_charges")
    @Expose
    private String shipping_charges;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalSalePrice() {
        return totalSalePrice;
    }

    public void setTotalSalePrice(String totalSalePrice) {
        this.totalSalePrice = totalSalePrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPromoCodeDiscount() {
        return promoCodeDiscount;
    }

    public void setPromoCodeDiscount(String promoCodeDiscount) {
        this.promoCodeDiscount = promoCodeDiscount;
    }

    public String getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(String totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getShipping_charges() {
        return shipping_charges;
    }

    public void setShipping_charges(String shipping_charges) {
        this.shipping_charges = shipping_charges;
    }
}
