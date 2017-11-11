package com.example.shami.tiffin360.Adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shami.tiffin360.Data_Models.Days_Data;
import com.example.shami.tiffin360.R;

import java.util.ArrayList;

/**
 * Created by Shami on 9/11/2017.
 */

public class DeliveryDayAdapter extends RecyclerView.Adapter<DeliveryDayAdapter.DeliveryDayViewHolder> {



    ArrayList<Days_Data> mList;


    public DeliveryDayAdapter(ArrayList<Days_Data> list)
    {
        mList=list;
    }


    @Override
    public DeliveryDayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (parent instanceof RecyclerView)
        {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.day_item,parent,false);
            view.setFocusable(true);
            return new DeliveryDayViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(final DeliveryDayViewHolder holder, final int position) {

        holder.dayTV.setText(mList.get(position).getmDay());
        holder.priceTV.setText(mList.get(position).getmPrice());
        holder.quanityET.setText(Integer.toString(mList.get(position).getmCount()));

        holder.quanityET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try
                {
                    mList.get(position).setmCount(Integer.parseInt(holder.quanityET.getText().toString()));

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        holder.plusIconIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount=Integer.parseInt(holder.quanityET.getText().toString());
                amount=amount+1;
                holder.quanityET.setText(Integer.toString(amount));
                mList.get(position).setmCount(amount);

            }
        });

        holder.minusIconIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount=Integer.parseInt(holder.quanityET.getText().toString());
                if(amount>1)
                {
                    amount=amount-1;
                    holder.quanityET.setText(Integer.toString(amount));
                    mList.get(position).setmCount(amount);
                }
            }
        });

        holder.priceCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true)
                {
                    holder.zema_quanity_picker.setVisibility(View.VISIBLE);
                    mList.get(position).setmFlag(true);
                }
                else
                {
                    holder.zema_quanity_picker.setVisibility(View.INVISIBLE);
                    mList.get(position).setmFlag(false);
                }
            }
        });

    }



    public ArrayList<Days_Data> returnList()
    {
        return mList;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class DeliveryDayViewHolder extends RecyclerView.ViewHolder {

    private final TextView dayTV;
    private final TextView priceTV;
    private final CheckBox priceCheckBox;
    private final View zema_quanity_picker;
    private final TextView qtyTV;
    private final TextView plusIconIV;
    private final EditText quanityET;
    private final TextView minusIconIV;

    public DeliveryDayViewHolder(View itemView) {
        super(itemView);
        dayTV=(TextView)itemView.findViewById(R.id.dayTV);
        priceTV=(TextView)itemView.findViewById(R.id.priceTV);
        priceCheckBox=(CheckBox)itemView.findViewById(R.id.priceCheckBox);
        zema_quanity_picker=(View)itemView.findViewById(R.id.zema_quanity_picker);
        qtyTV=(TextView)itemView.findViewById(R.id.qtyTV);
        plusIconIV=(TextView)itemView.findViewById(R.id.plusIconIV);
        quanityET=(EditText)itemView.findViewById(R.id.quanityET);
        minusIconIV=(TextView)itemView.findViewById(R.id.minusIconIV);
    }
}

}
