package com.example.solfege.constants;

import android.content.Context;

import com.example.solfege.R;

import java.util.ArrayList;

public enum Chords {
    MAJOR, MINOR, AUGMENTED, DIMINISHED, SUS2, SUS4, MAJOR_7, MINOR_7, _7, _7b5, _7sus4, DIMINISHED_7, MINOR_7b5,
    MAJOR_6, MINOR_6, MAJOR_9, MINOR_9, _9, MAJOR_11, MINOR_11, _11, MAJOR_13, MINOR_13, _13;

    //region Chords Definition
    public static final Intervals[][] MAJOR_CHORD = {//135,351,513
            {Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD},
            {Intervals.MINOR_THIRD, Intervals.PERFECT_FOURTH},
            {Intervals.PERFECT_FOURTH, Intervals.MAJOR_THIRD}};
    public static final Intervals[][] MINOR_CHORD = {//613,136,361
            {Intervals.MINOR_THIRD, Intervals.MAJOR_THIRD},
            {Intervals.MAJOR_THIRD, Intervals.PERFECT_FOURTH},
            {Intervals.PERFECT_FOURTH, Intervals.MINOR_THIRD}};
    public static final Intervals[][] AUGMENTED_CHORD = {//13b6,3b61,b613
            {Intervals.MAJOR_THIRD, Intervals.MAJOR_THIRD},
            {Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD},
            {Intervals.MAJOR_THIRD, Intervals.MAJOR_THIRD}};
    public static final Intervals[][] DIMINISHED_CHORD = {//1b3b5,b3b51,b51b3
            {Intervals.MINOR_THIRD, Intervals.MINOR_THIRD},
            {Intervals.MINOR_THIRD, Intervals.DIMINISHED_FIFTH},
            {Intervals.DIMINISHED_FIFTH, Intervals.MINOR_THIRD}};
    public static final Intervals[][] SUS2_CHORD = {//125,251,512
            {Intervals.MAJOR_SECOND, Intervals.PERFECT_FOURTH},
            {Intervals.PERFECT_FOURTH, Intervals.PERFECT_FOURTH},
            {Intervals.PERFECT_FOURTH, Intervals.MAJOR_SECOND}};
    public static final Intervals[][] SUS4_CHORD = {//145,451,514
            {Intervals.PERFECT_FOURTH, Intervals.MAJOR_SECOND},
            {Intervals.MAJOR_SECOND, Intervals.PERFECT_FOURTH},
            {Intervals.PERFECT_FOURTH, Intervals.PERFECT_FOURTH}};
    public static final Intervals[][] MAJOR_7_CHORD = {//1357,3571,5713,7135
            {Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD, Intervals.MAJOR_THIRD},
            {Intervals.MINOR_THIRD, Intervals.MAJOR_THIRD, Intervals.MINOR_SECOND},
            {Intervals.MAJOR_THIRD, Intervals.MINOR_SECOND, Intervals.MAJOR_THIRD},
            {Intervals.MINOR_SECOND, Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD}};
    public static final Intervals[][] MINOR_7_CHORD = {//1b35b7,b35b71,5b71b3,b71b35
            {Intervals.MINOR_THIRD, Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD},
            {Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD, Intervals.MAJOR_SECOND},
            {Intervals.MINOR_THIRD, Intervals.MAJOR_SECOND, Intervals.MINOR_THIRD},
            {Intervals.MAJOR_SECOND, Intervals.MINOR_THIRD, Intervals.MAJOR_THIRD}};
    public static final Intervals[][] _7_CHORD = {//135b7,35b71,5b713,b7135
            {Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD, Intervals.MINOR_THIRD},
            {Intervals.MINOR_THIRD, Intervals.MINOR_THIRD, Intervals.MAJOR_SECOND},
            {Intervals.MINOR_THIRD, Intervals.MAJOR_SECOND, Intervals.MAJOR_THIRD},
            {Intervals.MAJOR_SECOND, Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD}};
    public static final Intervals[][] _7b5_CHORD = {//13b5b7,3b5b71,b5b713,b713b5
            {Intervals.MAJOR_THIRD, Intervals.MAJOR_SECOND, Intervals.MAJOR_THIRD},
            {Intervals.MAJOR_SECOND, Intervals.MAJOR_THIRD, Intervals.MAJOR_SECOND},
            {Intervals.MAJOR_THIRD, Intervals.MAJOR_SECOND, Intervals.MAJOR_THIRD},
            {Intervals.MAJOR_SECOND, Intervals.MAJOR_THIRD, Intervals.MAJOR_SECOND}};
    public static final Intervals[][] _7sus4_CHORD = {//145b7,45b71,5b714,b7145
            {Intervals.PERFECT_FOURTH, Intervals.MAJOR_SECOND, Intervals.MINOR_THIRD},
            {Intervals.MAJOR_SECOND, Intervals.MINOR_THIRD, Intervals.MAJOR_SECOND},
            {Intervals.MINOR_THIRD, Intervals.MAJOR_SECOND, Intervals.PERFECT_FOURTH},
            {Intervals.MAJOR_SECOND, Intervals.PERFECT_FOURTH, Intervals.MAJOR_SECOND}};
    public static final Intervals[][] DIMINISHED_7_CHORD = {//1b3b5bb7,b3b5bb71,b5bb71b3,bb71b3b5
            {Intervals.MINOR_THIRD, Intervals.MINOR_THIRD, Intervals.MINOR_THIRD},
            {Intervals.MINOR_THIRD, Intervals.MINOR_THIRD, Intervals.MINOR_THIRD},
            {Intervals.MINOR_THIRD, Intervals.MINOR_THIRD, Intervals.MAJOR_THIRD},
            {Intervals.MINOR_THIRD, Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD}};
    public static final Intervals[][] MINOR_7b5_CHORD = {//1b3b5b7,b3b5b71,b5b71b3,b71b3b5
            {Intervals.MINOR_THIRD, Intervals.MINOR_THIRD, Intervals.MAJOR_THIRD},
            {Intervals.MINOR_THIRD, Intervals.MAJOR_THIRD, Intervals.MAJOR_SECOND},
            {Intervals.MAJOR_THIRD, Intervals.MAJOR_SECOND, Intervals.MINOR_THIRD},
            {Intervals.MAJOR_SECOND, Intervals.MINOR_THIRD, Intervals.MINOR_THIRD}};
    public static final Intervals[][] MAJOR_6_CHORD = {//1356,3561,5613,6135
            {Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD, Intervals.MAJOR_SECOND},
            {Intervals.MINOR_THIRD, Intervals.MAJOR_SECOND, Intervals.MINOR_THIRD},
            {Intervals.MAJOR_SECOND, Intervals.MINOR_THIRD, Intervals.MAJOR_THIRD},
            {Intervals.MINOR_SECOND, Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD}};
    public static final Intervals[][] MINOR_6_CHORD = {//1b356,b3561,561b3,61b35
            {Intervals.MINOR_THIRD, Intervals.MAJOR_THIRD, Intervals.MAJOR_SECOND},
            {Intervals.MAJOR_THIRD, Intervals.MAJOR_SECOND, Intervals.MINOR_THIRD},
            {Intervals.MAJOR_SECOND, Intervals.MINOR_THIRD, Intervals.MINOR_THIRD},
            {Intervals.MINOR_THIRD, Intervals.MINOR_THIRD, Intervals.MAJOR_THIRD}};

