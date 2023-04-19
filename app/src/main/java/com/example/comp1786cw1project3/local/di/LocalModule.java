package com.example.comp1786cw1project3.local.di;

import android.content.Context;
import androidx.room.Room;

import com.example.comp1786cw1project3.local.database.AppDatabase;
import com.example.comp1786cw1project3.local.database.ExpenseDao;
import com.example.comp1786cw1project3.local.database.TripDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class LocalModule {

    @Singleton
    @Provides
    public AppDatabase provideAppDatabase(@ApplicationContext  Context context) {
        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                "data"
        ).allowMainThreadQueries().build();
    }

    @Singleton
    @Provides
    public TripDao provideTripDao(AppDatabase appDatabase) {
        return appDatabase.tripDao();
    }

    @Provides
    @Singleton
    public ExpenseDao provideExpenseDao(AppDatabase appDatabase) {
        return appDatabase.expenseDao();
    }
}
