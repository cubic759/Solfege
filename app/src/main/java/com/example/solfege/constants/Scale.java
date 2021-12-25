package com.example.solfege.constants;

import android.content.Context;

import com.example.solfege.R;

import java.util.ArrayList;

public enum Scale {
    MAJOR, MINOR, HARMONIC_MINOR, MELODIC_MINOR, PENTATONIC_MAJOR, PENTATONIC_MINOR, BLUES_MAJOR, BLUES_MINOR, DORIAN, PHRYGIAN, LYDIAN, MIXOLYDIAN, LOCRIAN;
    public static final Interval[] MAJOR_SCALE = {Interval.MAJOR_SECOND, Interval.MAJOR_SECOND, Interval.MINOR_SECOND, Interval.MAJOR_SECOND, Interval.MAJOR_SECOND, Interval.MAJOR_SECOND, Interval.MINOR_SECOND};
    public static final Interval[] HARMONIC_MAJOR_SCALE = {Interval.MAJOR_SECOND, Interval.MAJOR_SECOND, Interval.MINOR_SECOND, Interval.MAJOR_SECOND, Interval.MINOR_SECOND, Interval.MINOR_THIRD, Interval.MINOR_SECOND};
    public static final Interval[] MELODIC_MAJOR_SCALE = {Interval.MAJOR_SECOND, Interval.MAJOR_SECOND, Interval.MINOR_SECOND, Interval.MAJOR_SECOND, Interval.MINOR_SECOND, Interval.MAJOR_SECOND, Interval.MAJOR_SECOND};
    public static final Interval[] MINOR_SCALE = {Interval.MAJOR_SECOND, Interval.MINOR_SECOND, Interval.MAJOR_SECOND, Interval.MAJOR_SECOND, Interval.MINOR_SECOND, Interval.MAJOR_SECOND, Interval.MAJOR_SECOND};
    public static final Interval[] HARMONIC_MINOR_SCALE = {Interval.MAJOR_SECOND, Interval.MINOR_SECOND, Interval.MAJOR_SECOND, Interval.MAJOR_SECOND, Interval.MINOR_SECOND, Interval.MINOR_THIRD, Interval.MINOR_SECOND};
    public static final Interval[] MELODIC_MINOR_SCALE = {Interval.MAJOR_SECOND, Interval.MINOR_SECOND, Interval.MAJOR_SECOND, Interval.MAJOR_SECOND, Interval.MAJOR_SECOND, Interval.MAJOR_SECOND, Interval.MINOR_SECOND};
    public static final Interval[] PENTATONIC_MAJOR_SCALE = {Interval.MAJOR_SECOND, Interval.MAJOR_SECOND, Interval.MINOR_THIRD, Interval.MAJOR_SECOND, Interval.MINOR_THIRD};
    public static final Interval[] PENTATONIC_MINOR_SCALE = {Interval.MINOR_THIRD, Interval.MAJOR_SECOND, Interval.MAJOR_SECOND, Interval.MINOR_THIRD, Interval.MAJOR_SECOND};
    public static final Interval[] BLUES_MAJOR_SCALE = {Interval.MAJOR_SECOND, Interval.MINOR_SECOND, Interval.MINOR_SECOND, Interval.MINOR_THIRD, Interval.MAJOR_SECOND, Interval.MINOR_THIRD};
    public static final Interval[] BLUES_MINOR_SCALE = {Interval.MINOR_THIRD, Interval.MAJOR_SECOND, Interval.MINOR_SECOND, Interval.MINOR_SECOND, Interval.MINOR_THIRD, Interval.MAJOR_SECOND};
    public static final Interval[] DORIAN_SCALE = {Interval.MAJOR_SECOND, Interval.MINOR_SECOND, Interval.MAJOR_SECOND, Interval.MAJOR_SECOND, Interval.MAJOR_SECOND, Interval.MINOR_SECOND, Interval.MAJOR_SECOND};
    public static final Interval[] PHRYGIAN_SCALE = {Interval.MINOR_SECOND, Interval.MAJOR_SECOND, Interval.MAJOR_SECOND, Interval.MAJOR_SECOND, Interval.MINOR_SECOND, Interval.MAJOR_SECOND, Interval.MAJOR_SECOND};
    public static final Interval[] LYDIAN_SCALE = {Interval.MAJOR_SECOND, Interval.MAJOR_SECOND, Interval.MAJOR_SECOND, Interval.MINOR_SECOND, Interval.MAJOR_SECOND, Interval.MAJOR_SECOND, Interval.MINOR_SECOND};
    public static final Interval[] MIXOLYDIAN_SCALE = {Interval.MAJOR_SECOND, Interval.MAJOR_SECOND, Interval.MINOR_SECOND, Interval.MAJOR_SECOND, Interval.MAJOR_SECOND, Interval.MINOR_SECOND, Interval.MAJOR_SECOND};
    public static final Interval[] LOCRIAN_SCALE = {Interval.MINOR_SECOND, Interval.MAJOR_SECOND, Interval.MAJOR_SECOND, Interval.MINOR_SECOND, Interval.MAJOR_SECOND, Interval.MAJOR_SECOND, Interval.MAJOR_SECOND};
    public static final Interval[][] SCALES = {MAJOR_SCALE, MINOR_SCALE, HARMONIC_MAJOR_SCALE, HARMONIC_MINOR_SCALE, MELODIC_MAJOR_SCALE, MELODIC_MINOR_SCALE, PENTATONIC_MAJOR_SCALE, PENTATONIC_MINOR_SCALE, BLUES_MAJOR_SCALE, BLUES_MINOR_SCALE, DORIAN_SCALE, PHRYGIAN_SCALE, LYDIAN_SCALE, MIXOLYDIAN_SCALE, LOCRIAN_SCALE};
    public static final Interval[][] PROGRESSION_SCALES = {MAJOR_SCALE, MINOR_SCALE, HARMONIC_MAJOR_SCALE, HARMONIC_MINOR_SCALE, DORIAN_SCALE, PHRYGIAN_SCALE, LYDIAN_SCALE, MIXOLYDIAN_SCALE, LOCRIAN_SCALE};
    public static final Interval[][][][] PROGRESSION_SCALES_CHORDS = {
            {Chord.MAJOR_CHORDS, Chord.MINOR_CHORDS, Chord.MINOR_CHORDS, Chord.MAJOR_CHORDS, Chord.DOMINANT_CHORDS, Chord.MINOR_CHORDS, Chord.DIMINISHED_CHORDS},
            {Chord.MINOR_CHORDS, Chord.DIMINISHED_CHORDS, Chord.MAJOR_CHORDS, Chord.MINOR_CHORDS, Chord.MINOR_CHORDS, Chord.MAJOR_CHORDS, Chord.DOMINANT_CHORDS},
            {Chord.MAJOR_CHORDS, Chord.DIMINISHED_CHORDS, Chord.MINOR_CHORDS, Chord.MINOR_CHORDS, Chord.DIMINISHED_CHORDS, Chord.AUGMENTED_CHORDS, Chord.DIMINISHED_CHORDS},//12345b67
            {Chord.MINOR_CHORDS, Chord.DIMINISHED_CHORDS, Chord.AUGMENTED_CHORDS, Chord.MINOR_CHORDS, Chord.MAJOR_CHORDS, Chord.MAJOR_CHORDS, Chord.DIMINISHED_CHORDS},//6712345#6
            {Chord.MINOR_CHORDS, Chord.MINOR_CHORDS, Chord.MAJOR_CHORDS, Chord.DOMINANT_CHORDS, Chord.MINOR_CHORDS, Chord.DIMINISHED_CHORDS, Chord.MAJOR_CHORDS},
            {Chord.MINOR_CHORDS, Chord.MAJOR_CHORDS, Chord.DOMINANT_CHORDS, Chord.MINOR_CHORDS, Chord.DIMINISHED_CHORDS, Chord.MAJOR_CHORDS, Chord.MINOR_CHORDS},
            {Chord.MAJOR_CHORDS, Chord.DOMINANT_CHORDS, Chord.MINOR_CHORDS, Chord.DIMINISHED_CHORDS, Chord.MAJOR_CHORDS, Chord.MINOR_CHORDS, Chord.MINOR_CHORDS},
            {Chord.DOMINANT_CHORDS, Chord.MINOR_CHORDS, Chord.DIMINISHED_CHORDS, Chord.MAJOR_CHORDS, Chord.MINOR_CHORDS, Chord.MINOR_CHORDS, Chord.MAJOR_CHORDS},
            {Chord.DIMINISHED_CHORDS, Chord.MAJOR_CHORDS, Chord.MINOR_CHORDS, Chord.MINOR_CHORDS, Chord.MAJOR_CHORDS, Chord.DOMINANT_CHORDS, Chord.MINOR_CHORDS}};

