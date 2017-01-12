package com.pixel.myinstagram.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.pixel.myinstagram.R;
import com.pixel.myinstagram.adapter.CommentsAdapter;
import com.pixel.myinstagram.view.SendCommentButton;

import butterknife.BindView;

import static com.pixel.myinstagram.R.id.rvComments;

/**
 * Created by ZZR on 2017/1/12.
 */

public class CommentsActivity extends BaseDrawerActivity {
    public static final String ARG_DRAWING_START_LOCATION = "arg_drawing_start_location";
    @BindView(R.id.contentRoot)
    LinearLayout contentRoot;
    @BindView(R.id.rvComments)
    RecyclerView rvComments;
    @BindView(R.id.llAddComment)
    LinearLayout llAddComment;
    @BindView(R.id.etComment)
    EditText etComment;
    @BindView(R.id.btnSendComment)
    SendCommentButton btnSendComment;

    private CommentsAdapter commentsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        setupComments();
    }

    private void setupComments() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvComments.setLayoutManager(linearLayoutManager);
        rvComments.setHasFixedSize(true);

        commentsAdapter = new CommentsAdapter(this);
        rvComments.setAdapter(commentsAdapter);
        rvComments.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }
}
