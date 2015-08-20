package team.far.footing.model;

import team.far.footing.model.callback.OnIsMyFriendListener;
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

    /**
     * 删除朋友
     *
     * @param userbean
     * @param onUpdateUserListener
     */
    void deleteFriend(Userbean userbean, OnUpdateUserListener onUpdateUserListener);

    /**
     *   根据用户名判断
     * @param username
     * @param onIsMyFriendListener
     */
    void isMyFriendByUsername(String username, OnIsMyFriendListener onIsMyFriendListener);

    void isMyFriendByNickname(String username, OnIsMyFriendListener onIsMyFriendListener);

}
