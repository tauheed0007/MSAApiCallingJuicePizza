package com.example.msajuice;

import static com.example.msajuice.JuiceMainActivity.EXTRA_CREATOR_juice;
import static com.example.msajuice.JuiceMainActivity.EXTRA_LIKES_juice;
import static com.example.msajuice.JuiceMainActivity.EXTRA_URL_juice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.msajuice.databinding.ActivityJuiceDetailBinding;

public class JuiceDetailActivity extends AppCompatActivity {

    private ActivityJuiceDetailBinding activityJuiceDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityJuiceDetailBinding = ActivityJuiceDetailBinding.inflate(getLayoutInflater());
        setContentView(activityJuiceDetailBinding.getRoot());

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL_juice);
        String creatorName = intent.getStringExtra(EXTRA_CREATOR_juice);
        int likeCount = intent.getIntExtra(EXTRA_LIKES_juice, 0);

        final ImageView imageViewDetailJuice = activityJuiceDetailBinding.imageViewDetailJuice;
        final TextView textViewCreatorDetailJuice = activityJuiceDetailBinding.textViewCreatorDetailJuice;
        final TextView textViewLikeDetailJuice = activityJuiceDetailBinding.textViewLikeDetailJuice;

        Glide.with(this).load(imageUrl).fitCenter().into(imageViewDetailJuice);
        textViewCreatorDetailJuice.setText(creatorName);
        textViewLikeDetailJuice.setText("Likes: " + likeCount);
    }
}