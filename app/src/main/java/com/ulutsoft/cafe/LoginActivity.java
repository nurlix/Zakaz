package com.ulutsoft.cafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ulutsoft.cafe.android.Dialog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends Activity implements View.OnClickListener {

    Cafe cafe;

    private Button btn0;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btnOk;
    private Button btnCancel;

    private TextView passwordText;
    private TextView activeuser;

    String apass;
    String epass;
    String uname;
    Integer userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cafe = (Cafe)getApplicationContext();

        btn0 = (Button)findViewById(R.id.btn0);
        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn3);
        btn4 = (Button)findViewById(R.id.btn4);
        btn5 = (Button)findViewById(R.id.btn5);
        btn6 = (Button)findViewById(R.id.btn6);
        btn7 = (Button)findViewById(R.id.btn7);
        btn8 = (Button)findViewById(R.id.btn8);
        btn9 = (Button)findViewById(R.id.btn9);
        btnOk = (Button)findViewById(R.id.btnOk);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        passwordText = (TextView) findViewById(R.id.passwordText);
        activeuser = (TextView)findViewById(R.id.active_user);

        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        Intent intent = getIntent();
        uname = intent.getStringExtra("uname");
        apass = intent.getStringExtra("upass");
        userid = intent.getIntExtra("userid", 0);

        activeuser.setText(uname);
    }

    @Override
    public void onBackPressed() {

    }

    private void login(){

        if(authorized()){
            cafe.setUsername(uname);
            cafe.setUserid(userid);
            cafe.setUserpass(apass);

            Intent intent = new Intent(this, OrderActivity.class);
            startActivity(intent);
        } else {
            Dialog dialog = new Dialog(this, "Error", "Wrong password", 1);
            passwordText.setText("");
        }
    }

    public Boolean authorized(){
        MessageDigest digest = null;
        epass  = passwordText.getText().toString();
        try {
            digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(epass.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < hash.length; ++i) {
                sb.append(Integer.toHexString((hash[i] & 0xFF) | 0x100).substring(1,3));
            }
            epass = sb.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }
        if(apass.equals(epass)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn0 : passwordText.setText(passwordText.getText() + "0"); break;
            case R.id.btn1 : passwordText.setText(passwordText.getText() + "1"); break;
            case R.id.btn2 : passwordText.setText(passwordText.getText() + "2"); break;
            case R.id.btn3 : passwordText.setText(passwordText.getText() + "3"); break;
            case R.id.btn4 : passwordText.setText(passwordText.getText() + "4"); break;
            case R.id.btn5 : passwordText.setText(passwordText.getText() + "5"); break;
            case R.id.btn6 : passwordText.setText(passwordText.getText() + "6"); break;
            case R.id.btn7 : passwordText.setText(passwordText.getText() + "7"); break;
            case R.id.btn8 : passwordText.setText(passwordText.getText() + "8"); break;
            case R.id.btn9:  passwordText.setText(passwordText.getText() + "9"); break;
            case R.id.btnOk : login(); break;
            case R.id.btnCancel : if(passwordText.getText() != "") {
                passwordText.setText("");
            } else {
                this.finish();
            }
                break;
        }
    }
}
