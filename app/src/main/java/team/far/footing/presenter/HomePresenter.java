package team.far.footing.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import team.far.footing.app.APP;
import team.far.footing.model.IFileModel;
import team.far.footing.model.IUserModel;
import team.far.footing.model.bean.Userbean;
import team.far.footing.model.callback.OngetUserPicListener;
import team.far.footing.model.impl.FileModel;
import team.far.footing.model.impl.UserModel;
import team.far.footing.ui.activity.AboutActivity;
import team.far.footing.ui.activity.AddFriendActivity;
import team.far.footing.ui.activity.MyMapActivity;
import team.far.footing.ui.activity.SettingActivity;
import team.far.footing.ui.activity.SuggestionActivity;
import team.far.footing.ui.activity.UserInfoActivity;
import team.far.footing.ui.activity.WalkActivity;
import team.far.footing.ui.vu.IHomeVu;
import team.far.footing.util.BmobUtils;
import team.far.footing.util.LogUtils;
import team.far.footing.util.SPUtils;

/**
 * Created by moi on 2015/8/9.
 */
public class HomePresenter {
    private IHomeVu v;
    private Userbean userbean;
    private IFileModel fileModel;
    private IUserModel userModel;

    public HomePresenter(IHomeVu v) {
        this.v = v;
        // 当homePresenter被实例化，用sp保存登录状态
        SPUtils.put(APP.getContext(), "isLogin", Boolean.TRUE);
        fileModel = FileModel.getInstance();
        userModel = UserModel.getInstance();
        userbean = BmobUtils.getCurrentUser();
        LogUtils.d(userbean.getUsername());
        showUserInformation();
        if (userbean.getHeadPortraitFileName() != null) {
            setUserPic(userbean.getHeadPortraitFileName());
        }
    }

    public void showUserInformation() {
        v.showUserInformation(userbean);
    }

    public void refreshUserInformation() {
        userbean = BmobUtils.getCurrentUser();
        showUserInformation();
        if (userbean.getHeadPortraitFileName() != null) {
            setUserPic(userbean.getHeadPortraitFileName());
        }
    }

    public void startWalkActivity(Context context) {
        Intent intent = new Intent(context, WalkActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }

    public void startUserInfoActivity(Context context) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }

    public void startSettingActivity(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }

    public void startMyMapActivity(Context context) {
        Intent intent = new Intent(context, MyMapActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_FROM_BACKGROUND);
        context.startActivity(intent);
    }

    public void startAboutActivity(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_FROM_BACKGROUND);
        context.startActivity(intent);
    }

    public void startSuggestionActivity(Context context) {
        Intent intent = new Intent(context, SuggestionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_FROM_BACKGROUND);
        context.startActivity(intent);
    }

    public void setUserPic(String filename) {

        fileModel.getUserPic(BmobUtils.getCurrentUser(), new OngetUserPicListener() {
            @Override
            public void onSucess(Bitmap bitmap) {
                if (v != null) v.showUserImg(bitmap);
            }

            @Override
            public void onError() {

            }
        });

    }

    public void startAddFriendActivity(Context context) {
        Intent intent = new Intent(context, AddFriendActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }

    // 解除view的绑定
    public void onRelieveView() {
        if (v != null) v = null;
    }

}
