package com.example.solfege.constants;

import com.example.solfege.external.FM_Score.FM_Accidental;
import com.example.solfege.external.FM_Score.FM_NoteValue;

public class Note {
    public static final int NOTES_LIST_LENGTH = 88;
    public static final int[][] FM_NOTES = {
            {FM_NoteValue.DO, FM_Accidental.None}, {FM_NoteValue.DO, FM_Accidental.Sharp},
            {FM_NoteValue.RE, FM_Accidental.None}, {FM_NoteValue.RE, FM_Accidental.Sharp},
            {FM_NoteValue.MI, FM_Accidental.None}, {FM_NoteValue.FA, FM_Accidental.None},
            {FM_NoteValue.FA, FM_Accidental.Sharp}, {FM_NoteValue.SOL, FM_Accidental.None},
            {FM_NoteValue.SOL, FM_Accidental.Sharp}, {FM_NoteValue.LA, FM_Accidental.None},
            {FM_NoteValue.LA, FM_Accidental.Sharp}, {FM_NoteValue.SI, FM_Accidental.None}};
    public static final String[] NOTES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    public static final String[] NOTES_LIST = {"c⁵",
            "b⁴", "#a⁴", "a⁴", "#g⁴", "g⁴", "#f⁴", "f⁴", "e⁴", "#d⁴", "d⁴", "#c⁴", "c⁴",
            "b³", "#a³", "a³", "#g³", "g³", "#f³", "f³", "e³", "#d³", "d³", "#c³", "c³",
            "b²", "#a²", "a²", "#g²", "g²", "#f²", "f²", "e²", "#d²", "d²", "#c²", "c²",
            "b¹", "#a¹", "a¹", "#g¹", "g¹", "#f¹", "f¹", "e¹", "#d¹", "d¹", "#c¹", "c¹",
            "b", "#a", "a", "#g", "g", "#f", "f", "e", "#d", "d", "#c", "c",
            "B", "#A", "A", "#G", "G", "#F", "F", "E", "#D", "D", "#C", "C",
            "B₁", "#A₁", "A₁", "#G₁", "G₁", "#F₁", "F₁", "E₁", "#D₁", "D₁", "#C₁", "C₁",
            "B₂", "#A₂", "A₂"};

    public static String getNote(int noteNumber) {
        return NOTES[noteNumber % 12];
    }

}
