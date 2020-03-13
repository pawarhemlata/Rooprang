package com.tsi.rooprang.DTO.DataModel;

public class DataModelLatest {
    private String ProdID;
    private int product_image;
    private String productname;
    private String disPercent;
    private String costprice;
    private String discountcodes;

    public DataModelLatest(int product_image, String productname,String disPercent,String costprice,String discountcodes) {
        this.product_image = product_image;
        this.productname = productname;
        this.disPercent = disPercent;
        this.costprice = costprice;
        this.discountcodes = discountcodes;
    }

    public String getProdID() {
        return ProdID;
    }

    public void setProdID(String prodID) {
        ProdID = prodID;
    }

    public int getProduct_image() {
        return product_image;
    }

    public void setProduct_image(int product_image) {
        this.product_image = product_image;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getDisPercent() {
        return disPercent;
    }

    public void setDisPercent(String disPercent) {
        this.disPercent = disPercent;
    }

    public String getCostprice() {
        return costprice;
    }

    public void setCostprice(String costprice) {
        this.costprice = costprice;
    }

    public String getDiscountcodes() {
        return discountcodes;
    }

    public void setDiscountcodes(String discountcodes) {
        this.discountcodes = discountcodes;
    }


}