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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;

import com.example.solfege.R;
import com.example.solfege.adapters.MyWheelAdapter;
import com.example.solfege.constants.Chord;
import com.example.solfege.constants.DialogType;
import com.example.solfege.constants.Duration;
import com.example.solfege.constants.Interval;
import com.example.solfege.constants.Note;
import com.example.solfege.constants.Scale;
import com.example.solfege.external.wheelview.view.WheelView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AnswerPickerDialog extends DialogFragment {
    private BottomSheetBehavior<FrameLayout> behavior;
    private DialogType dialogType;
    private int expected;
    private Button outputButton;
    private TextView outputText;
    private ConstraintLayout layout;
    private String text;
    private byte[] list;
    private int index;
    private int currentIndex;//the current index of picker
    private boolean isAscending;

    public AnswerPickerDialog() {
    }

    public AnswerPickerDialog(DialogType dialogType) {
        this.dialogType = dialogType;
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
        switch (dialogType) {
            case Intervals:
                list = createIntervalsList();
                break;
            case Notes:
                list = createNotesList();
                break;
            case Durations:
                list = createDurationsList();
                break;
            case Scales:
                list = createScalesList();
                break;
            case Chords:
                list = createChordsList();
                break;
            case Degrees:
                list = createDegreesList();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dialogType);
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
        Button outputBtn = this.outputButton;
        if (dialogType != DialogType.Durations) {
            outputBtn.setText(answer);
        } else {
            outputBtn.setText(Duration.DURATION_ANSWERS[currentIndex]);
        }
        if (compareAnswer()) {
            list[index] = 1;
            outputBtn.setBackground(Appearance.getStroke(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.right)));
            checkBtn.setText(R.string.correct);
            checkBtn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.correct_button, null));
        } else {
            list[index] = -1;
            outputBtn.setBackground(Appearance.getStroke(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.wrong)));
            checkBtn.setText(R.string.wrong);
            checkBtn.setTextColor(Color.WHITE);
            checkBtn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.wrong_button, null));
            TextView checkChar = this.outputText;
            checkChar.setText(String.format(getString(R.string.wrong_char), getExpectedAnswer()));
        }
    }

    private Boolean compareAnswer() {
        switch (dialogType) {
            case Intervals:
            case Durations:
            case Chords:
            case Degrees:
                return expected == currentIndex;
            case Notes:
                return expected % 12 == currentIndex;
            case Scales:
                boolean result;
                if (isAscending) {
                    if (expected == 0 || expected == 4) {
                        result = currentIndex == 0 || currentIndex == 4;
                    } else {
                        result = expected == currentIndex;
                    }
                } else {
                    if (expected == 1 || expected == 5) {
                        result = currentIndex == 1 || currentIndex == 5;
                    } else {
                        result = expected == currentIndex;
                    }
                }
                return result;
        }
        throw new IllegalStateException("Unexpected value: " + dialogType);
    }

    private String getExpectedAnswer() {
        switch (dialogType) {
            case Intervals:
                return Interval.getIntervals(Objects.requireNonNull(getContext()))[expected];
            case Notes:
                return Note.getNote(expected);
            case Durations:
                return Duration.DURATION_ANSWERS[expected];
            case Scales:
                return Scale.getScales(Objects.requireNonNull(getContext()))[expected];
            case Chords:
                return Chord.getChords(Objects.requireNonNull(getContext()))[expected];
            case Degrees:
                return Scale.DEGREES[expected];
        }
        throw new IllegalStateException("Unexpected value: " + dialogType);
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
        return Arrays.asList(Duration.getDurations(Objects.requireNonNull(getContext())));
    }

    private List<String> createIntervalsList() {
        return Arrays.asList(Interval.getIntervals(Objects.requireNonNull(getContext())));
    }

    private List<String> createScalesList() {
        return Arrays.asList(Scale.getScales(Objects.requireNonNull(getContext())));
    }

    private List<String> createChordsList() {
        return Arrays.asList(Chord.getChords(Objects.requireNonNull(getContext())));
    }

    private List<String> createDegreesList() {
        return Arrays.asList(Scale.DEGREES);
    }

    private List<String> createNotesList() {
        return Arrays.asList(Note.NOTES);
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

    public void setOutputButton(Button outputButton) {
        this.outputButton = outputButton;
    }

    public void setOutputText(TextView outputText) {
        this.outputText = outputText;
    }

    public void setAscending(int playMode) {
        isAscending = playMode == 0;
    }
}