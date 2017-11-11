package com.example.shami.tiffin360;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;

import com.example.shami.tiffin360.Data_Models.Days_Data;
import com.example.shami.tiffin360.UtilityClass.Constants;
import com.example.shami.tiffin360.databinding.SelectMealBinding;

import java.util.ArrayList;

/**
 * Created by Shami on 9/14/2017.
 */

public class Select_Meal extends AppCompatActivity {

    String mZipCode;
    String mMealID;
    String mMealPlanLinkBuilder;
    ArrayList<Days_Data> mList;

    SelectMealBinding mSelectMealBinding;


    boolean mChickenCheck=false;
    boolean mBeefCheck=false;
    boolean mLambCheck=false;
    boolean mSeaFoodCheck=false;


    String mFinalUrl=Constants.TIFFINAPI_BASE_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_meal);

        WindowManager.LayoutParams wmlp = getWindow().getAttributes();

        wmlp.gravity = Gravity.BOTTOM ;

        Intent intent=getIntent();

        if(intent!=null)
        {

            mZipCode=intent.getStringExtra(Constants.ZIP_CODE);

            mFinalUrl=mFinalUrl.concat(mZipCode.trim());

            mMealID=intent.getStringExtra(Constants.MEAL_ID);



            mMealPlanLinkBuilder=intent.getStringExtra(Constants.LINKBUILDER);

            mFinalUrl=mFinalUrl.concat(mMealPlanLinkBuilder);


            mList=intent.getParcelableArrayListExtra(Constants.Days_Data);

            for(int i=0;i<mList.size();i++)
            {
                if(mList.get(i).ismFlag())
                {
                    mFinalUrl=mFinalUrl.concat("&tmcp_checkbox_"+mMealID+mList.get(i).getmCode()+"&tmcp_checkbox_"+mMealID+"_"+i+"_quantity="+mList.get(i).getmCount());
                }
            }

            mFinalUrl=mFinalUrl.concat("&tmcp_radio_6=Vegetarian_0");

        }

        mSelectMealBinding= DataBindingUtil.setContentView(Select_Meal.this,R.layout.select_meal);

        mSelectMealBinding.chickenCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked)
               {
                   mChickenCheck=true;
               }
               else
               {

                   mChickenCheck=false;

               }

            }
        });

        mSelectMealBinding.beefCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    mBeefCheck=true;
                }
                else
                {

                    mBeefCheck=false;

                }
            }
        });

        mSelectMealBinding.lambCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    mLambCheck=true;
                }
                else
                {

                    mLambCheck=false;

                }
            }
        });

        mSelectMealBinding.seefoodCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    mSeaFoodCheck=true;
                }
                else
                {

                    mSeaFoodCheck=false;

                }
            }
        });

        mSelectMealBinding.checkOutbtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(mChickenCheck)
                        {
                            mFinalUrl=mFinalUrl.concat("&tmcp_checkbox_8_0=Chicken_0");
                        }
                        if(mBeefCheck)
                        {
                            mFinalUrl=mFinalUrl.concat("&tmcp_checkbox_9_0=Beef_0");
                        }
                        if(mLambCheck)
                        {
                            mFinalUrl=mFinalUrl.concat("&tmcp_checkbox_11_0=Lamb_0");
                        }
                        if(mSeaFoodCheck)
                        {
                            mFinalUrl=mFinalUrl.concat("&tmcp_checkbox_10_0=Seafood_0");
                        }

                        mFinalUrl=mFinalUrl.concat("&tmcp_textfield_12="+mSelectMealBinding.adressTV.getText().toString().trim()+"&tmcp_textfield_14=none&add-to-cart=421&tcajax=1");

                        Uri uri = Uri.parse(mFinalUrl);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);

                        Log.v("Url : ",mFinalUrl);
                    }
                }
        );

    }

}
