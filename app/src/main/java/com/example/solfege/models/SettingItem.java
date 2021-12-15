package com.example.solfege.models;

import android.view.View;

public class SettingItem {
    private final String titleText;
    private final View UIElement;

    public SettingItem(String titleText, View UIElement) {
        this.titleText = titleText;
        this.UIElement = UIElement;
    }

    public String getTitleText() {
        return titleText;
    }

    public View getUIElement() {
        return UIElement;
    }
}
