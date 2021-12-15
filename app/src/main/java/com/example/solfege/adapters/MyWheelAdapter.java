package com.example.solfege.adapters;

import com.example.solfege.external.wheelview.adapter.WheelAdapter;

import java.util.List;

public class MyWheelAdapter{
    public static WheelAdapter<String> getAdapter(List<String> list) {
        return new WheelAdapter<String>() {
            @Override
            public int getItemsCount() {
                return list.size();
            }

            @Override
            public String getItem(int index) {
                return list.get(index);
            }

            @Override
            public int indexOf(String o) {
                return list.indexOf(o);
            }
        };
    }
}