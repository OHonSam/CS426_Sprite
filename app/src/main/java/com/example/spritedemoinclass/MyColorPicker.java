package com.example.spritedemoinclass;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class MyColorPicker extends View {
    public MyColorPicker(Context context) {
        super(context);
    }

    public MyColorPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyColorPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyColorPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private Bitmap bitmap;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.colorcollection);
        // immutable

/*        Bitmap tempBitmap;
        tempBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888); // mutable
        Canvas myBitmapCanvas = new Canvas(bitmap);
        Paint myPaint = new Paint();
        myPaint.setColor(Color.RED);
        myBitmapCanvas.drawRect(new Rect(0, 0, 100, 200), myPaint);
        canvas.drawBitmap(tempBitmap, 0, 0, null);*/
        canvas.drawColor(Color.RED);
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);

//        int pointerIndex = event.getActionIndex();
        // get pointer ID
//        int pointerId = event.getPointerId(pointerIndex);

        // get masked (not specific to a pointer) action
        int maskedAction = event.getActionMasked();
        float x = event.getX();
        float y = event.getY();

        switch (maskedAction) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {
                // TODO use data
                getColorAtTouch(x, y);
                break;
            }
            case MotionEvent.ACTION_MOVE: { // a pointer was moved
                // TODO use data
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP: {
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                // TODO use data
                break;
            }
        }

        return true;

    }



    public int SelectedColor = Color.BLACK;

    private void getColorAtTouch(float x, float y) {
        int selColor = bitmap.getPixel((int)x, (int)y); // 4 bytes to represent a color
        MyGlobal.SelectedColor = selColor;
        //SelectedColor = selColor;
    }
}
