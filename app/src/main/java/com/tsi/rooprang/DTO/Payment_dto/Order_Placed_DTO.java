package com.tsi.rooprang.DTO.Payment_dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order_Placed_DTO {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("grand_total")
    @Expose
    private String grandTotal;

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
