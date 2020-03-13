package com.tsi.rooprang.DTO.singleproduct;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wishlist {

@SerializedName("wishlist_status")
@Expose
private String wishlistStatus;

public String getWishlistStatus() {
return wishlistStatus;
}

public void setWishlistStatus(String wishlistStatus) {
this.wishlistStatus = wishlistStatus;
}

}