package com.example.comp1786cw1project3.feature.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.comp1786cw1project3.databinding.FragmentTripDetailBinding;
import com.example.comp1786cw1project3.feature.viewModel.TripDetailViewModel;
import com.example.comp1786cw1project3.feature.trip_detail.adapter.ExpenseAdapter;
import com.example.comp1786cw1project3.model.ExpenseModel;
import com.example.comp1786cw1project3.util.listener.AddExpenseDialogListener;
import com.example.comp1786cw1project3.util.views.AddExpenseDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class TripDetailFragment extends BaseFragment<FragmentTripDetailBinding, TripDetailViewModel> implements AddExpenseDialogListener {
    private static final String KEY_TRIP_ID = "KEY_TRIP_ID";

    private TripDetailViewModel tripDetailViewModel;

    private ExpenseAdapter listExpenseAdapter;

    private ArrayList<ExpenseModel> expens = new ArrayList<>();

    public static TripDetailFragment newInstance(String tripId) {
        Bundle args = new Bundle();
        args.putString(KEY_TRIP_ID, tripId);
        TripDetailFragment fragment = new TripDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected TripDetailViewModel viewModel() {
        tripDetailViewModel = new ViewModelProvider(this).get(TripDetailViewModel.class);
        return tripDetailViewModel;
    }

    @Override
    public FragmentTripDetailBinding onCreateViewBinding(LayoutInflater inflater) {
        return FragmentTripDetailBinding.inflate(inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tripDetailViewModel.tripId = getArguments().getString(KEY_TRIP_ID, "");

        initRecyclerViewExpenseList();
        initButton();

        tripDetailViewModel.getTripDetailFromDatabase();
        tripDetailViewModel.getExpensesList();

        observeViewModel();
    }

    private void initRecyclerViewExpenseList() {
        listExpenseAdapter = new ExpenseAdapter(expens);
        viewBinding.rvExpense.setAdapter(listExpenseAdapter);
    }

    private void initButton() {
        viewBinding.btnAddExpense.setOnClickListener(v -> {
            AddExpenseDialog addExpenseDialog = new AddExpenseDialog(tripDetailViewModel.tripId, this);
            addExpenseDialog.setCancelable(false);
            addExpenseDialog.show(getParentFragmentManager(), TripDetailFragment.class.getName());
        });

        viewBinding.btnTakePicture.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1);
        });

        viewBinding.btnDeleteTrip.setOnClickListener(v -> {
            tripDetailViewModel.deleteTrip();
            navigateUp();
        });

        viewBinding.btnEditTrip.setOnClickListener(v -> {
            tripDetailViewModel.updateSelectedTrip(
                    viewBinding.edtTripName.getText().toString(),
                    viewBinding.edtDestination.getText().toString(),
                    viewBinding.edtDateOfTrip.getText().toString(),
                    viewBinding.cbRequiredRisk.isChecked() ? "Yes" : "No",
                    viewBinding.edtDescription.getText().toString()
            );
        });
    }

    private void observeViewModel() {
        tripDetailViewModel.tripDetailLiveData.observe(getViewLifecycleOwner(), trip -> {
            viewBinding.edtTripName.setText(trip.tripName);
            viewBinding.edtDestination.setText(trip.destination);
            viewBinding.edtDateOfTrip.setText(trip.dateTrip);
            viewBinding.edtDescription.setText(trip.description);
            viewBinding.cbRequiredRisk.setChecked(trip.risk.equals("Yes"));

            if (trip.path == null) {
                return;
            }

            if (trip.path.isEmpty()) {
                viewBinding.image.setVisibility(View.GONE);
            } else {
                viewBinding.image.setVisibility(View.VISIBLE);

                File file = new File(trip.path);
                if (file.exists()) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    viewBinding.image.setImageBitmap(bitmap);
                }

            }
        });

        tripDetailViewModel.tripExpenseLiveData.observe(getViewLifecycleOwner(), expensesNew -> {
            if (expensesNew.isEmpty()) {
                viewBinding.rvExpense.setVisibility(View.GONE);
                return;
            }
            viewBinding.rvExpense.setVisibility(View.VISIBLE);
            expens.clear();
            expens.addAll(expensesNew);
            listExpenseAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            String filePath = getRealPathFromURI(uri);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), uri);
                viewBinding.image.setVisibility(View.VISIBLE);
                viewBinding.image.setImageBitmap(bitmap);
                tripDetailViewModel.updatePictureOfThisTrip(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onAddClicked(ExpenseModel expenseModel) {
        tripDetailViewModel.storeExpenseToTrip(expenseModel);
        tripDetailViewModel.getExpensesList();

    }

    private String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = requireContext().getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
        cursor.close();
        return filePath;
    }
}