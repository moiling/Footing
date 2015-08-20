package team.far.footing.presenter;

import team.far.footing.model.IFriendModel;
import team.far.footing.model.bean.Userbean;
import team.far.footing.model.callback.OnIsMyFriendListener;
import team.far.footing.model.callback.OnUpdateUserListener;
import team.far.footing.model.impl.FriendModel;
import team.far.footing.ui.vu.IFriendInfoVu;
import team.far.footing.util.LogUtils;

/**
 * Created by moi on 2015/8/18.
 */
public class FriendInfoPresenter {

    private IFriendModel friendModel;
    private IFriendInfoVu v;
    private Userbean userbean;

    public FriendInfoPresenter(IFriendInfoVu v, Userbean userbean) {
        this.v = v;
        friendModel = FriendModel.getInstance();
        this.userbean = userbean;
    }

    public void initView() {
        friendModel.isMyFriendByUsername(userbean.getUsername(), new OnIsMyFriendListener() {
            @Override
            public void onSuccess(boolean b) {
                LogUtils.d("是不是好友？" + b);
                if (b) {
                    v.initFriendView();
                } else {
                    v.initNotFriendView();
                }
            }

            @Override
            public void onError(int code, String msg) {
                v.initNotFriendView();
                v.onError("加载出错了！");
            }
        });
    }

    public void addFriend() {
        v.showProgress();
        friendModel.addFriend(userbean, new OnUpdateUserListener() {
            @Override
            public void onSuccess() {
                v.dismissProgress();
                v.onAddSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                v.dismissProgress();
                v.onAddFail(i);
            }
        });
    }

    public void deleteFriend() {
        v.showProgress();
        friendModel.deleteFriend(userbean, new OnUpdateUserListener() {
            @Override
            public void onSuccess() {
                v.dismissProgress();
                v.onDeleteSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                v.dismissProgress();
                v.onDeleteFail(i);
            }
        });

    }

    public void talk() {

    }

    public void onRelieveView() {
        v = null;
    }



}
