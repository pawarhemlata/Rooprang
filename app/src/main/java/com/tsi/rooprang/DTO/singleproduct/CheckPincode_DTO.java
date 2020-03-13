package com.tsi.rooprang.DTO.singleproduct;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckPincode_DTO {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("delivery")
    @Expose
    private String delivery;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("shipping_charge")
    @Expose
    private String shipping_charge;

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

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getShipping_charge() {
        return shipping_charge;
    }

    public void setShipping_charge(String shipping_charge) {
        this.shipping_charge = shipping_charge;
    }
}

