package com.example.comp1786cw1project3.local.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.comp1786cw1project3.model.Expense;
import com.example.comp1786cw1project3.model.Trip;

@Database(entities = {Trip.class, Expense.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TripDao tripDao();
    public abstract ExpenseDao expenseDao();
}