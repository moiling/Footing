package team.far.footing.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.ButterKnife;
import butterknife.InjectView;
import team.far.footing.R;
import team.far.footing.app.BaseActivity;
import team.far.footing.presenter.MyMapPresenter;
import team.far.footing.ui.vu.IMyMapVu;

public class MyMapActivity extends BaseActivity implements IMyMapVu, SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.my_map_recyclerview)
    RecyclerView mRecyclerview;
    @InjectView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout swipeRefreshWidget;
    @InjectView(R.id.toolbar) Toolbar mToolbar;
    @InjectView(R.id.my_map_recycler_view_empty) TextView mEmptyView;

    private MaterialDialog dialog;
    private MyMapPresenter myMapPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_map);
        ButterKnife.inject(this);
        init();
        initToolbar();
        myMapPresenter = new MyMapPresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myMapPresenter.onRelieveView();
    }

    private void init() {
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshWidget.setOnRefreshListener(this);
    }

    private void initToolbar() {
        mToolbar.setTitle("我的足迹");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onRefresh() {
        swipeRefreshWidget.setRefreshing(false);
    }

    @Override
    public void showmaplist(RecyclerView.Adapter adapter) {
        swipeRefreshWidget.setVisibility(View.VISIBLE);
        mRecyclerview.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
        mRecyclerview.setAdapter(adapter);
    }

    @Override
    public void showLoading(String s) {
        showProgress(s);
    }

    @Override
    public void stopLoading() {
        dismissProgress();
    }

    @Override
    public void showEmpty() {
        swipeRefreshWidget.setVisibility(View.GONE);
        mRecyclerview.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public Context getActivity() {
        return this;
    }
}
