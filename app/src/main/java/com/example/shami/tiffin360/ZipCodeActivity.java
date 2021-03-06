package com.example.shami.tiffin360;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.shami.tiffin360.Adapters.MealPlanAdapter;
import com.example.shami.tiffin360.Data_Models.Meal_Data;
import com.example.shami.tiffin360.UtilityClass.Constants;
import com.example.shami.tiffin360.UtilityClass.LoaderClass;
import com.example.shami.tiffin360.UtilityClass.Network_Utility;
import com.example.shami.tiffin360.databinding.LayoutZipBinding;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shami on 9/10/2017.
 */

public class ZipCodeActivity extends AppCompatActivity implements LoaderCallbacks<String>,  LocationListener {


    private static final int ZIP_CCODE_LOADER_ID=1;


    private static final int MEAL_LOADER_ID=2;


    String lat="";
    String lng="";

    private static final int REQUEST_CHECK_SETTINGS = 0x1;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private static final int ACCESS_FINE_LOCATION_INTENT_ID = 3;
    private static final String BROADCAST_ACTION = "android.location.PROVIDERS_CHANGED";


    LayoutZipBinding mBinding;


    MealPlanAdapter adapter;

    List<Meal_Data> mMeal_Data_List;
    int select_Position;


    String[] zipCodeList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_zip);

        mBinding= DataBindingUtil.setContentView(ZipCodeActivity.this,R.layout.layout_zip);

        Intent intent=getIntent();
        if(intent!=null)
        {
            lat=intent.getStringExtra(Constants.Lat);
            lng=intent.getStringExtra(Constants.Long);
            if(!lat.equals("")&&!lng.equals(""))
            {
                mBinding.enableLocationIV.setEnabled(false);
                LoaderManager loaderManager = getLoaderManager();
                loaderManager.initLoader(ZIP_CCODE_LOADER_ID, null, this);
            }


        }

        mBinding.enableLocationIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initGoogleAPIClient();
                checkPermissions();
            }
        });




        mBinding.mealLayout.selectDaysBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent deliverYDayIntent=new Intent(ZipCodeActivity.this,DeliveryDayActivity.class);
               startActivity(deliverYDayIntent);
            }
        });


        mBinding.zipCodeET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(mBinding.zipCodeET.getText().toString().length()==5)
                {
                    setVisibilty(mBinding.zipCodeET.getText().toString());
                }
            }
        });

        LoadServerData();

    }


    public void LoadServerData()
    {
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(MEAL_LOADER_ID, null, this);
    }

    public void startActivity(Intent intent) {
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        ActivityCompat.startActivity(this, intent, activityOptions.toBundle());
    }



    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {

        URL url;
        switch (id)
        {
            case ZIP_CCODE_LOADER_ID:

                url= Network_Utility.createurl(Constants.USGS_REQUEST_URL,lat,lng);
                return new LoaderClass(this,url);

            case MEAL_LOADER_ID:

                try
                {
                    mBinding.avi.show();
                    url=new URL(Constants.TIFFINAPI_URL);

                    return new LoaderClass(this,url);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                break;
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        switch (loader.getId())
        {
            case MEAL_LOADER_ID:

                mBinding.avi.hide();
                SetMealData(data);
                setVisibilty(mBinding.zipCodeET.getText().toString().trim());
                break;
            case ZIP_CCODE_LOADER_ID:
                String postalAdress = getPostalCode(data);
                mBinding.zipCodeET.setText(postalAdress);
                break;
        }
    }


    ////
    public void setVisibilty(String zipCode) {
        if (zipCodeList != null)
        {
            for(int i=0;i<zipCodeList.length;i++)
            {
                if(zipCode.equals(zipCodeList[i]))
                {
                    mBinding.mealLayout.zipViewHolder.setVisibility(View.VISIBLE);

                }
            }
        }


    }



    @Override
    public void onLoaderReset(Loader<String> loader) {
        mBinding.zipCodeET.setText("");
    }


    /* Initiate Google API Client  */
    private void initGoogleAPIClient() {
        //Without Google API Client Auto Location Dialog will not work
        mGoogleApiClient = new GoogleApiClient.Builder(ZipCodeActivity.this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    /* Check Location Permission for Marshmallow Devices */
    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(ZipCodeActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)
                requestLocationPermission();
            else
                showSettingDialog();
        } else
            showSettingDialog();

    }

    /*  Show Popup to access User Permission  */
    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(ZipCodeActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(ZipCodeActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_INTENT_ID);

        } else {
            ActivityCompat.requestPermissions(ZipCodeActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_INTENT_ID);
        }
    }

    /* Show Location Access Dialog */
    private void showSettingDialog() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);//Setting priotity of Location request to high
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);//5 sec Time interval for location update
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true); //this is the key ingredient to show dialog always when GPS is off

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        LocationListerner();

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(ZipCodeActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }


    public void LocationListerner()
    {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case RESULT_OK:
                        LocationListerner();
                        break;
                    case RESULT_CANCELED:
                        break;
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(gpsLocationReceiver, new IntentFilter(BROADCAST_ACTION));//Register broadcast receiver to check the status of GPS
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Unregister receiver on destroy
        if (gpsLocationReceiver != null)
            unregisterReceiver(gpsLocationReceiver);
    }

    //Run on UI
    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            showSettingDialog();
        }
    };

    /* Broadcast receiver to check status of GPS */
    private BroadcastReceiver gpsLocationReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            //If Action is Location
            if (intent.getAction().matches(BROADCAST_ACTION)) {
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                //Check if GPS is turned ON or OFF
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Log.e("About GPS", "GPS is Enabled in your device");
                } else {
                    //If GPS turned OFF show Location Dialog
                    new Handler().postDelayed(sendUpdatesToUI, 10);
                    // showSettingDialog();


                }

            }
        }
    };



    /* On Request permission method to check the permisison is granted or not for Marshmallow+ Devices  */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ACCESS_FINE_LOCATION_INTENT_ID: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //If permission granted show location dialog if APIClient is not null
                    if (mGoogleApiClient == null) {
                        initGoogleAPIClient();
                        showSettingDialog();
                    } else
                        showSettingDialog();


                } else {

                    Toast.makeText(ZipCodeActivity.this, "Location Permission denied.", Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }




    @Override
    public void onLocationChanged(Location location) {
        lat=String.valueOf(location.getLatitude());
        lng= String.valueOf(location.getLongitude());
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(ZIP_CCODE_LOADER_ID, null, this);
    }



    /////Network Responses Parsing Section

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


    public void SetMealData(String data)
    {

        try
        {
            JSONObject mTiffinObject=new JSONObject(data);
            JSONArray zipCodeArray=mTiffinObject.getJSONArray("zipcodelist");
            mMeal_Data_List= new ArrayList<>();
            zipCodeList=new String[zipCodeArray.length()];
            for(int i=0;i<zipCodeArray.length();i++)
            {
                zipCodeList[i]=zipCodeArray.getString(i);
            }

            JSONArray mealOptionsArray=mTiffinObject.getJSONArray("meal options");

            for(int i=0;i<mealOptionsArray.length();i++)
            {
                JSONObject current_meal_object=mealOptionsArray.getJSONObject(i);

                String mID=current_meal_object.getString("id");
                String mTitle=current_meal_object.getString("title");
                String mCustom_Fields=current_meal_object.getString("custom_fields");
                String mCustomfield_Description=current_meal_object.getString("customfield_description");
                String mLinkBuilder=current_meal_object.getString("linkbuilder");
                String mPrice=current_meal_object.getString("price");
                String mCustomField_Image=current_meal_object.getString("customfield_image");

                mMeal_Data_List.add(new Meal_Data(mID,mTitle,mCustom_Fields,mCustomfield_Description,mLinkBuilder,mPrice,mCustomField_Image,false));
            }

            adapter=new MealPlanAdapter(ZipCodeActivity.this,mMeal_Data_List, new MealPlanAdapter.callback() {
                @Override
                public void SelectItem(int position) {
                 //   adapter.Update(position);
                    select_Position=position;
                    Intent deliverYDayIntent=new Intent(ZipCodeActivity.this,DeliveryDayActivity.class);

                   deliverYDayIntent.putExtra(Constants.ZIP_CODE,mBinding.zipCodeET.getText().toString());
                   deliverYDayIntent.putExtra(Constants.LINKBUILDER,mMeal_Data_List.get(select_Position).getmLinkBuilder());
                   deliverYDayIntent.putExtra(Constants.MEAL_ID,mMeal_Data_List.get(select_Position).getmID());
                   startActivity(deliverYDayIntent);
                }
            });
            mBinding.mealLayout.recyclerView.setLayoutManager(new LinearLayoutManager(this));
            mBinding.mealLayout.recyclerView.setAdapter(adapter);

        }
        catch (JSONException ex)
        {
            ex.printStackTrace();
        }

    }

}
