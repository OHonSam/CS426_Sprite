package com.example.spritedemoinclass;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyPaintView extends View {
    private boolean bDraw = false;

    public MyPaintView(Context context) {
        super(context);
    }

    public MyPaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyPaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private ArrayList<MyShape> shapes = new ArrayList<>();
    private Bitmap backgroundWithShapes = null;
    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        if (backgroundWithShapes!=null)
        {
            canvas.drawBitmap(backgroundWithShapes, 0, 0, null);
            if (bDraw)
                shapes.get(shapes.size()-1).draw(canvas);
        }
        else {
            canvas.drawColor(Color.BLACK);
            for (int i = 0; i < shapes.size(); ++i) {
                shapes.get(i).draw(canvas);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        int maskedAction = event.getActionMasked();
        float x = event.getX();
        float y = event.getY();

        switch (maskedAction) {

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {
                // TODO use data
                beginDraw(x, y);
                break;
            }
            case MotionEvent.ACTION_MOVE: { // a pointer was moved
                // TODO use data
                if (bDraw)
                    processDraw(x, y);
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP: {
                if (bDraw)
                    endDraw(x, y);
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                // TODO use data
                break;
            }
        }

        return true;



    }

    private void endDraw(float x, float y) {
        bDraw = false;
        processDraw(x, y);
        createBackgroundWithShapes();
    }

    private void createBackgroundWithShapes() {
        if (backgroundWithShapes == null)
            backgroundWithShapes =
                    Bitmap.createBitmap(getCurrentScreenWidth(), getCurrentScreenHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(backgroundWithShapes);
        canvas.drawARGB(255, 255, 255, 255);
        for (int i=0; i<shapes.size(); i++)
            shapes.get(i).draw(canvas);

    }

    private int getCurrentScreenHeight() {
        return 1024;
    }

    private int getCurrentScreenWidth() {
        return 1024;
    }

    private void processDraw(float x, float y) {
        MyShape curShape = shapes.get(shapes.size()-1);
        curShape.P2 = new Point((int)x, (int)y);
        invalidate();
    }

    private void beginDraw(float x, float y) {
        bDraw = true;
        MyShape newShape = null;
        newShape = new MyLine();
        // option, menu ==> MyLine or MyRectangle ...
        newShape.P1 = new Point((int)x, (int)y);
        newShape.P2 = new Point((int)x, (int)y);
        MyColorPicker colorPicker = (MyColorPicker)findViewById(R.id.colorPicker);
//        newShape.penColor = colorPicker.SelectedColor;
        newShape.penColor = MyGlobal.SelectedColor;
        shapes.add(newShape);
        invalidate();
    }
}
