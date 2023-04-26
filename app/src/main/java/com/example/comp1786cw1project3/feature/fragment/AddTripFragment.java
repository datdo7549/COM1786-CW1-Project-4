package com.example.comp1786cw1project3.feature.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.comp1786cw1project3.databinding.FragmentAddTripBinding;
import com.example.comp1786cw1project3.feature.viewModel.AddNewTripLocalViewModel;

import java.util.Calendar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddTripFragment extends BaseFragment<FragmentAddTripBinding, AddNewTripLocalViewModel> {
    private AddNewTripLocalViewModel addNewTripLocalViewModel;
    public static AddTripFragment newInstance() {
        return new AddTripFragment();
    }

    @Override
    protected AddNewTripLocalViewModel viewModel() {
        addNewTripLocalViewModel = new ViewModelProvider(this).get(AddNewTripLocalViewModel.class);
        return addNewTripLocalViewModel;
    }

    @Override
    public FragmentAddTripBinding onCreateViewBinding(LayoutInflater inflater) {
        return FragmentAddTripBinding.inflate(inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewListeners();
    }

    private void setupViewListeners() {
        viewBinding.edtDateOfTrip.setOnClickListener(v -> showDatePickerDialog());
        viewBinding.btnAddTrip.setOnClickListener(v -> onSaveTripClicked());
    }

    private void onSaveTripClicked() {
        String requiredRisk = viewBinding.cbRequiredRisk.isChecked() ? "Yes" : "No";

        if (viewBinding.edtTripName.getText().toString().trim().isEmpty()
                || viewBinding.edtDestination.getText().toString().trim().isEmpty()
                || viewBinding.edtDateOfTrip.getText().toString().trim().isEmpty()
        ) {
            Toast.makeText(requireContext(), "Input required field", Toast.LENGTH_SHORT).show();
            return;
        }

        addNewTripLocalViewModel.saveTripToLocalDatabase(
                viewBinding.edtTripName.getText().toString().trim(),
                viewBinding.edtDestination.getText().toString().trim(),
                viewBinding.edtDateOfTrip.getText().toString().trim(),
                viewBinding.edtDescription.getText().toString().trim(),
                requiredRisk
        );
        navigateUp();
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year, month, dayOfMonth) -> {
                    setDateOfTripToUi(year, month, dayOfMonth);
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void setDateOfTripToUi(int year, int month, int dayOfMonth) {
        String selectedDate = year + "/" + (month+1) + "/" + dayOfMonth;
        viewBinding.edtDateOfTrip.setText(selectedDate);
    }
}