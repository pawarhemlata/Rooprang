package com.tsi.rooprang.DTO.DataModel;

import java.io.Serializable;

public class WishlistDataModel implements Serializable {
    int img;
    String name, price, orgprice, off;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getOff() {
        return off;
    }

    public void setOff(String off) {
        this.off = off;
    }

    public WishlistDataModel(String name, int img, String price, String orgprice, String off) {
        this.name = name;
        this.img = img;
        this.price = price;
        this.orgprice = orgprice;

        this.off = off;

    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public String getOrgprice() {
        return orgprice;
    }

    public void setOrgprice(String orgprice) {
        this.orgprice = orgprice;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
