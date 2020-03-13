package com.tsi.rooprang.DTO.singleproduct;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cartlist {

@SerializedName("cart_status")
@Expose
private String cartStatus;

public String getCartStatus() {
return cartStatus;
}

public void setCartStatus(String cartStatus) {
this.cartStatus = cartStatus;
}

}