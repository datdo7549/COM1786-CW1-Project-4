package com.example.comp1786cw1project3.local.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.comp1786cw1project3.model.TripModel;

import java.util.List;

@Dao
public interface TripDao {
    @Query("SELECT * FROM Trip WHERE uid like :tripId")
    TripModel getTrip(String tripId);

    @Insert
    void insertTrip(TripModel tripModel);

    @Query("SELECT * FROM Trip")
    List<TripModel> getTrips();

    @Delete
    void deleteTrip(TripModel tripModel);

    @Query("DELETE FROM Trip")
    void deleteAllTrip();

    @Query("SELECT * FROM Trip WHERE trip_name LIKE '%' || :tripName || '%'")
    List<TripModel> searchTrip(String tripName);

    @Query("UPDATE Trip SET picture_path = :path WHERE uid = :id")
    void updatePath(int id, String path);


    @Query("UPDATE Trip SET trip_name = :tripName, destination = :destination, date_trip = :dateTrip, risk = :risk, description = :description  WHERE uid = :id")
    void updateSelectedTrip(int id,
                            String tripName,
                            String destination,
                            String dateTrip,
                            String risk,
                            String description);
}
