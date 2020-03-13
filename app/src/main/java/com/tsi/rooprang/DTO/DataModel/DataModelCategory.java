package com.tsi.rooprang.DTO.DataModel;

public class DataModelCategory {
    int img;
    String tv_catgry;
    String tv_age;

    public DataModelCategory(int img, String tv_catgry, String iv_age) {
        this.img = img;
        this.tv_age = iv_age;
        this.tv_catgry = tv_catgry;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTv_catgry() {
        return tv_catgry;
    }

    public void setTv_catgry(String tv_catgry) {
        this.tv_catgry = tv_catgry;
    }

    public String getTv_age() {
        return tv_age;
    }

    public void setTv_age(String tv_age) {
        this.tv_age = tv_age;
    }
}