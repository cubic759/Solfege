package com.example.solfege.models;

import android.content.Context;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.example.solfege.constants.Chords;
import com.example.solfege.utils.NoteRangePickerDialog;
import com.example.solfege.R;
import com.example.solfege.SettingFragment;
import com.example.solfege.adapters.MyListAdapter;
import com.example.solfege.adapters.OnSeekBarChangeListenerAdapter;
import com.example.solfege.constants.Durations;
import com.example.solfege.constants.Intervals;
import com.example.solfege.constants.Mode;
import com.example.solfege.constants.Scales;
import com.example.solfege.constants.Type;
import com.example.solfege.external.multiselectspinner.MultiSelectSpinner;
import com.example.solfege.utils.TimeSignaturePickerDialog;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import me.daolema.sakura.library.FlowRadioGroup;

public class SettingControls {
    private static final int OPERATION_CREATE = 0;
    private static final int OPERATION_WRITE = 1;
    private static final int OPERATION_READ = 2;
    private static final int OPERATION_DESTROY_PICKERS = 3;
    private final Type testType;
    private final Mode testMode;
    private final Settings settings;
    private final SettingFragment settingFragment;
    private final Context context;
    private final ListView listView;
    private final List<SettingItem> list = new ArrayList<>();
    private TextView lengthText, velocityText;
    private SeekBar noteVelocity;
    private SeekBar noteLength;
    private Object settingControls;

    public SettingControls(Type testType, Mode testMode, Settings settings, SettingFragment settingFragment, ListView listView) {
        this.testType = testType;
        this.testMode = testMode;
        this.settings = settings;
        this.settingFragment = settingFragment;
        this.listView = listView;
        context = settingFragment.getContext();
    }

    public void create() {
        settingControlsOperation(OPERATION_CREATE);
        noteVelocity = new SeekBar(context);
        noteVelocity.setMax(180);//60-240
        noteVelocity.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        noteVelocity.setOnSeekBarChangeListener(new OnSeekBarChangeListenerAdapter() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                velocityText.setText(String.format(context.getString(R.string.number), progress + 60));
            }
        });
        velocityText = new TextView(context);
        LinearLayout layout1 = new LinearLayout(context);
        layout1.addView(velocityText);
        layout1.addView(noteVelocity);
        lengthText = new TextView(context);
        noteLength = new SeekBar(context);
        noteLength.setMax(95);
        noteLength.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        noteLength.setOnSeekBarChangeListener(new OnSeekBarChangeListenerAdapter() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                lengthText.setText(String.format(context.getString(R.string.percent), progress + 5));
            }
        });
        LinearLayout layout2 = new LinearLayout(context);
        layout2.addView(lengthText);
        layout2.addView(noteLength);
        list.add(new SettingItem(context.getString(R.string.noteVelocity), layout1));
        list.add(new SettingItem(context.getString(R.string.noteLength), layout2));
        listView.setAdapter(new MyListAdapter(list, settingFragment));
        setSettings();
    }

    public Settings getSettings() {
        settingControlsOperation(OPERATION_READ);
        settings.setNoteVelocity(noteVelocity.getProgress() + 60);
        settings.setNoteLength(noteLength.getProgress() + 5);
        return settings;
    }

    public void setSettings() {
        settingControlsOperation(OPERATION_WRITE);
        if (settings.isUninitiated()) {
            velocityText.setText(String.format(context.getString(R.string.number), 120));
            noteVelocity.setProgress(60);
            lengthText.setText(String.format(context.getString(R.string.percent), 50));//1-100
            noteLength.setProgress(45);
        } else {
            noteVelocity.setProgress(settings.getNoteVelocity() - 60);
            noteLength.setProgress(settings.getNoteLength() - 5);
        }
    }

    public void destroyAllDialogs() {
        settingControlsOperation(OPERATION_DESTROY_PICKERS);
    }

    private void settingControlsOperation(int operation) {
        if (testMode == Mode.SIGHT_SINGING) {
            switch (testType) {
                case RHYTHM:
                    if (operation == OPERATION_CREATE) {
                        settingControls = new SSRhythmSC(settingFragment, settings, list);
                    } else if (operation == OPERATION_WRITE) {
                        ((SSRhythmSC) settingControls).setSettings();
                    } else if (operation == OPERATION_DESTROY_PICKERS) {
                        ((SSRhythmSC) settingControls).destroyDialogs();
                    } else {
                        ((SSRhythmSC) settingControls).getSettings();
                    }
                    break;
                case INTERVAL:
                    if (operation == OPERATION_CREATE) {
                        settingControls = new SSIntervalSC(settingFragment, settings, list);
                    } else if (operation == OPERATION_WRITE) {
                        ((SSIntervalSC) settingControls).setSettings();
                    } else if (operation == OPERATION_DESTROY_PICKERS) {
                        ((SSIntervalSC) settingControls).destroyDialogs();
                    } else {
                        ((SSIntervalSC) settingControls).getSettings();
                    }
                    break;
                case SCALE:
                    if (operation == OPERATION_CREATE) {
                        settingControls = new SSScaleSC(settingFragment, settings, list);
                    } else if (operation == OPERATION_WRITE) {
                        ((SSScaleSC) settingControls).setSettings();
                    } else if (operation == OPERATION_DESTROY_PICKERS) {
                        ((SSScaleSC) settingControls).destroyDialogs();
                    } else {
                        ((SSScaleSC) settingControls).getSettings();
                    }
                    break;
                case ARPEGGIO:
                    if (operation == OPERATION_CREATE) {
                        settingControls = new SSArpeggioSC(settingFragment, settings, list);
                    } else if (operation == OPERATION_WRITE) {
                        ((SSArpeggioSC) settingControls).setSettings();
                    } else if (operation == OPERATION_DESTROY_PICKERS) {
                        ((SSArpeggioSC) settingControls).destroyDialogs();
                    } else {
                        ((SSArpeggioSC) settingControls).getSettings();
                    }
                    break;
                case MELODY:
                    if (operation == OPERATION_CREATE) {
                        settingControls = new SSMelodySC(settingFragment, settings, list);
                    } else if (operation == OPERATION_WRITE) {
                        ((SSMelodySC) settingControls).setSettings();
                    } else if (operation == OPERATION_DESTROY_PICKERS) {
                        ((SSMelodySC) settingControls).destroyDialogs();
                    } else {
                        ((SSMelodySC) settingControls).getSettings();
                    }
                    break;
                case CHORD:
                    if (operation == OPERATION_CREATE) {
                        settingControls = new SSChordSC(settingFragment, settings, list);
                    } else if (operation == OPERATION_WRITE) {
                        ((SSChordSC) settingControls).setSettings();
                    } else if (operation == OPERATION_DESTROY_PICKERS) {
                        ((SSChordSC) settingControls).destroyDialogs();
                    } else {
                        ((SSChordSC) settingControls).getSettings();
                    }
                    break;
                case PROGRESSION:
                    if (operation == OPERATION_CREATE) {
                        settingControls = new SSProgressionSC(settingFragment, settings, list);
                    } else if (operation == OPERATION_WRITE) {
                        ((SSProgressionSC) settingControls).setSettings();
                    } else if (operation == OPERATION_DESTROY_PICKERS) {
                        ((SSProgressionSC) settingControls).destroyDialogs();
                    } else {
                        ((SSProgressionSC) settingControls).getSettings();
                    }
                    break;
            }
        } else {
            switch (testType) {
                case RHYTHM:
                    if (operation == OPERATION_CREATE) {
                        settingControls = new ETRhythmSC(settingFragment, settings, list);
                    } else if (operation == OPERATION_WRITE) {
                        ((ETRhythmSC) settingControls).setSettings();
                    } else if (operation == OPERATION_DESTROY_PICKERS) {
                        ((ETRhythmSC) settingControls).destroyDialogs();
                    } else {
                        ((ETRhythmSC) settingControls).getSettings();
                    }
                    break;
                case INTERVAL:
                    if (operation == OPERATION_CREATE) {
                        settingControls = new ETIntervalSC(settingFragment, settings, list);
                    } else if (operation == OPERATION_WRITE) {
                        ((ETIntervalSC) settingControls).setSettings();
                    } else if (operation == OPERATION_DESTROY_PICKERS) {
                        ((ETIntervalSC) settingControls).destroyDialogs();
                    } else {
                        ((ETIntervalSC) settingControls).getSettings();
                    }
                    break;
                case SCALE:
                    if (operation == OPERATION_CREATE) {
                        settingControls = new ETScaleSC(settingFragment, settings, list);
                    } else if (operation == OPERATION_WRITE) {
                        ((ETScaleSC) settingControls).setSettings();
                    } else if (operation == OPERATION_DESTROY_PICKERS) {
                        ((ETScaleSC) settingControls).destroyDialogs();
                    } else {
                        ((ETScaleSC) settingControls).getSettings();
                    }
                    break;
                case ARPEGGIO:
                    if (operation == OPERATION_CREATE) {
                        settingControls = new ETArpeggioSC(settingFragment, settings, list);
                    } else if (operation == OPERATION_WRITE) {
                        ((ETArpeggioSC) settingControls).setSettings();
                    } else if (operation == OPERATION_DESTROY_PICKERS) {
                        ((ETArpeggioSC) settingControls).destroyDialogs();
                    } else {
                        ((ETArpeggioSC) settingControls).getSettings();
                    }
                    break;
                case MELODY:
                    if (operation == OPERATION_CREATE) {
                        settingControls = new ETMelodySC(settingFragment, settings, list);
                    } else if (operation == OPERATION_WRITE) {
                        ((ETMelodySC) settingControls).setSettings();
                    } else if (operation == OPERATION_DESTROY_PICKERS) {
                        ((ETMelodySC) settingControls).destroyDialogs();
                    } else {
                        ((ETMelodySC) settingControls).getSettings();
                    }
                    break;
                case CHORD:
                    if (operation == OPERATION_CREATE) {
                        settingControls = new ETChordSC(settingFragment, settings, list);
                    } else if (operation == OPERATION_WRITE) {
                        ((ETChordSC) settingControls).setSettings();
                    } else if (operation == OPERATION_DESTROY_PICKERS) {
                        ((ETChordSC) settingControls).destroyDialogs();
                    } else {
                        ((ETChordSC) settingControls).getSettings();
                    }
                    break;
                case PROGRESSION:
                    if (operation == OPERATION_CREATE) {
                        settingControls = new ETProgressionSC(settingFragment, settings, list);
                    } else if (operation == OPERATION_WRITE) {
                        ((ETProgressionSC) settingControls).setSettings();
                    } else if (operation == OPERATION_DESTROY_PICKERS) {
                        ((ETProgressionSC) settingControls).destroyDialogs();
                    } else {
                        ((ETProgressionSC) settingControls).getSettings();
                    }
                    break;
            }
        }
    }
}

