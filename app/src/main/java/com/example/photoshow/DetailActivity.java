package com.example.photoshow;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail);

        imageView = (ImageView)findViewById(R.id.imageView);

        String uri = getIntent().getStringExtra("uri");

        Glide.with(this).load(Uri.parse(uri)).into(imageView);


    }
}
