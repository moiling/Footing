package team.far.footing.presenter;

import java.util.List;

import team.far.footing.model.IFriendModel;
import team.far.footing.model.IUserModel;
import team.far.footing.model.bean.Userbean;
import team.far.footing.model.callback.OnQueryFriendListener;
import team.far.footing.model.impl.FriendModel;
import team.far.footing.model.impl.UserModel;
import team.far.footing.ui.adapter.FriendsRyViewAdapter;
import team.far.footing.ui.vu.IAddFriendVu;

/**
 * Created by luoyy on 2015/8/18 0018.
 */
public class AddFriendPresenter {

    private IAddFriendVu iAddFriendVu;
    private IFriendModel friendModel;
    private IUserModel userModel;
    private FriendsRyViewAdapter friendsRyViewAdapter;

    public AddFriendPresenter(IAddFriendVu iAddFriendVu) {
        this.iAddFriendVu = iAddFriendVu;
        friendModel = FriendModel.getInstance();
        userModel = UserModel.getInstance();
        showAllUser();
    }


    public void query(final String s) {

        userModel.queryUserById(s, new OnQueryFriendListener() {
            @Override
            public void onSuccess(List<Userbean> object) {
                if (object.size() == 0) {

                    userModel.queryUserByName(s, new OnQueryFriendListener() {
                        @Override
                        public void onSuccess(List<Userbean> object) {
                            friendsRyViewAdapter = new FriendsRyViewAdapter(object);
                            iAddFriendVu.showfriends(friendsRyViewAdapter);
                        }

                        @Override
                        public void onError(int code, String msg) {

                        }
                    });

                } else {
                    friendsRyViewAdapter = new FriendsRyViewAdapter(object);
                    iAddFriendVu.showfriends(friendsRyViewAdapter);
                }
            }

            @Override
            public void onError(int code, String msg) {

            }
        });

    }

    private void showAllUser() {
        friendModel.getAllFriends(new OnQueryFriendListener() {
            @Override
            public void onSuccess(List<Userbean> object) {
                friendsRyViewAdapter = new FriendsRyViewAdapter(object);
                iAddFriendVu.showfriends(friendsRyViewAdapter);
            }

            @Override
            public void onError(int code, String msg) {

            }
        });

    }

    public void Refresh() {
        iAddFriendVu.stopRefresh();
    }

    public void onRelieveView() {
        iAddFriendVu = null;
    }
}
