package team.far.footing.model;

import android.app.Activity;
import android.content.Context;

import team.far.footing.model.Listener.OnLoginForQQListener;
import team.far.footing.model.Listener.OnLoginListener;
import team.far.footing.model.Listener.OnRegsterListener;

/**
 * Created by moi on 2015/8/7.
 */
public interface IUserModel {
    void Login(String username, String passwrod, OnLoginListener onLoginListener, Context context);

    void Regster(String username, String passwrod, OnRegsterListener onRegsterListener, Context context);

    void loginForQQ(Activity activity, OnLoginForQQListener onLoginForQQListener);
}
