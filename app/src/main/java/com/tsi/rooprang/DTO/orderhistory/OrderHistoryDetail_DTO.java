package com.tsi.rooprang.DTO.orderhistory;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderHistoryDetail_DTO {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("order_no")
    @Expose
    private String orderNo;
    @SerializedName("total_item_price")
    @Expose
    private String totalItemPrice;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("order_status")
    @Expose
    private String orderSatus;
    @SerializedName("ordered_date")
    @Expose
    private String orderredDate;
    @SerializedName("grand_total")
    @Expose
    private String grandTotal;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("address_id")
    @Expose
    private String addressId;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("full_address")
    @Expose
    private String fullAddress;
    @SerializedName("landmark")
    @Expose
    private String landmark;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("ordered_products")
    @Expose
    private List<OrderedProduct> orderedProducts = null;

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getOrderSatus() {
        return orderSatus;
    }

    public void setOrderSatus(String orderSatus) {
        this.orderSatus = orderSatus;
    }

    public String getOrderredDate() {
        return orderredDate;
    }

    public void setOrderredDate(String orderredDate) {
        this.orderredDate = orderredDate;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTotalItemPrice() {
        return totalItemPrice;
    }

    public void setTotalItemPrice(String totalItemPrice) {
        this.totalItemPrice = totalItemPrice;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<OrderedProduct> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(List<OrderedProduct> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }


}
