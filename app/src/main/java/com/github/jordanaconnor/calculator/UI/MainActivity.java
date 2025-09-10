package com.github.jordanaconnor.calculator.UI;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.jordanaconnor.calculator.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView calculateTextView, answerTextView;
    Button clearBtn, parenthesisOpenBtn, parenthesisCloseBtn, modulusBtn, piBtn, divideBtn, subtractionBtn,
            multiplyBtn, additionBtn, rootBtn, squareBtn, equalsBtn, percentageBtn, decimalBtn;
    boolean resetOnNextInput = false;
    Button[] buttonNums = new Button[10];
    StringBuilder exprBuilder = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Button input logic

        calculateTextView = findViewById(R.id.calculateTextView);
        answerTextView = findViewById(R.id.answerTextView);
        clearBtn = findViewById(R.id.clearBtn);
        buttonNums[0] = findViewById(R.id.btnNum0);
        buttonNums[1] = findViewById(R.id.btnNum1);
        buttonNums[2] = findViewById(R.id.btnNum2);
        buttonNums[3] = findViewById(R.id.btnNum3);
        buttonNums[4] = findViewById(R.id.btnNum4);
        buttonNums[5] = findViewById(R.id.btnNum5);
        buttonNums[6] = findViewById(R.id.btnNum6);
        buttonNums[7] = findViewById(R.id.btnNum7);
        buttonNums[8] = findViewById(R.id.btnNum8);
        buttonNums[9] = findViewById(R.id.btnNum9);

        parenthesisOpenBtn = findViewById(R.id.parenthesisOpenBtn);
        parenthesisCloseBtn = findViewById(R.id.parenthesisCloseBtn);
        modulusBtn = findViewById(R.id.modulusBtn);
        piBtn = findViewById(R.id.piBtn);
        divideBtn = findViewById(R.id.divideBtn);
        subtractionBtn = findViewById(R.id.subtractionBtn);
        multiplyBtn = findViewById(R.id.multiplyBtn);
        additionBtn = findViewById(R.id.additionBtn);
        rootBtn = findViewById(R.id.rootBtn);
        squareBtn = findViewById(R.id.squareBtn);
        equalsBtn = findViewById(R.id.equalsBtn);
        percentageBtn = findViewById(R.id.percentageBtn);
        decimalBtn = findViewById(R.id.decimalBtn);

        //Number keys write logic
        for (int i = 0; i < buttonNums.length; i++) {
            final int j = i;
            buttonNums[i].setOnClickListener(v -> {
                handleInput(String.valueOf(j), String.valueOf(j));
            });
        }

        //Clear
        clearBtn.setOnClickListener(v -> {
            calculateTextView.setText("");
            answerTextView.setText("");
            exprBuilder.setLength(0);
            resetOnNextInput = false;
        });

        // ( button
        parenthesisOpenBtn.setOnClickListener(v -> {
            CharSequence current = calculateTextView.getText();
            if (current.length() > 0) {
                char last = current.charAt(current.length() - 1);
                if (Character.isDigit(last) || last == ')' || last == 'π') {
                    handleInput("(", "*(");
                    return;
                }
            }
            handleInput("(", "(");
        });


        parenthesisCloseBtn.setOnClickListener(v -> handleInput(")", ")"));
        modulusBtn.setOnClickListener(v -> handleInput(" % ", " mod "));
        divideBtn.setOnClickListener(v -> handleInput("÷", "/"));
        multiplyBtn.setOnClickListener(v -> handleInput("*", "*"));
        subtractionBtn.setOnClickListener(v -> handleInput("-", "-"));
        additionBtn.setOnClickListener(v -> handleInput("+", "+"));
        percentageBtn.setOnClickListener(v -> handleInput("%", "%"));
        decimalBtn.setOnClickListener(v -> handleInput(".", "."));
        squareBtn.setOnClickListener(v -> handleInput("²", "^2"));


        piBtn.setOnClickListener(v -> {
            CharSequence current = calculateTextView.getText();
            if (current.length() > 0) {
                char last = current.charAt(current.length() - 1);
                if (Character.isDigit(last) || last == ')' || last == 'π') {
                    handleInput("π", "*3.141592653589793");
                    return;
                }
            }
            handleInput("π", "3.141592653589793");
        });


        //root button
        rootBtn.setOnClickListener(v -> {
            CharSequence current = calculateTextView.getText();
            if (current.length() > 0) {
                char last = current.charAt(current.length() - 1);
                if (Character.isDigit(last) || last == ')' || last == 'π') {
                    handleInput("√", "*√ ");
                    return;
                }
            }
            handleInput("√", "√ ");
        });

        //equals/submit
        equalsBtn.setOnClickListener(v -> {
            String expr = exprBuilder.toString();
            try {
                List<String> postfix = ShuntingYardAlgorithm.toPostfix(expr);
                double result = ShuntingYardAlgorithm.evalPostfix(postfix);

                String formatted;
                if (result == Math.floor(result)) {
                    formatted = String.valueOf((long) result);
                } else {
                    formatted = String.format("%.9f", result)
                            .replaceAll("0+$", "")
                            .replaceAll("\\.$", "");
                }

                answerTextView.setText(formatted);
                resetOnNextInput = true;
            } catch (Exception e) {
                answerTextView.setText("Error");
                resetOnNextInput = true;
            }
        });

    }

    private void handleInput(String displayVal, String exprVal) {
        if (resetOnNextInput) {
            calculateTextView.setText("");
            answerTextView.setText("");
            exprBuilder.setLength(0);
            resetOnNextInput = false;
        }
        calculateTextView.append(displayVal);
        exprBuilder.append(exprVal);
    }
}