package com.coin.murti.coincap;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapterList  extends ArrayAdapter<Items> {

    private Context context;
    private ViewHolderList viewHolder;
    private List<Items> arrayList = new ArrayList<Items>();
    private int deger;
    //virgülden sonra  kaç basamak gösterecegini belirliyoruz
    DecimalFormat df=new DecimalFormat("#.###");
    public CustomAdapterList(Context context, List<Items> list_items,int degerli) {

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
            view = layoutInflater.inflate(R.layout.itam_list_fav, parent,false);

            viewHolder = new ViewHolderList();
            viewHolder.txt_name = (TextView)view.findViewById(R.id.namefav);


            view.setTag(viewHolder);


        }else{

            viewHolder = (ViewHolderList) view.getTag();

        }

       String symboll=" : "+arrayList.get(position).getSymbol();
        viewHolder.txt_name.setText( "  "+ arrayList.get(position).getName()+symboll);


        return view;
    }

    private static class ViewHolderList{

        TextView txt_name;

    }

}