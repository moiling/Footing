package team.far.footing.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import team.far.footing.R;
import team.far.footing.app.BaseActivity;
import team.far.footing.presenter.MyMapPresenter;
import team.far.footing.ui.vu.IMyMapVu;

public class MyMapActivity extends BaseActivity implements IMyMapVu, SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.firends_recyclerview)
    RecyclerView firendsRecyclerview;
    @InjectView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout swipeRefreshWidget;

    private MyMapPresenter myMapPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_map);
        ButterKnife.inject(this);
        init();
        myMapPresenter = new MyMapPresenter(this);
    }

    private void init() {
        firendsRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshWidget.setOnRefreshListener(this);
    }


    @Override
    public void onRefresh() {
        swipeRefreshWidget.setRefreshing(false);
    }

    @Override
    public void showmaplist(RecyclerView.Adapter adapter) {
        firendsRecyclerview.setAdapter(adapter);
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
    public Context getActivity() {
        return this;
    }
}
