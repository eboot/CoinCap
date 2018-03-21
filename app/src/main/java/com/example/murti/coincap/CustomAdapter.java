package com.example.murti.coincap;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import utils.Constants;

/**
 * Created by alperbeyler on 17/11/15.
 */
public class CustomAdapter extends ArrayAdapter<Items> {

    private Context context;
    private ViewHolder viewHolder;
    private List<Items> arrayList = new ArrayList<Items>();
    private int deger;
    private double toplam;
    //virgülden sonra  kaç basamak gösterecegini belirliyoruz
    DecimalFormat df=new DecimalFormat("#.###");
    NumberFormat numberFormat=NumberFormat.getInstance();
    public CustomAdapter(Context context, List<Items> list_items,int degerli) {

        super(context, R.layout.item_layout, list_items);
        this.context = context;
        this.arrayList = list_items;
        deger=degerli;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if(view == null){

            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.item_layout, parent,false);

            viewHolder = new ViewHolder();
            viewHolder.txt_name = (TextView)view.findViewById(R.id.name);
            viewHolder.image = (ImageView)view.findViewById(R.id.img);
            viewHolder.symbol = (TextView)view.findViewById(R.id.symbol);
            viewHolder.price = (TextView)view.findViewById(R.id.price);
            viewHolder.percent24h=(TextView)view.findViewById(R.id.h24);
            viewHolder.change24=(TextView)view.findViewById(R.id.h1);
            viewHolder.percent7d=(TextView)view.findViewById(R.id.d7);
            viewHolder.market=(TextView)view.findViewById(R.id.market);


            view.setTag(viewHolder);


        }else{

            viewHolder = (ViewHolder) view.getTag();

        }
        numberFormat.setParseIntegerOnly(false);
        numberFormat=NumberFormat.getNumberInstance(Locale.US);
        double coinDegeri=Float.valueOf(String.valueOf(arrayList.get(position).getPrice_usd()));
        double coinim=coinDegeri*arrayList.get(position).getToplam();
        String pricedx=numberFormat.format(Float.valueOf(String.valueOf(coinim)));

        String marketdx=df.format(arrayList.get(position).getToplam());
        //String pricedx=String.valueOf(arrayList.get(position).getPrice_usd());
        String paraSembolu=arrayList.get(position).getParaSembol();
           viewHolder.market.setText("" + marketdx);//paramız
            viewHolder.price.setText(paraSembolu+" "+ pricedx);

        // name
        viewHolder.txt_name.setText("" + arrayList.get(position).getName());
        viewHolder.symbol.setText( "(" + arrayList.get(position).getSymbol()+")");

        //yuzdelik degisim degeri
        viewHolder.change24.setText( arrayList.get(position).getPercent_change_1h()+"%");
        // degere göre renk atama
        if(Float.valueOf(String.valueOf(arrayList.get(position).getPercent_change_1h()))<0){
            viewHolder.change24.setTextColor(Color.parseColor("#ba0d13"));
        }else{
            viewHolder.change24.setTextColor(Color.parseColor("#07992e"));
        }
        viewHolder.percent7d.setText(arrayList.get(position).getPercent_change_7d()+"%");//1h degeri
        if(Float.valueOf(String.valueOf(arrayList.get(position).getPercent_change_7d()))<0){
            viewHolder.percent7d.setTextColor(Color.parseColor("#ba0d13"));
        }else{
            viewHolder.percent7d.setTextColor(Color.parseColor("#07992e"));
        }
        viewHolder.percent24h.setText(String.valueOf(arrayList.get(position).getPercent_change_24h()).toString()+"%");
        if(Float.valueOf(String.valueOf(arrayList.get(position).getPercent_change_24h()))<0){
            viewHolder.percent24h.setTextColor(Color.parseColor("#ba0d13"));
        }else{
            viewHolder.percent24h.setTextColor(Color.parseColor("#07992e"));
        }


        //resim olayı burası
        Picasso.with(getContext()).load(Constants.URL_IMAGES+arrayList.get(position).getId()+".png:resizebox?40x40").placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher).into(viewHolder.image, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });

        //  Toast.makeText(getContext(),""+arrayList.get(position).getDeger(),Toast.LENGTH_SHORT).show();
        return view;
    }

    private static class ViewHolder{

        TextView txt_name;
        ImageView image;
        TextView symbol;
        TextView price;
        TextView percent24h;
        TextView market;
        TextView change24;
        TextView percent7d;

    }

}