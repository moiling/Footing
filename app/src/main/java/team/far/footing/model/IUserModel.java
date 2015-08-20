package team.far.footing.model;


import android.app.Activity;

import cn.bmob.v3.listener.FindListener;
import team.far.footing.model.bean.MessageBean;
import team.far.footing.model.bean.Userbean;
import team.far.footing.model.callback.OnLoginForQQListener;
import team.far.footing.model.callback.OnLoginListener;
import team.far.footing.model.callback.OnQueryFriendListener;
import team.far.footing.model.callback.OnRegsterListener;
import team.far.footing.model.callback.OnUpdateUserListener;
import team.far.footing.model.callback.OnUploadListener;

/**
 * Created by moi on 2015/8/7.
 */
public interface IUserModel {
    void Login(String username, String passwrod, OnLoginListener onLoginListener);

    void Regster(String username, String passwrod, String email, OnRegsterListener onRegsterListener);

    void loginForQQ(Activity activity, OnLoginForQQListener onLoginForQQListener);

    // 在编辑页面要一口气更新多个数据
    void updataUserInfo(String nickname, String signature, String email, OnUpdateUserListener onUpdateUserListener);

    /**
     * @param filePath                     ------- 文件路径
     * @param onUploadHeadPortraitListener ----------文件上传监听
     */
    void uploadHeadPortrait(String filePath, OnUploadListener onUploadHeadPortraitListener);


    /**
     * @param signatrue            ----------更新签名
     * @param onUpdateUserListener ---------更新用户数据的监听器
     */
    void updateUser_Signature(String signatrue, OnUpdateUserListener onUpdateUserListener);

    /**
     * @param level                ------------更新等级
     * @param onUpdateUserListener ------------更新用户数据的监听器
     */
    void updateUser_level(int level, OnUpdateUserListener onUpdateUserListener);

    /**
     * @param nickname             ------------昵称
     * @param onUpdateUserListener --------------更新用户数据的监听器
     */
    void update_NickName(String nickname, OnUpdateUserListener onUpdateUserListener);

    /**
     * @param PraiseCount          ----------点赞次数
     * @param onUpdateUserListener -----------更新用户数据的监听器
     */
    void update_PraiseCount(int PraiseCount, OnUpdateUserListener onUpdateUserListener);

    void update_distance(int today_distance, int all_distance, OnUpdateUserListener onUpdateUserListener);

    /**
     * @param today_distance       ----------今日路程
     * @param onUpdateUserListener ---------用户更新的监听器
     */
    void update_today_distance(int today_distance, OnUpdateUserListener onUpdateUserListener);

    /**
     * @param all_distance         --------总的距离
     * @param onUpdateUserListener --------用户更新的监听器
     */
    void update_all_distance(int all_distance, OnUpdateUserListener onUpdateUserListener);

    /**
     * @param update_is_finish_today ----------是否完成每日任务  0表示未完成  1表示完成
     * @param onUpdateUserListener   ------------------用户更新的监听器
     */
    void update_is_finish_today(int update_is_finish_today, OnUpdateUserListener onUpdateUserListener);


    /**
     * @param nickname              ----------查询的用户的nickname
     * @param onQueryFriendListener -----------查询的监听器
     *                              <p/>
     *                              ## 按照nickname查找
     */
    void queryUserByName(String nickname, OnQueryFriendListener onQueryFriendListener);

    /**
     * @param id                    -----------查询用户的username
     * @param onQueryFriendListener -----------查询的监听器
     */
    void queryUserById(String id, OnQueryFriendListener onQueryFriendListener);

    /**
     * ##查找附近的人
     * ===还没写好先把接口放在这里
     */
    void queryUserByDistance(OnQueryFriendListener onQueryFriendListener);

    /**
     * ##查询所有注册用户
     *
     * @param onQueryFriendListener -----------查询的监听器
     */
    void queryAlluser(OnQueryFriendListener onQueryFriendListener);

    /**
     * @param email                -----------用户注册时输入的邮箱
     * @param onUpdateUserListener ------------更新用户数据的监听器
     */
    void resetPasswordByEmail(String email, OnUpdateUserListener onUpdateUserListener);

}
