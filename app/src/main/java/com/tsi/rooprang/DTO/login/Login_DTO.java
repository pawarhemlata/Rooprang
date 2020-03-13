package com.tsi.rooprang.DTO.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login_DTO {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("data")
    @Expose
    private Login_Details_DTO data;

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

    public Login_Details_DTO getData() {
        return data;
    }

    public void setData(Login_Details_DTO data) {
        this.data = data;
    }

}