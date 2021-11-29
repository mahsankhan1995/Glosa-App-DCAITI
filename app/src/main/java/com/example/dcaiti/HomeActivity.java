package com.example.dcaiti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.internal.location.zzz;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

//public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback {
public class HomeActivity extends AppCompatActivity {

   /* public void imgChange (View view)
    {
        ImageView img1 = (ImageView) findViewById(R.id.image_red);
        img1.setImageResource(R.drawable.signal_orange);
    }
    */


    ImageView imagesignal;
    boolean imgDisplay = false;
    int count = 0;

    SupportMapFragment supportMapFragment;
    GoogleMap map;
    FusedLocationProviderClient clients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        // ---------------signal part -------------------
        // ---------------------------------------------

        int[] imagelist = new int[]{R.drawable.signal_red, R.drawable.signal_orange, R.drawable.signal_green};


        imagesignal = findViewById(R.id.image_signal);

        imagesignal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (count == 0) {
                    imagesignal.setImageResource(imagelist[0]);
                } else if (count == 1) {
                    imagesignal.setImageResource(imagelist[1]);
                } else if (count == 2) {
                    imagesignal.setImageResource(imagelist[2]);
                } else if (count == 3 || count > 3) {
                    count = 0;
                }

                count++;
            }
        });


        // ------- Map -----------------
        // -----------------------------
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        // mapFragment.getMapAsync((OnMapReadyCallback) this);

        //------- starting location ------
        clients = LocationServices.getFusedLocationProviderClient(this);

        //--------- permission check ---------
        if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

    }

    private void getCurrentLocation() {


        @SuppressLint("MissingPermission") Task<Location> task = clients.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if(location != null){
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {

                            LatLng latlag = new LatLng(location.getLatitude(),location.getLongitude());

                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlag, 18));

                        }
                    });
                }
            }
        });
    }
/*
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        LatLng berlin = new LatLng(52.5200,13.4050);
                   -- android:value="@string/map_key"




    }

 */

    // ------- Map -----------------


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
if(requestCode == 44){
    if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
        getCurrentLocation();
    }
}
    }
}