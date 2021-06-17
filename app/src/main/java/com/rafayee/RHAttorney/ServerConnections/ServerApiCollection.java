package com.rafayee.RHAttorney.ServerConnections;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ServerApiCollection {

    //String BASE_URL = "https://api.robinsonandhenry.com/api/";
   // String IMAGE_URL = "https://api.robinsonandhenry.com/";

    String BASE_URL = "https://dev-api.robinsonandhenry.com/api/";
    String IMAGE_URL = "https://dev-api.robinsonandhenry.com";


    @POST
    Call<ResponseBody> PostDataFromServer(@Url String url, @Body JsonObject registerObj);
    @POST("attorneys/login")
    Call<ResponseBody> LoginApi(@Body JsonObject loginObject );
    @POST("attorneys/forgotpassword")
    Call<ResponseBody> ForgotApi(@Body JsonObject loginObject );

    @POST("attorneys/verify")
    Call<ResponseBody> verifyApi(@Body JsonObject loginObject );

    @POST("attorneys/reset_password")
    Call<ResponseBody> resetPassword(@Body JsonObject loginObject );

    @POST("attorneys/changepassword")
    Call<ResponseBody> updatePassword(@Body JsonObject loginObject );

    @POST("attorneys/signout")
    Call<ResponseBody> signOutApi(@Body JsonObject logout);

    @GET
    Call<ResponseBody> fetchAttorneyList(@Url String url);
}