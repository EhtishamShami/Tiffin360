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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shami on 9/11/2017.
 */

public class DeliveryDayActivity extends Activity {


    DeliveryDayAdapter adapter;
    List<Days_Data> mList;
    RecyclerView zemaDayRecycler;
    TextView selectMealPlan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_days);


        WindowManager.LayoutParams wmlp = getWindow().getAttributes();

        wmlp.gravity = Gravity.BOTTOM ;



        selectMealPlan=(TextView)findViewById(R.id.mealPlanButton);

        selectMealPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DeliveryDayActivity.this,Select_Meal.class);
                startActivity(intent);
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


    public List<Days_Data> DayData()
    {
        List<Days_Data> mList=new ArrayList<Days_Data>();
        mList.add(new Days_Data("Mo","$9.00"));
        mList.add(new Days_Data("Tu","$9.00"));
        mList.add(new Days_Data("We","$9.00"));
        mList.add(new Days_Data("Th","$9.00"));
        mList.add(new Days_Data("Fr","$9.00"));
        mList.add(new Days_Data("Sa","$9.00"));
        mList.add(new Days_Data("Su","$9.00"));

        return mList;
    }


}
