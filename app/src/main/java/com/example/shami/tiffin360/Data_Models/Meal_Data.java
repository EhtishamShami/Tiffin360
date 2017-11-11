package com.example.shami.tiffin360.Data_Models;

/**
 * Created by Shami on 9/11/2017.
 */

public class Meal_Data {

    private String mID;
    private String mTitle;
    private String mCustomer_fields;
    private String mCustomField_Description;
    private String mLinkBuilder;
    private String mPrice;
    private String mCustomField_image;
    private boolean enabled;

    public Meal_Data(String mID,String mTitle, String mCustomer_fields, String mCustomField_Description, String mLinkBuilder, String mPrice, String mCustomField_image, boolean enabled) {

        this.mID=mID;
        this.mTitle = mTitle;
        this.mCustomer_fields = mCustomer_fields;
        this.mCustomField_Description = mCustomField_Description;
        this.mLinkBuilder = mLinkBuilder;
        this.mPrice = mPrice;
        this.mCustomField_image = mCustomField_image;
        this.enabled = enabled;
    }

    public String getmID() {
        return mID;
    }

    public void setmID(String mID) {
        this.mID = mID;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmCustomer_fields() {
        return mCustomer_fields;
    }

    public void setmCustomer_fields(String mCustomer_fields) {
        this.mCustomer_fields = mCustomer_fields;
    }

    public String getmCustomField_Description() {
        return mCustomField_Description;
    }

    public void setmCustomField_Description(String mCustomField_Description) {
        this.mCustomField_Description = mCustomField_Description;
    }

    public String getmLinkBuilder() {
        return mLinkBuilder;
    }

    public void setmLinkBuilder(String mLinkBuilder) {
        this.mLinkBuilder = mLinkBuilder;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public String getmCustomField_image() {
        return mCustomField_image;
    }

    public void setmCustomField_image(String mCustomField_image) {
        this.mCustomField_image = mCustomField_image;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }



}
