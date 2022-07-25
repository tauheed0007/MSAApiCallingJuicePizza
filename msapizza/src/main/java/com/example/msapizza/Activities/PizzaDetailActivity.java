package com.example.msapizza.Activities;

import static com.example.msapizza.Activities.PizzaMainActivity.EXTRA_CREATOR_pizza;
import static com.example.msapizza.Activities.PizzaMainActivity.EXTRA_LIKES_pizza;
import static com.example.msapizza.Activities.PizzaMainActivity.EXTRA_PHONE;
import static com.example.msapizza.Activities.PizzaMainActivity.EXTRA_URL_pizza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.msapizza.databinding.ActivityPizzaDetailBinding;

public class PizzaDetailActivity extends AppCompatActivity {

    private ActivityPizzaDetailBinding activityPizzaDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPizzaDetailBinding = ActivityPizzaDetailBinding.inflate(getLayoutInflater());
        setContentView(activityPizzaDetailBinding.getRoot());

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL_pizza);
        String creatorName = intent.getStringExtra(EXTRA_CREATOR_pizza);
        String phone = intent.getStringExtra(EXTRA_PHONE);
        int likeCount = intent.getIntExtra(EXTRA_LIKES_pizza, 0);

        Glide.with(this).load(imageUrl).fitCenter().into(activityPizzaDetailBinding.imageViewDetailPizza);
        activityPizzaDetailBinding.textViewCreatorDetailPizza.setText(creatorName);
        activityPizzaDetailBinding.textViewPhonePizza.setText(phone);
        activityPizzaDetailBinding.textViewLikeDetailPizza.setText("Likes: " + likeCount);
    }
}