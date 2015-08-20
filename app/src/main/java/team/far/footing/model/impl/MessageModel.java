package team.far.footing.model.impl;

import org.json.JSONObject;

import java.util.List;

import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.ValueEventListener;
import team.far.footing.app.APP;
import team.far.footing.model.IMessageModel;
import team.far.footing.model.bean.MessageBean;
import team.far.footing.model.callback.OnDateChangeListener;
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

                LogUtils.e("===============>>>>>>>>>>>>"+jsonObject.toString());
                UserModel.getInstance().getAllMessage(new FindListener<MessageBean>() {
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
}