//Subclasses
class SSRhythmSC {
    private final SettingFragment settingFragment;
    private final Context context;
    private final Settings initialSettings;
    private final SSRhythmSettings settings;
    private final List<SettingItem> list;
    private FlowRadioGroup group;
    private MultiSelectSpinner durationSpinner;
    private TimeSignaturePickerDialog timeSignaturePicker;
    private TextView timeSignatureText;
    private long lastClickTime = 0;

    SSRhythmSC(SettingFragment settingFragment, Settings settings, List<SettingItem> list) {
        this.settingFragment = settingFragment;
        this.context = settingFragment.getContext();
        initialSettings = settings;
        this.settings = (SSRhythmSettings) settings.getSettings();
        this.list = list;
        create();
    }

    void create() {
        durationSpinner = new MultiSelectSpinner(context);
        durationSpinner.setTitle(context.getString(R.string.chooseDuration));
        durationSpinner.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        durationSpinner.setListAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_multiple_choice,
                Arrays.asList(Durations.getDurations(context))));

        RadioButton oneBar = new RadioButton(context);
        RadioButton twoBars = new RadioButton(context);
        oneBar.setText(R.string.oneBar);
        twoBars.setText(R.string.twoBars);
        group = new FlowRadioGroup(context);
        group.addView(oneBar);
        group.addView(twoBars);

        timeSignatureText = new TextView(context);
        timeSignatureText.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
        timeSignatureText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Button button = new Button(context);
        button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button.setText(R.string.chooseTimeSignature);
        timeSignaturePicker = new TimeSignaturePickerDialog(timeSignatureText, initialSettings, context.getString(R.string.chooseTimeSignature));
        button.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - lastClickTime < 1500) {
                return;
            }
            lastClickTime = SystemClock.elapsedRealtime();
            timeSignaturePicker.show(settingFragment.getChildFragmentManager(), "Choose time signature");
        });
        LinearLayout layout = new LinearLayout(context);
        layout.addView(timeSignatureText);
        layout.addView(button);

        list.add(new SettingItem(context.getString(R.string.durations), durationSpinner));
        list.add(new SettingItem(context.getString(R.string.timeSignature), layout));
        list.add(new SettingItem(context.getString(R.string.bars), group));
    }

    void getSettings() {
        if (!durationSpinner.getSpinnerText().equals(context.getString(R.string.unselected))) {
            settings.setDurations(durationSpinner.getSelected());
            settings.setEmptyDurations(false);
        } else {
            settings.setEmptyDurations(true);
        }
        settings.setBars(group.indexOfChild(group.findViewById(group.getCheckedRadioButtonId())) + 1);
        initialSettings.setTimeSignature(timeSignaturePicker.getTimeSignature());
    }

    void setSettings() {
        if (initialSettings.isUninitiated()) {
            durationSpinner.selectItem(4);
            durationSpinner.selectItem(6);
            group.clearCheck();
            ((RadioButton) group.getChildAt(0)).setChecked(true);
            timeSignatureText.setText(String.format(context.getString(R.string.timeSignatureText), "4", "4"));
            timeSignaturePicker.setTimeSignature(new int[]{2, 0});
        } else {
            if (!settings.isEmptyDurations()) {
                durationSpinner.setSelect(settings.getDurations());
            }
            group.clearCheck();
            ((RadioButton) group.getChildAt(settings.getBars() - 1)).setChecked(true);
            timeSignaturePicker.setTimeSignature(initialSettings.getTimeSignature());
            String[] timeSignatureString = timeSignaturePicker.getTimeSignatureString();
            timeSignatureText.setText(String.format(context.getString(R.string.timeSignatureText), timeSignatureString[0], timeSignatureString[1]));
        }
    }

    void destroyDialogs() {
        if (timeSignaturePicker != null && timeSignaturePicker.getBehavior() != null) {
            timeSignaturePicker.getBehavior().setHideable(true);
            timeSignaturePicker.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }
}

class SSIntervalSC {
    private final SettingFragment settingFragment;
    private final Context context;
    private final Settings initialSettings;
    private final SSIntervalSettings settings;
    private final List<SettingItem> list;
    private long mLastClickTime = 0;
    private MultiSelectSpinner multiSelectSpinner;
    private FlowRadioGroup group;
    private NoteRangePickerDialog pickerDialog;
    private TextView noteRangeText;

    SSIntervalSC(SettingFragment settingFragment, Settings settings, List<SettingItem> list) {
        this.settingFragment = settingFragment;
        this.context = settingFragment.getContext();
        initialSettings = settings;
        this.settings = (SSIntervalSettings) settings.getSettings();
        this.list = list;
        create();
    }

