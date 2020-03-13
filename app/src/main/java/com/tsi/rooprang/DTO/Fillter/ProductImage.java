package com.tsi.rooprang.DTO.Fillter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductImage {

@SerializedName("product_image_id")
@Expose
private String productImageId;
@SerializedName("product_id")
@Expose
private String productId;
@SerializedName("color_chart_id")
@Expose
private String colorChartId;
@SerializedName("product_image")
@Expose
private String productImage;

public String getProductImageId() {
return productImageId;
}

public void setProductImageId(String productImageId) {
this.productImageId = productImageId;
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

public String getProductImage() {
return productImage;
}

public void setProductImage(String productImage) {
this.productImage = productImage;
}

}