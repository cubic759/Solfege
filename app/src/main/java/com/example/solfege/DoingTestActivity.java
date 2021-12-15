package com.example.solfege;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.MenuItem;

import com.example.solfege.constants.Type;
import com.example.solfege.models.UIControls;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DoingTestActivity extends AppCompatActivity {
    private SettingDialogFragment settingDialog;
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doing_test);
        ActionBar actionBar = getSupportActionBar();
        int index = getIntent().getIntExtra("index", -1);//Get current page title index
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);//Use back button
            actionBar.setTitle(Type.getTypes(getBaseContext())[index]);
        }

        //Display setting dialog when start.
        settingDialog = new SettingDialogFragment(this, Type.values()[index]);
        settingDialog.show(getSupportFragmentManager(), "settingDialog");

        //Setting Button: Click and open the setting dialog.
        FloatingActionButton settingButton = findViewById(R.id.settingButton);
        settingButton.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {//Ignore quick click
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            settingDialog.show(getSupportFragmentManager(), "settingDialog");
        });
    }

    //Go back button at action bar.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return true;
    }

    //Activity destroying: Close used dialogs.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        UIControls uiControls = settingDialog.getUiControls();
        if (uiControls != null) {
            uiControls.destroyDialogs();
        }
        if (settingDialog.getBehavior() != null) {
            settingDialog.getBehavior().setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }
}