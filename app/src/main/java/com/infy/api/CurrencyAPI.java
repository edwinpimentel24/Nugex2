package com.infy.api;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.example.wisdomsasus.nuggetnumbers.Main.CURRENCIES;

public class CurrencyAPI {

    private final String USER_AGENT = "Mozilla/5.0";

    public Map<String,Float> getRates() throws Exception{

        Map<String,Float> rates = new HashMap<>();
        String rate;

        for(int i = 0; i<CURRENCIES.length; i++) {

                rate = sendGet(CURRENCIES[i]);
                rates.put(CURRENCIES[i],Float.valueOf(rate));

        }


        return rates;
    }


    public String sendGet(String currency) throws Exception {

        String url2 = "https://www.freeforexapi.com/api/live?pairs=USD"+currency;

        HttpURLConnection httpConn = null;
        InputStreamReader isReader = null;
        BufferedReader bufReader = null;
        StringBuffer readTextBuf = new StringBuffer();


        try {
            // Create a URL object use page url.
            URL url = new URL(url2);

            httpConn = (HttpURLConnection)url.openConnection();
            httpConn.setRequestMethod("GET");


            InputStream inputStream = httpConn.getInputStream();
            isReader = new InputStreamReader(inputStream);
            bufReader = new BufferedReader(isReader);
            String line = bufReader.readLine();

            while(line != null)
            {
                readTextBuf.append(line);
                line = bufReader.readLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

                if (bufReader != null) {
                    bufReader.close();
                }

                if (isReader != null) {
                    isReader.close();
                }

                if (httpConn != null) {
                    httpConn.disconnect();

                }
        }

       // System.out.println(readTextBuf.toString());

        JSONObject jObj = new JSONObject(readTextBuf.toString());
        String rate = jObj.getString("rates");

        jObj = new JSONObject(rate);
        rate = jObj.getString("USD"+currency);

        jObj = new JSONObject(rate);
        rate = jObj.getString("rate");


        //System.out.println(rate);


        return rate;

    }

}

