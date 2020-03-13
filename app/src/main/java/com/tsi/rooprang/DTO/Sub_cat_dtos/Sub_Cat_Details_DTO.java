package com.tsi.rooprang.DTO.Sub_cat_dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sub_Cat_Details_DTO {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("parent_subCategory_id")
    @Expose
    private String parentSubCategoryId;
    @SerializedName("created")
    @Expose
    private String created;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getParentSubCategoryId() {
        return parentSubCategoryId;
    }

    public void setParentSubCategoryId(String parentSubCategoryId) {
        this.parentSubCategoryId = parentSubCategoryId;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

}
