package com.example.comp1786cw1project3.feature.trip_detail.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comp1786cw1project3.R;
import com.example.comp1786cw1project3.model.Expense;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {

    private ArrayList<Expense> mData;

    public ExpenseAdapter(ArrayList<Expense> data) {
        this.mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expense, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Expense item = mData.get(position);

        holder.type.setText(item.type);
        holder.amount.setText(item.amount);
        holder.time.setText(item.time);
        holder.comment.setText(item.comment);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView type;
        public AppCompatTextView amount;
        public AppCompatTextView time;
        public AppCompatTextView comment;

        public ViewHolder(View view) {
            super(view);
            type = view.findViewById(R.id.tvExpenseType);
            amount = view.findViewById(R.id.tvExpenseAmount);
            time = view.findViewById(R.id.tvExpenseTime);
            comment = view.findViewById(R.id.tvExpenseComment);
        }
    }
}
