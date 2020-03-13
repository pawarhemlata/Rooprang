package com.tsi.rooprang.DTO.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ViewProfile_DTO {
    @SerializedName("data")
    @Expose
    private ArrayList<ViewProfile_Detail_DTO> data = null;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("result")
    @Expose
    private String result;

    public List<ViewProfile_Detail_DTO> getData() {
        return data;
    }

    public void setData(ArrayList<ViewProfile_Detail_DTO> data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}