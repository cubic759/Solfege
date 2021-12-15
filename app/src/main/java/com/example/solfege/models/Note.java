package com.example.solfege.models;

public class Note {
    public int pitch;
    public int duration;
    private int beams = 0;
    private int tie = 0;
    private boolean hasBar;

    public Note(int pitch, int duration) {
        this.duration = duration;
        this.pitch = pitch;
    }

    public int getBeams() {
        return beams;
    }

    public void setBeams(int beams) {
        this.beams = beams;
    }

    public int getTie() {
        return tie;
    }

    public void setTie(int tie) {
        this.tie = tie;
    }

    public boolean getHasBar() {
        return hasBar;
    }

    public void setHasBar(boolean hasBar) {
        this.hasBar = hasBar;
    }
}
