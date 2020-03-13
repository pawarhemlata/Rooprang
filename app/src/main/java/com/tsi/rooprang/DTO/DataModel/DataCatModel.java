package com.tsi.rooprang.DTO.DataModel;

public class DataCatModel {
    int img;
    String iv_text;

    public DataCatModel(int img, String iv_text) {
        this.img = img;
        this.iv_text = iv_text;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getIv_text() {
        return iv_text;
    }

    public void setIv_text(String iv_text) {
        this.iv_text = iv_text;
    }
}
