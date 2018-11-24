package com.nindo.theninetails.geofeningtestapp;

/**
 * Created by theninetails on 20/1/18.
 */


import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

/**
 * this is the wrapper/cover class over Geofence class  provided by android
 * this class handles just 'adding of Geofence'
 *
 * procedure of handling Geofence is done privately in this class
 *
 * Only one function "addLocationForMonitoring" is public which add location / geofence around it
 *
 */

public class GeofenceWrapper {

    // context w.r.t to activity which is using "GeofenceWrapper class" object
    // it is needed to get "Location Services"
    // it is also needed to start "intent service" which runs in background to monitor geofence

    private Context mContext;

    // this Object of "class GeofencingCleint" actually adds geofence for monitoring
    private GeofencingClient mGeofencingClient;

    // this the list to maintain geofences added for monitoring
    private ArrayList<Geofence> geofenceList;

    // this is pending intent i.e this intent fires or do the work when a location event is encountered
    // like Geofence Enter or Geofence Exit is encountered this intent is fired until then it is added to backstack
    private PendingIntent geofencingPendingIntent;

    //expirtation of geofence monitoring in millis
    private long geofenceExpirationinMillis;

    // request ID
    private String geofenceRequestId="dhakiChiki";

    public GeofenceWrapper(Context mContext) {
        this.mContext = mContext;
        this.geofenceList=new ArrayList<>();

        // initializing geofencing client
        this.mGeofencingClient= LocationServices.getGeofencingClient(mContext);
        this.geofenceExpirationinMillis=(12*60*60*1000);
    }

    // pending intent get function ; needed for adding geofence
    private PendingIntent getGeofencingPendingIntent(){
//        if(geofencingPendingIntent!=null)return geofencingPendingIntent;


        // intent to fire background service of geofence encounters
        Intent intent = new Intent(mContext,GeofenceTransitionsIntentService.class);


        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().

        // Pending Intent which fires the intent when location enter or exit is encountered
        geofencingPendingIntent=PendingIntent.getService(mContext,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        return geofencingPendingIntent;
    }

    // needed for add geofencing
    private GeofencingRequest getGeofeningRequest(Geofence geofence){
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        // trigger type of geofence
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);


        // add geofences list to builder
        /*******NOTE geofences list is added to builder not added for Monitoring till now*************/
        builder.addGeofence(geofence);
        return builder.build(); // creates and returns request

    }

    @SuppressLint("MissingPermission")

    // warning suppressed as this permission will be handled by activity
    void addLocationForMonitoring(LatLng latLng, float radius){
        Geofence g =  new Geofence.Builder()
                .setRequestId(geofenceRequestId) // request id needed
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER|Geofence.GEOFENCE_TRANSITION_EXIT)
                .setCircularRegion(latLng.latitude,latLng.longitude,radius)
                .setExpirationDuration(geofenceExpirationinMillis)
                .build();

        mGeofencingClient.addGeofences(getGeofeningRequest(g),getGeofencingPendingIntent())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
    }
}
