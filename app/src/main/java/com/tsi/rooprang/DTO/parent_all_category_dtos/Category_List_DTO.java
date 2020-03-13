package com.tsi.rooprang.DTO.parent_all_category_dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category_List_DTO {

@SerializedName("category_name")
@Expose
private String categoryName;
@SerializedName("category_image")
@Expose
private String categoryImage;
@SerializedName("parent_category_id")
@Expose
private String parentCategoryId;
@SerializedName("category_created")
@Expose
private String categoryCreated;
@SerializedName("subcategory")
@Expose
private List<Subcategory> subcategory = null;

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

public List<Subcategory> getSubcategory() {
return subcategory;
}

public void setSubcategory(List<Subcategory> subcategory) {
this.subcategory = subcategory;
}

}