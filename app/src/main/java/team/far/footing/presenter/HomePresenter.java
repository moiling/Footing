package team.far.footing.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.listener.FindListener;
import team.far.footing.app.APP;
import team.far.footing.model.IFileModel;
import team.far.footing.model.IMessageModel;
import team.far.footing.model.IUserModel;
import team.far.footing.model.bean.MessageBean;
import team.far.footing.model.callback.OnDateChangeListener;
import team.far.footing.model.callback.OnUpdateUserListener;
import team.far.footing.model.callback.OngetUserPicListener;
import team.far.footing.model.bean.Userbean;
import team.far.footing.model.impl.FileModel;
import team.far.footing.model.impl.MessageModel;
import team.far.footing.model.impl.UserModel;
import team.far.footing.ui.activity.AddFriendActivity;
import team.far.footing.ui.activity.MyMapActivity;
import team.far.footing.ui.activity.SettingActivity;
import team.far.footing.ui.activity.UserInfoActivity;
import team.far.footing.ui.activity.WalkActivity;
import team.far.footing.ui.vu.IHomeVu;
import team.far.footing.util.BmobUtils;
import team.far.footing.util.LogUtils;
import team.far.footing.util.SPUtils;

/**
 * Created by moi on 2015/8/9.
 */
public class HomePresenter {
    private IHomeVu v;
    private Userbean userbean;
    private IFileModel fileModel;
    private IMessageModel realTimeData;
    private IUserModel userModel;
    private List<MessageBean> messageBeanList;
    private IMessageModel messageModel;

    public HomePresenter(IHomeVu v) {
        this.v = v;
        // 当homePresenter被实例化，用sp保存登录状态
        SPUtils.put(APP.getContext(), "isLogin", Boolean.TRUE);
        fileModel = FileModel.getInstance();
        realTimeData = MessageModel.getInstance();
        userModel = UserModel.getInstance();
        messageModel = MessageModel.getInstance();
        userbean = BmobUtils.getCurrentUser();
        LogUtils.d(userbean.getUsername());
        showUserInformation();
        if (userbean.getHeadPortraitFileName() != null) {
            setUserPic(userbean.getHeadPortraitFileName());
        }
        //开始监听
        start_listen_date();
    }

    public void refreshUserInformation() {
        userbean = BmobUtils.getCurrentUser();
        showUserInformation();
        if (userbean.getHeadPortraitFileName() != null) {
            setUserPic(userbean.getHeadPortraitFileName());
        }
    }

    public void showUserInformation() {
        messageModel.getAllMessage(new FindListener<MessageBean>() {
            @Override
            public void onSuccess(List<MessageBean> list) {
                messageBeanList = list;
                v.showUserInformation(userbean, list);
            }
            @Override
            public void onError(int i, String s) {

            }
        });
    }

    public void startWalkActivity(Context context) {
        Intent intent = new Intent(context, WalkActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }

    public void startUserInfoActivity(Context context) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }

    public void startSettingActivity(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }

    public void startMyMapActivity(Context context) {
        Intent intent = new Intent(context, MyMapActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_FROM_BACKGROUND);
        context.startActivity(intent);
    }

    public void setUserPic(String filename) {

        fileModel.getUserPic(BmobUtils.getCurrentUser(), new OngetUserPicListener() {
            @Override
            public void onSucess(Bitmap bitmap) {
                v.showUserImg(bitmap);
            }

            @Override
            public void onError() {

            }
        });

    }

    public void startAddFriendActivity(Context context) {
        Intent intent = new Intent(context, AddFriendActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
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
