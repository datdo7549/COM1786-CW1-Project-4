package com.example.comp1786cw1project3.util.views;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.comp1786cw1project3.databinding.AddExpenseDialogBinding;
import com.example.comp1786cw1project3.model.Expense;
import com.example.comp1786cw1project3.util.listener.AddExpenseDialogListener;

import java.util.Calendar;
import java.util.Objects;

public class AddExpenseDialog extends DialogFragment {
    private AddExpenseDialogBinding binding;
    private String tripId;
    private AddExpenseDialogListener expenseDialogListener;

    public AddExpenseDialog(String tripId, AddExpenseDialogListener listener) {
        this.tripId = tripId;
        this.expenseDialogListener = listener;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getDialog() != null) {
            Window window = getDialog().getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setAttributes(params);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for the dialog
        binding = AddExpenseDialogBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.edtExpenseTime.setOnClickListener(v -> {
            showDatePickerDialog();
        });

        binding.btnCancel.setOnClickListener(v -> {
            dismiss();
        });

        binding.btnAddExpense.setOnClickListener(v -> {
            String type = Objects.requireNonNull(binding.edtExpenseType.getText()).toString();
            String amount = Objects.requireNonNull(binding.edtExpenseAmount.getText()).toString();
            String time = Objects.requireNonNull(binding.edtExpenseTime.getText()).toString();
            String comment = Objects.requireNonNull(binding.edtExpenseComment.getText()).toString();

            if (type.isEmpty() || amount.isEmpty() || time.isEmpty() || comment.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill to required field", Toast.LENGTH_SHORT).show();
            } else {
                dismiss();
                Expense expense = new Expense(tripId, type, amount, time, comment);
                expenseDialogListener.onAddClicked(expense);
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year, month, dayOfMonth) -> {
                    // Handle the selected date
                    String selectedDate = year + "/" + (month+1) + "/" + dayOfMonth;
                    binding.edtExpenseTime.setText(selectedDate);
                },
                // Set the initial date to the current date
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
}