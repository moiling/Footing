package team.far.footing.ui.vu;

import android.content.Context;

import team.far.footing.model.bean.Userbean;

/**
 * Created by Luoyy on 2015/8/7 0007.
 */
public interface IRegsterVu {

    Context getContext();

    String getUserName();

    String getPassword();

    //正在注册
    void showRegsterLoading();

    //注册成功
    void showRegsterSuccee(Userbean userbean);

    //注册失败
    void showRegsterFail(String reason);
}
