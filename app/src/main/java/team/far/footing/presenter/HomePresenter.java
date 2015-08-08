package team.far.footing.presenter;

import team.far.footing.model.bean.Userbean;
import team.far.footing.ui.vu.IHomeVu;
import team.far.footing.util.BmobUtils;
import team.far.footing.util.LogUtils;

/**
 * Created by moi on 2015/8/9.
 */
public class HomePresenter {
    private IHomeVu v;
    private Userbean userbean;

    public HomePresenter(IHomeVu v) {
        this.v = v;
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
    // 解除view的绑定
    public void onRelieveView() {
        v = null;
    }
}
