package com.ulutsoft.cafe;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ulutsoft.cafe.android.Dialog;
import com.ulutsoft.cafe.android.HTTPRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {

    EditText serverip;
    EditText port;
    Button saveButton;

    Cafe cafe;
    CheckConnection checkConnection;

    String server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serverip = (EditText)findViewById(R.id.ServerIPText);
        port = (EditText)findViewById(R.id.PortText);
        saveButton = (Button)findViewById(R.id.SaveButton);

        cafe = (Cafe)getApplicationContext();

        if(!cafe.getServer().isEmpty()) {
            Intent intent = new Intent(this, UsersActivity.class);
            startActivity(intent);

        } else {
            setTitle("Cafe : Сервер не задан.");
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                server = "http://" + serverip.getText() + ":" + port.getText() + "/data/";
                checkConnection = new CheckConnection();
                checkConnection.execute();
            }
        });
    }

    class CheckConnection extends AsyncTask<Void, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(Void... params) {

            String query = "select 'OK' as result";
            HTTPRequest httpRequest = new HTTPRequest(MainActivity.this, server, query);
            return httpRequest.getJson();
        }

        @Override
        protected void onPostExecute(JSONArray json) {
                try {
                    JSONObject jo = json.getJSONObject(0);
                    if(jo.getString("result").equals("OK")){
                        cafe.setServer(serverip.getText().toString());
                        cafe.setPort(port.getText().toString());
                        Intent intent = new Intent(MainActivity.this, UsersActivity.class);
                        startActivity(intent);
                    } else {
                        Dialog dialog = new Dialog(MainActivity.this, "Внимание", "Адресс сервера не правильный \n" + server, 1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    Dialog dialog = new Dialog(MainActivity.this, "Внимание", "Адресс сервера не правильный \n" + server, 1);
                }

        }
    }
}
