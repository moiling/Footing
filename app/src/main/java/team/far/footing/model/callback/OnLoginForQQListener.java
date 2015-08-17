package team.far.footing.model.callback;

import team.far.footing.model.bean.Userbean;

/**
 * Created by Luoyy on 2015/8/8 0008.
 */
public interface OnLoginForQQListener {

    void loginSuccess(Userbean userbean);

    void loginFailed(String reason);

    void loginCancel();
}
