package com.example.comp1786cw1project3.feature.trip_detail;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.comp1786cw1project3.feature.base.BaseViewModel;
import com.example.comp1786cw1project3.local.database.ExpenseDao;
import com.example.comp1786cw1project3.local.database.TripDao;
import com.example.comp1786cw1project3.model.Expense;
import com.example.comp1786cw1project3.model.Trip;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TripDetailViewModel extends BaseViewModel {
    public String tripId;

    private TripDao tripDao;
    private ExpenseDao expenseDao;

    private MutableLiveData<Trip> _tripDetail = new MutableLiveData<>();
    public LiveData<Trip> tripDetail = _tripDetail;


    private MutableLiveData<List<Expense>> _tripExpenses = new MutableLiveData<>();
    public LiveData<List<Expense>> tripExpense = _tripExpenses;

    @Inject
    public TripDetailViewModel(TripDao tripDao, ExpenseDao expenseDao) {
        this.tripDao = tripDao;
        this.expenseDao = expenseDao;
    }

    public void getTripDetail() {
        _tripDetail.setValue(tripDao.getTrip(tripId));
    }

    public void deleteTrip() {
        tripDao.deleteTrip(tripDao.getTrip(tripId));
    }

    public void addExpenseToTrip(Expense expense) {
        expenseDao.insertExpense(expense);
    }

    public void getExpenses() {
        _tripExpenses.setValue(expenseDao.getExpenses(tripId));
    }

    public void updatePicture(String path) {
        tripDao.updatePath(Objects.requireNonNull(_tripDetail.getValue()).uid, path);
    }
}
