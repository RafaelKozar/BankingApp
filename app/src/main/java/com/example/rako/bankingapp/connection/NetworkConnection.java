package com.example.rako.bankingapp.connection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by rako on 28/09/2017.
 */

public class NetworkConnection {
    public static JSONArray getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            StringBuilder sb = new StringBuilder();
            String line;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            while ((line = bufferedReader.readLine()) != null){
                sb.append(line);
            }
            JSONArray jsonArray = new JSONArray(sb.toString());
            return jsonArray;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        } finally {
            urlConnection.disconnect();
        }
    }
}
