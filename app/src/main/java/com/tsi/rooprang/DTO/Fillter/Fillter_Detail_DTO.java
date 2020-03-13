package com.tsi.rooprang.DTO.Fillter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Fillter_Detail_DTO {

    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("added_by")
    @Expose
    private String addedBy;
    @SerializedName("subCategory_id")
    @Expose
    private String subCategoryId;
    @SerializedName("brand_id")
    @Expose
    private String brandId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("sale_price")
    @Expose
    private String salePrice;
    @SerializedName("regular_price")
    @Expose
    private String regularPrice;
    @SerializedName("short_description")
    @Expose
    private String shortDescription;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("stock")
    @Expose
    private String stock;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("create")
    @Expose
    private String create;
    @SerializedName("productImages")
    @Expose
    private List<ProductImage> productImages = null;
    @SerializedName("productAges")
    @Expose
    private List<Object> productAges = null;
    @SerializedName("productSize")
    @Expose
    private List<Object> productSize = null;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(String regularPrice) {
        this.regularPrice = regularPrice;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public List<Object> getProductAges() {
        return productAges;
    }

    public void setProductAges(List<Object> productAges) {
        this.productAges = productAges;
    }

    public List<Object> getProductSize() {
        return productSize;
    }

    public void setProductSize(List<Object> productSize) {
        this.productSize = productSize;
    }

}