    public static final int[][][] PROGRESSION_SCALES_CHORDS_MAX_INTERVALS = {
            {Chord.MAJOR_CHORDS_MAX_INTERVALS, Chord.MINOR_CHORDS_MAX_INTERVALS, Chord.MINOR_CHORDS_MAX_INTERVALS, Chord.MAJOR_CHORDS_MAX_INTERVALS, Chord.DOMINANT_CHORDS_MAX_INTERVALS, Chord.MINOR_CHORDS_MAX_INTERVALS, Chord.DIMINISHED_CHORDS_MAX_INTERVALS},
            {Chord.MINOR_CHORDS_MAX_INTERVALS, Chord.DIMINISHED_CHORDS_MAX_INTERVALS, Chord.MAJOR_CHORDS_MAX_INTERVALS, Chord.MINOR_CHORDS_MAX_INTERVALS, Chord.MINOR_CHORDS_MAX_INTERVALS, Chord.MAJOR_CHORDS_MAX_INTERVALS, Chord.DOMINANT_CHORDS_MAX_INTERVALS},
            {Chord.MAJOR_CHORDS_MAX_INTERVALS, Chord.DIMINISHED_CHORDS_MAX_INTERVALS, Chord.MINOR_CHORDS_MAX_INTERVALS, Chord.MINOR_CHORDS_MAX_INTERVALS, Chord.DIMINISHED_CHORDS_MAX_INTERVALS, Chord.AUGMENTED_CHORDS_MAX_INTERVALS, Chord.DIMINISHED_CHORDS_MAX_INTERVALS},//12345b67
            {Chord.MINOR_CHORDS_MAX_INTERVALS, Chord.DIMINISHED_CHORDS_MAX_INTERVALS, Chord.AUGMENTED_CHORDS_MAX_INTERVALS, Chord.MINOR_CHORDS_MAX_INTERVALS, Chord.MAJOR_CHORDS_MAX_INTERVALS, Chord.MAJOR_CHORDS_MAX_INTERVALS, Chord.DIMINISHED_CHORDS_MAX_INTERVALS},//6712345#6
            {Chord.MINOR_CHORDS_MAX_INTERVALS, Chord.MINOR_CHORDS_MAX_INTERVALS, Chord.MAJOR_CHORDS_MAX_INTERVALS, Chord.DOMINANT_CHORDS_MAX_INTERVALS, Chord.MINOR_CHORDS_MAX_INTERVALS, Chord.DIMINISHED_CHORDS_MAX_INTERVALS, Chord.MAJOR_CHORDS_MAX_INTERVALS},
            {Chord.MINOR_CHORDS_MAX_INTERVALS, Chord.MAJOR_CHORDS_MAX_INTERVALS, Chord.DOMINANT_CHORDS_MAX_INTERVALS, Chord.MINOR_CHORDS_MAX_INTERVALS, Chord.DIMINISHED_CHORDS_MAX_INTERVALS, Chord.MAJOR_CHORDS_MAX_INTERVALS, Chord.MINOR_CHORDS_MAX_INTERVALS},
            {Chord.MAJOR_CHORDS_MAX_INTERVALS, Chord.DOMINANT_CHORDS_MAX_INTERVALS, Chord.MINOR_CHORDS_MAX_INTERVALS, Chord.DIMINISHED_CHORDS_MAX_INTERVALS, Chord.MAJOR_CHORDS_MAX_INTERVALS, Chord.MINOR_CHORDS_MAX_INTERVALS, Chord.MINOR_CHORDS_MAX_INTERVALS},
            {Chord.DOMINANT_CHORDS_MAX_INTERVALS, Chord.MINOR_CHORDS_MAX_INTERVALS, Chord.DIMINISHED_CHORDS_MAX_INTERVALS, Chord.MAJOR_CHORDS_MAX_INTERVALS, Chord.MINOR_CHORDS_MAX_INTERVALS, Chord.MINOR_CHORDS_MAX_INTERVALS, Chord.MAJOR_CHORDS_MAX_INTERVALS},
            {Chord.DIMINISHED_CHORDS_MAX_INTERVALS, Chord.MAJOR_CHORDS_MAX_INTERVALS, Chord.MINOR_CHORDS_MAX_INTERVALS, Chord.MINOR_CHORDS_MAX_INTERVALS, Chord.MAJOR_CHORDS_MAX_INTERVALS, Chord.DOMINANT_CHORDS_MAX_INTERVALS, Chord.MINOR_CHORDS_MAX_INTERVALS}};
    public static final int DEGREES_LENGTH = 7;
    public static final String[] DEGREES = {"Ⅰ", "Ⅱ", "Ⅲ", "Ⅳ", "Ⅴ", "Ⅵ", "Ⅶ"};

