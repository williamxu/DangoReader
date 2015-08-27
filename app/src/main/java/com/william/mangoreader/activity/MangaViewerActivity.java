package com.william.mangoreader.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.william.mangoreader.R;
import com.william.mangoreader.activity.viewpager.MangaViewPager;
import com.william.mangoreader.adapter.MangaImagePagerAdapter;
import com.william.mangoreader.listener.MVPGestureListener;
import com.william.mangoreader.model.MangaEdenImageItem;
import com.william.mangoreader.parse.MangaEden;
import com.william.mangoreader.volley.VolleySingleton;

import org.json.JSONObject;

import java.util.ArrayList;

public class MangaViewerActivity extends AppCompatActivity {

    private static float STATUS_BAR_HEIGHT;

    private ArrayList<MangaEdenImageItem> images;
    private MangaViewPager mangaViewPager;
    private MangaImagePagerAdapter imageAdapter;

    private String chapterId;

    private RequestQueue queue;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_viewer);

        getWindow().setStatusBarColor(getResources().getColor(R.color.translucent_black));
        STATUS_BAR_HEIGHT = (float) Math.ceil(25 * getResources().getDisplayMetrics().density);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_manga_viewer);
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mToolbar.setVisibility(View.GONE);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");

        images = new ArrayList<>();
        chapterId = (String) getIntent().getExtras().get("chapterId");
        queue = VolleySingleton.getInstance(this).getRequestQueue();
        mangaViewPager = (MangaViewPager) findViewById(R.id.manga_view_pager);
        mangaViewPager.setMVPGestureListener(new MVPGestureListener(this, mangaViewPager) {
            @Override
            public void hideSystemUI() {
                mToolbar.animate()
                        .y(STATUS_BAR_HEIGHT)
                        .translationY(-1 * mToolbar.getHeight())
                        .setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                mToolbar.setVisibility(View.GONE);
                            }
                        });
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                                | View.SYSTEM_UI_FLAG_IMMERSIVE);
            }

            @Override
            public void showSystemUI() {
                mToolbar.setVisibility(View.VISIBLE);
                mToolbar.animate()
                        .translationY(mToolbar.getHeight())
                        .y(STATUS_BAR_HEIGHT)
                        .setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                        .setListener(null);

                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
        });

        fetchMangaImagesFromMangaEden();
    }

    private void fetchMangaImagesFromMangaEden() {
        String url = MangaEden.MANGAEDEN_CHAPTERDETAIL_PREFIX + chapterId;

        queue.add(new JsonObjectRequest
                        (url, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                images = MangaEden.parseMangaEdenMangaImageResponse(response.toString());
                                imageAdapter = new MangaImagePagerAdapter(getSupportFragmentManager(), images.size());
                                mangaViewPager.setAdapter(imageAdapter);
                                loadContent();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println("Response error: " + error.toString());
                            }
                        })
        );
    }

    private void loadContent() {
        mangaViewPager.setCurrentItem(images.size() - 1);
        // TODO: update other stuff
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_manga_viewer, menu);
        return true;
    }

    public ArrayList<MangaEdenImageItem> getImages() {
        return images;
    }

}
