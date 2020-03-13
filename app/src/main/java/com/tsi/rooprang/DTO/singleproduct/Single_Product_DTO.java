package com.tsi.rooprang.DTO.singleproduct;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Single_Product_DTO {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Single_Product_Detail_DTO> data = null;

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

    public List<Single_Product_Detail_DTO> getData() {
        return data;
    }

    public void setData(List<Single_Product_Detail_DTO> data) {
        this.data = data;
    }
}
