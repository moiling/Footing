package team.far.footing.model;

import team.far.footing.model.Listener.OnQueryCommentListener;
import team.far.footing.model.Listener.OnQueryFriendListener;
import team.far.footing.model.Listener.OnQueryPostListener;
import team.far.footing.model.Listener.OnUpdateUserListener;
import team.far.footing.model.bean.Postbean;

/**
 * Created by luoyy on 2015/8/11 0011.
 */
public interface ISquareModel {

    /**
     * @param title                ---- post 标题
     * @param content              --- 内容
     * @param picUrl               ---  图片的 url
     * @param onUpdateUserListener ----更新用户的Listener
     */
    void sendPost(String title, String content,String picName, String picUrl, OnUpdateUserListener onUpdateUserListener);

    /**
     * @param onQueryPostListener ----获取当前用户所有post的监听
     */
    void getUserPost(OnQueryPostListener onQueryPostListener);

    /**
     * ------------得到用户所有的好友的post
     *
     * @param friendModel         -----------通过friendModel获取所有好友 --->>得到所有post
     * @param onQueryPostListener -----------监听器
     */
    void getUserFriendsPost(IFriendModel friendModel, OnQueryPostListener onQueryPostListener);


    /**
     * --------------设置post的赞
     *
     * @param postbean             ------------PostBean
     * @param onUpdateUserListener -------- 更新监听器
     */
    void setPraiseToPost(Postbean postbean, OnUpdateUserListener onUpdateUserListener);

    /**
     * ---------向指定的post发布评论
     *
     * @param postbean
     * @param conmment
     * @param onUpdateUserListener
     */
    void publishCommentToPost(Postbean postbean, String conmment, OnUpdateUserListener onUpdateUserListener);

    /**
     * 获取post的所有评论
     *
     * @param postbean
     * @param onQueryCommentListener
     */
    void getCommentForPost(Postbean postbean, OnQueryCommentListener onQueryCommentListener);

    /**
     * - -----------------------查询喜欢该post的所有用户
     *
     * @param postbean
     * @param onQueryFriendListener
     */
    void getLikePostUser(Postbean postbean, OnQueryFriendListener onQueryFriendListener);



}
