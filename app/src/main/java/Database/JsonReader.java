package Database;

import java.io.BufferedReader;

import java.io.InputStreamReader;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;


public class JsonReader {

    private JSONArray jsonArray;
    private JSONObject jsonObject;


    public JsonReader() {

    }

    private static String getText(String url) throws Exception {
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        BufferedReader in = new BufferedReader( new InputStreamReader(connection.getInputStream(),"UTF8"));

        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null)
            response.append(inputLine);

        in.close();

        return response.toString();
    }

    public ArrayList<String> getStrings(String key, String url) throws Exception {
        ArrayList<String> list = new ArrayList<>();
        jsonArray = new JSONArray(getText(url));

        for(int i = 0; i < jsonArray.length(); i++) {
            if(jsonArray.getJSONObject(i) != null) {
                jsonObject = jsonArray.getJSONObject(i);
                list.add(jsonObject.getString(key));
            } else {
                list.add(null);
            }
        }
        return list;
    }

    public String getString(String key, String url) throws Exception{
        jsonArray = new JSONArray(getText(url));

        if(jsonArray.length() == 1) {
            jsonObject = jsonArray.getJSONObject(0);
            return jsonObject.getString(key);
        } else {
            return null;
        }
    }

    public ArrayList<Integer> getIntegers(String key, String url) throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        jsonArray = new JSONArray(getText(url));

        for(int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            list.add(jsonObject.getInt(key));
        }
        return list;
    }


    public int getInt(String key, String url) throws Exception{
        jsonArray = new JSONArray(getText(url));

        if(jsonArray.length() == 1) {
            jsonObject = jsonArray.getJSONObject(0);
            return jsonObject.getInt(key);
        } else {
            return -1;
        }
    }

    public ArrayList<Double> getDoubles(String key, String url) throws Exception {
        ArrayList<Double> list = new ArrayList<>();
        jsonArray = new JSONArray(getText(url));

        for(int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            list.add(jsonObject.getDouble(key));
        }
        return list;
    }


    public Double getDouble(String key, String url) throws Exception{
        jsonArray = new JSONArray(getText(url));

        if(jsonArray.length() == 1) {
            jsonObject = jsonArray.getJSONObject(0);
            return jsonObject.getDouble(key);
        } else {
            return -1.0;
        }
    }

}