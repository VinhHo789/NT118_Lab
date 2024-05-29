package com.example.lab3_bt2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PageFragment extends Fragment {

    private int pageNumber;

    // Constructor to pass the page number
    public PageFragment(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    // Rest of your class remains unchanged
    // ...
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save any critical data to outState
        outState.putInt("pageNumber", pageNumber);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView;
        if (savedInstanceState != null) {
            pageNumber = savedInstanceState.getInt("pageNumber");
        }
        // Check the position to determine the page
        if (pageNumber == 0) {
            rootView = inflater.inflate(R.layout.fragment_page_1, container, false);

        } else {
            rootView = inflater.inflate(R.layout.fragment_page_2, container, false);

        }

        return rootView;
    }
}
