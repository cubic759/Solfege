package com.example.solfege.constants;

public class TimeSignature {
    public static String[] LEFT_4 = {"2", "3", "4", "5", "7"};
    public static String[] LEFT_8 = {"5", "6", "7", "9", "11"};
    public static String[] RIGHT = {"4", "8"};
    public static int[] LEFT_4_VALUE = {2, 3, 4, 5, 7};
    public static int[] LEFT_8_VALUE = {5, 6, 7, 9, 11};
    public static int[] RIGHT_VALUE = {4, 8};

    public static int[] getTimeSignature(int[] timeSignature) {
        if (timeSignature[1] == 0) {
            return new int[]{LEFT_4_VALUE[timeSignature[0]], RIGHT_VALUE[timeSignature[1]]};
        } else {
            return new int[]{LEFT_8_VALUE[timeSignature[0]], RIGHT_VALUE[timeSignature[1]]};
        }
    }

    public static int getLimit(int[] timeSignature) {
        if (timeSignature[1] == 0) {
            return TimeSignature.LEFT_4_VALUE[timeSignature[0]] * 8;
        } else {
            return TimeSignature.LEFT_8_VALUE[timeSignature[0]] * 4;
        }
    }
}
