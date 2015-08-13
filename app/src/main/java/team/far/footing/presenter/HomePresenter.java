package team.far.footing.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.webkit.DownloadListener;

import team.far.footing.app.APP;
import team.far.footing.model.IFileModel;
import team.far.footing.model.bean.Userbean;
import team.far.footing.model.impl.FileModel;
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

    public HomePresenter(IHomeVu v) {
        this.v = v;
        // 当homePresenter被实例化，用sp保存登录状态
        SPUtils.put(APP.getContext(), "isLogin", Boolean.TRUE);
        fileModel = FileModel.getInstance();
        userbean = BmobUtils.getCurrentUser();
        LogUtils.d(userbean.getUsername());
        showUserInformation();
        setUserPic(BmobUtils.getCurrentUser().getHeadPortraitFileName());

    }

    public void refreshUserInformation() {
        userbean = BmobUtils.getCurrentUser();
        showUserInformation();
    }

    public void showUserInformation() {
        v.showUserInformation(userbean);
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


    public void setUserPic(String filename) {
        Log.e("=sds=============>>>>", filename);
        fileModel.downloadPic(filename, new com.bmob.btp.callback.DownloadListener() {
            @Override
            public void onSuccess(String s) {
                Log.e("=sds=============>>>>", s);
                v.showUserImg(BitmapFactory.decodeFile(s));
            }

            @Override
            public void onProgress(String s, int i) {
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    // 解除view的绑定
    public void onRelieveView() {
        v = null;
    }

}
