package com.tsi.rooprang.DTO.DataModel;

public class DataModelHome1 {
    String name;
    int img;
    public DataModelHome1(String name, int img) {
        this.img = img;
        this.name = name;
    }

    public DataModelHome1(int cover) {
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
