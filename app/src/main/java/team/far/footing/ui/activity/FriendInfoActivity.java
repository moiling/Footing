package team.far.footing.ui.activity;

import android.os.Bundle;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import team.far.footing.R;
import team.far.footing.app.BaseActivity;
import team.far.footing.model.bean.Userbean;
import team.far.footing.presenter.FriendInfoPresenter;
import team.far.footing.ui.vu.IFriendInfoVu;
import team.far.footing.util.BmobUtils;

public class FriendInfoActivity extends BaseActivity implements IFriendInfoVu {

    private FriendInfoPresenter presenter;
    private Userbean userbean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_info);
        presenter = new FriendInfoPresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onRelieveView();
    }

    @Override
    public void addFriend() {
        presenter.addFriend(userbean);
    }

    @Override
    public void deleteFriend() {
        presenter.deleteFriend(userbean);
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

    }

    @Override
    public void onDeleteFail(int i) {

    }
}
