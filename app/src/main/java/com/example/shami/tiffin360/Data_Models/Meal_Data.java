package com.example.shami.tiffin360.Data_Models;

/**
 * Created by Shami on 9/11/2017.
 */

public class Meal_Data {
    private String Food_Title;
    private String Food_Descrption;

    public Meal_Data(String food_Time, String food_Descrption) {
        Food_Title = food_Time;
        Food_Descrption = food_Descrption;
    }

    public String getFood_Title() {
        return Food_Title;
    }

    public void setFood_Time(String food_Time) {
        Food_Title = food_Time;
    }

    public String getFood_Descrption() {
        return Food_Descrption;
    }

    public void setFood_Descrption(String food_Descrption) {
        Food_Descrption = food_Descrption;
    }
}
