package com.example.comp1786cw1project3.feature.add_trip;

import com.example.comp1786cw1project3.feature.base.BaseViewModel;
import com.example.comp1786cw1project3.local.database.TripDao;
import com.example.comp1786cw1project3.model.Trip;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AddTripViewModel extends BaseViewModel {
    private TripDao tripDao;

    @Inject
    public AddTripViewModel(TripDao tripDao) {
        this.tripDao = tripDao;
    }

    public void saveTrip(String tripName, String destination, String dateOfTrip, String description, String risk) {
        Trip trip = new Trip(tripName, destination, dateOfTrip, risk, description);
        tripDao.insertTrip(trip);
    }
}
