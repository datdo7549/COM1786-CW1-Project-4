package com.example.comp1786cw1project3.local.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.comp1786cw1project3.model.ExpenseModel;
import com.example.comp1786cw1project3.model.TripModel;

@Database(entities = {TripModel.class, ExpenseModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TripDao tripDao();
    public abstract ExpenseDao expenseDao();
}