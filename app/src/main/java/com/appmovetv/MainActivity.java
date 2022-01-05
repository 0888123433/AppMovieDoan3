package com.appmovetv;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.appmovetv.adapter.ItemRecyclerAdapter;
import com.appmovetv.adapter.MainRecyclerAdapter;
import com.appmovetv.adapter.MovieAdapter;
import com.appmovetv.adapter.bannerMoviesPagerAdapter;
import com.appmovetv.admin.AdminAdapter;
import com.appmovetv.login.SignInActivity;
import com.appmovetv.model.AllCategory;
import com.appmovetv.model.BannerMovies;
import com.appmovetv.model.Movie;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.appmovetv.SplashActivity.homeBannerList;
import static com.appmovetv.SplashActivity.homeCatListItem1;
import static com.appmovetv.SplashActivity.homeCatListItem2;
import static com.appmovetv.SplashActivity.homeCatListItem3;
import static com.appmovetv.SplashActivity.homeCatListItem4;
import static com.appmovetv.SplashActivity.kidsBannerList;
import static com.appmovetv.SplashActivity.listAllMovie;
import static com.appmovetv.SplashActivity.movieBannerList;
import static com.appmovetv.SplashActivity.tvShowBannerList;


public class MainActivity extends AppCompatActivity   {
    TabLayout indicatorTab, categoryTab;
    Button btnmenu;
    TextView txtNameEmail,txtName;
    ViewPager bannerMoviesViewPager;

    List<AllCategory> allCategoryList;

    MainRecyclerAdapter mainRecyclerAdapter;
    bannerMoviesPagerAdapter bannerMoviesPagerAdapter;
    ItemRecyclerAdapter itemRecyclerAdapter;
    private AdminAdapter adminAdapter;

    RecyclerView mainRecycler;

