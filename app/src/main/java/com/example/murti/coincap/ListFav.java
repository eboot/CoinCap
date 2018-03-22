package com.example.murti.coincap;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import utils.Constants;

/**
 * Created by murti on 19.03.2018.
 */

public class ListFav extends AppCompatActivity {

    private CustomAdapterList customAdapter;
    private ListView listview;
    private SearchView searchView;
    private RestAdapter restAdapter;
    private RestInterfaceController restInterface;
    private ProgressDialog progressDialog;
    private List<Items> fav_list = new ArrayList<Items>();
    private int  poss=0;
    private int detay=0;
    private String spdeger="USD";
    private String paraSembolu="$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_fav);
        listview = (ListView)findViewById(R.id.liste_favla);
        searchView=(SearchView)findViewById(R.id.search_fav);

        Bundle bundle = getIntent().getExtras();
         detay=bundle.getInt("detay");
        spdeger=bundle.getString("spdeger");


        if(InternetKontrol()){
            fav_list.clear();

            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(Constants.URL+spdeger+"&limit=10")
                    .build();
            restInterface = restAdapter.create(RestInterfaceController.class);

            degerler(detay);
            listview.setTextFilterEnabled(true);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    List<Items> new_list = new ArrayList<Items>();
                    for(Items temp:fav_list){

                        if (temp.getName().toLowerCase().contains(s.toLowerCase())||
                                temp.getSymbol().toLowerCase().contains(s.toLowerCase())){
                            new_list.add(temp);
                        }
                    }
                    customAdapter = new CustomAdapterList(getApplicationContext(),new_list,poss);
                    listview.setAdapter(customAdapter);
                    return true;
                }
            });

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    listGonder(position);

                }
            });

    }
    //internet yoksa
        else{

        AlertDialog.Builder alertdialog=new AlertDialog.Builder(this);
        alertdialog.setMessage("Please connect to the internet to use this app");
        alertdialog.setCancelable(false);
        alertdialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                System.exit(0);

            }
        });
        AlertDialog alert=alertdialog.create();
        alert.show();

    }
    }


    //internet varmı yok mu?
    public boolean InternetKontrol() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager.getActiveNetworkInfo() != null
                &&manager.getActiveNetworkInfo().isAvailable()
                &&manager.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }



    private void degerler(int p){
        poss=p;
        progressDialog = new ProgressDialog(ListFav.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        restInterface.getJsonValues(new Callback<RetrofitModel[]>() { // json array döneceği için modelimizi array tipinde belirledik
            @Override
            public void success(RetrofitModel[] model, Response response) {

                for (RetrofitModel modelValues : model) {

                    Items items = new Items();
                    items.setName(modelValues.name);
                    items.setSymbol(modelValues.symbol);

                    items.setPhoto(modelValues.photo);
                    items.setRank(modelValues.rank);
                    items.setId(modelValues.id);
                    items.setPercent_change_24h(modelValues.percentChange24h);
                    items.setPercent_change_1h(modelValues.percentChange1h);
                    items.setPercent_change_7d(modelValues.percentChange7d);
                    //spinnara göre
                    switch (poss){
                        case 0:
                            items.setVolume_usd24(modelValues._24hVolumeUsd);
                            items.setPrice_usd(modelValues.priceUsd);
                            items.setMarket_cap_usd(modelValues.marketCapUsd);
                            items.setParaSembol("$");

                            break;

                        case 1:
                            items.setPrice_usd(modelValues.priceEur);
                            items.setMarket_cap_usd(modelValues.marketCapEur);
                            items.setVolume_usd24(modelValues._24hVolumeEur);
                            items.setParaSembol("€");
                            break;
                        case 2:
                            items.setPrice_usd(modelValues.priceBtc);
                            items.setMarket_cap_usd(modelValues.marketCapBtc);
                            items.setVolume_usd24(modelValues._24hVolumeBtc);
                            items.setParaSembol("Ƀ");

                            break;
                        case 3:

                            items.setPrice_usd(modelValues.priceRub);
                            items.setMarket_cap_usd(modelValues.marketCapRub);
                            items.setVolume_usd24(modelValues._24hVolumeRub);
                            items.setParaSembol("р");
                            break;
                        case 4:
                            items.setPrice_usd(modelValues.priceTry);
                            items.setMarket_cap_usd(modelValues.marketCapTry);
                            items.setVolume_usd24(modelValues._24hVolumeTry);
                            items.setParaSembol("TL");
                            break;
                        case 5:
                            items.setPrice_usd(modelValues.priceJpy);
                            items.setMarket_cap_usd(modelValues.marketCapJpy);
                            items.setVolume_usd24(modelValues._24hVolumeJpy);
                            items.setParaSembol("¥");
                            break;
                    }

                    //burasında kaldım
                        fav_list.add(items);
                }

                progressDialog.cancel();

                customAdapter = new CustomAdapterList(getApplicationContext(),fav_list,poss);
                listview.setAdapter(customAdapter);

            }

            public void failure(RetrofitError error) {

                String merror = error.getMessage();
                Toast.makeText(getApplicationContext(),merror,Toast.LENGTH_LONG).show();
            }
        });
    }


    //DetailsActivtyye deger gönderme
    private void listGonder(int position){

        Intent intent = new Intent(getApplicationContext(),AddCoin.class);

        // listview in hangi itemine tıklandıysa diğer sayfada ilgili resmi göstermek için sunucudan gelen resmin adını yolluyoruz.
        intent.putExtra("id",customAdapter.getItem(position).getId());
        intent.putExtra("name",customAdapter.getItem(position).getName());
        intent.putExtra("symbol",customAdapter.getItem(position).getSymbol());
        intent.putExtra("rank",customAdapter.getItem(position).getRank());
        intent.putExtra("market_cap_usd",customAdapter.getItem(position).getMarket_cap_usd());
        intent.putExtra("priceUsd",customAdapter.getItem(position).getPrice_usd());
        intent.putExtra("paraSembol",customAdapter.getItem(position).getParaSembol());

        startActivity(intent);
    }


}
