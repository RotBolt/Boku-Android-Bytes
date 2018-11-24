package com.nindo.theninetails.geofeningtestapp.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.nindo.theninetails.geofeningtestapp.R;
import com.nindo.theninetails.geofeningtestapp.models.PlaceDetails;

import java.util.ArrayList;

/**
 * Created by theninetails on 20/1/18.
 */

public class UserLocationCardAdapter extends RecyclerView.Adapter<UserLocationCardAdapter.LocationViewHolder> {

    private Context mContext;
    private ArrayList<PlaceDetails> placeList;

    public UserLocationCardAdapter(Context mContext, ArrayList<PlaceDetails> placeList) {
        this.mContext = mContext;
        this.placeList = placeList;
    }

    public void updateList(ArrayList<PlaceDetails> newList){
        this.placeList=newList; // assigning of new list
        notifyDataSetChanged(); // makes new data visible
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = li.inflate(R.layout.user_location_card_view,parent, false);
        return new LocationViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
        PlaceDetails thisPlace = placeList.get(position);
        holder.bind(thisPlace);  // fill data to location cards
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder {
        private TextView placeNameHolder ;
        private TextView addressNameHolder;

        public LocationViewHolder(View itemView) {
            super(itemView);
            placeNameHolder=itemView.findViewById(R.id.tvPlaceHeaderSmallView);
            addressNameHolder=itemView.findViewById(R.id.tvAddressHeaderSmallView);
        }

        public void bind(PlaceDetails thisPlace){
            placeNameHolder.setText(thisPlace.placeName);
            addressNameHolder.setText(thisPlace.address);
        }
    }
}
