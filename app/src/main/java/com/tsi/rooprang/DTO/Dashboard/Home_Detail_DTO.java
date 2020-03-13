
package com.tsi.rooprang.DTO.Dashboard;

import java.util.ArrayList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Home_Detail_DTO {

    @SerializedName("pagerData")
    @Expose
    private ArrayList<Pager_Detail_DTO> pagerData = null;
    @SerializedName("dealofDay")
    @Expose
    private DealofDay_DTO dealofDayDTO;
    @SerializedName("shopByCategory")
    @Expose
    private ArrayList<ShopByCategoryDTO> shopByCategoryDTO = null;
    @SerializedName("allHomeBannerData")
    @Expose
    private ArrayList<Home_All_Banner_Detail_DTO> allHomeBannerData = null;
    @SerializedName("bloomStyleData")
    @Expose
    private ArrayList<BloomStyleDTO> bloomStyleData = null;
    @SerializedName("trendingData")
    @Expose
    private Trending_DTO trendingDTO;
    @SerializedName("latestProductData")
    @Expose
    private ArrayList<LatestProduct_Detail_DTO> latestProductData = null;

    public ArrayList<Pager_Detail_DTO> getPagerData() {
        return pagerData;
    }

    public void setPagerData(ArrayList<Pager_Detail_DTO> pagerData) {
        this.pagerData = pagerData;
    }

    public DealofDay_DTO getDealofDayDTO() {
        return dealofDayDTO;
    }

    public void setDealofDayDTO(DealofDay_DTO dealofDayDTO) {
        this.dealofDayDTO = dealofDayDTO;
    }

    public ArrayList<ShopByCategoryDTO> getShopByCategoryDTO() {
        return shopByCategoryDTO;
    }

    public void setShopByCategoryDTO(ArrayList<ShopByCategoryDTO> shopByCategoryDTO) {
        this.shopByCategoryDTO = shopByCategoryDTO;
    }

    public ArrayList<Home_All_Banner_Detail_DTO> getAllHomeBannerData() {
        return allHomeBannerData;
    }

    public void setAllHomeBannerData(ArrayList<Home_All_Banner_Detail_DTO> allHomeBannerData) {
        this.allHomeBannerData = allHomeBannerData;
    }

    public ArrayList<BloomStyleDTO> getBloomStyleData() {
        return bloomStyleData;
    }

    public void setBloomStyleData(ArrayList<BloomStyleDTO> bloomStyleData) {
        this.bloomStyleData = bloomStyleData;
    }

    public Trending_DTO getTrendingDTO() {
        return trendingDTO;
    }

    public void setTrendingDTO(Trending_DTO trendingDTO) {
        this.trendingDTO = trendingDTO;
    }

    public ArrayList<LatestProduct_Detail_DTO> getLatestProductData() {
        return latestProductData;
    }

    public void setLatestProductData(ArrayList<LatestProduct_Detail_DTO> latestProductData) {
        this.latestProductData = latestProductData;
    }

}