    void create() {
        multiSelectSpinner = new MultiSelectSpinner(Objects.requireNonNull(context));
        multiSelectSpinner.setTitle(context.getString(R.string.chooseInterval));
        multiSelectSpinner.setListAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_multiple_choice,
                Arrays.asList(Intervals.getIntervals(context))));//setSelections

        RadioButton ascendButton = new RadioButton(context);
        ascendButton.setText(R.string.ascending);
        RadioButton descendButton = new RadioButton(context);
        descendButton.setText(R.string.descending);
        RadioButton harmonicButton = new RadioButton(context);
        harmonicButton.setText(R.string.harmonic);
        RadioButton randomButton = new RadioButton(context);
        randomButton.setText(R.string.random);
        group = new FlowRadioGroup(context);
        group.setOrientation(RadioGroup.HORIZONTAL);
        group.addView(ascendButton);
        group.addView(descendButton);
        group.addView(harmonicButton);
        group.addView(randomButton);

        noteRangeText = new TextView(context);
        noteRangeText.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
        noteRangeText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Button button = new Button(context);
        button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button.setText(R.string.chooseRange);
        pickerDialog = new NoteRangePickerDialog(noteRangeText, initialSettings, context.getString(R.string.chooseRange));
        button.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            pickerDialog.show(settingFragment.getChildFragmentManager(), "Choose range");
        });
        LinearLayout layout = new LinearLayout(context);
        layout.addView(noteRangeText);
        layout.addView(button);

        list.add(new SettingItem(context.getString(R.string.intervals), multiSelectSpinner));
        list.add(new SettingItem(context.getString(R.string.playMode), group));
        list.add(new SettingItem(context.getString(R.string.noteRange), layout));
    }

    void getSettings() {
        if (!multiSelectSpinner.getSpinnerText().equals(context.getString(R.string.unselected))) {
            settings.setIntervals(multiSelectSpinner.getSelected());
        } else {
            settings.setEmptyIntervals(true);
        }
        settings.setPlayMode(group.indexOfChild(group.findViewById(group.getCheckedRadioButtonId())));
        initialSettings.setNoteRange(pickerDialog.getRange());
    }

    void setSettings() {
        if (initialSettings.isUninitiated()) {
            multiSelectSpinner.selectItem(4);
            multiSelectSpinner.selectItem(5);
            multiSelectSpinner.selectItem(7);
            multiSelectSpinner.selectItem(11);
            group.clearCheck();
            ((RadioButton) group.getChildAt(0)).setChecked(true);
            noteRangeText.setText(String.format(context.getString(R.string.fromTo), "A₂", "c⁵"));
            pickerDialog.setRange(new int[]{87, 0});
        } else {
            if (!settings.isEmptyIntervals()) {
                multiSelectSpinner.setSelect(settings.getIntervals());
            }
            group.clearCheck();
            ((RadioButton) group.getChildAt(settings.getPlayMode())).setChecked(true);
            pickerDialog.setRange(initialSettings.getNoteRange());
            String[] rangeString = pickerDialog.getRangeString();
            noteRangeText.setText(String.format(context.getString(R.string.fromTo), rangeString[0], rangeString[1]));
        }
    }

    void destroyDialogs() {
        if (pickerDialog != null && pickerDialog.getBehavior() != null) {
            pickerDialog.getBehavior().setHideable(true);
            pickerDialog.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }
}

class SSScaleSC {
    SettingFragment settingFragment;
    Context context;
    Settings initialSettings;
    SSScaleSettings settings;
    List<SettingItem> list;
    long mLastClickTime = 0;
    private NoteRangePickerDialog pickerDialog;
    private MultiSelectSpinner multiSelectSpinner;
    private FlowRadioGroup group;
    private TextView noteRangeText;

    SSScaleSC(SettingFragment settingFragment, Settings settings, List<SettingItem> list) {
        this.settingFragment = settingFragment;
        this.context = settingFragment.getContext();
        initialSettings = settings;
        this.settings = (SSScaleSettings) settings.getSettings();
        this.list = list;
        create();
    }

    void create() {
        multiSelectSpinner = new MultiSelectSpinner(Objects.requireNonNull(context));
        multiSelectSpinner.setTitle(context.getString(R.string.chooseScale));
        multiSelectSpinner.setListAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_multiple_choice,
                Arrays.asList(Scales.getScales(context))));//setSelections

        RadioButton ascendButton = new RadioButton(context);
        ascendButton.setText(R.string.ascending);
        RadioButton descendButton = new RadioButton(context);
        descendButton.setText(R.string.descending);
        group = new FlowRadioGroup(context);
        group.setOrientation(RadioGroup.HORIZONTAL);
        group.addView(ascendButton);
        group.addView(descendButton);

        noteRangeText = new TextView(context);
        noteRangeText.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
        noteRangeText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Button button = new Button(context);
        button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button.setText(R.string.chooseRange);
        pickerDialog = new NoteRangePickerDialog(noteRangeText, initialSettings, context.getString(R.string.chooseRange));
        button.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            pickerDialog.show(settingFragment.getChildFragmentManager(), "Choose range");
        });
        LinearLayout layout = new LinearLayout(context);
        layout.addView(noteRangeText);
        layout.addView(button);

        list.add(new SettingItem(context.getString(R.string.scales), multiSelectSpinner));
        list.add(new SettingItem(context.getString(R.string.playMode), group));
        list.add(new SettingItem(context.getString(R.string.noteRange), layout));
    }

    void getSettings() {
        if (!multiSelectSpinner.getSpinnerText().equals(context.getString(R.string.unselected))) {
            settings.setScales(multiSelectSpinner.getSelected());
        } else {
            settings.setEmptyScales(true);
        }
        settings.setPlayMode(group.indexOfChild(group.findViewById(group.getCheckedRadioButtonId())));
        initialSettings.setNoteRange(pickerDialog.getRange());
    }

    void setSettings() {
        if (initialSettings.isUninitiated()) {
            multiSelectSpinner.selectItem(0);
            multiSelectSpinner.selectItem(1);
            multiSelectSpinner.selectItem(4);
            multiSelectSpinner.selectItem(5);
            group.clearCheck();
            ((RadioButton) group.getChildAt(0)).setChecked(true);
            noteRangeText.setText(String.format(context.getString(R.string.fromTo), "A₂", "c⁵"));
            pickerDialog.setRange(new int[]{87, 0});
        } else {
            multiSelectSpinner.setSelect(settings.getScales());
            group.clearCheck();
            ((RadioButton) group.getChildAt(settings.getPlayMode())).setChecked(true);
            pickerDialog.setRange(initialSettings.getNoteRange());
            String[] rangeString = pickerDialog.getRangeString();
            noteRangeText.setText(String.format(context.getString(R.string.fromTo), rangeString[0], rangeString[1]));
        }
    }

    void destroyDialogs() {
        if (pickerDialog != null && pickerDialog.getBehavior() != null) {
            pickerDialog.getBehavior().setHideable(true);
            pickerDialog.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }
}

class SSArpeggioSC {
    private final SettingFragment settingFragment;
    private final Context context;
    private final Settings initialSettings;
    private final SSArpeggioSettings settings;
    private final List<SettingItem> list;
    private long mLastClickTime = 0;
    private NoteRangePickerDialog pickerDialog;
    private MultiSelectSpinner multiSelectSpinner;
    private FlowRadioGroup group;
    private TextView noteRangeText;

    SSArpeggioSC(SettingFragment settingFragment, Settings settings, List<SettingItem> list) {
        this.settingFragment = settingFragment;
        this.context = settingFragment.getContext();
        initialSettings = settings;
        this.settings = (SSArpeggioSettings) settings.getSettings();
        this.list = list;
        create();
    }

    void create() {
        multiSelectSpinner = new MultiSelectSpinner(Objects.requireNonNull(context));
        multiSelectSpinner.setTitle(context.getString(R.string.chooseChord));
        multiSelectSpinner.setListAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_multiple_choice,
                Arrays.asList(Chords.getChords(context))));//setSelections

        RadioButton ascendButton = new RadioButton(context);
        ascendButton.setText(R.string.ascending);
        RadioButton descendButton = new RadioButton(context);
        descendButton.setText(R.string.descending);
        group = new FlowRadioGroup(context);
        group.setOrientation(RadioGroup.HORIZONTAL);
        group.addView(ascendButton);
        group.addView(descendButton);

        noteRangeText = new TextView(context);
        noteRangeText.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
        noteRangeText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Button button = new Button(context);
        button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button.setText(R.string.chooseRange);
        pickerDialog = new NoteRangePickerDialog(noteRangeText, initialSettings, context.getString(R.string.chooseRange));
        button.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            pickerDialog.show(settingFragment.getChildFragmentManager(), "Choose range");
        });
        LinearLayout layout = new LinearLayout(context);
        layout.addView(noteRangeText);
        layout.addView(button);

        list.add(new SettingItem(context.getString(R.string.chords), multiSelectSpinner));
        list.add(new SettingItem(context.getString(R.string.playMode), group));
        list.add(new SettingItem(context.getString(R.string.noteRange), layout));
    }

    void getSettings() {
        if (!multiSelectSpinner.getSpinnerText().equals(context.getString(R.string.unselected))) {
            settings.setChords(multiSelectSpinner.getSelected());
        } else {
            settings.setEmptyChords(true);
        }
        settings.setPlayMode(group.indexOfChild(group.findViewById(group.getCheckedRadioButtonId())));
        initialSettings.setNoteRange(pickerDialog.getRange());
    }

    void setSettings() {
        if (initialSettings.isUninitiated()) {
            multiSelectSpinner.selectItem(0);
            multiSelectSpinner.selectItem(1);
            multiSelectSpinner.selectItem(6);
            multiSelectSpinner.selectItem(7);
            multiSelectSpinner.selectItem(8);
            group.clearCheck();
            ((RadioButton) group.getChildAt(0)).setChecked(true);
            noteRangeText.setText(String.format(context.getString(R.string.fromTo), "A₂", "c⁵"));
            pickerDialog.setRange(new int[]{87, 0});
        } else {
            multiSelectSpinner.setSelect(settings.getChords());
            group.clearCheck();
            ((RadioButton) group.getChildAt(settings.getPlayMode())).setChecked(true);
            pickerDialog.setRange(initialSettings.getNoteRange());
            String[] rangeString = pickerDialog.getRangeString();
            noteRangeText.setText(String.format(context.getString(R.string.fromTo), rangeString[0], rangeString[1]));
        }
    }

    void destroyDialogs() {
        if (pickerDialog != null && pickerDialog.getBehavior() != null) {
            pickerDialog.getBehavior().setHideable(true);
            pickerDialog.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }
}

