package com.example.atatus;

import android.Manifest;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

/*public class admin extends AppCompatActivity {

    Button btn;
    EditText addlat, addlog;
    private ProgressDialog progressDialog;
    DatabaseReference mregister;
    private FirebaseAuth mAuth;
    Double lat, lon;
    String latitude,longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        btn = (Button) findViewById(R.id.addblackbtn);
        addlat = (EditText) findViewById(R.id.addlat);
        addlog = (EditText) findViewById(R.id.addlog);
    }

    public void onaddblack(View view) {
        add();
    }

    private void add() {
        latitude =addlat.getText().toString().trim();
        longitude = addlog.getText().toString().trim();

        if (TextUtils.isEmpty(addlat.getText().toString().trim()) && TextUtils.isEmpty(addlog.getText().toString().trim())) {
            addlat.setError("Required");
            addlog.setError("Required");
            Toast.makeText(this, "Please Enter the Details", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(addlat.getText().toString().trim())) {
            addlat.setError("Enter Latitude");
        } else if (TextUtils.isEmpty(addlog.getText().toString().trim())) {
            addlog.setError("Required");
        }
        else
        {
            progressDialog.setMessage("Adding Blackspot....Please Wait");
            progressDialog.show();
            userdata();
            Toast.makeText(admin.this, "ADDED SUCCESSFULLY!!!", Toast.LENGTH_SHORT).show();
        }
        progressDialog.dismiss();

    }


    private void userdata(){
        lat =Double.parseDouble(latitude);
        lon=Double.parseDouble(longitude);
        mregister = FirebaseDatabase.getInstance().getReference("blackspot");
        String addid=mregister.push().getKey();
        addblackbase register=new addblackbase(addid,lat,lon);
        mregister.child(addid).setValue(register);
        Toast.makeText(this, "Black Spot added to database!", Toast.LENGTH_SHORT).show();
    }

}

*/


