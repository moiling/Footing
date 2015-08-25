package team.far.footing.model.impl;

import org.json.JSONObject;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.ValueEventListener;
import team.far.footing.app.APP;
import team.far.footing.model.IMessageModel;
import team.far.footing.model.bean.MessageBean;
import team.far.footing.model.bean.Userbean;
import team.far.footing.model.callback.OnDateChangeListener;
import team.far.footing.model.callback.OnUpdateUserListener;
import team.far.footing.util.BmobUtils;
import team.far.footing.util.LogUtils;

/**
 * Created by luoyy on 2015/8/20 0020.
 */
public class MessageModel implements IMessageModel {
    public static final MessageModel instance = new MessageModel();


    private MessageModel() {

    }

    public static MessageModel getInstance() {
        return instance;
    }


    @Override
    public void startListener(final OnDateChangeListener onDateChangeListener) {
        final BmobRealTimeData rtd = new BmobRealTimeData();
        rtd.start(APP.getContext(), new ValueEventListener() {
            @Override
            public void onConnectCompleted() {
                //对当前用户对应的好友表中的自己那行进行监听
                rtd.subRowUpdate("_User", BmobUtils.getCurrentUser().getObjectId());
                onDateChangeListener.onConnectCompleted();
            }

            @Override
            public void onDataChange(final JSONObject jsonObject) {

                LogUtils.e("===============>>>>>>>>>>>>" + jsonObject.toString());
                getAllMessage(new FindListener<MessageBean>() {
                    @Override
                    public void onSuccess(List<MessageBean> list) {
                        onDateChangeListener.onDataChange(list);
                    }

                    @Override
                    public void onError(int i, String s) {
                        onDateChangeListener.onError(i, s);
                    }
                });


            }
        });
    }

    @Override
    public void deleteMsg(MessageBean messageBean, final OnUpdateUserListener onUpdateUserListener) {
        messageBean.delete(APP.getContext(), new DeleteListener() {
            @Override
            public void onSuccess() {
                onUpdateUserListener.onSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                onUpdateUserListener.onFailure(i, s);
            }
        });
    }

    @Override
    public void makeMsgReaded(MessageBean messageBean) {
        messageBean.setIsread(1);
        messageBean.update(APP.getContext());
    }

    @Override
    public void sendMssageToUser(final Userbean userbean, String message, String content, final OnUpdateUserListener onUpdateUserListener) {
        final MessageBean messageBean = new MessageBean();
        messageBean.setGetuser(userbean);
        messageBean.setSenduser(BmobUtils.getCurrentUser());
        messageBean.setIsread(0);
        messageBean.setMessage(message);
        messageBean.setContent(content);
        messageBean.save(APP.getContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                AddMessage(userbean, messageBean, onUpdateUserListener);
            }

            @Override
            public void onFailure(int i, String s) {
                onUpdateUserListener.onFailure(i, s);
            }
        });

    }

    @Override
    public void AddMessage(Userbean userbean, MessageBean messageBean, final OnUpdateUserListener onUpdateUserListener) {
        BmobRelation bmobRelation = new BmobRelation();
        bmobRelation.add(messageBean);
        userbean.setMessages(bmobRelation);
        userbean.update(APP.getContext(), new UpdateListener() {
            @Override
            public void onSuccess() {
                onUpdateUserListener.onSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                onUpdateUserListener.onFailure(i, s);
            }
        });
    }

    @Override
    public void getAllMessage(FindListener<MessageBean> findListener) {
        BmobQuery<MessageBean> query = new BmobQuery<MessageBean>();
        query.addWhereRelatedTo("messages", new BmobPointer(BmobUtils.getCurrentUser()));
        query.findObjects(APP.getContext(), findListener);
    }


}
