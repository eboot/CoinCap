package com.example.murti.coincap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RetrofitModel {


    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("symbol")
    @Expose
    public String symbol;
    @SerializedName("rank")
    @Expose
    public String rank;

    @SerializedName("percent_change_1h")
    @Expose
    public String percentChange1h;
    @SerializedName("percent_change_24h")
    @Expose
    public String percentChange24h;
    @SerializedName("percent_change_7d")
    @Expose
    public String percentChange7d;
    @SerializedName("last_updated")
    @Expose
    public String lastUpdated;


    //icon
    @SerializedName("photo")
    @Expose
    public String photo;

    //USD degerleri
    @SerializedName("price_usd")
    @Expose
    public String priceUsd;
    @SerializedName("24h_volume_usd")
    @Expose
    public String _24hVolumeUsd;
    @SerializedName("market_cap_usd")
    @Expose
    public String marketCapUsd;

    //euro degerleri

    @SerializedName("price_eur")
    @Expose
    public String priceEur;

    @SerializedName("24h_volume_eur")
    @Expose
    public String _24hVolumeEur;

    @SerializedName("market_cap_eur")
    @Expose
    public String marketCapEur;


    //BTC degerleri
    @SerializedName("price_btc")
    @Expose
    public String priceBtc;

    @SerializedName("24h_volume_btc")
    @Expose
    public String _24hVolumeBtc;

    @SerializedName("market_cap_btc")
    @Expose
    public String marketCapBtc;

    //RUB degerleri
    @SerializedName("price_rub")
    @Expose
    public String priceRub;

    @SerializedName("24h_volume_rub")
    @Expose
    public String _24hVolumeRub;

    @SerializedName("market_cap_rub")
    @Expose
    public String marketCapRub;

    //TRY degerleri
    @SerializedName("price_try")
    @Expose
    public String priceTry;

    @SerializedName("24h_volume_try")
    @Expose
    public String _24hVolumeTry;

    @SerializedName("market_cap_try")
    @Expose
    public String marketCapTry;

    //JPY değerleri
    @SerializedName("price_jpy")
    @Expose
    public String priceJpy;

    @SerializedName("24h_volume_jpy")
    @Expose
    public String _24hVolumeJpy;

    @SerializedName("market_cap_jpy")
    @Expose
    public String marketCapJpy;

}