    NestedScrollView nestedScrollView;
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        loadData();
        btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qlyMenu(Gravity.LEFT);
            }
        });
        //Bắt sự kiện click
        //this ís default tab selected
        startTableLayout();

        //Thêm dữ liệu
        loadCategory();

        //LoadBannerList
        setBannerMoviesPagerAdapter(homeBannerList);
        //LoadRecycler
        setMainRecycler(allCategoryList);
    }

    private void loadCategory() {
        allCategoryList = new ArrayList<>();
        allCategoryList.add(new AllCategory(1,"Watch next TV and Movies",homeCatListItem1));
        allCategoryList.add(new AllCategory(2,"Movies and Hollywood",homeCatListItem2));
        allCategoryList.add(new AllCategory(3,"Kids and Family movies",homeCatListItem3));
        allCategoryList.add(new AllCategory(4,"IonMan and Friend",homeCatListItem4));
    }

    private void loadData() {
        //banner movies
        bannerMoviesPagerAdapter = new bannerMoviesPagerAdapter(this,homeBannerList);
        bannerMoviesPagerAdapter.notifyDataSetChanged();

        bannerMoviesPagerAdapter = new bannerMoviesPagerAdapter(this,movieBannerList);
        bannerMoviesPagerAdapter.notifyDataSetChanged();

        //Item movies
        itemRecyclerAdapter = new ItemRecyclerAdapter(this,homeCatListItem1);
        itemRecyclerAdapter.notifyDataSetChanged();

        itemRecyclerAdapter = new ItemRecyclerAdapter(this,homeCatListItem2);
        itemRecyclerAdapter.notifyDataSetChanged();

        itemRecyclerAdapter = new ItemRecyclerAdapter(this,homeCatListItem3);
        itemRecyclerAdapter.notifyDataSetChanged();

        itemRecyclerAdapter = new ItemRecyclerAdapter(this,homeCatListItem4);
        itemRecyclerAdapter.notifyDataSetChanged();

        adminAdapter = new AdminAdapter(this,listAllMovie);
        adminAdapter.notifyDataSetChanged();
    }

    private void startTableLayout() {
        categoryTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 1: {
                        setScrollDefaultState();
                        setBannerMoviesPagerAdapter(tvShowBannerList);
                        return;
                    }

                    case 2:{
                        setAllmovies(listAllMovie);
                        setScrollDefaultState();
                        setBannerMoviesPagerAdapter(movieBannerList);
                        return;
                    }
                    case 3:{
                        setScrollDefaultState();
                        setBannerMoviesPagerAdapter(kidsBannerList);
                        return;
                    }
                    default:{
                        setMainRecycler(allCategoryList);
                        setScrollDefaultState();
                        setBannerMoviesPagerAdapter(homeBannerList);
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private void qlyMenu(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_menu);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        ((ViewGroup)dialog.getWindow().getDecorView())
                .getChildAt(0).startAnimation(AnimationUtils.loadAnimation(
                this,android.R.anim.slide_in_left));
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if (Gravity.LEFT == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }


        final LinearLayout menuhome = dialog.findViewById(R.id.menu_home);
        final LinearLayout menulogout = dialog.findViewById(R.id.menu_dangxuat);
        txtNameEmail = dialog.findViewById(R.id.txt_nameimail);
        txtName = dialog.findViewById(R.id.txt_name);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            return;
        }
        String name = user.getDisplayName();
        String email = user.getEmail();

        //check name
        if(name == null){
            txtName.setVisibility(View.GONE);
        }else {
            txtName.setVisibility(View.VISIBLE);
        }

        txtName.setText("name: ẨN DANH");
        txtNameEmail.setText(email);

        menuhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuhome.setBackgroundResource(R.drawable.bogocxanh);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        menuhome.setBackgroundResource(R.drawable.bogoc1);
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }, 300);
            }
        });

        menulogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menulogout.setBackgroundResource(R.drawable.bogocxanh);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        menulogout.setBackgroundResource(R.drawable.bogoc1);
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                        startActivity(intent);
                    }
                }, 300);
            }
        });

        dialog.show();

    }

    //set up bannerMovie
    private void setBannerMoviesPagerAdapter(List<BannerMovies> bannerMoviesList){
        bannerMoviesViewPager = findViewById(R.id.banner_viewPager);
        bannerMoviesPagerAdapter = new bannerMoviesPagerAdapter(this, bannerMoviesList);
        bannerMoviesViewPager.setAdapter(bannerMoviesPagerAdapter);

        //setup tab indicator with view pager
        indicatorTab.setupWithViewPager(bannerMoviesViewPager);

        //run slider view pager
        Timer slideTimer = new Timer();
        slideTimer.scheduleAtFixedRate(new AutoSlide(),5000,6000);
        indicatorTab.setupWithViewPager(bannerMoviesViewPager,true);
    }

    //setup auto silder view pager
    class AutoSlide extends TimerTask{
        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(bannerMoviesViewPager.getCurrentItem() < homeBannerList.size() -1 ){
                        bannerMoviesViewPager.setCurrentItem(bannerMoviesViewPager.getCurrentItem() + 1);

                    }else {
                        bannerMoviesViewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

    // setting up recyclerview one is horizontal and another is verticle but the work together
    // setting verticle recyclerview with category title
    public void setAllmovies(List<Movie> allMovieList) {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2,GridLayoutManager.VERTICAL,false);
        mainRecycler.setLayoutManager(layoutManager);
        adminAdapter =new AdminAdapter(this, allMovieList);
        mainRecycler.setAdapter(adminAdapter);

        adminAdapter.setOnItemClick(new AdminAdapter.onItemClick() {
            @Override
            public void onclick(int postion, View view) {
                Movie movie = listAllMovie.get(postion);
                Intent intent = new Intent(MainActivity.this, MovieDetails.class);
                intent.putExtra("MovieId",movie.getId());
                intent.putExtra("MovieName",movie.getMovieName());
                intent.putExtra("MovieImageUrl",movie.getImageUrl());
                intent.putExtra("MovieFile",movie.getFileUrl());
                intent.putExtra("MovieCategory",movie.getCategory());
                intent.putExtra("MovieDaoDien",movie.getDaoDien());
                intent.putExtra("MovieNamSanXuat",movie.getNamSanXuat());
                intent.putExtra("MovieMoTa",movie.getMoTa());
                startActivity(intent);
            }
        });
    }


    public void setMainRecycler(List<AllCategory> allCategoryList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mainRecycler.setLayoutManager(layoutManager);
        mainRecyclerAdapter =new MainRecyclerAdapter(this, allCategoryList);
        mainRecycler.setAdapter(mainRecyclerAdapter);
    }

    public void setScrollDefaultState(){
        nestedScrollView.fullScroll(View.FOCUS_UP);
        nestedScrollView.scrollTo(0,0);
        appBarLayout.setExpanded(true);
    }

    private void init() {
        btnmenu = findViewById(R.id.btn_menu);
        indicatorTab =findViewById(R.id.tab_indicator);
        categoryTab =findViewById(R.id.tablayout);
        nestedScrollView =findViewById(R.id.nested_scroll);
        appBarLayout =findViewById(R.id.appbar);
        mainRecycler = findViewById(R.id.main_recycler);

    }
}