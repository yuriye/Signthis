package com.yelisoft.signthis;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Point> arrayPoint = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();
        int h = res.getDisplayMetrics().heightPixels;
        int w = res.getDisplayMetrics().widthPixels;

//        PaintView paintView = new PaintView(this, Color.BLACK, 255, 4, w, h);
        PaintView paintView = new PaintView(this);
        setContentView(paintView);

    }

    public void addPoint(Point point) {
        arrayPoint.add(point);
    }

    public ArrayList<Point> getPoints() {
        return arrayPoint;
    }


}