package com.ulutsoft.nurlan.cafe.android;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by NURLAN on 21.07.2015.
 */
public class JSON {

    public JSONArray getJSON(String url, String query){

        URL uri;
        OutputStream output;
        InputStream response = null;
        String charset = "UTF-8";
        JSONArray json = null;

        StringBuffer stringBuffer = new StringBuffer();

        HttpURLConnection connection = null;
        try {
            uri = new URL(url);
            connection = (HttpURLConnection) uri.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Content-Type", "text/plain; charset=" + charset);

            output = connection.getOutputStream();
            output.write(query.getBytes(charset));

            output.flush();
            output.close();

            response = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(response));

            String string;
            while ((string = reader.readLine()) != null) {
                stringBuffer.append(string.toString());
            }
            reader.close();
            json = new JSONArray(stringBuffer.toString());


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }
}
