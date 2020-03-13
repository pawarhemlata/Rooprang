
package com.tsi.rooprang.DTO.Dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Home_All_Banner_Detail_DTO {

    @SerializedName("app_home_banner_id")
    @Expose
    private String appHomeBannerId;
    @SerializedName("banner_name")
    @Expose
    private String bannerName;
    @SerializedName("banner_image")
    @Expose
    private String bannerImage;
    @SerializedName("subcategory_id")
    @Expose
    private String subcategoryId;
    @SerializedName("created")
    @Expose
    private String created;

    public String getAppHomeBannerId() {
        return appHomeBannerId;
    }

    public void setAppHomeBannerId(String appHomeBannerId) {
        this.appHomeBannerId = appHomeBannerId;
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
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

}
