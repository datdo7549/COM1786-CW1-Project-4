package com.example.comp1786cw1project3.feature.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.comp1786cw1project3.databinding.FragmentHomeBinding;
import com.example.comp1786cw1project3.feature.viewModel.HomePageViewModel;
import com.example.comp1786cw1project3.feature.homepage.adapter.TripAdapter;
import com.example.comp1786cw1project3.model.TripModel;
import com.example.comp1786cw1project3.util.listener.ItemTripClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomePageViewModel> implements ItemTripClickListener {
    private TripAdapter listTripAdapter;
    private HomePageViewModel homePageViewModel;
    private final ArrayList<TripModel> tripModels = new ArrayList<>();
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected HomePageViewModel viewModel() {
        homePageViewModel = new ViewModelProvider(this).get(HomePageViewModel.class);
        return homePageViewModel;
    }

    @Override
    public FragmentHomeBinding onCreateViewBinding(LayoutInflater inflater) {
        return FragmentHomeBinding.inflate(inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onBackPressedCallBack();
        initView();
        initViewModel();
    }

    private void initView() {
        initRecyclerView();
        initButton();
    }

    private void initViewModel() {
        getNewListTrip();
        observeListTrip();
    }

    private void initRecyclerView() {
        listTripAdapter = new TripAdapter(requireContext(), tripModels, this);
        viewBinding.recyclerViewTripList.setAdapter(listTripAdapter);
    }

    private void initButton() {
        viewBinding.btnAddNewTrip.setOnClickListener(v -> goToAddNewTripScreen());
        viewBinding.btnSearch.setOnClickListener(v -> {showSearch();});
        viewBinding.btnClearSearch.setOnClickListener(v -> {
            clearSearch();
        });
        viewBinding.btnClearDatabase.setOnClickListener(v -> {
            resetDatabase();
        });
    }

    private void showSearch() {
        viewBinding.linearLayoutSearch.setVisibility(View.VISIBLE);
        viewBinding.edtSearch.setOnEditorActionListener((v1, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String key = Objects.requireNonNull(viewBinding.edtSearch.getText()).toString();
                homePageViewModel.searchTripByName(key);
                return false;
            }
            return false;
        });
    }

    private void clearSearch() {
        viewBinding.edtSearch.getText().clear();
        viewBinding.linearLayoutSearch.setVisibility(View.GONE);
        homePageViewModel.getListTripsFromDatabase();
    }

    private void resetDatabase() {
        homePageViewModel.deleteAllTripFromDatabase();
    }

    private void goToAddNewTripScreen() {
        navigate(AddTripFragment.newInstance(), false);
    }


    private void getNewListTrip() {
        homePageViewModel.getListTripsFromDatabase();
    }

    private void observeListTrip() {
        homePageViewModel.tripsLiveData.observe(getViewLifecycleOwner(), tripsNew -> {
            if (tripsNew.isEmpty()) {
                Log.d("TAG", "observeListTrip: No trips");
            }
            updateListTripData(tripsNew);
        });
    }

    private void updateListTripData(List<TripModel> tripsNew) {
        tripModels.clear();
        tripModels.addAll(tripsNew);
        listTripAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(String tripId) {
        navigate(TripDetailFragment.newInstance(tripId), false);
    }


    private void onBackPressedCallBack() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
            }
        });
    }

}