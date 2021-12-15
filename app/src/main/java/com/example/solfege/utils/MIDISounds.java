package com.example.solfege.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.Button;

import com.example.solfege.MainActivity;
import com.example.solfege.constants.Chords;
import com.example.solfege.constants.Durations;
import com.example.solfege.constants.Intervals;
import com.example.solfege.constants.PlayMode;
import com.example.solfege.constants.Scales;
import com.example.solfege.constants.TimeSignature;
import com.example.solfege.external.FM_Score.FM_DurationValue;
import com.example.solfege.external.midi.MidiFile;
import com.example.solfege.external.midi.MidiTrack;
import com.example.solfege.external.midi.event.meta.Tempo;
import com.example.solfege.models.Note;
import com.example.solfege.models.Settings;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MIDISounds {//C3=48
    private final File dir;
    private final File questionFile;
    private final File standardCFile;
    private final File standardRhythmFile;
    private final Settings settings;
    private final Context context;
    private int[] timeSignature;
    private int answer;
    private int[][] answers;
    private Note[][] notes;

    public MIDISounds(Settings settings, Context context, int[] timeSignature) {
        this.context = context;
        this.settings = settings;
        this.timeSignature = timeSignature;
        dir = new File(context.getExternalFilesDir(null), "Midi");
        questionFile = new File(dir, "question.mid");
        standardCFile = new File(dir, "standardC.mid");
        standardRhythmFile = new File(dir, "standardRhythm.mid");
    }

    public void createMIDIFile(MidiTrack noteTrack) {
        MidiTrack tempoTrack = new MidiTrack();
        com.example.solfege.external.midi.event.meta.TimeSignature ts = new com.example.solfege.external.midi.event.meta.TimeSignature();
        //0:top, 1:bottom
        ts.setTimeSignature(timeSignature[0], timeSignature[1],
                com.example.solfege.external.midi.event.meta.TimeSignature.DEFAULT_METER,
                com.example.solfege.external.midi.event.meta.TimeSignature.DEFAULT_DIVISION);

        Tempo tempo = new Tempo();
        tempo.setBpm(settings.getNoteVelocity());
        tempoTrack.insertEvent(ts);
        tempoTrack.insertEvent(tempo);
        List<MidiTrack> tracks = new ArrayList<>();
        tracks.add(tempoTrack);
        tracks.add(noteTrack);

        MidiFile midi = new MidiFile(MidiFile.DEFAULT_RESOLUTION, tracks);
        try {
            MainActivity.IGNORE_RESULT(dir.mkdir());
            MainActivity.IGNORE_RESULT(questionFile.createNewFile());
            midi.writeToFile(questionFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MidiTrack createRhythmNotes(ArrayList<Integer> durations, int bars, int noteLength) {
        MidiTrack noteTrack = new MidiTrack();
        int limit_min = TimeSignature.getLimit(timeSignature);
        int limit = limit_min * bars;
        int total = 0;
        ArrayList<Integer> indexList = new ArrayList<>();
        ArrayList<Integer> valuesList = new ArrayList<>();
        ArrayList<Note> notesList = new ArrayList<>();

        //add notes
        while (limit != 0) {
            int random = (int) (Math.random() * durations.size());
            int index = durations.get(random);
            int value = Durations.CALCULATE_VALUE[index];
            if (total + value < limit_min) {
                total += value;
                indexList.add(index);
                valuesList.add(Durations.REAL_VALUES[index]);
                notesList.add(new Note(48, Durations.FM_DURATION[index]));
            } else if (total + value > limit_min) {
                if (limit != limit_min) {
                    if (Durations.isDotted(index)) {
                        int value1 = limit_min - total;
                        int value2 = value - value1;
                        int index1 = Durations.getCalculateIndex(value1);
                        int index2 = Durations.getCalculateIndex(value2);
                        indexList.add(index1);
                        valuesList.add(Durations.REAL_VALUES[index1]);
                        Note note = new Note(48, Durations.FM_DURATION[index]);
                        note.setTie(1);
                        note.setHasBar(true);
                        notesList.add(note);
                        total = 0;
                        limit -= limit_min;
                        indexList.add(index2);
                        valuesList.add(Durations.REAL_VALUES[index2]);
                        note = new Note(48, Durations.FM_DURATION[index2]);
                        note.setTie(1);
                        notesList.add(note);
                        total += value2;
                    }
                } else {
                    for (int r = random + 1; r < durations.size(); r++) {
                        value = Durations.CALCULATE_VALUE[durations.get(r)];
                        if (total + value == limit) {
                            int index1 = durations.get(r);
                            indexList.add(index1);
                            valuesList.add(Durations.REAL_VALUES[index1]);
                            notesList.add(new Note(48, Durations.FM_DURATION[index1]));
                            total = 0;
                            limit -= limit_min;
                            break;
                        } else if (total + value < limit) {
                            total += value;
                            int index1 = durations.get(r);
                            indexList.add(index1);
                            valuesList.add(Durations.REAL_VALUES[index1]);
                            notesList.add(new Note(48, Durations.FM_DURATION[index1]));
                        }
                    }
                }
            } else if (total + value == limit_min) {
                indexList.add(index);
                valuesList.add(Durations.REAL_VALUES[index]);
                Note note = new Note(48, Durations.FM_DURATION[index]);
                if (limit != limit_min) {
                    note.setHasBar(true);
                }
                notesList.add(note);
                limit -= limit_min;
                total = 0;
            }
        }

        //set beams
        int beams = 0;
        int value = 0;
        int index = -1;
        for (int i = 0; i < indexList.size(); i++) {
            if (Durations.COULD_HAVE_BEAM[indexList.get(i)]) {
                if (index < 0) {
                    index = i;
                }
                if (value + Durations.CALCULATE_VALUE[indexList.get(i)] <= Durations.CALCULATE_VALUE[Durations.QUARTER_NOTE.ordinal()]) {
                    beams++;
                    if (notesList.get(i).getHasBar()) {
                        if (beams > 1) {
                            notesList.get(index).setBeams(beams);
                        }
                        beams = 0;
                        value = 0;
                        index = -1;
                    } else {
                        value += Durations.CALCULATE_VALUE[indexList.get(i)];
                        if (i == indexList.size() - 1 && beams > 1 && index > 0) {
                            notesList.get(index).setBeams(beams);
                        }
                    }
                } else {
                    if (beams > 1) {
                        notesList.get(index).setBeams(beams);
                    }
                    if (notesList.get(i).getHasBar()) {
                        index = i + 1;
                        beams = 0;
                        value = 0;
                    } else {
                        index = i;
                        beams = 1;
                        value = Durations.CALCULATE_VALUE[indexList.get(i)];
                    }
                }
            } else {
                if (beams > 1) {
                    notesList.get(index).setBeams(beams);
                }
                index = -1;
                beams = 0;
                value = 0;
            }
        }

        //set midi notes
        int tick = 0;
        for (int i = 0; i < valuesList.size(); i++) {
            long duration = (long) (valuesList.get(i) / 100000.0 * noteLength / 50 * 480 * 4);
            noteTrack.insertNote(0, 48, (int) (40 * Math.random()) + 60, tick, duration);
            tick += duration;
        }

        //set notes
        notes = new Note[notesList.size()][];
        for (int i = 0; i < notesList.size(); i++) {
            notes[i] = new Note[]{notesList.get(i)};
        }
        return noteTrack;
    }

    public MidiTrack createIntervalNotes(PlayMode playMode, int[] noteRange, List<Integer> intervals, int noteLength) {
        timeSignature = new int[]{4, 4};
        notes = new Note[2][1];
        MidiTrack noteTrack = new MidiTrack();
        int lowest = 21 + (87 - noteRange[0]);//MIDI 21-108
        int highest = 108 - noteRange[1];
        int range = highest - lowest;
        int duration = (int) (noteLength / 50.0 * 480);

        //initiate pitch
        int pitch;
        if (lowest == highest) {
            pitch = lowest;
        } else {
            pitch = (int) (range * Math.random()) + lowest;//from lowest
        }

        answer = intervals.get((int) (intervals.size() * Math.random()));
        boolean isAdd = false;// if there is space to make a higher note
        if (pitch + answer > highest) {// if higher note will not be played
            if (pitch - lowest > (pitch + answer) - highest) {// if there is space to make a higher note
                pitch = (int) ((range - answer) * Math.random()) + lowest;// make lower note lower
                isAdd = true;
            }
        } else if (pitch - answer < lowest) {// if lower note will not be played
            if (highest - pitch > lowest - (pitch - answer)) {// if there is space to make a lower note
                pitch = (int) ((range - answer) * Math.random()) + (lowest + answer);// make higher note higher
            } else {
                isAdd = true;
            }
        }

        if (playMode == PlayMode.ASCENDING) {
            if (isAdd) {
                notes[0][0] = new Note(pitch, FM_DurationValue.NOTE_QUARTER);
                notes[1][0] = new Note(pitch + answer, FM_DurationValue.NOTE_QUARTER);
            } else {
                notes[0][0] = new Note(pitch - answer, FM_DurationValue.NOTE_QUARTER);
                notes[1][0] = new Note(pitch, FM_DurationValue.NOTE_QUARTER);
            }
        } else if (playMode == PlayMode.DESCENDING) {
            if (isAdd) {
                notes[0][0] = new Note(pitch + answer, FM_DurationValue.NOTE_QUARTER);
                notes[1][0] = new Note(pitch, FM_DurationValue.NOTE_QUARTER);
            } else {
                notes[0][0] = new Note(pitch, FM_DurationValue.NOTE_QUARTER);
                notes[1][0] = new Note(pitch - answer, FM_DurationValue.NOTE_QUARTER);
            }
        } else {
            if (isAdd) {
                notes[0][0] = new Note(pitch, FM_DurationValue.NOTE_QUARTER);
                notes[0][1] = new Note(pitch + answer, FM_DurationValue.NOTE_QUARTER);
            } else {
                notes[0][0] = new Note(pitch - answer, FM_DurationValue.NOTE_QUARTER);
                notes[0][1] = new Note(pitch, FM_DurationValue.NOTE_QUARTER);
            }
        }
        int tick = 0;
        for (Note[] note : notes) {
            noteTrack.insertNote(0, note[0].pitch, (int) (40 * Math.random()) + 60, tick, duration);
            if (playMode != PlayMode.HARMONIC) {
                tick += duration;
            }
        }
        return noteTrack;
    }

    public MidiTrack createScaleNotes(PlayMode playMode, int[] noteRange, List<Integer> scales, int noteLength) {
        timeSignature = new int[]{4, 4};

        MidiTrack noteTrack = new MidiTrack();
        int lowest = 21 + (87 - noteRange[0]);//MIDI 21-108
        int highest = 108 - noteRange[1];
        int range = highest - lowest;
        int duration = (int) (noteLength / 50.0 * 480);

        int maxInterval = Intervals.OCTAVE.ordinal();
        int pitch = (int) (range * Math.random()) + lowest;//which root
        if (playMode == PlayMode.ASCENDING && pitch + maxInterval > highest) {// if higher note will not be played
            pitch = (int) ((range - maxInterval) * Math.random()) + lowest;// make lower note lower
        } else if (playMode == PlayMode.DESCENDING && pitch - maxInterval < lowest) {// if lower note will not be played
            pitch = (int) ((range - maxInterval) * Math.random()) + (lowest + maxInterval);// make higher note higher
        }
        answer = scales.get((int) (scales.size() * Math.random()));//which scale
        int length = Scales.SCALES[answer].length;//how many notes
        notes = new Note[length + 1][1];
        if (playMode == PlayMode.ASCENDING) {
            notes[0][0] = new Note(pitch, FM_DurationValue.NOTE_QUARTER);
            if (answer == 4) {
                for (int i = 0; i < length; i++) {
                    pitch += Scales.SCALES[Scales.MAJOR.ordinal()][i].ordinal();
                    notes[i + 1][0] = new Note(pitch, FM_DurationValue.NOTE_QUARTER);
                }
            } else {
                for (int i = 0; i < length; i++) {
                    pitch += Scales.SCALES[answer][i].ordinal();
                    notes[i + 1][0] = new Note(pitch, FM_DurationValue.NOTE_QUARTER);
                }
            }
        } else {
            int count = length;
            notes[count][0] = new Note(pitch, FM_DurationValue.NOTE_QUARTER);
            count--;
            if (answer == 5) {
                for (int i = 0; i < length; i++) {
                    pitch += Scales.SCALES[Scales.MINOR.ordinal()][i].ordinal();
                    notes[count][0] = new Note(pitch, FM_DurationValue.NOTE_QUARTER);
                    count--;
                }
            } else {
                for (int i = 0; i < length; i++) {
                    pitch += Scales.SCALES[answer][i].ordinal();
                    notes[count][0] = new Note(pitch, FM_DurationValue.NOTE_QUARTER);
                    count--;
                }
            }
        }
        int tick = 0;
        for (Note[] note : notes) {
            noteTrack.insertNote(0, note[0].pitch, (int) (40 * Math.random()) + 60, tick, duration);
            tick += duration;
        }
        return noteTrack;
    }

    public MidiTrack createMelodyNotes(ArrayList<Integer> durations, ArrayList<Integer> scales, int[] noteRange, int bars, int noteLength) {
        MidiTrack noteTrack = new MidiTrack();
        int limit_min = TimeSignature.getLimit(timeSignature);
        int limit = limit_min * bars;
        int total = 0;
        ArrayList<Integer> indexList = new ArrayList<>();
        ArrayList<Integer> valuesList = new ArrayList<>();
        ArrayList<Note> notesList = new ArrayList<>();

        //add notes
        while (limit != 0) {
            int random = (int) (Math.random() * durations.size());
            int index = durations.get(random);
            int value = Durations.CALCULATE_VALUE[index];
            if (total + value < limit_min) {
                total += value;
                indexList.add(index);
                valuesList.add(Durations.REAL_VALUES[index]);
                notesList.add(new Note(48, Durations.FM_DURATION[index]));
            } else if (total + value > limit_min) {
                if (limit != limit_min) {
                    if (Durations.isDotted(index)) {
                        int value1 = limit_min - total;
                        int value2 = value - value1;
                        int index1 = Durations.getCalculateIndex(value1);
                        int index2 = Durations.getCalculateIndex(value2);
                        indexList.add(index1);
                        valuesList.add(Durations.REAL_VALUES[index1]);
                        Note note = new Note(48, Durations.FM_DURATION[index]);
                        note.setTie(1);
                        note.setHasBar(true);
                        notesList.add(note);
                        total = 0;
                        limit -= limit_min;
                        indexList.add(index2);
                        valuesList.add(Durations.REAL_VALUES[index2]);
                        note = new Note(48, Durations.FM_DURATION[index2]);
                        note.setTie(1);
                        notesList.add(note);
                        total += value2;
                    }
                } else {
                    for (int r = random + 1; r < durations.size(); r++) {
                        value = Durations.CALCULATE_VALUE[durations.get(r)];
                        if (total + value == limit) {
                            int index1 = durations.get(r);
                            indexList.add(index1);
                            valuesList.add(Durations.REAL_VALUES[index1]);
                            notesList.add(new Note(48, Durations.FM_DURATION[index1]));
                            total = 0;
                            limit -= limit_min;
                            break;
                        } else if (total + value < limit) {
                            total += value;
                            int index1 = durations.get(r);
                            indexList.add(index1);
                            valuesList.add(Durations.REAL_VALUES[index1]);
                            notesList.add(new Note(48, Durations.FM_DURATION[index1]));
                        }
                    }
                }
            } else if (total + value == limit_min) {
                indexList.add(index);
                valuesList.add(Durations.REAL_VALUES[index]);
                Note note = new Note(48, Durations.FM_DURATION[index]);
                if (limit != limit_min) {
                    note.setHasBar(true);
                }
                notesList.add(note);
                limit -= limit_min;
                total = 0;
            }
        }

        //set beams
        int beams = 0;
        int value = 0;
        int index = -1;
        for (int i = 0; i < indexList.size(); i++) {
            if (Durations.COULD_HAVE_BEAM[indexList.get(i)]) {
                if (index < 0) {
                    index = i;
                }
                if (value + Durations.CALCULATE_VALUE[indexList.get(i)] <= Durations.CALCULATE_VALUE[Durations.QUARTER_NOTE.ordinal()]) {
                    beams++;
                    if (notesList.get(i).getHasBar()) {
                        if (beams > 1) {
                            notesList.get(index).setBeams(beams);
                        }
                        beams = 0;
                        value = 0;
                        index = -1;
                    } else {
                        value += Durations.CALCULATE_VALUE[indexList.get(i)];
                        if (i == indexList.size() - 1 && beams > 1 && index > 0) {
                            notesList.get(index).setBeams(beams);
                        }
                    }
                } else {
                    if (beams > 1) {
                        notesList.get(index).setBeams(beams);
                    }
                    if (notesList.get(i).getHasBar()) {
                        index = i + 1;
                        beams = 0;
                        value = 0;
                    } else {
                        index = i;
                        beams = 1;
                        value = Durations.CALCULATE_VALUE[indexList.get(i)];
                    }
                }
            } else {
                if (beams > 1) {
                    notesList.get(index).setBeams(beams);
                }
                index = -1;
                beams = 0;
                value = 0;
            }
        }

        //set notes pitch
        int scaleIndex = scales.get((int) (scales.size() * Math.random()));
        Intervals[] scale = Scales.SCALES[scaleIndex];
        int scaleLength = scale.length;
        int[] pitches = new int[scaleLength + 1];
        int lowest = 21 + (87 - noteRange[0]);//MIDI 21-108
        int highest = 108 - noteRange[1];
        int range = highest - lowest;
        int root = (int) (range * Math.random()) + lowest;
        int maxInterval = Intervals.OCTAVE.ordinal();

        if (root + maxInterval > highest) {// if higher note will not be played
            root = (int) ((range - maxInterval) * Math.random()) + lowest;// make lower note lower
        }

        pitches[0] = root;
        for (int i = 0; i < scaleLength; i++) {
            root += scale[i].ordinal();
            pitches[i + 1] = root;
        }

        for (int i = 0; i < notesList.size(); i++) {
            int pitchIndex = (int) ((scaleLength + 1) * Math.random());
            int pitch = pitches[pitchIndex];
            if (scaleIndex == 3 && notesList.get(i - 1).pitch > pitch && (pitchIndex == 6 || pitchIndex == 5)) {//Melodic Minor Scale
                pitch -= 1;
            }
            notesList.get(i).pitch = pitch;
        }

        //set notes
        notes = new Note[notesList.size()][];
        int tick = 0;
        for (int i = 0; i < valuesList.size(); i++) {
            long duration = (long) (valuesList.get(i) / 100000.0 * noteLength / 50 * 480 * 4);
            int pitch = notesList.get(i).pitch;
            noteTrack.insertNote(0, pitch, (int) (40 * Math.random()) + 60, tick, duration);
            tick += duration;
            notes[i] = new Note[]{notesList.get(i)};
        }
        return noteTrack;
    }

    public MidiTrack createArpeggioNotes(ArrayList<Integer> chords, PlayMode playMode, int[] noteRange, int noteLength) {
        timeSignature = new int[]{4, 4};
        MidiTrack noteTrack = new MidiTrack();
        int lowest = 21 + (87 - noteRange[0]);//MIDI 21-108
        int highest = 108 - noteRange[1];
        int range = highest - lowest;
        int duration = (int) (noteLength / 50.0 * 480 / 2);

        int maxInterval = (int) (Intervals.OCTAVE.ordinal() * 2.5);
        int root = (int) (range * Math.random()) + lowest;//which root
        if (playMode == PlayMode.ASCENDING && root + maxInterval > highest) {// if higher note will not be played
            root = (int) ((range - maxInterval) * Math.random()) + lowest;// make lower note lower
        } else if (playMode == PlayMode.DESCENDING && root - maxInterval < lowest) {// if lower note will not be played
            root = (int) ((range - maxInterval) * Math.random()) + (lowest + maxInterval);// make higher note higher
        }
        answer = chords.get((int) (chords.size() * Math.random()));//which scale
        Intervals[] chord = Chords.CHORDS[answer][0];
        int chordLength = chord.length;//how many notes
        int chordNote = root;
        int tempRoot = root;

        List<Integer> arpeggioNotes = new ArrayList<>();
        arpeggioNotes.add(root);
        if (playMode == PlayMode.ASCENDING) {
            int count = 0;
            while (true) {
                chordNote += chord[count].ordinal();
                if (chordNote - root < maxInterval) {
                    arpeggioNotes.add(chordNote);
                } else if (chordNote - root == maxInterval) {
                    arpeggioNotes.add(chordNote);
                    break;
                } else {
                    break;
                }
                if (count == chordLength - 1) {
                    count = 0;
                    tempRoot += Intervals.OCTAVE.ordinal();
                    chordNote = tempRoot;
                    arpeggioNotes.add(chordNote);
                } else {
                    count++;
                }
            }
        } else {
            int count = chordLength - 1;
            while (true) {
                chordNote -= chord[count].ordinal();
                if (root - chordNote < maxInterval) {
                    arpeggioNotes.add(chordNote);
                } else if (root - chordNote == maxInterval) {
                    arpeggioNotes.add(chordNote);
                    break;
                } else {
                    break;
                }
                if (count == 0) {
                    count = chordLength - 1;
                    tempRoot -= Intervals.OCTAVE.ordinal();
                    chordNote = tempRoot;
                    arpeggioNotes.add(chordNote);
                } else {
                    count--;
                }
            }
        }

        int arpeggioLength = arpeggioNotes.size();
        boolean isEven = arpeggioLength % 2 == 0;
        boolean setBeams = true;
        notes = new Note[arpeggioLength][1];
        if (isEven) {
            for (int i = 0; i < arpeggioLength; i++) {
                int pitch = arpeggioNotes.get(i);
                Note note = new Note(pitch, FM_DurationValue.NOTE_EIGHTH);
                if (setBeams) {
                    note.setBeams(2);
                    setBeams = false;
                } else {
                    setBeams = true;
                }
                notes[i][0] = note;
            }
        } else {
            for (int i = 0; i < arpeggioLength; i++) {
                int pitch = arpeggioNotes.get(i);
                Note note = new Note(pitch, FM_DurationValue.NOTE_EIGHTH);
                if (setBeams && i != arpeggioLength - 1) {
                    note.setBeams(2);
                    setBeams = false;
                } else {
                    setBeams = true;
                }
                notes[i][0] = note;
            }
        }

        int tick = 0;
        for (int i = 0; i < arpeggioLength; i++) {
            noteTrack.insertNote(0, notes[i][0].pitch, (int) (40 * Math.random()) + 60, tick, duration);
            tick += duration;
        }
        return noteTrack;
    }

    public MidiTrack createChordNotes(ArrayList<Integer> chords, int[] noteRange, int noteLength) {//TODO: chord could also be broken
        timeSignature = new int[]{4, 4};

        MidiTrack noteTrack = new MidiTrack();
        int lowest = 21 + (87 - noteRange[0]);//MIDI 21-108
        int highest = 108 - noteRange[1];
        int range = highest - lowest;
        int duration = (int) (noteLength / 50.0 * 480);
        answer = chords.get((int) (chords.size() * Math.random()));//which chord

        int maxInterval = Chords.maxInterval[answer];
        int pitch = (int) (range * Math.random()) + lowest;//which root
        if (pitch + maxInterval > highest) {// if higher note will not be played
            pitch = (int) ((range - maxInterval) * Math.random()) + lowest;// make lower note lower
        }
        int length = Chords.CHORDS[answer][0].length;//how many notes
        notes = new Note[1][length + 1];

        notes[0][0] = new Note(pitch, FM_DurationValue.NOTE_QUARTER);
        for (int i = 0; i < length; i++) {
            pitch += Chords.CHORDS[answer][0][i].ordinal();
            notes[0][i + 1] = new Note(pitch, FM_DurationValue.NOTE_QUARTER);
        }

        int tick = 0;
        for (Note note : notes[0]) {
            noteTrack.insertNote(0, note.pitch, (int) (30 * Math.random()) + 50, tick, duration);
        }
        return noteTrack;
    }

    public MidiTrack createProgressionNotes(ArrayList<Integer> progressionScales, ArrayList<Integer> chordTypes, int[] noteRange, int noteLength) {
        timeSignature = new int[]{4, 4};

        MidiTrack noteTrack = new MidiTrack();
        int lowest = 21 + (87 - noteRange[0]);//MIDI 21-108
        int highest = 108 - noteRange[1];
        int range = highest - lowest;
        int duration = (int) (noteLength / 50.0 * 480 * 4);
        int maxInterval = -1;

        //Scale, degrees and chordTypes answer
        answers = new int[5][3];//Fixed amount: 4 chords to make a progression and 1 for scale answer
        answers[0][1] = progressionScales.get((int) (progressionScales.size() * Math.random()));
        for (int i = 1; i < answers.length; i++) {
            int degreeIndex = (int) (Scales.DEGREES_LENGTH * Math.random());
            answers[i][2] = degreeIndex;
            int chordTypeIndex = chordTypes.get((int) (chordTypes.size() * Math.random()));
            answers[i][1] = chordTypeIndex;
            int interval = Scales.getProgressionScaleRootInterval(answers[0][1], degreeIndex)
                    + Scales.getMaxInterval(answers[0][1], degreeIndex, chordTypeIndex);
            if (interval > maxInterval) {
                maxInterval = interval;
            }
        }

        int scaleRoot = (int) (range * Math.random()) + lowest;//Which root
        if (scaleRoot + maxInterval > highest) {// If higher note will not be played
            scaleRoot = (int) ((range - maxInterval) * Math.random()) + lowest;// Make lower note lower
        }

        answers[0][0] = scaleRoot;
        Intervals[] progressionScale = Scales.PROGRESSION_SCALES[answers[0][1]];
        int[] scale = new int[progressionScale.length];
        scale[0] = scaleRoot;
        for (int i = 0; i < scale.length - 1; i++) {
            scaleRoot += progressionScale[i].ordinal();
            scale[i + 1] = scaleRoot;
        }
        Intervals[][][] progressionScaleChords = Scales.PROGRESSION_SCALES_CHORDS[answers[0][1]];
        notes = new Note[answers.length - 1][];
        for (int i = 1; i < answers.length; i++) {
            int degreeIndex = answers[i][2];
            int chordTypeIndex = answers[i][1];
            int chordRoot = scale[degreeIndex];
            answers[i][0] = chordRoot;
            Intervals[] chord = progressionScaleChords[degreeIndex][chordTypeIndex];

            notes[i - 1] = new Note[chord.length + 1];
            notes[i - 1][0] = new Note(chordRoot, FM_DurationValue.NOTE_QUARTER);
            for (int j = 0; j < chord.length; j++) {
                chordRoot += chord[j].ordinal();
                notes[i - 1][j + 1] = new Note(chordRoot, FM_DurationValue.NOTE_QUARTER);
            }
        }

        int tick = 0;
        for (Note[] note : notes) {
            for (Note value : note) {
                noteTrack.insertNote(0, value.pitch, (int) (30 * Math.random()) + 50, tick, duration);
            }
            tick += duration;
        }
        return noteTrack;
    }

    public void playSound(Button... buttons) {
        if (questionFile.exists()) {
            final MediaPlayer[] mediaPlayer = {MediaPlayer.create(context, Uri.fromFile(questionFile))};
            mediaPlayer[0].setOnCompletionListener(mp -> {
                mediaPlayer[0].release();
                mediaPlayer[0] = null;
                for (Button button : buttons) {
                    button.setEnabled(true);
                }
            });
            mediaPlayer[0].start();
            for (Button button : buttons) {
                button.setEnabled(false);
            }
        }
    }

    public void playStandardRhythm(Button... buttons) {
        MidiTrack tempoTrack = new MidiTrack();
        com.example.solfege.external.midi.event.meta.TimeSignature ts = new com.example.solfege.external.midi.event.meta.TimeSignature();
        //0:top, 1:bottom
        ts.setTimeSignature(timeSignature[0], timeSignature[1],
                com.example.solfege.external.midi.event.meta.TimeSignature.DEFAULT_METER,
                com.example.solfege.external.midi.event.meta.TimeSignature.DEFAULT_DIVISION);
        Tempo tempo = new Tempo();
        tempo.setBpm(settings.getNoteVelocity());
        tempoTrack.insertEvent(ts);
        tempoTrack.insertEvent(tempo);
        List<MidiTrack> tracks = new ArrayList<>();
        tracks.add(tempoTrack);
        MidiTrack noteTrack = new MidiTrack();
        int tick = 0;
        for (int i = 0; i < 4; i++) {
            long duration = 480;
            noteTrack.insertNote(0, 48, 75, tick, duration + tick);
            tick += duration;
        }
        tracks.add(noteTrack);
        MidiFile midi = new MidiFile(MidiFile.DEFAULT_RESOLUTION, tracks);
        try {
            MainActivity.IGNORE_RESULT(dir.mkdir());
            MainActivity.IGNORE_RESULT(standardRhythmFile.createNewFile());
            midi.writeToFile(standardRhythmFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (standardRhythmFile.exists()) {
            final MediaPlayer[] mediaPlayer = {MediaPlayer.create(context, Uri.fromFile(standardRhythmFile))};
            mediaPlayer[0].setOnCompletionListener(mp -> {
                mediaPlayer[0].release();
                mediaPlayer[0] = null;
                for (Button button : buttons) {
                    button.setEnabled(true);
                }
            });
            mediaPlayer[0].start();
            for (Button button : buttons) {
                button.setEnabled(false);
            }
        }
    }

    public void playStandardC(Button... buttons) {
        MidiTrack tempoTrack = new MidiTrack();
        com.example.solfege.external.midi.event.meta.TimeSignature ts = new com.example.solfege.external.midi.event.meta.TimeSignature();
        //0:top, 1:bottom
        ts.setTimeSignature(timeSignature[0], timeSignature[1],
                com.example.solfege.external.midi.event.meta.TimeSignature.DEFAULT_METER,
                com.example.solfege.external.midi.event.meta.TimeSignature.DEFAULT_DIVISION);
        Tempo tempo = new Tempo();
        tempo.setBpm(settings.getNoteVelocity());
        tempoTrack.insertEvent(ts);
        tempoTrack.insertEvent(tempo);
        List<MidiTrack> tracks = new ArrayList<>();
        tracks.add(tempoTrack);
        MidiTrack noteTrack = new MidiTrack();
        noteTrack.insertNote(0, 48, 75, 0, 480);
        tracks.add(noteTrack);
        MidiFile midi = new MidiFile(MidiFile.DEFAULT_RESOLUTION, tracks);
        try {
            MainActivity.IGNORE_RESULT(dir.mkdir());
            MainActivity.IGNORE_RESULT(standardCFile.createNewFile());
            midi.writeToFile(standardCFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (standardCFile.exists()) {
            final MediaPlayer[] mediaPlayer = {MediaPlayer.create(context, Uri.fromFile(standardCFile))};
            mediaPlayer[0].setOnCompletionListener(mp -> {
                mediaPlayer[0].release();
                mediaPlayer[0] = null;
                for (Button button : buttons) {
                    button.setEnabled(true);
                }
            });
            mediaPlayer[0].start();
            for (Button button : buttons) {
                button.setEnabled(false);
            }
        }
    }

    public int getAnswer() {
        return answer;
    }

    public int[][] getAnswers() {
        return answers;
    }

    public Note[][] getNotes() {
        return notes;
    }
}
