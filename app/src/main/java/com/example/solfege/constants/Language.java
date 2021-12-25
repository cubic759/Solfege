package com.example.solfege.constants;

import android.content.Context;

import com.example.solfege.R;

public class Language {
    public static int TOTAL_LANGUAGES = 2;
    public static String[] COUNTRY_CODE = {"zh", "en_us"};

    public static String[] getLanguages(Context context) {
        return new String[]{context.getString(R.string.Chinese), context.getString(R.string.English)};
    }
}
