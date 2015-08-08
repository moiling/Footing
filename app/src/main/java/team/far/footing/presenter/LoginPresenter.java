package team.far.footing.presenter;

import team.far.footing.model.IUserModel;
import team.far.footing.model.Listener.OnLoginListener;
import team.far.footing.model.bean.Userbean;
import team.far.footing.model.impl.UserModel;
import team.far.footing.ui.vu.ILoginVu;

/**
 * Created by moi on 2015/8/7.
 */
public class LoginPresenter {
    private ILoginVu mILoginVu;
    // 这里应该是model的接口、否则接口白写了(＞﹏＜)
    private IUserModel mUserModel;

    public LoginPresenter(ILoginVu mILoginVu) {
        this.mILoginVu = mILoginVu;
        mUserModel = new UserModel();
    }


    public void Login() {
        mILoginVu.showLoginLoading();

        mUserModel.Login(mILoginVu.getUserName(), mILoginVu.getPassword(), new OnLoginListener() {
            @Override
            public void loginSuccess(Userbean userbean) {
                mILoginVu.showLoginSuccee(userbean);
            }

            @Override
            public void loginFailed(String reason) {
                mILoginVu.showLoginFail(reason);
            }
        });

    }
}
