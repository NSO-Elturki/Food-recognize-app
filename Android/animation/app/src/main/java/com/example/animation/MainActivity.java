package com.example.animation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    AnimationDrawable rocketAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        workAnimation();
        animationList();

    }

    void animationList(){

        ImageView img = (ImageView)findViewById(R.id.animationList);
        img.setBackgroundResource(R.drawable.animationtest);
        rocketAnimation = (AnimationDrawable) img.getBackground();
        rocketAnimation.start();

    }

    public void moveImage(View view){

        TranslateAnimation animation = new TranslateAnimation(0.0f, 400.0f,
                0.0f, 0.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.setDuration(11000);  // animation duration
        animation.setRepeatCount(1000);  // animation repeat count
        animation.setRepeatMode(2);   // repeat animation (left to right, right to left )
        //animation.setFillAfter(true);

        view.startAnimation(animation);  // start animation
    }

    public void workAnimation() {
        ImageView imageView = findViewById( R.id.animationView );
        final AnimatedVectorDrawableCompat animatedVectorDrawable = AnimatedVectorDrawableCompat.create( this, R.drawable.avd_anim );
        imageView.setImageDrawable(animatedVectorDrawable);
        animatedVectorDrawable.start();
        this.moveImage(imageView);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            animatedVectorDrawable.registerAnimationCallback( new Animatable2Compat.AnimationCallback() {
                @Override
                public void onAnimationStart(Drawable drawable) {
                    super.onAnimationStart( drawable );
                }

                @Override
                public void onAnimationEnd(Drawable drawable) {
                    super.onAnimationEnd(drawable);
                    animatedVectorDrawable.start();

                }
            } );
        }
    }
}
