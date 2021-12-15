package com.example.solfege.constants;

import android.content.Context;

import com.example.solfege.R;

import java.util.ArrayList;

public enum Scales {
    MAJOR, MINOR, HARMONIC_MINOR, MELODIC_MINOR, PENTATONIC_MAJOR, PENTATONIC_MINOR, BLUES_MAJOR, BLUES_MINOR, DORIAN, PHRYGIAN, LYDIAN, MIXOLYDIAN, LOCRIAN;
    public static final Intervals[] MAJOR_SCALE = {Intervals.MAJOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MINOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MINOR_SECOND};
    public static final Intervals[] HARMONIC_MAJOR_SCALE = {Intervals.MAJOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MINOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MINOR_SECOND, Intervals.MINOR_THIRD, Intervals.MINOR_SECOND};
    public static final Intervals[] MELODIC_MAJOR_SCALE = {Intervals.MAJOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MINOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MINOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MAJOR_SECOND};
    public static final Intervals[] MINOR_SCALE = {Intervals.MAJOR_SECOND, Intervals.MINOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MINOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MAJOR_SECOND};
    public static final Intervals[] HARMONIC_MINOR_SCALE = {Intervals.MAJOR_SECOND, Intervals.MINOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MINOR_SECOND, Intervals.MINOR_THIRD, Intervals.MINOR_SECOND};
    public static final Intervals[] MELODIC_MINOR_SCALE = {Intervals.MAJOR_SECOND, Intervals.MINOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MINOR_SECOND};
    public static final Intervals[] PENTATONIC_MAJOR_SCALE = {Intervals.MAJOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MINOR_THIRD, Intervals.MAJOR_SECOND, Intervals.MINOR_THIRD};
    public static final Intervals[] PENTATONIC_MINOR_SCALE = {Intervals.MINOR_THIRD, Intervals.MAJOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MINOR_THIRD, Intervals.MAJOR_SECOND};
    public static final Intervals[] BLUES_MAJOR_SCALE = {Intervals.MAJOR_SECOND, Intervals.MINOR_SECOND, Intervals.MINOR_SECOND, Intervals.MINOR_THIRD, Intervals.MAJOR_SECOND, Intervals.MINOR_THIRD};
    public static final Intervals[] BLUES_MINOR_SCALE = {Intervals.MINOR_THIRD, Intervals.MAJOR_SECOND, Intervals.MINOR_SECOND, Intervals.MINOR_SECOND, Intervals.MINOR_THIRD, Intervals.MAJOR_SECOND};
    public static final Intervals[] DORIAN_SCALE = {Intervals.MAJOR_SECOND, Intervals.MINOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MINOR_SECOND, Intervals.MAJOR_SECOND};
    public static final Intervals[] PHRYGIAN_SCALE = {Intervals.MINOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MINOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MAJOR_SECOND};
    public static final Intervals[] LYDIAN_SCALE = {Intervals.MAJOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MINOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MINOR_SECOND};
    public static final Intervals[] MIXOLYDIAN_SCALE = {Intervals.MAJOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MINOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MINOR_SECOND, Intervals.MAJOR_SECOND};
    public static final Intervals[] LOCRIAN_SCALE = {Intervals.MINOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MINOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MAJOR_SECOND, Intervals.MAJOR_SECOND};
    public static final Intervals[][] SCALES = {MAJOR_SCALE, MINOR_SCALE, HARMONIC_MAJOR_SCALE, HARMONIC_MINOR_SCALE, MELODIC_MAJOR_SCALE, MELODIC_MINOR_SCALE, PENTATONIC_MAJOR_SCALE, PENTATONIC_MINOR_SCALE, BLUES_MAJOR_SCALE, BLUES_MINOR_SCALE, DORIAN_SCALE, PHRYGIAN_SCALE, LYDIAN_SCALE, MIXOLYDIAN_SCALE, LOCRIAN_SCALE};
    public static final Intervals[][] PROGRESSION_SCALES = {MAJOR_SCALE, MINOR_SCALE, HARMONIC_MAJOR_SCALE, HARMONIC_MINOR_SCALE, DORIAN_SCALE, PHRYGIAN_SCALE, LYDIAN_SCALE, MIXOLYDIAN_SCALE, LOCRIAN_SCALE};
    public static final Intervals[][][][] PROGRESSION_SCALES_CHORDS = {
            {Chords.MAJOR_CHORDS, Chords.MINOR_CHORDS, Chords.MINOR_CHORDS, Chords.MAJOR_CHORDS, Chords.DOMINANT_CHORDS, Chords.MINOR_CHORDS, Chords.DIMINISHED_CHORDS},
            {Chords.MINOR_CHORDS, Chords.DIMINISHED_CHORDS, Chords.MAJOR_CHORDS, Chords.MINOR_CHORDS, Chords.MINOR_CHORDS, Chords.MAJOR_CHORDS, Chords.DOMINANT_CHORDS},
            {Chords.MAJOR_CHORDS, Chords.DIMINISHED_CHORDS, Chords.MINOR_CHORDS, Chords.MINOR_CHORDS, Chords.DIMINISHED_CHORDS, Chords.AUGMENTED_CHORDS, Chords.DIMINISHED_CHORDS},//12345b67
            {Chords.MINOR_CHORDS, Chords.DIMINISHED_CHORDS, Chords.AUGMENTED_CHORDS, Chords.MINOR_CHORDS, Chords.MAJOR_CHORDS, Chords.MAJOR_CHORDS, Chords.DIMINISHED_CHORDS},//6712345#6
            {Chords.MINOR_CHORDS, Chords.MINOR_CHORDS, Chords.MAJOR_CHORDS, Chords.DOMINANT_CHORDS, Chords.MINOR_CHORDS, Chords.DIMINISHED_CHORDS, Chords.MAJOR_CHORDS},
            {Chords.MINOR_CHORDS, Chords.MAJOR_CHORDS, Chords.DOMINANT_CHORDS, Chords.MINOR_CHORDS, Chords.DIMINISHED_CHORDS, Chords.MAJOR_CHORDS, Chords.MINOR_CHORDS},
            {Chords.MAJOR_CHORDS, Chords.DOMINANT_CHORDS, Chords.MINOR_CHORDS, Chords.DIMINISHED_CHORDS, Chords.MAJOR_CHORDS, Chords.MINOR_CHORDS, Chords.MINOR_CHORDS},
            {Chords.DOMINANT_CHORDS, Chords.MINOR_CHORDS, Chords.DIMINISHED_CHORDS, Chords.MAJOR_CHORDS, Chords.MINOR_CHORDS, Chords.MINOR_CHORDS, Chords.MAJOR_CHORDS},
            {Chords.DIMINISHED_CHORDS, Chords.MAJOR_CHORDS, Chords.MINOR_CHORDS, Chords.MINOR_CHORDS, Chords.MAJOR_CHORDS, Chords.DOMINANT_CHORDS, Chords.MINOR_CHORDS}};

