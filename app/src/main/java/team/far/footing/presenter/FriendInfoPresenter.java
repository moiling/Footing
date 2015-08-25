package team.far.footing.presenter;

import android.graphics.Bitmap;

import team.far.footing.model.IFileModel;
import team.far.footing.model.IFriendModel;
import team.far.footing.model.IUserModel;
import team.far.footing.model.bean.Userbean;
import team.far.footing.model.callback.OnIsMyFriendListener;
import team.far.footing.model.callback.OnUpdateUserListener;
import team.far.footing.model.impl.FileModel;
import team.far.footing.model.impl.FriendModel;
import team.far.footing.model.impl.UserModel;
import team.far.footing.ui.vu.IFriendInfoVu;
import team.far.footing.util.BmobUtils;
import team.far.footing.util.LogUtils;

/**
 * Created by moi on 2015/8/18.
 */
public class FriendInfoPresenter {

    private IFriendModel friendModel;
    private IFriendInfoVu v;
    private IUserModel userModel;
    private IFileModel fileModel;
    private Userbean userbean;

    public FriendInfoPresenter(IFriendInfoVu v, Userbean userbean) {
        this.v = v;
        friendModel = FriendModel.getInstance();
        userModel = UserModel.getInstance();
        fileModel = FileModel.getInstance();
        this.userbean = userbean;
        showUserInformation();
    }

    public void initView() {
        friendModel.isMyFriendByUsername(userbean.getUsername(), new OnIsMyFriendListener() {
            @Override
            public void onSuccess(boolean b) {
                LogUtils.d("是不是好友？" + b);
                if (b) {
                    if (v != null) v.initFriendView();
                } else {
                    if (v != null) v.initNotFriendView();
                }
            }

            @Override
            public void onError(int code, String msg) {
                if (v != null) {
                    v.initNotFriendView();
                    v.onError("加载出错了！");
                }
            }
        });
    }

    public void addFriend() {
        v.showProgress();
        friendModel.addFriend(userbean, new OnUpdateUserListener() {
            @Override
            public void onSuccess() {
                if (v != null) {
                    v.dismissProgress();
                    v.onAddSuccess();
                }
            }

            @Override
            public void onFailure(int i, String s) {
                if (v != null) {
                    v.dismissProgress();
                    v.onAddFail(i);
                }
            }
        });
    }

    public void deleteFriend() {
        if (v != null) v.showProgress();
        friendModel.deleteFriend(userbean, new OnUpdateUserListener() {
            @Override
            public void onSuccess() {
                if (v != null) {
                    v.dismissProgress();
                    v.onDeleteSuccess();
                }
            }

            @Override
            public void onFailure(int i, String s) {
                if (v != null) {
                    v.dismissProgress();
                    v.onDeleteFail(i);
                }
            }
        });

    }

    public void talk() {

    }

    public void onRelieveView() {
        if (v != null) v = null;
    }

    public void refreshUserInformation() {
        userbean = BmobUtils.getCurrentUser();
        showUserInformation();
    }

    public void showUserInformation() {
        Bitmap bitmap = fileModel.getLocalPic(userbean.getHeadPortraitFileName());
        if (v != null) v.showUserInformation(userbean, bitmap);
    }


}
