package team.far.footing.ui.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import java.text.DecimalFormat;

import butterknife.ButterKnife;
import butterknife.InjectView;
import team.far.footing.R;
import team.far.footing.app.BaseActivity;
import team.far.footing.model.bean.Userbean;
import team.far.footing.presenter.FriendInfoPresenter;
import team.far.footing.ui.vu.IFriendInfoVu;
import team.far.footing.ui.widget.CircleImageView;
import team.far.footing.util.BmobUtils;
import team.far.footing.util.LevelUtils;
import team.far.footing.util.ScreenUtils;
import team.far.footing.util.TimeUtils;

public class FriendInfoActivity extends BaseActivity implements IFriendInfoVu, View.OnClickListener {

    @InjectView(R.id.btn_add_friend)
    TextView btnAddFriend;
    @InjectView(R.id.toolbar_t)
    Toolbar mToolbar;
    @InjectView(R.id.iv_friend_info_user_pic)
    CircleImageView ivFriendInfoUserPic;
    @InjectView(R.id.tv_friend_info_user_lv)
    TextView tvFriendInfoUserLv;
    @InjectView(R.id.tv_friend_info_user_name)
    TextView tvFriendInfoUserName;
    @InjectView(R.id.tv_friend_info_friend_signature)
    TextView tvFriendInfoFriendSignature;
    @InjectView(R.id.btn_friend_signature)
    RelativeLayout btnFriendSignature;
    @InjectView(R.id.tv_friend_today_distance)
    TextView tvFriendTodayDistance;
    @InjectView(R.id.tv_friend_all_distance)
    TextView tvFriendAllDistance;
    @InjectView(R.id.tv_friend_email)
    TextView tvFriendEmail;
    @InjectView(R.id.friend_info_bar)
    FrameLayout barLayout;
    private FriendInfoPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_info);
        ButterKnife.inject(this);
        presenter = new FriendInfoPresenter(this, (Userbean) getIntent().getSerializableExtra("user"));
        presenter.initView();
        initToolbar();
        init();
    }

    private void init() {
        btnFriendSignature.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onRelieveView();
    }

    private void initToolbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            barLayout.setPadding(0, ScreenUtils.getStatusHeight(this), 0, 0);
        mToolbar.setTitle("");
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
    public void initFriendView() {
        changeBtn2Delete();
    }

    @Override
    public void initNotFriendView() {
        changeBtn2Add();
    }

    private void changeBtn2Add() {
        btnAddFriend.setText("加为好友");
        btnAddFriend.setTextColor(getResources().getColor(R.color.white));
        btnAddFriend.setBackgroundColor(getResources().getColor(R.color.accent_color));
        btnAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriend();
            }
        });
    }

    private void changeBtn2Delete() {
        btnAddFriend.setText("删除好友");
        btnAddFriend.setTextColor(getResources().getColor(R.color.white));
        btnAddFriend.setBackgroundColor(getResources().getColor(R.color.primary_color));
        btnAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFriend();
            }
        });
    }

    @Override
    public void addFriend() {
        presenter.addFriend();
    }

    @Override
    public void deleteFriend() {
        presenter.deleteFriend();
    }

    @Override
    public void talk() {

    }

    @Override
    public void showProgress() {
        super.showProgress("正在操作");
    }

    @Override
    public void dismissProgress() {
        super.dismissProgress();
    }

    @Override
    public void onAddSuccess() {
        new MaterialDialog.Builder(this).title("添加好友成功").backgroundColor(getResources().getColor(R.color.white)).content("你们已经是好友了！").positiveText("好的").theme(Theme.LIGHT).callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                dialog.dismiss();
            }
        }).show();
        changeBtn2Delete();
    }

    @Override
    public void onAddFail(int i) {
        new MaterialDialog.Builder(this).title("添加好友失败").backgroundColor(getResources().getColor(R.color.white)).content(BmobUtils.searchCode(i)).positiveText("好的").theme(Theme.LIGHT).callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public void onDeleteSuccess() {
        new MaterialDialog.Builder(this).title("删除好友成功").backgroundColor(getResources().getColor(R.color.white)).content("已经删除了~").positiveText("好的").theme(Theme.LIGHT).callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                dialog.dismiss();
            }
        }).show();
        changeBtn2Add();
    }

    @Override
    public void onDeleteFail(int i) {
        new MaterialDialog.Builder(this).title("删除好友失败").backgroundColor(getResources().getColor(R.color.white)).content(BmobUtils.searchCode(i)).positiveText("好的").theme(Theme.LIGHT).callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public void onError(String s) {
        new MaterialDialog.Builder(this).title("出错了！").backgroundColor(getResources().getColor(R.color.white)).content(s).positiveText("好的").theme(Theme.LIGHT).callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public void showUserInformation(Userbean userbean, Bitmap bitmap) {
        if (!(userbean.getNickName() == null)) {
            tvFriendInfoUserName.setText(userbean.getNickName());
        } else {
            tvFriendInfoUserName.setText("未取名");
        }
        if (bitmap != null) {
            ivFriendInfoUserPic.setImageBitmap(bitmap);
        }
        tvFriendInfoUserLv.setText("Lv." + LevelUtils.getLevel(userbean.getLevel()));
        tvFriendInfoFriendSignature.setText(userbean.getSignature());
        if (TimeUtils.isToday(userbean.getToday_date())) {
            if (userbean.getToday_distance() != null) {
                if (userbean.getToday_distance() > 1000) {
                    tvFriendTodayDistance.setText(new DecimalFormat(".##").format(userbean.getToday_distance() / 1000.0) + " km");
                } else {
                    tvFriendTodayDistance.setText(userbean.getToday_distance() + " m");
                }
            } else {
                tvFriendTodayDistance.setText("0 m");
            }
        } else {
            tvFriendTodayDistance.setText("0 m");
        }
        if (userbean.getAll_distance() > 1000) {
            tvFriendAllDistance.setText(new DecimalFormat(".##").format(userbean.getAll_distance() / 1000.0) + " km");
        } else {
            tvFriendAllDistance.setText(userbean.getAll_distance() + " m");
        }
        tvFriendEmail.setText(userbean.getEmail());
    }

    @Override
    public void showUserPic(Bitmap bitmap) {
        if (bitmap != null) {
            ivFriendInfoUserPic.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_friend_signature:
                if (tvFriendInfoFriendSignature.getText() != null) {
                    new MaterialDialog.Builder(this).backgroundColor(getResources().getColor(R.color.white)).content(tvFriendInfoFriendSignature.getText()).theme(Theme.LIGHT).show();
                }
                break;
        }
    }
}
