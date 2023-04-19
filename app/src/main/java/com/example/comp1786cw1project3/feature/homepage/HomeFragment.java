package com.example.comp1786cw1project3.feature.homepage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.comp1786cw1project3.databinding.FragmentHomeBinding;
import com.example.comp1786cw1project3.feature.add_trip.AddTripFragment;
import com.example.comp1786cw1project3.feature.base.BaseFragment;
import com.example.comp1786cw1project3.feature.homepage.adapter.TripAdapter;
import com.example.comp1786cw1project3.feature.trip_detail.TripDetailFragment;
import com.example.comp1786cw1project3.model.Trip;
import com.example.comp1786cw1project3.util.listener.ItemTripClickListener;

import java.util.ArrayList;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> implements ItemTripClickListener {
    private HomeViewModel viewModel;
    private TripAdapter tripAdapter;
    private ArrayList<Trip> trips = new ArrayList<>();
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected HomeViewModel viewModel() {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        return viewModel;
    }

    @Override
    public FragmentHomeBinding onCreateViewBinding(LayoutInflater inflater) {
        return FragmentHomeBinding.inflate(inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initViewModel();

        onBackPressedCallBack();
    }

    private void initView() {
        tripAdapter = new TripAdapter(requireContext(), trips, this);
        viewBinding.rvTrips.setAdapter(tripAdapter);


        viewBinding.btnAddNewTrip.setOnClickListener(v -> navigate(AddTripFragment.newInstance(), false));
        viewBinding.btnSearch.setOnClickListener(v -> {showSearch();});
        viewBinding.btnClearSearch.setOnClickListener(v -> {
            clearSearch();
        });
        viewBinding.btnReset.setOnClickListener(v -> {
            resetDatabase();
        });
    }

    private void showSearch() {
        viewBinding.lnSearch.setVisibility(View.VISIBLE);
        viewBinding.edtSearch.setOnEditorActionListener((v1, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String key = Objects.requireNonNull(viewBinding.edtSearch.getText()).toString();
                viewModel.searchTrip(key);
                return false;
            }
            return false;
        });
    }

    private void clearSearch() {
        viewBinding.edtSearch.getText().clear();
        viewBinding.lnSearch.setVisibility(View.GONE);
        viewModel.getTrips();
    }

    private void resetDatabase() {
        viewModel.resetDatabase();
    }


    private void initViewModel() {
        viewModel.getTrips();
        viewModel.trips.observe(getViewLifecycleOwner(), tripsNew -> {
            if (tripsNew.isEmpty()) {
                Toast.makeText(requireContext(), "No trip", Toast.LENGTH_SHORT).show();
            }
            trips.clear();
            trips.addAll(tripsNew);
            tripAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onItemClicked(String tripId) {
        navigate(TripDetailFragment.newInstance(tripId), false);
    }


    private void onBackPressedCallBack() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                //Do nothing
            }
        });
    }

}