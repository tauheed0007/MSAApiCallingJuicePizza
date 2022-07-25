package com.example.msajuice;

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
import com.example.msajuice.databinding.ActivityJuiceMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JuiceMainActivity extends AppCompatActivity implements JuiceAdapter.OnItemClickListener {

    private ActivityJuiceMainBinding bindingJuice;

    public static final String EXTRA_URL_juice = "imageUrl";
    public static final String EXTRA_CREATOR_juice = "creatorName";
    public static final String EXTRA_LIKES_juice = "likeCount";


    private JuiceAdapter mExampleAdapter;
    private ArrayList<JuiceItem> mJuiceList;
    private RequestQueue mRequestQueue;
    private final double lat = BaseActivity.global_latitude;
    private final double lng = BaseActivity.global_longitude;
    private final double radius = BaseActivity.radius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingJuice = ActivityJuiceMainBinding.inflate(getLayoutInflater());
        setContentView(bindingJuice.getRoot());




        bindingJuice.progressBarJuice.setVisibility(View.VISIBLE);
        bindingJuice.recyclerViewJuice.setHasFixedSize(true);
        bindingJuice.recyclerViewJuice.setLayoutManager(new LinearLayoutManager(this));

        mJuiceList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }

    private void parseJSON() {
        String url = "https://api.yelp.com/v3/businesses/search?term=pizza&"+lat+"&"+lng+"&"+radius;


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
                            bindingJuice.progressBarJuice.setVisibility(View.GONE);
                            JSONArray jsonArray = response.getJSONArray("businesses");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);

                                String creatorName = hit.getString("name");
                                String imageUrl = hit.getString("image_url");
                                int likeCount = hit.getInt("review_count");

                                mJuiceList.add(new JuiceItem(imageUrl, creatorName, likeCount));
                            }

                            mExampleAdapter = new JuiceAdapter(JuiceMainActivity.this, mJuiceList);
                            bindingJuice.recyclerViewJuice.setAdapter(mExampleAdapter);
                            mExampleAdapter.setOnItemClickListener(JuiceMainActivity.this);

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
                // Basic Authentication
                //String auth = "Basic " + Base64.encodeToString(CONSUMER_KEY_AND_SECRET.getBytes(), Base64.NO_WRAP);

                headers.put("Authorization", "Bearer 2ROaa2Rh9qu3WVTCms8FoVE4mSfHQHC7QJua95-kKT-PqzIlLSrs4tmHVdtdFw_66-JNfRiJmbCByHTvFNy5dQq-tpfS4FrPpupIzKlgELR3br-r5trpeFhrCRgwWnYx");
                return headers;
            }
        };

        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, JuiceDetailActivity.class);
        JuiceItem clickedItem = mJuiceList.get(position);

        detailIntent.putExtra(EXTRA_URL_juice, clickedItem.getImageUrl());
        detailIntent.putExtra(EXTRA_CREATOR_juice, clickedItem.getCreator());
        detailIntent.putExtra(EXTRA_LIKES_juice, clickedItem.getLikeCount());

        startActivity(detailIntent);
    }
}