package team.far.footing.model.impl;

import cn.bmob.v3.listener.SaveListener;
import team.far.footing.app.APP;
import team.far.footing.model.IUserModel;
import team.far.footing.model.Listener.OnLoginListener;
import team.far.footing.model.Listener.OnRegsterListener;
import team.far.footing.model.bean.Userbean;

/**
 * Created by moi on 2015/8/7.
 */
public class UserModel implements IUserModel {


    @Override
    public void Login(String username, String passwrod, final OnLoginListener onLoginListener) {
        final Userbean loginBean = new Userbean();
        loginBean.setUsername(username);
        loginBean.setPassword(passwrod);

        loginBean.login(APP.getContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                onLoginListener.loginSuccess(loginBean);
            }

            @Override
            public void onFailure(int i, String s) {
                onLoginListener.loginFailed(s);
            }
        });


    }

    @Override
    public void Regster(String username, String passwrod, final OnRegsterListener onRegsterListener) {

        final Userbean regsterBean = new Userbean();

        regsterBean.setUsername(username);
        regsterBean.setPassword(passwrod);

        regsterBean.signUp(APP.getContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                onRegsterListener.RegsterSuccess(regsterBean);
            }

            @Override
            public void onFailure(int i, String s) {
                onRegsterListener.RegsterFail(s);
            }
        });

    }
}
