package com.tsi.rooprang.DTO.singleproduct;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Single_Product_Detail_DTO {
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("added_by")
    @Expose
    private String addedBy;
    @SerializedName("subCategory_id")
    @Expose
    private String subCategoryId;
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
    @SerializedName("productAvailabelAge")
    @Expose
    private List<ProductAvailabelAge> productAvailabelAge = null;
    @SerializedName("productAvailabelSize")
    @Expose
    private List<ProductAvailabelSize> productAvailabelSize = null;
    @SerializedName("productAvailabelColor")
    @Expose
    private List<ProductAvailabelColor> productAvailabelColor = null;
    @SerializedName("productOffers")
    @Expose
    private List<ProductOffers> productOffers = null;
    @SerializedName("Rating")
    @Expose
    private String rating;
    @SerializedName("TotalRating")
    @Expose
    private String TotalRating;
    @SerializedName("delivery_by")
    @Expose
    private String deliveryBy;
    @SerializedName("extra_charges")
    @Expose
    private String extraCharges;
    @SerializedName("wishlist")
    @Expose
    private Wishlist wishlist;
    @SerializedName("cartlist")
    @Expose
    private Cartlist cartlist;
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

    public String getTotalRating() {
        return TotalRating;
    }

    public void setTotalRating(String totalRating) {
        TotalRating = totalRating;
    }

    public List<ProductAvailabelAge> getProductAvailabelAge() {
        return productAvailabelAge;
    }

    public void setProductAvailabelAge(List<ProductAvailabelAge> productAvailabelAge) {
        this.productAvailabelAge = productAvailabelAge;
    }

    public List<ProductAvailabelSize> getProductAvailabelSize() {
        return productAvailabelSize;
    }

    public void setProductAvailabelSize(List<ProductAvailabelSize> productAvailabelSize) {
        this.productAvailabelSize = productAvailabelSize;
    }

    public List<ProductAvailabelColor> getProductAvailabelColor() {
        return productAvailabelColor;
    }

    public void setProductAvailabelColor(List<ProductAvailabelColor> productAvailabelColor) {
        this.productAvailabelColor = productAvailabelColor;
    }

    public List<ProductOffers> getProductOffers() {
        return productOffers;
    }

    public void setProductOffers(List<ProductOffers> productOffers) {
        this.productOffers = productOffers;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDeliveryBy() {
        return deliveryBy;
    }

    public void setDeliveryBy(String deliveryBy) {
        this.deliveryBy = deliveryBy;
    }

    public String getExtraCharges() {
        return extraCharges;
    }

    public void setExtraCharges(String extraCharges) {
        this.extraCharges = extraCharges;
    }

    public Wishlist getWishlist() {
        return wishlist;
    }

    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }

    public Cartlist getCartlist() {
        return cartlist;
    }

    public void setCartlist(Cartlist cartlist) {
        this.cartlist = cartlist;
    }
}
