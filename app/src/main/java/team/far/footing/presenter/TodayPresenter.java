package team.far.footing.presenter;

import java.util.ArrayList;
import java.util.List;

import team.far.footing.model.IFileModel;
import team.far.footing.model.IFriendModel;
import team.far.footing.model.IUserModel;
import team.far.footing.model.callback.OnQueryFriendListener;
import team.far.footing.model.bean.Userbean;
import team.far.footing.model.impl.FileModel;
import team.far.footing.model.impl.FriendModel;
import team.far.footing.model.impl.UserModel;
import team.far.footing.ui.vu.IFgTodayVu;
import team.far.footing.util.BmobUtils;
import team.far.footing.util.LogUtils;

/**
 * Created by luoyy on 2015/8/13 0013.
 */
public class TodayPresenter {
    private IFgTodayVu iFgTodayVU;
    private IFileModel fileModel;
    private IFriendModel friendModel;
    private IUserModel userModel;
    private List<Userbean> list = new ArrayList<>();

    public TodayPresenter(IFgTodayVu iFgTodayVU) {
        this.iFgTodayVU = iFgTodayVU;
        fileModel = FileModel.getInstance();
        friendModel = FriendModel.getInstance();
        userModel = UserModel.getInstance();
        getUserbeans();
    }


    public void getUserbeans() {
        friendModel.getAllFriends(new OnQueryFriendListener() {
            @Override
            public void onSuccess(List<Userbean> object) {
                LogUtils.e("===============>>>>>>>>>>>>", object.toString());
                list = object;
                iFgTodayVU.init(BmobUtils.getCurrentUser(), getSortListByAll(list));
            }

            @Override
            public void onError(int code, String msg) {
                iFgTodayVU.oninit_error(code, msg);
            }


        });

    }


    public void choose_spinner(int position) {
        switch (position) {
            case 0:
                iFgTodayVU.choose_alldistance(getSortListByAll(list));
                break;
            case 1:
                iFgTodayVU.choose_distance(getSortListByToday(list));
                break;
            case 2:
                iFgTodayVU.choose_level(getSortListBylevel(list));
                break;
            case 3:
                break;
        }
    }

    private List<Userbean> getSortListByAll(List<Userbean> list) {
        Userbean temp;
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 - i; j++) {
                if (list.get(i).getAll_distance() < list.get(i + 1).getAll_distance()) {
                    temp = list.get(i);
                    list.set(i, list.get(i + 1));
                    list.set(i + 1, temp);
                }
            }
        }
        return list;
    }

    private List<Userbean> getSortListByToday(List<Userbean> list) {
        Userbean temp;
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 - i; j++) {
                if (list.get(i).getToday_distance() < list.get(i + 1).getToday_distance()) {
                    temp = list.get(i);
                    list.set(i, list.get(i + 1));
                    list.set(i + 1, temp);
                }
            }
        }

        return list;
    }

    private List<Userbean> getSortListBylevel(List<Userbean> list) {
        Userbean temp;
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 - i; j++) {
                if (list.get(i).getLevel() < list.get(i + 1).getLevel()) {
                    temp = list.get(i);
                    list.set(i, list.get(i + 1));
                    list.set(i + 1, temp);
                }
            }
        }
        return list;
    }


    //测试用的
    public void add_friends() {
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

        userModel.queryUserById("12345", new OnQueryFriendListener() {
            @Override
            public void onSuccess(List<Userbean> object) {
                friendModel.addFriend(object.get(0), null);
            }

            @Override
            public void onError(int code, String msg) {

            }
        });
    }

}
