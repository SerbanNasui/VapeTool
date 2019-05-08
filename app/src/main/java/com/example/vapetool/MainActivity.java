package com.example.vapetool;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText volts, amps, ohms, watts;
    private List<EditText> editTexts, editTextsNic, editTextsFlav;
    private Dialog dialog;
    private EditText liquid, nicotineStrength, finalStrenghtNicotine, addingNicotine, totalLiquid;
    private EditText flavourOwn, flavourNeed, totalMadeLiq, baseAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeEditTexts();
        initializeButtons();
        //dialog = new Dialog(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initializeEditTexts() {
        editTexts = new ArrayList<>();

        volts =  findViewById(R.id.voltage_value);
        amps =  findViewById(R.id.current_value);
        ohms =  findViewById(R.id.resistence_value);
        watts =  findViewById(R.id.wattage_value);

        editTexts.add(volts);
        editTexts.add(amps);
        editTexts.add(ohms);
        editTexts.add(watts);

        editTextsNic = new ArrayList<>();

        liquid = findViewById(R.id.liquid_value);
        nicotineStrength = findViewById(R.id.nicotine_value);
        finalStrenghtNicotine = findViewById(R.id.nicotine_wanted_value);
        addingNicotine = findViewById(R.id.nicotine_must_add_value);
        totalLiquid = findViewById(R.id.total_liquid_value);

        editTextsNic.add(liquid);
        editTextsNic.add(nicotineStrength);
        editTextsNic.add(finalStrenghtNicotine);
        editTextsNic.add(addingNicotine);
        editTextsNic.add(totalLiquid);

        editTextsFlav = new ArrayList<>();

        flavourOwn = findViewById(R.id.flavor_own_value);
        flavourNeed = findViewById(R.id.flavor_need_value);
        totalMadeLiq = findViewById(R.id.liquid_total_flavor_value);
        baseAdd = findViewById(R.id.total_base_value);

        editTextsFlav.add(flavourOwn);
        editTextsFlav.add(flavourNeed);
        editTextsFlav.add(totalMadeLiq);
        editTextsFlav.add(baseAdd);





    }

    private void initializeButtons() {
        Button calculate = findViewById(R.id.calculate);
        Button reset =  findViewById(R.id.clear);

        Button calculateNicotine = findViewById(R.id.calculate_nicotine);

        Button calculateFlavor = findViewById(R.id.calculate_flavor);

        calculateFlavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidEntries()) {
                    calculateFlavor();
                }
            }
        });

        calculateNicotine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidEntries()) {
                    calculateNicotine();
                }
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidEntries()) {
                    calculate();
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (EditText text : editTexts) {
                    text.setText("0");
                }
            }
        });
    }

    private boolean isValidEntries() {
        int enteredCount = 0;
        for (EditText text : editTexts) {

            if (!text.getText().toString().equals("") && !text.getText().toString().equals("0")) {
                enteredCount += 1;
            }

        }



        return true;
    }

    private void calculate() {
        double value1 = 0;
        double value2 = 0;
        int index1 = -1;
        int index2 = -1;
        int count = 0;

        for (EditText text : editTexts) {
            if (!text.getText().toString().equals("") && !text.getText().toString().equals("0")) {
                if (value1 == 0) {
                    value1 = Double.parseDouble(text.getText().toString());
                    index1 = count;
                } else {
                    value2 = Double.parseDouble(text.getText().toString());
                    index2 = count;
                }
            }
            count++;
        }

        if (index1 == 0 && index2 == 1) {
            double ohmsValue = value1 / value2;
            double wattsValue = value1 * value2;
            ohms.setText(String.valueOf(ohmsValue));
            watts.setText(String.valueOf(wattsValue));
        } else if (index1 == 0 && index2 == 2) {
            double ampsValue = value1 / value2;
            double wattsValue = (Math.pow(value1, 2)) / value2;
            amps.setText(String.valueOf(ampsValue));
            watts.setText(String.valueOf(wattsValue));
        } else if (index1 == 0 && index2 == 3) {
            double ampsValue = value2 / value1;
            double ohmsValue = (Math.pow(value1, 2)) / value2;
            amps.setText(String.valueOf(ampsValue));
            ohms.setText(String.valueOf(ohmsValue));
        } else if (index1 == 1 && index2 == 2) {
            double voltsValue = value1 * value2;
            double wattsValue = (Math.pow(value1, 2)) * value2;
            volts.setText(String.valueOf(voltsValue));
            watts.setText(String.valueOf(wattsValue));
        } else if (index1 == 1 && index2 == 3) {
            double voltsValue = value2 / value1;
            double ohmsValue =  value2 / (Math.pow(value1, 2));
            volts.setText(String.valueOf(voltsValue));
            ohms.setText(String.valueOf(ohmsValue));
        } else if (index1 == 2 && index2 == 3) {
            double voltsValue = Math.sqrt(value1 * value2);
            double ampsValue = Math.sqrt(value2 / value1);
            volts.setText(String.valueOf(voltsValue));
            amps.setText(String.valueOf(ampsValue));
        }
    }

    private void calculateNicotine(){
        double liq =0;
        double nicS = 0;
        double nicWant = 0;
        liq = Double.parseDouble(liquid.getText().toString());
        nicS = Double.parseDouble(nicotineStrength.getText().toString());
        nicWant = Double.parseDouble(finalStrenghtNicotine.getText().toString());
        double nicAdd = (((nicWant*liq)/(nicWant-nicS))*-1);
        double totLiq = liq+nicAdd;
        addingNicotine.setText(String.valueOf(nicAdd));
        totalLiquid.setText(String.valueOf(totLiq));
    }

    private void calculateFlavor(){
        double flOwn = 0;
        double flNeed = 0;
        flOwn = Double.parseDouble(flavourOwn.getText().toString());
        flNeed = Double.parseDouble(flavourNeed.getText().toString());
        double totLoq = (100*flOwn)/flNeed;
        double baseAdded = totLoq - flOwn;
        totalMadeLiq.setText(String.valueOf(totLoq));
        baseAdd.setText(String.valueOf(baseAdded));
    }
}
