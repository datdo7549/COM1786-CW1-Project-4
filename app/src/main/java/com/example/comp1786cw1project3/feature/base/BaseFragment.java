package com.example.comp1786cw1project3.feature.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.example.comp1786cw1project3.BaseActivity;
import com.tunjid.androidx.navigation.StackNavigator;

public abstract class BaseFragment<VB, VM> extends Fragment {
    private StackNavigator navigator;

    public VB viewBinding;
    protected abstract VM viewModel();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel();
        BaseActivity baseActivity = (BaseActivity) requireActivity();
        navigator = baseActivity.getStackNavigator();
    }

    abstract public VB onCreateViewBinding(LayoutInflater inflater);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewBinding = onCreateViewBinding(inflater);
        ViewBinding a = (ViewBinding) viewBinding;
        a.getRoot();
        return a.getRoot();
    }

    public void navigate(Fragment fragment, Boolean clearAll) {
        if (clearAll) {
            navigator.clear(null, true);
        }
        navigator.push(fragment, fragment.getClass().getName());
    }

    public void navigateUp(String upToTag, Boolean includeMatch) {
        navigator.clear(upToTag, includeMatch);
    }

    public void navigateUp() {
        navigator.pop();
    }
}
