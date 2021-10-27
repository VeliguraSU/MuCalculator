package com.example.mucalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    private static final String KEY_MAIN_SCREEN = "mainScreen";
    private static final String KEY_MEMORY_SCREEN = "memoryScreen";
    private static final String KEY_EQUATION = "equation";

    private TextView mainScreen;
    private TextView memoryScreen;
    private CountShow countShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainScreen = findViewById(R.id.main_screen);
        memoryScreen = findViewById(R.id.memory_screen);
        countShow = new CountShow(mainScreen, memoryScreen);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_MAIN_SCREEN, mainScreen.getText().toString());
        outState.putString(KEY_MEMORY_SCREEN, memoryScreen.getText().toString());
        outState.putString(KEY_EQUATION, countShow.getEquation());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mainScreen.setText(savedInstanceState.getString(KEY_MAIN_SCREEN));
        memoryScreen.setText(savedInstanceState.getString(KEY_MEMORY_SCREEN));
        countShow.setEquation(savedInstanceState.getString(KEY_EQUATION));
    }
    public void clear(View view) {
        memoryScreen.setText("");
        mainScreen.setText("");
        countShow.setEquation("");
    }
    public void press(View view) {
        String input = mainScreen.getText().toString();
        Button button = (Button) view;
        switch (view.getId()) {
            case R.id.button_1:
            case R.id.button_2:
            case R.id.button_3:
            case R.id.button_4:
            case R.id.button_5:
            case R.id.button_6:
            case R.id.button_7:
            case R.id.button_8:
            case R.id.button_9:
            case R.id.button_0: {
                String last = !countShow.getEquation().isEmpty() ? countShow.getEquation().substring(countShow.getEquation().length() - 3) : "";
                if (last.contains("=")) {
                    Toast.makeText(this, "Must enter an operator first", Toast.LENGTH_LONG).show();
                    break;
                }
                mainScreen.setText(String.format("%s%s", input, button.getText().toString()));
                break;
            }
            case R.id.button_addition:
            case R.id.button_division:
            case R.id.button_subtraction:
            case R.id.button_multiplication: {
                if (!countShow.getEquation().isEmpty() || !input.isEmpty()) {
                    countShow.addToEquation(button.getText().toString());
                }
                break;
            }
            case R.id.button_equal: {
                if (!countShow.getEquation().isEmpty() || !input.isEmpty()) {
                    countShow.addToEquation(button.getText().toString());
                    mainScreen.setText(countShow.calculate());
                }
            }
        }

    }
}
