package team.far.footing.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    private FgFriendPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        ButterKnife.inject(this, view);
        init();
        presenter = new FgFriendPresenter(this);
        return view;
    }

    private void init() {
        mRecyclerview.setLayoutManager(new LinearLayoutManager(APP.getContext()));
        mRecyclerview.addItemDecoration(new DividerItemDecoration(APP.getContext(), LinearLayoutManager.VERTICAL));
        swipeRefreshWidget.setOnRefreshListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void showFriends(RecyclerView.Adapter adapter) {
        mRecyclerview.setAdapter(adapter);
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        presenter.Refresh();
        swipeRefreshWidget.setRefreshing(false);
    }
}
