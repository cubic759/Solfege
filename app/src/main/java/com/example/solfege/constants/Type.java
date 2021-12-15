package com.example.solfege.constants;

import android.content.Context;

import com.example.solfege.R;

public enum Type {
    RHYTHM, INTERVAL, SCALE, ARPEGGIO, MELODY, CHORD, PROGRESSION;

    public static String[] getTypes(Context context) {
        return new String[]{context.getString(R.string.rhythm), context.getString(R.string.interval), context.getString(R.string.scale), context.getString(R.string.arpeggio), context.getString(R.string.melody), context.getString(R.string.chord), context.getString(R.string.progression)};
    }
}

