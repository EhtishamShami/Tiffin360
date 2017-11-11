package com.example.shami.tiffin360.UtilityClass;

/**
 * Created by Shami on 9/10/2017.
 */

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.URL;


public class LoaderClass extends  AsyncTaskLoader<String> {

    /** Tag for log messages */
    private static final String LOG_TAG = LoaderClass.class.getName();

    private URL mUrl;

    private String postalAdress;

    public LoaderClass(Context context, URL url) {
        super(context);
        mUrl=url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    @Override
    public String loadInBackground() {

        if(mUrl == null)
        {
            return null;
        }

        String jsonResponse="";
        try{
            jsonResponse= Network_Utility.makeHttpRequest(mUrl);
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG,"Error in making http request"+e );
        }

        return jsonResponse;
    }
}