    public static final Intervals[][] MAJOR_9_CHORD = {{Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD, Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD}};//13572
    public static final Intervals[][] MINOR_9_CHORD = {{Intervals.MINOR_THIRD, Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD, Intervals.MAJOR_THIRD}};//1b35b72
    public static final Intervals[][] _9_CHORD = {{Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD, Intervals.MINOR_THIRD, Intervals.MAJOR_THIRD}};//135b72
    public static final Intervals[][] MAJOR_11_CHORD = {{Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD, Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD, Intervals.MINOR_THIRD}};//135724
    public static final Intervals[][] MINOR_11_CHORD = {{Intervals.MINOR_THIRD, Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD, Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD}};//1b35b724
    public static final Intervals[][] _11_CHORD = {{Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD, Intervals.MINOR_THIRD, Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD}};//135b724
    public static final Intervals[][] MAJOR_13_CHORD = {{Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD, Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD, Intervals.MINOR_THIRD, Intervals.MAJOR_THIRD}};//1357246
    public static final Intervals[][] MINOR_13_CHORD = {{Intervals.MINOR_THIRD, Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD, Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD, Intervals.MAJOR_THIRD}};//1b35b7246
    public static final Intervals[][] _13_CHORD = {{Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD, Intervals.MINOR_THIRD, Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD, Intervals.MAJOR_THIRD}};//135b7246

    //Not Common
    public static final Intervals[][] AUGMENTED_6_CHORD = {{Intervals.MAJOR_THIRD, Intervals.MAJOR_THIRD, Intervals.MINOR_SECOND}};//b6134
    public static final Intervals[][] AUGMENTED_7_CHORD = {{Intervals.MAJOR_THIRD, Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD}};//b6135
    public static final Intervals[][] AUGMENTED_9_CHORD = {{Intervals.MAJOR_THIRD, Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD, Intervals.MAJOR_THIRD}};//b61357
    public static final Intervals[][] AUGMENTED_11_CHORD = {{Intervals.MAJOR_THIRD, Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD, Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD}};//b613572
    public static final Intervals[][] AUGMENTED_13_CHORD = {{Intervals.MAJOR_THIRD, Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD, Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD, Intervals.MINOR_THIRD}};//b6135724
    public static final Intervals[][] DIMINISHED_6_CHORD = {{Intervals.MINOR_THIRD, Intervals.MINOR_THIRD, Intervals.MAJOR_SECOND}};//7245
    public static final Intervals[][] DIMINISHED_9_CHORD = {{Intervals.MINOR_THIRD, Intervals.MINOR_THIRD, Intervals.MINOR_THIRD, Intervals.MINOR_THIRD}};//72461
    public static final Intervals[][] DIMINISHED_11_CHORD = {{Intervals.MINOR_THIRD, Intervals.MINOR_THIRD, Intervals.MINOR_THIRD, Intervals.MINOR_THIRD, Intervals.MAJOR_THIRD}};//724613
    public static final Intervals[][] DIMINISHED_13_CHORD = {{Intervals.MINOR_THIRD, Intervals.MINOR_THIRD, Intervals.MINOR_THIRD, Intervals.MINOR_THIRD, Intervals.MAJOR_THIRD, Intervals.MINOR_THIRD}};//7246135
    //endregion

