package com.example.solfege.models;

import com.example.solfege.constants.Chord;
import com.example.solfege.constants.Duration;
import com.example.solfege.constants.Interval;
import com.example.solfege.constants.Mode;
import com.example.solfege.constants.Scale;
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

    public int[] getNoteRange() {
        return noteRange;
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
        boolean noteRangeResult = true;
        if (testMode == Mode.SIGHT_SINGING) {
            switch (testType) {
                case RHYTHM:
                    result = ((SSRhythmSettings) settings).isValid();
                    break;
                case INTERVAL:
                    SSIntervalSettings intervalSettings = (SSIntervalSettings) settings;
                    result = intervalSettings.isValid();
                    noteRangeResult = intervalSettings.isNoteRangeValid(noteRangeTemp);
                    break;
                case SCALE:
                    SSScaleSettings scaleSettings = (SSScaleSettings) settings;
                    result = scaleSettings.isValid();
                    noteRangeResult = scaleSettings.isNoteRangeValid(noteRangeTemp);
                    break;
                case ARPEGGIO:
                    SSArpeggioSettings arpeggioSettings = (SSArpeggioSettings) settings;
                    result = arpeggioSettings.isValid();
                    noteRangeResult = arpeggioSettings.isNoteRangeValid(noteRangeTemp);
                    break;
                case MELODY:
                    SSMelodySettings melodySettings = (SSMelodySettings) settings;
                    result = melodySettings.isValid();
                    noteRangeResult = melodySettings.isNoteRangeValid(noteRangeTemp);
                    break;
                case CHORD:
                    SSChordSettings chordSettings = (SSChordSettings) settings;
                    result = chordSettings.isValid();
                    noteRangeResult = chordSettings.isNoteRangeValid(noteRangeTemp);
                    break;
                case PROGRESSION:
                    SSProgressionSettings progressionSettings = (SSProgressionSettings) settings;
                    result = progressionSettings.isValid();
                    noteRangeResult = progressionSettings.isNoteRangeValid(noteRangeTemp);
                    break;
            }
        } else {
            switch (testType) {
                case RHYTHM:
                    result = ((ETRhythmSettings) settings).isValid();
                    break;
                case INTERVAL:
                    ETIntervalSettings intervalSettings = (ETIntervalSettings) settings;
                    result = intervalSettings.isValid();
                    noteRangeResult = intervalSettings.isNoteRangeValid(noteRangeTemp);
                    break;
                case SCALE:
                    ETScaleSettings scaleSettings = (ETScaleSettings) settings;
                    result = scaleSettings.isValid();
                    noteRangeResult = scaleSettings.isNoteRangeValid(noteRangeTemp);
                    break;
                case ARPEGGIO:
                    ETArpeggioSettings arpeggioSettings = (ETArpeggioSettings) settings;
                    result = arpeggioSettings.isValid();
                    noteRangeResult = arpeggioSettings.isNoteRangeValid(noteRangeTemp);
                    break;
                case MELODY:
                    ETMelodySettings melodySettings = (ETMelodySettings) settings;
                    result = melodySettings.isValid();
                    noteRangeResult = melodySettings.isNoteRangeValid(noteRangeTemp);
                    break;
                case CHORD:
                    ETChordSettings chordSettings = (ETChordSettings) settings;
                    result = chordSettings.isValid();
                    noteRangeResult = chordSettings.isNoteRangeValid(noteRangeTemp);
                    break;
                case PROGRESSION:
                    ETProgressionSettings progressionSettings = (ETProgressionSettings) settings;
                    result = progressionSettings.isValid();
                    noteRangeResult = progressionSettings.isNoteRangeValid(noteRangeTemp);
                    break;
            }
        }
        if (noteRangeResult) {
            noteRange = noteRangeTemp;
        }
        return noteRangeResult && result;
    }
}

class SSRhythmSettings {
    private ArrayList<Integer> durations;
    private boolean emptyDurations;
    private ArrayList<Integer> tempDurations;
    private int bars = 1;

    public void setTempDurations(ArrayList<Integer> tempDurations) {
        this.tempDurations = tempDurations;
    }

    public ArrayList<Integer> getDurations() {
        return durations;
    }

    public boolean isEmptyDurations() {
        return emptyDurations;
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
        if (emptyDurations || tempDurations == null) {
            emptyDurations = false;
            return false;
        } else {
            if (Duration.isDurationsValid(tempDurations)) {
                durations = tempDurations;
                return true;
            } else {
                return false;
            }
        }
    }
}

