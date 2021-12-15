package com.example.solfege.models;

import com.example.solfege.constants.Intervals;
import com.example.solfege.constants.Mode;
import com.example.solfege.constants.Type;

import java.util.ArrayList;
import java.util.List;

public class Settings {
    private final Type testType;
    private final Mode testMode;
    private Object settings;
    private boolean isUninitiated = true;
    private int[] timeSignature = {2, 0};
    private int[] noteRange = {87, 0};
    private int[] noteRangeTemp = {87, 0};
    private int noteVelocity = 120;
    private int noteLength = 50;

    public Settings(Type testType, Mode testMode) {
        this.testType = testType;
        this.testMode = testMode;
        createSettings();
    }

    private void createSettings() {
        if (testMode == Mode.SIGHT_SINGING) {
            switch (testType) {
                case RHYTHM:
                    settings = new SSRhythmSettings();
                    break;
                case INTERVAL:
                    settings = new SSIntervalSettings();
                    break;
                case SCALE:
                    settings = new SSScaleSettings();
                    break;
                case ARPEGGIO:
                    settings = new SSArpeggioSettings();
                    break;
                case MELODY:
                    settings = new SSMelodySettings();
                    break;
                case CHORD:
                    settings = new SSChordSettings();
                    break;
                case PROGRESSION:
                    settings = new SSProgressionSettings();
                    break;
            }
        } else {
            switch (testType) {
                case RHYTHM:
                    settings = new ETRhythmSettings();
                    break;
                case INTERVAL:
                    settings = new ETIntervalSettings();
                    break;
                case SCALE:
                    settings = new ETScaleSettings();
                    break;
                case ARPEGGIO:
                    settings = new ETArpeggioSettings();
                    break;
                case MELODY:
                    settings = new ETMelodySettings();
                    break;
                case CHORD:
                    settings = new ETChordSettings();
                    break;
                case PROGRESSION:
                    settings = new ETProgressionSettings();
                    break;
            }
        }
    }

    public boolean isUninitiated() {
        return isUninitiated;
    }

    public void setUninitiated(boolean uninitiated) {
        isUninitiated = uninitiated;
    }

    public int[] getTimeSignature() {
        return timeSignature;
    }

    public void setTimeSignature(int[] timeSignature) {
        this.timeSignature = timeSignature;
    }

    public int getNoteLength() {
        return noteLength;
    }

    public void setNoteLength(int noteLength) {
        this.noteLength = noteLength;
    }

    public Object getSettings() {
        return settings;
    }

    public void setNoteRange(int[] noteRange) {
        this.noteRange = noteRange;
    }

    public int[] getNoteRange() {
        return noteRange;
    }

    public int[] getNoteRangeTemp() {
        return noteRangeTemp;
    }

    public void setNoteRangeTemp(int[] noteRangeTemp) {
        this.noteRangeTemp = noteRangeTemp;
    }

    public void setNoteVelocity(int noteVelocity) {
        this.noteVelocity = noteVelocity;
    }

    public int getNoteVelocity() {
        return noteVelocity;
    }

    public boolean isValid() {
        boolean result = true;
        if (testMode == Mode.SIGHT_SINGING) {
            switch (testType) {
                case RHYTHM:
                    result = ((SSRhythmSettings) settings).isValid();
                    break;
                case INTERVAL:
                    result = ((SSIntervalSettings) settings).isValid(noteRangeTemp);
                    break;
                case SCALE:
                    result = ((SSScaleSettings) settings).isValid(noteRangeTemp);
                    break;
                case ARPEGGIO:
                    result = ((SSArpeggioSettings) settings).isValid(noteRangeTemp);
                    break;
                case MELODY:
                    result = ((SSMelodySettings) settings).isValid(noteRangeTemp);
                    break;
                case CHORD:
                    result = ((SSChordSettings) settings).isValid(noteRangeTemp);
                    break;
                case PROGRESSION:
                    result = ((SSProgressionSettings) settings).isValid(noteRangeTemp);
                    break;
            }
        } else {
            switch (testType) {
                case RHYTHM:
                    result = ((ETRhythmSettings) settings).isValid();
                    break;
                case INTERVAL:
                    result = ((ETIntervalSettings) settings).isValid(noteRangeTemp);
                    break;
                case SCALE:
                    result = ((ETScaleSettings) settings).isValid(noteRangeTemp);
                    break;
                case ARPEGGIO:
                    result = ((ETArpeggioSettings) settings).isValid(noteRangeTemp);
                    break;
                case MELODY:
                    result = ((ETMelodySettings) settings).isValid(noteRangeTemp);
                    break;
                case CHORD:
                    result = ((ETChordSettings) settings).isValid(noteRangeTemp);
                    break;
                case PROGRESSION:
                    result = ((ETProgressionSettings) settings).isValid(noteRangeTemp);
                    break;
            }
        }
        return result;
    }
}

