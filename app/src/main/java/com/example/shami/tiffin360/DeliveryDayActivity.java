package com.example.shami.tiffin360;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.shami.tiffin360.Adapters.DeliveryDayAdapter;
import com.example.shami.tiffin360.Data_Models.Days_Data;
import com.example.shami.tiffin360.UtilityClass.Constants;

import java.util.ArrayList;

/**
 * Created by Shami on 9/11/2017.
 */

public class DeliveryDayActivity extends Activity {


    DeliveryDayAdapter adapter;
    ArrayList<Days_Data> mList;
    RecyclerView zemaDayRecycler;
    TextView selectMealPlan;

    String mZipCode;
    String mMealPlanLinkBuilder;
    String mMealId;

    private static final String LOG_TAG="[DMShami "+DeliveryDayActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_days);


        WindowManager.LayoutParams wmlp = getWindow().getAttributes();

        wmlp.gravity = Gravity.BOTTOM ;


        Intent intent=getIntent();

        if(intent!=null)
        {
            mZipCode=intent.getStringExtra(Constants.ZIP_CODE);
            mMealPlanLinkBuilder=intent.getStringExtra(Constants.LINKBUILDER);
            mMealId=intent.getStringExtra(Constants.MEAL_ID);
        }


        selectMealPlan=(TextView)findViewById(R.id.mealPlanButton);

        selectMealPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(adapter!=null&&mZipCode!=null&&mMealPlanLinkBuilder!=null&&mMealId!=null)
                {
                    mList=adapter.returnList();
                    Intent intent=new Intent(DeliveryDayActivity.this,Select_Meal.class);
                    intent.putExtra(Constants.ZIP_CODE,mZipCode);
                    intent.putExtra(Constants.LINKBUILDER,mMealPlanLinkBuilder);
                    intent.putExtra(Constants.MEAL_ID,mMealId);
                    intent.putParcelableArrayListExtra(Constants.Days_Data,mList);
                    startActivity(intent);
                }

            }
        });

        mList=DayData();
        adapter=new DeliveryDayAdapter(mList);
        zemaDayRecycler=(RecyclerView)findViewById(R.id.daySelectionRecycler);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        zemaDayRecycler.setLayoutManager(manager);
        zemaDayRecycler.setAdapter(adapter);
        zemaDayRecycler.setHasFixedSize(true);

    }


    public void startActivity(Intent intent) {
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        ActivityCompat.startActivity(this, intent, activityOptions.toBundle());
    }


    public ArrayList<Days_Data> DayData()
    {
        ArrayList<Days_Data> mList=new ArrayList<Days_Data>();
        mList.add(new Days_Data("Mo","$9.00","_0=Monday_0",1,false));
        mList.add(new Days_Data("Tu","$9.00","_1=Tuesday_1",1,false));
        mList.add(new Days_Data("We","$9.00","_2=Wednesday_2",1,false));
        mList.add(new Days_Data("Th","$9.00","_3=Thursday_3",1,false));
        mList.add(new Days_Data("Fr","$9.00","_4=Friday_4",1,false));
        mList.add(new Days_Data("Sa","$9.00","_5=Saturday_5",1,false));
        mList.add(new Days_Data("Su","$9.00","_6=Sunday_6",1,false));

        return mList;
    }


}
