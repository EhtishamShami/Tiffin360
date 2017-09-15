package com.example.shami.tiffin360;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by Shami on 9/14/2017.
 */

public class Select_Meal extends AppCompatActivity {


    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_meal);

        WindowManager.LayoutParams wmlp = getWindow().getAttributes();

        wmlp.gravity = Gravity.BOTTOM ;
        btn1=(Button)findViewById(R.id.checkOutbtn);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Select_Meal.this,Checkout.class);
                startActivity(intent);
            }
        });

    }

}
