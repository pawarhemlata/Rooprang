package com.tsi.rooprang.DTO.Address_DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Show_Address_DTO {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Show_Address_Details_DTO> data = null;

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

    public List<Show_Address_Details_DTO> getData() {
        return data;
    }

    public void setData(List<Show_Address_Details_DTO> data) {
        this.data = data;
    }
}
