package com.c301t3.c301t3app;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class addingTaskLocal extends FragmentActivity
        implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private float zoomLvl;
    private EditText searchAddress;
    private Button search;
    private String taskCoords;
    public static final int REQUEST_LOCATION_CODE = 1;
    InputMethodManager imm;


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
     * createsthe map fragment then checks the SDK of the phone.
     *
     * @param savedInstanceState the saved instance of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_task_local);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        search = findViewById(R.id.searchMap);
        searchAddress = findViewById(R.id.locationText);
        if (Build.VERSION.SDK_INT >= 22) {
            checkLocationPermission();
        }
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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
     * Also once the map has loaded, zooms to the userslocation.
     * @param googleMap the actual API for google maps
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // TODO: Before enabling the My Location layer, you must request
        // location permission from the user. This sample does not include
        // a request for location permission.

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            checkLocationPermission();
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnMapClickListener(this);


    }


    /**
     * When the button to the location is clicked, then it pans to the users location, then if the user clicks the marker
     * an alert asks if the users is sure to set that location as the task location.
     * confirmation sends it to the creation screen.
     * @return the passing or failing of the alert.
     */
    @Override
    public boolean onMyLocationButtonClick() {
        if (imm != null) {
            imm.hideSoftInputFromWindow(searchAddress.getWindowToken(), 0);
        }
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
        Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        mMap.clear(); //clears unwanted markers.
        zoomLvl = 15.0f;

        LatLng userLocation = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location")).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, zoomLvl));

        // from https://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard
        if (imm != null) {
            imm.hideSoftInputFromWindow(searchAddress.getWindowToken(), 0);
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //alert for use then set the long and lat for the task as this location. Currently as doubles, change to floats.
                new AlertDialog.Builder(addingTaskLocal.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Accept Task Location")
                        .setMessage("Are you sure you wish to set this location as the Task's location?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(), "Confirmed", Toast.LENGTH_SHORT).show();


                                // set the LatLOG to task  send it to create new task activity.

//                                //Intent mapIntent = new Intent(this, FindTaskonMapActivity.class);
//                                String coords = "33.8994864" + "/" + "-118.2861378"; //33.8994864,-118.2861378
//                                mapIntent.putExtra("taskCoords", coords);
//                                startActivity(mapIntent);

                                setResult(Activity.RESULT_OK);
                                finish();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();

                return false;


            }
        });
        return false;

    }

    /**
     * not used because we are using the built in portion above.
     * @param location users location coords.
     */
    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    public void searchMap(View view) {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (imm != null) {
            imm.hideSoftInputFromWindow(searchAddress.getWindowToken(), 0);
        }
        String query = searchAddress.getText().toString();
        query = query.replace(" ", "+");
        Log.i("The users request---->", query);
        List<Address> addressesList = new ArrayList<>();
        if (!query.equals("")) {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            try {
                addressesList = geocoder.getFromLocationName(query, 1); // Max 1 locations since task should be at one location.
                zoomLvl = 13.7f;
                Log.i("The address list ---->", addressesList.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addressesList.size() > 0) {
                try {
                    Address location = addressesList.get(0);
                    LatLng coords = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(coords).title("Task Location"))
                            //  got how to change colour here...https://stackoverflow.com/questions/16598169/changing-colour-of-markers-google-map-v2-android.
                            .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coords, zoomLvl));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }

    }


    /**
     * This checks the manifest permission and what the user said they accept.
     * Sets the permission code to 1
     */
    public void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mMap.clear();
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        String address = "";
        try {
            List<Address> listAddresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (listAddresses != null && listAddresses.size() > 0) {
                if (listAddresses.get(0).getThoroughfare() != null) {
                    if (listAddresses.get(0).getSubThoroughfare() != null) {
                        address += listAddresses.get(0).getSubThoroughfare() + " ";
                    }
                    address += listAddresses.get(0).getThoroughfare();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        zoomLvl = 15.0f;
        mMap.addMarker(new MarkerOptions().position(latLng).title(address)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLvl));
//        Log.i("<_________", latLng.toString() + address);
        Toast.makeText(getApplicationContext(), "If you wish to save this location, press the marker", Toast.LENGTH_LONG).show();


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //alert for use then set the long and lat for the task as this location. Currently as doubles, change to floats.
                new AlertDialog.Builder(addingTaskLocal.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Accept Task Location")
                        .setMessage("Are you sure you wish to set this location as the Task's location?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(), "Confirmed", Toast.LENGTH_SHORT).show();


                                // set the LatLOG to task  send it to create new task activity. and address if you want.

//                                //Intent mapIntent = new Intent(this, FindTaskonMapActivity.class);
//                                String coords = "33.8994864" + "/" + "-118.2861378"; //33.8994864,-118.2861378
//                                mapIntent.putExtra("taskCoords", coords);
//                                startActivity(mapIntent);

                                setResult(Activity.RESULT_OK);
                                finish();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();

                return false;


            }
        });


    }
}

