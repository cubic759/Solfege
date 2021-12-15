package com.example.solfege.models;

import android.content.Context;
import android.graphics.Color;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.solfege.constants.Chords;
import com.example.solfege.utils.AnswerPickerDialog;
import com.example.solfege.DoingTestActivity;
import com.example.solfege.R;
import com.example.solfege.constants.DialogType;
import com.example.solfege.constants.Durations;
import com.example.solfege.constants.Intervals;
import com.example.solfege.constants.Mode;
import com.example.solfege.constants.Notes;
import com.example.solfege.constants.PlayMode;
import com.example.solfege.constants.Scales;
import com.example.solfege.constants.TimeSignature;
import com.example.solfege.constants.Type;
import com.example.solfege.external.FM_Score.FM_Align;
import com.example.solfege.external.FM_Score.FM_Score;
import com.example.solfege.utils.MIDISounds;
import com.example.solfege.utils.Scores;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.apmem.tools.layouts.FlowLayout;

public class UIControls {
    private static final int OPERATION_CREATE = 0;
    private static final int OPERATION_DESTROY_DIALOGS = 1;
    private final Type testType;
    private final Mode testMode;
    private final Settings settings;
    private final LinearLayout layout;
    private final DoingTestActivity activity;
    private Context context;
    private Object uiControls;

    public UIControls(Type testType, Mode testMode, Settings settings, DoingTestActivity activity) {
        this.testType = testType;
        this.testMode = testMode;
        this.settings = settings;
        this.activity = activity;
        this.context = activity.getBaseContext();
        layout = activity.findViewById(R.id.layout3);
        context = activity.getBaseContext();
    }

    public void create() {
        UIControlsOperation(OPERATION_CREATE);
    }

    public void destroyDialogs() {
        if (testMode == Mode.EAR_TRAINING) {
            UIControlsOperation(OPERATION_DESTROY_DIALOGS);
        }
    }

    public void UIControlsOperation(int operation) {
        layout.removeAllViews();
        if (testMode == Mode.SIGHT_SINGING) {
            switch (testType) {
                case RHYTHM:
                    uiControls = new SSRhythmUC(context, settings, layout);
                    break;
                case INTERVAL:
                    uiControls = new SSIntervalUC(context, settings, layout);
                    break;
                case SCALE:
                    uiControls = new SSScaleUC(context, settings, layout);
                    break;
                case ARPEGGIO:
                    uiControls = new SSArpeggioUC(context, settings, layout);
                    break;
                case MELODY:
                    uiControls = new SSMelodyUC(context, settings, layout);
                    break;
                case CHORD:
                    uiControls = new SSChordUC(context, settings, layout);
                    break;
                case PROGRESSION:
                    uiControls = new SSProgressionUC(context, settings, layout);
                    break;
            }
        } else {
            switch (testType) {
                case RHYTHM:
                    if (operation == OPERATION_CREATE) {
                        uiControls = new ETRhythmUC(settings, activity, layout);
                    } else {
                        ((ETRhythmUC) uiControls).destroyDialogs();
                    }
                    break;
                case INTERVAL:
                    if (operation == OPERATION_CREATE) {
                        uiControls = new ETIntervalUC(settings, activity, layout);
                    } else {
                        ((ETIntervalUC) uiControls).destroyDialogs();
                    }
                    break;
                case SCALE:
                    if (operation == OPERATION_CREATE) {
                        uiControls = new ETScaleUC(settings, activity, layout);
                    } else {
                        ((ETScaleUC) uiControls).destroyDialogs();
                    }
                    break;
                case ARPEGGIO:
                    if (operation == OPERATION_CREATE) {
                        uiControls = new ETArpeggioUC(settings, activity, layout);
                    } else {
                        ((ETArpeggioUC) uiControls).destroyDialogs();
                    }
                    break;
                case MELODY:
                    if (operation == OPERATION_CREATE) {
                        uiControls = new ETMelodyUC(settings, activity, layout);
                    } else {
                        ((ETMelodyUC) uiControls).destroyDialogs();
                    }
                    break;
                case CHORD:
                    if (operation == OPERATION_CREATE) {
                        uiControls = new ETChordUC(settings, activity, layout);
                    } else {
                        ((ETChordUC) uiControls).destroyDialogs();
                    }
                    break;
                case PROGRESSION:
                    if (operation == OPERATION_CREATE) {
                        uiControls = new ETProgressionUC(settings, activity, layout);
                    } else {
                        ((ETProgressionUC) uiControls).destroyDialogs();
                    }
                    break;
            }
        }
    }
}

class SSRhythmUC {
    Context context;
    Settings initialSettings;
    SSRhythmSettings settings;
    LinearLayout layout;
    private Button newQuestionBtn, replayBtn;
    private final MIDISounds midiSounds;

    SSRhythmUC(Context context, Settings settings, LinearLayout layout) {
        this.context = context;
        initialSettings = settings;
        this.settings = (SSRhythmSettings) settings.getSettings();
        this.layout = layout;
        midiSounds = new MIDISounds(initialSettings, context, initialSettings.getTimeSignature());
        create();
    }

    void create() {
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 1100));
        FM_Score s = new FM_Score(context, null);
        s.setBackgroundColor(Color.WHITE);
        s.setAllowZoomPan(false);
        s.setShowBrace(true);
        s.setAllowZoomPan(true);
        s.setTrimLastRow(false);
        s.setMultiRow(true);
        s.setNotesAlign(FM_Align.ALIGN_CENTER_NOTES);
        frameLayout.addView(s);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        newQuestionBtn = new Button(context);
        newQuestionBtn.setText(R.string.newQuestion);
        newQuestionBtn.setAllCaps(false);
        newQuestionBtn.setOnClickListener(v -> {
            if (!replayBtn.isEnabled()) {
                replayBtn.setEnabled(true);
            }
            midiSounds.createMIDIFile(midiSounds.createRhythmNotes(settings.getDurations(), settings.getBars(), initialSettings.getNoteLength()));
            s.clearStaveNotes();
            int[] timeSignature = TimeSignature.getTimeSignature(initialSettings.getTimeSignature());
            s.setTimeSignature(timeSignature[0], timeSignature[1]);
            Scores.setNotes(s, midiSounds.getNotes());
            if (layout.getChildAt(1) == null) {
                layout.addView(frameLayout);
            }
        });
        linearLayout.addView(newQuestionBtn);
        replayBtn = new Button(context);
        replayBtn.setText(R.string.playSample);
        replayBtn.setAllCaps(false);
        replayBtn.setEnabled(false);
        replayBtn.setOnClickListener(v -> midiSounds.playSound(newQuestionBtn, replayBtn));
        linearLayout.addView(replayBtn);
        layout.addView(linearLayout);
    }
}