class SSIntervalSettings {
    private List<Integer> intervals;
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

    public boolean isNoteRangeValid(int[] noteRange) {
        return noteRange[0] - noteRange[1] >= intervals.get(intervals.size() - 1);
    }

    public boolean isValid() {
        if (intervals == null || emptyIntervals) {
            emptyIntervals = false;
            return false;
        } else {
            return true;
        }
    }
}

class SSScaleSettings {
    private List<Integer> scales;
    private boolean emptyScales;
    private int playMode;

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

    public boolean isNoteRangeValid(int[] noteRange) {
        return noteRange[0] - noteRange[1] >= Interval.OCTAVE.ordinal();
    }

    public boolean isValid() {
        if (scales == null || emptyScales) {
            emptyScales = false;
            return false;
        } else {
            return true;
        }
    }
}

class SSArpeggioSettings {
    private ArrayList<Integer> chords;
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

    public void setEmptyChords(boolean emptyChords) {
        this.emptyChords = emptyChords;
    }

    public boolean isNoteRangeValid(int[] noteRange) {
        return noteRange[0] - noteRange[1] >= (int) (Interval.OCTAVE.ordinal() * 2.5);
    }

    public boolean isValid() {
        if (chords == null || emptyChords) {
            emptyChords = false;
            return false;
        } else {
            return true;
        }
    }
}

class SSMelodySettings {
    private ArrayList<Integer> scales;
    private boolean emptyScales;
    private ArrayList<Integer> durations;
    private ArrayList<Integer> tempDurations;
    private boolean emptyDurations;
    private int bars = 1;

    public void setTempDurations(ArrayList<Integer> tempDurations) {
        this.tempDurations = tempDurations;
    }

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

    public void setEmptyDurations(boolean emptyDurations) {
        this.emptyDurations = emptyDurations;
    }

    public int getBars() {
        return bars;
    }

    public void setBars(int bars) {
        this.bars = bars;
    }

    public boolean isNoteRangeValid(int[] noteRange) {
        return noteRange[0] - noteRange[1] >= Interval.OCTAVE.ordinal();
    }

    public boolean isValid() {
        if (scales == null || emptyScales || emptyDurations) {
            emptyScales = false;
            emptyDurations = false;
            return false;
        } else {
            if (Duration.isDurationsValid(tempDurations)) {
                durations = tempDurations;
                return true;
            } else {
                return false;
            }
        }
    }
}

class SSChordSettings {
    private ArrayList<Integer> chords;
    private boolean emptyChords;

    public ArrayList<Integer> getChords() {
        return chords;
    }

    public void setChords(ArrayList<Integer> chords) {
        this.chords = chords;
    }

    public void setEmptyChords(boolean emptyChords) {
        this.emptyChords = emptyChords;
    }

    public boolean isNoteRangeValid(int[] noteRange) {
        return noteRange[0] - noteRange[1] >= Chord.getMaxInterval(chords);
    }

    public boolean isValid() {
        if (chords == null || emptyChords) {
            emptyChords = false;
            return false;
        } else {
            return true;
        }
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

    public void setEmptyChordTypes(boolean emptyChordType) {
        this.emptyChordTypes = emptyChordType;
    }

    public ArrayList<Integer> getChordTypes() {
        return chordTypes;
    }

    public void setChordTypes(ArrayList<Integer> chordType) {
        this.chordTypes = chordType;
    }

    public boolean isNoteRangeValid(int[] noteRange) {
        return noteRange[0] - noteRange[1] >= Scale.getMaxInterval(scales, chordTypes);
    }

    public boolean isValid() {
        if (chordTypes == null || emptyChordTypes || scales == null || emptyScales) {
            emptyScales = false;
            emptyChordTypes = false;
            return false;
        } else {
            return true;
        }
    }
}

class ETRhythmSettings {
    private ArrayList<Integer> durations;
    private ArrayList<Integer> tempDurations;
    private boolean emptyDurations;
    private int bars = 1;

    public void setTempDurations(ArrayList<Integer> tempDurations) {
        this.tempDurations = tempDurations;
    }

    public ArrayList<Integer> getDurations() {
        return durations;
    }

    public boolean isEmptyDurations() {
        return emptyDurations;
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
        if (emptyDurations || tempDurations == null) {
            emptyDurations = false;
            return false;
        } else {
            if (Duration.isDurationsValid(tempDurations)) {
                durations = tempDurations;
                return true;
            } else {
                return false;
            }
        }
    }
}

class ETIntervalSettings {
    private List<Integer> intervals;
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

