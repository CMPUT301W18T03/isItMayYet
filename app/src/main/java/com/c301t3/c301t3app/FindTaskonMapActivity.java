package com.c301t3.c301t3app;

import android.Manifest;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class FindTaskonMapActivity extends FragmentActivity
        implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private String taskCoords;
    private float longi;
    private float lati;
    private float zoomLvl;
    public TextView details;

    /**
     * THis is the logic for where the permission was granted or not depending on the SDK of the phone. If not granted it does't show anything.
     *
     * @param requestCode  the  request code from the bottom portion of which permission we are talking about.
     * @param permissions  The exact manifest permission in question.
     * @param grantResults The param to see if user grants or doesn't grant permission.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Addtion to the usual onCreate, this also grabs the put extra part from the previous screen
     * which holds the latitude and longitude of the task.
     * @param savedInstanceState the bundle that holds any info
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_find_task);
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
     *
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     *
     * Also once the map has loaded, zooms to the tasks location.
     * If the marker is clicked on then displays the address info on the top left.
     * @param googleMap the actual API for google maps
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // location permission from the user. This sample does not include
        // a request for location permission.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                if (Build.VERSION.SDK_INT < 23) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener); //0 second, 0 meters
                } else {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    } else {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener); //0 second, 0 meters
                    }
                }
            }
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        details = findViewById(R.id.detailsView);

        zoomLvl = 15.0f;
        LatLng userLocation = new LatLng(lati, longi);
        mMap.clear(); // clears unwanted markers.
        mMap.addMarker(new MarkerOptions().position(userLocation).title("Task Location")).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, zoomLvl));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.CANADA);
                try {
                    List<Address> listAddress = geocoder.getFromLocation(lati, longi, 1);
                    if (listAddress != null && listAddress.size() > 0) {
                        Log.i("Place Info <----", listAddress.get(0).toString());
//                        StringBuilder address = new StringBuilder();
//                        int i = 0;
//                        int maxLen = listAddress.get(0).getMaxAddressLineIndex();
//
//                        while (i != maxLen) {
//                            if (listAddress.get(0).getAddressLine(0) != null) {
//                                address.append(listAddress.get(0).getAddressLine(i));
//                                i++;
//                                address.append(", ");
//                            }
//                        }
//                        Log.i("dasda sfsfsf <---------", address.toString());
//                        address = new StringBuilder(address.substring(0, address.length())); // This will give you forthrought, city area, postal code. If has those options.
                        details.setText(listAddress.get(0).getAddressLine(0));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

    }

    /**
     * used to zoom in to the users position on earth if location is on, using the location button.
     * @return returns a bool if the button is unclicked
     */
    @Override
    public boolean onMyLocationButtonClick() {
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        LatLng userLocation = new LatLng(lati, longi);
        //mMap.clear(); // clears unwanted markers.
        //mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));

        return false;
    }

    /**
     *  location fuction that was mandatory to have for mylocation, however this part isn't used
     * @param location gps location of user
     */
    @Override
    public void onMyLocationClick(@NonNull Location location) {
    }
}

