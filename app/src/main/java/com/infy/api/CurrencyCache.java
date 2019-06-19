package com.infy.api;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.wisdomsasus.nuggetnumbers.Main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.example.wisdomsasus.nuggetnumbers.Main.CURRENCIES;

public class CurrencyCache {

    public static final String MyPREFERENCES = "MyPrefs" ;
    String dateKey = "updateDate";
    SharedPreferences sharedPreferences;



    public boolean isUpdated(){

        sharedPreferences = Main.mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String cachedDate = sharedPreferences.getString(dateKey,"0");

        String todayDate= new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        if(todayDate.equals(cachedDate)){
            return true;
        }else{
            return false;
        }

    }

    public void saveCurrency(Map<String,Float> rates){

        sharedPreferences = Main.mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String todayDate= new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        editor.putString(dateKey,todayDate);

        Iterator it = rates.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            editor.putFloat(pair.getKey().toString(), (Float) pair.getValue());
            }

        editor.commit();

    }

    public Map<String,Float> getCachedCurrencies(){

        sharedPreferences = Main.mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        Map<String,Float> rates = new HashMap<>();
        Float rate;

        for(int i = 0; i<CURRENCIES.length; i++) {
            rate = sharedPreferences.getFloat(CURRENCIES[i],0);
            rates.put(CURRENCIES[i],rate);

        }
            return rates;
    }

}
