package com.pixel.myinstagram.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;

import com.pixel.myinstagram.R;
import com.pixel.myinstagram.adapter.FeedAdapter;

import butterknife.BindView;

/**
 * Created by ZZR on 2016/12/26.
 */

public class HomeActivity extends BaseDrawerActivity {

    @BindView(R.id.rvFeed)
    RecyclerView rvFeed;
    @BindView(R.id.btnCreate)
    FloatingActionButton fabCreate;
    @BindView(R.id.content)
    CoordinatorLayout clContent;

    private boolean pendingIntroAnimation;
    private FeedAdapter feedAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupFeed();

        if (savedInstanceState == null) {
            pendingIntroAnimation = true;
        } else {
            feedAdapter.updateItems(false);
        }
    }

    private void setupFeed() {

    }
}
