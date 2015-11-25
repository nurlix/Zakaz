package com.ulutsoft.cafe.android;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by NURLAN on 23.07.2015.
 */
public class HTTPRequest {

    public String json = null;
    Context context;

    OutputStream output;
    InputStream inputStream = null;
    HttpURLConnection connection = null;
    String charset = "UTF-8";

    public HTTPRequest(Context context, String url, String query) {

        this.context = context;

        try {
            URL uri = new URL(url);
            connection = (HttpURLConnection) uri.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Content-Type", "text/plain; charset=" + charset);
            output = connection.getOutputStream();
            output.write(query.getBytes(charset));
            output.close();

            int statusCode = connection.getResponseCode();
            if (statusCode == 200) {
                inputStream = new BufferedInputStream(connection.getInputStream());
                json = getJSON(inputStream);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private String getJSON(InputStream inputStream) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = null;
        String result = null;

        while((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line.toString());
        }
        result = stringBuffer.toString();

        if(null!=inputStream){
            inputStream.close();
        }
        return result;
    }

    public JSONArray getJson(){
        try {
            JSONArray jsonArray = new JSONArray(json);
            return jsonArray;
        }
        catch (JSONException e){
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
