package com.example.shami.tiffin360.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.shami.tiffin360.Image_Fragment;

/**
 * Created by Shami on 9/16/2017.
 */

public class Preview_Adapter extends FragmentPagerAdapter {

    public Preview_Adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new Image_Fragment();
            case 1:
                return new Image_Fragment();
            case 2:
                return new Image_Fragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