class SSMelodySC {
    private final SettingFragment settingFragment;
    private final Context context;
    private final Settings initialSettings;
    private final SSMelodySettings settings;
    private final List<SettingItem> list;
    private final long[] lastClickTime = new long[2];
    private FlowRadioGroup group;
    private MultiSelectSpinner durationSpinner;
    private MultiSelectSpinner scaleSpinner;
    private NoteRangePickerDialog noteRangePicker;
    private TextView noteRangeText;
    private TimeSignaturePickerDialog timeSignaturePicker;
    private TextView timeSignatureText;

    SSMelodySC(SettingFragment settingFragment, Settings settings, List<SettingItem> list) {
        this.settingFragment = settingFragment;
        this.context = settingFragment.getContext();
        initialSettings = settings;
        this.settings = (SSMelodySettings) settings.getSettings();
        this.list = list;
        create();
    }

    void create() {
        scaleSpinner = new MultiSelectSpinner(Objects.requireNonNull(context));
        scaleSpinner.setTitle(context.getString(R.string.chooseScale));
        scaleSpinner.setListAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_multiple_choice,
                Arrays.asList(Scales.getScales(context))));//setSelections

        durationSpinner = new MultiSelectSpinner(context);
        durationSpinner.setTitle(context.getString(R.string.chooseDuration));
        durationSpinner.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        durationSpinner.setListAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_multiple_choice,
                Arrays.asList(Durations.getDurations(context))));

        RadioButton oneBar = new RadioButton(context);
        RadioButton twoBars = new RadioButton(context);
        oneBar.setText(R.string.oneBar);
        twoBars.setText(R.string.twoBars);
        group = new FlowRadioGroup(context);
        group.addView(oneBar);
        group.addView(twoBars);

        noteRangeText = new TextView(context);
        noteRangeText.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
        noteRangeText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Button button = new Button(context);
        button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button.setText(R.string.chooseRange);
        noteRangePicker = new NoteRangePickerDialog(noteRangeText, initialSettings, context.getString(R.string.chooseRange));
        button.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - lastClickTime[0] < 1500) {
                return;
            }
            lastClickTime[0] = SystemClock.elapsedRealtime();
            noteRangePicker.show(settingFragment.getChildFragmentManager(), "Choose range");
        });
        LinearLayout layout = new LinearLayout(context);
        layout.addView(noteRangeText);
        layout.addView(button);

        timeSignatureText = new TextView(context);
        timeSignatureText.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
        timeSignatureText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Button button1 = new Button(context);
        button1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button1.setText(R.string.chooseTimeSignature);
        timeSignaturePicker = new TimeSignaturePickerDialog(timeSignatureText, initialSettings, context.getString(R.string.chooseTimeSignature));
        button1.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - lastClickTime[1] < 1500) {
                return;
            }
            lastClickTime[1] = SystemClock.elapsedRealtime();
            timeSignaturePicker.show(settingFragment.getChildFragmentManager(), "Choose time signature");
        });
        LinearLayout layout1 = new LinearLayout(context);
        layout1.addView(timeSignatureText);
        layout1.addView(button1);

        list.add(new SettingItem(context.getString(R.string.durations), durationSpinner));
        list.add(new SettingItem(context.getString(R.string.scales), scaleSpinner));
        list.add(new SettingItem(context.getString(R.string.noteRange), layout));
        list.add(new SettingItem(context.getString(R.string.timeSignature), layout1));
        list.add(new SettingItem(context.getString(R.string.bars), group));
    }

    void getSettings() {
        if (!durationSpinner.getSpinnerText().equals(context.getString(R.string.unselected))) {
            settings.setDurations(durationSpinner.getSelected());
            settings.setEmptyDurations(false);
        } else {
            settings.setEmptyDurations(true);
        }
        if (!scaleSpinner.getSpinnerText().equals(context.getString(R.string.unselected))) {
            settings.setScales(scaleSpinner.getSelected());
        } else {
            settings.setEmptyScales(true);
        }
        settings.setBars(group.indexOfChild(group.findViewById(group.getCheckedRadioButtonId())) + 1);
        initialSettings.setTimeSignature(timeSignaturePicker.getTimeSignature());
        initialSettings.setNoteRange(noteRangePicker.getRange());
    }

    void setSettings() {
        if (initialSettings.isUninitiated()) {
            durationSpinner.selectItem(4);
            durationSpinner.selectItem(6);
            group.clearCheck();
            ((RadioButton) group.getChildAt(0)).setChecked(true);
            timeSignatureText.setText(String.format(context.getString(R.string.timeSignatureText), "4", "4"));
            timeSignaturePicker.setTimeSignature(new int[]{2, 0});
            scaleSpinner.selectItem(0);
            scaleSpinner.selectItem(1);
            scaleSpinner.selectItem(4);
            scaleSpinner.selectItem(5);
            noteRangeText.setText(String.format(context.getString(R.string.fromTo), "A₂", "c⁵"));
            noteRangePicker.setRange(new int[]{87, 0});
        } else {
            if (!settings.isEmptyDurations()) {
                durationSpinner.setSelect(settings.getDurations());
            }
            if (!settings.isEmptyScales()) {
                scaleSpinner.setSelect(settings.getScales());
            }
            group.clearCheck();
            ((RadioButton) group.getChildAt(settings.getBars() - 1)).setChecked(true);
            timeSignaturePicker.setTimeSignature(initialSettings.getTimeSignature());
            String[] timeSignatureString = timeSignaturePicker.getTimeSignatureString();
            timeSignatureText.setText(String.format(context.getString(R.string.timeSignatureText), timeSignatureString[0], timeSignatureString[1]));
            noteRangePicker.setRange(initialSettings.getNoteRange());
            String[] rangeString = noteRangePicker.getRangeString();
            noteRangeText.setText(String.format(context.getString(R.string.fromTo), rangeString[0], rangeString[1]));
        }
    }

    void destroyDialogs() {
        if (timeSignaturePicker != null && timeSignaturePicker.getBehavior() != null) {
            timeSignaturePicker.getBehavior().setHideable(true);
            timeSignaturePicker.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
        if (noteRangePicker != null && noteRangePicker.getBehavior() != null) {
            noteRangePicker.getBehavior().setHideable(true);
            noteRangePicker.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }
}

class SSChordSC {
    private final SettingFragment settingFragment;
    private final Context context;
    private final Settings initialSettings;
    private final SSChordSettings settings;
    private final List<SettingItem> list;
    private long mLastClickTime = 0;
    private NoteRangePickerDialog pickerDialog;
    private MultiSelectSpinner multiSelectSpinner;
    private TextView noteRangeText;

    SSChordSC(SettingFragment settingFragment, Settings settings, List<SettingItem> list) {
        this.settingFragment = settingFragment;
        this.context = settingFragment.getContext();
        initialSettings = settings;
        this.settings = (SSChordSettings) settings.getSettings();
        this.list = list;
        create();
    }

    void create() {
        multiSelectSpinner = new MultiSelectSpinner(Objects.requireNonNull(context));
        multiSelectSpinner.setTitle(context.getString(R.string.chooseChord));
        multiSelectSpinner.setListAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_multiple_choice,
                Arrays.asList(Chords.getChords(context))));//setSelections

        noteRangeText = new TextView(context);
        noteRangeText.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
        noteRangeText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Button button = new Button(context);
        button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button.setText(R.string.chooseRange);
        pickerDialog = new NoteRangePickerDialog(noteRangeText, initialSettings, context.getString(R.string.chooseRange));
        button.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            pickerDialog.show(settingFragment.getChildFragmentManager(), "Choose range");
        });
        LinearLayout layout = new LinearLayout(context);
        layout.addView(noteRangeText);
        layout.addView(button);

        list.add(new SettingItem(context.getString(R.string.intervals), multiSelectSpinner));
        list.add(new SettingItem(context.getString(R.string.noteRange), layout));
    }

    void getSettings() {
        if (!multiSelectSpinner.getSpinnerText().equals(context.getString(R.string.unselected))) {
            settings.setChords(multiSelectSpinner.getSelected());
        } else {
            settings.setEmptyChords(true);
        }
        initialSettings.setNoteRange(pickerDialog.getRange());
    }

    void setSettings() {
        if (initialSettings.isUninitiated()) {
            multiSelectSpinner.selectItem(0);
            multiSelectSpinner.selectItem(1);
            multiSelectSpinner.selectItem(6);
            multiSelectSpinner.selectItem(7);
            multiSelectSpinner.selectItem(8);
            noteRangeText.setText(String.format(context.getString(R.string.fromTo), "A₂", "c⁵"));
            pickerDialog.setRange(new int[]{87, 0});
        } else {
            multiSelectSpinner.setSelect(settings.getChords());
            pickerDialog.setRange(initialSettings.getNoteRange());
            String[] rangeString = pickerDialog.getRangeString();
            noteRangeText.setText(String.format(context.getString(R.string.fromTo), rangeString[0], rangeString[1]));
        }
    }

    void destroyDialogs() {
        if (pickerDialog != null && pickerDialog.getBehavior() != null) {
            pickerDialog.getBehavior().setHideable(true);
            pickerDialog.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }
}