    public static final int[][][] PROGRESSION_SCALES_CHORDS_MAX_INTERVALS = {
            {Chords.MAJOR_CHORDS_MAX_INTERVALS, Chords.MINOR_CHORDS_MAX_INTERVALS, Chords.MINOR_CHORDS_MAX_INTERVALS, Chords.MAJOR_CHORDS_MAX_INTERVALS, Chords.DOMINANT_CHORDS_MAX_INTERVALS, Chords.MINOR_CHORDS_MAX_INTERVALS, Chords.DIMINISHED_CHORDS_MAX_INTERVALS},
            {Chords.MINOR_CHORDS_MAX_INTERVALS, Chords.DIMINISHED_CHORDS_MAX_INTERVALS, Chords.MAJOR_CHORDS_MAX_INTERVALS, Chords.MINOR_CHORDS_MAX_INTERVALS, Chords.MINOR_CHORDS_MAX_INTERVALS, Chords.MAJOR_CHORDS_MAX_INTERVALS, Chords.DOMINANT_CHORDS_MAX_INTERVALS},
            {Chords.MAJOR_CHORDS_MAX_INTERVALS, Chords.DIMINISHED_CHORDS_MAX_INTERVALS, Chords.MINOR_CHORDS_MAX_INTERVALS, Chords.MINOR_CHORDS_MAX_INTERVALS, Chords.DIMINISHED_CHORDS_MAX_INTERVALS, Chords.AUGMENTED_CHORDS_MAX_INTERVALS, Chords.DIMINISHED_CHORDS_MAX_INTERVALS},//12345b67
            {Chords.MINOR_CHORDS_MAX_INTERVALS, Chords.DIMINISHED_CHORDS_MAX_INTERVALS, Chords.AUGMENTED_CHORDS_MAX_INTERVALS, Chords.MINOR_CHORDS_MAX_INTERVALS, Chords.MAJOR_CHORDS_MAX_INTERVALS, Chords.MAJOR_CHORDS_MAX_INTERVALS, Chords.DIMINISHED_CHORDS_MAX_INTERVALS},//6712345#6
            {Chords.MINOR_CHORDS_MAX_INTERVALS, Chords.MINOR_CHORDS_MAX_INTERVALS, Chords.MAJOR_CHORDS_MAX_INTERVALS, Chords.DOMINANT_CHORDS_MAX_INTERVALS, Chords.MINOR_CHORDS_MAX_INTERVALS, Chords.DIMINISHED_CHORDS_MAX_INTERVALS, Chords.MAJOR_CHORDS_MAX_INTERVALS},
            {Chords.MINOR_CHORDS_MAX_INTERVALS, Chords.MAJOR_CHORDS_MAX_INTERVALS, Chords.DOMINANT_CHORDS_MAX_INTERVALS, Chords.MINOR_CHORDS_MAX_INTERVALS, Chords.DIMINISHED_CHORDS_MAX_INTERVALS, Chords.MAJOR_CHORDS_MAX_INTERVALS, Chords.MINOR_CHORDS_MAX_INTERVALS},
            {Chords.MAJOR_CHORDS_MAX_INTERVALS, Chords.DOMINANT_CHORDS_MAX_INTERVALS, Chords.MINOR_CHORDS_MAX_INTERVALS, Chords.DIMINISHED_CHORDS_MAX_INTERVALS, Chords.MAJOR_CHORDS_MAX_INTERVALS, Chords.MINOR_CHORDS_MAX_INTERVALS, Chords.MINOR_CHORDS_MAX_INTERVALS},
            {Chords.DOMINANT_CHORDS_MAX_INTERVALS, Chords.MINOR_CHORDS_MAX_INTERVALS, Chords.DIMINISHED_CHORDS_MAX_INTERVALS, Chords.MAJOR_CHORDS_MAX_INTERVALS, Chords.MINOR_CHORDS_MAX_INTERVALS, Chords.MINOR_CHORDS_MAX_INTERVALS, Chords.MAJOR_CHORDS_MAX_INTERVALS},
            {Chords.DIMINISHED_CHORDS_MAX_INTERVALS, Chords.MAJOR_CHORDS_MAX_INTERVALS, Chords.MINOR_CHORDS_MAX_INTERVALS, Chords.MINOR_CHORDS_MAX_INTERVALS, Chords.MAJOR_CHORDS_MAX_INTERVALS, Chords.DOMINANT_CHORDS_MAX_INTERVALS, Chords.MINOR_CHORDS_MAX_INTERVALS}};
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
