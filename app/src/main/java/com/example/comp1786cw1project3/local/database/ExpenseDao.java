package com.example.comp1786cw1project3.local.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.comp1786cw1project3.model.Expense;

import java.util.List;

@Dao
public interface ExpenseDao {
    @Insert
    void insertExpense(Expense expense);

    @Query("SELECT * FROM expense WHERE trip_id like :tripId")
    List<Expense> getExpenses(String tripId);

    @Query("DELETE FROM expense")
    void deleteAllExpense();
}
