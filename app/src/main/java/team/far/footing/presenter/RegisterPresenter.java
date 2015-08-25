package team.far.footing.presenter;

import android.content.Context;
import android.content.Intent;

import team.far.footing.model.IMessageModel;
import team.far.footing.model.IUserModel;
import team.far.footing.model.callback.OnLoginListener;
import team.far.footing.model.callback.OnRegsterListener;
import team.far.footing.model.bean.Userbean;
import team.far.footing.model.callback.OnUpdateUserListener;
import team.far.footing.model.impl.MessageModel;
import team.far.footing.model.impl.UserModel;
import team.far.footing.ui.activity.HomeActivity;
import team.far.footing.ui.vu.IRegsterVu;

/**
 * Created by Luoyy on 2015/8/7 0007.
 */
public class RegisterPresenter {

    private IRegsterVu mIRegsterVu;
    private IMessageModel mMessageModel;
    // 这里应该是model的接口、否则接口白写了(＞﹏＜)
    private IUserModel mUserModel;

    public RegisterPresenter(IRegsterVu mIRegsterVu) {
        this.mIRegsterVu = mIRegsterVu;
        mUserModel = UserModel.getInstance();
        mMessageModel = MessageModel.getInstance();
    }

    public void Login() {
        if (mIRegsterVu != null) {
            mUserModel.Login(mIRegsterVu.getUserName(), mIRegsterVu.getPassword(), new OnLoginListener() {
                @Override
                public void loginSuccess(Userbean userbean) {
                    if (mIRegsterVu != null) mIRegsterVu.showLoginSuccee(userbean);
                }

                @Override
                public void loginFailed(int i, String reason) {
                    if (mIRegsterVu != null) mIRegsterVu.showLoginFail(i, reason);
                }
            });
        }
    }

    public void Regster() {
        if (mIRegsterVu != null) {
            mIRegsterVu.showRegsterLoading();
            mUserModel.Regster(mIRegsterVu.getUserName(), mIRegsterVu.getPassword(), mIRegsterVu.getEmail(), new OnRegsterListener() {
                @Override
                public void RegsterSuccess(final Userbean userbean) {
                    mMessageModel.sendMssageToUser(userbean, "欢迎注册足下", "希望你在足下玩得开心！\n有什么问题请一定及时和我们反馈哦！", new OnUpdateUserListener() {
                        @Override
                        public void onSuccess() {
                            if (mIRegsterVu != null) mIRegsterVu.showRegsterSuccee(userbean);
                        }

                        @Override
                        public void onFailure(int i, String s) {
                        }
                    });
                }

                @Override
                public void RegsterFail(int i) {
                    if (mIRegsterVu != null) mIRegsterVu.showRegsterFail(i);
                }
            });
        }
    }

    public void startHomeActivity(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }
    // 解除view的绑定
    public void onRelieveView() {
        if (mIRegsterVu != null) mIRegsterVu = null;
    }
}
