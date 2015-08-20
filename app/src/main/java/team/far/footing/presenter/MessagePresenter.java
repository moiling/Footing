package team.far.footing.presenter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.listener.FindListener;
import team.far.footing.model.IMessageModel;
import team.far.footing.model.bean.MessageBean;
import team.far.footing.model.callback.OnDateChangeListener;
import team.far.footing.model.callback.OnUpdateUserListener;
import team.far.footing.model.impl.MessageModel;
import team.far.footing.ui.vu.IMessageVu;
import team.far.footing.util.LogUtils;

/**
 * Created by moi on 2015/8/20.
 */
public class MessagePresenter {

    private List<MessageBean> messageBeanList;
    private IMessageModel messageModel;
    private IMessageModel realTimeData;
    private IMessageVu v;

    public MessagePresenter(IMessageVu v) {
        this.v = v;
        messageModel = MessageModel.getInstance();
        realTimeData = MessageModel.getInstance();
        start_listen_date();
        // 绑定的时候就获取一次信息
        // 以后就可以直接取
        loadMessage();
    }

    private void loadMessage() {
        messageModel.getAllMessage(new FindListener<MessageBean>() {
            @Override
            public void onSuccess(List<MessageBean> list) {
                messageBeanList = list;
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    public void refreshMessage() {
        messageModel.getAllMessage(new FindListener<MessageBean>() {
            @Override
            public void onSuccess(List<MessageBean> list) {
                messageBeanList = list;
                v.showMessage(list);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    private void start_listen_date() {
        realTimeData.startListener(
                new OnDateChangeListener() {
                    @Override
                    public void onConnectCompleted() {
                        LogUtils.e("监听开启");
                    }

                    @Override
                    public void onDataChange(List<MessageBean> list) {
                        if (list != null && list.size() != messageBeanList.size()) {
                            //有新的消息来了---在子线程哟 === 更新UI吧
                            messageBeanList = list;
                            LogUtils.e("有消息来了");
                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                    }
                }
        );
    }

    public void deleteMessage(MessageBean bean) {
        messageModel.deleteMsg(bean, new OnUpdateUserListener() {
            @Override
            public void onSuccess() {
                v.onMsgSuccess("删除成功");
            }

            @Override
            public void onFailure(int i, String s) {
                v.onMsgFail("删除失败");
            }
        });
    }

    public void remarkMessage(MessageBean bean) {
        messageModel.makeMsgReaded(bean);
    }

    //得到所有的消息
    public List<MessageBean> getAllMessage() {
        return messageBeanList;
    }

    //得到未读的消息
    public List<MessageBean> getUnReadMessage() {
        List<MessageBean> unread = new ArrayList<>();
        for (MessageBean msg : getAllMessage()) {
            if (msg.getIsread() == 0) {
                unread.add(msg);
            }
        }
        return unread;
    }

    // 解除view的绑定
    public void onRelieveView() {
        v = null;
    }

}
