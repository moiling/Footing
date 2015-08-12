package team.far.footing.presenter;

import android.content.Context;
import android.content.Intent;

import team.far.footing.model.bean.Userbean;
import team.far.footing.ui.activity.EditUserInfoActivity;
import team.far.footing.ui.vu.IUserInfoVu;
import team.far.footing.util.BmobUtils;

/**
 * Created by moi on 2015/8/12.
 */
public class UserInfoPresenter {

    private IUserInfoVu v;
    private Userbean userbean;

    public UserInfoPresenter(IUserInfoVu v) {
        this.v = v;
        userbean = BmobUtils.getCurrentUser();
        showUserInformation();
    }

    public void refreshUserInformation() {
        userbean = BmobUtils.getCurrentUser();
        showUserInformation();
    }

    public void showUserInformation() {
        v.showUserInformation(userbean);
    }

    public void startEditUserInfoActivity(Context context) {
        Intent intent = new Intent(context, EditUserInfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }

    // 解除view的绑定
    public void onRelieveView() {
        v = null;
    }

}
