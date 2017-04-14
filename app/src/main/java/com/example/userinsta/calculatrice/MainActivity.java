package com.example.userinsta.calculatrice;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonPlus;
    Button buttonMoins;
    Button buttonMult;
    Button buttonDiv;
    Button buttonC;
    Button buttonPoint;
    Button buttonEgale;
    Button [] buttonsNumbers= new Button[10];
    TextView ecran;

    CalculateModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonPlus = (Button) findViewById(R.id.buttonPLus);
        buttonPlus.setOnClickListener(this);
        buttonMoins= (Button) findViewById(R.id.buttonMoins);
        buttonMoins.setOnClickListener(this);
        buttonDiv  = (Button) findViewById(R.id.buttonDiviser);
        buttonDiv.setOnClickListener(this);
        buttonMult = (Button) findViewById(R.id.buttonMultiplier);
        buttonMult.setOnClickListener(this);
        buttonC    = (Button) findViewById(R.id.buttonClear);
        buttonC.setOnClickListener(this);
        buttonPoint= (Button) findViewById(R.id.buttonPoint);
        buttonPoint.setOnClickListener(this);
        buttonEgale= (Button) findViewById(R.id.buttonEgale);
        buttonEgale.setOnClickListener(this);
        for(int i=0; i<buttonsNumbers.length; i++) {
            buttonsNumbers[i] = (Button) findViewById(
                    getResources()
                            .getIdentifier("button"+i, "id", getPackageName()));
            buttonsNumbers[i].setOnClickListener(this);
        }

        ecran = (TextView) findViewById(R.id.TextViewEcran);
        model = new CalculateModel();
    }

    @Override
    public void onClick(View v) {
        if(v.equals(buttonPlus)){
            model.setOperator(Constant.OPERATOR_PLUS);
        }else if(v.equals(buttonMoins)){
            model.setOperator(Constant.OPERATOR_MOINS);
        }else if(v.equals(buttonDiv)){
            model.setOperator(Constant.OPERATOR_DIV);
        }else if(v.equals(buttonMult)){
            model.setOperator(Constant.OPERATOR_MULT);
        }else if(v.equals(buttonC)){
            model.clear();
            ecran.setText("0");
        }else if(v.equals(buttonEgale)){
            double result = model.calculate();
            ecran.setText(String.valueOf(result));
            model.clear();
            model.setFirstValue(String.valueOf(result));
        }else{
            Button numberButton= (Button) v;
            if(model.getOperator()==""){
                model.addFirstValueNumber(numberButton.getText().toString());
                ecran.setText(String.valueOf(model.getFirstValue()));
            }else{
                model.addSecondValueNumber(numberButton.getText().toString());
                ecran.setText(String.valueOf(model.getSecondValue()));
            }
        }
    }
}