class SSIntervalUC {
    private final LinearLayout layout;
    private final Context context;
    private final Settings initialSettings;
    private final SSIntervalSettings settings;
    private final MIDISounds midiSounds;
    private int answer;
    private Note[][] notes;
    private Button newQuestionBtn, replayBtn;
    private PlayMode playMode;

    SSIntervalUC(Context context, Settings settings, LinearLayout layout) {
        this.layout = layout;
        this.context = context;
        initialSettings = settings;
        this.settings = (SSIntervalSettings) settings.getSettings();
        midiSounds = new MIDISounds(settings, context, initialSettings.getTimeSignature());
        create();
    }

    void create() {
        TextView text = new TextView(context);
        Button displayAnswers = new Button(context);
        displayAnswers.setAllCaps(false);
        displayAnswers.setText(R.string.showInfo);
        displayAnswers.setEnabled(false);
        displayAnswers.setTag(false);
        displayAnswers.setOnClickListener(v -> {
            if (!(boolean) displayAnswers.getTag()) {
                displayAnswers.setText(R.string.hideInfo);
                layout.addView(text);
                displayAnswers.setTag(true);
            } else {
                displayAnswers.setText(R.string.showInfo);
                layout.removeView(text);
                displayAnswers.setTag(false);
            }
        });
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 1100));
        FM_Score s = new FM_Score(context, null);
        s.setBackgroundColor(Color.WHITE);
        s.setAllowZoomPan(false);
        s.setShowBrace(true);
        s.setTrimLastRow(false);
        s.setNotesAlign(FM_Align.ALIGN_CENTER_NOTES);
        frameLayout.addView(s);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        newQuestionBtn = new Button(context);
        newQuestionBtn.setText(R.string.newQuestion);
        newQuestionBtn.setAllCaps(false);
        newQuestionBtn.setOnClickListener(v -> {
            if (!replayBtn.isEnabled()) {
                replayBtn.setEnabled(true);
                displayAnswers.setEnabled(true);
            }
            playMode = PlayMode.values()[settings.getPlayMode()];//Get the play mode
            if (playMode == PlayMode.RANDOM) {
                playMode = PlayMode.values()[(int) (3 * Math.random())];
            }
            midiSounds.createMIDIFile(midiSounds.createIntervalNotes(playMode, initialSettings.getNoteRange(), settings.getIntervals(), initialSettings.getNoteLength()));
            answer = midiSounds.getAnswer();
            notes = midiSounds.getNotes();
            s.clearStaveNotes();
            Scores.setNotes(s, notes);

            if (layout.getChildAt(1) == null) {
                layout.addView(frameLayout);
            }
            int pitch1, pitch2;
            if (playMode == PlayMode.HARMONIC) {
                pitch1 = notes[0][0].pitch;
                pitch2 = notes[0][1].pitch;
            } else {
                pitch1 = notes[0][0].pitch;
                pitch2 = notes[1][0].pitch;
            }
            text.setText(String.format(context.getString(R.string.intervalNoteInformation),
                    Notes.getNote(pitch1), Intervals.getIntervals(context)[answer],
                    Notes.getNote(pitch2)));
        });
        linearLayout.addView(newQuestionBtn);
        replayBtn = new Button(context);
        replayBtn.setText(R.string.playSample);
        replayBtn.setAllCaps(false);
        replayBtn.setEnabled(false);
        replayBtn.setOnClickListener(v -> midiSounds.playSound(newQuestionBtn, replayBtn));
        linearLayout.addView(replayBtn);
        linearLayout.addView(displayAnswers);
        layout.addView(linearLayout);
    }
}

class SSScaleUC {//TODO: Expert Mode Standard C
    private final LinearLayout layout;
    private final Context context;
    private final Settings initialSettings;
    private final SSScaleSettings settings;
    private final MIDISounds midiSounds;
    private Button newQuestionBtn, replayBtn;
    private PlayMode playMode;


    SSScaleUC(Context context, Settings settings, LinearLayout layout) {
        this.context = context;
        this.layout = layout;
        initialSettings = settings;
        this.settings = (SSScaleSettings) settings.getSettings();
        midiSounds = new MIDISounds(settings, context, initialSettings.getTimeSignature());
        create();
    }

    void create() {
        TextView text = new TextView(context);
        Button displayAnswers = new Button(context);
        displayAnswers.setAllCaps(false);
        displayAnswers.setText(R.string.showInfo);
        displayAnswers.setEnabled(false);
        displayAnswers.setTag(false);
        displayAnswers.setOnClickListener(v -> {
            if (!(boolean) displayAnswers.getTag()) {
                displayAnswers.setText(R.string.hideInfo);
                layout.addView(text);
                displayAnswers.setTag(true);
            } else {
                displayAnswers.setText(R.string.showInfo);
                layout.removeView(text);
                displayAnswers.setTag(false);
            }
        });
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 1100));
        FM_Score s = new FM_Score(context, null);
        s.setBackgroundColor(Color.WHITE);
        s.setAllowZoomPan(false);
        s.setShowBrace(true);
        s.setTrimLastRow(false);
        s.setNotesAlign(FM_Align.ALIGN_CENTER_NOTES);
        frameLayout.addView(s);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        newQuestionBtn = new Button(context);
        newQuestionBtn.setText(R.string.newQuestion);
        newQuestionBtn.setAllCaps(false);
        newQuestionBtn.setOnClickListener(v -> {
            if (!replayBtn.isEnabled()) {
                replayBtn.setEnabled(true);
                displayAnswers.setEnabled(true);
            }
            playMode = PlayMode.values()[settings.getPlayMode()];//Get the play mode
            midiSounds.createMIDIFile(midiSounds.createScaleNotes(playMode, initialSettings.getNoteRange(), settings.getScales(), initialSettings.getNoteLength()));
            int answer = midiSounds.getAnswer();
            Note[][] notes = midiSounds.getNotes();
            s.clearStaveNotes();
            Scores.setNotes(s, notes);

            if (layout.getChildAt(1) == null) {
                layout.addView(frameLayout);
            }
            text.setText(String.format(context.getString(R.string.scaleNoteInformation), Notes.getNote(notes[0][0].pitch), Scales.getScales(context)[answer]));
        });
        linearLayout.addView(newQuestionBtn);
        replayBtn = new Button(context);
        replayBtn.setText(R.string.playSample);
        replayBtn.setAllCaps(false);
        replayBtn.setEnabled(false);
        replayBtn.setOnClickListener(v -> midiSounds.playSound(newQuestionBtn, replayBtn));
        linearLayout.addView(replayBtn);
        linearLayout.addView(displayAnswers);
        layout.addView(linearLayout);
    }
}

