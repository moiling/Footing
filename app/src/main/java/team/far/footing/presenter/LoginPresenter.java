package team.far.footing.presenter;

import android.content.Context;
import android.content.Intent;

import team.far.footing.model.IMessageModel;
import team.far.footing.model.IUserModel;
import team.far.footing.model.callback.OnLoginForQQListener;
import team.far.footing.model.callback.OnLoginListener;
import team.far.footing.model.bean.Userbean;
import team.far.footing.model.callback.OnUpdateUserListener;
import team.far.footing.model.impl.MessageModel;
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
    private IMessageModel mMessageModel;

    public LoginPresenter(ILoginVu mILoginVu) {
        this.mILoginVu = mILoginVu;
        mUserModel = UserModel.getInstance();
        mMessageModel = MessageModel.getInstance();
    }


    public void Login() {
        mILoginVu.showLoginLoading();

        mUserModel.Login(mILoginVu.getUserName(), mILoginVu.getPassword(), new OnLoginListener() {
            @Override
            public void loginSuccess(Userbean userbean) {
                mILoginVu.showLoginSuccee(userbean);
            }

            @Override
            public void loginFailed(int i, String reason) {
                mILoginVu.showLoginFail(i, reason);
            }
        });

    }

    public void LoginForQQ() {
        mUserModel.loginForQQ(mILoginVu.getActivity(), new OnLoginForQQListener() {
            @Override
            public void loginSuccess(Userbean userbean) {
                mILoginVu.showLoginSuccee(userbean);
                mMessageModel.sendMssageToUser(userbean, "欢迎登录足下", "希望你在足下玩得开心！\n有什么问题请一定及时和我们反馈哦！", new OnUpdateUserListener() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onFailure(int i, String s) {
                    }
                });
            }

            @Override
            public void loginFailed(String reason) {
                mILoginVu.showLoginFail(-1, reason);
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