class SSProgressionSC {
    private final SettingFragment settingFragment;
    private final Context context;
    private final Settings initialSettings;
    private final SSProgressionSettings settings;
    private final List<SettingItem> list;
    private long mLastClickTime = 0;
    private NoteRangePickerDialog pickerDialog;
    private MultiSelectSpinner scaleSpinner;
    private MultiSelectSpinner chordTypeSpinner;
    private TextView noteRangeText;

    SSProgressionSC(SettingFragment settingFragment, Settings settings, List<SettingItem> list) {
        this.settingFragment = settingFragment;
        this.context = settingFragment.getContext();
        initialSettings = settings;
        this.settings = (SSProgressionSettings) settings.getSettings();
        this.list = list;
        create();
    }

    void create() {
        scaleSpinner = new MultiSelectSpinner(Objects.requireNonNull(context));
        scaleSpinner.setTitle(context.getString(R.string.chooseScale));
        scaleSpinner.setListAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_multiple_choice,
                Arrays.asList(Scales.getProgressionScales(context))));//setSelections
        chordTypeSpinner = new MultiSelectSpinner(Objects.requireNonNull(context));
        chordTypeSpinner.setTitle(context.getString(R.string.chooseChordType));
        chordTypeSpinner.setListAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_multiple_choice,
                Arrays.asList(Chords.getChordTypes(context))));//setSelections

        noteRangeText = new TextView(context);
        noteRangeText.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
        noteRangeText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Button button = new Button(context);
        button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button.setText(R.string.chooseRange);
        pickerDialog = new NoteRangePickerDialog(noteRangeText, initialSettings, context.getString(R.string.chooseRange));
        button.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            pickerDialog.show(settingFragment.getChildFragmentManager(), "Choose range");
        });
        LinearLayout layout = new LinearLayout(context);
        layout.addView(noteRangeText);
        layout.addView(button);

        list.add(new SettingItem(context.getString(R.string.scales),scaleSpinner));
        list.add(new SettingItem(context.getString(R.string.chordTypes), chordTypeSpinner));
        list.add(new SettingItem(context.getString(R.string.noteRange), layout));
    }

    void getSettings() {
        if (!scaleSpinner.getSpinnerText().equals(context.getString(R.string.unselected))) {
            settings.setScales(scaleSpinner.getSelected());
        } else {
            settings.setEmptyScales(true);
        }
        if (!chordTypeSpinner.getSpinnerText().equals(context.getString(R.string.unselected))) {
            settings.setChordTypes(chordTypeSpinner.getSelected());
        } else {
            settings.setEmptyChordTypes(true);
        }
        initialSettings.setNoteRange(pickerDialog.getRange());
    }

    void setSettings() {
        if (initialSettings.isUninitiated()) {
            scaleSpinner.selectItem(0);
            scaleSpinner.selectItem(1);
            chordTypeSpinner.selectItem(0);
            chordTypeSpinner.selectItem(2);
            noteRangeText.setText(String.format(context.getString(R.string.fromTo), "A₂", "c⁵"));
            pickerDialog.setRange(new int[]{87, 0});
        } else {
            scaleSpinner.setSelect(settings.getScales());
            chordTypeSpinner.setSelect(settings.getChordTypes());
            pickerDialog.setRange(initialSettings.getNoteRange());
            String[] rangeString = pickerDialog.getRangeString();
            noteRangeText.setText(String.format(context.getString(R.string.fromTo), rangeString[0], rangeString[1]));
        }
    }

    void destroyDialogs() {
        if (pickerDialog != null && pickerDialog.getBehavior() != null) {
            pickerDialog.getBehavior().setHideable(true);
            pickerDialog.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }
}

class ETRhythmSC {
    private final SettingFragment settingFragment;
    private final Context context;
    private final Settings initialSettings;
    private final ETRhythmSettings settings;
    private final List<SettingItem> list;
    private FlowRadioGroup group;
    private MultiSelectSpinner durationSpinner;
    private TimeSignaturePickerDialog timeSignaturePicker;
    private TextView timeSignatureText;
    private long lastClickTime = 0;

    ETRhythmSC(SettingFragment settingFragment, Settings settings, List<SettingItem> list) {
        this.settingFragment = settingFragment;
        this.context = settingFragment.getContext();
        initialSettings = settings;
        this.settings = (ETRhythmSettings) settings.getSettings();
        this.list = list;
        create();
    }

    void create() {
        durationSpinner = new MultiSelectSpinner(context);
        durationSpinner.setTitle(context.getString(R.string.chooseDuration));
        durationSpinner.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        durationSpinner.setListAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_multiple_choice,
                Arrays.asList(Durations.getDurations(context))));

        LinearLayout verticalLayout = new LinearLayout(context);
        verticalLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout horizontalLayout = new LinearLayout(context);
        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
        horizontalLayout.addView(durationSpinner);
        verticalLayout.addView(horizontalLayout);

        RadioButton oneBar = new RadioButton(context);
        RadioButton twoBars = new RadioButton(context);
        oneBar.setText(R.string.oneBar);
        twoBars.setText(R.string.twoBars);
        group = new FlowRadioGroup(context);
        group.addView(oneBar);
        group.addView(twoBars);

        timeSignatureText = new TextView(context);
        timeSignatureText.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
        timeSignatureText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Button button = new Button(context);
        button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button.setText(R.string.chooseTimeSignature);
        timeSignaturePicker = new TimeSignaturePickerDialog(timeSignatureText, initialSettings, context.getString(R.string.chooseTimeSignature));
        button.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - lastClickTime < 1500) {
                return;
            }
            lastClickTime = SystemClock.elapsedRealtime();
            timeSignaturePicker.show(settingFragment.getChildFragmentManager(), "Choose time signature");
        });
        LinearLayout layout = new LinearLayout(context);
        layout.addView(timeSignatureText);
        layout.addView(button);

        list.add(new SettingItem(context.getString(R.string.durations), verticalLayout));
        list.add(new SettingItem(context.getString(R.string.timeSignature), layout));
        list.add(new SettingItem(context.getString(R.string.bars), group));
    }

    void getSettings() {
        if (!durationSpinner.getSpinnerText().equals(context.getString(R.string.unselected))) {
            settings.setDurations(durationSpinner.getSelected());
            settings.setEmptyDurations(false);
        } else {
            settings.setEmptyDurations(true);
        }
        settings.setBars(group.indexOfChild(group.findViewById(group.getCheckedRadioButtonId())) + 1);
        initialSettings.setTimeSignature(timeSignaturePicker.getTimeSignature());
    }

    void setSettings() {
        if (initialSettings.isUninitiated()) {
            durationSpinner.selectItem(4);
            durationSpinner.selectItem(6);
            group.clearCheck();
            ((RadioButton) group.getChildAt(0)).setChecked(true);
            timeSignatureText.setText(String.format(context.getString(R.string.timeSignatureText), "4", "4"));
            timeSignaturePicker.setTimeSignature(new int[]{2, 0});
        } else {
            if (!settings.isEmptyDurations()) {
                durationSpinner.setSelect(settings.getDurations());
            }
            group.clearCheck();
            ((RadioButton) group.getChildAt(settings.getBars() - 1)).setChecked(true);
            timeSignaturePicker.setTimeSignature(initialSettings.getTimeSignature());
            String[] timeSignatureString = timeSignaturePicker.getTimeSignatureString();
            timeSignatureText.setText(String.format(context.getString(R.string.timeSignatureText), timeSignatureString[0], timeSignatureString[1]));
        }
    }

    void destroyDialogs() {
        if (timeSignaturePicker != null && timeSignaturePicker.getBehavior() != null) {
            timeSignaturePicker.getBehavior().setHideable(true);
            timeSignaturePicker.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }
}

