package com.example.spritedemoinclass;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements MyAnimationView.OnSpriteClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        MyAnimationView animationView = new MyAnimationView(this);

        animationView.setOnSpriteClickListener(this);
        setContentView(animationView);
    }

    @Override
    public void OnSpriteClick(View view, float x, float y, int selectedIndex) {

    }
}