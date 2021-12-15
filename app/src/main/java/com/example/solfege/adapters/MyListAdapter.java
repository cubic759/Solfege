package com.example.solfege.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.solfege.R;
import com.example.solfege.SettingFragment;
import com.example.solfege.models.SettingItem;

import java.util.List;

public class MyListAdapter extends BaseAdapter {
    private final List<SettingItem> list;
    private final SettingFragment settingFragment;
    public MyListAdapter(List<SettingItem> list, SettingFragment settingFragment) {
        this.list = list;
        this.settingFragment=settingFragment;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = settingFragment.getLayoutInflater().inflate(R.layout.list_item_setting, container, false);
        }
        ((TextView) convertView.findViewById(R.id.textView2))
                .setText(((SettingItem) getItem(position)).getTitleText());
        FrameLayout frameLayout = convertView.findViewById(R.id.frameLayout);
        frameLayout.removeAllViews();
        frameLayout.addView(((SettingItem) getItem(position)).getUIElement());
        return convertView;
    }
}