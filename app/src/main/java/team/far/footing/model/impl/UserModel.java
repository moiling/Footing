package team.far.footing.model.impl;

import android.app.Activity;
import android.content.Context;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import team.far.footing.R;
import team.far.footing.app.APP;
import team.far.footing.model.IUserModel;
import team.far.footing.model.Listener.OnLoginForQQListener;
import team.far.footing.model.Listener.OnLoginListener;
import team.far.footing.model.Listener.OnRegsterListener;
import team.far.footing.model.bean.Userbean;

/**
 * Created by moi on 2015/8/7.
 */
public class UserModel implements IUserModel {


    @Override
    public void Login(String username, String passwrod, final OnLoginListener onLoginListener, final Context context) {
        final Userbean loginBean = new Userbean();
        loginBean.setUsername(username);
        loginBean.setPassword(passwrod);

        loginBean.login(context, new SaveListener() {
            @Override
            public void onSuccess() {
                onLoginListener.loginSuccess(BmobUser.getCurrentUser(context, Userbean.class));
            }

            @Override
            public void onFailure(int i, String s) {
                onLoginListener.loginFailed(s);
            }
        });


    }

    @Override
    public void Regster(String username, String passwrod, final OnRegsterListener onRegsterListener, final Context context) {

        final Userbean regsterBean = new Userbean();

        regsterBean.setUsername(username);
        regsterBean.setPassword(passwrod);

        regsterBean.signUp(context, new SaveListener() {
            @Override
            public void onSuccess() {
                onRegsterListener.RegsterSuccess(BmobUser.getCurrentUser(context, Userbean.class));
            }

            @Override
            public void onFailure(int i, String s) {
                onRegsterListener.RegsterFail(s);
            }
        });

    }

    @Override
    public void loginForQQ(Activity activity, OnLoginForQQListener onLoginForQQListener) {

        Tencent mTencent =
                Tencent.createInstance(APP.getContext().getString(R.string.QQ_KEY), APP.getContext());

        if (!mTencent.isSessionValid()) {
            mTencent.login(activity, "不知道是什么乱", new IUiListener() {
                @Override
                public void onComplete(Object o) {

                }

                @Override
                public void onError(UiError uiError) {

                }

                @Override
                public void onCancel() {

                }
            });
        }
    }
}