class SSRhythmSettings {
    private ArrayList<Integer> durations = new ArrayList<>();
    private boolean emptyDurations;
    private int bars = 1;

    public ArrayList<Integer> getDurations() {
        return durations;
    }

    public boolean isEmptyDurations() {
        return emptyDurations;
    }

    public void setDurations(ArrayList<Integer> durations) {
        this.durations = durations;
    }

    public void setEmptyDurations(boolean emptyDurations) {
        this.emptyDurations = emptyDurations;
    }

    public int getBars() {
        return bars;
    }

    public void setBars(int bars) {
        this.bars = bars;
    }

    public boolean isValid() {
        return !emptyDurations;
    }
}

class SSIntervalSettings {
    private List<Integer> intervals = new ArrayList<>();
    private boolean emptyIntervals;
    private int playMode = 0;

    public List<Integer> getIntervals() {
        return intervals;
    }

    public void setIntervals(List<Integer> intervals) {
        this.intervals = intervals;
    }

    public boolean isEmptyIntervals() {
        return emptyIntervals;
    }

    public void setEmptyIntervals(boolean emptyIntervals) {
        this.emptyIntervals = emptyIntervals;
    }

    public int getPlayMode() {
        return playMode;
    }

    public void setPlayMode(int playMode) {
        this.playMode = playMode;
    }

    public boolean isValid(int[] noteRange) {
        if (intervals == null || emptyIntervals) {
            emptyIntervals = false;
            return false;
        } else {
            return noteRange[0] - noteRange[1] >= intervals.get(intervals.size() - 1);
        }
    }
}

class SSScaleSettings {
    private List<Integer> scales = new ArrayList<>();
    private boolean emptyScales;
    private int playMode;

    public boolean isEmptyScales() {
        return emptyScales;
    }

    public void setEmptyScales(boolean emptyScales) {
        this.emptyScales = emptyScales;
    }

    public List<Integer> getScales() {
        return scales;
    }

    public void setScales(List<Integer> scales) {
        this.scales = scales;
    }

    public int getPlayMode() {
        return playMode;
    }

    public void setPlayMode(int playMode) {
        this.playMode = playMode;
    }

    public boolean isValid(int[] noteRange) {
        if (scales == null || emptyScales) {
            emptyScales = false;
            return false;
        } else {
            return noteRange[0] - noteRange[1] >= Intervals.OCTAVE.ordinal();
        }
    }
}

class SSArpeggioSettings {
    private ArrayList<Integer> chords = new ArrayList<>();
    private boolean emptyChords;
    private int playMode;

    public int getPlayMode() {
        return playMode;
    }

    public void setPlayMode(int playMode) {
        this.playMode = playMode;
    }

    public ArrayList<Integer> getChords() {
        return chords;
    }

    public void setChords(ArrayList<Integer> chords) {
        this.chords = chords;
    }

    public boolean isEmptyChords() {
        return emptyChords;
    }

    public void setEmptyChords(boolean emptyChords) {
        this.emptyChords = emptyChords;
    }

    public boolean isValid(int[] noteRange) {
        return true;
    }
}

class SSMelodySettings {
    private ArrayList<Integer> scales = new ArrayList<>();
    private boolean emptyScales;
    private ArrayList<Integer> durations = new ArrayList<>();
    private boolean emptyDurations;
    private int bars = 1;

    public ArrayList<Integer> getScales() {
        return scales;
    }

    public void setScales(ArrayList<Integer> scales) {
        this.scales = scales;
    }

    public boolean isEmptyScales() {
        return emptyScales;
    }

    public void setEmptyScales(boolean emptyScales) {
        this.emptyScales = emptyScales;
    }

