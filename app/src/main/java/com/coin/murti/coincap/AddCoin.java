package com.coin.murti.coincap;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class AddCoin extends AppCompatActivity {

    private TextView txtname,txtSymbol,txtPrice,txtTotal;
    private Button btnAdd;
    private EditText edTotal;
    private  double toplamprice=0.0;

    private String id,name,symbol,priceUsdx;
    private int rank;
    private double degerim;
    private boolean kontrol=true;
   private double tahminiDeger=1;
   private  double asd;
    NumberFormat numberFormat=NumberFormat.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add);
        initVer();
        SimpleDateFormat bicim=new SimpleDateFormat("dd/M/yyyy");
        Date tarih=new Date();




        numberFormat.setParseIntegerOnly(false);
        numberFormat=NumberFormat.getNumberInstance(Locale.US);

        Bundle bundle = getIntent().getExtras();
        id=bundle.getString("id").trim();
        name=bundle.getString("name");
        symbol=bundle.getString("symbol");
        priceUsdx=bundle.getString("priceUsd");
        name=bundle.getString("name");
        String ranks=bundle.getString("rank");
        String paraSembol=bundle.getString("paraSembol");
        rank= Integer.valueOf(ranks);

        String pricedx=numberFormat.format(Float.valueOf(String.valueOf(priceUsdx)));


        txtname.setText(name);
        txtSymbol.setText("("+symbol+")");
        txtPrice.setText(paraSembol+"  "+pricedx);
        txtTotal.setText(""+bicim.format(tarih));


        //ekle

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edTotal.length()>0){
                    degerim=Float.parseFloat(edTotal.getText().toString());
                }else{
                    degerim=0.0;
                }

                Cursor cursor = MainActivity.sqLiteHelper.getData("SELECT * FROM FOOD");
                while (cursor.moveToNext()) {

                    String nameid = cursor.getString(0);
                   double  pricedegeri = cursor.getDouble(1);

                    int rankdegeri = cursor.getInt(2);
                    //bu coin daha once eklenmisse eklemiyor
                    if(rank==rankdegeri){
                        toplamprice=pricedegeri+degerim;
                        try {
                            MainActivity.sqLiteHelper.updateData(
                                    id.toString().trim(),
                                    toplamprice,
                                    rank

                            );
                        }
                        catch (Exception error) {
                            Log.e("Update error", error.getMessage());
                        }
                        kontrol=false;
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("EXIT", true);
                        startActivity(intent);
                    }
                }
                if(kontrol==true){
                    try{
                        MainActivity.sqLiteHelper.insertData(
                                id.toString().trim(),
                                degerim,
                                rank
                        );
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("EXIT", true);
                        startActivity(intent);
                    }
                    catch (Exception e){
                        Log.e("error", e.getMessage());
                    }

                }

            }
        });



    }

    private void initVer() {
        txtname=(TextView)findViewById(R.id.txisimm);
        txtSymbol=(TextView)findViewById(R.id.txtsembol);
        txtPrice=(TextView)findViewById(R.id.txtpricefav);
        txtTotal=(TextView)findViewById(R.id.txttotaldeger);
        edTotal=(EditText)findViewById(R.id.edtotal);
        btnAdd=(Button)findViewById(R.id.btnfavadd);

    }

}
