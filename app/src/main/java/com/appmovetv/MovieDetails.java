package com.appmovetv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MovieDetails extends AppCompatActivity {

    ImageView movieImage;
    TextView movieName, movieCategory, movieDaoDien, movieNamSanXuat, movieMota;
    Button playButton;

    String mName, mImage, mId, mFileUrl, mCategory, mDaoDien, mNamSanXuat, mMota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        anhxa();

        //get data from intent - Đổ thông tin movie lấy từ adapter - ItemRecyclerAdapter
        mId = getIntent().getStringExtra("MovieId");
        mName = getIntent().getStringExtra("MovieName");
        mImage = getIntent().getStringExtra("MovieImageUrl");
        mFileUrl = getIntent().getStringExtra("MovieFile");
        mCategory = getIntent().getStringExtra("MovieCategory");
        mDaoDien = getIntent().getStringExtra("MovieDaoDien");
        mNamSanXuat = getIntent().getStringExtra("MovieNamSanXuat");
        mMota = getIntent().getStringExtra("MovieMoTa");

        //set data to layout - Set ảnh & tên movie
        Glide.with(this).load(mImage).into(movieImage);
        movieName.setText(mName);
        movieCategory.setText(  "Thể loại:  "+mCategory);
        movieDaoDien.setText(   "Đạo diễn:  "+mDaoDien);
        movieNamSanXuat.setText("Năm sản xuất phim: "+mNamSanXuat);
        movieMota.setText("- "+mMota);


        // Sự kiện play - Run movie
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieDetails.this, VideoPlayerActivity.class);
                intent.putExtra("url", mFileUrl);
                startActivity(intent);
            }
        });

    }

    private void anhxa() {
        movieImage = findViewById(R.id.movie_image);
        movieName = findViewById(R.id.movie_name);
        playButton = findViewById(R.id.play_button);
        movieCategory = findViewById(R.id.txt_theloai);
        movieDaoDien = findViewById(R.id.txt_daodien);
        movieNamSanXuat = findViewById(R.id.txt_namsanxuat);
        movieMota = findViewById(R.id.txt_mota);

    }
}