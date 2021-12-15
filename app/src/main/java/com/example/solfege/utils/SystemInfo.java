package com.example.solfege.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

public class SystemInfo {
    public static int getHeight(Context context) {
        int height = 1920;
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Point point = new Point();
            if (wm != null) {
                wm.getDefaultDisplay().getSize(point);
                height = point.y - 80;
            }
        return height;
    }

    public static int getWidth(Context context) {
        int width = 900;
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Point point = new Point();
            if (wm != null) {
                wm.getDefaultDisplay().getSize(point);
                width = point.x - 480;
            }
        return width;
    }
}
