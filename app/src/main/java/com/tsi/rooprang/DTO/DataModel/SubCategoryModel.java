package com.tsi.rooprang.DTO.DataModel;

public class SubCategoryModel {

    private String cat_id;
    private int cat_image;
    private String cat_title;
    private String cat_type;
    private String cat_parent;
    private String cat_super_parent;

    public SubCategoryModel(String cat_id, int cat_image, String cat_title, String cat_type, String cat_parent, String cat_super_parent) {
        this.cat_id = cat_id;
        this.cat_image = cat_image;
        this.cat_title = cat_title;
        this.cat_type = cat_type;
        this.cat_parent = cat_parent;
        this.cat_super_parent = cat_super_parent;
    }
    public SubCategoryModel(  String cat_title,int cat_image) {

        this.cat_image = cat_image;
        this.cat_title = cat_title;

    }

    public String getCat_super_parent() {
        return cat_super_parent;
    }

    public void setCat_super_parent(String cat_super_parent) {
        this.cat_super_parent = cat_super_parent;
    }

    public String getCat_parent() {
        return cat_parent;
    }

    public void setCat_parent(String cat_parent) {
        this.cat_parent = cat_parent;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public int getCat_image() {
        return cat_image;
    }

    public void setCat_image(int cat_image) {
        this.cat_image = cat_image;
    }

    public String getCat_title() {
        return cat_title;
    }

    public void setCat_title(String cat_title) {
        this.cat_title = cat_title;
    }

    public String getCat_type() {
        return cat_type;
    }

    public void setCat_type(String cat_type) {
        this.cat_type = cat_type;
    }
}
