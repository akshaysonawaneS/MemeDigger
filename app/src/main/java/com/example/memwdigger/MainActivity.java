package com.example.memwdigger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.lang.UCharacter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private String url = "https://meme-api.herokuapp.com/gimme";
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String subReddit,title;
    private String postLink,memeUrl;
    private ImageView imageView;
    private Button next1,orignal1,about1;
    private Bitmap bmp;
    private TextView title1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        next1 = (Button)findViewById(R.id.next);
        imageView = (ImageView) findViewById(R.id.meme);
        orignal1 = (Button)findViewById(R.id.orignal);
        title1 = (TextView)findViewById(R.id.tit);
        about1 = (Button)findViewById(R.id.about);
        retriveMeme();

        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retriveMeme();
            }
        });

        orignal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(postLink)));
            }
        });

        about1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,setting.class);
                startActivity(intent);
            }
        });

    }

    public void retriveMeme(){

        Log.e("inside","retrive");
        Toast.makeText(MainActivity.this,"Have Patience",Toast.LENGTH_SHORT).show();
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Meme", "Getting Meme");
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.e("response","is"+ response);
                    postLink = jsonObject.getString("postLink");
                    subReddit = jsonObject.getString("subreddit");
                    title = jsonObject.getString("title");
                    memeUrl = jsonObject.getString("url");
                    Log.e("Meme","Collected Meme");
                    Log.e("Title","titlr: "+memeUrl.toString());

                    Picasso.with(MainActivity.this).load(memeUrl).into(imageView);
                    title1.setText(title);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error","Error: "+error.toString());
                error.printStackTrace();
            }
        });

        mRequestQueue.add(mStringRequest);


    }

}
