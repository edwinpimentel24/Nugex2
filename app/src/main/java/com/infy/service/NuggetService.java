package com.infy.service;

import com.infy.model.ComboCost;
import com.infy.model.ResultOutput;
import com.infy.model.State;
import com.infy.model.Nugget;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class NuggetService {


    public static double usdToForeign(float conversionFactor, double amount){
        double result = 0;
        result=conversionFactor*amount;
        return result;
    }

    public static double foreignToUsd(float conversionFactor, double amount){
        double result = 0;
        result=amount / conversionFactor;
        return result;
    }


    public static ResultOutput calculateNuggets2(State state, String requestBrand, double moneyAvailable, float conversionFactor){
        ResultOutput result = new ResultOutput();
        long totalNuggets = 0;
        long tempNuggets;
        Nugget nuggetInfo =  new Nugget();
        ArrayList<ComboCost> comboCost;
        String comboRes="";

        moneyAvailable = foreignToUsd(conversionFactor,moneyAvailable);

        //Get combos of the brand
        for(Nugget nugget:state.getNuggets()){
            if(nugget.getBrand().equals(requestBrand)){
                nuggetInfo = nugget;
                break;
            }
        }
        comboCost = nuggetInfo.getCost();
        for(int i = comboCost.size()-1; i >=0;i--){

            tempNuggets = (int) Math.floor(moneyAvailable / comboCost.get(i).getPrice());
            totalNuggets+=tempNuggets *comboCost.get(i).getAmount();

            if (tempNuggets >0)
                comboRes = comboRes +"\n"+ tempNuggets + " groups of " + comboCost.get(i).getAmount() + " nuggets";

            moneyAvailable = moneyAvailable - (tempNuggets*comboCost.get(i).getPrice());

        }

        moneyAvailable = usdToForeign(conversionFactor,moneyAvailable);

        result.setNuggets(totalNuggets+"");
        result.setChange(String.format("%.2f",moneyAvailable));
        result.setCombo(comboRes);

        return result;
    }

}
