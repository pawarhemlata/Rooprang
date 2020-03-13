package com.tsi.rooprang.DTO.parent_all_category_dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category_Detail_DTO {

@SerializedName("name")
@Expose
private String categoryName;
@SerializedName("image")
@Expose
private String categoryImage;
@SerializedName("parent_category_id")
@Expose
private String parentCategoryId;
@SerializedName("created")
@Expose
private String categoryCreated;

public String getCategoryName() {
return categoryName;
}

public void setCategoryName(String categoryName) {
this.categoryName = categoryName;
}

public String getCategoryImage() {
return categoryImage;
}

public void setCategoryImage(String categoryImage) {
this.categoryImage = categoryImage;
}

public String getParentCategoryId() {
return parentCategoryId;
}

public void setParentCategoryId(String parentCategoryId) {
this.parentCategoryId = parentCategoryId;
}

public String getCategoryCreated() {
return categoryCreated;
}

public void setCategoryCreated(String categoryCreated) {
this.categoryCreated = categoryCreated;
}


}