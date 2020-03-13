package com.tsi.rooprang.DTO.singleproduct;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductOffers {
    @SerializedName("offer_id")
    @Expose
    private String offerId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("offer_name")
    @Expose
    private String offerName;
    @SerializedName("create")
    @Expose
    private String create;

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }

}