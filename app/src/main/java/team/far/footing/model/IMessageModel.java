package team.far.footing.model;

import cn.bmob.v3.listener.FindListener;
import team.far.footing.model.bean.MessageBean;
import team.far.footing.model.bean.Userbean;
import team.far.footing.model.callback.OnDateChangeListener;
import team.far.footing.model.callback.OnUpdateUserListener;

/**
 * Created by luoyy on 2015/8/20 0020.
 */
public interface IMessageModel {
    void startListener(OnDateChangeListener onDateChangeListener);

    /**
     * 删除msg
     *
     * @param messageBean
     */
    void deleteMsg(MessageBean messageBean);

    /**
     * 把msg标记为 已读
     */
    void makeMsgReaded(MessageBean messageBean);


    /**
     * 给用户发送消息
     *
     * @param userbean
     * @param messager
     */
    void sendMssageToUser(Userbean userbean, String messager, OnUpdateUserListener onUpdateUserListener);


    /**
     * 得到当前用户的所有 message
     *
     * @param findListener
     */
    void getAllMessage(FindListener<MessageBean> findListener);

    /**
     * 给指定的  userbean 添加一条消息
     *
     * @param userbean
     * @param messageBean
     */
    void AddMessage(Userbean userbean, MessageBean messageBean, OnUpdateUserListener onUpdateUserListener);

}
