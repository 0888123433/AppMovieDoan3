package com.appmovetv.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.appmovetv.MovieDetails;
import com.appmovetv.R;
import com.appmovetv.model.BannerMovies;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class bannerMoviesPagerAdapter extends PagerAdapter {

    Context context;
    List<BannerMovies> bannerMoviesList;

    public bannerMoviesPagerAdapter(Context context, List<BannerMovies> bannerMoviesList) {
        this.context = context;
        this.bannerMoviesList = bannerMoviesList;
    }

    @Override
    public int getCount() {
        return bannerMoviesList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull @org.jetbrains.annotations.NotNull View view, @NonNull @org.jetbrains.annotations.NotNull Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object object) {
        container.removeView((View) object);
    }


    //Tạo bố cục điều hợp cho file
    // we need to create a pager adapter layout file
    @NonNull
    @NotNull
    @Override
    public Object instantiateItem(@NonNull @NotNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate((R.layout.banner_movie_layout),null);
        ImageView bannerImage = view.findViewById(R.id.banner_image);

        //Sử dụng library để nạp hình từ URL và đặt nó ở chế độ xem hình ảnh
        //lets add  Glide  dependency
        Glide.with(context).load(bannerMoviesList.get(position).getImageUrl()).into(bannerImage);
        container.addView(view);

        //Sự kiện onclick cho banner
        bannerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MovieDetails.class);
                intent.putExtra("MovieId",bannerMoviesList.get(position).getId());
                intent.putExtra("MovieName",bannerMoviesList.get(position).getMovieName());
                intent.putExtra("MovieImageUrl",bannerMoviesList.get(position).getImageUrl());
                intent.putExtra("MovieFile",bannerMoviesList.get(position).getFileUrl());
                intent.putExtra("MovieCategory",bannerMoviesList.get(position).getCategory());
                intent.putExtra("MovieDaoDien",bannerMoviesList.get(position).getDaoDien());
                intent.putExtra("MovieNamSanXuat",bannerMoviesList.get(position).getNamSanXuat());
                intent.putExtra("MovieMoTa",bannerMoviesList.get(position).getMoTa());
                context.startActivity(intent);
            }
        });

        return view;
    }
}
