package com.yelisoft.signthis;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by yuriye on 19.08.2017.
 */

public class PaintView extends View {

    private int linecolor = Color.BLACK;
    private int alpha = 255;
    private int lwidth = 4;
    private int WIDTH;
    private int HEIGHT;
    MainActivity act;
    Paint paint = new Paint();

    public PaintView(MainActivity act, int linecolor, int alpha, int lwidth, int width, int height) {
        super(act);
        this.act = act;
        this.linecolor = linecolor;
        this.alpha = alpha;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.lwidth = lwidth;

        paint.setStrokeWidth(lwidth);
        paint.setColor(linecolor);
        paint.setAlpha(alpha);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.MITER);
        this.setDrawingCacheBackgroundColor(Color.WHITE);
    }

    public PaintView(Context context) {
        super(context);
        Resources res = getResources();
        int h = res.getDisplayMetrics().heightPixels;
        int w = res.getDisplayMetrics().widthPixels;
        this.act = (MainActivity) context;
        this.WIDTH = w;
        this.HEIGHT = h;

        paint.setStrokeWidth(lwidth);
        paint.setColor(linecolor);
        paint.setAlpha(alpha);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.MITER);
        this.setDrawingCacheBackgroundColor(Color.WHITE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        int action = e.getAction();

        if (action == MotionEvent.ACTION_UP) {
            act.addPoint(null);

        } else if (action == MotionEvent.ACTION_MOVE) {
            Point point = new Point(Math.round(e.getX()), Math.round(e.getY()), linecolor, alpha, lwidth);
            act.addPoint(point);
            postInvalidate();
        } else if (action == MotionEvent.ACTION_DOWN) {

        }

        return true;
    }

    @Override
    protected void onDraw(Canvas c) {
        Point curr = null;
        float sx = -1.0F;
        float sy = -1.0F;

        for (Point data : act.getPoints()) {
            if (curr != null && data != null) {

                if (sx == -1.0F) {
                    sx = curr.getX();
                    sy = curr.getY();
                    continue;
                }
                c.drawLine(sx, sy, curr.getX(), curr.getY(), paint);
                sx = curr.getX();
                sy = curr.getY();
            } else {
                sx = -1.0F;
                sy = -1.0F;
            }
            curr = data;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.setMeasuredDimension(WIDTH, HEIGHT);
    }

    public void saveToPng() {
        try {
            String directory = Environment.getDataDirectory().toString();
            File file = new File(directory, "sign.png");
            file.createNewFile();
            FileOutputStream os = new FileOutputStream(file);
            Bitmap bitmap = this.getDrawingCache();
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, os);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
