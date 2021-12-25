package com.example.solfege;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.solfege.constants.Mode;
import com.example.solfege.constants.Type;
import com.example.solfege.models.SettingControls;
import com.example.solfege.models.Settings;

public class SettingFragment extends Fragment {
    private Type testType;
    private Mode testMode;
    private Settings settings;
    private SettingControls settingControls;
    private ListView listView;

    public SettingFragment() {
        super();
    }

    public SettingFragment(Type testType, Mode testMode, Settings settings) {
        super();
        this.testType = testType;
        this.testMode = testMode;
        this.settings = settings;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View settingView = inflater.inflate(R.layout.fragment_setting, container, false);
        //Add setting controls
        listView = settingView.findViewById(R.id.settingList);
        if (android.os.Build.VERSION.SDK_INT <= 27) {
            listView.setNestedScrollingEnabled(true);
        }
        settingControls = new SettingControls(testType, testMode, settings, this, listView);
        settingControls.create();
        return settingView;
    }

    public ListView getListView() {
        return listView;
    }

    public Settings getSettings() {
        return settingControls.getSettings();//Give to setting dialog to save it.
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (isAdded()) {
            settingControls.destroyAllDialogs();
        }
    }
}