    public ArrayList<Integer> getDurations() {
        return durations;
    }

    public boolean isEmptyDurations() {
        return emptyDurations;
    }

    public void setDurations(ArrayList<Integer> durations) {
        this.durations = durations;
    }

    public void setEmptyDurations(boolean emptyDurations) {
        this.emptyDurations = emptyDurations;
    }

    public int getBars() {
        return bars;
    }

    public void setBars(int bars) {
        this.bars = bars;
    }

    public boolean isValid(int[] noteRange) {
        if (scales == null || emptyScales || emptyDurations) {
            emptyScales = false;
            return false;
        } else {
            return noteRange[0] - noteRange[1] >= 12;
        }
    }
}

class SSChordSettings {
    private ArrayList<Integer> chords = new ArrayList<>();
    private boolean emptyChords;

    public ArrayList<Integer> getChords() {
        return chords;
    }

    public void setChords(ArrayList<Integer> chords) {
        this.chords = chords;
    }

    public boolean isEmptyChords() {
        return emptyChords;
    }

    public void setEmptyChords(boolean emptyChords) {
        this.emptyChords = emptyChords;
    }

    public boolean isValid(int[] noteRange) {
        return true;
    }
}

class SSProgressionSettings {
    private ArrayList<Integer> chordTypes;
    boolean emptyChordTypes;
    private ArrayList<Integer> scales;
    boolean emptyScales;

    public ArrayList<Integer> getScales() {
        return scales;
    }

    public void setEmptyScales(boolean emptyScales) {
        this.emptyScales = emptyScales;
    }

    public void setScales(ArrayList<Integer> scales) {
        this.scales = scales;
    }

    public boolean isEmptyScales() {
        return emptyScales;
    }

    public void setEmptyChordTypes(boolean emptyChordType) {
        this.emptyChordTypes = emptyChordType;
    }

    public boolean isEmptyChordTypes() {
        return emptyChordTypes;
    }

    public ArrayList<Integer> getChordTypes() {
        return chordTypes;
    }

    public void setChordTypes(ArrayList<Integer> chordType) {
        this.chordTypes = chordType;
    }

    public boolean isValid(int[] noteRange) {
        return true;
    }
}

class ETRhythmSettings {
    private ArrayList<Integer> durations = new ArrayList<>();
    private boolean emptyDurations;
    private int bars = 1;

    public ArrayList<Integer> getDurations() {
        return durations;
    }

    public boolean isEmptyDurations() {
        return emptyDurations;
    }

    public void setEmptyDurations(boolean emptyDurations) {
        this.emptyDurations = emptyDurations;
    }

    public void setDurations(ArrayList<Integer> durations) {
        this.durations = durations;
    }

    public int getBars() {
        return bars;
    }

    public void setBars(int bars) {
        this.bars = bars;
    }

    public boolean isValid() {
        return true;
    }
}

class ETIntervalSettings {
    private List<Integer> intervals = new ArrayList<>();
    private boolean emptyIntervals;
    private int playMode = 0;
    private boolean isExpertMode;

    public List<Integer> getIntervals() {
        return intervals;
    }

    public void setIntervals(List<Integer> intervals) {
        this.intervals = intervals;
    }

    public boolean isEmptyIntervals() {
        return emptyIntervals;
    }

    public void setEmptyIntervals(boolean emptyIntervals) {
        this.emptyIntervals = emptyIntervals;
    }

    public int getPlayMode() {
        return playMode;
    }

    public void setPlayMode(int playMode) {
        this.playMode = playMode;
    }

    public boolean isExpertMode() {
        return isExpertMode;
    }

    public void setExpertMode(boolean expertMode) {
        isExpertMode = expertMode;
    }

    public boolean isValid(int[] noteRange) {
        if (intervals == null || emptyIntervals) {
            emptyIntervals = false;
            return false;
        } else {
            return noteRange[0] - noteRange[1] >= intervals.get(intervals.size() - 1);
        }
    }
}

class ETScaleSettings {
    private List<Integer> scales = new ArrayList<>();
    private int playMode;
    private boolean isExpertMode;
    private boolean emptyScales;

    public boolean isEmptyScales() {
        return emptyScales;
    }

    public void setEmptyScales(boolean emptyScales) {
        this.emptyScales = emptyScales;
    }

