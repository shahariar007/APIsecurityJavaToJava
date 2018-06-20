package com.sil.it.copy.utills;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sil.it.copy.fragment.IDFragment;

public class ViewPagerAdapters extends FragmentStatePagerAdapter {

    public ViewPagerAdapters(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return IDFragment.newInstance(position + "", "");
    }

    @Override
    public int getCount() {
        return 4;
    }
}
