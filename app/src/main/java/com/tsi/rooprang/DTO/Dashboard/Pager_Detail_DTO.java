
package com.tsi.rooprang.DTO.Dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pager_Detail_DTO {

    @SerializedName("page_banner_id")
    @Expose
    private String pageBannerId;
    @SerializedName("banner")
    @Expose
    private String banner;
    @SerializedName("subcategory_id")
    @Expose
    private String subcategoryId;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("banner_name")
    @Expose
    private String banner_name;

    public String getPageBannerId() {
        return pageBannerId;
    }

    public void setPageBannerId(String pageBannerId) {
        this.pageBannerId = pageBannerId;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(String subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getBanner_name() {
        return banner_name;
    }

    public void setBanner_name(String banner_name) {
        this.banner_name = banner_name;
    }
}
