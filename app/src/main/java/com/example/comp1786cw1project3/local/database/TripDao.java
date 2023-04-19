package com.example.comp1786cw1project3.local.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.comp1786cw1project3.model.Trip;

import java.util.List;

@Dao
public interface TripDao {
    @Query("SELECT * FROM trip WHERE uid like :tripId")
    Trip getTrip(String tripId);

    @Insert
    void insertTrip(Trip trip);

    @Query("SELECT * FROM trip")
    List<Trip> getTrips();

    @Delete
    void deleteTrip(Trip trip);

    @Query("DELETE FROM trip")
    void deleteAllTrip();

    @Query("SELECT * FROM trip WHERE trip_name LIKE '%' || :tripName || '%'")
    List<Trip> searchTrip(String tripName);

    @Query("UPDATE trip SET picture_path = :path WHERE uid = :id")
    void updatePath(int id, String path);
}
