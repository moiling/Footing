package team.far.footing.ui.vu;

/**
 * Created by moi on 2015/8/18.
 */
public interface IFriendInfoVu {
    // 添加为好友
    void addFriend();

    // 删除该好友
    void deleteFriend();

    // 发起聊天
    void talk();

    // 显示进程条
    void showProgress();

    // 消失进程条
    void dismissProgress();

    // 添加好友成功
    void onAddSuccess();

    // 添加好友失败
    void onAddFail(int i);

    // 删除好友成功
    void onDeleteSuccess();

    // 删除好友失败
    void onDeleteFail(int i);


}
