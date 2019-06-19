package com.example.wisdomsasus.nuggetnumbers;

import com.infy.io.DataReader;
import com.infy.model.ResultOutput;
import com.infy.model.State;
import com.infy.service.CurrencyService;
import com.infy.service.NuggetService;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;


public class Main extends AppCompatActivity implements View.OnClickListener {

        public static Context mContext;
        public static final String[] CURRENCIES = {"USD","EUR","GBP","AUD","NZD","CAD","CHF","JPY"};
        public static final String MCDONALDS = "McDonald's";
        public static final String BURGUERKING = "Burger King";
        public static final String CHICKFILA= "Chick-fil-A";


        public static ArrayList<State> statesArray;
        public static Map<String,Float> currencyRates;
        public static float conversionRate;
        public static State state;
        public static String brand = MCDONALDS;
        public static Map<String,Float> currency;
        public static String money="0";
        public static Boolean decimal=false;
        public static final Integer MAX_DIGITS = 12;
        public static int decDigit = 0;

        public NuggetService nuggetService;

        public TextView resultText;
        public TextView currencyText;
        public TextView comboText;
        public TextView changeText;
        public ImageButton mcdonaldButton;
        public ImageButton burguerkingButton;
        public ImageButton chickfilaButton;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.main);

        //Get data
        CurrencyService service = new CurrencyService();
        currencyRates = service.getCurrencyRates();

        DataReader io = new DataReader();
        statesArray =  io.getNuggetData();

        state = statesArray.get(5);


        nuggetService = new NuggetService();


        //Initialize UI elements

        //Buttons
        Button zero = findViewById(R.id.btn_zero);
        zero.setOnClickListener(this);
        Button one = findViewById(R.id.btn_one);
        one.setOnClickListener(this);
        Button two = findViewById(R.id.btn_two);
        two.setOnClickListener(this);
        Button three = findViewById(R.id.btn_three);
        three.setOnClickListener(this);
        Button four = findViewById(R.id.btn_four);
        four.setOnClickListener(this);
        Button five = findViewById(R.id.btn_five);
        five.setOnClickListener(this);
        Button six = findViewById(R.id.btn_six);
        six.setOnClickListener(this);
        Button seven = findViewById(R.id.btn_seven);
        seven.setOnClickListener(this);
        Button eight = findViewById(R.id.btn_eight);
        eight.setOnClickListener(this);
        Button nine = findViewById(R.id.btn_nine);
        nine.setOnClickListener(this);
        Button dot = findViewById(R.id.btn_dot);
        dot.setOnClickListener(this);
        ImageButton clear = findViewById(R.id.btn_clear);
        clear.setOnClickListener(this);
        ImageButton backspace = findViewById(R.id.btn_backspace);
        backspace.setOnClickListener(this);
       // ImageButton exchange = findViewById(R.id.btn_exchange);
       // exchange.setOnClickListener(this);

        //Button currencyButton = findViewById(R.id.btn_currency);
        //currencyButton.setOnClickListener(this);
        //Button locationButton = findViewById(R.id.btn_location);
        //locationButton.setOnClickListener(this);
        Spinner stateSpinner = findViewById(R.id.state_spinner);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this,R.array.state_arrays,R.layout.spinner_text);
        adapter1.setDropDownViewResource(R.layout.state_spinner_dropdown_item);
        stateSpinner.setAdapter(adapter1);


        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                state =  statesArray.get(position);
                ResultOutput res = nuggetService.calculateNuggets2(state,brand,Float.parseFloat(money), conversionRate);
                resultText.setText(res.getNuggets());
                comboText.setText(res.getCombo());
                changeText.setText("Change: "+res.getChange());
                }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {


                }
            }
        );


        Spinner currencySpinner = findViewById(R.id.currency_spinner);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,R.array.currency_arrays,R.layout.cur_spn_text);
        adapter2.setDropDownViewResource(R.layout.state_spinner_dropdown_item);
        currencySpinner.setAdapter(adapter2);

        currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //here

                if(position == 0){
                    conversionRate = 1;

                }else{
                    conversionRate = currencyRates.get(CURRENCIES[position]);
                }

                ResultOutput res = nuggetService.calculateNuggets2(state,brand,Float.parseFloat(money), conversionRate);
                resultText.setText(res.getNuggets());
                comboText.setText(res.getCombo());
                changeText.setText("Change: "+res.getChange());
                }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        }
        );

        mcdonaldButton = findViewById(R.id.mcdonald_button);
        mcdonaldButton.setOnClickListener(this);
        burguerkingButton = findViewById(R.id.burgerking_button);
        burguerkingButton.setOnClickListener(this);
        burguerkingButton.setColorFilter(new ColorMatrixColorFilter(new ColorMatrix()));
        chickfilaButton = findViewById(R.id.chickfila_button);
        chickfilaButton.setOnClickListener(this);

        setBrand();

        //TextViews
        resultText = findViewById(R.id.output_text);
        resultText.setText("0");
        currencyText = findViewById(R.id.currency_text);
        currencyText.setText("0");
        comboText = findViewById(R.id.combo_text);
        comboText.setText("");
        changeText = findViewById(R.id.change_text);
        changeText.setText("Change: 0");


    }

    public void setBrand(){
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            ColorMatrixColorFilter filterBW = new ColorMatrixColorFilter(matrix);
            matrix.setSaturation(1);
            ColorMatrixColorFilter filterColor = new ColorMatrixColorFilter(matrix);

            switch (brand){
                case MCDONALDS:
                    burguerkingButton.setColorFilter(filterBW);
                    chickfilaButton.setColorFilter(filterBW);
                    mcdonaldButton.setColorFilter(filterColor);
                    break;

                case BURGUERKING:
                    burguerkingButton.setColorFilter(filterColor);
                    chickfilaButton.setColorFilter(filterBW);
                    mcdonaldButton.setColorFilter(filterBW);
                    break;

                case CHICKFILA:
                    burguerkingButton.setColorFilter(filterBW);
                    chickfilaButton.setColorFilter(filterColor);
                    mcdonaldButton.setColorFilter(filterBW);
                    break;
            }

    }

    public void putNumber(String num){
        if(money.equals("0")) money = "";
        if (decimal == false || decDigit <2) {
            if (money.length() < MAX_DIGITS) {
                money = money + num;
                currencyText.setText(money);
                if (decimal ==true){
                    decDigit++;
                }
            }
        }
    }
    @Override
    public void onClick(View v) {

            switch (v.getId()){

                case R.id.btn_dot:
                    if (money.length()==0){
                        money = "0";
                    }
                    if (decimal == false && money.length() < MAX_DIGITS){
                        money=money+".";
                        currencyText.setText(money);
                        decimal =true;
                    }
                    break;

                case R.id.btn_zero:
                    if (!money.equals("0")) {
                        putNumber("0");
                    }
                    break;
                case R.id.btn_one:
                    putNumber("1");
                    break;
                case R.id.btn_two:
                    putNumber("2");
                    break;

                case R.id.btn_three:
                    putNumber("3");
                    break;

                case R.id.btn_four:
                    putNumber("4");
                    break;

                case R.id.btn_five:
                    putNumber("5");
                    break;

                case R.id.btn_six:
                    putNumber("6");
                    break;

                case R.id.btn_seven:
                    putNumber("7");
                    break;

                case R.id.btn_eight:
                    putNumber("8");
                    break;

                case R.id.btn_nine:
                    putNumber("9");
                    break;

                case R.id.btn_backspace:
                    if (money != null && money.length()>0){
                        if (money.charAt(money.length() -1) == '.'){
                            decimal=false;
                        }

                        if (decimal==true){
                            decDigit--;
                        }

                        money = money.substring(0, money.length() -1);
                        if(money.length() == 0) money = "0";
                        currencyText.setText(money);
                    }
                    break;

                case R.id.btn_clear:
                    money = "0";
                    decimal = false;
                    decDigit = 0;
                    currencyText.setText(money);
                    break;


                case R.id.mcdonald_button:
                    brand = MCDONALDS;
                    setBrand();
                    break;

                case R.id.burgerking_button:
                    brand = BURGUERKING;
                    setBrand();
                    break;

                case R.id.chickfila_button:
                    brand = CHICKFILA;
                    setBrand();
                    break;
            }

            //Calculations and set number of chicken nuggets

            ResultOutput res = nuggetService.calculateNuggets2(state,brand,Float.parseFloat(money), conversionRate);
            resultText.setText(res.getNuggets());
            comboText.setText(res.getCombo());
            changeText.setText("Change: "+res.getChange());


    }
}
