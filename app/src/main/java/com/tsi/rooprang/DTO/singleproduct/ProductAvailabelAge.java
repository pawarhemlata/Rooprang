package com.tsi.rooprang.DTO.singleproduct;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductAvailabelAge {

    @SerializedName("product_aval_age_id")
    @Expose
    private String productAvalAgeId;
    @SerializedName("age_chart_id")
    @Expose
    private String ageChartId;
    @SerializedName("age_name")
    @Expose
    private String ageName;

    public String getProductAvalAgeId() {
        return productAvalAgeId;
    }

    public void setProductAvalAgeId(String productAvalAgeId) {
        this.productAvalAgeId = productAvalAgeId;
    }

    public String getAgeChartId() {
        return ageChartId;
    }

    public void setAgeChartId(String ageChartId) {
        this.ageChartId = ageChartId;
    }

    public String getAgeName() {
        return ageName;
    }

    public void setAgeName(String ageName) {
        this.ageName = ageName;
    }

}