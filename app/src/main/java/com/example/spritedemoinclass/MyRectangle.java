package com.example.spritedemoinclass;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class MyRectangle  extends  MyShape {
    @Override
    public void draw(Canvas canvas) {
        //super.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(penColor);
        canvas.drawRect(new Rect(P1.x, P1.y, P2.x, P2.y), paint);
    }
}
