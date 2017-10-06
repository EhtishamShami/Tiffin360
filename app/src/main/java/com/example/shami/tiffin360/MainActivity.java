package com.example.shami.tiffin360;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.shami.tiffin360.UtilityClass.Constants;
import com.example.shami.tiffin360.UtilityClass.DriverData;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener {



    private static final String Log_Tag="[DM]SHAMI "+MainActivity.class.getSimpleName();

    GoogleMap map;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;


    // Firebase instance variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;


    boolean isMapReady = false;


    Button orderBtn;

    String lat="";
    String lng="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("driver");
        BuildGoogleApiClient();

        orderBtn=(Button)findViewById(R.id.OrderBtn);
        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ZipCodeActivity.class);
                intent.putExtra(Constants.Lat,lat);
                intent.putExtra(Constants.Long,lng);
                startActivity(intent);
            }
        });

    }

    public void startActivity(Intent intent) {
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        ActivityCompat.startActivity(this, intent, activityOptions.toBundle());
    }


    public void BuildGoogleApiClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }

        mChildEventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(isMapReady)
                {
                    try
                    {
                        DriverData mDriverData=new DriverData();
                        for(DataSnapshot child : dataSnapshot.getChildren()) {
                            String id = child.getKey();
                            String Driver_name = child.child("driver_Name").getValue().toString();
                            String Driver_Lat = child.child("driver_Lat").getValue().toString();
                            String Driver_long = child.child("driver_Long").getValue().toString();
                            mDriverData.setDriver_Id(id);
                            mDriverData.setDriver_Name(Driver_name);
                            mDriverData.setDriver_Lat(Driver_Lat);
                            mDriverData.setDriver_Long(Driver_long);
                        }

                        Log.v(Log_Tag, "COUNT is "+dataSnapshot.getChildrenCount());
                        Log.v(Log_Tag,mDriverData.getDriver_Name()+ " "+mDriverData.getDriver_Lat()+" "+mDriverData.getDriver_Long());
                        Add_Marker(mDriverData.getDriver_Name(),mDriverData.getDriver_Lat(),mDriverData.getDriver_Long());

                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(isMapReady)
                {
                    try
                    {
                        DriverData mDriverData=new DriverData();
                        for(DataSnapshot child : dataSnapshot.getChildren()) {
                            String id = child.getKey();
                            String Driver_name = child.child("driver_Name").getValue().toString();
                            String Driver_Lat = child.child("driver_Lat").getValue().toString();
                            String Driver_long = child.child("driver_Long").getValue().toString();
                            mDriverData.setDriver_Id(id);
                            mDriverData.setDriver_Name(Driver_name);
                            mDriverData.setDriver_Lat(Driver_Lat);
                            mDriverData.setDriver_Long(Driver_long);
                        }

                        Log.v(Log_Tag, "COUNT is "+dataSnapshot.getChildrenCount());
                        Log.v(Log_Tag,mDriverData.getDriver_Name()+ " "+mDriverData.getDriver_Lat()+" "+mDriverData.getDriver_Long());
                        Add_Marker(mDriverData.getDriver_Name(),mDriverData.getDriver_Lat(),mDriverData.getDriver_Long());

                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabaseReference.addChildEventListener(mChildEventListener);

    }



    @Override
    protected void onStop() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        lat=String.valueOf(location.getLatitude());
        lng= String.valueOf(location.getLongitude());
     //   CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
   //     map.animateCamera(cameraUpdate);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        isMapReady=true;
        map = googleMap;
    }

    void Add_Marker(String DriverName,String DriverLat,String DriverLong)
    {
        try{
            Double lat=Double.parseDouble(DriverLat);
            Double longi=Double.parseDouble(DriverLong);

            LatLng sydney = new LatLng(lat, longi);
            map.clear();
            map.addMarker(new MarkerOptions().position(sydney)
                    .title(DriverName)).setIcon(BitmapDescriptorFactory.defaultMarker(R.drawable.ic_delivery_man));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }



}
