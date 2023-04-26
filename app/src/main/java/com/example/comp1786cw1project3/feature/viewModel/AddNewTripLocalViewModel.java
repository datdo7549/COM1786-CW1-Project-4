package com.example.comp1786cw1project3.feature.viewModel;

import com.example.comp1786cw1project3.feature.viewModel.BaseViewModel;
import com.example.comp1786cw1project3.local.database.TripDao;
import com.example.comp1786cw1project3.model.TripModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AddNewTripLocalViewModel extends BaseViewModel {
    private TripDao tripDaoDatabase;

    @Inject
    public AddNewTripLocalViewModel(TripDao tripDao) {
        this.tripDaoDatabase = tripDao;
    }

    public void saveTripToLocalDatabase(String tripName,
                                        String destination,
                                        String dateOfTrip,
                                        String description,
                                        String tripRisk
    ) {
        TripModel tripModel = new TripModel(tripName, destination, dateOfTrip, tripRisk, description);
        tripDaoDatabase.insertTrip(tripModel);
    }
}
