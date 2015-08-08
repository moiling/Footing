package team.far.footing.ui.vu;

import android.app.Activity;
import team.far.footing.model.bean.Userbean;

/**
 * Created by moi on 2015/8/7.
 */
public interface ILoginVu {

    String getUserName();
    Activity getActivity();

    String getPassword();

    void clearUserName();

    void clearPassword();

    void showLoginLoading();

    void showLoginSuccee(Userbean userbean);

    void showLoginFail(String reason);
    void showLogincancel();

    void loginForQQ();
}
