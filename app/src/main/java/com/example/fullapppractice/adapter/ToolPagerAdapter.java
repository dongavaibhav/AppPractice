package com.example.fullapppractice.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.fullapppractice.fragment.RecyclerFragment;
import com.example.fullapppractice.fragment.Recyclers;
import com.example.fullapppractice.fragment.StaticFragment;

public class ToolPagerAdapter extends FragmentPagerAdapter {

    CharSequence Titles[];
    int NumbOfTabs;

    public ToolPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }

    @Override
    public Fragment getItem(int position) {

        if(position == 0) {
            Recyclers tab1 = new Recyclers();
            return tab1;
        }
        else {
            StaticFragment tab2 = new StaticFragment();
            return tab2;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}

