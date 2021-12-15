package com.example.solfege.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;

import com.example.solfege.R;
import com.example.solfege.adapters.MyWheelAdapter;
import com.example.solfege.constants.DialogType;
import com.example.solfege.constants.Durations;
import com.example.solfege.constants.Intervals;
import com.example.solfege.constants.Notes;
import com.example.solfege.constants.Scales;
import com.example.solfege.external.wheelview.view.WheelView;
import com.example.solfege.utils.SystemInfo;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AnswerPickerDialog extends DialogFragment {
    private BottomSheetBehavior<FrameLayout> behavior;
    private DialogType dialogType;
    private int expected;
    private LinearLayout linearLayout;
    private ConstraintLayout layout;
    private String text;
    private byte[] list;
    private int index;
    private int currentIndex;//the current index of picker

    public AnswerPickerDialog() {
    }

    public AnswerPickerDialog(DialogType dialogType) {
        this.dialogType = dialogType;
    }

    public AnswerPickerDialog(DialogType dialogType, LinearLayout linearLayout) {
        this.dialogType = dialogType;
        this.linearLayout = linearLayout;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog dialog = new BottomSheetDialog(Objects.requireNonNull(getContext()), R.style.SettingDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dialog_picker, container, false);
        layout = view.findViewById(R.id.constraintLayout4);
        TextView titleView = view.findViewById(R.id.textView4);
        titleView.setText(text);

        WheelView picker = view.findViewById(R.id.picker);
        List<String> list;
        if (dialogType == DialogType.Intervals) {
            list = createIntervalsList();
        } else if (dialogType == DialogType.Notes) {
            list = createNotesList();
        } else if (dialogType == DialogType.Durations) {
            list = createDurationsList();
        } else if (dialogType == DialogType.Scales) {
            list = createScalesList();
        } else {
            list = new ArrayList<>();
        }
        picker.setAdapter(MyWheelAdapter.getAdapter(list));
        picker.setCurrentItem(0);

        Button checkBtn = view.findViewById(R.id.checkBtn);
        checkBtn.setOnClickListener(v -> {
            currentIndex = picker.getCurrentItem();
            String answer = (String) picker.getAdapter().getItem(currentIndex);//Get Answer
            checkAnswer(answer, checkBtn);//Show if the answer is correct
            playAnimation(checkBtn);
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

    private void checkAnswer(String answer, Button checkBtn) {
        Button outputBtn = (Button) linearLayout.getChildAt(1);
        TextView checkChar = (TextView) linearLayout.getChildAt(2);
        if (compareAnswer()) {
            list[index] = 1;
            outputBtn.setText(answer);
            checkBtn.setText(R.string.correct);
            checkBtn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.correct_button, null));
            checkChar.setText(getString(R.string.correct_char));
        } else {
            list[index] = -1;
            outputBtn.setText(answer);
            checkBtn.setText(R.string.wrong);
            checkBtn.setTextColor(Color.WHITE);
            checkBtn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.wrong_button, null));
            checkChar.setText(String.format(getString(R.string.wrong_char), getExpectedAnswer()));
        }
    }

    private Boolean compareAnswer() {//TODO:melodic scales
        if (dialogType == DialogType.Intervals) {
            return expected == currentIndex;
        } else if (dialogType == DialogType.Notes) {
            return expected % 12 == currentIndex;
        } else if (dialogType == DialogType.Durations) {
            return expected == currentIndex;
        } else if (dialogType == DialogType.Scales) {
            return expected == currentIndex;
        }
        throw new IllegalStateException("No such dialog type");
    }

    private String getExpectedAnswer() {
        if (dialogType == DialogType.Intervals) {
            return Intervals.getIntervals(Objects.requireNonNull(getContext()))[expected];
        } else if (dialogType == DialogType.Notes) {
            return Notes.getNote(expected);
        } else if (dialogType == DialogType.Durations) {
            return Durations.getDurations(Objects.requireNonNull(getContext()))[expected];
        }else if (dialogType == DialogType.Scales) {
            return Scales.getScales(Objects.requireNonNull(getContext()))[expected];
        }
        throw new IllegalStateException("No such dialog type");
    }

    private void playAnimation(Button checkBtn) {
        ValueAnimator expandAnimator = ValueAnimator
                .ofInt(checkBtn.getWidth(), checkBtn.getWidth() + SystemInfo.getWidth(Objects.requireNonNull(getContext())))
                .setDuration(1000);
        expandAnimator.addUpdateListener(animation -> {
            checkBtn.getLayoutParams().width = (Integer) animation.getAnimatedValue();
            checkBtn.requestLayout();
        });
        ValueAnimator translateAnimator = ValueAnimator.ofFloat(0.918f, 0.5f).setDuration(1000);
        translateAnimator.addUpdateListener(animation -> {
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(layout);
            constraintSet.setHorizontalBias(R.id.checkBtn, (Float) animation.getAnimatedValue());
            constraintSet.applyTo(layout);
        });
        AnimatorSet set = new AnimatorSet();
        set.playTogether(expandAnimator, translateAnimator);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                dismiss();//Close when animation ends.
            }
        });
        set.start();
    }

    private List<String> createDurationsList() {
        return Arrays.asList(Durations.getDurations(Objects.requireNonNull(getContext())));
    }

    private List<String> createIntervalsList() {
        return Arrays.asList(Intervals.getIntervals(Objects.requireNonNull(getContext())));
    }

    private List<String> createScalesList() {
        return Arrays.asList(Scales.getScales(Objects.requireNonNull(getContext())));
    }

    private List<String> createNotesList() {
        return Arrays.asList(Notes.NOTES);
    }

    public BottomSheetBehavior<FrameLayout> getBehavior() {
        return behavior;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setExpected(int expected) {
        this.expected = expected;
    }

    public void setRecordList(byte[] list) {
        this.list = list;
    }

    public void setRecordListIndex(int index) {
        this.index = index;
    }

    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }
}