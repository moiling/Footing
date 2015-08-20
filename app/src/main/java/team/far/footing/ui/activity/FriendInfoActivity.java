package team.far.footing.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import butterknife.ButterKnife;
import butterknife.InjectView;
import team.far.footing.R;
import team.far.footing.app.BaseActivity;
import team.far.footing.model.bean.Userbean;
import team.far.footing.presenter.FriendInfoPresenter;
import team.far.footing.ui.vu.IFriendInfoVu;
import team.far.footing.util.BmobUtils;

public class FriendInfoActivity extends BaseActivity implements IFriendInfoVu {

    @InjectView(R.id.btn_add_friend) Button btnAddFriend;
    private FriendInfoPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_info);
        ButterKnife.inject(this);
        presenter = new FriendInfoPresenter(this, (Userbean) getIntent().getSerializableExtra("user"));
        presenter.initView();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onRelieveView();
    }

    @Override
    public void initFriendView() {
        btnAddFriend.setText("删除好友");
        btnAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFriend();
            }
        });
    }

    @Override
    public void initNotFriendView() {
        btnAddFriend.setText("加为好友");
        btnAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriend();
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
        new MaterialDialog.Builder(this).title("添加好友成功").content("你们已经是好友了！").positiveText("好的").theme(Theme.LIGHT).callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public void onAddFail(int i) {
        new MaterialDialog.Builder(this).title("添加好友失败").content(BmobUtils.searchCode(i)).positiveText("好的").theme(Theme.LIGHT).callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public void onDeleteSuccess() {
        new MaterialDialog.Builder(this).title("删除好友成功").content("已经删除了~").positiveText("好的").theme(Theme.LIGHT).callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public void onDeleteFail(int i) {
        new MaterialDialog.Builder(this).title("删除好友失败").content(BmobUtils.searchCode(i)).positiveText("好的").theme(Theme.LIGHT).callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public void onError(String s) {
        new MaterialDialog.Builder(this).title("出错了！").content(s).positiveText("好的").theme(Theme.LIGHT).callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                dialog.dismiss();
            }
        }).show();
    }
}
