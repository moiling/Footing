package team.far.footing.presenter;

import android.content.Context;
import android.content.Intent;

import team.far.footing.app.APP;
import team.far.footing.model.bean.Userbean;
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

    public HomePresenter(IHomeVu v) {
        this.v = v;
        // 当homePresenter被实例化，用sp保存登录状态
        SPUtils.put(APP.getContext(), "isLogin", Boolean.TRUE);
        userbean = BmobUtils.getCurrentUser();
        LogUtils.d(userbean.getUsername());
        showUserInformation();
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

    // 解除view的绑定
    public void onRelieveView() {
        v = null;
    }
}
