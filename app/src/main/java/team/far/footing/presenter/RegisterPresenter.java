package team.far.footing.presenter;

import team.far.footing.model.IUserModel;
import team.far.footing.model.Listener.OnRegsterListener;
import team.far.footing.model.bean.Userbean;
import team.far.footing.model.impl.UserModel;
import team.far.footing.ui.vu.IRegsterVu;

/**
 * Created by Luoyy on 2015/8/7 0007.
 */
public class RegisterPresenter {

    private IRegsterVu mIRegsterVu;
    // 这里应该是model的接口、否则接口白写了(＞﹏＜)
    private IUserModel mUserModel;

    public RegisterPresenter(IRegsterVu mIRegsterVu) {
        this.mIRegsterVu = mIRegsterVu;
        mUserModel = new UserModel();
    }

    public void Regster() {
        mIRegsterVu.showRegsterLoading();

        mUserModel.Regster(mIRegsterVu.getUserName(), mIRegsterVu.getPassword(), mIRegsterVu.getEmail(), new OnRegsterListener() {
            @Override
            public void RegsterSuccess(Userbean userbean) {
                mIRegsterVu.showRegsterSuccee(userbean);
            }

            @Override
            public void RegsterFail(String reason) {
                mIRegsterVu.showRegsterFail(reason);
            }
        });
    }

    // 解除view的绑定
    public void onRelieveView() {
        mIRegsterVu = null;
    }
}
