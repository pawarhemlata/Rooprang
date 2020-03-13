package com.tsi.rooprang.DTO.DataModel;

public class DataModelHome3 {
    String name,price;
    int img;
    public DataModelHome3(String name, int img, String price) {
        this.img = img;
        this.name = name;
        this.price = price;
    }

    public DataModelHome3(int cover) {
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
