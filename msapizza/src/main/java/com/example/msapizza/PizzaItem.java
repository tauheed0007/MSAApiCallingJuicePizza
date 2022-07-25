package com.example.msapizza;

public class PizzaItem {
    private String mImageUrl;
    private String mCreator;
    private String mphone;
    private int mLikes;

    public PizzaItem(String imageUrl, String creator, String phone,int likes ) {
        mImageUrl = imageUrl;
        mCreator = creator;
        mphone = phone;
        mLikes = likes;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getCreator() {
        return mCreator;
    }

    public String getPhone() {
        return mphone;
    }


    public int getLikeCount() {
        return mLikes;
    }
}