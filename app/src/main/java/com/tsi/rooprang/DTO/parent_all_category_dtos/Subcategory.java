package com.tsi.rooprang.DTO.parent_all_category_dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subcategory {

@SerializedName("subcat_name")
@Expose
private String subcatName;
@SerializedName("subcat_image")
@Expose
private String subcatImage;
@SerializedName("parent_subcategory_id")
@Expose
private String parentSubcategoryId;
@SerializedName("subcat_created")
@Expose
private String subcatCreated;
@SerializedName("parent")
@Expose
private String parent;

public String getSubcatName() {
return subcatName;
}

public void setSubcatName(String subcatName) {
this.subcatName = subcatName;
}

public String getSubcatImage() {
return subcatImage;
}

public void setSubcatImage(String subcatImage) {
this.subcatImage = subcatImage;
}

public String getParentSubcategoryId() {
return parentSubcategoryId;
}

public void setParentSubcategoryId(String parentSubcategoryId) {
this.parentSubcategoryId = parentSubcategoryId;
}

public String getSubcatCreated() {
return subcatCreated;
}

public void setSubcatCreated(String subcatCreated) {
this.subcatCreated = subcatCreated;
}

public String getParent() {
return parent;
}

public void setParent(String parent) {
this.parent = parent;
}

}