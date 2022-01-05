package com.appmovetv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appmovetv.R;
import com.appmovetv.model.Movie;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.AllItemViewHolder>{

    Context context;
    List<Movie> movieList = null;

    public MovieAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @NotNull
    @Override
    public MovieAdapter.AllItemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new MovieAdapter.AllItemViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.main_recycler_all_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MovieAdapter.AllItemViewHolder holder, int position) {

        ItemRecyclerAdapter itemRecyclerAdapter = new ItemRecyclerAdapter(context, movieList);
        holder.recyclerViewAllMovie.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, true));
        holder.recyclerViewAllMovie.setAdapter(itemRecyclerAdapter);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class AllItemViewHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerViewAllMovie;
        public AllItemViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            recyclerViewAllMovie = itemView.findViewById(R.id.all_item_recycler);
        }
    }


}