    public boolean isExpertMode() {
        return isExpertMode;
    }

    public void setExpertMode(boolean expertMode) {
        isExpertMode = expertMode;
    }

    public List<Integer> getScales() {
        return scales;
    }

    public void setScales(List<Integer> scales) {
        this.scales = scales;
    }

    public int getPlayMode() {
        return playMode;
    }

    public void setPlayMode(int playMode) {
        this.playMode = playMode;
    }

    public boolean isValid(int[] noteRange) {
        if (scales == null || emptyScales) {
            emptyScales = false;
            return false;
        } else {
            return noteRange[0] - noteRange[1] >= Intervals.OCTAVE.ordinal();
        }
    }
}

class ETArpeggioSettings {
    private ArrayList<Integer> chords = new ArrayList<>();
    private boolean emptyChords;
    private int playMode;
    private boolean isExpertMode;

    public boolean isExpertMode() {
        return isExpertMode;
    }

    public void setExpertMode(boolean expertMode) {
        isExpertMode = expertMode;
    }

    public int getPlayMode() {
        return playMode;
    }

    public void setPlayMode(int playMode) {
        this.playMode = playMode;
    }

    public ArrayList<Integer> getChords() {
        return chords;
    }

    public void setChords(ArrayList<Integer> chords) {
        this.chords = chords;
    }

    public boolean isEmptyChords() {
        return emptyChords;
    }

    public void setEmptyChords(boolean emptyChords) {
        this.emptyChords = emptyChords;
    }

    public boolean isValid(int[] noteRange) {
        return true;
    }
}

class ETMelodySettings {
    private List<Integer> scales = new ArrayList<>();
    private boolean emptyScales;
    private ArrayList<Integer> durations = new ArrayList<>();
    private boolean emptyDurations;
    private int bars = 1;

    public List<Integer> getScales() {
        return scales;
    }

    public void setScales(List<Integer> scales) {
        this.scales = scales;
    }

    public boolean isEmptyScales() {
        return emptyScales;
    }

    public void setEmptyScales(boolean emptyScales) {
        this.emptyScales = emptyScales;
    }

    public ArrayList<Integer> getDurations() {
        return durations;
    }

    public boolean isEmptyDurations() {
        return emptyDurations;
    }

    public void setDurations(ArrayList<Integer> durations) {
        this.durations = durations;
    }

    public void setEmptyDurations(boolean emptyDurations) {
        this.emptyDurations = emptyDurations;
    }

    public int getBars() {
        return bars;
    }

    public void setBars(int bars) {
        this.bars = bars;
    }

    public boolean isValid(int[] noteRange) {
        if (scales == null || emptyScales || emptyDurations) {
            emptyScales = false;
            return false;
        } else {
            return noteRange[0] - noteRange[1] >= 12;
        }
    }
}

class ETChordSettings {
    private ArrayList<Integer> chords = new ArrayList<>();
    private boolean emptyChords;

    public ArrayList<Integer> getChords() {
        return chords;
    }

    public void setChords(ArrayList<Integer> chords) {
        this.chords = chords;
    }

    public boolean isEmptyChords() {
        return emptyChords;
    }

    public void setEmptyChords(boolean emptyChords) {
        this.emptyChords = emptyChords;
    }

    public boolean isValid(int[] noteRange) {
        return true;
    }
}

class ETProgressionSettings {
    private ArrayList<Integer> chordTypes;
    boolean emptyChordTypes;
    private ArrayList<Integer> scales;
    boolean emptyScales;

    public void setScales(ArrayList<Integer> scales) {
        this.scales = scales;
    }

    public void setEmptyScales(boolean emptyScales) {
        this.emptyScales = emptyScales;
    }

    public ArrayList<Integer> getScales() {
        return scales;
    }

    public boolean isEmptyScales() {
        return emptyScales;
    }

    public void setEmptyChordTypes(boolean emptyChordType) {
        this.emptyChordTypes = emptyChordType;
    }

    public boolean isEmptyChordTypes() {
        return emptyChordTypes;
    }

    public ArrayList<Integer> getChordTypes() {
        return chordTypes;
    }

    public void setChordTypes(ArrayList<Integer> chordType) {
        this.chordTypes = chordType;
    }

    public boolean isValid(int[] noteRange) {
        return true;
    }
}
