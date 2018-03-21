package com.example.murti.coincap;


import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import utils.Constants;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {


    private ListView listview;
    private RestAdapter restAdapter;
    private RestInterfaceController restInterface;
    private ProgressDialog progressDialog;
    private List<Items> list = new ArrayList<Items>();
    private CustomAdapter customAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    Spinner spinner;
    private String spdeger="USD";
    private int  poss=0;
    private int detay=0;
   // private  Button buttonSort;
    private TextView toplamPara;
    private boolean sortla=true;
    ArrayList<Food> list_fav;
    private double toplam_para;
    private String paraSembolu="$";

    private static final String TAG = "MainActivity";
    private AdView mAdView;
    NumberFormat numberFormat=NumberFormat.getInstance();

    DecimalFormat df=new DecimalFormat("#.###");
    private int girKontrol=0;


    public static SQLiteHelper sqLiteHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        numberFormat.setParseIntegerOnly(false);
        initt();
        list_fav = new ArrayList<>();
        list_fav.clear();

        sqLiteHelper=new SQLiteHelper(this,"FoodDB,sqlite",null,1);

        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS FOOD( name VARCHAR, price REAL, rank INTEGER)");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent ıntent=new Intent(MainActivity.this,ListFav.class);
                ıntent.putExtra("spdeger",spdeger);
                ıntent.putExtra("detay",detay);
                startActivity(ıntent);

            }
        });






        //internet varsa
        if(InternetKontrol()){

            mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
            //tıklanan satırın değerleri
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    listGonder(position);

                }
            });
            //spinnerda seşilen değer
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                try{
                    spdeger=spinner.getSelectedItem().toString();
                    detay=pos;

                    restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Constants.URL+spdeger+"&limit=10")
                            .build();
                           restInterface = restAdapter.create(RestInterfaceController.class);

                    degerler(pos);
                } catch (Exception e){
                    Log.e("spinner error", e.getMessage());
                }
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


           swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    try {


                    restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Constants.URL+spdeger+"&limit=10")
                            .build();

                    restInterface = restAdapter.create(RestInterfaceController.class);

                    degerler(detay);
                    swipeRefreshLayout.setRefreshing(false);
                    } catch (Exception e){
                        Log.e("refresh error", e.getMessage());
                    }

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

    @Override
    public void onBackPressed() {

                if (getIntent().getBooleanExtra("EXIT", false)) {
                    finish();
                }
                else {
                    System.exit(0);
                }
    }




    //delete menu
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.coinAddd){

            Intent ıntent=new Intent(MainActivity.this,ListFav.class);
            ıntent.putExtra("spdeger",spdeger);
            ıntent.putExtra("detay",detay);
            startActivity(ıntent);
        }
        return false;
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
        list.clear();
        toplam_para=0.0;
        progressDialog = new ProgressDialog(MainActivity.this);
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
                            break;

                        case 1:
                            items.setPrice_usd(modelValues.priceEur);
                            items.setMarket_cap_usd(modelValues.marketCapEur);
                            items.setVolume_usd24(modelValues._24hVolumeEur);
                            break;
                        case 2:
                            items.setPrice_usd(modelValues.priceBtc);
                            items.setMarket_cap_usd(modelValues.marketCapBtc);
                            items.setVolume_usd24(modelValues._24hVolumeBtc);

                            break;
                        case 3:

                            items.setPrice_usd(modelValues.priceRub);
                            items.setMarket_cap_usd(modelValues.marketCapRub);
                            items.setVolume_usd24(modelValues._24hVolumeRub);
                            break;
                        case 4:
                            items.setPrice_usd(modelValues.priceTry);
                            items.setMarket_cap_usd(modelValues.marketCapTry);
                            items.setVolume_usd24(modelValues._24hVolumeTry);
                            break;
                        case 5:
                            items.setPrice_usd(modelValues.priceJpy);
                            items.setMarket_cap_usd(modelValues.marketCapJpy);
                            items.setVolume_usd24(modelValues._24hVolumeJpy);
                            break;
                    }

                    Cursor cursor =sqLiteHelper.getData("SELECT * FROM FOOD");

                    while (cursor.moveToNext()) {
                        String nameid = cursor.getString(0);
                        double pricedegeri = cursor.getDouble(1);
                        int rankdegeri = cursor.getInt(2);
                        girKontrol=1;
                        if(Float.valueOf(modelValues.rank)==rankdegeri){
                            girKontrol=1;
                            double carp=1;
                            switch (poss){
                                case 0:
                                    carp=pricedegeri*Float.valueOf(modelValues.priceUsd);
                                    paraSembolu="$";
                                    break;
                                case 1:
                                    carp=pricedegeri*Float.valueOf(modelValues.priceEur);
                                    paraSembolu="€";
                                    break;
                                case 2:
                                    carp=pricedegeri*Float.valueOf(modelValues.priceBtc);
                                    paraSembolu="Ƀ";
                                    break;
                                case 3:
                                    carp=pricedegeri*Float.valueOf(modelValues.priceRub);
                                    paraSembolu="р";
                                    break;
                                case 4:
                                    carp=pricedegeri*Float.valueOf(modelValues.priceTry);
                                    paraSembolu="TL";
                                    break;
                                case 5:
                                    carp=pricedegeri*Float.valueOf(modelValues.priceJpy);
                                    paraSembolu="¥";
                                    break;
                            }
                            toplam_para+=carp;
                            items.setToplam(pricedegeri);
                            items.setParaSembol(paraSembolu);
                            list.add(items);
                        }
                    }

                            if(girKontrol==0){
                            if(Float.valueOf(modelValues.rank)==1) {
                                items.setToplam(0);
                                items.setParaSembol(paraSembolu);
                                list.add(items);
                            }
                        }

                }

                progressDialog.cancel();

                customAdapter = new CustomAdapter(getApplicationContext(),list,poss);
                listview.setAdapter(customAdapter);
                numberFormat=NumberFormat.getNumberInstance(Locale.US);
                String sayi=numberFormat.format(toplam_para);
               // String topla_format=df.format(toplam_para);

                toplamPara.setText(paraSembolu+" "+sayi);

            }
            @Override
            public void failure(RetrofitError error) {

                String merror = error.getMessage();
                Toast.makeText(getApplicationContext(),merror,Toast.LENGTH_LONG).show();
            }
        });

    }

    //DetailsActivtyye deger gönderme
    private void listGonder(int position){

        Intent intent = new Intent(getApplicationContext(),DetailsActivity.class);

        // listview in hangi itemine tıklandıysa diğer sayfada ilgili resmi göstermek için sunucudan gelen resmin adını yolluyoruz.
        intent.putExtra("image_name",list.get(position).getId());
        intent.putExtra("name",list.get(position).getName());
        intent.putExtra("symbol",list.get(position).getSymbol());
        intent.putExtra("percen24h",list.get(position).getPercent_change_24h());
        intent.putExtra("percen1h",list.get(position).getPercent_change_1h());
        intent.putExtra("percen7d",list.get(position).getPercent_change_7d());
        intent.putExtra("rank",list.get(position).getRank());

        intent.putExtra("market_cap_usd",list.get(position).getMarket_cap_usd());
        intent.putExtra("priceUsd",list.get(position).getPrice_usd());
        intent.putExtra("detay",detay);
        intent.putExtra("volume_usd24",list.get(position).getVolume_usd24());
        intent.putExtra("spdeger",spdeger);
        intent.putExtra("paraSembolu",paraSembolu);

        startActivity(intent);
    }

    //sırala
    private void sortArrayList() {
        Collections.sort(list, new Comparator<Items>() {
            @Override
            public int compare(Items ıtems, Items t1) {
                return Float.valueOf(ıtems.getMarket_cap_usd()).compareTo(Float.valueOf(t1.getMarket_cap_usd()));
            }
        });
        customAdapter.notifyDataSetChanged();
    }

    private void sortArrayListY() {
        Collections.reverse(list);
        customAdapter.notifyDataSetChanged();
    }



    private void initt(){

        spinner=(Spinner)findViewById(R.id.spinner);
     //   buttonSort=(Button)findViewById(R.id.sort);
        listview = (ListView)findViewById(R.id.listview);
        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swwp);
        toplamPara=(TextView)findViewById(R.id.toplampara);

    }
}
