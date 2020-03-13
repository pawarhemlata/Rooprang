package com.tsi.rooprang.DTO.singleproduct;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductAvailabelSize {

    @SerializedName("product_avail_size_id")
    @Expose
    private String productAvailSizeId;
    @SerializedName("size_chart_id")
    @Expose
    private String sizeChartId;
    @SerializedName("size_number")
    @Expose
    private String sizeNumber;

    public String getProductAvailSizeId() {
        return productAvailSizeId;
    }

    public void setProductAvailSizeId(String productAvailSizeId) {
        this.productAvailSizeId = productAvailSizeId;
    }

    public String getSizeChartId() {
        return sizeChartId;
    }

    public void setSizeChartId(String sizeChartId) {
        this.sizeChartId = sizeChartId;
    }

    public String getSizeNumber() {
        return sizeNumber;
    }

    public void setSizeNumber(String sizeNumber) {
        this.sizeNumber = sizeNumber;
    }


}