public class admin extends AppCompatActivity
        implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        OnMapReadyCallback,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener,
        ResultCallback<Status> {


    private static final String TAG = admin.class.getSimpleName();

    private GoogleMap map;
    private GoogleApiClient googleApiClient;
    private Location lastLocation;
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();

            //moveTaskToBack(false);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void exitByBackKey() {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Do you want to exit application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                        finish();
                        //close();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();
    }


    TextView addlati, addlog;
    private ProgressDialog progressDialog;
    DatabaseReference mregister;
    private FirebaseAuth mAuth;
    Double latiid, longiid;
    Double remlat,remlog;
    String latitude,longitude;

    LatLng objLatLng;
    private TextView textLat, textLong;

    private MapFragment mapFragment;

    private static final String NOTIFICATION_MSG = "NOTIFICATION MSG";
    // Create a Intent send by the notification
    public static Intent makeNotificationIntent(Context context, String msg) {
        Intent intent = new Intent( context, MainActivity.class );
        intent.putExtra( NOTIFICATION_MSG, msg );
        return intent;
    }
    private GeofencingClient geofencingClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        geofencingClient = LocationServices.getGeofencingClient(this);
        addlati = (EditText) findViewById(R.id.addlati);
        addlog = (EditText) findViewById(R.id.addlog);


        // initialize GoogleMaps
        initGMaps();

        // create GoogleApiClient
        createGoogleApi();

    }



    // Create GoogleApiClient instance
    private void createGoogleApi() {
        Log.d(TAG, "createGoogleApi()");
        if ( googleApiClient == null ) {
            googleApiClient = new GoogleApiClient.Builder( this )
                    .addConnectionCallbacks( this )
                    .addOnConnectionFailedListener( this )
                    .addApi( LocationServices.API )
                    .build();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Call GoogleApiClient connection when starting the Activity
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Disconnect GoogleApiClient when stopping Activity
        googleApiClient.disconnect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.main_menu, menu );
        return true;
    }

    //@Override
  /*  public boolean onOptionsItemSelected(MenuItem item) {
        switch ( item.getItemId() ) {
            case R.id.addblackspot: {
                store();
                finish();
                startActivity(getIntent());

                return true;
            }
            case R.id.removeblackspot: {
                removeblack();
                finish();
                startActivity(getIntent());

                return true;
            }
            case R.id.logout:{
                Intent i=new Intent(this, login.class);
                startActivity(i);

                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }*/
    private void store()
    {
        add();

    }
    private void add() {
        latitude =addlati.getText().toString().trim();
        longitude = addlog.getText().toString().trim();

        if (TextUtils.isEmpty(addlati.getText().toString().trim()) && TextUtils.isEmpty(addlog.getText().toString().trim())) {
            addlati.setError("Required");
            addlog.setError("Required");
            Toast.makeText(this, "Please Enter the Details", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(addlati.getText().toString().trim())) {
            addlati.setError("Enter Latitude");
        } else if (TextUtils.isEmpty(addlog.getText().toString().trim())) {
            addlog.setError("Required");
        }
        else
        {
            //progressDialog.setMessage("Adding Blackspot....Please Wait");
            //progressDialog.show();
            userdata();
        }

    }


    private void userdata(){
        latiid =Double.parseDouble(latitude);
        longiid=Double.parseDouble(longitude);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference locat;
        locat =firebaseDatabase.getReference().child("users").child(mAuth.getUid()).child("Location");
        String addid=locat.push().getKey();
        loc register=new loc(addid,latiid,longiid);
        locat.child(addid).setValue(register);


        Toast.makeText(this, "added !", Toast.LENGTH_SHORT).show();
    }
    public void removeblack()
    {
        latitude=addlati.getText().toString().trim();
        longitude = addlog.getText().toString().trim();
        remlat=Double.parseDouble(latitude);
        remlog=Double.parseDouble(longitude);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getUid()).child("Location");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        collectlatlon1((Map<String,Object>) dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }
    private void collectlatlon1(Map<String,Object> Location) {

        ArrayList<Double> latitudes = new ArrayList<>();

        ArrayList<Double> longitudes = new ArrayList<>();
        int count = 0, i;
        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : Location.entrySet()) {

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            latitudes.add((Double) singleUser.get("latiid"));
            longitudes.add((Double) singleUser.get("longiid"));
            count += 1;

            for (i=0;i<count;i++) {

                //Get user map
                //Double addlat = objLatLng.latitudes;
                //Double addlog = objLatLng.longitudes;
                Double addlatit = latitudes.get(i);
                Double addlogit = longitudes.get(i);
                if(remlat.equals(addlatit) && remlog.equals(addlogit))
                {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getUid());
                    Query applesQuery = ref.child("Location").orderByChild("latitude").equalTo(remlat);
                    applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                appleSnapshot.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e(TAG, "onCancelled", databaseError.toException());
                        }
                    });

                    Query applesQuery1 = ref.child("Location").orderByChild("latitude").equalTo(remlog);
                    applesQuery1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                appleSnapshot.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e(TAG, "onCancelled", databaseError.toException());
                        }
                    });

                    startActivity(getIntent());
                }

            }
        }
    }



    private final int REQ_PERMISSION = 999;

    // Check for permission to access Location
    private boolean checkPermission() {
        Log.d(TAG, "checkPermission()");
        // Ask for permission if it wasn't granted yet
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED );
    }

    // Asks for permission
    private void askPermission() {
        Log.d(TAG, "askPermission()");
        ActivityCompat.requestPermissions(
                this,
                new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                REQ_PERMISSION
        );
    }

    // Verify user's response of the permission requested
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult()");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch ( requestCode ) {
            case REQ_PERMISSION: {
                if ( grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                    // Permission granted
                    //getLastKnownLocation();

                } else {
                    // Permission denied
                    permissionsDenied();
                }
                break;
            }
        }
    }

    // App cannot work without the permissions
    private void permissionsDenied() {
        Log.w(TAG, "permissionsDenied()");
        // TODO close app and warn user
    }

    // Initialize GoogleMaps
    private void initGMaps(){
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    // Callback called when Map is ready
    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.d(TAG, "onMapReady()");
        map = googleMap;
        map.setOnMapClickListener(this);
        map.setOnMarkerClickListener(this);
        map.setMapType(map.MAP_TYPE_NORMAL);
        LatLng latLng = new LatLng( 11.0236,76.9426 );
        markerForGeofence(latLng);
        drawGeofence();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getUid()).child("Location");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        collectlatlon((Map<String,Object>) dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }

    private void collectlatlon(Map<String,Object> Location) {

        ArrayList<Double> latitudes = new ArrayList<>();

        ArrayList<Double> longitudes = new ArrayList<>();
        int count=0,i;
        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : Location.entrySet()) {

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            latitudes.add((Double) singleUser.get("latiid"));
            longitudes.add((Double) singleUser.get("longiid"));
            count+=1;
        }
        for (i=0;i<count;i++) {

            //Get user map
            //Double addlat = objLatLng.latitudes;
            //Double addlog = objLatLng.longitudes;
            Double addlat=latitudes.get(i);
            Double addlog=longitudes.get(i);

            LatLng latLng1 = new LatLng( addlat,addlog );
            markerForGeofence(latLng1);
            drawGeofence();
            //Get phone field and append to list
        }





        //    System.out.println(phoneNumbers.toString());
    }


    @Override
    public void onMapClick(LatLng latLng) {
        Log.d(TAG, "onMapClick("+latLng +")");
        markerForGeofence(latLng);
        addlati.setText( String.format(Double.toString(latLng.latitude),4) );
        addlog.setText( String.format(Double.toString(latLng.longitude),4));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d(TAG, "onMarkerClickListener: " + marker.getPosition() );
        addlati.setText(String.format(Double.toString(marker.getPosition().latitude),4));
        addlog.setText(String.format(Double.toString(marker.getPosition().longitude),4));
        return false;
    }

    private LocationRequest locationRequest;
    // Defined in mili seconds.
    // This number in extremely low, and should be used only for debug
    private final int UPDATE_INTERVAL =  1000;
    private final int FASTEST_INTERVAL = 900;

    // Start location Updates
    private void startLocationUpdates(){
        Log.i(TAG, "startLocationUpdates()");
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);

        if ( checkPermission() )
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged ["+location+"]");
        lastLocation = location;
        writeActualLocation(location);
    }

    // GoogleApiClient.ConnectionCallbacks connected
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "onConnected()");
        getLastKnownLocation();
        //recoverGeofenceMarker();
    }

    // GoogleApiClient.ConnectionCallbacks suspended
    @Override
    public void onConnectionSuspended(int i) {
        Log.w(TAG, "onConnectionSuspended()");
    }

    // GoogleApiClient.OnConnectionFailedListener fail
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.w(TAG, "onConnectionFailed()");
    }

    // Get last known location
    private void getLastKnownLocation() {
        Log.d(TAG, "getLastKnownLocation()");
        if ( checkPermission() ) {
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if ( lastLocation != null ) {
                Log.i(TAG, "LasKnown location. " +
                        "Long: " + lastLocation.getLongitude() +
                        " | Lat: " + lastLocation.getLatitude());
                writeLastLocation();
                startLocationUpdates();
            } else {
                Log.w(TAG, "No location retrieved yet");
                startLocationUpdates();
            }
        }
        else askPermission();
    }

    private void writeActualLocation(Location location) {
        //textLat.setText( "Lat: " + location.getLatitude() );
        //textLong.setText( "Long: " + location.getLongitude() );
        //locationMarker.remove();
        markerLocation(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    private void writeLastLocation() {
        writeActualLocation(lastLocation);
    }

    private Marker locationMarker;
    private void markerLocation(LatLng latLng) {
        Log.i(TAG, "markerLocation("+latLng+")");
        String title = latLng.latitude + ", " + latLng.longitude;
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title(title);
        if ( map!=null ) {
            if ( locationMarker != null )
                locationMarker.remove();
            //locationMarker = map.addMarker(markerOptions);
            float zoom = 14f;
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
            map.animateCamera(cameraUpdate);
        }
    }


    private Marker geoFenceMarker;
    private void markerForGeofence(LatLng latLng) {
        Log.i(TAG, "markerForGeofence("+latLng+")");
        String title = latLng.latitude + ", " + latLng.longitude;
        // Define marker options
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .title(title);
        if ( map!=null ) {
            // Remove last geoFenceMarker
            //if (geoFenceMarker != null)
            //  geoFenceMarker.remove();

            geoFenceMarker = map.addMarker(markerOptions);

        }
    }
    public ArrayList<Geofence> geofenceList=new ArrayList<>();
    // Start Geofence creation process
    private void startGeofence() {
        Log.i(TAG, "startGeofence()");
        if( geoFenceMarker != null ) {
            Geofence geofence = createGeofence( geoFenceMarker.getPosition(), GEOFENCE_RADIUS );
            Log.i("First Geofence",geofence.toString());
            geofenceList.add(geofence);
            GeofencingRequest geofenceRequest = createGeofenceRequest( geofence );
            //      addGeofence( geofenceRequest );
        }
        else {
            Log.e(TAG, "Geofence marker is null");
        }
    }

    private static final long GEO_DURATION = 60 * 60 * 1000;
    private static final float GEOFENCE_RADIUS = 500.0f; // in meters
    //private static final String GEOFENCE_REQ_ID = "My Geofence";
    public String makeid()
    {

        String text = "";
        String possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (int i = 0; i < 5; i++)
            text += possible.charAt((int) Math.floor(Math.random() * possible.length()));

        return text;
    }


    // Create a Geofence
    private Geofence createGeofence( LatLng latLng, float radius ) {
        Log.i("indresh", latLng.toString());
        String GEOFENCE_REQ_ID=makeid();
        return new Geofence.Builder()
                .setRequestId(GEOFENCE_REQ_ID)
                .setCircularRegion( latLng.latitude, latLng.longitude, radius)
                .setExpirationDuration( GEO_DURATION )
                .setTransitionTypes( Geofence.GEOFENCE_TRANSITION_ENTER
                        | Geofence.GEOFENCE_TRANSITION_EXIT )
                .build();
    }

    // Create a Geofence Request
    private GeofencingRequest createGeofenceRequest( Geofence geofence ) {
        Log.d(TAG, "createGeofenceRequest");
        /*return new GeofencingRequest.Builder()
                .setInitialTrigger( GeofencingRequest.INITIAL_TRIGGER_ENTER )
                .addGeofence( geofence )
                .build();*/
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(geofenceList);
        return builder.build();
    }

    private PendingIntent geoFencePendingIntent;
    private final int GEOFENCE_REQ_CODE = 0;
    private PendingIntent createGeofencePendingIntent() {
        Log.d(TAG, "createGeofencePendingIntent");
        if ( geoFencePendingIntent != null )
            return geoFencePendingIntent;

        Intent intent = new Intent( this, GeofenceTrasitionService.class);
        return PendingIntent.getService(
                this, GEOFENCE_REQ_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT );
    }

    // Add the created GeofenceRequest to the device's monitoring list
    /*private void addGeofence(GeofencingRequest request) {
        Log.d(TAG, "addGeofence");
        if (checkPermission())
            LocationServices.GeofencingApi.addGeofences(
                    googleApiClient,
                    request,
                    createGeofencePendingIntent()
            ).setResultCallback(this);
    }

    @Override
    public void onResult(@NonNull Status status) {
        Log.i(TAG, "onResult: " + status);
        if ( status.isSuccess() ) {
            saveGeofence();
            drawGeofence();
        } else {
            // inform about fail
        }
    }*/

  /*  private  void addGeofence(GeofencingRequest request)
    {
        geofencingClient.addGeofences(request, createGeofencePendingIntent())
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Geofences added
                        // ...
                    }
                })
                .addOnFailureListener(this, new  OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to add geofences
                        // ...
                    }
                });
    }
*/

    // Draw Geofence circle on GoogleMap
    private Circle geoFenceLimits;
    private void drawGeofence() {
        Log.d(TAG, "drawGeofence()");

        //if ( geoFenceLimits != null )
        //geoFenceLimits.remove();

        CircleOptions circleOptions = new CircleOptions()
                .center( geoFenceMarker.getPosition())
                .strokeColor(Color.argb(50, 70,70,70))
                .fillColor( Color.argb(100, 150,150,150) )
                .radius( GEOFENCE_RADIUS );
        geoFenceLimits = map.addCircle( circleOptions );
    }

    private final String KEY_GEOFENCE_LAT = "GEOFENCE LATITUDE";
    private final String KEY_GEOFENCE_LON = "GEOFENCE LONGITUDE";

    // Saving GeoFence marker with prefs mng
    /*private void saveGeofence() {
        Log.d(TAG, "saveGeofence()");
        SharedPreferences sharedPref = getPreferences( Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putLong( KEY_GEOFENCE_LAT, Double.doubleToRawLongBits( geoFenceMarker.getPosition().latitude ));
        editor.putLong( KEY_GEOFENCE_LON, Double.doubleToRawLongBits( geoFenceMarker.getPosition().longitude ));
        editor.apply();
    }

    // Recovering last Geofence marker
    private void recoverGeofenceMarker() {
        Log.d(TAG, "recoverGeofenceMarker");
        SharedPreferences sharedPref = getPreferences( Context.MODE_PRIVATE );

        if ( sharedPref.contains( KEY_GEOFENCE_LAT ) && sharedPref.contains( KEY_GEOFENCE_LON )) {
            double lat = Double.longBitsToDouble( sharedPref.getLong( KEY_GEOFENCE_LAT, -1 ));
            double lon = Double.longBitsToDouble( sharedPref.getLong( KEY_GEOFENCE_LON, -1 ));
            LatLng latLng = new LatLng( lat, lon );
            markerForGeofence(latLng);
            drawGeofence();
        }
    }*/

    // Clear Geofence
    private void clearGeofence() {
        Log.d(TAG, "clearGeofence()");
        LocationServices.GeofencingApi.removeGeofences(
                googleApiClient,
                createGeofencePendingIntent()
        ).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if ( status.isSuccess() ) {

                    removeGeofenceDraw();
                }
            }
        });
    }

    private void removeGeofenceDraw() {
        Log.d(TAG, "removeGeofenceDraw()");

        if ( geoFenceMarker != null)
            geoFenceMarker.remove();
        if ( geoFenceLimits != null )
            geoFenceLimits.remove();
    }


    @Override
    public void onResult(@NonNull Status status) {

    }
}