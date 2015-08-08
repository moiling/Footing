package team.far.footing.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.tauth.Tencent;

import team.far.footing.R;
import team.far.footing.app.APP;
import team.far.footing.app.BaseActivity;
import team.far.footing.model.bean.Userbean;
import team.far.footing.presenter.LoginPresenter;
import team.far.footing.ui.vu.ILoginVu;

/**
 * Created by Luoyy on 2015/8/7 0007.
 */
public class LoginActivity extends BaseActivity implements ILoginVu {

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        loginPresenter = new LoginPresenter(this);
    }

    @Override
    public String getUserName() {
        return "12345";
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public String getPassword() {
        return "12345";
    }

    @Override
    public void clearUserName() {

    }

    @Override
    public void clearPassword() {

    }

    @Override
    public void showLoginLoading() {

    }

    /**
     * @param userbean 当前登录成功的用户
     */
    @Override
    public void showLoginSuccee(Userbean userbean) {

    }

    @Override
    public void showLoginFail(String reason) {

    }

    @Override
    public void loginForQQ() {

    }

    //应用调用Andriod_SDK接口时，如果要成功接收到回调，
    // 需要在调用接口的Activity的onActivityResult方法中增加如下代码
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.createInstance(APP.getContext().getString(R.string.QQ_KEY), APP.getContext()).onActivityResult(requestCode, resultCode, data);
    }
}
