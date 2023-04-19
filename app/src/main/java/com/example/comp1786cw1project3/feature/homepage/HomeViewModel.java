package com.example.comp1786cw1project3.feature.homepage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.comp1786cw1project3.feature.base.BaseViewModel;
import com.example.comp1786cw1project3.local.database.ExpenseDao;
import com.example.comp1786cw1project3.local.database.TripDao;
import com.example.comp1786cw1project3.model.Trip;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HomeViewModel extends BaseViewModel {
    private MutableLiveData<List<Trip>> _trips = new MutableLiveData<>();
    LiveData<List<Trip>> trips = _trips;


    private TripDao tripDao;
    private ExpenseDao expenseDao;
    @Inject
    public HomeViewModel(TripDao tripDao, ExpenseDao expenseDao) {
        this.tripDao = tripDao;
        this.expenseDao = expenseDao;
        getTrips();
    }

    public void getTrips() {
        _trips.setValue(tripDao.getTrips());
    }

    public void resetDatabase() {
        tripDao.deleteAllTrip();
        expenseDao.deleteAllExpense();
        getTrips();
    }

    public void searchTrip(String key) {
        _trips.setValue(tripDao.searchTrip(key));
    }
}
