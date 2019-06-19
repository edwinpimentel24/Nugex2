package com.infy.service;

import com.infy.api.CurrencyAPI;
import com.infy.api.CurrencyCache;
import java.util.Map;

public class CurrencyService {

    public Map<String,Float> getCurrencyRates(){
        Map<String,Float> rates;
        CurrencyAPI api = new CurrencyAPI();
        CurrencyCache cache = new CurrencyCache();

        if(cache.isUpdated()){ //return saved rates
            return cache.getCachedCurrencies();
        }

        try {
            rates =  api.getRates();
            cache.saveCurrency(rates);
            return rates;

        } catch (Exception e) {
            System.out.println("No internet Connection");
            return cache.getCachedCurrencies();
        }

    }
}
