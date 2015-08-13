package team.far.footing.presenter;

import java.util.List;

import team.far.footing.model.IFileModel;
import team.far.footing.model.IFriendModel;
import team.far.footing.model.IUserModel;
import team.far.footing.model.Listener.OnQueryFriendListener;
import team.far.footing.model.bean.Userbean;
import team.far.footing.model.impl.FileModel;
import team.far.footing.model.impl.FriendModel;
import team.far.footing.model.impl.UserModel;
import team.far.footing.ui.vu.IFgTodayVU;
import team.far.footing.util.BmobUtils;
import team.far.footing.util.LogUtils;

/**
 * Created by luoyy on 2015/8/13 0013.
 */
public class TodayPresenter {
    private IFgTodayVU iFgTodayVU;
    private IFileModel fileModel;
    private IFriendModel friendModel;
    private IUserModel userModel;
    public TodayPresenter(IFgTodayVU iFgTodayVU) {
        this.iFgTodayVU = iFgTodayVU;
        fileModel = FileModel.getInstance();
        friendModel = FriendModel.getInstance();
        userModel = UserModel.getInstance();
        userModel.queryUserById("12345",new  OnQueryFriendListener(){
            @Override
            public void onSuccess(List<Userbean> object) {
                friendModel.addFriend(object.get(0),null);
            }

            @Override
            public void onError(int code, String msg) {

            }
        });
        userModel.queryUserById("yy", new OnQueryFriendListener() {
            @Override
            public void onSuccess(List<Userbean> object) {
                friendModel.addFriend(object.get(0), null);
            }

            @Override
            public void onError(int code, String msg) {

            }
        });
        userModel.queryUserById("321", new OnQueryFriendListener() {
            @Override
            public void onSuccess(List<Userbean> object) {
                friendModel.addFriend(object.get(0), null);
            }

            @Override
            public void onError(int code, String msg) {

            }
        });
        userModel.queryUserById("123", new OnQueryFriendListener() {
            @Override
            public void onSuccess(List<Userbean> object) {
                friendModel.addFriend(object.get(0), null);
            }

            @Override
            public void onError(int code, String msg) {

            }
        });
        getUserbeans();
    }


    public void getUserbeans() {
        friendModel.getAllFriends(new OnQueryFriendListener() {
            @Override
            public void onSuccess(List<Userbean> object) {
                LogUtils.e("===============>>>>>>>>>>>>",object.toString());
                iFgTodayVU.init(BmobUtils.getCurrentUser(), object);
            }

            @Override
            public void onError(int code, String msg) {
                iFgTodayVU.oninit_error(code, msg);
            }
        });

    }


}