class SSArpeggioUC {
    private final Context context;
    private final Settings initialSettings;
    private final SSArpeggioSettings settings;
    private final LinearLayout layout;
    private Button newQuestionBtn, replayBtn;
    private final MIDISounds midiSounds;

    SSArpeggioUC(Context context, Settings settings, LinearLayout layout) {
        this.context = context;
        initialSettings = settings;
        this.settings = (SSArpeggioSettings) settings.getSettings();
        this.layout = layout;
        midiSounds = new MIDISounds(initialSettings, context, initialSettings.getTimeSignature());
        create();
    }

    void create() {
        TextView text = new TextView(context);
        Button displayAnswers = new Button(context);
        displayAnswers.setAllCaps(false);
        displayAnswers.setText(R.string.showInfo);
        displayAnswers.setEnabled(false);
        displayAnswers.setTag(false);
        displayAnswers.setOnClickListener(v -> {
            if (!(boolean) displayAnswers.getTag()) {
                displayAnswers.setText(R.string.hideInfo);
                layout.addView(text);
                displayAnswers.setTag(true);
            } else {
                displayAnswers.setText(R.string.showInfo);
                layout.removeView(text);
                displayAnswers.setTag(false);
            }
        });

        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 1100));
        FM_Score s = new FM_Score(context, null);
        s.setBackgroundColor(Color.WHITE);
        s.setAllowZoomPan(false);
        s.setShowBrace(true);
        s.setAllowZoomPan(true);
        s.setTrimLastRow(false);
        s.setMultiRow(true);
        s.setNotesAlign(FM_Align.ALIGN_CENTER_NOTES);
        frameLayout.addView(s);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        newQuestionBtn = new Button(context);
        newQuestionBtn.setText(R.string.newQuestion);
        newQuestionBtn.setAllCaps(false);
        newQuestionBtn.setOnClickListener(v -> {
            if (!replayBtn.isEnabled()) {
                replayBtn.setEnabled(true);
                displayAnswers.setEnabled(true);
            }
            midiSounds.createMIDIFile(midiSounds.createArpeggioNotes(settings.getChords(), PlayMode.values()[settings.getPlayMode()], initialSettings.getNoteRange(), initialSettings.getNoteLength()));
            s.clearStaveNotes();
            Note[][] notes = midiSounds.getNotes();
            int answer = midiSounds.getAnswer();
            Scores.setNotes(s, notes);
            if (layout.getChildAt(1) == null) {
                layout.addView(frameLayout);
            }
            text.setText(String.format(context.getString(R.string.scaleNoteInformation), Notes.getNote(notes[0][0].pitch), Chords.getChords(context)[answer]));
        });
        linearLayout.addView(newQuestionBtn);
        replayBtn = new Button(context);
        replayBtn.setText(R.string.playSample);
        replayBtn.setAllCaps(false);
        replayBtn.setEnabled(false);
        replayBtn.setOnClickListener(v -> midiSounds.playSound(newQuestionBtn, replayBtn));
        linearLayout.addView(replayBtn);
        linearLayout.addView(displayAnswers);
        layout.addView(linearLayout);
    }
}

class SSMelodyUC {
    private final Context context;
    private final Settings initialSettings;
    private final SSMelodySettings settings;
    private final LinearLayout layout;
    private Button newQuestionBtn, replayBtn;
    private final MIDISounds midiSounds;

    SSMelodyUC(Context context, Settings settings, LinearLayout layout) {
        this.context = context;
        initialSettings = settings;
        this.settings = (SSMelodySettings) settings.getSettings();
        this.layout = layout;
        midiSounds = new MIDISounds(initialSettings, context, initialSettings.getTimeSignature());
        create();
    }

    void create() {
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 1900));
        FM_Score s = new FM_Score(context, null);
        s.setBackgroundColor(Color.WHITE);
        s.setAllowZoomPan(false);
        s.setShowBrace(true);
        s.setAllowZoomPan(true);
        s.setTrimLastRow(false);
        s.setMultiRow(true);
        s.setNotesAlign(FM_Align.ALIGN_CENTER_NOTES);
        frameLayout.addView(s);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        newQuestionBtn = new Button(context);
        newQuestionBtn.setText(R.string.newQuestion);
        newQuestionBtn.setAllCaps(false);
        newQuestionBtn.setOnClickListener(v -> {
            if (!replayBtn.isEnabled()) {
                replayBtn.setEnabled(true);
            }
            midiSounds.createMIDIFile(midiSounds.createMelodyNotes(settings.getDurations(), settings.getScales(), initialSettings.getNoteRange(), settings.getBars(), initialSettings.getNoteLength()));
            s.clearStaveNotes();
            int[] timeSignature = TimeSignature.getTimeSignature(initialSettings.getTimeSignature());
            s.setTimeSignature(timeSignature[0], timeSignature[1]);
            Scores.setNotes(s, midiSounds.getNotes());
            if (layout.getChildAt(1) == null) {
                layout.addView(frameLayout);
            }
        });
        linearLayout.addView(newQuestionBtn);
        replayBtn = new Button(context);
        replayBtn.setText(R.string.playSample);
        replayBtn.setAllCaps(false);
        replayBtn.setEnabled(false);
        replayBtn.setOnClickListener(v -> midiSounds.playSound(newQuestionBtn, replayBtn));
        linearLayout.addView(replayBtn);
        layout.addView(linearLayout);
    }
}

class SSChordUC {
    private final Context context;
    private final Settings initialSettings;
    private final SSChordSettings settings;
    private final LinearLayout layout;
    private Button newQuestionBtn, replayBtn;
    private final MIDISounds midiSounds;

    SSChordUC(Context context, Settings settings, LinearLayout layout) {
        this.context = context;
        initialSettings = settings;
        this.settings = (SSChordSettings) settings.getSettings();
        this.layout = layout;
        midiSounds = new MIDISounds(initialSettings, context, initialSettings.getTimeSignature());
        create();
    }

