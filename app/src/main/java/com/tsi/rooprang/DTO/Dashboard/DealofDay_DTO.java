
package com.tsi.rooprang.DTO.Dashboard;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DealofDay_DTO {

    @SerializedName("subCategory_id")
    @Expose
    private Integer subCategoryId;
    @SerializedName("data")
    @Expose
    private List<DealOfTheDay_Detail_DTO> data = null;

    public Integer getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Integer subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public List<DealOfTheDay_Detail_DTO> getData() {
        return data;
    }

    public void setData(List<DealOfTheDay_Detail_DTO> data) {
        this.data = data;
    }

}
