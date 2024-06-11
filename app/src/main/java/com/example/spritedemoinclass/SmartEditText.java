package com.example.spritedemoinclass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

@SuppressLint("AppCompatCustomView")
public class SmartEditText extends EditText {
    public SmartEditText(Context context) {
        super(context);
    }

    public SmartEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmartEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SmartEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    interface MyOperation
    {
        int process(int a, int b);
    }

    private MyOperation addition = new MyOperation() {
        @Override
        public int process(int a, int b) {
            return a+b;
        }
    };

    private MyOperation subtraction = new MyOperation() {
        @Override
        public int process(int a, int b) {
            return a-b;
        }
    };

    private Map<String, MyOperation> myOperationMap;

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {

        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused)
        {
            this.setText("");
        }
        else
        {
            String s = this.getText().toString();
            myOperationMap = new HashMap<>();
            myOperationMap.put("+", addition);
            myOperationMap.put("-", subtraction);
            if (s.length()>0) {
                String[] tokens = s.split(" "); // 123 + 456
                int a = Integer.parseInt(tokens[0]);
                int b = Integer.parseInt(tokens[2]);
        //        int c = a + b;
                int c = myOperationMap.get(tokens[1]).process(a, b);
                this.setText(String.valueOf(c));
            }
        }
    }
}
