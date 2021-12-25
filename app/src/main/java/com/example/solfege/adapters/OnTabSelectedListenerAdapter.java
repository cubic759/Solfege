package com.example.solfege.adapters;

import android.widget.ListView;

import com.example.solfege.SettingFragment;
import com.google.android.material.tabs.TabLayout;

public class OnTabSelectedListenerAdapter implements TabLayout.OnTabSelectedListener {
    SettingFragment SSFragment, ETFragment;

    public OnTabSelectedListenerAdapter(SettingFragment SSFragment, SettingFragment ETFragment) {
        this.SSFragment = SSFragment;
        this.ETFragment = ETFragment;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getPosition() == 0) {
            ListView listView = ETFragment.getListView();
            if (listView != null) {
                listView.setNestedScrollingEnabled(false);
            }
            listView = SSFragment.getListView();
            if (listView != null) {
                listView.setNestedScrollingEnabled(true);
            }
        } else {
            ListView listView = SSFragment.getListView();
            if (listView != null) {
                listView.setNestedScrollingEnabled(false);
            }
            listView = ETFragment.getListView();
            if (listView != null) {
                listView.setNestedScrollingEnabled(true);
            }
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}