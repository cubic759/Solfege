package com.example.solfege.constants;

import android.content.Context;

import com.example.solfege.R;

public enum Interval {
    UNISON, MINOR_SECOND, MAJOR_SECOND, MINOR_THIRD, MAJOR_THIRD, PERFECT_FOURTH, DIMINISHED_FIFTH,
    PERFECT_FIFTH, MINOR_SIXTH, MAJOR_SIXTH, MINOR_SEVENTH, MAJOR_SEVENTH, OCTAVE, MINOR_NINTH,
    MAJOR_NINTH, MINOR_TENTH, MAJOR_TENTH, PERFECT_ELEVENTH, DIMINISHED_TWELFTH, PERFECT_TWELFTH,
    MINOR_THIRTEENTH, MAJOR_THIRTEENTH, MINOR_FOURTEENTH, MAJOR_FOURTEENTH, DOUBLE_OCTAVES;

    public static String[] getIntervals(Context context) {
        return new String[]{context.getString(R.string.unison), context.getString(R.string.minor2nd), context.getString(R.string.major2nd), context.getString(R.string.minor3rd), context.getString(R.string.major3rd),
                context.getString(R.string.perfect4th), context.getString(R.string.diminished5th), context.getString(R.string.perfect5th), context.getString(R.string.minor6th), context.getString(R.string.major6th),
                context.getString(R.string.minor7th), context.getString(R.string.major7th), context.getString(R.string.octave)};
    }
}
