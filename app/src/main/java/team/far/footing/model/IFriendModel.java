package team.far.footing.model;

import team.far.footing.model.callback.OnQueryFriendListener;
import team.far.footing.model.callback.OnUpdateUserListener;
import team.far.footing.model.bean.Userbean;

/**
 * Created by Luoyy on 2015/8/9 0009.
 */
public interface IFriendModel {

    /**
     * @param userbean             ------------需要添加的Userbean
     * @param onUpdateUserListener ---------------更新用户数据的监听器
     */
    void addFriend(Userbean userbean, OnUpdateUserListener onUpdateUserListener);

    /**
     * ##查询当前用户已经添加了的好友 list
     *
     * @param onQueryFriendListener ------------查询的监听器
     */
    void getAllFriends(OnQueryFriendListener onQueryFriendListener);

    void deleteFriend(Userbean userbean, OnUpdateUserListener onUpdateUserListener);
}
