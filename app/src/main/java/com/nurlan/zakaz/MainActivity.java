package com.nurlan.zakaz;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nurlan.zakaz.android.Dialog;
import com.nurlan.zakaz.android.SQLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {

    EditText serverip;
    EditText port;
    Button saveButton;

    Zakaz zakaz;
    CheckConnection checkConnection;

    String server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        zakaz = (Zakaz)getApplicationContext();

        if(!zakaz.getServer().isEmpty()) {
            Intent intent = new Intent(MainActivity.this, OrderActivity.class);
            startActivity(intent);
        } else {
            setTitle("Zakaz : Сервер не задан.");
        }

        serverip = (EditText)findViewById(R.id.ServerIPText);
        saveButton = (Button)findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(serverip.getText().toString().isEmpty()) {
                    Dialog dialog = new Dialog(MainActivity.this, "Внимание", "Адресс сервера или Порт не может быть пустым \n", 1);
                } else {
                    server = serverip.getText().toString();
                    checkConnection = new CheckConnection();
                    checkConnection.execute();
                }
            }
        });
    }



    class CheckConnection extends AsyncTask<Void, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(Void... params) {
            String query = "select 'OK' as result";
            SQLConnection httpRequest = new SQLConnection(server);
            return httpRequest.Result(query);
        }

        @Override
        protected void onPostExecute(JSONArray json) {
            try {
                JSONObject jo = json.getJSONObject(0);
                if(jo.getString("result").equals("OK")){
                    zakaz.setServer(serverip.getText().toString());

                    Intent intent = new Intent(MainActivity.this, AgentActivity.class);
                    startActivity(intent);

                } else {
                    Dialog dialog = new Dialog(MainActivity.this, "Внимание", "Адресс сервера не правильный \n" + server, 1);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
