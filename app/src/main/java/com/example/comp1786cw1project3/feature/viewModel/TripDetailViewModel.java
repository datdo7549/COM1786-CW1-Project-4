package com.example.comp1786cw1project3.feature.viewModel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.comp1786cw1project3.local.database.ExpenseDao;
import com.example.comp1786cw1project3.local.database.TripDao;
import com.example.comp1786cw1project3.model.ExpenseModel;
import com.example.comp1786cw1project3.model.TripModel;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TripDetailViewModel extends BaseViewModel {
    public String tripId;

    private TripDao tripDaoDatabase;
    private ExpenseDao expenseDaoDatabase;

    private MutableLiveData<TripModel> _tripDetailMutableLiveData = new MutableLiveData<>();
    public LiveData<TripModel> tripDetailLiveData = _tripDetailMutableLiveData;


    private MutableLiveData<List<ExpenseModel>> _tripExpensesMutableLiveData = new MutableLiveData<>();
    public LiveData<List<ExpenseModel>> tripExpenseLiveData = _tripExpensesMutableLiveData;

    @Inject
    public TripDetailViewModel(TripDao tripDao, ExpenseDao expenseDao) {
        this.tripDaoDatabase = tripDao;
        this.expenseDaoDatabase = expenseDao;
    }

    public void getTripDetailFromDatabase() {
        TripModel singleTripModel = tripDaoDatabase.getTrip(tripId);
        _tripDetailMutableLiveData.setValue(singleTripModel);
    }

    public void updatePictureOfThisTrip(String path) {
        tripDaoDatabase.updatePath(Objects.requireNonNull(_tripDetailMutableLiveData.getValue()).uid, path);
    }

    public void storeExpenseToTrip(ExpenseModel expenseModel) {
        expenseDaoDatabase.insertExpense(expenseModel);
    }

    public void deleteTrip() {
        TripModel tripModelNeedDeleted = tripDaoDatabase.getTrip(tripId);
        tripDaoDatabase.deleteTrip(tripModelNeedDeleted);
    }

    public void getExpensesList() {
        List<ExpenseModel> listExpenseModel = expenseDaoDatabase.getExpenses(tripId);
        _tripExpensesMutableLiveData.setValue(listExpenseModel);
    }

    public void updateSelectedTrip(String tripName,
                                   String destination,
                                   String dateTrip,
                                   String risk,
                                   String description) {
        tripDaoDatabase.updateSelectedTrip(
                Objects.requireNonNull(_tripDetailMutableLiveData.getValue()).uid,
                tripName,
                destination,
                dateTrip,
                risk,
                description
        );
    }
}
