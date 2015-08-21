package team.far.footing.presenter;

import java.util.List;

import team.far.footing.model.IFriendModel;
import team.far.footing.model.IUserModel;
import team.far.footing.model.bean.Userbean;
import team.far.footing.model.callback.OnQueryFriendListener;
import team.far.footing.model.impl.FriendModel;
import team.far.footing.model.impl.UserModel;
import team.far.footing.ui.adapter.FriendsRyViewAdapter;
import team.far.footing.ui.vu.IFgFriendVu;

/**
 * Created by luoyy on 2015/8/18 0018.
 */
public class FgFriendPresenter {

    private IFgFriendVu iFgFriendVu;
    private IFriendModel friendModel;
    private IUserModel userModel;
    private FriendsRyViewAdapter friendsRyViewAdapter;


    public FgFriendPresenter(IFgFriendVu iFgFriendVu) {
        this.iFgFriendVu = iFgFriendVu;
        friendModel = FriendModel.getInstance();
        userModel = UserModel.getInstance();
        showFriends();
    }

    private void showFriends() {
        friendModel.getAllFriends(new OnQueryFriendListener() {
            @Override
            public void onSuccess(List<Userbean> object) {
                if (iFgFriendVu != null) {
                    friendsRyViewAdapter = new FriendsRyViewAdapter(object);
                    iFgFriendVu.showFriends(friendsRyViewAdapter);
                }
            }

            @Override
            public void onError(int code, String msg) {

            }
        });
    }


    public void refresh() {
        friendModel.getAllFriends(new OnQueryFriendListener() {
            @Override
            public void onSuccess(List<Userbean> object) {
                if (iFgFriendVu != null) {
                    friendsRyViewAdapter = new FriendsRyViewAdapter(object);
                    iFgFriendVu.showFriends(friendsRyViewAdapter);
                    iFgFriendVu.stopRefresh();
                }
            }

            @Override
            public void onError(int code, String msg) {

            }
        });

    }
    public void onRelieveView() {
        iFgFriendVu = null;
    }

}
