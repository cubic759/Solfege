package com.example.solfege;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.solfege.adapters.MyRecyclerAdapter;
import com.example.solfege.constants.Languages;
import com.example.solfege.constants.Type;
import com.example.solfege.models.LanguageManager;

import java.util.Arrays;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.app_name));
        }
        //Adapter sets cards and makes that clicking one of cards will go to DoingTestActivity
        MyRecyclerAdapter adapter = new MyRecyclerAdapter(this, Type.getTypes(getBaseContext()));
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(R.string.language);
        menu.add(R.string.about);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals(getString(R.string.language))) {
            LanguageManager languageManager = new LanguageManager(this);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.language);
            builder.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice,
                    Arrays.asList(Languages.getLanguages(this))), null);
            builder.setPositiveButton(R.string.ok, ((dialog1, which) -> {
                dialog1.cancel();
                recreate();
            }));
            AlertDialog dialog = builder.create();
            dialog.getListView().setItemsCanFocus(false);
            dialog.getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            dialog.show();
            Configuration configuration = getResources().getConfiguration();
            String s = configuration.locale.toString().toLowerCase(Locale.ROOT);
            for (int i = 0; i < Languages.TOTAL_LANGUAGES; i++) {
                if (s.equals(Languages.COUNTRY_CODE[i].toLowerCase(Locale.ROOT)) || s.substring(0, 2).equals(Languages.COUNTRY_CODE[i])) {
                    dialog.getListView().setItemChecked(i, true);
                    break;
                }
            }
            dialog.getListView().setOnItemClickListener((parent, view, position, id) ->
                    languageManager.updateResource(Languages.COUNTRY_CODE[position]));
        } else {
            LinearLayout layout = new LinearLayout(getBaseContext());
            layout.setOrientation(LinearLayout.VERTICAL);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setSize(1,50);
            layout.setDividerDrawable(drawable);
            layout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            TextView version = new TextView(getBaseContext());
            TextView github = new TextView(getBaseContext());
            TextView gitee = new TextView(getBaseContext());
            version.setText(R.string.version);
            version.setGravity(Gravity.CENTER);
            version.setTextSize(17);
            github.setText(getClickableSpan(getString(R.string.githubPage),getString(R.string.githubUrl)));
            github.setMovementMethod(LinkMovementMethod.getInstance());
            github.setGravity(Gravity.CENTER);
            github.setTextSize(17);
            gitee.setText(getClickableSpan(getString(R.string.giteePage),getString(R.string.giteeUrl)));
            gitee.setMovementMethod(LinkMovementMethod.getInstance());
            gitee.setGravity(Gravity.CENTER);
            gitee.setTextSize(17);
            layout.addView(version);
            layout.addView(github);
            layout.addView(gitee);

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.about);
            builder.setView(layout);
            builder.setPositiveButton(R.string.ok, ((dialog1, which) -> {
                dialog1.cancel();
                recreate();
            }));
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return true;
    }

    private SpannableString getClickableSpan(String text, String url) {
        SpannableString spannableString = new SpannableString(text);
        int end = text.length();
        spannableString.setSpan(new UnderlineSpan(), 0, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.BLUE), 0, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {//Open default browser
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(url);
                intent.setData(uri);
                startActivity(intent);
            }
        }, 0, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    @SuppressWarnings("unused")//To ignore boolean value
    public static void IGNORE_RESULT(boolean b) {
    }
}