package com.tsi.rooprang.retrofit;

import com.tsi.rooprang.DTO.Address_DTO.Add_Address_DTO;
import com.tsi.rooprang.DTO.Address_DTO.Delete_Address_DTO;
import com.tsi.rooprang.DTO.Address_DTO.Show_Address_DTO;
import com.tsi.rooprang.DTO.Color.ProductImagesByColorId_DTO;
import com.tsi.rooprang.DTO.Dashboard.HomeDTO;
import com.tsi.rooprang.DTO.Fillter.FillterDto;
import com.tsi.rooprang.DTO.Payment_dto.Order_Placed_DTO;
import com.tsi.rooprang.DTO.Sub_cat_dtos.Sub_Category_DTO;
import com.tsi.rooprang.DTO.cart.Add__To_Cart_DTO;
import com.tsi.rooprang.DTO.cart.Cart_DTO;
import com.tsi.rooprang.DTO.cart.Cart_Total_amount_DTO;
import com.tsi.rooprang.DTO.cart.Remove_Cart_DTO;
import com.tsi.rooprang.DTO.login.Forgot_Pass_DTO;
import com.tsi.rooprang.DTO.login.Login_DTO;
import com.tsi.rooprang.DTO.login.Resend_OTP;
import com.tsi.rooprang.DTO.login.ResetPwd_DTO;
import com.tsi.rooprang.DTO.login.Verify_OTP;


import com.tsi.rooprang.DTO.orderhistory.OrderHistory_DTO;
import com.tsi.rooprang.DTO.orderhistory.OrderReturn_DTO;
import com.tsi.rooprang.DTO.parent_all_category_dtos.CategoryHome_DTO;
import com.tsi.rooprang.DTO.parent_all_category_dtos.Category_DTO;
import com.tsi.rooprang.DTO.product.Product_DTO;
import com.tsi.rooprang.DTO.profile.Change_Pwd_DTO;
import com.tsi.rooprang.DTO.profile.Update_Profile_DTO;
import com.tsi.rooprang.DTO.profile.Update_image_DTO;
import com.tsi.rooprang.DTO.profile.ViewProfile_DTO;
import com.tsi.rooprang.DTO.signup.SignUp_DTO;
import com.tsi.rooprang.DTO.singleproduct.CheckPincode_DTO;
import com.tsi.rooprang.DTO.singleproduct.Single_Product_DTO;
import com.tsi.rooprang.DTO.singleproduct.Update_Cart_DTO;
import com.tsi.rooprang.DTO.wishlist.Add_Wish_DTO;
import com.tsi.rooprang.DTO.wishlist.Remove_wish_DTO;
import com.tsi.rooprang.DTO.wishlist.Show_Wish_DTO;


