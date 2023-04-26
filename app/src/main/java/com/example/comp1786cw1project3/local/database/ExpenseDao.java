package com.example.comp1786cw1project3.local.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.comp1786cw1project3.model.ExpenseModel;

import java.util.List;

@Dao
public interface ExpenseDao {
    @Insert
    void insertExpense(ExpenseModel expenseModel);

    @Query("SELECT * FROM Expense WHERE trip_id like :tripId")
    List<ExpenseModel> getExpenses(String tripId);

    @Query("DELETE FROM Expense")
    void deleteAllExpense();
}
