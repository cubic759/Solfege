package com.example.solfege.models;

import android.content.Context;
import android.graphics.Color;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.solfege.DoingTestActivity;
import com.example.solfege.R;
import com.example.solfege.constants.Chord;
import com.example.solfege.constants.DialogType;
import com.example.solfege.constants.Duration;
import com.example.solfege.constants.Interval;
import com.example.solfege.constants.Mode;
import com.example.solfege.constants.Note;
import com.example.solfege.constants.PlayMode;
import com.example.solfege.constants.Scale;
import com.example.solfege.constants.TimeSignature;
import com.example.solfege.constants.Type;
import com.example.solfege.external.FM_Score.FM_Align;
import com.example.solfege.external.FM_Score.FM_Score;
import com.example.solfege.utils.AnswerPickerDialog;
import com.example.solfege.utils.Appearance;
import com.example.solfege.utils.MIDISounds;
import com.example.solfege.utils.Scores;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;

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
        UIControlsOperation(OPERATION_DESTROY_DIALOGS);
    }

    public void UIControlsOperation(int operation) {
        layout.removeAllViews();
        if (testMode == Mode.SIGHT_SINGING) {
            switch (testType) {
                case RHYTHM:
                    if (operation == OPERATION_CREATE) {
                        uiControls = new SSRhythmUC(context, settings, layout);
                    } else {
                        ((SSRhythmUC) uiControls).destroyDialogs();
                    }
                    break;
                case INTERVAL:
                    if (operation == OPERATION_CREATE) {
                        uiControls = new SSIntervalUC(context, settings, layout);
                    } else {
                        ((SSIntervalUC) uiControls).destroyDialogs();
                    }
                    break;
                case SCALE:
                    if (operation == OPERATION_CREATE) {
                        uiControls = new SSScaleUC(context, settings, layout);
                    } else {
                        ((SSScaleUC) uiControls).destroyDialogs();
                    }
                    break;
                case ARPEGGIO:
                    if (operation == OPERATION_CREATE) {
                        uiControls = new SSArpeggioUC(context, settings, layout);
                    } else {
                        ((SSArpeggioUC) uiControls).destroyDialogs();
                    }
                    break;
                case MELODY:
                    if (operation == OPERATION_CREATE) {
                        uiControls = new SSMelodyUC(context, settings, layout);
                    } else {
                        ((SSMelodyUC) uiControls).destroyDialogs();
                    }
                    break;
                case CHORD:
                    if (operation == OPERATION_CREATE) {
                        uiControls = new SSChordUC(context, settings, layout);
                    } else {
                        ((SSChordUC) uiControls).destroyDialogs();
                    }
                    break;
                case PROGRESSION:
                    if (operation == OPERATION_CREATE) {
                        uiControls = new SSProgressionUC(context, settings, layout);
                    } else {
                        ((SSProgressionUC) uiControls).destroyDialogs();
                    }
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
    private final Context context;
    private final Settings initialSettings;
    private final SSRhythmSettings settings;
    private final LinearLayout layout;
    private Button newQuestionBtn, replayBtn;
    private final MIDISounds midiSounds;

    SSRhythmUC(Context context, Settings settings, LinearLayout layout) {
        this.context = context;
        initialSettings = settings;
        this.settings = (SSRhythmSettings) settings.getSettings();
        this.layout = layout;
        midiSounds = new MIDISounds(initialSettings, context, initialSettings.getTimeSignature(), initialSettings.getNoteLength());
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
            midiSounds.createRhythmNotes(settings.getDurations(), settings.getBars());
            midiSounds.createMIDIFile(MIDISounds.MAKE_QUESTION);
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

    void destroyDialogs() {
        if (midiSounds != null) {
            midiSounds.stop();
        }
    }
}

class SSIntervalUC {
    private final LinearLayout layout;
    private final Context context;
    private final Settings initialSettings;
    private final SSIntervalSettings settings;
    private final MIDISounds midiSounds;
    private int answer;
    private com.example.solfege.models.Note[][] notes;
    private Button newQuestionBtn, replayBtn;
    private PlayMode playMode;

    SSIntervalUC(Context context, Settings settings, LinearLayout layout) {
        this.layout = layout;
        this.context = context;
        initialSettings = settings;
        this.settings = (SSIntervalSettings) settings.getSettings();
        midiSounds = new MIDISounds(initialSettings, context, initialSettings.getTimeSignature(), initialSettings.getNoteLength());
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
            midiSounds.createIntervalNotes(playMode, initialSettings.getNoteRange(), settings.getIntervals());
            midiSounds.createMIDIFile(MIDISounds.MAKE_QUESTION);
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
                    Note.getNote(pitch1), Interval.getIntervals(context)[answer],
                    Note.getNote(pitch2)));
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

    void destroyDialogs() {
        if (midiSounds != null) {
            midiSounds.stop();
        }
    }
}

class SSScaleUC {
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
        midiSounds = new MIDISounds(initialSettings, context, initialSettings.getTimeSignature(), initialSettings.getNoteLength());
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
            midiSounds.createScaleNotes(playMode, initialSettings.getNoteRange(), settings.getScales());
            midiSounds.createMIDIFile(MIDISounds.MAKE_QUESTION);
            int answer = midiSounds.getAnswer();
            com.example.solfege.models.Note[][] notes = midiSounds.getNotes();
            s.clearStaveNotes();
            Scores.setNotes(s, notes);

            if (layout.getChildAt(1) == null) {
                layout.addView(frameLayout);
            }
            text.setText(String.format(context.getString(R.string.scaleNoteInformation), Note.getNote(notes[0][0].pitch), Scale.getScales(context)[answer]));
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

    void destroyDialogs() {
        if (midiSounds != null) {
            midiSounds.stop();
        }
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
        midiSounds = new MIDISounds(initialSettings, context, initialSettings.getTimeSignature(), initialSettings.getNoteLength());
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
            midiSounds.createArpeggioNotes(settings.getChords(), PlayMode.values()[settings.getPlayMode()], initialSettings.getNoteRange());
            midiSounds.createMIDIFile(MIDISounds.MAKE_QUESTION);
            s.clearStaveNotes();
            com.example.solfege.models.Note[][] notes = midiSounds.getNotes();
            int answer = midiSounds.getAnswer();
            Scores.setNotes(s, notes);
            if (layout.getChildAt(1) == null) {
                layout.addView(frameLayout);
            }
            text.setText(String.format(context.getString(R.string.scaleNoteInformation), Note.getNote(notes[0][0].pitch), Chord.getChords(context)[answer]));
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

    void destroyDialogs() {
        if (midiSounds != null) {
            midiSounds.stop();
        }
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
        midiSounds = new MIDISounds(initialSettings, context, initialSettings.getTimeSignature(), initialSettings.getNoteLength());
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
            midiSounds.createMelodyNotes(settings.getDurations(), settings.getScales(), initialSettings.getNoteRange(), settings.getBars());
            midiSounds.createMIDIFile(MIDISounds.MAKE_QUESTION);
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

    void destroyDialogs() {
        if (midiSounds != null) {
            midiSounds.stop();
        }
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
        midiSounds = new MIDISounds(initialSettings, context, initialSettings.getTimeSignature(), initialSettings.getNoteLength());
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
            midiSounds.createChordNotes(settings.getChords(), initialSettings.getNoteRange());
            midiSounds.createMIDIFile(MIDISounds.MAKE_QUESTION);
            s.clearStaveNotes();
            com.example.solfege.models.Note[][] notes = midiSounds.getNotes();
            int answer = midiSounds.getAnswer();
            Scores.setNotes(s, notes);
            if (layout.getChildAt(1) == null) {
                layout.addView(frameLayout);
            }
            text.setText(String.format(context.getString(R.string.scaleNoteInformation), Note.getNote(notes[0][0].pitch), Chord.getChords(context)[answer]));
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

    void destroyDialogs() {
        if (midiSounds != null) {
            midiSounds.stop();
        }
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
        midiSounds = new MIDISounds(initialSettings, context, initialSettings.getTimeSignature(), initialSettings.getNoteLength());
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
            midiSounds.createProgressionNotes(settings.getScales(), settings.getChordTypes(), initialSettings.getNoteRange());
            midiSounds.createMIDIFile(MIDISounds.MAKE_QUESTION);
            s.clearStaveNotes();
            int[] timeSignature = TimeSignature.getTimeSignature(initialSettings.getTimeSignature());
            s.setTimeSignature(timeSignature[0], timeSignature[1]);
            com.example.solfege.models.Note[][] notes = midiSounds.getNotes();
            int[][] answers = midiSounds.getAnswers();
            Scores.setNotes(s, notes);
            if (layout.getChildAt(1) == null) {
                layout.addView(frameLayout);
            }
            StringBuilder string = new StringBuilder();
            string.append(String.format(context.getString(R.string.scaleNoteInformation), Note.getNote(answers[0][0]), Scale.getProgressionScales(context)[answers[0][1]]));
            for (int i = 1; i < answers.length; i++) {
                string.append("\n");
                string.append(String.format(context.getString(R.string.progressionInformation),
                        Note.getNote(answers[i][0]), Chord.getChordTypes(context)[answers[i][1]],
                        Scale.DEGREES[answers[i][2]]));
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

    void destroyDialogs() {
        if (midiSounds != null) {
            midiSounds.stop();
        }
    }
}

class ETRhythmUC {
    private final DoingTestActivity activity;
    private final LinearLayout layout;
    private final Context context;
    private final ETRhythmSettings settings;
    private final MIDISounds midiSounds;
    private long lastClickTimes;
    private byte[] list;
    private com.example.solfege.models.Note[][] notes;
    private boolean isInitialized = false;
    private boolean isAdded = false;
    private Button[] buttons;
    private Button newQuestionBtn;
    private Button replayBtn;
    private Button standardBtn;
    private AnswerPickerDialog durationPickerDialog;

    ETRhythmUC(Settings settings, DoingTestActivity activity, LinearLayout layout) {
        this.activity = activity;
        this.layout = layout;
        context = activity.getBaseContext();
        midiSounds = new MIDISounds(settings, context, settings.getTimeSignature(), settings.getNoteLength());
        this.settings = (ETRhythmSettings) settings.getSettings();
        create();
    }

    void create() {
        newQuestionBtn = new Button(context);
        newQuestionBtn.setText(R.string.newQuestion);
        newQuestionBtn.setAllCaps(false);
        newQuestionBtn.setOnClickListener(v -> {
            midiSounds.createRhythmNotes(settings.getDurations(), settings.getBars());
            notes = midiSounds.getNotes();
            buttons = new Button[notes.length + 3];
            midiSounds.createMIDIFile(MIDISounds.MAKE_QUESTION);
            isInitialized = true;
            if (layout.getChildCount() != 1) {
                layout.removeViewAt(2);
            }
            list = new byte[notes.length];
            if (!isAdded) {
                durationPickerDialog = new AnswerPickerDialog(DialogType.Durations);
                durationPickerDialog.setText(activity.getString(R.string.chooseDuration));
                TextView answerDurationsText = new TextView(context);
                answerDurationsText.setText(R.string.answerDurations);
                layout.addView(answerDurationsText);
                isAdded = true;
            }
            ScrollView scrollView = new ScrollView(context);
            LinearLayout mainLayout = new LinearLayout(context);
            mainLayout.setOrientation(LinearLayout.VERTICAL);
            buttons[0] = newQuestionBtn;
            buttons[1] = replayBtn;
            buttons[2] = standardBtn;
            int count = 3;
            for (int i = 0; i < notes.length; i++) {
                int index = i;
                LinearLayout layout2 = new LinearLayout(context);
                layout2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                layout2.setOrientation(LinearLayout.HORIZONTAL);
                TextView durationIs = new TextView(context);
                durationIs.setText(R.string.durationIs);
                Button emptyBtn = new Button(context);
                TextView checkChar = new TextView(context);
                emptyBtn.setAllCaps(false);
                emptyBtn.setBackground(Appearance.getStroke(ContextCompat.getColor(context, R.color.empty)));
                emptyBtn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                int answer = Duration.getFMIndex(notes[i][0].duration);
                emptyBtn.setOnClickListener(view -> {
                    if (SystemClock.elapsedRealtime() - lastClickTimes < 1500) {
                        return;
                    }
                    lastClickTimes = SystemClock.elapsedRealtime();
                    if (isInitialized && list[index] == 0) {
                        durationPickerDialog.setOutputButton(emptyBtn);
                        durationPickerDialog.setOutputText(checkChar);
                        durationPickerDialog.setExpected(answer);
                        durationPickerDialog.setRecordList(list);
                        durationPickerDialog.setRecordListIndex(index);
                        durationPickerDialog.show(activity.getSupportFragmentManager(), "chooseDuration");
                    }
                });
                layout2.addView(durationIs);
                layout2.addView(emptyBtn);
                layout2.addView(checkChar);
                buttons[count] = emptyBtn;
                count++;
                mainLayout.addView(layout2);
            }
            scrollView.addView(mainLayout);
            layout.addView(scrollView);
            midiSounds.playSound(buttons);
        });
        LinearLayout layout1 = new LinearLayout(context);
        layout1.setOrientation(LinearLayout.HORIZONTAL);
        layout1.addView(newQuestionBtn);
        replayBtn = new Button(context);
        replayBtn.setEnabled(false);
        replayBtn.setText(R.string.replay);
        replayBtn.setAllCaps(false);
        replayBtn.setOnClickListener(v -> midiSounds.playSound(buttons));
        layout1.addView(replayBtn);
        standardBtn = new Button(context);
        standardBtn.setAllCaps(false);
        standardBtn.setText(R.string.play4_4OnC);
        midiSounds.createStandardRhythmNotes();
        midiSounds.createMIDIFile(MIDISounds.MAKE_STANDARD_RHYTHM);
        standardBtn.setOnClickListener(v -> midiSounds.playStandardRhythm(buttons));
        layout1.addView(standardBtn);
        layout.addView(layout1);
    }

    public void destroyDialogs() {
        midiSounds.stop();
        if (durationPickerDialog != null && durationPickerDialog.getBehavior() != null) {
            durationPickerDialog.getBehavior().setHideable(true);
            durationPickerDialog.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
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
    private long lastClickTimes;
    private byte[] list;
    private int answer;
    private com.example.solfege.models.Note[][] notes;
    private boolean isInitialized = false;
    private boolean isAdded = false;
    private final Button[] buttons = new Button[6];
    private AnswerPickerDialog notePickerDialog;
    private AnswerPickerDialog intervalPickerDialog;
    private PlayMode playMode;

    ETIntervalUC(Settings settings, DoingTestActivity activity, LinearLayout layout) {
        this.activity = activity;
        initialSettings = settings;
        this.settings = (ETIntervalSettings) settings.getSettings();
        this.layout = layout;
        context = activity.getBaseContext();
        midiSounds = new MIDISounds(initialSettings, context, initialSettings.getTimeSignature(), initialSettings.getNoteLength());
        create();
    }

    void create() {
        LinearLayout layout2 = new LinearLayout(context);
        LinearLayout layout3 = new LinearLayout(context);
        LinearLayout layout4 = new LinearLayout(context);
        Button intervalEmptyBtn = new Button(context);
        Button lowerNoteEmptyBtn = new Button(context);
        Button higherNoteEmptyBtn = new Button(context);
        buttons[3] = intervalEmptyBtn;
        buttons[4] = lowerNoteEmptyBtn;
        buttons[5] = higherNoteEmptyBtn;
        TextView intervalCheckChar = new TextView(context);
        TextView lowerNoteCheckChar = new TextView(context);
        TextView higherNoteCheckChar = new TextView(context);
        Button newQuestionBtn = new Button(context);
        newQuestionBtn.setText(R.string.newQuestion);
        newQuestionBtn.setAllCaps(false);
        newQuestionBtn.setOnClickListener(v -> {
            list = new byte[3];
            intervalEmptyBtn.setText("");
            intervalEmptyBtn.setBackground(Appearance.getStroke(ContextCompat.getColor(context, R.color.empty)));
            intervalCheckChar.setText("");
            if (settings.isExpertMode()) {
                lowerNoteEmptyBtn.setText("");
                lowerNoteEmptyBtn.setBackground(Appearance.getStroke(ContextCompat.getColor(context, R.color.empty)));
                lowerNoteCheckChar.setText("");
                higherNoteEmptyBtn.setText("");
                higherNoteEmptyBtn.setBackground(Appearance.getStroke(ContextCompat.getColor(context, R.color.empty)));
                higherNoteCheckChar.setText("");
            }
            if (!isAdded) {
                intervalPickerDialog = new AnswerPickerDialog(DialogType.Intervals);
                intervalPickerDialog.setText(activity.getString(R.string.chooseInterval));
                layout.addView(layout2);
                if (settings.isExpertMode()) {
                    notePickerDialog = new AnswerPickerDialog(DialogType.Notes);
                    notePickerDialog.setText(activity.getString(R.string.chooseNote));
                    layout.addView(layout3);
                    layout.addView(layout4);
                }
                isAdded = true;
            }
            playMode = PlayMode.values()[settings.getPlayMode()];
            if (playMode == PlayMode.RANDOM) {
                playMode = PlayMode.values()[(int) (3 * Math.random())];
            }
            midiSounds.createIntervalNotes(playMode, initialSettings.getNoteRange(), settings.getIntervals());
            midiSounds.createMIDIFile(MIDISounds.MAKE_QUESTION);
            midiSounds.playSound(buttons);
            answer = midiSounds.getAnswer();
            notes = midiSounds.getNotes();
            isInitialized = true;
        });
        LinearLayout layout1 = new LinearLayout(context);
        layout1.setOrientation(LinearLayout.HORIZONTAL);
        layout1.addView(newQuestionBtn);
        Button replayBtn = new Button(context);
        replayBtn.setEnabled(false);
        replayBtn.setText(R.string.replay);
        replayBtn.setAllCaps(false);
        replayBtn.setOnClickListener(v -> midiSounds.playSound(buttons));
        layout1.addView(replayBtn);
        if (settings.isExpertMode()) {
            Button centralCBtn = new Button(context);
            centralCBtn.setEnabled(false);
            centralCBtn.setText(R.string.playCentralC);
            centralCBtn.setAllCaps(false);
            midiSounds.createCentralCNote();
            midiSounds.createMIDIFile(MIDISounds.MAKE_CENTRAL_C);
            centralCBtn.setOnClickListener(v -> midiSounds.playCentralC(buttons));
            layout1.addView(centralCBtn);
            buttons[2] = centralCBtn;
        }
        buttons[0] = newQuestionBtn;
        buttons[1] = replayBtn;
        layout.addView(layout1);

        layout2.setOrientation(LinearLayout.HORIZONTAL);
        TextView intervalIs = new TextView(context);
        intervalIs.setText(R.string.intervalIs);
        intervalEmptyBtn.setAllCaps(false);
        intervalEmptyBtn.setBackground(Appearance.getStroke(ContextCompat.getColor(context, R.color.empty)));
        intervalEmptyBtn.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - lastClickTimes < 1500) {
                return;
            }
            lastClickTimes = SystemClock.elapsedRealtime();
            if (isInitialized && list[0] == 0) {
                intervalPickerDialog.setOutputText(intervalCheckChar);
                intervalPickerDialog.setOutputButton(intervalEmptyBtn);
                intervalPickerDialog.setExpected(answer);
                intervalPickerDialog.setRecordList(list);
                intervalPickerDialog.setRecordListIndex(0);
                intervalPickerDialog.show(activity.getSupportFragmentManager(), "chooseInterval");
            }
        });
        layout2.addView(intervalIs);
        layout2.addView(intervalEmptyBtn);
        layout2.addView(intervalCheckChar);

        if (settings.isExpertMode()) {
            layout3.setOrientation(LinearLayout.HORIZONTAL);
            TextView lowerNoteIs = new TextView(context);
            lowerNoteIs.setText(R.string.lowerNoteIs);
            lowerNoteEmptyBtn.setAllCaps(false);
            lowerNoteEmptyBtn.setBackground(Appearance.getStroke(ContextCompat.getColor(context, R.color.empty)));
            lowerNoteEmptyBtn.setOnClickListener(v -> {
                if (SystemClock.elapsedRealtime() - lastClickTimes < 1500) {
                    return;
                }
                lastClickTimes = SystemClock.elapsedRealtime();
                if (isInitialized && list[1] == 0) {
                    notePickerDialog.setOutputButton(lowerNoteEmptyBtn);
                    notePickerDialog.setOutputText(lowerNoteCheckChar);
                    notePickerDialog.setExpected(notes[0][0].pitch);
                    notePickerDialog.setRecordList(list);
                    notePickerDialog.setRecordListIndex(1);
                    notePickerDialog.show(activity.getSupportFragmentManager(), "chooseNote");
                }
            });
            layout3.addView(lowerNoteIs);
            layout3.addView(lowerNoteEmptyBtn);
            layout3.addView(lowerNoteCheckChar);

            layout4.setOrientation(LinearLayout.HORIZONTAL);
            TextView higherNoteIs = new TextView(context);
            higherNoteIs.setText(R.string.higherNoteIs);
            higherNoteEmptyBtn.setAllCaps(false);
            higherNoteEmptyBtn.setBackground(Appearance.getStroke(ContextCompat.getColor(context, R.color.empty)));
            higherNoteEmptyBtn.setOnClickListener(v -> {
                if (SystemClock.elapsedRealtime() - lastClickTimes < 1500) {
                    return;
                }
                lastClickTimes = SystemClock.elapsedRealtime();
                if (isInitialized && list[2] == 0) {
                    notePickerDialog.setOutputText(higherNoteCheckChar);
                    notePickerDialog.setOutputButton(higherNoteEmptyBtn);
                    notePickerDialog.setExpected(notes[1][0].pitch);
                    notePickerDialog.setRecordList(list);
                    notePickerDialog.setRecordListIndex(2);
                    notePickerDialog.show(activity.getSupportFragmentManager(), "chooseNote");
                }
            });
            layout4.addView(higherNoteIs);
            layout4.addView(higherNoteEmptyBtn);
            layout4.addView(higherNoteCheckChar);
        }
    }

    public void destroyDialogs() {
        midiSounds.stop();
        if (intervalPickerDialog != null && intervalPickerDialog.getBehavior() != null) {
            intervalPickerDialog.getBehavior().setHideable(true);
            intervalPickerDialog.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
        if (notePickerDialog != null && notePickerDialog.getBehavior() != null) {
            notePickerDialog.getBehavior().setHideable(true);
            notePickerDialog.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
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
    private long lastClickTimes;
    private byte[] list;
    private int answer;
    private com.example.solfege.models.Note[][] notes;
    private boolean isInitialized = false;
    private boolean isAdded = false;
    private final Button[] buttons = new Button[5];
    private AnswerPickerDialog scalePickerDialog;
    private AnswerPickerDialog notePickerDialog;
    private PlayMode playMode;

    ETScaleUC(Settings settings, DoingTestActivity activity, LinearLayout layout) {
        this.activity = activity;
        initialSettings = settings;
        this.settings = (ETScaleSettings) settings.getSettings();
        this.layout = layout;
        context = activity.getBaseContext();
        midiSounds = new MIDISounds(initialSettings, context, initialSettings.getTimeSignature(), initialSettings.getNoteLength());
        create();
    }

    void create() {
        LinearLayout layout2 = new LinearLayout(context);
        LinearLayout layout3 = new LinearLayout(context);
        Button scaleEmptyBtn = new Button(context);
        Button rootNoteEmptyBtn = new Button(context);
        buttons[3] = scaleEmptyBtn;
        buttons[4] = rootNoteEmptyBtn;
        TextView scaleCheckChar = new TextView(context);
        TextView rootNoteCheckChar = new TextView(context);
        Button newQuestionBtn = new Button(context);
        newQuestionBtn.setText(R.string.newQuestion);
        newQuestionBtn.setAllCaps(false);
        newQuestionBtn.setOnClickListener(v -> {
            list = new byte[2];
            scaleEmptyBtn.setText("");
            scaleEmptyBtn.setBackground(Appearance.getStroke(ContextCompat.getColor(context, R.color.empty)));
            scaleCheckChar.setText("");
            if (settings.isExpertMode()) {
                rootNoteEmptyBtn.setText("");
                rootNoteEmptyBtn.setBackground(Appearance.getStroke(ContextCompat.getColor(context, R.color.empty)));
                rootNoteCheckChar.setText("");
            }
            if (!isAdded) {
                scalePickerDialog = new AnswerPickerDialog(DialogType.Scales);
                scalePickerDialog.setText(activity.getString(R.string.chooseScale));
                layout.addView(layout2);
                if (settings.isExpertMode()) {
                    notePickerDialog = new AnswerPickerDialog(DialogType.Notes);
                    notePickerDialog.setText(activity.getString(R.string.chooseNote));
                    layout.addView(layout3);
                }
                isAdded = true;
            }
            playMode = PlayMode.values()[settings.getPlayMode()];
            midiSounds.createScaleNotes(playMode, initialSettings.getNoteRange(), settings.getScales());
            midiSounds.createMIDIFile(MIDISounds.MAKE_QUESTION);
            midiSounds.playSound(buttons);
            answer = midiSounds.getAnswer();
            notes = midiSounds.getNotes();
            isInitialized = true;
        });
        LinearLayout layout1 = new LinearLayout(context);
        layout1.setOrientation(LinearLayout.HORIZONTAL);
        layout1.addView(newQuestionBtn);
        Button replayBtn = new Button(context);
        replayBtn.setEnabled(false);
        replayBtn.setText(R.string.replay);
        replayBtn.setAllCaps(false);
        replayBtn.setOnClickListener(v -> midiSounds.playSound(buttons));
        layout1.addView(replayBtn);
        if (settings.isExpertMode()) {
            Button centralCBtn = new Button(context);
            centralCBtn.setEnabled(false);
            centralCBtn.setAllCaps(false);
            centralCBtn.setText(R.string.playCentralC);
            midiSounds.createCentralCNote();
            midiSounds.createMIDIFile(MIDISounds.MAKE_CENTRAL_C);
            centralCBtn.setOnClickListener(v -> midiSounds.playCentralC(buttons));
            layout1.addView(centralCBtn);
            buttons[2] = centralCBtn;
        }
        buttons[0] = newQuestionBtn;
        buttons[1] = replayBtn;
        layout.addView(layout1);

        layout2.setOrientation(LinearLayout.HORIZONTAL);
        TextView scaleIs = new TextView(context);
        scaleIs.setText(R.string.scaleIs);
        scaleEmptyBtn.setAllCaps(false);
        scaleEmptyBtn.setBackground(Appearance.getStroke(ContextCompat.getColor(context, R.color.empty)));
        scaleEmptyBtn.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - lastClickTimes < 1500) {
                return;
            }
            lastClickTimes = SystemClock.elapsedRealtime();
            if (isInitialized && list[0] == 0) {
                scalePickerDialog.setOutputText(scaleCheckChar);
                scalePickerDialog.setOutputButton(scaleEmptyBtn);
                scalePickerDialog.setExpected(answer);
                scalePickerDialog.setAscending(settings.getPlayMode());
                scalePickerDialog.setRecordList(list);
                scalePickerDialog.setRecordListIndex(0);
                scalePickerDialog.show(activity.getSupportFragmentManager(), "Choose Scales");
            }
        });
        layout2.addView(scaleIs);
        layout2.addView(scaleEmptyBtn);
        layout2.addView(scaleCheckChar);

        if (settings.isExpertMode()) {
            layout3.setOrientation(LinearLayout.HORIZONTAL);
            TextView rootNoteIs = new TextView(context);
            rootNoteIs.setText(R.string.rootNoteIs);
            rootNoteEmptyBtn.setAllCaps(false);
            rootNoteEmptyBtn.setBackground(Appearance.getStroke(ContextCompat.getColor(context, R.color.empty)));
            rootNoteEmptyBtn.setOnClickListener(v -> {
                if (SystemClock.elapsedRealtime() - lastClickTimes < 1500) {
                    return;
                }
                lastClickTimes = SystemClock.elapsedRealtime();
                if (isInitialized && list[1] == 0) {
                    notePickerDialog.setOutputButton(rootNoteEmptyBtn);
                    notePickerDialog.setOutputText(rootNoteCheckChar);
                    notePickerDialog.setExpected(notes[0][0].pitch);
                    notePickerDialog.setRecordList(list);
                    notePickerDialog.setRecordListIndex(1);
                    notePickerDialog.show(activity.getSupportFragmentManager(), "chooseNote");
                }
            });
            layout3.addView(rootNoteIs);
            layout3.addView(rootNoteEmptyBtn);
            layout3.addView(rootNoteCheckChar);
        }
    }

    public void destroyDialogs() {
        if (midiSounds != null) {
            midiSounds.stop();
        }
        if (scalePickerDialog != null && scalePickerDialog.getBehavior() != null) {
            scalePickerDialog.getBehavior().setHideable(true);
            scalePickerDialog.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
        if (notePickerDialog != null && notePickerDialog.getBehavior() != null) {
            notePickerDialog.getBehavior().setHideable(true);
            notePickerDialog.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
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
    private long lastClickTimes;
    private byte[] list;
    private int answer;
    private com.example.solfege.models.Note[][] notes;
    private boolean isInitialized = false;
    private boolean isAdded = false;
    private final Button[] buttons = new Button[5];
    private AnswerPickerDialog arpeggioPickerDialog;
    private AnswerPickerDialog notePickerDialog;
    private PlayMode playMode;

    ETArpeggioUC(Settings settings, DoingTestActivity activity, LinearLayout layout) {
        this.activity = activity;
        initialSettings = settings;
        this.settings = (ETArpeggioSettings) settings.getSettings();
        this.layout = layout;
        context = activity.getBaseContext();
        midiSounds = new MIDISounds(initialSettings, context, initialSettings.getTimeSignature(), initialSettings.getNoteLength());
        create();
    }

    void create() {
        LinearLayout layout2 = new LinearLayout(context);
        LinearLayout layout3 = new LinearLayout(context);
        Button chordEmptyBtn = new Button(context);
        Button rootNoteEmptyBtn = new Button(context);
        buttons[3] = chordEmptyBtn;
        buttons[4] = rootNoteEmptyBtn;
        TextView chordCheckChar = new TextView(context);
        TextView rootNoteCheckChar = new TextView(context);
        Button newQuestionBtn = new Button(context);
        newQuestionBtn.setText(R.string.newQuestion);
        newQuestionBtn.setAllCaps(false);
        newQuestionBtn.setOnClickListener(v -> {
            list = new byte[2];
            chordEmptyBtn.setText("");
            chordEmptyBtn.setBackground(Appearance.getStroke(ContextCompat.getColor(context, R.color.empty)));
            chordCheckChar.setText("");
            if (settings.isExpertMode()) {
                rootNoteEmptyBtn.setText("");
                rootNoteEmptyBtn.setBackground(Appearance.getStroke(ContextCompat.getColor(context, R.color.empty)));
                rootNoteCheckChar.setText("");
            }
            if (!isAdded) {
                arpeggioPickerDialog = new AnswerPickerDialog(DialogType.Chords);
                arpeggioPickerDialog.setText(activity.getString(R.string.chooseChord));
                layout.addView(layout2);
                if (settings.isExpertMode()) {
                    notePickerDialog = new AnswerPickerDialog(DialogType.Notes);
                    notePickerDialog.setText(activity.getString(R.string.chooseNote));
                    layout.addView(layout3);
                }
                isAdded = true;
            }
            playMode = PlayMode.values()[settings.getPlayMode()];
            midiSounds.createArpeggioNotes(settings.getChords(), playMode, initialSettings.getNoteRange());
            midiSounds.createMIDIFile(MIDISounds.MAKE_QUESTION);
            midiSounds.playSound(buttons);
            answer = midiSounds.getAnswer();
            notes = midiSounds.getNotes();
            isInitialized = true;
        });
        LinearLayout layout1 = new LinearLayout(context);
        layout1.setOrientation(LinearLayout.HORIZONTAL);
        layout1.addView(newQuestionBtn);
        Button replayBtn = new Button(context);
        replayBtn.setEnabled(false);
        replayBtn.setText(R.string.replay);
        replayBtn.setAllCaps(false);
        replayBtn.setOnClickListener(v -> midiSounds.playSound(buttons));
        layout1.addView(replayBtn);
        if (settings.isExpertMode()) {
            Button centralCBtn = new Button(context);
            centralCBtn.setEnabled(false);
            centralCBtn.setAllCaps(false);
            centralCBtn.setText(R.string.playCentralC);
            midiSounds.createCentralCNote();
            midiSounds.createMIDIFile(MIDISounds.MAKE_CENTRAL_C);
            centralCBtn.setOnClickListener(v -> midiSounds.playCentralC(buttons));
            layout1.addView(centralCBtn);
            buttons[2] = centralCBtn;
        }
        buttons[0] = newQuestionBtn;
        buttons[1] = replayBtn;
        layout.addView(layout1);

        layout2.setOrientation(LinearLayout.HORIZONTAL);
        TextView chordIs = new TextView(context);
        chordIs.setText(R.string.chordIs);
        chordEmptyBtn.setAllCaps(false);
        chordEmptyBtn.setBackground(Appearance.getStroke(ContextCompat.getColor(context, R.color.empty)));
        chordEmptyBtn.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - lastClickTimes < 1500) {
                return;
            }
            lastClickTimes = SystemClock.elapsedRealtime();
            if (isInitialized && list[0] == 0) {
                arpeggioPickerDialog.setOutputButton(chordEmptyBtn);
                arpeggioPickerDialog.setOutputText(chordCheckChar);
                arpeggioPickerDialog.setExpected(answer);
                arpeggioPickerDialog.setRecordList(list);
                arpeggioPickerDialog.setRecordListIndex(0);
                arpeggioPickerDialog.show(activity.getSupportFragmentManager(), "Choose Chords");
            }
        });
        layout2.addView(chordIs);
        layout2.addView(chordEmptyBtn);
        layout2.addView(chordCheckChar);

        if (settings.isExpertMode()) {
            layout3.setOrientation(LinearLayout.HORIZONTAL);
            TextView rootNoteIs = new TextView(context);
            rootNoteIs.setText(R.string.rootNoteIs);
            rootNoteEmptyBtn.setAllCaps(false);
            rootNoteEmptyBtn.setBackground(Appearance.getStroke(ContextCompat.getColor(context, R.color.empty)));
            rootNoteEmptyBtn.setOnClickListener(v -> {
                if (SystemClock.elapsedRealtime() - lastClickTimes < 1500) {
                    return;
                }
                lastClickTimes = SystemClock.elapsedRealtime();
                if (isInitialized && list[1] == 0) {
                    notePickerDialog.setOutputText(rootNoteCheckChar);
                    notePickerDialog.setOutputButton(rootNoteEmptyBtn);
                    notePickerDialog.setExpected(notes[0][0].pitch);
                    notePickerDialog.setRecordList(list);
                    notePickerDialog.setRecordListIndex(1);
                    notePickerDialog.show(activity.getSupportFragmentManager(), "chooseNote");
                }
            });
            layout3.addView(rootNoteIs);
            layout3.addView(rootNoteEmptyBtn);
            layout3.addView(rootNoteCheckChar);
        }
    }

    public void destroyDialogs() {
        if (midiSounds != null) {
            midiSounds.stop();
        }
        if (arpeggioPickerDialog != null && arpeggioPickerDialog.getBehavior() != null) {
            arpeggioPickerDialog.getBehavior().setHideable(true);
            arpeggioPickerDialog.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
        if (notePickerDialog != null && notePickerDialog.getBehavior() != null) {
            notePickerDialog.getBehavior().setHideable(true);
            notePickerDialog.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
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
    private long lastClickTimes;
    private byte[][] list;
    private com.example.solfege.models.Note[][] notes;
    private boolean isInitialized = false;
    private boolean isAdded = false;
    private Button[] buttons;
    private Button newQuestionBtn;
    private Button replayBtn;
    private Button standardBtn;
    private AnswerPickerDialog durationPickerDialog;
    private AnswerPickerDialog notePickerDialog;

    ETMelodyUC(Settings settings, DoingTestActivity activity, LinearLayout layout) {
        this.activity = activity;
        this.layout = layout;
        context = activity.getBaseContext();
        initialSettings = settings;
        this.settings = (ETMelodySettings) settings.getSettings();
        midiSounds = new MIDISounds(initialSettings, context, initialSettings.getTimeSignature(), initialSettings.getNoteLength());
        create();
    }

    void create() {
        newQuestionBtn = new Button(context);
        newQuestionBtn.setText(R.string.newQuestion);
        newQuestionBtn.setAllCaps(false);
        newQuestionBtn.setOnClickListener(v -> {
            midiSounds.createMelodyNotes(settings.getDurations(), settings.getScales(), initialSettings.getNoteRange(), settings.getBars());
            notes = midiSounds.getNotes();
            buttons = new Button[notes.length * 2 + 3];
            midiSounds.createMIDIFile(MIDISounds.MAKE_QUESTION);
            isInitialized = true;
            if (layout.getChildCount() != 1) {
                layout.removeViewAt(2);
            }
            list = new byte[notes.length][2];
            if (!isAdded) {
                durationPickerDialog = new AnswerPickerDialog(DialogType.Durations);
                durationPickerDialog.setText(activity.getString(R.string.chooseDuration));
                notePickerDialog = new AnswerPickerDialog(DialogType.Notes);
                notePickerDialog.setText(activity.getString(R.string.chooseNote));
                TextView answerDurationsText = new TextView(context);
                answerDurationsText.setText(R.string.answerDurationsAndNotes);
                layout.addView(answerDurationsText);
                isAdded = true;
            }
            ScrollView scrollView = new ScrollView(context);
            LinearLayout mainLayout = new LinearLayout(context);
            mainLayout.setOrientation(LinearLayout.VERTICAL);
            buttons[0] = newQuestionBtn;
            buttons[1] = replayBtn;
            buttons[2] = standardBtn;
            int count = 3;
            for (int i = 0; i < notes.length; i++) {
                int index = i;
                LinearLayout layout2 = new LinearLayout(context);
                layout2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                layout2.setOrientation(LinearLayout.HORIZONTAL);
                TextView durationIs = new TextView(context);
                durationIs.setText(R.string.durationIs);
                Button emptyBtn = new Button(context);
                TextView checkChar = new TextView(context);
                emptyBtn.setAllCaps(false);
                emptyBtn.setBackground(Appearance.getStroke(ContextCompat.getColor(context, R.color.empty)));
                emptyBtn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                int answer = Duration.getFMIndex(notes[i][0].duration);
                emptyBtn.setOnClickListener(view -> {
                    if (SystemClock.elapsedRealtime() - lastClickTimes < 1500) {
                        return;
                    }
                    lastClickTimes = SystemClock.elapsedRealtime();
                    if (isInitialized && list[index][0] == 0) {
                        durationPickerDialog.setOutputButton(emptyBtn);
                        durationPickerDialog.setOutputText(checkChar);
                        durationPickerDialog.setExpected(answer);
                        durationPickerDialog.setRecordList(list[index]);
                        durationPickerDialog.setRecordListIndex(0);
                        durationPickerDialog.show(activity.getSupportFragmentManager(), "chooseDuration");
                    }
                });
                layout2.addView(durationIs);
                layout2.addView(emptyBtn);
                layout2.addView(checkChar);
                buttons[count] = emptyBtn;
                count++;
                TextView noteIs = new TextView(context);
                noteIs.setText(R.string.noteIs);
                Button emptyBtn1 = new Button(context);
                TextView checkChar1 = new TextView(context);
                emptyBtn1.setAllCaps(false);
                emptyBtn1.setBackground(Appearance.getStroke(ContextCompat.getColor(context, R.color.empty)));
                emptyBtn1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                int answer1 = notes[i][0].pitch;
                emptyBtn1.setOnClickListener(view -> {
                    if (SystemClock.elapsedRealtime() - lastClickTimes < 1500) {
                        return;
                    }
                    lastClickTimes = SystemClock.elapsedRealtime();
                    if (isInitialized && list[index][1] == 0) {
                        notePickerDialog.setOutputButton(emptyBtn1);
                        notePickerDialog.setOutputText(checkChar1);
                        notePickerDialog.setExpected(answer1);
                        notePickerDialog.setRecordList(list[index]);
                        notePickerDialog.setRecordListIndex(1);
                        notePickerDialog.show(activity.getSupportFragmentManager(), "chooseNote");
                    }
                });
                layout2.addView(noteIs);
                layout2.addView(emptyBtn1);
                layout2.addView(checkChar1);
                buttons[count] = emptyBtn1;
                count++;
                mainLayout.addView(layout2);
            }
            scrollView.addView(mainLayout);
            layout.addView(scrollView);
            midiSounds.playSound(buttons);
        });
        LinearLayout layout1 = new LinearLayout(context);
        layout1.setOrientation(LinearLayout.HORIZONTAL);
        layout1.addView(newQuestionBtn);
        replayBtn = new Button(context);
        replayBtn.setEnabled(false);
        replayBtn.setText(R.string.replay);
        replayBtn.setAllCaps(false);
        replayBtn.setOnClickListener(v -> midiSounds.playSound(buttons));
        layout1.addView(replayBtn);
        standardBtn = new Button(context);
        standardBtn.setAllCaps(false);
        standardBtn.setText(R.string.play4_4OnC);
        midiSounds.createStandardRhythmNotes();
        midiSounds.createMIDIFile(MIDISounds.MAKE_STANDARD_RHYTHM);
        standardBtn.setOnClickListener(v -> midiSounds.playStandardRhythm(buttons));
        layout1.addView(standardBtn);
        layout.addView(layout1);
    }

    public void destroyDialogs() {
        if (midiSounds != null) {
            midiSounds.stop();
        }
        if (durationPickerDialog != null && durationPickerDialog.getBehavior() != null) {
            durationPickerDialog.getBehavior().setHideable(true);
            durationPickerDialog.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
        if (notePickerDialog != null && notePickerDialog.getBehavior() != null) {
            notePickerDialog.getBehavior().setHideable(true);
            notePickerDialog.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }
}

class ETChordUC {
    private final DoingTestActivity activity;
    private final LinearLayout layout;
    private final Context context;
    private final Settings initialSettings;
    private final ETChordSettings settings;
    private final MIDISounds midiSounds;
    private long lastClickTimes;
    private byte[] list;
    private int answer;
    private com.example.solfege.models.Note[][] notes;
    private boolean isInitialized = false;
    private boolean isAdded = false;
    private final Button[] buttons = new Button[6];
    private AnswerPickerDialog chordPickerDialog;
    private AnswerPickerDialog notePickerDialog;

    ETChordUC(Settings settings, DoingTestActivity activity, LinearLayout layout) {
        this.activity = activity;
        initialSettings = settings;
        this.settings = (ETChordSettings) settings.getSettings();
        this.layout = layout;
        context = activity.getBaseContext();
        midiSounds = new MIDISounds(initialSettings, context, initialSettings.getTimeSignature(), initialSettings.getNoteLength());
        create();
    }

    void create() {
        LinearLayout layout2 = new LinearLayout(context);
        LinearLayout layout3 = new LinearLayout(context);
        Button chordEmptyBtn = new Button(context);
        Button rootNoteEmptyBtn = new Button(context);
        buttons[4] = chordEmptyBtn;
        buttons[5] = rootNoteEmptyBtn;
        TextView chordCheckChar = new TextView(context);
        TextView rootNoteCheckChar = new TextView(context);
        Button newQuestionBtn = new Button(context);
        newQuestionBtn.setText(R.string.newQuestion);
        newQuestionBtn.setAllCaps(false);
        newQuestionBtn.setOnClickListener(v -> {
            list = new byte[2];
            chordEmptyBtn.setText("");
            chordEmptyBtn.setBackground(Appearance.getStroke(ContextCompat.getColor(context, R.color.empty)));
            chordCheckChar.setText("");
            if (settings.isExpertMode()) {
                rootNoteEmptyBtn.setText("");
                rootNoteEmptyBtn.setBackground(Appearance.getStroke(ContextCompat.getColor(context, R.color.empty)));
                rootNoteCheckChar.setText("");
            }
            if (!isAdded) {
                chordPickerDialog = new AnswerPickerDialog(DialogType.Chords);
                chordPickerDialog.setText(activity.getString(R.string.chooseChord));
                layout.addView(layout2);
                if (settings.isExpertMode()) {
                    notePickerDialog = new AnswerPickerDialog(DialogType.Notes);
                    notePickerDialog.setText(activity.getString(R.string.chooseNote));
                    layout.addView(layout3);
                }
                isAdded = true;
            }
            midiSounds.createChordNotes(settings.getChords(), initialSettings.getNoteRange());
            midiSounds.createMIDIFile(MIDISounds.MAKE_QUESTION);
            midiSounds.playSound(buttons);
            answer = midiSounds.getAnswer();
            notes = midiSounds.getNotes();
            isInitialized = true;
        });
        LinearLayout layout1 = new LinearLayout(context);
        layout1.setOrientation(LinearLayout.HORIZONTAL);
        layout1.addView(newQuestionBtn);
        Button replayBtn = new Button(context);
        replayBtn.setEnabled(false);
        replayBtn.setText(R.string.replay);
        replayBtn.setAllCaps(false);
        replayBtn.setOnClickListener(v -> midiSounds.playSound(buttons));
        layout1.addView(replayBtn);
        Button ascendBtn = new Button(context);
        ascendBtn.setEnabled(false);
        ascendBtn.setText(R.string.ascendIt);
        ascendBtn.setAllCaps(false);
        ascendBtn.setOnClickListener(v -> {
            midiSounds.createBrokenChordNotes();
            midiSounds.createMIDIFile(MIDISounds.MAKE_BROKEN_CHORD);
            midiSounds.playBrokenChord(buttons);
        });
        layout1.addView(ascendBtn);
        if (settings.isExpertMode()) {
            Button centralCBtn = new Button(context);
            centralCBtn.setEnabled(false);
            centralCBtn.setAllCaps(false);
            centralCBtn.setText(R.string.playCentralC);
            midiSounds.createCentralCNote();
            midiSounds.createMIDIFile(MIDISounds.MAKE_CENTRAL_C);
            centralCBtn.setOnClickListener(v -> midiSounds.playCentralC(buttons));
            layout1.addView(centralCBtn);
            buttons[3] = centralCBtn;
        }
        buttons[0] = newQuestionBtn;
        buttons[1] = replayBtn;
        buttons[2] = ascendBtn;
        layout.addView(layout1);

        layout2.setOrientation(LinearLayout.HORIZONTAL);
        TextView chordIs = new TextView(context);
        chordIs.setText(R.string.chordIs);
        chordEmptyBtn.setAllCaps(false);
        chordEmptyBtn.setBackground(Appearance.getStroke(ContextCompat.getColor(context, R.color.empty)));
        chordEmptyBtn.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - lastClickTimes < 1500) {
                return;
            }
            lastClickTimes = SystemClock.elapsedRealtime();
            if (isInitialized && list[0] == 0) {
                chordPickerDialog.setOutputText(chordCheckChar);
                chordPickerDialog.setOutputButton(chordEmptyBtn);
                chordPickerDialog.setExpected(answer);
                chordPickerDialog.setRecordList(list);
                chordPickerDialog.setRecordListIndex(0);
                chordPickerDialog.show(activity.getSupportFragmentManager(), "Choose Chords");
            }
        });
        layout2.addView(chordIs);
        layout2.addView(chordEmptyBtn);
        layout2.addView(chordCheckChar);

        if (settings.isExpertMode()) {
            layout3.setOrientation(LinearLayout.HORIZONTAL);
            TextView rootNoteIs = new TextView(context);
            rootNoteIs.setText(R.string.rootNoteIs);
            rootNoteEmptyBtn.setAllCaps(false);
            rootNoteEmptyBtn.setBackground(Appearance.getStroke(ContextCompat.getColor(context, R.color.empty)));
            rootNoteEmptyBtn.setOnClickListener(v -> {
                if (SystemClock.elapsedRealtime() - lastClickTimes < 1500) {
                    return;
                }
                lastClickTimes = SystemClock.elapsedRealtime();
                if (isInitialized && list[1] == 0) {
                    notePickerDialog.setOutputButton(rootNoteEmptyBtn);
                    notePickerDialog.setOutputText(rootNoteCheckChar);
                    notePickerDialog.setExpected(notes[0][0].pitch);
                    notePickerDialog.setRecordList(list);
                    notePickerDialog.setRecordListIndex(1);
                    notePickerDialog.show(activity.getSupportFragmentManager(), "chooseNote");
                }
            });
            layout3.addView(rootNoteIs);
            layout3.addView(rootNoteEmptyBtn);
            layout3.addView(rootNoteCheckChar);
        }
    }

    public void destroyDialogs() {
        if (midiSounds != null) {
            midiSounds.stop();
        }
        if (chordPickerDialog != null && chordPickerDialog.getBehavior() != null) {
            chordPickerDialog.getBehavior().setHideable(true);
            chordPickerDialog.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
        if (notePickerDialog != null && notePickerDialog.getBehavior() != null) {
            notePickerDialog.getBehavior().setHideable(true);
            notePickerDialog.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }
}

class ETProgressionUC {
    private final DoingTestActivity activity;
    private final LinearLayout layout;
    private final Context context;
    private final Settings initialSettings;
    private final ETProgressionSettings settings;
    private final MIDISounds midiSounds;
    private long lastClickTimes;
    private byte[][] list;
    private int[][] answers;
    private boolean isInitialized = false;
    private boolean isAdded = false;
    private final Button[] buttons = new Button[11];
    private final ArrayList<Button> questionButtons = new ArrayList<>();
    private final ArrayList<TextView> checkChars = new ArrayList<>();
    private AnswerPickerDialog degreePickerDialog;
    private AnswerPickerDialog notePickerDialog;

    ETProgressionUC(Settings settings, DoingTestActivity activity, LinearLayout layout) {
        this.activity = activity;
        initialSettings = settings;
        this.settings = (ETProgressionSettings) settings.getSettings();
        this.layout = layout;
        context = activity.getBaseContext();
        midiSounds = new MIDISounds(initialSettings, context, initialSettings.getTimeSignature(), initialSettings.getNoteLength());
        create();
    }

    void create() {
        TextView scaleInfo = new TextView(context);
        int count = 3;
        LinearLayout mainLayout = new LinearLayout(context);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < 4; i++) {
            int index = i;
            LinearLayout layout = new LinearLayout(context);
            Button emptyBtn = new Button(context);
            TextView checkChar = new TextView(context);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            TextView degreeIs = new TextView(context);
            degreeIs.setText(R.string.degreeIs);
            emptyBtn.setAllCaps(false);
            emptyBtn.setBackground(Appearance.getStroke(ContextCompat.getColor(context, R.color.empty)));
            emptyBtn.setOnClickListener(v -> {
                if (SystemClock.elapsedRealtime() - lastClickTimes < 1500) {
                    return;
                }
                lastClickTimes = SystemClock.elapsedRealtime();
                if (isInitialized && list[index][0] == 0) {
                    degreePickerDialog.setOutputButton(emptyBtn);
                    degreePickerDialog.setOutputText(checkChar);
                    degreePickerDialog.setExpected(answers[index + 1][2]);
                    degreePickerDialog.setRecordList(list[index]);
                    degreePickerDialog.setRecordListIndex(0);
                    degreePickerDialog.show(activity.getSupportFragmentManager(), "Choose Degree");
                }
            });
            layout.addView(degreeIs);
            layout.addView(emptyBtn);
            layout.addView(checkChar);
            questionButtons.add(emptyBtn);
            checkChars.add(checkChar);
            buttons[count] = emptyBtn;
            count++;
            if (settings.isExpertMode()) {
                Button emptyBtn1 = new Button(context);
                TextView checkChar1 = new TextView(context);
                TextView rootNoteIs = new TextView(context);
                rootNoteIs.setText(R.string.rootNoteIs);
                emptyBtn1.setAllCaps(false);
                emptyBtn1.setBackground(Appearance.getStroke(ContextCompat.getColor(context, R.color.empty)));
                emptyBtn1.setOnClickListener(v -> {
                    if (SystemClock.elapsedRealtime() - lastClickTimes < 1500) {
                        return;
                    }
                    lastClickTimes = SystemClock.elapsedRealtime();
                    if (isInitialized && list[index][1] == 0) {
                        notePickerDialog.setOutputText(checkChar1);
                        notePickerDialog.setOutputButton(emptyBtn1);
                        notePickerDialog.setExpected(answers[index + 1][0]);
                        notePickerDialog.setRecordList(list[index]);
                        notePickerDialog.setRecordListIndex(1);
                        notePickerDialog.show(activity.getSupportFragmentManager(), "chooseNote");
                    }
                });
                layout.addView(rootNoteIs);
                layout.addView(emptyBtn1);
                layout.addView(checkChar1);
                questionButtons.add(emptyBtn1);
                checkChars.add(checkChar1);
                buttons[count] = emptyBtn1;
                count++;
            }
            mainLayout.addView(layout);
        }
        Button newQuestionBtn = new Button(context);
        newQuestionBtn.setText(R.string.newQuestion);
        newQuestionBtn.setAllCaps(false);
        newQuestionBtn.setOnClickListener(v -> {
            list = new byte[4][2];
            for (int i = 0; i < questionButtons.size(); i++) {
                questionButtons.get(i).setText("");
                checkChars.get(i).setText("");
            }
            if (!isAdded) {
                degreePickerDialog = new AnswerPickerDialog(DialogType.Degrees);
                degreePickerDialog.setText(activity.getString(R.string.chooseDegree));
                if (settings.isExpertMode()) {
                    notePickerDialog = new AnswerPickerDialog(DialogType.Notes);
                    notePickerDialog.setText(activity.getString(R.string.chooseNote));
                }
                layout.addView(scaleInfo);
                layout.addView(mainLayout);
                isAdded = true;
            }
            midiSounds.createProgressionNotes(settings.getScales(), settings.getChordTypes(), initialSettings.getNoteRange());
            midiSounds.createMIDIFile(MIDISounds.MAKE_QUESTION);
            answers = midiSounds.getAnswers();
            scaleInfo.setText(String.format(context.getString(R.string.scaleNoteInformation), Note.getNote(answers[0][0]), Scale.getScales(context)[answers[0][1]]));
            isInitialized = true;
            midiSounds.playSound(buttons);
        });
        LinearLayout layout1 = new LinearLayout(context);
        layout1.setOrientation(LinearLayout.HORIZONTAL);
        layout1.addView(newQuestionBtn);
        Button replayBtn = new Button(context);
        replayBtn.setEnabled(false);
        replayBtn.setText(R.string.replay);
        replayBtn.setAllCaps(false);
        replayBtn.setOnClickListener(v -> midiSounds.playSound(buttons));
        layout1.addView(replayBtn);
        Button scaleBtn = new Button(context);
        scaleBtn.setEnabled(false);
        scaleBtn.setText(R.string.playScale);
        scaleBtn.setAllCaps(false);
        scaleBtn.setOnClickListener(v -> {
            midiSounds.createScale(answers[0][0], answers[0][1]);
            midiSounds.createMIDIFile(MIDISounds.MAKE_SCALE);
            midiSounds.playScale(buttons);
        });
        layout1.addView(scaleBtn);
        buttons[0] = newQuestionBtn;
        buttons[1] = replayBtn;
        buttons[2] = scaleBtn;
        layout.addView(layout1);
    }

    public void destroyDialogs() {
        if (midiSounds != null) {
            midiSounds.stop();
        }
        if (degreePickerDialog != null && degreePickerDialog.getBehavior() != null) {
            degreePickerDialog.getBehavior().setHideable(true);
            degreePickerDialog.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
        if (notePickerDialog != null && notePickerDialog.getBehavior() != null) {
            notePickerDialog.getBehavior().setHideable(true);
            notePickerDialog.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }
}
