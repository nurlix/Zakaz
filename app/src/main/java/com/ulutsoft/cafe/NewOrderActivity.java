package com.ulutsoft.cafe;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ulutsoft.cafe.Adapters.RoomAdapter;
import com.ulutsoft.cafe.Adapters.TableAdapter;
import com.ulutsoft.cafe.Objects.Room;
import com.ulutsoft.cafe.Objects.Table;
import com.ulutsoft.cafe.android.HTTPRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewOrderActivity extends Activity {

    Spinner roomSpinner;
    Spinner tableSpinner;
    EditText edGuests;
    TextView tvGuestCount;

    Button btnOK;
    Button btnCancel;

    LoadRooms loadRooms;
    LoadTables loadTables;

    Cafe cafe;

    String query;

    int selRoom;
    int selTable;
    String roomName;

    Intent intent;

    ArrayList<Room> rooms = new ArrayList<>();
    RoomAdapter roomAdapter;

    ArrayList<Table> tables = new ArrayList<>();
    TableAdapter tableAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        cafe = (Cafe)getApplicationContext();
        edGuests = (EditText)findViewById(R.id.edGuests);
        tvGuestCount = (TextView)findViewById(R.id.tvGuestCount);

        intent = getIntent();

        if (intent.getBooleanExtra("hide", false)) {
            edGuests.setVisibility(View.INVISIBLE);
            tvGuestCount.setVisibility(View.INVISIBLE);
        }


        query = "SELECT id, name, roomprinter FROM rooms";
        loadRooms = new LoadRooms();
        loadRooms.execute(cafe.getServer(), query);

        query = "select t.id, t.name, t.room from tables t where t.id not in (select o.ordertable from orders o where o.status = 0)";
        loadTables = new LoadTables();
        loadTables.execute(cafe.getServer(), query);

        roomSpinner = (Spinner)findViewById(R.id.spRooms);
        roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int rid = 0;
                rid = roomAdapter.getItem(position).getRoomId();
                selRoom = rid;
                roomName = roomAdapter.getItem(position).getRoomName();
                tableAdapter.filter(rid);
                selTable = tableAdapter.getItem(0).getID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tableSpinner = (Spinner) findViewById(R.id.spTables);
        tableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int rid = 0;
                rid = tableAdapter.getItem(position).getID();
                selTable = rid;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnOK = (Button)findViewById(R.id.btnOk);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("room", selRoom);
                data.putExtra("table", selTable);
                data.putExtra("roomname", roomName);
                if (!intent.getBooleanExtra("hide", false)) {
                    data.putExtra("guests", Integer.parseInt(edGuests.getText().toString()));
                }
                setResult(RESULT_OK, data);
                finish();
            }
        });

        btnCancel = (Button)findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(NewOrderActivity.this, edGuests.getText(), Toast.LENGTH_SHORT).show();
                Intent data = new Intent();
                setResult(RESULT_CANCELED, data);
                finish();
            }
        });
   }

    class LoadRooms extends AsyncTask<String, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(String... params) {
            HTTPRequest httpRequest = new HTTPRequest(NewOrderActivity.this, params[0], params[1]);
            return httpRequest.getJson();
        }

        @Override
        protected void onPostExecute(JSONArray json) {
            if(json != null) {
                for (int i = 0; i < json.length(); i++) {
                    try {
                        JSONObject jo = json.getJSONObject(i);
                        Room room = new Room(
                                jo.getInt("id"),
                                jo.getString("name"),
                                jo.getInt("roomprinter")
                        );
                        rooms.add(room);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                roomAdapter = new RoomAdapter(NewOrderActivity.this, rooms);
                roomSpinner.setAdapter(roomAdapter);
            }
        }
    }

    class LoadTables extends AsyncTask<String, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(String... params) {
            HTTPRequest httpRequest = new HTTPRequest(NewOrderActivity.this, params[0], params[1]);
            return httpRequest.getJson();
        }

        @Override
        protected void onPostExecute(JSONArray json) {
            if(json != null) {
                for (int i = 0; i < json.length(); i++) {
                    try {
                        JSONObject jo = json.getJSONObject(i);
                        Table table = new Table(
                                jo.getInt("id"),
                                jo.getString("name"),
                                jo.getInt("room")
                        );
                        tables.add(table);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                tableAdapter = new TableAdapter(NewOrderActivity.this, tables);
                tableSpinner.setAdapter(tableAdapter);
            }
        }
    }
}
