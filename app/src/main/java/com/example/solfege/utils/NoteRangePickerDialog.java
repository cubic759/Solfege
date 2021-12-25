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
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;

import com.example.solfege.R;
import com.example.solfege.adapters.MyWheelAdapter;
import com.example.solfege.constants.Note;
import com.example.solfege.external.wheelview.view.WheelView;
import com.example.solfege.models.Settings;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Arrays;
import java.util.List;

public class NoteRangePickerDialog extends DialogFragment {
    private final List<String> notesList = Arrays.asList(Note.NOTES_LIST);
    private BottomSheetBehavior<FrameLayout> behavior;
    private TextView setText;
    private Settings settings;
    private WheelView lowestPicker, highestPicker;
    private int highestPrevious = 0;//use to calculate offset
    private int[] range;
    private String title;

    public NoteRangePickerDialog() {
    }

    public NoteRangePickerDialog(TextView setText, Settings settings, String title) {
        this.setText = setText;
        this.settings = settings;
        this.title = title;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext(), R.style.SettingDialog);
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
        lowestPicker = view.findViewById(R.id.lowest);
        highestPicker = view.findViewById(R.id.highest);
        initiateChooseRangePickers();

        Button okBtn = view.findViewById(R.id.okBtn);
        okBtn.setOnClickListener(v -> {
            range = getSelectedRange();
            settings.setNoteRangeTemp(range);
            setRangeText(getRangeString());
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

    public BottomSheetBehavior<FrameLayout> getBehavior() {
        return behavior;
    }

    public int[] getRange() {
        if (lowestPicker == null || highestPicker == null) {
            return settings.getNoteRange();
        } else {
            return range;
        }
    }

    public String[] getRangeString() {
        String[] rangeString = new String[2];
        if (lowestPicker == null || highestPicker == null) {
            rangeString[0] = Note.NOTES_LIST[range[0]];
            rangeString[1] = Note.NOTES_LIST[range[1]];
        } else {
            rangeString[0] = (String) lowestPicker.getAdapter().getItem(lowestPicker.getCurrentItem());
            rangeString[1] = (String) highestPicker.getAdapter().getItem(highestPicker.getCurrentItem());
        }
        return rangeString;
    }

    public void setRangeText(String[] rangeString) {
        if (!rangeString[0].equals(rangeString[1])) {
            setText.setText(String.format(getString(R.string.fromTo), rangeString[0], rangeString[1]));
        } else {
            setText.setText(String.format(getString(R.string.note), rangeString[0]));
        }
    }

    private void initiateChooseRangePickers() {
        lowestPicker.setAdapter(MyWheelAdapter.getAdapter(notesList.subList(range[1], Note.NOTES_LIST_LENGTH)));
        lowestPicker.setCyclic(false);
        lowestPicker.setCurrentItem(range[0] - range[1]);//87-range[1]

        highestPicker.setAdapter(MyWheelAdapter.getAdapter(notesList.subList(0, range[0] + 1)));
        highestPicker.setCyclic(false);
        highestPicker.setCurrentItem(range[1]);
        highestPrevious = range[1];

        lowestPicker.setOnItemSelectedListener(i -> {
            int highCurrent = highestPicker.getCurrentItem();
            if (i + highCurrent < Note.NOTES_LIST_LENGTH) {//Exclude extreme situations
                highestPicker.setAdapter(MyWheelAdapter.getAdapter(notesList.subList(0, i + highCurrent + 1)));//(<=x) +1
                highestPicker.setCurrentItem(highCurrent);
            }
        });

        highestPicker.setOnItemSelectedListener(i -> {
            int lowCurrent = lowestPicker.getCurrentItem();
            lowestPicker.setAdapter(MyWheelAdapter.getAdapter(notesList.subList(i, Note.NOTES_LIST_LENGTH)));
            lowestPicker.setCurrentItem(lowCurrent + (highestPrevious - i));
            highestPrevious = i;
        });
    }

    public void setRange(int[] range) {
        this.range = range;
    }

    public int[] getSelectedRange() {
        int[] range = new int[2];
        boolean lowOK = false, highOK = false;
        int highest = highestPicker.getCurrentItem();
        int lowest = lowestPicker.getCurrentItem() + highest;
        for (int i = 0; i < Note.NOTES_LIST_LENGTH; i++) {
            if (!highOK && highest == i) {
                highOK = true;
                range[1] = i;
            }
            if (!lowOK && lowest == i) {
                lowOK = true;
                range[0] = i;
            }
            if (highOK && lowOK) {
                break;
            }
        }
        return range;
    }
}