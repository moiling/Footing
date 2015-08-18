package team.far.footing.presenter;

import team.far.footing.model.IFriendModel;
import team.far.footing.model.bean.Userbean;
import team.far.footing.model.callback.OnUpdateUserListener;
import team.far.footing.model.impl.FileModel;
import team.far.footing.ui.vu.IFriendInfoVu;

/**
 * Created by moi on 2015/8/18.
 */
public class FriendInfoPresenter {

    private IFriendModel friendModel;
    private IFriendInfoVu v;

    public FriendInfoPresenter(IFriendInfoVu v) {
        this.v = v;
        friendModel = (IFriendModel) FileModel.getInstance();
    }

    // 判断是否为好友
    public void isFriend(Userbean userbean) {

    }

    public void addFriend(Userbean userbean) {
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

    public void deleteFriend(Userbean userbean) {
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