    void create() {
        TextView text = new TextView(context);
        Button displayAnswers = new Button(context);
        displayAnswers.setAllCaps(false);
        displayAnswers.setText(R.string.showInfo);
        displayAnswers.setEnabled(false);
        displayAnswers.setTag(false);
        displayAnswers.setOnClickListener(v -> {
            if (!(boolean) displayAnswers.getTag()) {
                displayAnswers.setText(R.string.hideInfo);
                layout.addView(text);
                displayAnswers.setTag(true);
            } else {
                displayAnswers.setText(R.string.showInfo);
                layout.removeView(text);
                displayAnswers.setTag(false);
            }
        });

        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 1100));
        FM_Score s = new FM_Score(context, null);
        s.setBackgroundColor(Color.WHITE);
        s.setAllowZoomPan(false);
        s.setShowBrace(true);
        s.setAllowZoomPan(true);
        s.setTrimLastRow(false);
        s.setMultiRow(true);
        s.setNotesAlign(FM_Align.ALIGN_CENTER_NOTES);
        frameLayout.addView(s);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        newQuestionBtn = new Button(context);
        newQuestionBtn.setText(R.string.newQuestion);
        newQuestionBtn.setAllCaps(false);
        newQuestionBtn.setOnClickListener(v -> {
            if (!replayBtn.isEnabled()) {
                replayBtn.setEnabled(true);
                displayAnswers.setEnabled(true);
            }
            midiSounds.createMIDIFile(midiSounds.createChordNotes(settings.getChords(), initialSettings.getNoteRange(), initialSettings.getNoteLength()));
            s.clearStaveNotes();
            Note[][] notes = midiSounds.getNotes();
            int answer = midiSounds.getAnswer();
            Scores.setNotes(s, notes);
            if (layout.getChildAt(1) == null) {
                layout.addView(frameLayout);
            }
            text.setText(String.format(context.getString(R.string.scaleNoteInformation), Notes.getNote(notes[0][0].pitch), Chords.getChords(context)[answer]));
        });
        linearLayout.addView(newQuestionBtn);
        replayBtn = new Button(context);
        replayBtn.setText(R.string.playSample);
        replayBtn.setAllCaps(false);
        replayBtn.setEnabled(false);
        replayBtn.setOnClickListener(v -> midiSounds.playSound(newQuestionBtn, replayBtn));
        linearLayout.addView(replayBtn);
        linearLayout.addView(displayAnswers);
        layout.addView(linearLayout);
    }
}

class SSProgressionUC {
    private final Context context;
    private final Settings initialSettings;
    private final SSProgressionSettings settings;
    private final LinearLayout layout;
    private Button newQuestionBtn, replayBtn;
    private final MIDISounds midiSounds;

    SSProgressionUC(Context context, Settings settings, LinearLayout layout) {
        this.context = context;
        initialSettings = settings;
        this.settings = (SSProgressionSettings) settings.getSettings();
        this.layout = layout;
        midiSounds = new MIDISounds(initialSettings, context, initialSettings.getTimeSignature());
        create();
    }

    void create() {
        TextView text = new TextView(context);
        text.setGravity(Gravity.CENTER);
        Button displayAnswers = new Button(context);
        displayAnswers.setAllCaps(false);
        displayAnswers.setText(R.string.showInfo);
        displayAnswers.setEnabled(false);
        displayAnswers.setTag(false);
        displayAnswers.setOnClickListener(v -> {
            if (!(boolean) displayAnswers.getTag()) {
                displayAnswers.setText(R.string.hideInfo);
                layout.addView(text);
                displayAnswers.setTag(true);
            } else {
                displayAnswers.setText(R.string.showInfo);
                layout.removeView(text);
                displayAnswers.setTag(false);
            }
        });

        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 1100));
        FM_Score s = new FM_Score(context, null);
        s.setBackgroundColor(Color.WHITE);
        s.setAllowZoomPan(false);
        s.setShowBrace(true);
        s.setAllowZoomPan(true);
        s.setTrimLastRow(false);
        s.setMultiRow(true);
        s.setNotesAlign(FM_Align.ALIGN_CENTER_NOTES);
        frameLayout.addView(s);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        newQuestionBtn = new Button(context);
        newQuestionBtn.setText(R.string.newQuestion);
        newQuestionBtn.setAllCaps(false);
        newQuestionBtn.setOnClickListener(v -> {
            if (!replayBtn.isEnabled()) {
                replayBtn.setEnabled(true);
                displayAnswers.setEnabled(true);
            }
            midiSounds.createMIDIFile(midiSounds.createProgressionNotes(settings.getScales(), settings.getChordTypes(), initialSettings.getNoteRange(), initialSettings.getNoteLength()));
            s.clearStaveNotes();
            int[] timeSignature = TimeSignature.getTimeSignature(initialSettings.getTimeSignature());
            s.setTimeSignature(timeSignature[0], timeSignature[1]);
            Note[][] notes = midiSounds.getNotes();
            int[][] answers = midiSounds.getAnswers();
            Scores.setNotes(s, notes);
            if (layout.getChildAt(1) == null) {
                layout.addView(frameLayout);
            }
            StringBuilder string = new StringBuilder();
            string.append(String.format(context.getString(R.string.scaleNoteInformation), Notes.getNote(answers[0][0]), Scales.getProgressionScales(context)[answers[0][1]]));
            for (int i = 1; i < answers.length; i++) {
                string.append("\n");
                string.append(String.format(context.getString(R.string.progressionInformation),
                        Notes.getNote(answers[i][0]), Chords.getChordTypes(context)[answers[i][1]],
                        Scales.DEGREES[answers[i][2]]));
            }
            text.setText(string);
        });
        linearLayout.addView(newQuestionBtn);
        replayBtn = new Button(context);
        replayBtn.setText(R.string.playSample);
        replayBtn.setAllCaps(false);
        replayBtn.setEnabled(false);
        replayBtn.setOnClickListener(v -> midiSounds.playSound(newQuestionBtn, replayBtn));
        linearLayout.addView(replayBtn);
        linearLayout.addView(displayAnswers);
        layout.addView(linearLayout);
    }
}

class ETRhythmUC {
    private final DoingTestActivity activity;
    private final LinearLayout layout;
    private final Context context;
    private final Settings initialSettings;
    private final ETRhythmSettings settings;
    private final MIDISounds midiSounds;
    private long[] lastClickTimes;
    private byte[] list;
    private Note[][] notes;
    private boolean isInitialized = false;
    private Button newQuestionBtn;
    private Button replayBtn;
    private Button standardBtn;
    private AnswerPickerDialog answerPickerDialog;

    ETRhythmUC(Settings settings, DoingTestActivity activity, LinearLayout layout) {
        this.activity = activity;
        this.layout = layout;
        context = activity.getBaseContext();
        initialSettings = settings;
        this.settings = (ETRhythmSettings) settings.getSettings();
        midiSounds = new MIDISounds(settings, context, settings.getTimeSignature());
        create();
    }

