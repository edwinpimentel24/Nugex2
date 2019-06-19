package com.infy.io;

import com.example.wisdomsasus.nuggetnumbers.Main;
import com.example.wisdomsasus.nuggetnumbers.R;
import com.infy.model.ComboCost;
import com.infy.model.Nugget;
import com.infy.model.State;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class DataReader {

    private final int STATE = 0;
    private final int BRAND = 1;
    private final int UNIT = 2;
    private final int PRICE = 3;
    private final String SEPARATOR=",";


    public ArrayList<State> getNuggetData(){

        InputStream is = Main.mContext.getResources().openRawResource(R.raw.nugget_price);
        BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line;
        ArrayList<State> stateList = new ArrayList<>();
        Nugget nugget = new Nugget("");
        ComboCost comboCost;
        String tempState="New";
        String tempBrand = "New";
        State state = new State("");

        try {
            // If buffer is not empty
            br.readLine();
            while ((line = br.readLine()) != null) {


                String[] nuggetEntry = line.split(SEPARATOR);

                if(!tempState.equals(nuggetEntry[STATE])) {

                    state = new State(nuggetEntry[STATE]);
                    stateList.add(state);
                    tempState = nuggetEntry[STATE];
                    tempBrand = nuggetEntry[BRAND];
                    nugget = new Nugget(nuggetEntry[BRAND]);
                    state.getNuggets().add(nugget);

                }

                else if(!tempBrand.equals(nuggetEntry[BRAND])) {

                    nugget = new Nugget(nuggetEntry[BRAND]);
                    state.getNuggets().add(nugget);
                    tempBrand = nuggetEntry[BRAND];

                }

                comboCost = new ComboCost();
                comboCost.setAmount(Integer.parseInt(nuggetEntry[UNIT]));
                comboCost.setPrice(Float.parseFloat(nuggetEntry[PRICE]));
                nugget.getCost().add(comboCost);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stateList;
    }

}
