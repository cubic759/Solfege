package com.example.solfege;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.solfege.adapters.ContentPagerAdapter;
import com.example.solfege.adapters.OnTabSelectedListenerAdapter;
import com.example.solfege.constants.Mode;
import com.example.solfege.constants.Type;
import com.example.solfege.models.Settings;
import com.example.solfege.models.UIControls;
import com.example.solfege.utils.SystemInfo;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class SettingDialogFragment extends BottomSheetDialogFragment {
    private Settings[] savedSettings;
    private BottomSheetBehavior<FrameLayout> behavior;
    private DoingTestActivity activity;
    private Mode testMode;
    private Type testType;
    private UIControls uiControls;

    //region Initialization
    public SettingDialogFragment() {
    }

    //Conductor: Get activity to access the context and layout.
    public SettingDialogFragment(DoingTestActivity activity, Type testType) {
        this.activity = activity;
        this.testType = testType;
        savedSettings = new Settings[]{new Settings(testType, Mode.SIGHT_SINGING),
                new Settings(testType, Mode.EAR_TRAINING)};
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new BottomSheetDialog(requireContext(), R.style.SettingDialog);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View settingDialogView = inflater.inflate(R.layout.dialog_setting, container, false);

        //Set tabLayout
        SettingFragment SSFragment = new SettingFragment(testType, Mode.SIGHT_SINGING, savedSettings[0]);
        SettingFragment ETFragment = new SettingFragment(testType, Mode.EAR_TRAINING, savedSettings[1]);
        List<SettingFragment> tabFragments = new ArrayList<>();
        tabFragments.add(SSFragment);
        tabFragments.add(ETFragment);
        ViewPager viewPager = settingDialogView.findViewById(R.id.viewPage);
        ContentPagerAdapter contentAdapter = new ContentPagerAdapter(getChildFragmentManager(), tabFragments, getContext());
        viewPager.setAdapter(contentAdapter);
        TabLayout tabLayout = settingDialogView.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new OnTabSelectedListenerAdapter(SSFragment, ETFragment) {
        });//To set nestScrolling

        Button applyBtn = settingDialogView.findViewById(R.id.applyBtn);
        applyBtn.setOnClickListener(v -> {

            testMode = Mode.values()[tabLayout.getSelectedTabPosition()];
            savedSettings[0] = SSFragment.getSettings();
            savedSettings[1] = ETFragment.getSettings();
            Settings settings = (testMode == Mode.SIGHT_SINGING) ? savedSettings[0] : savedSettings[1];
            if (!settings.isValid()) {
                showAlertDialog();
            } else {
                settings.setUninitiated(false);
                if (uiControls != null) {
                    uiControls.destroyDialogs();
                }
                uiControls = new UIControls(testType, testMode, settings, activity);
                uiControls.create();
                dismiss();
            }
        });
        return settingDialogView;
    }

    @Override
    public void onStart() {
        super.onStart();
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        assert dialog != null;
        FrameLayout bottomSheet = dialog.getDelegate().findViewById(R.id.design_bottom_sheet);
        if (bottomSheet != null) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams();
            layoutParams.height = SystemInfo.getHeight(requireContext());//Set the max height.
            behavior = BottomSheetBehavior.from(bottomSheet);
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);//Starts with collapsed status.
        }
    }

    private void showAlertDialog() {
        new AlertDialog.Builder(activity)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.invalidSelection)
                .setMessage(R.string.alertInfo)
                .show();
    }

    public UIControls getUiControls() {
        return uiControls;
    }

    public BottomSheetBehavior<FrameLayout> getBehavior() {
        return behavior;
    }
}