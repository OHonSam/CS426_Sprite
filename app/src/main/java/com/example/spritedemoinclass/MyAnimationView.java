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
import java.util.Random;
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
                //update each sprite
                for (int i=0; i<sprites.size(); i++)
                    sprites.get(i).update();
                postInvalidate();
            }
        };

        timer = new Timer();
        timer.schedule(timerTask, 1000, 40); // 25 fps
    }

    private void CreateAngel(int left, int top) {
        int[] angelResources = {
                R.drawable.angel01,
                R.drawable.angel02,
                R.drawable.angel03,
                R.drawable.angel04,
                R.drawable.angel05,
                R.drawable.angel06,
                R.drawable.angel07,
                R.drawable.angel08,
                R.drawable.angel09,
                R.drawable.angel10,
                R.drawable.angel11,
                R.drawable.angel12,
                R.drawable.angel13,
                R.drawable.angel14,
                R.drawable.angel15
        };
        Bitmap[] bitmaps = new Bitmap[angelResources.length];
        for (int i = 0; i < angelResources.length; i++) {
            bitmaps[i] = BitmapFactory.decodeResource(getResources(), angelResources[i]);
        }
        My2DSprite newSprite = new My2DSprite(bitmaps, left, top, 0, 0);
        sprites.add(newSprite);
    }

    private void CreateBuilding(int left, int top, int resID) {
        createSpriteWithASingleImage(left, top, resID);
    }
    private void CreateIsland(int left, int top, int resID) {
        createSpriteWithASingleImage(left, top, resID);
    }

    private void createSpriteWithASingleImage(int left, int top, int resID) {
        Bitmap[] bitmaps = new Bitmap[1];
        bitmaps[0] = BitmapFactory.decodeResource(getResources(), resID);
        My2DSprite newSprite = new My2DSprite(bitmaps, left, top, 0, 0);
        sprites.add(newSprite);
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
                handleActionDown(x, y);
                break;
            }
            case MotionEvent.ACTION_MOVE: { // a pointer was moved
                handleActionMove(x, y);
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP: {
                handleActionUp(x, y);
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                handleActionCancel();
                break;
            }
        }
        return true;

    }

    private void handleActionCancel() {
        // TODO use data
    }

    private void handleActionUp(float x, float y) {
        endDrag(x, y);
    }

    private void handleActionMove(float x, float y) {
        // TODO use data
        processDrag(x, y);
//        My2DSprite sprite = sprites.get(selectedIndex);
//        resizeSprite(sprite, 1.1F);
    }

    private void handleActionDown(float x, float y) {
        // TODO use data
        int tempIdx = getSelectedSpriteIndex(x, y);
        if (tempIdx!=-1) {
            selectSprite(tempIdx);
            invalidate();
        }
        beginDrag(x, y);
    }

    private void endDrag(float x, float y) {
        processDrag(x, y);
        selectedIndex = -1; // no sprite is currently being dragged
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
            //invalidate();
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
//    private void randomizeSpritePosition(My2DSprite sprite) {
//        Random random = new Random();
//        sprite.left = random.nextInt(getWidth() - sprite.getWidth());
//        sprite.top = random.nextInt(getHeight() - sprite.getHeight());
//        postInvalidate();
//    }
    private void resizeSprite(My2DSprite sprite, float scaleFactor) {
        for (int i = 0; i < sprite.BMPs.length; i++) {
            sprite.BMPs[i] = Bitmap.createScaledBitmap(sprite.BMPs[i],
                    (int)(sprite.BMPs[i].getWidth() * scaleFactor),
                    (int)(sprite.BMPs[i].getHeight() * scaleFactor),
                    true);
        }
        postInvalidate();
    }

}
