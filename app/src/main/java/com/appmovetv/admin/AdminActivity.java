package com.appmovetv.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.appmovetv.R;
import com.appmovetv.SplashActivity;
import com.appmovetv.adapter.ItemRecyclerAdapter;
import com.appmovetv.adapter.MovieAdapter;
import com.appmovetv.model.Movie;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.appmovetv.SplashActivity.homeCatListItem1;
import static com.appmovetv.SplashActivity.listAllMovie;
import static com.appmovetv.SplashActivity.firebaseDatabase;

public class AdminActivity extends AppCompatActivity  {

    private Button btnaddmovie;

    RecyclerView recyclerView;
    private AdminAdapter adminAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        anhxa();

        loadMovieAdmin();

        btnaddmovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this,AddMovie.class);
                startActivity(intent);
            }
        });
    }

    private void loadMovieAdmin() {
        //set movie vào bố cục
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adminAdapter = new AdminAdapter(this, listAllMovie);
        recyclerView.setAdapter(adminAdapter);
        SplashActivity a= new SplashActivity();
        a.getAllMovies();
    }

    private void anhxa() {
        recyclerView = findViewById(R.id.admin_main_recycler);
        btnaddmovie = findViewById(R.id.btn_addmovie);
    }

    // nnanfsd


}