package com.nurlan.zakaz.android;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by NURLAN on 20.01.2016.
 */

public class SQLConnection {

    String ip;
    String classs = "net.sourceforge.jtds.jdbc.Driver";
    String db = "ForAndro";
    String un = "sa";
    String password = "123";
    String query;

    String ConnURL;

    public SQLConnection(String ip) {
        this.ip = ip;
        ConnURL = "jdbc:jtds:sqlserver://" + ip + ";"
                + "databaseName=" + db + ";user=" + un + ";password="
                + password + ";";
    }

    public Connection CON() {
        Connection conn = null;
        try {
            Class.forName(classs);
            conn = DriverManager.getConnection(ConnURL);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public JSONArray Result(String query) {

        JSONArray resultSet = new JSONArray();
        this.query = query;
        Connection conn = null;

        Statement st = null;
        ResultSet rs = null;

        try {
            Class.forName(classs);
            conn = DriverManager.getConnection(ConnURL);

            if (conn != null) {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                if (rs != null) {
                    int columnCount = rs.getMetaData().getColumnCount();

                    while (rs.next()) {
                        JSONObject rowObject = new JSONObject();
                        for (int i = 1; i <= columnCount; i++) {
                            rowObject.put(rs.getMetaData().getColumnName(i), (rs.getString(i) != null) ? rs.getString(i) : "");
                        }
                        resultSet.put(rowObject);
                    }
                }
            }

        } catch (SQLException se) {
            Log.e("ERRO", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return resultSet;
    }
}
