package com.example.greduationproject;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.greduationproject.login_tasks.models.Request;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Show_map extends AppCompatActivity implements OnMapReadyCallback {
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("request");
    private List<String> ids=new ArrayList<>();
    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final float DEFAULT_ZOOM = 15f;
    private GoogleApiClient mGoogleApiClient;
    private EditText mSearchText;
    private ImageView mGps;
    Button goToButton, continuetravel;
    Polyline polyline;
    TextView distance,price;
    Marker  cMarker2;
    LatLng startLatlang, endLatlang;
    String xLocation = "";
    Double sLat, eLat, sLong, eLong;
    DatabaseReference placeoFtravel;
    public String to;
    public  int year,month,day,hour,min,second;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map);
        mSearchText = findViewById(R.id.input_search);
        mGps = findViewById(R.id.ic_gps);
        goToButton = findViewById(R.id.go_to);
        continuetravel = findViewById(R.id.btncontinue);
        distance = findViewById(R.id.textViewDistance);
        price = findViewById(R.id.price);


        placeoFtravel =FirebaseDatabase.getInstance().getReference().child("travels").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("travel");


        getIdsOfDrivers();

        calculate();

        getlocationpermission();

        /************************************* TO CONTINUE Travel*******************************************/
        continuetravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                            Intent intent = new Intent(Show_map.this, Passenger_Travel_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("To",to);
                            intent.putExtra("Year",year);
                            intent.putExtra("Month",month);
                            intent.putExtra("Min",min);
                            intent.putExtra("Hour",hour);
                            intent.putExtra("Day",day);
                            intent.putExtra("Second",second);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(Show_map.this, Driver_Travel_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("To",to);
                            intent.putExtra("Year",year);
                            intent.putExtra("Month",month);
                            intent.putExtra("Min",min);
                            intent.putExtra("Hour",hour);
                            intent.putExtra("Day",day);
                            intent.putExtra("Second",second);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }


    /************************************* Get id of the driver *******************************************/
    private void getIdsOfDrivers() {
        DatabaseReference r_ids=FirebaseDatabase.getInstance().getReference().child("Driver");
        r_ids.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot d:dataSnapshot.getChildren())
                {
                    Log.d("eeeeeeeeeeee0",d.getKey());
                  reference.child(d.getKey()).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                      @RequiresApi(api = Build.VERSION_CODES.O)
                      @Override
                      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                          if (dataSnapshot!=null && dataSnapshot.exists())
                          {
                              boolean is_Accept= (boolean) dataSnapshot.child("is_accept").getValue();
                                  Log.d("eeeeeeeeeeee1",is_Accept+"");
                                  if (is_Accept)
                                  {
                                      String name=d.child("fullname").getValue()+"";
                                      buildNotification(name+"  is accept your request");
                                      Request request=new Request()  ;
                                      request.setName_users(dataSnapshot.child("name_users").getValue()+"");
                                      request.setPhone_users(dataSnapshot.child("phone_users").getValue()+"");
                                      request.setImage_users(dataSnapshot.child("image_users").getValue()+"");
                                      request.setIs_accept(false);
                                      reference.child(d.getKey()).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(request);
                                  }
                          }
                          else {
                              Log.d("eeeeeeeeeeee0","noooooooooooooo");
                          }
                      }

                      @Override
                      public void onCancelled(@NonNull DatabaseError databaseError) {

                      }
                  });
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void buildNotification(String text) {

        NotificationChannel channel = new NotificationChannel("not1", "reques", NotificationManager.IMPORTANCE_HIGH);
        Notification notification = new Notification.Builder(getApplicationContext(), "not1")
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentTitle("Request state")
                .setContentText(text)
                .build();

        NotificationManager manager=getSystemService(NotificationManager.class);

        manager.createNotificationChannel(channel);
        manager.notify(1,notification);
    }

    /************************************* Calender Function *******************************************/
    private void calculate() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        min = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);
        month = month + 1;
        //  dateee.setText(" ( Date : "+day+"/"+month+"/"+year+")");
        //  time.setText(second+":"+minute+":"+hour);
        //   timeee.setText(" ( Time : "+hour+":"+minute+":"+second+")");
    }

    private void geoLocate() {
        Log.d(TAG, "geoLocate: geolocating");

        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(Show_map.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString, 1);
        } catch (IOException e) {
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
        }

        if (list.size() > 0) {
            Address addresss = list.get(0);
            xLocation = addresss.getAddressLine(0).trim();
            Log.d(TAG, "geoLocate: found a location: " + addresss.toString());

            moveCamera(new LatLng(addresss.getLatitude(), addresss.getLongitude()), DEFAULT_ZOOM,
                    addresss.getAddressLine(0));
        }
    }

    /*************************************  Show Map on The screen *******************************************/
    private void initMap() {
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(Show_map.this);
    }

