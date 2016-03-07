package com.nurlan.zakaz;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.nurlan.zakaz.Adapters.AgentAdapter;
import com.nurlan.zakaz.DBHelpers.DataProvider;
import com.nurlan.zakaz.Objects.Agent;
import com.nurlan.zakaz.Objects.Customer;
import com.nurlan.zakaz.Objects.Product;
import com.nurlan.zakaz.android.SQLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AgentActivity extends Activity {

    private AgentAdapter agentAdapter;
    private ArrayList<Agent> agents;
    LoadAgents loadAgents;
    Zakaz zakaz;
    int selectedAgent;

    Spinner agentsList;
    Button setAgent;
    TextView ssCustomers;
    TextView ssProducts;
    Button mabtnOK;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent);

        zakaz = (Zakaz)getApplicationContext();

        agents = new ArrayList<Agent>();

        agentsList = (Spinner) findViewById(R.id.spAgents);
        agentsList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedAgent = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setAgent = (Button)findViewById(R.id.setAgent);
        setAgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zakaz.setAgent(agentAdapter.getItem(selectedAgent).getId());
                //loadCustomers
                ssCustomers.setVisibility(View.VISIBLE);
                String query = "select id, name, phone, adres from customers";
                LoadCustomers loadCustomers = new LoadCustomers();
                loadCustomers.execute(zakaz.getServer(), query);
            }
        });

        ssCustomers = (TextView)findViewById(R.id.ssCustomers);
        ssProducts = (TextView)findViewById(R.id.ssProducts);

        mabtnOK = (Button)findViewById(R.id.mabtnOK);
        mabtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AgentActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });

        String query = "select id, agent from agents";
        loadAgents = new LoadAgents();
        loadAgents.execute(zakaz.getServer(), query);
    }

    class LoadAgents extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(String... params) {
            SQLConnection httpRequest = new SQLConnection(params[0]);
            return httpRequest.Result(params[1]);
        }

        @Override
        protected void onPostExecute(JSONArray json) {
            if(json != null) {
                for (int i = 0; i < json.length(); i++) {
                    try {
                        JSONObject jo = json.getJSONObject(i);
                        Agent agent = new Agent();
                        agent.setId(jo.getInt("id"));
                        agent.setName(jo.getString("agent"));
                        agents.add(agent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                agentAdapter = new AgentAdapter(AgentActivity.this, agents);
                agentsList.setAdapter(agentAdapter);
            }
        }
    }

    class LoadCustomers extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(String... params) {
            SQLConnection httpRequest = new SQLConnection(params[0]);
            return httpRequest.Result(params[1]);
        }
        @Override
        protected void onPostExecute(JSONArray json) {
            if(json != null) {
                DataProvider CustomerProvider = new DataProvider(AgentActivity.this);
                List<Customer> customers = new ArrayList<Customer>();

                for (int i = 0; i < json.length(); i++) {
                    try {
                        JSONObject jo = json.getJSONObject(i);
                        Customer customer = new Customer(
                                jo.getInt("id"),
                                jo.isNull("name") ? "" : jo.getString("name"),
                                jo.isNull("phone") ? "" : jo.getString("phone"),
                                jo.isNull("adres") ? "" : jo.getString("adres")
                        );
                        customers.add(customer);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                CustomerProvider.FILLCUSTOMERS(customers);
                ssCustomers.setText("Загрузка клиентов ... OK!");
                //loadProducts
                ssProducts.setVisibility(View.VISIBLE);
                String query = getString(R.string.qry_loadMenu, zakaz.getAgent());
                LoadProducts loadProducts = new LoadProducts();
                loadProducts.execute(zakaz.getServer(), query);
            }
        }
    }

    class LoadProducts extends AsyncTask<String, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(String... params) {
            SQLConnection httpRequest = new SQLConnection(params[0]);
            return httpRequest.Result(params[1]);
        }
        @Override
        protected void onPostExecute(JSONArray json) {
            if(json != null) {
                DataProvider productProvider = new DataProvider(AgentActivity.this);
                List<Product> products = new ArrayList<Product>();

                for (int i = 0; i < json.length(); i++) {
                    try {
                        JSONObject jo = json.getJSONObject(i);
                        Product product = new Product(
                                jo.getInt("id"),
                                jo.getInt("parent"),
                                jo.getString("item"),
                                (float)jo.getDouble("price"),
                                jo.getInt("isfolder")
                        );
                        products.add(product);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                productProvider.FILLPRODUCTS(products);
                ssProducts.setText("Загрузка продукции ... OK!");

                //OK Button
                mabtnOK.setVisibility(View.VISIBLE);
            }
        }
    }
}
