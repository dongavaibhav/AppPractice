package com.example.fullapppractice.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fullapppractice.R;
import com.example.fullapppractice.activity.HomeActivity;

public class EditFragment extends Fragment {

    static EditFragment editFragment;
    boolean isFromDealSearchResult;
    String dealDetail;

    public EditFragment() {
        // Required empty public constructor
    }

    public static EditFragment getInstance() {
        return editFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editFragment = this;
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeActivity) getActivity()).DealClick();
        isFromDealSearchResult = getArguments().getBoolean("isFromDealSearchResult");
        dealDetail = getArguments().getString("dealDetail");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false);

    }
}
