package team.far.footing.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import team.far.footing.R;
import team.far.footing.app.APP;
import team.far.footing.presenter.FgFriendPresenter;
import team.far.footing.ui.vu.IFgFriendVu;
import team.far.footing.ui.widget.DividerItemDecoration;

public class FriendsFragment extends Fragment implements IFgFriendVu, SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.firends_recyclerview)
    RecyclerView mRecyclerview;
    @InjectView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout swipeRefreshWidget;
    @InjectView(R.id.firends_recycler_view_empty) TextView firendsRecyclerViewEmpty;

    private FgFriendPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        ButterKnife.inject(this, view);
        init();
        presenter = new FgFriendPresenter(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.refresh();
    }

    private void init() {
        mRecyclerview.setLayoutManager(new LinearLayoutManager(APP.getContext()));
        mRecyclerview.addItemDecoration(new DividerItemDecoration(APP.getContext(), LinearLayoutManager.VERTICAL));
        swipeRefreshWidget.setOnRefreshListener(this);
        swipeRefreshWidget.setColorSchemeResources(android.R.color.holo_purple, android.R.color.holo_blue_bright, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onRelieveView();
        ButterKnife.reset(this);
    }

    @Override
    public void showFriends(RecyclerView.Adapter adapter) {
        swipeRefreshWidget.setVisibility(View.VISIBLE);
        mRecyclerview.setVisibility(View.VISIBLE);
        firendsRecyclerViewEmpty.setVisibility(View.GONE);
        mRecyclerview.setAdapter(adapter);
    }

    @Override
    public void stopRefresh() {
        swipeRefreshWidget.setRefreshing(false);
    }

    @Override
    public void showEmpty() {
        // 必须去掉、否则下拉刷新卡顿
        swipeRefreshWidget.setVisibility(View.GONE);
        mRecyclerview.setVisibility(View.GONE);
        firendsRecyclerViewEmpty.setVisibility(View.VISIBLE);
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        presenter.refresh();
    }
}
