package com.sil.it.copy.utills;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

public class TransFormView implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.65f;
    private static final float MIN_ALPHA = 0.3f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        Log.d("positionLOG",position+"");
        if (position < -1){    // [-Infinity,-1)
            // This page is way off-screen to the left.
            page.setAlpha(0);
        }
        else if (position <= 0){    // [-1,0]
            page.setAlpha(1);
            page.setTranslationX(0);
            page.setScaleX(1);
            page.setScaleY(1);
        }
        else if (position <= 1){    // (0,1]
            page.setTranslationX(-position*page.getWidth());
            page.setAlpha(1-Math.abs(position));
            page.setScaleX(1-Math.abs(position));
            page.setScaleY(1-Math.abs(position));
        }
        else {    // (1,+Infinity]
            // This page is way off-screen to the right.
            page.setAlpha(0);
        }
       // ====================================================
//        if (position <-1){  // [-Infinity,-1)
//            // This page is way off-screen to the left.
//            page.setAlpha(0);
//        }
//        else if (position <=1){ // [-1,1]
//            page.setScaleX(Math.max(MIN_SCALE,1-Math.abs(position)));
//            page.setScaleY(Math.max(MIN_SCALE,1-Math.abs(position)));
//            page.setAlpha(Math.max(MIN_ALPHA,1-Math.abs(position)));
//        }
//        else {  // (1,+Infinity]
//            // This page is way off-screen to the right.
//            page.setAlpha(0);
//        }
        //=====================================================

//        page.setTranslationX(-position * page.getWidth());
//        if (Math.abs(position) <= 0.5) {
//            page.setVisibility(View.VISIBLE);
//            page.setScaleX(1 - Math.abs(position));
//            page.setScaleY(1 - Math.abs(position));
//        } else if (Math.abs(position) > 0.5) {
//            page.setVisibility(View.GONE);
//        }
//        if (position < -1){  // [-Infinity,-1)
//            // This page is way off-screen to the left.
//            page.setAlpha(0);
//        }
//        else if (position <= 0) {   // [-1,0]
//            page.setAlpha(1);
//            page.setRotation(360 * Math.abs(position));
//        }
//        else if (position <= 1) {   // (0,1]
//            page.setAlpha(1);
//            page.setRotation(-360 * Math.abs(position));
//        }
//        else {  // (1,+Infinity]
//            // This page is way off-screen to the right.
//            page.setAlpha(0);
//        }
        //========================================================
//        if (position < -1){    // [-Infinity,-1)
//            // This page is way off-screen to the left.
//            page.setAlpha(0);
//        }
//        else if (position <= 0){    // [-1,0]
//            page.setAlpha(1);
//            page.setTranslationX(0);
//            page.setScaleX(1);
//            page.setScaleY(1);
//        }
//        else if (position <= 1){    // (0,1]
//            page.setTranslationX(-position*page.getWidth());
//            page.setAlpha(1-Math.abs(position));
//            page.setScaleX(1-Math.abs(position));
//            page.setScaleY(1-Math.abs(position));
//        }
//        else {    // (1,+Infinity]
//            // This page is way off-screen to the right.
//            page.setAlpha(0);
//        }


    }
}
