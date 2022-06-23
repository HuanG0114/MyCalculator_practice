package com.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class MainActivity extends AppCompatActivity
{
    TextView workingsTV,resultsTV;

    String workings = "";
    String formula = "";
    String temFormula = "" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTextView();
        
    }

    private void initTextView() {
        workingsTV = (TextView) findViewById(R.id.workingsTextView);
        resultsTV = (TextView) findViewById(R.id.resultTextView);
    }
    private void setWorkings(String givenValue){
        workings = workings + givenValue;
        workingsTV.setText(workings);
    }


    public void clearOnclick(View view) {
        workingsTV.setText("");
        workings = "";
        resultsTV.setText("");
        lefBracket = true;
    }
    boolean lefBracket = true;

    public void bracketsOnClick(View view) {
        if(lefBracket)
        {
            setWorkings("(");
            lefBracket = false;
        }
        else {
            setWorkings(")");
            lefBracket = true;
        }
    }

    public void powerOfOnclick(View view) {
        setWorkings("^");
    }

    public void divisionOnclick(View view) {
        setWorkings("/");

    }

    public void sevenOnClick(View view) {
        setWorkings("7");
    }

    public void eightOnclick(View view) {
        setWorkings("8");
    }

    public void nineOnclick(View view) {
        setWorkings("9");
    }

    public void timeOnclick(View view) {
        setWorkings("X");
    }

    public void fourOnclick(View view) {
        setWorkings("4");
    }

    public void fiveOnclick(View view) {
        setWorkings("5");
    }

    public void sixOnclick(View view) {
        setWorkings("6");
    }

    public void minusOnclick(View view) {
        setWorkings("-");
    }

    public void oneOnclick(View view) {
        setWorkings("1");
    }

    public void twoOnclick(View view) {
        setWorkings("2");
    }

    public void threeOnclick(View view) {
        setWorkings("3");
    }

    public void plusOnclick(View view) {
        setWorkings("+");
    }

    public void decimalOnclick(View view) {
        setWorkings(".");
    }

    public void zeroOnclick(View view) {
        setWorkings("0");
    }

    public void equalOnclick(View view) {
        Double result = null ;
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
        checkForPowerOf();

        try {
            result = (double)engine.eval(formula);
        } catch (ScriptException e) {
            Toast.makeText(this,"Tnvalid Input",Toast.LENGTH_SHORT).show();
        }
        if(result != null)
            resultsTV.setText(String.valueOf(result.doubleValue()));
    }

    private void checkForPowerOf() {
        ArrayList<Integer> indexOfPowers = new ArrayList<>();
        for(int i = 0;i < workings.length();i++){

            if (workings.charAt(i)=='^')
                indexOfPowers.add(i);
        }
        formula = workings;
        temFormula = workings;
        for (Integer index: indexOfPowers)
        {
            changeFormula(index);
        }
        formula = temFormula;
    }

    private void changeFormula(Integer index) {
        String numberLeft="";
        String numberRight="";

        for (int i = index +1; i< workings.length(); i++)
        {
            if(isNumeric(workings.charAt(i)))
                numberRight = numberRight + workings.charAt(i);
            else
                break;
        }
        for (int i = index -1; i>= 0; i--)
        {
            if(isNumeric(workings.charAt(i)))
                numberLeft = numberLeft + workings.charAt(i);
            else
                break;
        }
        String original = numberLeft + "^" + numberRight;
        String changed = "Math.pow("+numberLeft+","+numberRight+")";
        temFormula = temFormula.replace(original,changed);
    }
    private boolean isNumeric(char c){
        if((c <='9'&& c >= '0' )|| c == '.')
            return true;
        return false;
    }
}