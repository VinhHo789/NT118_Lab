package com.example.lab3_bt2;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;
import androidx.viewpager2.widget.ViewPager2;

import com.example.lab3_bt2.PageFragment;

public class MyPagerAdapter extends FragmentStateAdapter {

    private Context context;
    private ViewPager2 viewPager; // Reference to the ViewPager2

    public MyPagerAdapter(FragmentActivity fragmentActivity, Context context, ViewPager2 viewPager) {
        super(fragmentActivity);
        this.context = context;
        this.viewPager = viewPager;
    }

    @Override
    public Fragment createFragment(int position) {
        return new PageFragment(position);
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    // Override this method to provide custom animations
    @Override
    public void onBindViewHolder(FragmentViewHolder holder, int position, java.util.List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);

        // Set up custom animations when swiping
        final ViewPager2.PageTransformer pageTransformer = (page, pos) -> {
            float absPosition = Math.abs(pos);
            page.setAlpha(1 - absPosition);
            page.setTranslationX(-pos * page.getWidth());

            // Optionally, you can use custom animators
            if (pos < 0) {
                AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.slide_in_right);
                animatorSet.setTarget(page);
                animatorSet.start();
            } else if (pos > 0) {
                AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.slide_out_left);
                animatorSet.setTarget(page);
                animatorSet.start();
            }
        };

        viewPager.setPageTransformer(pageTransformer);
    }
}