    void create() {
        newQuestionBtn = new Button(context);
        newQuestionBtn.setText(R.string.newQuestion);
        newQuestionBtn.setAllCaps(false);
        newQuestionBtn.setOnClickListener(v -> {
            midiSounds.createMIDIFile(
                    midiSounds.createRhythmNotes(settings.getDurations(), settings.getBars(), initialSettings.getNoteLength()));
            midiSounds.playSound(newQuestionBtn, replayBtn, standardBtn);
            notes = midiSounds.getNotes();
            isInitialized = true;
            if (layout.getChildCount() != 1) {
                layout.removeViewAt(1);
                layout.removeViewAt(1);
            }
            list = new byte[notes.length];
            lastClickTimes = new long[notes.length];
            answerPickerDialog = new AnswerPickerDialog(DialogType.Durations);
            TextView answerDurationsText = new TextView(context);
            answerDurationsText.setText(R.string.answerDurations);
            layout.addView(answerDurationsText);

            FlowLayout flowLayout = new FlowLayout(context);
            flowLayout.setLayoutParams(new FlowLayout.LayoutParams(FlowLayout.LayoutParams.MATCH_PARENT, FlowLayout.LayoutParams.WRAP_CONTENT));
            for (int i = 0; i < notes.length; i++) {
                LinearLayout layout2 = new LinearLayout(context);
                layout2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                layout2.setOrientation(LinearLayout.HORIZONTAL);
                TextView spaceChar = new TextView(context);
                Button emptyBtn = new Button(context);
                TextView checkChar = new TextView(context);
                emptyBtn.setAllCaps(false);
                emptyBtn.setBackgroundColor(Color.WHITE);
                emptyBtn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                int answer = Durations.getFMIndex(notes[i][0].duration);
                int index = i;
                emptyBtn.setOnClickListener(view -> {
                    if (SystemClock.elapsedRealtime() - lastClickTimes[index] < 1500) {
                        return;
                    }
                    lastClickTimes[index] = SystemClock.elapsedRealtime();
                    if (isInitialized && list[index] == 0) {
                        answerPickerDialog.setLinearLayout(layout2);
                        answerPickerDialog.setExpected(answer);
                        answerPickerDialog.setText(activity.getString(R.string.chooseDuration));
                        answerPickerDialog.setRecordList(list);
                        answerPickerDialog.setRecordListIndex(index);
                        answerPickerDialog.show(activity.getSupportFragmentManager(), "chooseInterval");
                    }
                });
                layout2.addView(spaceChar);
                layout2.addView(emptyBtn);
                layout2.addView(checkChar);
                flowLayout.addView(layout2);
            }
            layout.addView(flowLayout);
        });
        LinearLayout layout1 = new LinearLayout(context);
        layout1.setOrientation(LinearLayout.HORIZONTAL);
        layout1.addView(newQuestionBtn);
        replayBtn = new Button(context);
        replayBtn.setEnabled(false);
        replayBtn.setText(R.string.replay);
        replayBtn.setAllCaps(false);
        replayBtn.setOnClickListener(v -> midiSounds.playSound(newQuestionBtn, replayBtn));
        layout1.addView(replayBtn);
        standardBtn = new Button(context);
        standardBtn.setAllCaps(false);
        standardBtn.setText(R.string.play4_4Rhythm);
        standardBtn.setOnClickListener(v -> midiSounds.playStandardRhythm(newQuestionBtn, replayBtn, standardBtn));
        layout1.addView(standardBtn);
        layout.addView(layout1);
    }

    public void destroyDialogs() {
        if (answerPickerDialog != null && answerPickerDialog.getBehavior() != null) {
            answerPickerDialog.getBehavior().setHideable(true);
            answerPickerDialog.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }
}

class ETIntervalUC {
    private final DoingTestActivity activity;
    private final LinearLayout layout;
    private final Context context;
    private final Settings initialSettings;
    private final ETIntervalSettings settings;
    private final MIDISounds midiSounds;
    private final long[] lastClickTimes = new long[3];
    private byte[] list;
    private int answer;
    private Note[][] notes;
    private boolean isInitialized = false;
    private Button newQuestionBtn;
    private Button replayBtn;
    private AnswerPickerDialog answerPickerDialog;
    private PlayMode playMode;

    ETIntervalUC(Settings settings, DoingTestActivity activity, LinearLayout layout) {
        this.activity = activity;
        initialSettings = settings;
        this.settings = (ETIntervalSettings) settings.getSettings();
        this.layout = layout;
        context = activity.getBaseContext();
        midiSounds = new MIDISounds(settings, context, settings.getTimeSignature());
        create();
    }

