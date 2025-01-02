package com.shiftschedule.app.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.style.LineBackgroundSpan;
import android.util.TypedValue;
import androidx.annotation.ColorRes;
import com.shiftschedule.app.R;

public class GroupDayDecorator implements LineBackgroundSpan {
    private final int color;
    private final float radius;

    public GroupDayDecorator(Context context, @ColorRes int colorResId) {
        this.color = context.getResources().getColor(colorResId);
        this.radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20,
                context.getResources().getDisplayMetrics());
    }

    @Override
    public void drawBackground(Canvas canvas, Paint paint,
                             int left, int right, int top, int baseline, int bottom,
                             CharSequence text, int start, int end, int lineNum) {
        Paint circlePaint = new Paint(paint);
        circlePaint.setColor(color);
        circlePaint.setAlpha(50);

        float centerX = (left + right) / 2f;
        float centerY = (top + bottom) / 2f;

        canvas.drawCircle(centerX, centerY, radius, circlePaint);
    }

    public static @ColorRes int getGroupColor(int groupIndex) {
        switch (groupIndex) {
            case 0: return R.color.group_1;
            case 1: return R.color.group_2;
            case 2: return R.color.group_3;
            case 3: return R.color.group_4;
            case 4: return R.color.group_5;
            case 5: return R.color.group_6;
            default: return R.color.group_1;
        }
    }
} 