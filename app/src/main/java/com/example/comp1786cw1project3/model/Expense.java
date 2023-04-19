package com.example.comp1786cw1project3.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Expense")
public class Expense {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "trip_id")
    public String tripId;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "amount")
    public String amount;

    @ColumnInfo(name = "time")
    public String time;

    @ColumnInfo(name = "comment")
    public String comment;

    public Expense(String tripId, String type, String amount, String time, String comment) {
        this.tripId = tripId;
        this.type = type;
        this.amount = amount;
        this.time = time;
        this.comment = comment;
    }
}