    void create() {
        list = new byte[3];
        Button intervalEmptyBtn = new Button(context);
        Button lowerNoteEmptyBtn = new Button(context);
        Button higherNoteEmptyBtn = new Button(context);
        TextView intervalCheckChar = new TextView(context);
        TextView lowerNoteCheckChar = new TextView(context);
        TextView higherNoteCheckChar = new TextView(context);
        newQuestionBtn = new Button(context);
        newQuestionBtn.setText(R.string.newQuestion);
        newQuestionBtn.setAllCaps(false);
        newQuestionBtn.setOnClickListener(v -> {
            if (settings.isExpertMode()) {
                lowerNoteEmptyBtn.setText("");
                lowerNoteCheckChar.setText("");
                higherNoteEmptyBtn.setText("");
                higherNoteCheckChar.setText("");
            }
            list = new byte[3];
            intervalEmptyBtn.setText("");
            intervalCheckChar.setText("");
            playMode = PlayMode.values()[settings.getPlayMode()];
            if (playMode == PlayMode.RANDOM) {
                playMode = PlayMode.values()[(int) (3 * Math.random())];
            }
            midiSounds.createMIDIFile(
                    midiSounds.createIntervalNotes(playMode, initialSettings.getNoteRange(), settings.getIntervals(), initialSettings.getNoteLength()));
            midiSounds.playSound(newQuestionBtn, replayBtn);
            answer = midiSounds.getAnswer();
            notes = midiSounds.getNotes();
            isInitialized = true;
        });
        LinearLayout layout1 = new LinearLayout(context);
        layout1.setOrientation(LinearLayout.HORIZONTAL);
        layout1.addView(newQuestionBtn);
        replayBtn = new Button(context);
        replayBtn.setEnabled(false);
        replayBtn.setText(R.string.replay);
        replayBtn.setAllCaps(false);
        replayBtn.setOnClickListener(v -> midiSounds.playSound(newQuestionBtn, replayBtn));
        layout1.addView(replayBtn);
        layout.addView(layout1);

        LinearLayout layout2 = new LinearLayout(context);
        layout2.setOrientation(LinearLayout.HORIZONTAL);
        TextView intervalIs = new TextView(context);
        intervalIs.setText(R.string.intervalIs);

        intervalEmptyBtn.setAllCaps(false);
        intervalEmptyBtn.setBackgroundColor(Color.WHITE);
        intervalEmptyBtn.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - lastClickTimes[0] < 1500) {
                return;
            }
            lastClickTimes[0] = SystemClock.elapsedRealtime();
            if (isInitialized && list[0] == 0) {
                answerPickerDialog = new AnswerPickerDialog(DialogType.Intervals, layout2);
                answerPickerDialog.setExpected(answer);
                answerPickerDialog.setText(activity.getString(R.string.chooseInterval));
                answerPickerDialog.setRecordList(list);
                answerPickerDialog.setRecordListIndex(0);
                answerPickerDialog.show(activity.getSupportFragmentManager(), "chooseInterval");
            }
        });
        layout2.addView(intervalIs);
        layout2.addView(intervalEmptyBtn);
        layout2.addView(intervalCheckChar);
        layout.addView(layout2);

        if (settings.isExpertMode()) {
            LinearLayout layout3 = new LinearLayout(context);
            layout3.setOrientation(LinearLayout.HORIZONTAL);
            TextView lowerNoteIs = new TextView(context);
            lowerNoteIs.setText(R.string.lowerNoteIs);

            lowerNoteEmptyBtn.setAllCaps(false);
            lowerNoteEmptyBtn.setBackgroundColor(Color.WHITE);
            lowerNoteEmptyBtn.setOnClickListener(v -> {
                if (SystemClock.elapsedRealtime() - lastClickTimes[1] < 1500) {
                    return;
                }
                lastClickTimes[1] = SystemClock.elapsedRealtime();
                if (isInitialized && list[1] == 0) {
                    answerPickerDialog = new AnswerPickerDialog(DialogType.Notes, layout3);
                    answerPickerDialog.setExpected(notes[0][0].pitch);
                    answerPickerDialog.setText(activity.getString(R.string.chooseNote));
                    answerPickerDialog.setRecordList(list);
                    answerPickerDialog.setRecordListIndex(1);
                    answerPickerDialog.show(activity.getSupportFragmentManager(), "chooseNote");
                }
            });
            layout3.addView(lowerNoteIs);
            layout3.addView(lowerNoteEmptyBtn);
            layout3.addView(lowerNoteCheckChar);
            layout.addView(layout3);

            LinearLayout layout4 = new LinearLayout(context);
            layout4.setOrientation(LinearLayout.HORIZONTAL);
            TextView higherNoteIs = new TextView(context);
            higherNoteIs.setText(R.string.higherNoteIs);

            higherNoteEmptyBtn.setAllCaps(false);
            higherNoteEmptyBtn.setBackgroundColor(Color.WHITE);
            higherNoteEmptyBtn.setOnClickListener(v -> {
                if (SystemClock.elapsedRealtime() - lastClickTimes[2] < 1500) {
                    return;
                }
                lastClickTimes[2] = SystemClock.elapsedRealtime();
                if (isInitialized && list[2] == 0) {
                    answerPickerDialog = new AnswerPickerDialog(DialogType.Notes, layout4);
                    answerPickerDialog.setExpected(notes[1][0].pitch);
                    answerPickerDialog.setText(context.getString(R.string.chooseNote));
                    answerPickerDialog.setRecordList(list);
                    answerPickerDialog.setRecordListIndex(1);
                    answerPickerDialog.show(activity.getSupportFragmentManager(), "chooseNote");
                }
            });
            layout4.addView(higherNoteIs);
            layout4.addView(higherNoteEmptyBtn);
            layout4.addView(higherNoteCheckChar);
            layout.addView(layout4);
        }
    }

    public void destroyDialogs() {
        if (answerPickerDialog != null && answerPickerDialog.getBehavior() != null) {
            answerPickerDialog.getBehavior().setHideable(true);
            answerPickerDialog.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }
}

class ETScaleUC {
    private final DoingTestActivity activity;
    private final LinearLayout layout;
    private final Context context;
    private final Settings initialSettings;
    private final ETScaleSettings settings;
    private final MIDISounds midiSounds;
    private final long[] lastClickTimes = new long[2];
    private byte[] list;
    private int answer;
    private Note[][] notes;
    private boolean isInitialized = false;
    private Button newQuestionBtn;
    private Button replayBtn;
    private AnswerPickerDialog answerPickerDialog;
    private PlayMode playMode;

    ETScaleUC(Settings settings, DoingTestActivity activity, LinearLayout layout) {
        this.activity = activity;
        initialSettings = settings;
        this.settings = (ETScaleSettings) settings.getSettings();
        this.layout = layout;
        context = activity.getBaseContext();
        midiSounds = new MIDISounds(settings, context, settings.getTimeSignature());
        create();
    }

