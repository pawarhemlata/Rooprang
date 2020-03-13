package com.tsi.rooprang.DTO.orderhistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderedProduct implements Serializable {

    @SerializedName("order_product_id")
    @Expose
    private String orderProductId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("order_product_status")
    @Expose
    private String orderProductStatus;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("product_aval_age_id")
    @Expose
    private String productAvalAgeId;
    @SerializedName("product_avail_size_id")
    @Expose
    private String productAvailSizeId;
    @SerializedName("product_color_chart_id")
    @Expose
    private String productColorChartId;
    @SerializedName("create")
    @Expose
    private String create;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("btn_status")
    @Expose
    private String btnStatus;

    public String getBtnStatus() {
        return btnStatus;
    }

    public void setBtnStatus(String btnStatus) {
        this.btnStatus = btnStatus;
    }

    public String getOrderProductStatus() {
        return orderProductStatus;
    }

    public void setOrderProductStatus(String orderProductStatus) {
        this.orderProductStatus = orderProductStatus;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(String orderProductId) {
        this.orderProductId = orderProductId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getProductAvalAgeId() {
        return productAvalAgeId;
    }

    public void setProductAvalAgeId(String productAvalAgeId) {
        this.productAvalAgeId = productAvalAgeId;
    }

    public String getProductAvailSizeId() {
        return productAvailSizeId;
    }

    public void setProductAvailSizeId(String productAvailSizeId) {
        this.productAvailSizeId = productAvailSizeId;
    }

    public String getProductColorChartId() {
        return productColorChartId;
    }

    public void setProductColorChartId(String productColorChartId) {
        this.productColorChartId = productColorChartId;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}