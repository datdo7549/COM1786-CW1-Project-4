package com.example.comp1786cw1project3.feature.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.comp1786cw1project3.local.database.ExpenseDao;
import com.example.comp1786cw1project3.local.database.TripDao;
import com.example.comp1786cw1project3.model.TripModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HomePageViewModel extends BaseViewModel {
    private ExpenseDao expenseDaoDatabase;

    private MutableLiveData<List<TripModel>> _tripsMutableLiveData = new MutableLiveData<>();
    public LiveData<List<TripModel>> tripsLiveData = _tripsMutableLiveData;


    private TripDao tripDaoDatabase;
    @Inject
    public HomePageViewModel(TripDao tripDao, ExpenseDao expenseDao) {
        this.tripDaoDatabase = tripDao;
        this.expenseDaoDatabase = expenseDao;
    }

    public void deleteAllTripFromDatabase() {
        expenseDaoDatabase.deleteAllExpense();
        tripDaoDatabase.deleteAllTrip();
        getListTripsFromDatabase();
    }

    public void getListTripsFromDatabase() {
        List<TripModel> tripModels = tripDaoDatabase.getTrips();
        _tripsMutableLiveData.setValue(tripModels);
    }

    public void searchTripByName(String key) {
        List<TripModel> tripModelSearches = tripDaoDatabase.searchTrip(key);
        _tripsMutableLiveData.setValue(tripModelSearches);
    }
}