    void create() {
        list = new byte[2];
        Button scaleEmptyBtn = new Button(context);
        Button rootNoteEmptyBtn = new Button(context);
        TextView scaleCheckChar = new TextView(context);
        TextView rootNoteCheckChar = new TextView(context);
        newQuestionBtn = new Button(context);
        newQuestionBtn.setText(R.string.newQuestion);
        newQuestionBtn.setAllCaps(false);
        newQuestionBtn.setOnClickListener(v -> {
            if (settings.isExpertMode()) {
                rootNoteEmptyBtn.setText("");
                rootNoteCheckChar.setText("");
            }
            list = new byte[2];
            scaleEmptyBtn.setText("");
            scaleCheckChar.setText("");
            playMode = PlayMode.values()[settings.getPlayMode()];
            midiSounds.createMIDIFile(
                    midiSounds.createScaleNotes(playMode, initialSettings.getNoteRange(), settings.getScales(), initialSettings.getNoteLength()));
            midiSounds.playSound(newQuestionBtn, replayBtn);
            answer = midiSounds.getAnswer();
            notes = midiSounds.getNotes();
            isInitialized = true;
        });
        LinearLayout layout1 = new LinearLayout(context);
        layout1.setOrientation(LinearLayout.HORIZONTAL);
        layout1.addView(newQuestionBtn);
        replayBtn = new Button(context);
        replayBtn.setEnabled(false);
        replayBtn.setText(R.string.replay);
        replayBtn.setAllCaps(false);
        replayBtn.setOnClickListener(v -> midiSounds.playSound(newQuestionBtn, replayBtn));
        layout1.addView(replayBtn);
        layout.addView(layout1);

        LinearLayout layout2 = new LinearLayout(context);
        layout2.setOrientation(LinearLayout.HORIZONTAL);
        TextView scaleIs = new TextView(context);
        scaleIs.setText(R.string.scaleIs);

        scaleEmptyBtn.setAllCaps(false);
        scaleEmptyBtn.setBackgroundColor(Color.WHITE);
        scaleEmptyBtn.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - lastClickTimes[0] < 1500) {
                return;
            }
            lastClickTimes[0] = SystemClock.elapsedRealtime();
            if (isInitialized && list[0] == 0) {
                answerPickerDialog = new AnswerPickerDialog(DialogType.Scales, layout2);
                answerPickerDialog.setExpected(answer);
                answerPickerDialog.setText(activity.getString(R.string.chooseScale));
                answerPickerDialog.setRecordList(list);
                answerPickerDialog.setRecordListIndex(0);
                answerPickerDialog.show(activity.getSupportFragmentManager(), "Choose Scales");
            }
        });
        layout2.addView(scaleIs);
        layout2.addView(scaleEmptyBtn);
        layout2.addView(scaleCheckChar);
        layout.addView(layout2);

        if (settings.isExpertMode()) {
            LinearLayout layout3 = new LinearLayout(context);
            layout3.setOrientation(LinearLayout.HORIZONTAL);
            TextView rootNoteIs = new TextView(context);
            rootNoteIs.setText(R.string.rootNoteIs);

            rootNoteEmptyBtn.setAllCaps(false);
            rootNoteEmptyBtn.setBackgroundColor(Color.WHITE);
            rootNoteEmptyBtn.setOnClickListener(v -> {
                if (SystemClock.elapsedRealtime() - lastClickTimes[1] < 1500) {
                    return;
                }
                lastClickTimes[1] = SystemClock.elapsedRealtime();
                if (isInitialized && list[1] == 0) {
                    answerPickerDialog = new AnswerPickerDialog(DialogType.Notes, layout3);
                    answerPickerDialog.setExpected(notes[0][0].pitch);
                    answerPickerDialog.setText(activity.getString(R.string.chooseNote));
                    answerPickerDialog.setRecordList(list);
                    answerPickerDialog.setRecordListIndex(1);
                    answerPickerDialog.show(activity.getSupportFragmentManager(), "chooseNote");
                }
            });
            layout3.addView(rootNoteIs);
            layout3.addView(rootNoteEmptyBtn);
            layout3.addView(rootNoteCheckChar);
            layout.addView(layout3);
        }
    }

    public void destroyDialogs() {
        if (answerPickerDialog != null && answerPickerDialog.getBehavior() != null) {
            answerPickerDialog.getBehavior().setHideable(true);
            answerPickerDialog.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }
}

class ETArpeggioUC {
    private final DoingTestActivity activity;
    private final LinearLayout layout;
    private final Context context;
    private final Settings initialSettings;
    private final ETArpeggioSettings settings;
    private final MIDISounds midiSounds;
    private final long[] lastClickTimes = new long[2];
    private byte[] list;
    private int answer;
    private Note[][] notes;
    private boolean isInitialized = false;
    private Button newQuestionBtn;
    private Button replayBtn;
    private AnswerPickerDialog answerPickerDialog;
    private PlayMode playMode;

    ETArpeggioUC(Settings settings, DoingTestActivity activity, LinearLayout layout) {
        this.activity = activity;
        initialSettings = settings;
        this.settings = (ETArpeggioSettings) settings.getSettings();
        this.layout = layout;
        context = activity.getBaseContext();
        midiSounds = new MIDISounds(settings, context, settings.getTimeSignature());
        create();
    }

    void create() {
        list = new byte[2];
        Button chordEmptyBtn = new Button(context);
        Button rootNoteEmptyBtn = new Button(context);
        TextView chordCheckChar = new TextView(context);
        TextView rootNoteCheckChar = new TextView(context);
        newQuestionBtn = new Button(context);
        newQuestionBtn.setText(R.string.newQuestion);
        newQuestionBtn.setAllCaps(false);
        newQuestionBtn.setOnClickListener(v -> {
            if (settings.isExpertMode()) {
                rootNoteEmptyBtn.setText("");
                rootNoteCheckChar.setText("");
            }
            list = new byte[2];
            chordEmptyBtn.setText("");
            chordCheckChar.setText("");
            playMode = PlayMode.values()[settings.getPlayMode()];
            midiSounds.createMIDIFile(
                    midiSounds.createArpeggioNotes(settings.getChords(), playMode, initialSettings.getNoteRange(), initialSettings.getNoteLength()));
            midiSounds.playSound(newQuestionBtn, replayBtn);
            answer = midiSounds.getAnswer();
            notes = midiSounds.getNotes();
            isInitialized = true;
        });
        LinearLayout layout1 = new LinearLayout(context);
        layout1.setOrientation(LinearLayout.HORIZONTAL);
        layout1.addView(newQuestionBtn);
        replayBtn = new Button(context);
        replayBtn.setEnabled(false);
        replayBtn.setText(R.string.replay);
        replayBtn.setAllCaps(false);
        replayBtn.setOnClickListener(v -> midiSounds.playSound(newQuestionBtn, replayBtn));
        layout1.addView(replayBtn);
        layout.addView(layout1);

        LinearLayout layout2 = new LinearLayout(context);
        layout2.setOrientation(LinearLayout.HORIZONTAL);
        TextView chordIs = new TextView(context);
        chordIs.setText(R.string.chordIs);

        chordEmptyBtn.setAllCaps(false);
        chordEmptyBtn.setBackgroundColor(Color.WHITE);
        chordEmptyBtn.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - lastClickTimes[0] < 1500) {
                return;
            }
            lastClickTimes[0] = SystemClock.elapsedRealtime();
            if (isInitialized && list[0] == 0) {
                answerPickerDialog = new AnswerPickerDialog(DialogType.Scales, layout2);
                answerPickerDialog.setExpected(answer);
                answerPickerDialog.setText(activity.getString(R.string.chooseScale));
                answerPickerDialog.setRecordList(list);
                answerPickerDialog.setRecordListIndex(0);
                answerPickerDialog.show(activity.getSupportFragmentManager(), "Choose Chords");
            }
        });
        layout2.addView(chordIs);
        layout2.addView(chordEmptyBtn);
        layout2.addView(chordCheckChar);
        layout.addView(layout2);

        if (settings.isExpertMode()) {
            LinearLayout layout3 = new LinearLayout(context);
            layout3.setOrientation(LinearLayout.HORIZONTAL);
            TextView rootNoteIs = new TextView(context);
            rootNoteIs.setText(R.string.rootNoteIs);

            rootNoteEmptyBtn.setAllCaps(false);
            rootNoteEmptyBtn.setBackgroundColor(Color.WHITE);
            rootNoteEmptyBtn.setOnClickListener(v -> {
                if (SystemClock.elapsedRealtime() - lastClickTimes[1] < 1500) {
                    return;
                }
                lastClickTimes[1] = SystemClock.elapsedRealtime();
                if (isInitialized && list[1] == 0) {
                    answerPickerDialog = new AnswerPickerDialog(DialogType.Notes, layout3);
                    answerPickerDialog.setExpected(notes[0][0].pitch);
                    answerPickerDialog.setText(activity.getString(R.string.chooseNote));
                    answerPickerDialog.setRecordList(list);
                    answerPickerDialog.setRecordListIndex(1);
                    answerPickerDialog.show(activity.getSupportFragmentManager(), "chooseNote");
                }
            });
            layout3.addView(rootNoteIs);
            layout3.addView(rootNoteEmptyBtn);
            layout3.addView(rootNoteCheckChar);
            layout.addView(layout3);
        }
    }

    public void destroyDialogs() {
        if (answerPickerDialog != null && answerPickerDialog.getBehavior() != null) {
            answerPickerDialog.getBehavior().setHideable(true);
            answerPickerDialog.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }
}

