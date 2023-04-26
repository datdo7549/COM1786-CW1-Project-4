package com.example.comp1786cw1project3.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Trip")
public class TripModel {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "trip_name")
    public String tripName;

    @ColumnInfo(name = "destination")
    public String destination;

    @ColumnInfo(name = "date_trip")
    public String dateTrip;

    @ColumnInfo(name = "risk")
    public String risk;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "picture_path")
    public String path;

    public TripModel(String tripName, String destination, String dateTrip, String risk, String description) {
        this.tripName = tripName;
        this.destination = destination;
        this.dateTrip = dateTrip;
        this.risk = risk;
        this.description = description;
        this.path = "";
    }
}
