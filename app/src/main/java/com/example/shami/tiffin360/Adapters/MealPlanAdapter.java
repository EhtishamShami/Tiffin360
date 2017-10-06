package com.example.shami.tiffin360.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shami.tiffin360.Data_Models.Meal_Data;
import com.example.shami.tiffin360.Image_Slider;
import com.example.shami.tiffin360.R;
import com.example.shami.tiffin360.UtilityClass.Constants;

import java.util.List;

/**
 * Created by Shami on 9/11/2017.
 */

public class MealPlanAdapter extends  RecyclerView.Adapter<MealPlanAdapter.MealPlanAdapterViewHolder> {


    List<Meal_Data> mealData;

    callback mClickLister;

    Context mContext;

    public MealPlanAdapter(Context context,List<Meal_Data> mMealData, callback dh)
    {
        mContext=context;
        mealData=mMealData;
        mClickLister=dh;
    }

    @Override
    public MealPlanAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(parent instanceof RecyclerView)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item, parent, false);
            view.setFocusable(true);
            return new MealPlanAdapterViewHolder(view);
        }


        return null;
    }

    @Override
    public void onBindViewHolder(final MealPlanAdapterViewHolder holder, final int position) {

        holder.mTitle.setText(mealData.get(position).getFood_Title());
        holder.mDescription.setText(mealData.get(position).getFood_Descrption());
        if(mealData.get(position).isEnabled())
        {
            holder.mCheckerIV.setImageResource(R.drawable.checked);
        }
        else
        {
            holder.mCheckerIV.setImageResource(R.drawable.un_check_white);
        }

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, Image_Slider.class);
                intent.putExtra(Constants.slider_title,mealData.get(position).getFood_Title());
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mealData.size();
    }




    public class MealPlanAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public final ImageView mImageView;
    public final TextView  mTitle;
    public final TextView  mDescription;
    public final ImageView mCheckerIV;

    public MealPlanAdapterViewHolder(View view)
    {
        super(view);
        mImageView=(ImageView)view.findViewById(R.id.iconIV);
        mTitle=(TextView)view.findViewById(R.id.titleTV);
        mDescription=(TextView)view.findViewById(R.id.priceTV);
        mCheckerIV=(ImageView)view.findViewById(R.id.checkbox);
        mCheckerIV.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        mClickLister.SelectItem(getAdapterPosition());
    }

}


public void Update(int position)
{
    for(int i=0;i<mealData.size();i++)
    {
        if(i==position)
        {
            mealData.get(i).setEnabled(true);
        }
        else
        {

            mealData.get(i).setEnabled(false);
        }
    }


    notifyDataSetChanged();
}

public interface callback
{
    public void SelectItem(int position);
}


}