class ETMelodyUC {
    private final DoingTestActivity activity;
    private final LinearLayout layout;
    private final Context context;
    private final Settings initialSettings;
    private final ETMelodySettings settings;
    private final MIDISounds midiSounds;
    private long[] lastClickTimes;
    private byte[] list;
    private Note[][] notes;
    private boolean isInitialized = false;
    private Button newQuestionBtn;
    private Button replayBtn;
    private Button standardBtn;
    private AnswerPickerDialog answerPickerDialog;

    ETMelodyUC(Settings settings, DoingTestActivity activity, LinearLayout layout) {
        this.activity = activity;
        this.layout = layout;
        context = activity.getBaseContext();
        initialSettings = settings;
        this.settings = (ETMelodySettings) settings.getSettings();
        midiSounds = new MIDISounds(settings, context, settings.getTimeSignature());
        create();
    }

    void create() {
        newQuestionBtn = new Button(context);
        newQuestionBtn.setText(R.string.newQuestion);
        newQuestionBtn.setAllCaps(false);
        newQuestionBtn.setOnClickListener(v -> {
            midiSounds.createMIDIFile(
                    midiSounds.createRhythmNotes(settings.getDurations(), settings.getBars(), initialSettings.getNoteLength()));
            midiSounds.playSound(newQuestionBtn, replayBtn, standardBtn);
            notes = midiSounds.getNotes();
            isInitialized = true;
            if (layout.getChildCount() != 1) {
                layout.removeViewAt(1);
                layout.removeViewAt(1);
            }
            list = new byte[notes.length];
            lastClickTimes = new long[notes.length];
            answerPickerDialog = new AnswerPickerDialog(DialogType.Durations);
            TextView answerDurationsText = new TextView(context);
            answerDurationsText.setText(R.string.answerDurations);
            layout.addView(answerDurationsText);

            FlowLayout flowLayout = new FlowLayout(context);
            flowLayout.setLayoutParams(new FlowLayout.LayoutParams(FlowLayout.LayoutParams.MATCH_PARENT, FlowLayout.LayoutParams.WRAP_CONTENT));
            for (int i = 0; i < notes.length; i++) {
                LinearLayout layout2 = new LinearLayout(context);
                layout2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                layout2.setOrientation(LinearLayout.HORIZONTAL);
                TextView spaceChar = new TextView(context);
                Button emptyBtn = new Button(context);
                TextView checkChar = new TextView(context);
                emptyBtn.setAllCaps(false);
                emptyBtn.setBackgroundColor(Color.WHITE);
                emptyBtn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                int answer = Durations.getFMIndex(notes[i][0].duration);
                int index = i;
                emptyBtn.setOnClickListener(view -> {
                    if (SystemClock.elapsedRealtime() - lastClickTimes[index] < 1500) {
                        return;
                    }
                    lastClickTimes[index] = SystemClock.elapsedRealtime();
                    if (isInitialized && list[index] == 0) {
                        answerPickerDialog.setLinearLayout(layout2);
                        answerPickerDialog.setExpected(answer);
                        answerPickerDialog.setText(activity.getString(R.string.chooseDuration));
                        answerPickerDialog.setRecordList(list);
                        answerPickerDialog.setRecordListIndex(index);
                        answerPickerDialog.show(activity.getSupportFragmentManager(), "chooseInterval");
                    }
                });
                layout2.addView(spaceChar);
                layout2.addView(emptyBtn);
                layout2.addView(checkChar);
                flowLayout.addView(layout2);
            }
            layout.addView(flowLayout);
        });
        LinearLayout layout1 = new LinearLayout(context);
        layout1.setOrientation(LinearLayout.HORIZONTAL);
        layout1.addView(newQuestionBtn);
        replayBtn = new Button(context);
        replayBtn.setEnabled(false);
        replayBtn.setText(R.string.replay);
        replayBtn.setAllCaps(false);
        replayBtn.setOnClickListener(v -> midiSounds.playSound(newQuestionBtn, replayBtn));
        layout1.addView(replayBtn);
        standardBtn = new Button(context);
        standardBtn.setAllCaps(false);
        standardBtn.setText(R.string.play4_4Rhythm);
        standardBtn.setOnClickListener(v -> midiSounds.playStandardRhythm(newQuestionBtn, replayBtn, standardBtn));
        layout1.addView(standardBtn);
        layout.addView(layout1);
    }

    public void destroyDialogs() {
        if (answerPickerDialog != null && answerPickerDialog.getBehavior() != null) {
            answerPickerDialog.getBehavior().setHideable(true);
            answerPickerDialog.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }
}

class ETChordUC {
    LinearLayout layout;
    private AnswerPickerDialog answerPickerDialog;

    ETChordUC(Settings settings, DoingTestActivity activity, LinearLayout layout) {

    }

    public void destroyDialogs() {
        if (answerPickerDialog != null && answerPickerDialog.getBehavior() != null) {
            answerPickerDialog.getBehavior().setHideable(true);
            answerPickerDialog.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }
}

class ETProgressionUC {
    LinearLayout layout;
    private AnswerPickerDialog answerPickerDialog;

    ETProgressionUC(Settings settings, DoingTestActivity activity, LinearLayout layout) {

    }

    public void destroyDialogs() {
        if (answerPickerDialog != null && answerPickerDialog.getBehavior() != null) {
            answerPickerDialog.getBehavior().setHideable(true);
            answerPickerDialog.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }
}
