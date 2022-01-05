package com.appmovetv;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.appmovetv.login.SignInActivity;
import com.appmovetv.model.BannerMovies;
import com.appmovetv.model.Movie;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    VideoView myVideo;
    private MediaController media_control;

    public static List<BannerMovies> homeBannerList;
    public static List<BannerMovies> tvShowBannerList;
    public static List<BannerMovies> movieBannerList;
    public static List<BannerMovies> kidsBannerList;

    public static List<Movie> homeCatListItem1;
    public static List<Movie> homeCatListItem2;
    public static List<Movie> homeCatListItem3;
    public static List<Movie> homeCatListItem4;

    public static List<Movie> listAllMovie;

    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        listAllMovie = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance(); // Cho phép sử dụng database
        loadDataBannerMovie();
        loadDataItemMovie();

        getAllMovies();

        //Run video demo
        myVideo = (VideoView) findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.logo_video);
        media_control = new MediaController(this);
        myVideo.setMediaController(media_control);
        myVideo.setVideoURI(uri);
        myVideo.start();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
            }
        },2300);
    }

    public void getAllMovies(){
        databaseReference = firebaseDatabase.getReference().child("itemmovie");
        listAllMovie = new ArrayList<>();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @androidx.annotation.Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    //index list 1234
                    dataSnapshot.getKey();
                    Movie movie = dataSnapshot.getValue(Movie.class);
                    listAllMovie.add(movie);
                }
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @androidx.annotation.Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                listAllMovie.clear();
                listAllMovie = new ArrayList<>();
                databaseReference.removeEventListener(this); //xóa sự kiện cũ
                //bắt đầu lắng nghe sự kiện mới
                databaseReference.addChildEventListener(this);
            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
                listAllMovie.clear();
                databaseReference.removeEventListener(this); //xóa sự kiện cũ
                //bắt đầu lắng nghe sự kiện mới
                databaseReference.addChildEventListener(this);
            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @androidx.annotation.Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void loadDataItemMovie() {
        //first we will add catitem data
        databaseReference = firebaseDatabase.getReference().child("itemmovie").child("item1"); // Lấy đường dẫn package realtime db
        homeCatListItem1 = new ArrayList<>();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Movie movie = snapshot.getValue(Movie.class);
                    homeCatListItem1.add(movie);
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        databaseReference = firebaseDatabase.getReference().child("itemmovie").child("item2");
        homeCatListItem2 = new ArrayList<>();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Movie movie = snapshot.getValue(Movie.class);
                homeCatListItem2.add(movie);
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        databaseReference = firebaseDatabase.getReference().child("itemmovie").child("item3");
        homeCatListItem3 = new ArrayList<>();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @androidx.annotation.Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Movie movie = snapshot.getValue(Movie.class);
                homeCatListItem3.add(movie);
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @androidx.annotation.Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @androidx.annotation.Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        databaseReference = firebaseDatabase.getReference().child("itemmovie").child("item4");
        homeCatListItem4 = new ArrayList<>();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @androidx.annotation.Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Movie movie = snapshot.getValue(Movie.class);
                homeCatListItem4.add(movie);
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @androidx.annotation.Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @androidx.annotation.Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    private void loadDataBannerMovie() {
        //Thêm dữ liệu cho homeBannerList
        databaseReference = firebaseDatabase.getReference().child("bannermovie"); // Lấy đường dẫn package realtime db
        homeBannerList = new ArrayList<>();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded( @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                BannerMovies bannerMovies = snapshot.getValue(BannerMovies.class);
                homeBannerList.add(bannerMovies);
            }
            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        movieBannerList = new ArrayList<>();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @androidx.annotation.Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                BannerMovies bannerMovies = snapshot.getValue(BannerMovies.class);
                movieBannerList.add(bannerMovies);
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @androidx.annotation.Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @androidx.annotation.Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


    private void nextActivity() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            //Chưa login
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            //đã login
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}