    public boolean isNoteRangeValid(int[] noteRange) {
        return noteRange[0] - noteRange[1] >= intervals.get(intervals.size() - 1);
    }

    public boolean isValid() {
        if (intervals == null || emptyIntervals) {
            emptyIntervals = false;
            return false;
        } else {
            return true;
        }
    }
}

class ETScaleSettings {
    private List<Integer> scales;
    private int playMode;
    private boolean isExpertMode;
    private boolean emptyScales;

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

    public boolean isNoteRangeValid(int[] noteRange) {
        return noteRange[0] - noteRange[1] >= Interval.OCTAVE.ordinal();
    }

    public boolean isValid() {
        if (scales == null || emptyScales) {
            emptyScales = false;
            return false;
        } else {
            return true;
        }
    }
}

class ETArpeggioSettings {
    private ArrayList<Integer> chords;
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

    public void setEmptyChords(boolean emptyChords) {
        this.emptyChords = emptyChords;
    }

    public boolean isNoteRangeValid(int[] noteRange) {
        return noteRange[0] - noteRange[1] >= (int) (Interval.OCTAVE.ordinal() * 2.5);
    }

    public boolean isValid() {
        if (emptyChords || chords == null) {
            emptyChords = false;
            return false;
        } else {
            return true;
        }
    }
}

class ETMelodySettings {
    private ArrayList<Integer> scales;
    private boolean emptyScales;
    private ArrayList<Integer> durations;
    private ArrayList<Integer> tempDurations;
    private boolean emptyDurations;
    private int bars = 1;

    public void setTempDurations(ArrayList<Integer> tempDurations) {
        this.tempDurations = tempDurations;
    }

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

    public void setEmptyDurations(boolean emptyDurations) {
        this.emptyDurations = emptyDurations;
    }

    public int getBars() {
        return bars;
    }

    public void setBars(int bars) {
        this.bars = bars;
    }

    public boolean isNoteRangeValid(int[] noteRange) {
        return noteRange[0] - noteRange[1] >= Interval.OCTAVE.ordinal();
    }

    public boolean isValid() {
        if (scales == null || emptyScales || emptyDurations) {
            emptyScales = false;
            emptyDurations = false;
            return false;
        } else {
            if (Duration.isDurationsValid(tempDurations)) {
                durations = tempDurations;
                return true;
            } else {
                return false;
            }
        }
    }
}

class ETChordSettings {
    private ArrayList<Integer> chords;
    private boolean emptyChords;
    private boolean isExpertMode;

    public boolean isExpertMode() {
        return isExpertMode;
    }

    public void setExpertMode(boolean expertMode) {
        isExpertMode = expertMode;
    }

    public ArrayList<Integer> getChords() {
        return chords;
    }

    public void setChords(ArrayList<Integer> chords) {
        this.chords = chords;
    }

    public void setEmptyChords(boolean emptyChords) {
        this.emptyChords = emptyChords;
    }

    public boolean isNoteRangeValid(int[] noteRange) {
        return noteRange[0] - noteRange[1] >= Chord.getMaxInterval(chords);
    }

    public boolean isValid() {
        if (emptyChords || chords == null) {
            emptyChords = false;
            return false;
        } else {
            return true;
        }
    }
}

class ETProgressionSettings {
    private ArrayList<Integer> chordTypes;
    boolean emptyChordTypes;
    private ArrayList<Integer> scales;
    boolean emptyScales;
    boolean isExpertMode;

    public boolean isExpertMode() {
        return isExpertMode;
    }

    public void setExpertMode(boolean expertMode) {
        isExpertMode = expertMode;
    }

    public void setScales(ArrayList<Integer> scales) {
        this.scales = scales;
    }

    public void setEmptyScales(boolean emptyScales) {
        this.emptyScales = emptyScales;
    }

    public ArrayList<Integer> getScales() {
        return scales;
    }

    public void setEmptyChordTypes(boolean emptyChordType) {
        this.emptyChordTypes = emptyChordType;
    }

    public ArrayList<Integer> getChordTypes() {
        return chordTypes;
    }

    public void setChordTypes(ArrayList<Integer> chordType) {
        this.chordTypes = chordType;
    }

    public boolean isNoteRangeValid(int[] noteRange) {
        return noteRange[0] - noteRange[1] >= Scale.getMaxInterval(scales, chordTypes);
    }

    public boolean isValid() {
        if (emptyChordTypes || emptyScales || chordTypes == null || scales == null) {
            emptyScales = false;
            emptyChordTypes = false;
            return false;
        } else {
            return true;
        }
    }
}