    public static String[] getScales(Context context) {
        return new String[]{context.getString(R.string.majorScale), context.getString(R.string.minorScale), context.getString(R.string.harmonicMajorScale), context.getString(R.string.harmonicMinorScale),
                context.getString(R.string.melodicMajorScale), context.getString(R.string.melodicMinorScale), context.getString(R.string.pentatonicMajorScale), context.getString(R.string.pentatonicMinorScale),
                context.getString(R.string.bluesMajorScale), context.getString(R.string.bluesMinorScale), context.getString(R.string.dorianScale), context.getString(R.string.phrygianScale), context.getString(R.string.lydianScale),
                context.getString(R.string.mixolydianScale), context.getString(R.string.locrianScale)};
    }

    public static String[] getProgressionScales(Context context) {
        return new String[]{context.getString(R.string.majorScale), context.getString(R.string.minorScale), context.getString(R.string.harmonicMajorScale),
                context.getString(R.string.harmonicMinorScale), context.getString(R.string.dorianScale), context.getString(R.string.phrygianScale), context.getString(R.string.lydianScale),
                context.getString(R.string.mixolydianScale), context.getString(R.string.locrianScale)};
    }

    // For progression
    public static int getMaxInterval(ArrayList<Integer> scales, ArrayList<Integer> chordTypes) {
        int max = 0;
        int chordTypeIndex = chordTypes.get(chordTypes.size() - 1);
        for (int i = 0; i < scales.size(); i++) {
            int scaleIndex = scales.get(i);
            for (int j = 0; j < DEGREES_LENGTH; j++) {
                int interval = getProgressionScaleRootInterval(scaleIndex, j) + PROGRESSION_SCALES_CHORDS_MAX_INTERVALS[scaleIndex][j][chordTypeIndex];
                if (interval > max) {
                    max = interval;
                }
            }
        }
        return max;
    }

    public static int getProgressionScaleRootInterval(int scaleIndex, int noteIndex) {
        int rootInterval = 0;
        for (int k = 0; k < noteIndex; k++) {
            rootInterval += PROGRESSION_SCALES[scaleIndex][k].ordinal();
        }
        return rootInterval;
    }

    public static int getMaxInterval(int scaleIndex, int degreeIndex, int chordTypeIndex) {
        return PROGRESSION_SCALES_CHORDS_MAX_INTERVALS[scaleIndex][degreeIndex][chordTypeIndex];
    }
}
