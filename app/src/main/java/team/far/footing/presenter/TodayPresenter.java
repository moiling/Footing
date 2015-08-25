package team.far.footing.presenter;

import java.util.ArrayList;
import java.util.List;

import team.far.footing.model.IFileModel;
import team.far.footing.model.IFriendModel;
import team.far.footing.model.IUserModel;
import team.far.footing.model.bean.Userbean;
import team.far.footing.model.callback.OnQueryFriendListener;
import team.far.footing.model.impl.FileModel;
import team.far.footing.model.impl.FriendModel;
import team.far.footing.model.impl.UserModel;
import team.far.footing.ui.vu.IFgTodayVu;
import team.far.footing.util.BmobUtils;
import team.far.footing.util.LogUtils;
import team.far.footing.util.TimeUtils;

/**
 * Created by luoyy on 2015/8/13 0013.
 */
public class TodayPresenter {
    private IFgTodayVu iFgTodayVu;
    private IFileModel fileModel;
    private IFriendModel friendModel;
    private IUserModel userModel;
    private List<Userbean> list = new ArrayList<>();

    public TodayPresenter(IFgTodayVu iFgTodayVu) {
        this.iFgTodayVu = iFgTodayVu;
        fileModel = FileModel.getInstance();
        friendModel = FriendModel.getInstance();
        userModel = UserModel.getInstance();
        getUserbeans(0);
    }


    public void refresh(int type) {
        getUserbeans(type);
    }

    public void getUserbeans(final int type) {
        if (iFgTodayVu != null) iFgTodayVu.onProgress();
        friendModel.getAllFriends(new OnQueryFriendListener() {
            @Override
            public void onSuccess(List<Userbean> object) {
                LogUtils.e("===============>>>>>>>>>>>>", object.toString());
                list = object;
                //把自己加进去
                list.add(BmobUtils.getCurrentUser());
                if (iFgTodayVu != null) {
                    iFgTodayVu.init(BmobUtils.getCurrentUser(), getSortListByAll(list));
                    choose_spinner(type);
                    iFgTodayVu.onProgressEnd();
                }
            }

            @Override
            public void onError(int code, String msg) {
                if (iFgTodayVu != null) {
                    iFgTodayVu.oninit_error(code, msg);
                    iFgTodayVu.onProgressEnd();
                }
            }


        });

    }


    public void choose_spinner(int position) {
        switch (position) {
            case 0:
                if (iFgTodayVu != null) iFgTodayVu.choose_alldistance(getSortListByAll(list));
                break;
            case 1:
                if (iFgTodayVu != null) iFgTodayVu.choose_distance(getSortListByToday(list));
                break;
            case 2:
                if (iFgTodayVu != null) iFgTodayVu.choose_level(getSortListBylevel(list));
                break;
            case 3:
                break;
        }
    }

    private List<Userbean> getSortListByAll(List<Userbean> list) {
        Userbean temp;
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 - i; j++) {
                if (list.get(j).getAll_distance() < list.get(j + 1).getAll_distance()) {
                    temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
        return list;
    }

    private List<Userbean> getSortListByToday(List<Userbean> list) {
        for (Userbean userbean : list) {
            if (TimeUtils.isToday(userbean.getToday_date())) {
                if (userbean.getToday_distance() != null) {
                    userbean.setToday_distance(userbean.getToday_distance());
                } else {
                    userbean.setToday_distance(0);
                }
            } else {
                userbean.setToday_distance(0);
            }
        }
        Userbean temp;
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 - i; j++) {
                if (list.get(j).getToday_distance() < list.get(j + 1).getToday_distance()) {
                    temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }

        return list;
    }

    private List<Userbean> getSortListBylevel(List<Userbean> list) {
        Userbean temp;
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 - i; j++) {
                if (list.get(j).getLevel() < list.get(j + 1).getLevel()) {
                    temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
        return list;
    }

    public void onRelieveView() {
        if (iFgTodayVu != null) iFgTodayVu = null;
    }
}
