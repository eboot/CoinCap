package com.example.murti.coincap;


public class Items   {

    private String name;
    private String  id;
    private String symbol;
    private String rank;
    private String photo;
    private String percent_change_24h,percent_change_1h,percent_change_7d;
    //spinera göre degisen degerler
    private String market_cap_usd,volume_usd24,price_usd;

    //param
    private double toplam;
    private String paraSembol;

    public void setParaSembol(String paraSembol){
        this.paraSembol=paraSembol;
    }
    public String getParaSembol(){
        return this.paraSembol;
    }

    public void setToplam(double toplam){
        this.toplam=toplam;
    }
    public double getToplam(){
        return this.toplam;
    }

    public  void  setPercent_change_1h(String percent_change_1h){
        this.percent_change_1h=percent_change_1h;
    }
    public String getPercent_change_1h(){
        return this.percent_change_1h;
    }
    public  void  setPercent_change_7d(String percent_change_7d){
        this.percent_change_7d=percent_change_7d;
    }
    public String getPercent_change_7d(){
        return this.percent_change_7d;
    }

    public void setPercent_change_24h(String percent_change_24h){
        this.percent_change_24h=percent_change_24h;
    }
    public String getPercent_change_24h(){
        return this.percent_change_24h;
    }

    public void setName(String name){

        this.name = name;
    }
    public String getName(){

        return this.name;
    }
    public void setId(String id){

        this.id = id;
    }
    public String getId(){

        return this.id;
    }
    public void setSymbol(String symbol){

        this.symbol = symbol;
    }
    public String getSymbol(){

        return this.symbol;
    }
    public void setRank(String rank){

        this.rank = rank;
    }
    public String getRank(){

        return this.rank;
    }
    public void setPhoto(String photo){

        this.photo = photo;
    }
    public String getPhoto(){

        return this.photo;
    }

    //USD item degerleri
    public void setVolume_usd24(String volume_usd24){
        this.volume_usd24=volume_usd24;
    }
    public String getVolume_usd24(){
        return this.volume_usd24;
    }
    public void setPrice_usd(String price_usd){

        this.price_usd = price_usd;
    }
    public String getPrice_usd(){

        return this.price_usd;
    }
    public  void  setMarket_cap_usd(String market_cap_usd){
        this.market_cap_usd=market_cap_usd;
    }
    public String getMarket_cap_usd(){
        return this.market_cap_usd;
    }

}