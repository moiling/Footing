package team.far.footing.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import team.far.footing.R;
import team.far.footing.app.APP;
import team.far.footing.presenter.AddFriendPresenter;
import team.far.footing.ui.vu.IAddFriendVu;
import team.far.footing.ui.widget.DividerItemDecoration;

public class AddFriendActivity extends AppCompatActivity implements IAddFriendVu, SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.edit_addfriend)
    EditText editAddfriend;
    @InjectView(R.id.firends_recyclerview)
    RecyclerView mRecyclerview;
    @InjectView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout swipeRefreshWidget;
    @InjectView(R.id.bt_query)
    Button btQuery;

    private AddFriendPresenter addFriendPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ButterKnife.inject(this);
        init();
        addFriendPresenter = new AddFriendPresenter(this);
    }

    private void init() {
        mRecyclerview.setLayoutManager(new LinearLayoutManager(APP.getContext()));
        mRecyclerview.addItemDecoration(new DividerItemDecoration(APP.getContext(), LinearLayoutManager.VERTICAL));
        swipeRefreshWidget.setOnRefreshListener(this);
        swipeRefreshWidget.setColorSchemeResources(android.R.color.holo_purple, android.R.color.holo_blue_bright, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        btQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriendPresenter.query(editAddfriend.getText().toString());
            }
        });
    }


    @Override
    public void showfriends(RecyclerView.Adapter adapter) {
        mRecyclerview.setAdapter(adapter);
    }

    @Override
    public void stopRefresh() {
        swipeRefreshWidget.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        addFriendPresenter.Refresh();
    }
}
