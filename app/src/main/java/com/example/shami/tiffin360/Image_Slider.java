package com.example.shami.tiffin360;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.example.shami.tiffin360.Adapters.Preview_Adapter;
import com.example.shami.tiffin360.UtilityClass.Constants;
import com.example.shami.tiffin360.databinding.ImageSliderLayoutBinding;

import me.crosswall.lib.coverflow.CoverFlow;

/**
 * Created by Shami on 9/16/2017.
 */

public class Image_Slider extends FragmentActivity {

    ImageSliderLayoutBinding mBinding;

    Preview_Adapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_slider_layout);
        mBinding= DataBindingUtil.setContentView(Image_Slider.this,R.layout.image_slider_layout);

        Intent intent=getIntent();
        if(intent!=null&&intent.getStringExtra(Constants.slider_title)!=null)
        {
            mBinding.mealTitleTV.setText(intent.getStringExtra(Constants.slider_title));
        }

        mBinding.exitTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adapter=new Preview_Adapter(getSupportFragmentManager());

        mBinding.viewPager.setAdapter(adapter);
        mBinding.slidingTabs.setupWithViewPager(mBinding.viewPager);


        new CoverFlow.Builder()
                .with(mBinding.viewPager)
                .pagerMargin(0f)
                .scale(0.3f)
                .spaceSize(0f)
                .rotationY(0f)
                .build();


    }



}
