package com.example.solfege.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.solfege.R;
import com.example.solfege.SettingFragment;

import java.util.List;

public class ContentPagerAdapter extends FragmentPagerAdapter {

    private final List<SettingFragment> tabFragments;
    private final Context context;

    public ContentPagerAdapter(FragmentManager fm, List<SettingFragment> tabFragments, Context context) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.tabFragments = tabFragments;
        this.context=context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return tabFragments.get(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence title;
        if (position == 0) {
            title = context.getString(R.string.sightSingingText);
        } else {
            title = context.getString(R.string.earTrainingText);
        }
        return title;
    }
}