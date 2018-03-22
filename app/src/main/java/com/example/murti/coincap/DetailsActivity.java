package com.example.murti.coincap;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import utils.Constants;

public class DetailsActivity extends AppCompatActivity {

    // private ImageView img;
    TextView percent_1h,percent_24h,percent_7d,txtbaslik;
    TextView txtmarket_cap,price;
    String photo_name;
    WebView vwgrafik;
    private int rankDeger=0;
    private int girKontrol;
    //virgülden sonra  kaç basamak gösterecegini belirliyoruz
    DecimalFormat df=new DecimalFormat("#.###");
    NumberFormat numberFormat=NumberFormat.getInstance();

    private static final String TAG = "MainActivity";
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_details2);
        percent_1h=(TextView)findViewById(R.id.txxpercent1h);
        percent_24h=(TextView)findViewById(R.id.txxpercent24h);
        percent_7d=(TextView)findViewById(R.id.txxpercent7d);
        txtbaslik=(TextView)findViewById(R.id.txtbaslik);
        txtmarket_cap=(TextView)findViewById(R.id.txtmarket_cap);
        price=(TextView) findViewById(R.id.txtPrice_usd);
        vwgrafik = (WebView) findViewById(R.id.wv_grafik);
        numberFormat.setParseIntegerOnly(false);
        numberFormat=NumberFormat.getNumberInstance(Locale.US);

        Bundle bundle = getIntent().getExtras();

        photo_name = bundle.getString("image_name");
        String name=bundle.getString("name");
        String symbol=bundle.getString("symbol");
        String percen24h=bundle.getString("percen24h");
        String market_cap_usdx=bundle.getString("market_cap_usd");
        String volume_usd24x =bundle.getString("volume_usd24");
        String  priceUsdx=bundle.getString("priceUsd");
        String rankd=bundle.getString("rank");
        String paraSembolu=bundle.getString("paraSembolu");
        girKontrol=bundle.getInt("girKontrol");
        String volume_usd24=numberFormat.format(Float.valueOf(String.valueOf(volume_usd24x)));
        String priceUsd=numberFormat.format(Float.valueOf(String.valueOf(priceUsdx)));
        String market_cap_usd=numberFormat.format(Float.valueOf(String.valueOf(market_cap_usdx)));
        rankDeger=Integer.valueOf(rankd);

        //String volume_usd24=df.format(Float.valueOf(String.valueOf(volume_usd24x)));
        //String  priceUsd=df.format(Float.valueOf(String.valueOf(priceUsdx)));
       // String market_cap_usd=df.format(Float.valueOf(String.valueOf(market_cap_usdx)));
        percent_1h.setText(""+rankd);
        txtbaslik.setText(name+"("+symbol+")");
            price.setText(paraSembolu+" "+priceUsd);
            percent_24h.setText(paraSembolu+" "+market_cap_usd);

        percent_7d.setText(""+volume_usd24);

        txtmarket_cap.setText(percen24h+"%");
        if(Float.valueOf(percen24h)<0){
            txtmarket_cap.setTextColor(Color.parseColor("#ba0d13"));
        }else{
            txtmarket_cap.setTextColor(Color.parseColor("#07992e"));
        }

        //img = (ImageView)findViewById(R.id.img_detail);

        //  Picasso.with(getApplicationContext()).load(Constants.URL_IMAGES+photo_name+".png:resizebox?120x120").into(image);
        vwgrafik.getSettings().setBuiltInZoomControls(false);
        vwgrafik.getSettings().setSupportZoom(false);
        vwgrafik.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        vwgrafik.getSettings().setAllowFileAccess(true);
        vwgrafik.getSettings().setDomStorageEnabled(true);
        vwgrafik.getSettings().setJavaScriptEnabled(true);
        vwgrafik.setBackgroundColor(Color.TRANSPARENT);
        vwgrafik.loadUrl(Constants.URL_GRAFIK+photo_name);

        mAdView = (AdView) findViewById(R.id.adViewD);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    //delete menu
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.coinDelete){
            if(girKontrol!=0){
            showInputDialog();
            }
        }
        return false;
    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Coin Delete");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (rankDeger>0){
                    try {
                        MainActivity.sqLiteHelper.deleteData(rankDeger);
                        Toast.makeText(getApplicationContext(), "Delete successfully!!!",Toast.LENGTH_SHORT).show();
                    } catch (Exception e){
                        Log.e("error", e.getMessage());
                    }
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("EXIT", true);
                    startActivity(intent);
                    ;
                }


            }
        });
        builder.show();
    }


}
