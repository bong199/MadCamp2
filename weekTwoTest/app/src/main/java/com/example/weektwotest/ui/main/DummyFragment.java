package com.example.weektwotest.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.weektwotest.R;
import com.example.weektwotest.ui.main.GalleryFragment;

public class DummyFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static DummyFragment newInstance(int index) {
        DummyFragment fragment = new DummyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dummy, container, false);
    }
}