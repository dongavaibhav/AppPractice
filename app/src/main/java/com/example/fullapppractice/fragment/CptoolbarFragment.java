package com.example.fullapppractice.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fullapppractice.R;
import com.example.fullapppractice.adapter.ToolPagerAdapter;
import com.google.android.material.tabs.TabLayout;


public class CptoolbarFragment extends Fragment {

    Toolbar toolbar;
    TabLayout tabs;
    ViewPager pager;
    ToolPagerAdapter adapter;
    CharSequence Titles[] = {"RecyclerView","Static data"};

    public CptoolbarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cptoolbar, container, false);

        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        tabs = view.findViewById(R.id.tabs);
        pager = view.findViewById(R.id.pager);

        adapter =  new ToolPagerAdapter(getActivity().getSupportFragmentManager(),Titles,Titles.length);
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);
        setupTabIcons();
        return view;
    }

    private void setupTabIcons() {
        tabs.getTabAt(0).setText("RecyclerView");
        tabs.getTabAt(1).setText("Static data");
    }
}
