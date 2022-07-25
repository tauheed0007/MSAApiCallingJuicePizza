package com.example.msapizza;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.msajuice.BaseActivity;
import com.example.msapizza.databinding.ActivityPizzaDetailBinding;
import com.example.msapizza.databinding.ActivityPizzaMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PizzaMainActivity extends AppCompatActivity implements PizzaAdapter.OnItemClickListener {

    private ActivityPizzaMainBinding bindingPizza;

    public static final String EXTRA_URL_pizza = "imageUrl";
    public static final String EXTRA_CREATOR_pizza = "creatorName";
    public static final String EXTRA_LIKES_pizza = "likeCount";
    public static final String EXTRA_PHONE = "phone";

    private PizzaAdapter mExampleAdapter;
    private ArrayList<PizzaItem> mPizzaList;
    private RequestQueue mRequestQueue;

    private final double lat = BaseActivity.global_latitude;
    private final double lng = BaseActivity.global_longitude;
    private final double radius = BaseActivity.radius;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingPizza = ActivityPizzaMainBinding.inflate(getLayoutInflater());
        setContentView(bindingPizza.getRoot());

        bindingPizza.progressBarPizza.setVisibility(View.VISIBLE);
        bindingPizza.recyclerViewPizza.setHasFixedSize(true);
        bindingPizza.recyclerViewPizza.setLayoutManager(new LinearLayoutManager(this));

        mPizzaList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }

    private void parseJSON() {
        String url = "https://api.yelp.com/v3/businesses/search?term=pizza&"+lat+"&"+lng+"&"+radius;
        Log.d("latii","Done "+lat);
        JSONObject parameters = new JSONObject();
        try {
            parameters.put("key", "value");
        } catch (Exception e) {
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            bindingPizza.progressBarPizza.setVisibility(View.GONE);
                            JSONArray jsonArray = response.getJSONArray("businesses");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);
                                String creatorName = hit.getString("name");
                                String imageUrl = hit.getString("image_url");
                                String phone = hit.getString("phone");
                                int likeCount = hit.getInt("review_count");

                                mPizzaList.add(new PizzaItem(imageUrl, creatorName, phone,likeCount));
                            }

                            mExampleAdapter = new PizzaAdapter(PizzaMainActivity.this, mPizzaList);
                            bindingPizza.recyclerViewPizza.setAdapter(mExampleAdapter);
                            mExampleAdapter.setOnItemClickListener(PizzaMainActivity.this);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer 2ROaa2Rh9qu3WVTCms8FoVE4mSfHQHC7QJua95-kKT-PqzIlLSrs4tmHVdtdFw_66-JNfRiJmbCByHTvFNy5dQq-tpfS4FrPpupIzKlgELR3br-r5trpeFhrCRgwWnYx");
                return headers;
            }
        };

        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, PizzaDetailActivity.class);
        PizzaItem clickedItem = mPizzaList.get(position);

        detailIntent.putExtra(EXTRA_URL_pizza, clickedItem.getImageUrl());
        detailIntent.putExtra(EXTRA_CREATOR_pizza, clickedItem.getCreator());
        detailIntent.putExtra(EXTRA_LIKES_pizza, clickedItem.getLikeCount());
        detailIntent.putExtra(EXTRA_PHONE, clickedItem.getLikeCount());

        startActivity(detailIntent);
    }
}