package com.tsi.rooprang.DTO.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResetPwd_DTO {
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("result")
    @Expose
    private String result;

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