package com.c301t3.c301t3app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class MapsActivity extends FragmentActivity
        implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private String taskCoords;
    private float longi;
    private float lati;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        taskCoords = null;


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                taskCoords = null;
                Toast.makeText(getApplicationContext(), "extra string is NULL", Toast.LENGTH_SHORT).show();
            } else {
                taskCoords = extras.getString("taskCoords");

                StringTokenizer tokens = new StringTokenizer(taskCoords, "/");
                lati = Float.parseFloat(tokens.nextToken());
                longi = Float.parseFloat(tokens.nextToken());
            }
        } else {
            taskCoords = (String) savedInstanceState.getSerializable("MainToSelectedTask");
            Toast.makeText(getApplicationContext(), "savedInstanceState not NULL", Toast.LENGTH_SHORT).show();

        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // TODO: Before enabling the My Location layer, you must request
        // location permission from the user. This sample does not include
        // a request for location permission.
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                // Updates the location when location moves.
                LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.clear(); // clears unwanted markers.
                mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));

                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.CANADA);

                try {
                    List<Address> listAddress = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if (listAddress != null && listAddress.size() > 0) {
                        Log.i("Place Info <----", listAddress.get(0).toString());
                        String address = "";
                        int i = 0;
                        int maxLen = listAddress.get(0).getMaxAddressLineIndex();

                        while (i != maxLen) {
                            if (listAddress.get(0).getAddressLine(0) != null) {
                                address += listAddress.get(0).getAddressLine(i);
                                i++;
                                address += ", ";
                            }
                        }
                        address = address.substring(0, address.length() - 2); // This will give you forthrought, city area, postal code. If has those options.
                        Toast.makeText(getApplicationContext(), address, Toast.LENGTH_SHORT).show();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if (Build.VERSION.SDK_INT < 23) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener); //0 second, 0 meters
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener); //0 second, 0 meters
                //getting last known location.

                Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                mMap.clear(); //clears unwanted markers.

                LatLng userLocation = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));


            }

        }

    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        float zoomLevel = 15.0f;
        LatLng userLocation = new LatLng(lati, longi);
        mMap.clear(); // clears unwanted markers.
        mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, zoomLevel));
        return false;
    }

    public void searchAddress(View view) {

    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();

    }
}
