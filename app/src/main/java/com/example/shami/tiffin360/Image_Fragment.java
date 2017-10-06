package com.example.shami.tiffin360;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Shami on 9/16/2017.
 */

public class Image_Fragment extends Fragment {

    public Image_Fragment(){};

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.image_item,container,false);

        return view;
    }
}