import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("signUp.php")
    Call<SignUp_DTO> api_signup(@Field("name") String name,
                                @Field("email") String email,
                                @Field("phone") String phone,
                                @Field("password") String password);

    @FormUrlEncoded
    @POST("login.php")
    Call<Login_DTO> api_login(@Field("email") String email, @Field("password") String password, @Field("device_id") String device_id);

    @FormUrlEncoded
    @POST("forgetPassword.php")
    Call<Forgot_Pass_DTO> api_forgot_password(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("verifyOtp.php")
    Call<Verify_OTP> api_verify_otp(@Field("phone") String phone, @Field("otp") String otp);

    @FormUrlEncoded
    @POST("resendOtp.php")
    Call<Resend_OTP> api_resend_otp(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("checkPincodeForDeliveryArea.php")
    Call<CheckPincode_DTO> api_check_pincode(@Field("product_id") String product_id, @Field("pincode") String pincode);

    /////////for main sub category.........///////
    @FormUrlEncoded
    @POST("category.php")
    Call<Sub_Category_DTO> api_sub_category(@Field("category_id") String category_id);

    /////////for main category.........///////
    @FormUrlEncoded
    @POST("category.php")
    Call<Category_DTO> category_api(@Field("category_id") String category_id);

    @GET("showParentCategory.php")
    Call<CategoryHome_DTO> all_category_api();


    @FormUrlEncoded
    @POST("showProductBysubCategoryId.php")
    Call<Product_DTO> api_product(@Field("subCategory_id") String sub_category_id, @Field("page_count") String page_count, @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("sortProductBySubcategoryID.php")
    Call<Product_DTO> api_product_sortProduct(@Field("user_id") String user_id, @Field("subCategory_id") String subCategory_id, @Field("page_count") String page_count, @Field("sortID") String sortID);

    @FormUrlEncoded
    @POST("viewProfile.php")
    Call<ViewProfile_DTO> api_viewprofile(@Field("id") String id);


    @FormUrlEncoded
    @POST("updateUserProfile.php")
    Call<Update_Profile_DTO> api_updateUserProfile(@Field("id") String id, @Field("name") String name,
                                                   @Field("email") String email,
                                                   @Field("phone") String phone,
                                                   @Field("password") String password, @Field("address") String address,
                                                   @Field("pincode") String pincode, @Field("state") String state,
                                                   @Field("landmark") String landmark, @Field("city") String city);

//    @FormUrlEncoded
//    @POST("updateUserProfile.php")
//    Call<ViewProfile_DTO> api_updateUserProfile(@Field("id") String id, @Field("name") String name, @Field("email") String email,
//                                                @Field("phone") String phone,
//                                                @Field("password") String password, @Field("address") String address,
//                                                @Field("pincode") String pincode, @Field("state") String state,
//                                                @Field("landmark") String landmark, @Field("city") String city);


    @FormUrlEncoded
    @POST("showProductDetailByProductId.php")
    Call<Single_Product_DTO> api_single_product(@Field("user_id") String user_id, @Field("product_id") String product_id);

    @FormUrlEncoded
    @POST("reset_password.php")
    Call<ResetPwd_DTO> api_reset_pwd(@Field("id") String id,
                                     @Field("password") String password);

    @FormUrlEncoded
    @POST("addToWishlist.php")
    Call<Add_Wish_DTO> api_add_wishlist(@Field("user_id") String user_id,
                                        @Field("product_id") String product_id);

    @FormUrlEncoded
    @POST("removeWishlist.php")
    Call<Remove_wish_DTO> api_remove_wishlist(@Field("user_id") String user_id,
                                              @Field("product_id") String product_id);


    @FormUrlEncoded
    @POST("showWishlistByUserId.php")
    Call<Show_Wish_DTO> api_show_wishlist(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("updatePassword.php")
    Call<Change_Pwd_DTO> api_change_pwd(@Field("id") String id,
                                        @Field("oldpassword") String oldpassword,
                                        @Field("newpassword") String newpassword);


    @FormUrlEncoded
    @POST("addToCart.php")
    Call<Add__To_Cart_DTO> api_cart(@Field("user_id") String user_id,
                                    @Field("subcategory_id") String subcategory_id,
                                    @Field("product_id") String product_id,
                                    @Field("product_aval_age_id") String product_aval_age_id,
                                    @Field("product_avail_size_id") String product_avail_size_id,
                                    @Field("product_color_chart_id") String product_color_chart_id,
                                    @Field("qty") String qty,
                                    @Field("amount") String amount,
                                    @Field("pincode") String pincode,
                                    @Field("shipping_charges") String shipping_charges);

    @FormUrlEncoded
    @POST("showCartListByUserId.php")
    Call<Cart_DTO> api_cart_details_api(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("showTotalCartAmounts.php")
    Call<Cart_Total_amount_DTO> api_cart_total_amount(@Field("user_id") String user_id,
                                                      @Field("product_id") String product_id,
                                                      @Field("sale_price") String sale_price,
                                                      @Field("qty") String qty,
                                                      @Field("discount") String discount);

    @FormUrlEncoded
    @POST("removeProductFromCart.php")
    Call<Remove_Cart_DTO> remove_cart_api(@Field("user_id") String user_id, @Field("product_id") String product_id);

    @FormUrlEncoded
    @POST("updateCart.php")
    Call<Update_Cart_DTO> api_update_cart_api(@Field("user_id") String user_id,
                                              @Field("product_id") String product_id,
                                              @Field("product_avail_size_id") String product_avail_size_id,
                                              @Field("product_aval_age_id") String product_aval_age_id,
                                              @Field("product_color_chart_id") String product_color_chart_id,
                                              @Field("qty") String qty,
                                              @Field("pincode") String pincode,
                                              @Field("shipping_charges") String vShipping_charges);

    @FormUrlEncoded
    @POST("addAddress.php")
    Call<Add_Address_DTO> add_address_api(@Field("user_id") String user_id, @Field("full_name") String full_name,
                                          @Field("pincode") String pincode, @Field("full_address") String full_address,
                                          @Field("landmark") String landmark, @Field("city") String city, @Field("state") String state,
                                          @Field("mobile") String mobile);


    @FormUrlEncoded
    @POST("showAddressByUserId.php")
    Call<Show_Address_DTO> show_address_api(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("searchSubcategory.php")
    Call<Show_Address_DTO> search_api(@Field("name") String name);

    @GET("homePageData.php")
    Call<HomeDTO> api_home();


    @FormUrlEncoded
    @POST("deleteAddressByUserId.php")
    Call<Delete_Address_DTO> delete_address_api(@Field("user_id") String user_id, @Field("address_id") String address_id);

    @FormUrlEncoded
    @POST("filterProduct.php")
    Call<Product_DTO> apply_filter_api(@Field("subCategory_id") String subCategory_id,
                                       @Field("sale_price") String sale_price,
                                       @Field("discount") String discount,
                                       @Field("brand_id") String brand_id,
                                       @Field("size_chart_id") String size_chart_id,
                                       @Field("age_chart_id") String age_chart_id,
                                       @Field("color_chart_id") String color_chart_id,
                                       @Field("page_count") String page_count);

    @FormUrlEncoded
    @POST("addOrder.php")
    Call<Order_Placed_DTO> call_apply_order_api(@Field("user_id") String user_id,
                                                @Field("address_id") String address_id,
                                                @Field("payment_type") String payment_type,
                                                @Field("product_id") String product_id,
                                                @Field("qty") String qty,
                                                @Field("amount") String amount,
                                                @Field("discount") String discount);

    @Multipart
    @POST("editUserImage.php")
    Call<Update_image_DTO> api_update_profile_photo(
            @Part MultipartBody.Part mFileTemp,
            @Part MultipartBody.Part vUserId);
    @FormUrlEncoded
    @POST("showProductImagesByColorId.php")
    Call<ProductImagesByColorId_DTO> ProductImagesByColorId_api(@Field("product_id") String product_id, @Field("color_chart_id") String color_chart_id);

    /////////for Orders History.........///////
    @FormUrlEncoded
    @POST("showOrderByUserId.php")
    Call<OrderHistory_DTO> api_order_history(@Field("user_id") String user_id); /////////for Orders History.........///////


    @FormUrlEncoded
    @POST("returnOrderedProduct.php")
    Call<OrderReturn_DTO> api_order_return(@Field("order_product_id") String order_product_id,
                                           @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("cancelorder.php")
    Call<OrderReturn_DTO> api_order_cancel(@Field("order_product_id") String order_product_id,
                                           @Field("user_id") String user_id);
}
