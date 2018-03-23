package com.coin.murti.coincap;


import retrofit.Callback;
import retrofit.http.GET;



public interface RestInterfaceController {

    // GET yada POST mu olduğunu belirliyoruz.
    @GET("/servejson")
    void getJsonValues(Callback<RetrofitModel[]> response);

}