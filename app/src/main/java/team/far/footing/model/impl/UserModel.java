package team.far.footing.model.impl;

import android.app.Activity;

import com.google.gson.Gson;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.OtherLoginListener;
import cn.bmob.v3.listener.SaveListener;
import team.far.footing.R;
import team.far.footing.app.APP;
import team.far.footing.model.IUserModel;
import team.far.footing.model.Listener.OnLoginForQQListener;
import team.far.footing.model.Listener.OnLoginListener;
import team.far.footing.model.Listener.OnRegsterListener;
import team.far.footing.model.bean.Userbean;
import team.far.footing.util.BmobUtils;
import team.far.footing.util.LogUtils;

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
                onLoginListener.loginSuccess(BmobUtils.getCurrentUser());
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
                onRegsterListener.RegsterSuccess(BmobUser.getCurrentUser(APP.getContext(), Userbean.class));
            }

            @Override
            public void onFailure(int i, String s) {
                onRegsterListener.RegsterFail(s);
            }
        });

    }

    @Override
    public void loginForQQ(Activity activity, final OnLoginForQQListener onLoginForQQListener) {

        Tencent mTencent =
                Tencent.createInstance(APP.getContext().getString(R.string.QQ_ID), APP.getContext());
        if (!mTencent.isSessionValid()) {
            mTencent.login(activity, "all", new IUiListener() {
                @Override
                public void onComplete(Object arg0) {
                    if (arg0 != null) {
                        JSONObject jsonObject = (JSONObject) arg0;
                        try {
                            String token = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN);
                            String expires = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_EXPIRES_IN);
                            String openId = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_OPEN_ID);
                            BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth(BmobUser.BmobThirdUserAuth.SNS_TYPE_QQ, token, expires, openId);
                            loginWithAuth(authInfo, onLoginForQQListener);
                        } catch (JSONException e) {
                        }
                    }
                }

                @Override
                public void onError(UiError uiError) {
                    onLoginForQQListener.loginFailed(uiError.toString());
                }

                @Override
                public void onCancel() {
                    onLoginForQQListener.loginCancel();
                }
            });
        }
    }

    //调用第三方登录  -- 暂时qq
    public void loginWithAuth(final BmobUser.BmobThirdUserAuth authInfo, final OnLoginForQQListener onLoginForQQListener) {
        BmobUser.loginWithAuthData(APP.getContext(), authInfo, new OtherLoginListener() {

            @Override
            public void onSuccess(JSONObject userAuth) {
                // TODO Auto-generated method stub
                LogUtils.i("smile", authInfo.getSnsType() + "登陆成功返回:" + userAuth);
                Gson gson = new Gson();
                onLoginForQQListener.loginSuccess(BmobUtils.getCurrentUser());
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                onLoginForQQListener.loginFailed(msg);
            }

        });
    }

}