class ETIntervalSC {
    SettingFragment settingFragment;
    Context context;
    Settings initialSettings;
    ETIntervalSettings settings;
    List<SettingItem> list;

    private long mLastClickTime = 0;
    private MultiSelectSpinner multiSelectSpinner;
    private FlowRadioGroup group;
    private NoteRangePickerDialog pickerDialog;
    private SwitchCompat expertMode;
    private TextView noteRangeText;

    ETIntervalSC(SettingFragment settingFragment, Settings settings, List<SettingItem> list) {
        this.settingFragment = settingFragment;
        this.context = settingFragment.getContext();
        initialSettings = settings;
        this.settings = (ETIntervalSettings) settings.getSettings();
        this.list = list;
        create();
    }

    void create() {
        multiSelectSpinner = new MultiSelectSpinner(Objects.requireNonNull(context));
        multiSelectSpinner.setTitle(context.getString(R.string.chooseInterval));
        multiSelectSpinner.setListAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_multiple_choice,
                Arrays.asList(Intervals.getIntervals(context))));//setSelections

        RadioButton ascendButton = new RadioButton(context);
        ascendButton.setText(R.string.ascending);
        RadioButton descendButton = new RadioButton(context);
        descendButton.setText(R.string.descending);
        RadioButton harmonicButton = new RadioButton(context);
        harmonicButton.setText(R.string.harmonic);
        RadioButton randomButton = new RadioButton(context);
        randomButton.setText(R.string.random);
        group = new FlowRadioGroup(context);
        group.setOrientation(RadioGroup.HORIZONTAL);
        group.addView(ascendButton);
        group.addView(descendButton);
        group.addView(harmonicButton);
        group.addView(randomButton);
        FrameLayout wrapLayout = new FrameLayout(context);
        wrapLayout.addView(group);

        noteRangeText = new TextView(context);
        noteRangeText.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
        noteRangeText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Button button = new Button(context);
        button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button.setText(R.string.chooseRange);
        pickerDialog = new NoteRangePickerDialog(noteRangeText, initialSettings, context.getString(R.string.chooseRange));
        button.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            pickerDialog.show(settingFragment.getChildFragmentManager(), "Choose range");
        });
        LinearLayout layout = new LinearLayout(context);
        layout.addView(noteRangeText);
        layout.addView(button);

        expertMode = new SwitchCompat(context);
        expertMode.setText(R.string.answerNotesAlso);

        list.add(new SettingItem(context.getString(R.string.intervals), multiSelectSpinner));
        list.add(new SettingItem(context.getString(R.string.playMode), wrapLayout));
        list.add(new SettingItem(context.getString(R.string.noteRange), layout));
        list.add(new SettingItem(context.getString(R.string.expertMode), expertMode));
    }

    void getSettings() {
        if (!multiSelectSpinner.getSpinnerText().equals(context.getString(R.string.unselected))) {
            settings.setIntervals(multiSelectSpinner.getSelected());
        } else {
            settings.setEmptyIntervals(true);
        }
        settings.setPlayMode(group.indexOfChild(group.findViewById(group.getCheckedRadioButtonId())));
        initialSettings.setNoteRange(pickerDialog.getRange());
        settings.setExpertMode(expertMode.isChecked());
    }

    void setSettings() {
        if (initialSettings.isUninitiated()) {
            multiSelectSpinner.selectItem(4);
            multiSelectSpinner.selectItem(5);
            multiSelectSpinner.selectItem(7);
            multiSelectSpinner.selectItem(11);
            group.clearCheck();
            ((RadioButton) group.getChildAt(0)).setChecked(true);
            noteRangeText.setText(String.format(context.getString(R.string.fromTo), "A₂", "c⁵"));
            pickerDialog.setRange(new int[]{87, 0});
        } else {
            if (!settings.isEmptyIntervals()) {
                multiSelectSpinner.setSelect(settings.getIntervals());
            }
            group.clearCheck();
            ((RadioButton) group.getChildAt(settings.getPlayMode())).setChecked(true);
            pickerDialog.setRange(initialSettings.getNoteRange());
            String[] rangeString = pickerDialog.getRangeString();
            noteRangeText.setText(String.format(context.getString(R.string.fromTo), rangeString[0], rangeString[1]));
            expertMode.setChecked(settings.isExpertMode());
        }
    }

    void destroyDialogs() {
        if (pickerDialog != null && pickerDialog.getBehavior() != null) {
            pickerDialog.getBehavior().setHideable(true);
            pickerDialog.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }
}

class ETScaleSC {
    SettingFragment settingFragment;
    Context context;
    Settings initialSettings;
    ETScaleSettings settings;
    List<SettingItem> list;
    private NoteRangePickerDialog pickerDialog;
    long mLastClickTime = 0;
    private MultiSelectSpinner multiSelectSpinner;
    private FlowRadioGroup group;
    private SwitchCompat expertMode;
    private TextView noteRangeText;

    ETScaleSC(SettingFragment settingFragment, Settings settings, List<SettingItem> list) {
        this.settingFragment = settingFragment;
        this.context = settingFragment.getContext();
        initialSettings = settings;
        this.settings = (ETScaleSettings) settings.getSettings();
        this.list = list;
        create();
    }

    void create() {
        multiSelectSpinner = new MultiSelectSpinner(Objects.requireNonNull(context));
        multiSelectSpinner.setTitle(context.getString(R.string.chooseScale));
        multiSelectSpinner.setListAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_multiple_choice,
                Arrays.asList(Scales.getScales(context))));//setSelections

        RadioButton ascendButton = new RadioButton(context);
        ascendButton.setText(R.string.ascending);
        RadioButton descendButton = new RadioButton(context);
        descendButton.setText(R.string.descending);
        group = new FlowRadioGroup(context);
        group.setOrientation(RadioGroup.HORIZONTAL);
        group.addView(ascendButton);
        group.addView(descendButton);

        noteRangeText = new TextView(context);
        noteRangeText.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
        noteRangeText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Button button = new Button(context);
        button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button.setText(R.string.chooseRange);
        pickerDialog = new NoteRangePickerDialog(noteRangeText, initialSettings, context.getString(R.string.chooseRange));
        button.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            pickerDialog.show(settingFragment.getChildFragmentManager(), "Choose range");
        });
        LinearLayout layout = new LinearLayout(context);
        layout.addView(noteRangeText);
        layout.addView(button);

        expertMode = new SwitchCompat(context);
        expertMode.setText(R.string.answerRootNoteAlso);

        list.add(new SettingItem(context.getString(R.string.scales), multiSelectSpinner));
        list.add(new SettingItem(context.getString(R.string.playMode), group));
        list.add(new SettingItem(context.getString(R.string.noteRange), layout));
        list.add(new SettingItem(context.getString(R.string.expertMode), expertMode));
    }

    void getSettings() {
        if (!multiSelectSpinner.getSpinnerText().equals(context.getString(R.string.unselected))) {
            settings.setScales(multiSelectSpinner.getSelected());
        } else {
            settings.setEmptyScales(true);
        }
        settings.setPlayMode(group.indexOfChild(group.findViewById(group.getCheckedRadioButtonId())));
        initialSettings.setNoteRange(pickerDialog.getRange());
        settings.setExpertMode(expertMode.isChecked());
    }

    void setSettings() {
        if (initialSettings.isUninitiated()) {
            multiSelectSpinner.selectItem(0);
            multiSelectSpinner.selectItem(1);
            multiSelectSpinner.selectItem(4);
            multiSelectSpinner.selectItem(5);
            group.clearCheck();
            ((RadioButton) group.getChildAt(0)).setChecked(true);
            noteRangeText.setText(String.format(context.getString(R.string.fromTo), "A₂", "c⁵"));
            pickerDialog.setRange(new int[]{87, 0});
        } else {
            multiSelectSpinner.setSelect(settings.getScales());
            group.clearCheck();
            ((RadioButton) group.getChildAt(settings.getPlayMode())).setChecked(true);
            pickerDialog.setRange(initialSettings.getNoteRange());
            String[] rangeString = pickerDialog.getRangeString();
            noteRangeText.setText(String.format(context.getString(R.string.fromTo), rangeString[0], rangeString[1]));
            expertMode.setChecked(settings.isExpertMode());
        }
    }

    void destroyDialogs() {
        if (pickerDialog != null && pickerDialog.getBehavior() != null) {
            pickerDialog.getBehavior().setHideable(true);
            pickerDialog.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }
}

