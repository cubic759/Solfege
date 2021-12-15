package com.example.solfege.utils;

import com.example.solfege.constants.Notes;
import com.example.solfege.external.FM_Score.FM_BarNote;
import com.example.solfege.external.FM_Score.FM_BaseNote;
import com.example.solfege.external.FM_Score.FM_ClefValue;
import com.example.solfege.external.FM_Score.FM_Note;
import com.example.solfege.external.FM_Score.FM_Score;
import com.example.solfege.external.FM_Score.FM_ScoreBase;
import com.example.solfege.models.Note;

import java.util.ArrayList;
import java.util.List;

public class Scores {
    public static void setNotes(FM_Score s, Note[][] notes) {
        boolean isBegin = false;
        int count = 0;
        int beams = 0;
        for (Note[] items : notes) {
            if (!isBegin) {
                beams = items[0].getBeams();
                if (beams > 0) {
                    isBegin = true;
                    s.BeginBeam();
                }
            }
            addNotes(s, items);
            if (items[0].getTie() > 0) {
                s.AddToTie(String.valueOf(1), (FM_Note) s.getLastNote());
            }
            if (isBegin) {
                s.AddToBeam((FM_Note) s.getLastNote());
                count++;
            }
            if (beams > 0 && count == beams) {
                isBegin = false;
                s.EndBeam();
                count = 0;
                beams = 0;
            }
            if (items[0].getHasBar()) {
                s.addStaveNote(new FM_BarNote(new FM_ScoreBase(s)));
                if (isBegin) {
                    isBegin = false;
                    s.EndBeam();
                }
                count = 0;
                beams = 0;
            }
        }
    }

    private static void addNotes(FM_Score s, Note[] items) {
        if (items.length == 1) {
            int noteValue = Notes.FM_NOTES[items[0].pitch % 12][0];
            int octave = items[0].pitch / 12;
            int clefValue = (items[0].pitch > 47) ? FM_ClefValue.TREBLE : FM_ClefValue.BASS;
            int accidental = Notes.FM_NOTES[items[0].pitch % 12][1];
            boolean isStemUp = (clefValue == FM_ClefValue.TREBLE) ? items[0].pitch <= 59 : items[0].pitch >= 42;
            FM_Note note = new FM_Note(s, noteValue, octave, clefValue, accidental, items[0].duration, 0, isStemUp);
            s.addStaveNote(note, clefValue);
        } else {
            List<Integer> clefs = new ArrayList<>();
            List<FM_BaseNote> chord = new ArrayList<>();
            for (Note item : items) {
                int noteValue = Notes.FM_NOTES[item.pitch % 12][0];
                int octave = item.pitch / 12;
                int clefValue = (item.pitch > 47) ? FM_ClefValue.TREBLE : FM_ClefValue.BASS;
                int accidental = Notes.FM_NOTES[item.pitch % 12][1];
                boolean isStemUp = (clefValue == FM_ClefValue.TREBLE) ? items[0].pitch <= 59 : items[0].pitch >= 42;
                FM_Note note = new FM_Note(s, noteValue, octave, clefValue, accidental, item.duration, 0, isStemUp);
                clefs.add(clefValue);
                chord.add(note);
            }
            s.addChord(chord, clefs);
        }
    }
}
