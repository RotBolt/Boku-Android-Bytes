package com.nindo.theninetails.geofeningtestapp;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.nindo.theninetails.geofeningtestapp.adapters.UserLocationCardAdapter;
import com.nindo.theninetails.geofeningtestapp.database.SavedPlaceDBHelper;
import com.nindo.theninetails.geofeningtestapp.database.SavedPlacesReaderContract;
import com.nindo.theninetails.geofeningtestapp.models.PlaceDetails;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private PlacePicker.IntentBuilder builder;
    private static final int placePickerRequest=1;
    private RecyclerView rvUserLocations;
    private FloatingActionButton fab;
    private GeofenceWrapper geoWorker;

    private UserLocationCardAdapter userLocationCardAdapter;

    private LinearLayout emptyView;

    private static String[] locationPermissions=new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!checkForPermissions(locationPermissions)){
            // if permission not granted then request for permissions
            ActivityCompat.requestPermissions(this,locationPermissions,1);
        }

        emptyView=findViewById(R.id.emptyView);

        //floating action button
        fab=findViewById(R.id.fab);

        // onclick opens place picker
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // starts activity and after work is done call goes to "onActivityResult"
                    startActivityForResult(builder.build(MainActivity.this),placePickerRequest);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        builder = new PlacePicker.IntentBuilder();
        rvUserLocations =findViewById(R.id.rvUserLocations);
        rvUserLocations.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                true
        ));


        userLocationCardAdapter = new UserLocationCardAdapter(this,new ArrayList<PlaceDetails>());
        rvUserLocations.setAdapter(userLocationCardAdapter);

        geoWorker= new GeofenceWrapper(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==placePickerRequest){
            if(resultCode==RESULT_OK){
                Place thisPlace = PlacePicker.getPlace(this,data);
                PlaceDetails placeDetails = new PlaceDetails();
                placeDetails.placeName=thisPlace.getName().toString();
                placeDetails.address=thisPlace.getAddress().toString();

                // key value pair class like hash map
                // key value must match Columns of database (i.e in ContractClass in database package)
                ContentValues values = new ContentValues();
                values.put(SavedPlacesReaderContract.PlaceEntry.COLUMN_PLACE_NAME,placeDetails.placeName);
                values.put(SavedPlacesReaderContract.PlaceEntry.COLUMN_ADDRESS,placeDetails.address);


                SavedPlaceDBHelper dbHelper = new SavedPlaceDBHelper(this);
                SQLiteDatabase placeDb = dbHelper.getWritableDatabase();
                // inserts data to database
                placeDb.insert(
                        SavedPlacesReaderContract.PlaceEntry.TABLE_NAME,
                        null,
                        values
                );
                // by default radius is 5 km
                geoWorker.addLocationForMonitoring(
                        thisPlace.getLatLng(),
                        5000.0f
                );
            }
        }
    }



    private boolean checkForPermissions(String[] locationPermissions){
        if(ActivityCompat.checkSelfPermission(this,locationPermissions[0])!= PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this,locationPermissions[1])!=PackageManager.PERMISSION_GRANTED){
            return false;
        }
        return true;
    }

    private boolean checkDbEmpty(){
        SavedPlaceDBHelper helper = new SavedPlaceDBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        // projections are those columns which we want to fetch from database
        String[] projecttions = new String[]{SavedPlacesReaderContract.PlaceEntry.COLUMN_PLACE_NAME};


        // this cursor object actually medium to fetch data from db
        Cursor cursor = db.query(
                SavedPlacesReaderContract.PlaceEntry.TABLE_NAME,
                projecttions,
                null,
                null,
                null,
                null,
                null
        );

        return !cursor.moveToNext();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(checkDbEmpty()){
            emptyView.setVisibility(View.VISIBLE);
            rvUserLocations.setVisibility(View.GONE);
        }else{
            emptyView.setVisibility(View.GONE);
            rvUserLocations.setVisibility(View.VISIBLE);
            userLocationCardAdapter.updateList(getListFromDb());
        }

    }

    private ArrayList<PlaceDetails> getListFromDb(){

        ArrayList<PlaceDetails> list = new ArrayList<>();
        SavedPlaceDBHelper helper = new SavedPlaceDBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        // projections are those columns which we want to fetch from database
        String[] projecttions = new String[]{
                SavedPlacesReaderContract.PlaceEntry.COLUMN_PLACE_NAME,
                SavedPlacesReaderContract.PlaceEntry.COLUMN_ADDRESS
        };


        // this cursor object actually medium to fetch data from db
        Cursor cursor = db.query(
                SavedPlacesReaderContract.PlaceEntry.TABLE_NAME,
                projecttions,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()){
            String placeName = cursor.getString(cursor.getColumnIndexOrThrow(SavedPlacesReaderContract.PlaceEntry.COLUMN_PLACE_NAME));
            String address = cursor.getString(cursor.getColumnIndexOrThrow(SavedPlacesReaderContract.PlaceEntry.COLUMN_ADDRESS));

            // creating PlaceDetails object
            PlaceDetails placeDetails = new PlaceDetails();
            placeDetails.placeName=placeName;
            placeDetails.address=address;
            list.add(placeDetails);
        }

        return list;
    }
}
