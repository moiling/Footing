package team.far.footing.presenter;

import android.content.Context;
import android.content.Intent;

import team.far.footing.model.IUserModel;
import team.far.footing.model.Listener.OnLoginForQQListener;
import team.far.footing.model.Listener.OnLoginListener;
import team.far.footing.model.bean.Userbean;
import team.far.footing.model.impl.UserModel;
import team.far.footing.ui.activity.HomeActivity;
import team.far.footing.ui.activity.RegisterActivty;
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
        mUserModel = UserModel.getInstance();
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

    public void LoginForQQ() {
        mUserModel.loginForQQ(mILoginVu.getActivity(), new OnLoginForQQListener() {
            @Override
            public void loginSuccess(Userbean userbean) {
                mILoginVu.showLoginSuccee(userbean);
            }

            @Override
            public void loginFailed(String reason) {
                mILoginVu.showLoginFail(reason);
            }

            @Override
            public void loginCancel() {
                mILoginVu.showLogincancel();
            }
        });
    }

    public void startHomeActivity(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }

    public void startRegisterActivity(Context context) {
        Intent intent = new Intent(context, RegisterActivty.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }

    // 解除view的绑定
    public void onRelieveView() {
        mILoginVu = null;
    }
}

