/*
 * Copyright (C) 2015 Kalin Maldzhanski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.solfege.external.multiselectspinner;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultiSelectSpinner extends BaseMultiSelectSpinner {

    public MultiSelectSpinner(Context context) {
        super(context);
    }

    public MultiSelectSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MultiSelectSpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MultiSelectSpinner(Context context, AttributeSet attrs, int defStyle, int styleRes) {
        super(context, attrs, defStyle, styleRes);
    }

    public MultiSelectSpinner setItems(List<String> items) {
        this.items = items;

        // all selected by default
        selected = new boolean[items.size()];
        if (selectAll) {
            Arrays.fill(selected, true);
        }

        // all text on the spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(),
                spinnerItemLayout, new String[]{(isSelectAll()) ? allCheckedText : allUncheckedText});
        setAdapter(spinnerAdapter);

        return this;
    }

    public void setListAdapter(ListAdapter listAdapter) {
        this.listAdapter = listAdapter;
        this.items = new ArrayList<>();
        selected = new boolean[listAdapter.getCount()];
        for (int i = 0; i < listAdapter.getCount(); i++) {
            items.add(String.valueOf(listAdapter.getItem(i)));
            if (selectAll) {
                selected[i] = true;
            }

        }

        // all text on the spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(),
                spinnerItemLayout, new String[]{(isSelectAll()) ? allCheckedText : allUncheckedText});
        setAdapter(spinnerAdapter);

    }

    public ListAdapter getListAdapter() {
        return this.listAdapter;
    }

    //  @SuppressLint("NewApi")
    @SuppressLint({"NewApi", "ClickableViewAccessibility"})
    @Override
    public boolean performClick() {
        AlertDialog.Builder builder;
        final AlertDialog dialog;
        if (choiceDialogTheme > 0) {
            builder = new AlertDialog.Builder(getContext(), choiceDialogTheme);
        } else {
            builder = new AlertDialog.Builder(getContext());
        }

        builder.setTitle(title);

        builder.setOnCancelListener(this);
        builder.setPositiveButton(android.R.string.ok,
                (dialog1, which) -> dialog1.cancel()
        );

        if (listAdapter != null) {
            builder.setAdapter(this.listAdapter, null);
            dialog = builder.create();
            dialog.getListView().setItemsCanFocus(false);
            dialog.getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

            dialog.show();
            for (int i = 0; i < listAdapter.getCount(); i++) {
                dialog.getListView().setItemChecked(i, selected[i]);
            }
            dialog.getListView().setOnItemClickListener((parent, view, position, id) -> {
                if (!selected[position]) {
                    if (dialog.getListView().getCheckedItemCount() > maxSelectedItems) {
                        dialog.getListView().setItemChecked(position, false);
                    } else {
                        selected[position] = !selected[position];
                    }
                } else {
                    if (dialog.getListView().getCheckedItemCount() < minSelectedItems) {
                        dialog.getListView().setItemChecked(position, true);
                    } else {
                        selected[position] = !selected[position];
                    }
                }


            });
        } else if (items != null) {
            dialog = builder.setMultiChoiceItems(items.toArray(new CharSequence[0]), selected, this).show();
        } else {
            dialog = null;
        }
        if (titleDividerDrawable != null && dialog != null) {
            int dividerId = dialog.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
            View divider = dialog.findViewById(dividerId);
            if (divider != null) {
                divider.setBackground(titleDividerDrawable);
            }
        }

        if (titleDividerColor != 0 && dialog != null) {
            int dividerId = dialog.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
            View divider = dialog.findViewById(dividerId);
            if (divider != null) {
                divider.setBackgroundColor(titleDividerColor);
            }
        }
        return dialog != null;
    }
}