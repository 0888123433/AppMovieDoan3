package com.appmovetv.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appmovetv.MovieDetails;
import com.appmovetv.R;
import com.appmovetv.model.Movie;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ItemViewHolder> {

    Context context;
    List<Movie> movieItemList;

    public ItemRecyclerAdapter(Context context, List<Movie> movieItemList) {
        this.context = context;
        this.movieItemList = movieItemList;
    }

    @NonNull
    @NotNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.cat_recycler_row_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ItemRecyclerAdapter.ItemViewHolder holder, int position) {

        //i will fetch image from server so we use glide library
        //tìm nạp hình ảnh từ máy chủ - sử dụng thư viện glide
        Glide.with(context).load(movieItemList.get(position).getImageUrl()).into(holder.itemImage);
        holder.itemName.setText(movieItemList.get(position).getMovieName());
        //Onclick cho item
        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MovieDetails.class);
                intent.putExtra("MovieId",movieItemList.get(position).getId());
                intent.putExtra("MovieName",movieItemList.get(position).getMovieName());
                intent.putExtra("MovieImageUrl",movieItemList.get(position).getImageUrl());
                intent.putExtra("MovieFile",movieItemList.get(position).getFileUrl());
                intent.putExtra("MovieCategory",movieItemList.get(position).getCategory());
                intent.putExtra("MovieDaoDien",movieItemList.get(position).getDaoDien());
                intent.putExtra("MovieNamSanXuat",movieItemList.get(position).getNamSanXuat());
                intent.putExtra("MovieMoTa",movieItemList.get(position).getMoTa());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieItemList.size();
    }

    public static final class ItemViewHolder extends RecyclerView.ViewHolder{

        ImageView itemImage;
        TextView itemName;

        public ItemViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            itemName = itemView.findViewById(R.id.item_name);
        }
    }

}
