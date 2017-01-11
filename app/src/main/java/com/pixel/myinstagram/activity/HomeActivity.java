package com.pixel.myinstagram.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.pixel.myinstagram.R;
import com.pixel.myinstagram.Utils;
import com.pixel.myinstagram.adapter.FeedAdapter;
import com.pixel.myinstagram.view.FeedContextMenu;
import com.pixel.myinstagram.view.FeedContextMenuManager;

import butterknife.BindView;

/**
 * Created by ZZR on 2016/12/26.
 */

public class HomeActivity extends BaseDrawerActivity implements FeedAdapter.OnFeedItemClickListener {

    private static final int ANIM_DURATION_TOOLBAR = 300;
    private static final int ANIM_DURATION_FAB = 400;

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                //使用getExtraLayoutSpace为LayoutManager设置更多的预留空间 让加载动画不会卡顿
                return 300;
            }
        };
        rvFeed.setLayoutManager(linearLayoutManager);
        feedAdapter = new FeedAdapter(this);
        feedAdapter.setOnFeedItemClickListener(this);
        rvFeed.setAdapter(feedAdapter);
        rvFeed.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                FeedContextMenuManager.getInstance().hideContextMenu();
            }
        });
        //rvFeed.setItemAnimator(new FeedItemAnimator());
        rvFeed.setItemAnimator(new DefaultItemAnimator());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (pendingIntroAnimation) {
            pendingIntroAnimation = false;
            startIntroAnimation();
        }
        return true;
    }

    private void startIntroAnimation(){
        fabCreate.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.btn_fab_size));
        int actionbarSize = Utils.dpToPx(56);
        getToolbar().setTranslationY(-actionbarSize);
        getIvLogo().setTranslationY(-actionbarSize);
        getInboxMenuItem().getActionView().setTranslationY(-actionbarSize);

        getToolbar().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);
        getIvLogo().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(400);
        getInboxMenuItem().getActionView().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        startContentAnimation();
                    }
                });
                //.start();
    }

    private void startContentAnimation() {
        fabCreate.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setStartDelay(300)
                .setDuration(ANIM_DURATION_FAB)
                .start();
        feedAdapter.updateItems(true);
    }


    // OnFeedItemClickListener start
    @Override
    public void onCommentsClick(View v, int position) {

    }

    @Override
    public void onMoreClick(View v, int position) {
        FeedContextMenuManager.getInstance().toggleContextMenuFromView(v, position,
                new FeedContextMenu.OnFeedContextMenuItemClickListener() {
            @Override
            public void onReportClick(int feedItem) {
                FeedContextMenuManager.getInstance().hideContextMenu();
            }

            @Override
            public void onSharePhotoClick(int feedItem) {
                FeedContextMenuManager.getInstance().hideContextMenu();
            }

            @Override
            public void onCopyShareUrlClick(int feedItem) {
                FeedContextMenuManager.getInstance().hideContextMenu();
            }

            @Override
            public void onCancelClick(int feedItem) {
                FeedContextMenuManager.getInstance().hideContextMenu();
            }
        });
    }

    @Override
    public void onProfileClick(View v) {
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        startingLocation[0] += v.getWidth() / 2;
        UserProfileActivity.startUserProfileFromLocation(startingLocation, this);
        overridePendingTransition(0, 0);
    }
    // OnFeedItemClickListener end

    public void showLikedSnackbar() {
        Snackbar.make(clContent, "Liked!", Snackbar.LENGTH_SHORT).show();
    }
}
