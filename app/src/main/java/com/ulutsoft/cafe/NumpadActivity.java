package com.ulutsoft.cafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class NumpadActivity extends Activity implements View.OnClickListener {

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button button0;
    Button buttonOk;
    Button buttonCancel;

    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_numpad);

        Intent intent = getIntent();
        setTitle(intent.getStringExtra("Title"));

        result = (TextView)findViewById(R.id.ResultText);

        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);
        button5 = (Button)findViewById(R.id.button5);
        button6 = (Button)findViewById(R.id.button6);
        button7 = (Button)findViewById(R.id.button7);
        button8 = (Button)findViewById(R.id.button8);
        button9 = (Button)findViewById(R.id.button9);
        button0 = (Button)findViewById(R.id.button0);
        buttonOk = (Button)findViewById(R.id.buttonOk);
        buttonCancel = (Button)findViewById(R.id.buttonCancel);

        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        buttonOk.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button0 : result.setText(result.getText() + "0"); break;
            case R.id.button1 : result.setText(result.getText() + "1"); break;
            case R.id.button2 : result.setText(result.getText() + "2"); break;
            case R.id.button3 : result.setText(result.getText() + "3"); break;
            case R.id.button4 : result.setText(result.getText() + "4"); break;
            case R.id.button5 : result.setText(result.getText() + "5"); break;
            case R.id.button6 : result.setText(result.getText() + "6"); break;
            case R.id.button7 : result.setText(result.getText() + "7"); break;
            case R.id.button8 : result.setText(result.getText() + "8"); break;
            case R.id.button9:  result.setText(result.getText() + "9"); break;
            case R.id.buttonOk :
            {
                Intent data = new Intent();
                data.putExtra("result", Integer.parseInt(result.getText().toString()));
                setResult(RESULT_OK, data);
                finish();
            } break;
            case R.id.buttonCancel : if(result.getText() != "") {
                result.setText("");
            } else {
                Intent data = new Intent();
                setResult(RESULT_CANCELED, data);
                finish();
            }
                break;
        }
    }
}
