package com.example.shami.tiffin360.UtilityClass;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Shami on 9/10/2017.
 */

public class Network_Utility {

    private static final String Log_Tag="[DM]Shami "+Network_Utility.class.getSimpleName();


    public static URL createurl(String url,String lat,String lng)
    {
        URL myurl=null;
        try{
            Uri builtUri = Uri.parse(url)
                    .buildUpon()
                    .appendQueryParameter("lat", lat)
                    .appendQueryParameter("lng", lng)
                    .appendQueryParameter("username", "ehtisham")
                    .build();

            myurl=new URL(builtUri.toString());

        }catch(MalformedURLException exception)
        {
            Log.e(Log_Tag,"URl cannot be parsed "+exception);
        }
        return myurl;

    }


    public static String makeHttpRequest(URL url) throws IOException
    {
        String jsonResponse="";

        if(url==null)
        {
            return jsonResponse;
        }
        HttpURLConnection connection=null;
        InputStream in=null;

        try{
            connection=(HttpURLConnection)url.openConnection();
            connection.setReadTimeout(20000 /* milliseconds */);
            connection.setConnectTimeout(20000);
            connection.setRequestMethod("GET");
            connection.connect();

            if(connection.getResponseCode()==200)
            {
                in=connection.getInputStream();
                jsonResponse=readfromstream(in);
            }

        }
        catch (IOException e)
        {
            Log.e(Log_Tag,"Error response code: " + connection.getResponseCode());
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (in != null) {

                in.close();
            }
        }

        return jsonResponse;
    }


    private static String readfromstream(InputStream inputStream)throws IOException
    {
        StringBuilder stringBuilder=new StringBuilder();
        if(inputStream!=null)
        {
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader=new BufferedReader(inputStreamReader);
            String line=reader.readLine();
            while(line!=null)
            {
                stringBuilder.append(line);
                line=reader.readLine();
            }
        }
        return stringBuilder.toString();

    }



    public static String getPostalCode(String str)
    {
        try
        {
            JSONObject zemaObject=new JSONObject(str);
            JSONArray jsonArray=zemaObject.getJSONArray("postalCodes");
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject data=jsonArray.getJSONObject(i);
                String postalCode=data.getString("postalCode");
                return  postalCode;
            }
        }
        catch (JSONException ex)
        {
            ex.printStackTrace();
        }

        return null;
    }


}
