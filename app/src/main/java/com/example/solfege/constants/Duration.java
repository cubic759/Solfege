package com.example.solfege.constants;

import android.content.Context;

import com.example.solfege.R;
import com.example.solfege.external.FM_Score.FM_DurationValue;

import java.util.ArrayList;

public enum Duration {
    WHOLE_NOTE, HALF_NOTE_DOTTED, HALF_NOTE, QUARTER_NOTE_DOTTED, QUARTER_NOTE, EIGHTH_NOTE_DOTTED, EIGHTH_NOTE,
    SIXTEENTH_NOTE_DOTTED, SIXTEENTH_NOTE, THIRTY_SECOND_NOTE;

    public static final int[] CALCULATE_VALUE = {32, 24, 16, 12, 8, 6, 4, 3, 2, 1};
    public static final int[] REAL_VALUES = {100000, 75000, 50000, 37500, 25000, 18750, 12500, 9375, 6250, 3125};
    public static final boolean[] COULD_HAVE_BEAM = {false, false, false, false, false, false, true, false, true, true};
    public static final int[] FM_DURATION = {FM_DurationValue.NOTE_WHOLE, FM_DurationValue.NOTE_HALF_D,
            FM_DurationValue.NOTE_HALF, FM_DurationValue.NOTE_QUARTER_D, FM_DurationValue.NOTE_QUARTER,
            FM_DurationValue.NOTE_EIGHTH_D, FM_DurationValue.NOTE_EIGHTH, FM_DurationValue.NOTE_SIXTEENTH_D,
            FM_DurationValue.NOTE_SIXTEENTH, FM_DurationValue.NOTE_THIRTY_SECOND};
    public static final String wholeNote="\uD834\uDD5D";
    public static final String halfNote="\uD834\uDD5E";
    public static final String quarterNote="\uD834\uDD5F";
    public static final String eighthNote="\uD834\uDD60";
    public static final String sixteenthNote="\uD834\uDD61";
    public static final String thirtySecondNote="\uD834\uDD62";
    public static final String dot="\uD834\uDD6D";
    public static final String[] DURATION_ANSWERS = {wholeNote, halfNote + dot, halfNote,
            quarterNote + dot, quarterNote, eighthNote + dot, eighthNote, sixteenthNote + dot,
            sixteenthNote, thirtySecondNote};

    public static boolean isDotted(int index) {
        return index == 1 || index == 3 || index == 5 || index == 7;
    }

    public static boolean isDurationsValid(ArrayList<Integer> durations) {
        boolean isOk = true;
        int[] dottedDuration = new int[4];
        int count = 0;
        for (int i = 0; i < durations.size(); i++) {
            int duration = durations.get(i);
            if (isDotted(duration)) {
                isOk = false;
                dottedDuration[count] = duration;
                count++;
            } else {
                if (!isOk) {
                    if (CALCULATE_VALUE[dottedDuration[count - 1]] / 3 % CALCULATE_VALUE[duration] == 0) {
                        isOk = true;
                    }
                }
            }
        }
        return isOk;
    }

    public static int getFMIndex(int value) {
        int index = -1;
        for (int i = 0; i < FM_DURATION.length; i++) {
            if (FM_DURATION[i] == value) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw (new IllegalStateException("FMIndex not invalid"));
        }
        return index;
    }

    public static int getCalculateIndex(int value) {
        int i = 0;
        for (; i < CALCULATE_VALUE.length; i++) {
            if (CALCULATE_VALUE[i] == value) {
                break;
            }
        }
        return i;
    }

    public static String[] getDurations(Context context) {
        return new String[]{context.getString(R.string.wholeNote), context.getString(R.string.halfNoteDotted), context.getString(R.string.halfNote), context.getString(R.string.quarterNoteDotted),
                context.getString(R.string.quarterNote), context.getString(R.string.eighthNoteDotted), context.getString(R.string.eighthNote), context.getString(R.string.sixteenthNoteDotted),
                context.getString(R.string.sixteenthNote), context.getString(R.string.thirtySecondNote)};
    }
}