/***********************************Get Location Permission *******************************************/
    private void getlocationpermission() {
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                mLocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this,
                        permission,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permission,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    /*********************************** Request  Permission *******************************************/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }}
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }
    /***********************************Initialization *******************************************/
    //Search for location ...
    private void init() {
        Log.d(TAG, "init: initializing");

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {


            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_1) {

                    //execute our method for searching
                    geoLocate();
                }

                return false;
            }
        });
        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked gps icon");
                getDeviceLocation();
            }
        });
        hideSoftKeyboard();


    }


    /***********************************Get Device Location *******************************************/
    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        String curr=mFusedLocationProviderClient.toString();
        placeoFtravel.child("from").setValue(curr);
        try {
            if (mLocationPermissionsGranted) {

                if (ActivityCompat.checkSelfPermission(Show_map.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Show_map.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            Log.d(TAG, "onComplete: found location!");


                            Location currentLocation = (Location) task.getResult();
                            double lat1 = currentLocation.getLatitude();
                            double lng1 = currentLocation.getLongitude();
                            LatLng latLng = new LatLng(lat1, lng1);

                            sLat = currentLocation.getLatitude();
                            sLong = currentLocation.getLongitude();

                            // Toast.makeText(this,"dddd",Toast.LENGTH_LONG).show();
                            startLatlang = new LatLng(sLat, sLong);

                            moveCamera(latLng, 15, "My location");

                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(Show_map.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (Exception e) {

        }
    }

    /***********************************On Map Ready  *******************************************/
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);


            init();
            goToButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    geoLocate();  // take me to the second place

                    drawStraightLine();

                    to=mSearchText.getText().toString();
                    placeoFtravel.push().getKey();
                }
            });

        }
    }

    /***********************************Move Camera  *******************************************/
    private void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));


        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title(title);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));


        mMap.addMarker(options);   //add marker


        hideSoftKeyboard();
    }

    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void drawStraightLine() {


//////////////////////////////////////////////////////////////////////////
        List<Address> addressList2 = null;
        String dest = mSearchText.getText().toString();
        try {


            if (dest != null || !dest.equals("")) {
                Geocoder geocoder2 = new Geocoder(Show_map.this);

                try {
                    addressList2 = geocoder2.getFromLocationName(dest, 1);
                } catch (IOException e) {
                    e.printStackTrace();

                }
                Address address2 = addressList2.get(0);

                eLat = address2.getLatitude();
                eLong = address2.getLongitude();

                endLatlang = new LatLng(eLat, eLong);

                MarkerOptions options = new MarkerOptions().position(endLatlang).title("مصر");
                String des=mSearchText.getText().toString();
                placeoFtravel.child("to").setValue(des);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(endLatlang, 10));
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                cMarker2 = mMap.addMarker(options);


                float result[] = new float[10], res;
                Location.distanceBetween(sLat, sLong, eLat, eLong, result);
                res = result[0] / 1000;
                distance.setText(res + " km  ");
                //price.setText(" ( Price : "+res*1+"  )");


            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        // polyline= mMap.addPolygon(new PolygonOptions().add(startLatlang,endLatlang));


        polyline = mMap.addPolyline(new PolylineOptions()
                .add(startLatlang, endLatlang)
                .width(5)
                .color(Color.RED));
    }


}

