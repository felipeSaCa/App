package com.comov.myapplication.location;

import androidx.fragment.app.FragmentActivity;

import android.location.Location;
import android.os.Bundle;

import com.comov.myapplication.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        fusedLocationClient = LocationHelper.getLocationFusedInstance(this);
        getLastLocation(fusedLocationClient, this);

        /*// Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

    private void getLastLocation(FusedLocationProviderClient fusedLocationClient, MapsActivity mapsActivity) {
        fusedLocationClient.getLastLocation().addOnSuccessListener(mapsActivity, location -> {//ON SUCCESS LISTENER
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                LocationHelper.location = location;
                LatLng lastLocation = new LatLng(LocationHelper.location.getLatitude(), LocationHelper.location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(lastLocation).title("Marker in Last location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(lastLocation));
            }
        });
    }
}
