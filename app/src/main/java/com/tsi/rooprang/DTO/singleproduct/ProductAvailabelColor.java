package com.tsi.rooprang.DTO.singleproduct;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductAvailabelColor {
    @SerializedName("product_color_chart_id")
    @Expose
    private String productColorChartId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("color_chart_id")
    @Expose
    private String colorChartId;
    @SerializedName("color_name")
    @Expose
    private String colorName;

    @SerializedName("color_code")
    @Expose
    private String colorcode;

    public String getProductColorChartId() {
        return productColorChartId;
    }

    public void setProductColorChartId(String productColorChartId) {
        this.productColorChartId = productColorChartId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getColorChartId() {
        return colorChartId;
    }

    public void setColorChartId(String colorChartId) {
        this.colorChartId = colorChartId;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }
    public String getColorcode() {
        return colorcode;
    }

    public void setColorcode(String colorcode) {
        this.colorcode = colorcode;
    }


}