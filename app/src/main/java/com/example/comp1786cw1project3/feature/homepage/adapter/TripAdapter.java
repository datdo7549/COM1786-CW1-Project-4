package com.example.comp1786cw1project3.feature.homepage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comp1786cw1project3.R;
import com.example.comp1786cw1project3.model.Trip;
import com.example.comp1786cw1project3.util.listener.ItemTripClickListener;

import java.util.ArrayList;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {

    private ArrayList<Trip> mData;
    private ItemTripClickListener listener;

    private Context context;

    public TripAdapter(Context context, ArrayList<Trip> data, ItemTripClickListener listener) {
        this.context = context;
        this.mData = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trip, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trip item = mData.get(position);
        if (position%2 == 0) {
            holder.root.setBackground(ContextCompat.getDrawable(context, R.drawable.item_trip_background));
        } else {
            holder.root.setBackground(ContextCompat.getDrawable(context, R.drawable.item_trip_background_1));
        }
        holder.tripName.setText(item.tripName);
        holder.dateOfTrip.setText(item.dateTrip);
        holder.itemView.setOnClickListener(v -> {
            listener.onItemClicked(String.valueOf(item.uid));
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout root;
        public AppCompatTextView tripName;
        public AppCompatTextView dateOfTrip;

        public ViewHolder(View view) {
            super(view);
            root = view.findViewById(R.id.root);
            tripName = view.findViewById(R.id.tvTripName);
            dateOfTrip = view.findViewById(R.id.dateOfTrip);
        }
    }
}