    public static final Intervals[][][] CHORDS = {MAJOR_CHORD, MINOR_CHORD, AUGMENTED_CHORD,
            DIMINISHED_CHORD, SUS2_CHORD, SUS4_CHORD, MAJOR_7_CHORD, MINOR_7_CHORD, _7_CHORD, _7b5_CHORD,
            _7sus4_CHORD, DIMINISHED_7_CHORD, MINOR_7b5_CHORD, MAJOR_6_CHORD, MINOR_6_CHORD, MAJOR_9_CHORD,
            MINOR_9_CHORD, _9_CHORD, MAJOR_11_CHORD, MINOR_11_CHORD, _11_CHORD, MAJOR_13_CHORD,
            MINOR_13_CHORD, _13_CHORD};

    public static final int[] MAJOR_CHORDS_MAX_INTERVALS = {7, 9, 11, 14, 17, 21};
    public static final int[] MINOR_CHORDS_MAX_INTERVALS = {7, 9, 10, 14, 17, 21};
    public static final int[] DOMINANT_CHORDS_MAX_INTERVALS = {7, 9, 10, 14, 17, 21};
    public static final int[] DIMINISHED_CHORDS_MAX_INTERVALS = {6, 8, 10, 13, 17, 20};
    public static final int[] AUGMENTED_CHORDS_MAX_INTERVALS = {8, 9, 11, 15, 18, 21};

    public static final Intervals[][] MAJOR_CHORDS = {MAJOR_CHORD[0], MAJOR_6_CHORD[0], MAJOR_7_CHORD[0], MAJOR_9_CHORD[0], MAJOR_11_CHORD[0], MAJOR_13_CHORD[0]};
    public static final Intervals[][] MINOR_CHORDS = {MINOR_CHORD[0], MINOR_6_CHORD[0], MINOR_7_CHORD[0], MINOR_9_CHORD[0], MINOR_11_CHORD[0], MINOR_13_CHORD[0]};
    public static final Intervals[][] DOMINANT_CHORDS = {MAJOR_CHORD[0], MAJOR_6_CHORD[0], _7_CHORD[0], _9_CHORD[0], _11_CHORD[0], _13_CHORD[0]};
    public static final Intervals[][] DIMINISHED_CHORDS = {DIMINISHED_CHORD[0], DIMINISHED_6_CHORD[0], DIMINISHED_7_CHORD[0], DIMINISHED_9_CHORD[0], DIMINISHED_11_CHORD[0], DIMINISHED_13_CHORD[0]};
    public static final Intervals[][] AUGMENTED_CHORDS = {AUGMENTED_CHORD[0], AUGMENTED_6_CHORD[0], AUGMENTED_7_CHORD[0], AUGMENTED_9_CHORD[0], AUGMENTED_11_CHORD[0], AUGMENTED_13_CHORD[0]};

    public static final int[] maxInterval = {7, 7, 8, 6, 7, 7, 11, 10, 10, 10, 10, 9, 10, 9, 9, 14, 14, 14, 17, 17, 17, 21, 21, 21};

    public static int getMaxInterval(ArrayList<Integer> chords) {
        int max = -1;
        for (int i = 0; i < chords.size(); i++) {
            int interval = maxInterval[chords.get(i)];
            if (interval > max) {
                max = interval;
            }
        }
        return max;
    }

    public static String[] getChordTypes(Context context) {
        return new String[]{context.getString(R.string.thirdChords), context.getString(R.string.sixthChords), context.getString(R.string.seventhChords), context.getString(R.string.ninthChords),
                context.getString(R.string.eleventhChords), context.getString(R.string.thirteenthChords)};
    }

    public static String[] getChords(Context context) {
        return new String[]{context.getString(R.string.majorChord), context.getString(R.string.minorChord), context.getString(R.string.augmentedChord), context.getString(R.string.diminishedChord),
                context.getString(R.string.sus2Chord), context.getString(R.string.sus4Chord), context.getString(R.string.major7Chord), context.getString(R.string.minor7Chord), context.getString(R.string._7Chord), context.getString(R.string._7b5Chord),
                context.getString(R.string._7sus4Chord), context.getString(R.string.diminished7Chord), context.getString(R.string.minor7b5Chord), context.getString(R.string.major6Chord), context.getString(R.string.minor6Chord),
                context.getString(R.string.major9Chord), context.getString(R.string.minor9Chord), context.getString(R.string._9Chord), context.getString(R.string.major11Chord), context.getString(R.string.minor11Chord),
                context.getString(R.string._11Chord), context.getString(R.string.major13Chord), context.getString(R.string.minor13Chord), context.getString(R.string._13Chord)};
    }
}