class ETArpeggioSC {
    private final SettingFragment settingFragment;
    private final Context context;
    private final Settings initialSettings;
    private final ETArpeggioSettings settings;
    private final List<SettingItem> list;
    private long mLastClickTime = 0;
    private NoteRangePickerDialog pickerDialog;
    private MultiSelectSpinner multiSelectSpinner;
    private FlowRadioGroup group;
    private SwitchCompat expertMode;
    private TextView noteRangeText;

    ETArpeggioSC(SettingFragment settingFragment, Settings settings, List<SettingItem> list) {
        this.settingFragment = settingFragment;
        this.context = settingFragment.getContext();
        initialSettings = settings;
        this.settings = (ETArpeggioSettings) settings.getSettings();
        this.list = list;
        create();
    }

    void create() {
        multiSelectSpinner = new MultiSelectSpinner(Objects.requireNonNull(context));
        multiSelectSpinner.setTitle(context.getString(R.string.chooseChord));
        multiSelectSpinner.setListAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_multiple_choice,
                Arrays.asList(Chords.getChords(context))));//setSelections

        RadioButton ascendButton = new RadioButton(context);
        ascendButton.setText(R.string.ascending);
        RadioButton descendButton = new RadioButton(context);
        descendButton.setText(R.string.descending);
        group = new FlowRadioGroup(context);
        group.setOrientation(RadioGroup.HORIZONTAL);
        group.addView(ascendButton);
        group.addView(descendButton);

        noteRangeText = new TextView(context);
        noteRangeText.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
        noteRangeText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Button button = new Button(context);
        button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button.setText(R.string.chooseRange);
        pickerDialog = new NoteRangePickerDialog(noteRangeText, initialSettings, context.getString(R.string.chooseRange));
        button.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            pickerDialog.show(settingFragment.getChildFragmentManager(), "Choose range");
        });
        LinearLayout layout = new LinearLayout(context);
        layout.addView(noteRangeText);
        layout.addView(button);

        expertMode = new SwitchCompat(context);
        expertMode.setText(R.string.answerRootNoteAlso);

        list.add(new SettingItem(context.getString(R.string.chords), multiSelectSpinner));
        list.add(new SettingItem(context.getString(R.string.playMode), group));
        list.add(new SettingItem(context.getString(R.string.noteRange), layout));
        list.add(new SettingItem(context.getString(R.string.expertMode), expertMode));
    }

    void getSettings() {
        if (!multiSelectSpinner.getSpinnerText().equals(context.getString(R.string.unselected))) {
            settings.setChords(multiSelectSpinner.getSelected());
        } else {
            settings.setEmptyChords(true);
        }
        settings.setPlayMode(group.indexOfChild(group.findViewById(group.getCheckedRadioButtonId())));
        initialSettings.setNoteRange(pickerDialog.getRange());
        settings.setExpertMode(expertMode.isChecked());
    }

    void setSettings() {
        if (initialSettings.isUninitiated()) {
            multiSelectSpinner.selectItem(0);
            multiSelectSpinner.selectItem(1);
            multiSelectSpinner.selectItem(6);
            multiSelectSpinner.selectItem(7);
            multiSelectSpinner.selectItem(8);
            group.clearCheck();
            ((RadioButton) group.getChildAt(0)).setChecked(true);
            noteRangeText.setText(String.format(context.getString(R.string.fromTo), "A₂", "c⁵"));
            pickerDialog.setRange(new int[]{87, 0});
        } else {
            multiSelectSpinner.setSelect(settings.getChords());
            group.clearCheck();
            ((RadioButton) group.getChildAt(settings.getPlayMode())).setChecked(true);
            pickerDialog.setRange(initialSettings.getNoteRange());
            String[] rangeString = pickerDialog.getRangeString();
            noteRangeText.setText(String.format(context.getString(R.string.fromTo), rangeString[0], rangeString[1]));
            expertMode.setChecked(settings.isExpertMode());
        }
    }

    void destroyDialogs() {
        if (pickerDialog != null && pickerDialog.getBehavior() != null) {
            pickerDialog.getBehavior().setHideable(true);
            pickerDialog.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }
}

class ETMelodySC {
    private final SettingFragment settingFragment;
    private final Context context;
    private final Settings initialSettings;
    private final ETMelodySettings settings;
    private final List<SettingItem> list;
    private final long[] lastClickTime = new long[2];
    private FlowRadioGroup group;
    private MultiSelectSpinner durationSpinner;
    private MultiSelectSpinner scaleSpinner;
    private NoteRangePickerDialog noteRangePicker;
    private TextView noteRangeText;
    private TimeSignaturePickerDialog timeSignaturePicker;
    private TextView timeSignatureText;

    ETMelodySC(SettingFragment settingFragment, Settings settings, List<SettingItem> list) {
        this.settingFragment = settingFragment;
        this.context = settingFragment.getContext();
        initialSettings = settings;
        this.settings = (ETMelodySettings) settings.getSettings();
        this.list = list;
        create();
    }

