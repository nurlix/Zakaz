package com.ulutsoft.cafe;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ulutsoft.cafe.Adapters.UserAdapter;
import com.ulutsoft.cafe.Objects.User;
import com.ulutsoft.cafe.android.HTTPRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UsersActivity extends Activity {

    LoadUsers loadUsers;

    ListView userlist;
    ArrayList<User> users = new ArrayList<>();
    UserAdapter userAdapter;

    Cafe cafe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        cafe = (Cafe)getApplicationContext();

        userlist = (ListView) findViewById(R.id.userlist);
        userlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String uname = userAdapter.getItem(position).getUsername();
                String upass = userAdapter.getItem(position).getPassword();
                Integer userid = userAdapter.getItem(position).getID();

                Intent intent = new Intent(UsersActivity.this, LoginActivity.class);
                intent.putExtra("uname", uname);
                intent.putExtra("userid", userid);
                intent.putExtra("upass", upass);
                startActivity(intent);
            }
        });

        String query = getString(R.string.qry_loadUsers);
        loadUsers = new LoadUsers();
        loadUsers.execute(cafe.getServer(), query);
    }

    @Override
    public void onBackPressed() {
    }

    class LoadUsers extends AsyncTask<String, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(String... params) {
            HTTPRequest httpRequest = new HTTPRequest(UsersActivity.this, params[0], params[1]);
            return httpRequest.getJson();
        }

        @Override
        protected void onPostExecute(JSONArray json) {
            if(json != null) {
                for (int i = 0; i < json.length(); i++) {
                    try {
                        JSONObject jo = json.getJSONObject(i);
                        User user = new User(
                                jo.getInt("id"),
                                jo.getString("name"),
                                jo.getString("password")
                        );
                        users.add(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                userAdapter = new UserAdapter(UsersActivity.this, users);
                userlist.setAdapter(userAdapter);
            }
        }
    }
}
