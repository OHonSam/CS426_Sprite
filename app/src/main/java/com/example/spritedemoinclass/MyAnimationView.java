package com.example.spritedemoinclass;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MyAnimationView extends View {

    public interface OnSpriteClickListener
    {
        void OnSpriteClick(View view, float x, float y, int selectedIndex);
    }

    private OnSpriteClickListener onSpriteClickListener = null;
    public void setOnSpriteClickListener(OnSpriteClickListener listener)
    {
        onSpriteClickListener = listener;
    }

    private ArrayList<My2DSprite> sprites;
    private Timer timer;
    private TimerTask timerTask;
    private float oldx;
    private float oldy;

    public MyAnimationView(Context context) {
        super(context);
        prepareContent();

    }

    private void prepareContent() {
        sprites = new ArrayList<>();
        CreateIsland(100, 100, R.drawable.starter_island01);
        CreateBuilding(50, 150, R.drawable.kindergarten01);
        CreateBuilding(500, 150, R.drawable.lava_island);
        for (int i=0; i<1; i++)
            CreateAngel(100+i*30, 100+i*2);

        timerTask = new TimerTask() {
            @Override
            public void run() {
                for (int i=0; i<sprites.size(); i++)
                    sprites.get(i).update();
                postInvalidate();
            }
        };

        timer = new Timer();
        timer.schedule(timerTask, 1000, 40); // 25 fps
    }

    private void CreateAngel(int left, int top) {
        Bitmap[] bitmaps = new Bitmap[15];
//        for (int i=0; i<bitmaps.length; i++)
//            bitmaps[i] = BitmapFactory.decodeResource(getResources(), R.drawable.angel01+i);
        bitmaps[0] = BitmapFactory.decodeResource(getResources(), R.drawable.angel01);
        bitmaps[1] = BitmapFactory.decodeResource(getResources(), R.drawable.angel02);
        bitmaps[2] = BitmapFactory.decodeResource(getResources(), R.drawable.angel03);
        bitmaps[3] = BitmapFactory.decodeResource(getResources(), R.drawable.angel04);
        bitmaps[4] = BitmapFactory.decodeResource(getResources(), R.drawable.angel05);
        bitmaps[5] = BitmapFactory.decodeResource(getResources(), R.drawable.angel06);
        bitmaps[6] = BitmapFactory.decodeResource(getResources(), R.drawable.angel07);
        bitmaps[7] = BitmapFactory.decodeResource(getResources(), R.drawable.angel08);
        bitmaps[8] = BitmapFactory.decodeResource(getResources(), R.drawable.angel09);
        bitmaps[9] = BitmapFactory.decodeResource(getResources(), R.drawable.angel10);
        bitmaps[10] = BitmapFactory.decodeResource(getResources(), R.drawable.angel11);
        bitmaps[11] = BitmapFactory.decodeResource(getResources(), R.drawable.angel12);
        bitmaps[12] = BitmapFactory.decodeResource(getResources(), R.drawable.angel13);
        bitmaps[13] = BitmapFactory.decodeResource(getResources(), R.drawable.angel14);
        bitmaps[14] = BitmapFactory.decodeResource(getResources(), R.drawable.angel15);
        My2DSprite newSprite = new My2DSprite(bitmaps, left, top, 0, 0);
        sprites.add(newSprite);
    }

    private void CreateBuilding(int left, int top, int resID) {
        createSpriteWithASingleImage(left, top, resID);

    }

    private void createSpriteWithASingleImage(int left, int top, int resID) {
        Bitmap[] bitmaps = new Bitmap[1];
        bitmaps[0] = BitmapFactory.decodeResource(getResources(), resID);
        My2DSprite newSprite = new My2DSprite(bitmaps, left, top, 0, 0);
        sprites.add(newSprite);
    }

    private void CreateIsland(int left, int top, int resID) {
        createSpriteWithASingleImage(left, top, resID);
    }

    public MyAnimationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        prepareContent();
    }

    public MyAnimationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        prepareContent();
    }

    public MyAnimationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        prepareContent();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i=0; i<sprites.size(); i++)
            sprites.get(i).draw(canvas);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int maskedAction = event.getActionMasked();

        float x = event.getX();
        float y = event.getY();
        int tempIdx;

        switch (maskedAction) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {
                // TODO use data
                tempIdx = getSelectedSpriteIndex(x, y);
                if (tempIdx!=-1) {
                    selectSprite(tempIdx);
                    invalidate();
                }
                beginDrag(x, y);

                break;
            }
            case MotionEvent.ACTION_MOVE: { // a pointer was moved
                // TODO use data
                processDrag(x, y);

                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP: {
                endDrag(x, y);

                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                // TODO use data
                break;
            }
        }

        return true;

    }

    private void endDrag(float x, float y) {
        processDrag(x, y);
        selectedIndex = -1;
    }

    private void processDrag(float x, float y) {
        if (selectedIndex!=-1)
        {
            float dx = x - oldx;
            float dy = y - oldy;
            oldx = x; oldy = y;
            sprites.get(selectedIndex).left+=dx;
            sprites.get(selectedIndex).top+=dy;
            invalidate();
        }
    }

    int selectedIndex = -1;

    private void beginDrag(float x, float y) {
        int tempIdx = getSelectedSpriteIndex(x, y);
        if (tempIdx!=-1) {
            selectedIndex = tempIdx;
            oldx = x;
            oldy = y;

            selectSprite(selectedIndex);
            if (onSpriteClickListener!=null)
            {
                onSpriteClickListener.OnSpriteClick(this, x, y, selectedIndex);
            }
            invalidate();
        }
        else selectedIndex = -1;


    }

    private void selectSprite(int newIndex) {
        for (int i=0; i<sprites.size(); i++)
            if (i == newIndex)
                sprites.get(i).State = 1;
            else
                sprites.get(i).State = 0;

    }

    private int getSelectedSpriteIndex(float x, float y) {
        for (int i=sprites.size()-1;i>=0; i--)
            if (sprites.get(i).isSelected(x, y))
                return i;

        return -1;
    }
}