    void create() {
        scaleSpinner = new MultiSelectSpinner(Objects.requireNonNull(context));
        scaleSpinner.setTitle(context.getString(R.string.chooseScale));
        scaleSpinner.setListAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_multiple_choice,
                Arrays.asList(Scales.getScales(context))));//setSelections

        durationSpinner = new MultiSelectSpinner(context);
        durationSpinner.setTitle(context.getString(R.string.chooseDuration));
        durationSpinner.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        durationSpinner.setListAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_multiple_choice,
                Arrays.asList(Durations.getDurations(context))));

        RadioButton oneBar = new RadioButton(context);
        RadioButton twoBars = new RadioButton(context);
        oneBar.setText(R.string.oneBar);
        twoBars.setText(R.string.twoBars);
        group = new FlowRadioGroup(context);
        group.addView(oneBar);
        group.addView(twoBars);

        noteRangeText = new TextView(context);
        noteRangeText.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
        noteRangeText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Button button = new Button(context);
        button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button.setText(R.string.chooseRange);
        noteRangePicker = new NoteRangePickerDialog(noteRangeText, initialSettings, context.getString(R.string.chooseRange));
        button.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - lastClickTime[0] < 1500) {
                return;
            }
            lastClickTime[0] = SystemClock.elapsedRealtime();
            noteRangePicker.show(settingFragment.getChildFragmentManager(), "Choose range");
        });
        LinearLayout layout = new LinearLayout(context);
        layout.addView(noteRangeText);
        layout.addView(button);

        timeSignatureText = new TextView(context);
        timeSignatureText.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
        timeSignatureText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Button button1 = new Button(context);
        button1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button1.setText(R.string.chooseTimeSignature);
        timeSignaturePicker = new TimeSignaturePickerDialog(timeSignatureText, initialSettings, context.getString(R.string.chooseTimeSignature));
        button1.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - lastClickTime[1] < 1500) {
                return;
            }
            lastClickTime[1] = SystemClock.elapsedRealtime();
            timeSignaturePicker.show(settingFragment.getChildFragmentManager(), "Choose time signature");
        });
        LinearLayout layout1 = new LinearLayout(context);
        layout1.addView(timeSignatureText);
        layout1.addView(button1);

        list.add(new SettingItem(context.getString(R.string.durations), durationSpinner));
        list.add(new SettingItem(context.getString(R.string.scales), scaleSpinner));
        list.add(new SettingItem(context.getString(R.string.noteRange), layout));
        list.add(new SettingItem(context.getString(R.string.timeSignature), layout1));
        list.add(new SettingItem(context.getString(R.string.bars), group));
    }

    void getSettings() {
        if (!durationSpinner.getSpinnerText().equals(context.getString(R.string.unselected))) {
            settings.setDurations(durationSpinner.getSelected());
            settings.setEmptyDurations(false);
        } else {
            settings.setEmptyDurations(true);
        }
        if (!scaleSpinner.getSpinnerText().equals(context.getString(R.string.unselected))) {
            settings.setScales(scaleSpinner.getSelected());
        } else {
            settings.setEmptyScales(true);
        }
        settings.setBars(group.indexOfChild(group.findViewById(group.getCheckedRadioButtonId())) + 1);
        initialSettings.setTimeSignature(timeSignaturePicker.getTimeSignature());
        initialSettings.setNoteRange(noteRangePicker.getRange());
    }

    void setSettings() {
        if (initialSettings.isUninitiated()) {
            durationSpinner.selectItem(4);
            durationSpinner.selectItem(6);
            group.clearCheck();
            ((RadioButton) group.getChildAt(0)).setChecked(true);
            timeSignatureText.setText(String.format(context.getString(R.string.timeSignatureText), "4", "4"));
            timeSignaturePicker.setTimeSignature(new int[]{2, 0});
            scaleSpinner.selectItem(0);
            scaleSpinner.selectItem(1);
            scaleSpinner.selectItem(4);
            scaleSpinner.selectItem(5);
            noteRangeText.setText(String.format(context.getString(R.string.fromTo), "A₂", "c⁵"));
            noteRangePicker.setRange(new int[]{87, 0});
        } else {
            if (!settings.isEmptyDurations()) {
                durationSpinner.setSelect(settings.getDurations());
            }
            if (!settings.isEmptyScales()) {
                scaleSpinner.setSelect(settings.getScales());
            }
            group.clearCheck();
            ((RadioButton) group.getChildAt(settings.getBars() - 1)).setChecked(true);
            timeSignaturePicker.setTimeSignature(initialSettings.getTimeSignature());
            String[] timeSignatureString = timeSignaturePicker.getTimeSignatureString();
            timeSignatureText.setText(String.format(context.getString(R.string.timeSignatureText), timeSignatureString[0], timeSignatureString[1]));
            noteRangePicker.setRange(initialSettings.getNoteRange());
            String[] rangeString = noteRangePicker.getRangeString();
            noteRangeText.setText(String.format(context.getString(R.string.fromTo), rangeString[0], rangeString[1]));
        }
    }

    void destroyDialogs() {
        if (timeSignaturePicker != null && timeSignaturePicker.getBehavior() != null) {
            timeSignaturePicker.getBehavior().setHideable(true);
            timeSignaturePicker.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
        if (noteRangePicker != null && noteRangePicker.getBehavior() != null) {
            noteRangePicker.getBehavior().setHideable(true);
            noteRangePicker.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }
}

class ETChordSC {
    private final SettingFragment settingFragment;
    private final Context context;
    private final Settings initialSettings;
    private final ETChordSettings settings;
    private final List<SettingItem> list;
    private long mLastClickTime = 0;
    private NoteRangePickerDialog pickerDialog;
    private MultiSelectSpinner multiSelectSpinner;
    private TextView noteRangeText;

    ETChordSC(SettingFragment settingFragment, Settings settings, List<SettingItem> list) {
        this.settingFragment = settingFragment;
        this.context = settingFragment.getContext();
        initialSettings = settings;
        this.settings = (ETChordSettings) settings.getSettings();
        this.list = list;
        create();
    }

    void create() {
        multiSelectSpinner = new MultiSelectSpinner(Objects.requireNonNull(context));
        multiSelectSpinner.setTitle(context.getString(R.string.chooseChord));
        multiSelectSpinner.setListAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_multiple_choice,
                Arrays.asList(Chords.getChords(context))));//setSelections

        noteRangeText = new TextView(context);
        noteRangeText.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
        noteRangeText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Button button = new Button(context);
        button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button.setText(R.string.chooseRange);
        pickerDialog = new NoteRangePickerDialog(noteRangeText, initialSettings, context.getString(R.string.chooseRange));
        button.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            pickerDialog.show(settingFragment.getChildFragmentManager(), "Choose range");
        });
        LinearLayout layout = new LinearLayout(context);
        layout.addView(noteRangeText);
        layout.addView(button);

        list.add(new SettingItem(context.getString(R.string.intervals), multiSelectSpinner));
        list.add(new SettingItem(context.getString(R.string.noteRange), layout));
    }

    void getSettings() {
        if (!multiSelectSpinner.getSpinnerText().equals(context.getString(R.string.unselected))) {
            settings.setChords(multiSelectSpinner.getSelected());
        } else {
            settings.setEmptyChords(true);
        }
        initialSettings.setNoteRange(pickerDialog.getRange());
    }

    void setSettings() {
        if (initialSettings.isUninitiated()) {
            multiSelectSpinner.selectItem(0);
            multiSelectSpinner.selectItem(1);
            multiSelectSpinner.selectItem(6);
            multiSelectSpinner.selectItem(7);
            multiSelectSpinner.selectItem(8);
            noteRangeText.setText(String.format(context.getString(R.string.fromTo), "A₂", "c⁵"));
            pickerDialog.setRange(new int[]{87, 0});
        } else {
            multiSelectSpinner.setSelect(settings.getChords());
            pickerDialog.setRange(initialSettings.getNoteRange());
            String[] rangeString = pickerDialog.getRangeString();
            noteRangeText.setText(String.format(context.getString(R.string.fromTo), rangeString[0], rangeString[1]));
        }
    }

    void destroyDialogs() {
        if (pickerDialog != null && pickerDialog.getBehavior() != null) {
            pickerDialog.getBehavior().setHideable(true);
            pickerDialog.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }
}

class ETProgressionSC {
    private final SettingFragment settingFragment;
    private final Context context;
    private final Settings initialSettings;
    private final ETProgressionSettings settings;
    private final List<SettingItem> list;
    private long mLastClickTime = 0;
    private NoteRangePickerDialog pickerDialog;
    private MultiSelectSpinner multiSelectSpinner;
    private TextView noteRangeText;

    ETProgressionSC(SettingFragment settingFragment, Settings settings, List<SettingItem> list) {
        this.settingFragment = settingFragment;
        this.context = settingFragment.getContext();
        initialSettings = settings;
        this.settings = (ETProgressionSettings) settings.getSettings();
        this.list = list;
        create();
    }

    void create() {
        multiSelectSpinner = new MultiSelectSpinner(Objects.requireNonNull(context));
        multiSelectSpinner.setTitle(context.getString(R.string.chooseChordType));
        multiSelectSpinner.setListAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_multiple_choice,
                Arrays.asList(Chords.getChordTypes(context))));//setSelections

        noteRangeText = new TextView(context);
        noteRangeText.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
        noteRangeText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Button button = new Button(context);
        button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button.setText(R.string.chooseRange);
        pickerDialog = new NoteRangePickerDialog(noteRangeText, initialSettings, context.getString(R.string.chooseRange));
        button.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            pickerDialog.show(settingFragment.getChildFragmentManager(), "Choose range");
        });
        LinearLayout layout = new LinearLayout(context);
        layout.addView(noteRangeText);
        layout.addView(button);

        list.add(new SettingItem(context.getString(R.string.chordTypes), multiSelectSpinner));
        list.add(new SettingItem(context.getString(R.string.noteRange), layout));
    }

    void getSettings() {
        if (!multiSelectSpinner.getSpinnerText().equals(context.getString(R.string.unselected))) {
            settings.setChordTypes(multiSelectSpinner.getSelected());
        } else {
            settings.setEmptyChordTypes(true);
        }
        initialSettings.setNoteRange(pickerDialog.getRange());
    }

    void setSettings() {
        if (initialSettings.isUninitiated()) {
            multiSelectSpinner.selectItem(0);
            multiSelectSpinner.selectItem(2);
            noteRangeText.setText(String.format(context.getString(R.string.fromTo), "A₂", "c⁵"));
            pickerDialog.setRange(new int[]{87, 0});
        } else {
            multiSelectSpinner.setSelect(settings.getChordTypes());
            pickerDialog.setRange(initialSettings.getNoteRange());
            String[] rangeString = pickerDialog.getRangeString();
            noteRangeText.setText(String.format(context.getString(R.string.fromTo), rangeString[0], rangeString[1]));
        }
    }

    void destroyDialogs() {
        if (pickerDialog != null && pickerDialog.getBehavior() != null) {
            pickerDialog.getBehavior().setHideable(true);
            pickerDialog.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }
}