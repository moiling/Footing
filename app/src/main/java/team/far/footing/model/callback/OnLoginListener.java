package team.far.footing.model.callback;

import team.far.footing.model.bean.Userbean;

/**
 * Created by Luoyy on 2015/8/7 0007.
 */
public interface OnLoginListener {
    void loginSuccess(Userbean userbean);

    void loginFailed(String reason);
}
