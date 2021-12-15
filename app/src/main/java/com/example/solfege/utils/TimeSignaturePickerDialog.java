package com.example.solfege.utils;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;

import com.example.solfege.R;
import com.example.solfege.adapters.MyWheelAdapter;
import com.example.solfege.constants.Notes;
import com.example.solfege.constants.TimeSignature;
import com.example.solfege.external.wheelview.view.WheelView;
import com.example.solfege.models.Settings;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TimeSignaturePickerDialog extends DialogFragment {
    private final List<String> notesList = Arrays.asList(Notes.NOTES_LIST);
    private BottomSheetBehavior<FrameLayout> behavior;
    private TextView setText;
    private Settings settings;
    private WheelView lowestPicker, highestPicker;
    private int[] timeSignature;
    private String title;

    public TimeSignaturePickerDialog() {
    }

    public TimeSignaturePickerDialog(TextView setText, Settings settings, String title) {
        this.setText = setText;
        this.settings = settings;
        this.title = title;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog dialog = new BottomSheetDialog(Objects.requireNonNull(getContext()), R.style.SettingDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//Cannot drag
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dialog_range_picker, container, false);
        TextView textView = view.findViewById(R.id.textView3);
        textView.setText(title);

        ConstraintLayout layout = view.findViewById(R.id.constraintLayout5);
        lowestPicker = view.findViewById(R.id.lowest);
        TextView textView1 = view.findViewById(R.id.textView7);
        textView1.setText("/");
        TextView textView2 = view.findViewById(R.id.textView6);
        layout.removeView(textView2);
        highestPicker = view.findViewById(R.id.highest);
        setTimeSignaturePickers();

        Button okBtn = view.findViewById(R.id.okBtn);
        okBtn.setOnClickListener(v -> {
            timeSignature = getSelectedTimeSignature();
            setTimeSignatureText(getTimeSignatureString());
            dismiss();
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        assert dialog != null;
        FrameLayout bottomSheet = dialog.getDelegate().findViewById(R.id.design_bottom_sheet);
        if (bottomSheet != null) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams();
            layoutParams.height = 1000;//Set the max height.
            behavior = BottomSheetBehavior.from(bottomSheet);
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);//Starts with expanded status.
            behavior.setHideable(false);
        }
    }

    private void setTimeSignaturePickers() {
        if (timeSignature[1] == 0) {
            lowestPicker.setAdapter(MyWheelAdapter.getAdapter(Arrays.asList(TimeSignature.LEFT_4)));
        } else {
            lowestPicker.setAdapter(MyWheelAdapter.getAdapter(Arrays.asList(TimeSignature.LEFT_8)));
        }
        lowestPicker.setCyclic(false);
        lowestPicker.setCurrentItem(timeSignature[0]);

        highestPicker.setAdapter(MyWheelAdapter.getAdapter(Arrays.asList(TimeSignature.RIGHT)));
        highestPicker.setCyclic(false);
        highestPicker.setCurrentItem(timeSignature[1]);

        highestPicker.setOnItemSelectedListener(i -> {
            if (i == 1) {
                lowestPicker.setAdapter(MyWheelAdapter.getAdapter(Arrays.asList(TimeSignature.LEFT_8)));
            } else {
                lowestPicker.setAdapter(MyWheelAdapter.getAdapter(Arrays.asList(TimeSignature.LEFT_4)));
            }
            lowestPicker.setCurrentItem(2);
        });
    }

    public BottomSheetBehavior<FrameLayout> getBehavior() {
        return behavior;
    }

    public int[] getTimeSignature() {
        if (lowestPicker == null || highestPicker == null) {
            return settings.getTimeSignature();
        } else {
            return timeSignature;
        }
    }

    public int[] getSelectedTimeSignature() {
        return new int[]{lowestPicker.getCurrentItem(), highestPicker.getCurrentItem()};
    }

    public void setTimeSignature(int[] timeSignature) {
        this.timeSignature = timeSignature;
    }

    public String[] getTimeSignatureString() {
        String[] timeSignatureString = new String[2];
        if (lowestPicker == null || highestPicker == null) {
            if (timeSignature[1] == 1) {
                timeSignatureString[0] = TimeSignature.LEFT_8[timeSignature[0]];
            } else {
                timeSignatureString[0] = TimeSignature.LEFT_4[timeSignature[0]];
            }
            timeSignatureString[1] = TimeSignature.RIGHT[timeSignature[1]];
        } else {
            timeSignatureString[0] = (String) lowestPicker.getAdapter().getItem(lowestPicker.getCurrentItem());
            timeSignatureString[1] = (String) highestPicker.getAdapter().getItem(highestPicker.getCurrentItem());
        }
        return timeSignatureString;
    }

    public void setTimeSignatureText(String[] timeSignatureString) {
        setText.setText(String.format(getString(R.string.timeSignatureText), timeSignatureString[0], timeSignatureString[1]));
    }
}