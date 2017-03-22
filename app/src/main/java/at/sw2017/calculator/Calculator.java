package at.sw2017.calculator;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Calculator extends Activity implements View.OnClickListener{

    TextView numberView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        Button buttonPlus = (Button) findViewById(R.id.buttonPlus);
        Button buttonMinus = (Button) findViewById(R.id.buttonMinus);
        Button buttonDivision = (Button) findViewById(R.id.buttonDivision);
        Button buttonMultiply = (Button) findViewById(R.id.buttonMultiply);
        Button buttonEquals = (Button) findViewById(R.id.buttonEquals);
        Button buttonClear = (Button) findViewById(R.id.buttonClear);
        numberView = (TextView) findViewById(R.id.textView);
        setUpNumberButtonListener();

        buttonPlus.setOnClickListener(this);
        buttonMinus.setOnClickListener(this);
        buttonDivision.setOnClickListener(this);
        buttonMultiply.setOnClickListener(this);
        buttonEquals.setOnClickListener(this);
        buttonClear.setOnClickListener(this);


    }

    public enum State {
        ADD, SUB, MUL, DIV, INIT, NUM
    }

    Integer firstNumber = 0;
    State state = State.INIT;

    private void clearTextView() {
        numberView.setText("0");
        firstNumber = 0;
        state = State.INIT;
    }

    private void clearNumberView() {
        String tempString = numberView.getText().toString();
        if(!tempString.equals("")){
            firstNumber = Integer.valueOf(tempString);
        }
        numberView.setText("");
    }


    public void setUpNumberButtonListener() {
        ArrayList<Button> numberButtons = new ArrayList<Button>();

        for(int i = 0; i <= 9; i++ ) {
            String buttonName​ = "button" + i;

            int id = getResources().getIdentifier(buttonName​, "id", R.class.getPackage().getName());

            Button button = (Button) findViewById(id);
            button.setOnClickListener(this);

            numberButtons.add(button);
        }
    }

    private void calculateResult() {
        int secondNumber = 0;
        String tempString = numberView.getText().toString();
        if(!tempString.equals("")){
            secondNumber = Integer.valueOf(tempString);
        }
        int result;
        switch(state){
            case ADD:
                result = Calculations.doAddition(firstNumber, secondNumber);
                break;
            case SUB:
                result = Calculations.doSubtraction(firstNumber, secondNumber);
                break;
            case MUL:
                result = Calculations.doMultiplication(firstNumber, secondNumber);
                break;
            case DIV:
                result = Calculations.doDivision(firstNumber, secondNumber);
                break;
            default:
                result = secondNumber;
        }
        numberView.setText(Integer.toString(result));
    }


    @Override
    public void onClick(View v) {
        Button clickedButton = (Button) v;
        switch (clickedButton.getId()) {
            case R.id.buttonPlus:
                clearNumberView();
                state = State.ADD;
                break;
            case R.id.buttonMinus:
                clearNumberView();
                state = State.SUB;
                break;
            case R.id.buttonMultiply:
                clearNumberView();
                state = State.MUL;
                break;
            case R.id.buttonDivision:
                clearNumberView();
                state = State.DIV;
                break;
            case R.id.buttonEquals:
                calculateResult();
                state = State.INIT;
                break;
            case R.id.buttonClear:
                clearTextView();
                break;
            default:
                String recentNumber = numberView.getText().toString();
                if (state == State.INIT) {
                    recentNumber = "";
                    state = State.NUM;
                }
                recentNumber += clickedButton.getText().toString();
                numberView.setText(recentNumber);
        }
    }
}
