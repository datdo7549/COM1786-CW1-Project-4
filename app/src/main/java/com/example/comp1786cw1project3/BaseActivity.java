package com.example.comp1786cw1project3;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.tunjid.androidx.navigation.StackNavigator;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public abstract class BaseActivity extends AppCompatActivity {
    StackNavigator stackNavigator;
    abstract public Fragment startDestination();

    public StackNavigator getStackNavigator() {
        return stackNavigator;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stackNavigator = new StackNavigator(getSupportFragmentManager(), R.id.navRootHost);
        if (savedInstanceState == null) {
            Fragment rootFragment = startDestination();
            stackNavigator.push(rootFragment, rootFragment.getClass().getName());
        }
    }
}

