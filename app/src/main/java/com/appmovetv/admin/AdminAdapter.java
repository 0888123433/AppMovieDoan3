package com.appmovetv.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appmovetv.R;
import com.appmovetv.model.Movie;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder>  {
    private Context context;
    private List<Movie> movieList;

    private onItemClick onItemClick;

    public AdminAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    public interface onItemClick{
        void onclick(int postion, View view);
    }

    public void setOnItemClick(onItemClick onClick){
        this.onItemClick = onClick;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.cat_recycler_row_items,parent,false);
        return new ViewHolder(view, onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdminAdapter.ViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        if(movie == null){
            return;
        }
        holder.textView.setText(movie.getMovieName());
        Glide.with(context).load(movie.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (movieList == null){
            return 0;
        }
        else return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView textView;

        public ViewHolder(@NonNull @NotNull View itemView, onItemClick onItemClick1) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            textView = itemView.findViewById(R.id.item_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClick1 != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            onItemClick1.onclick(pos,view);
                        }
                    }
                }
            });
        }
    }

}
