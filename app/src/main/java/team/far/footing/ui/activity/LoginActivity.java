package team.far.footing.ui.activity;

import android.content.Context;
import android.os.Bundle;

import team.far.footing.app.BaseActivity;
import team.far.footing.model.bean.Userbean;
import team.far.footing.ui.vu.ILoginVu;

/**
 * Created by Luoyy on 2015/8/7 0007.
 */
public class LoginActivity extends BaseActivity implements ILoginVu {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public String getUserName() {
        return "12345";
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

    @Override
    public void showLoginSuccee(Userbean userbean) {

    }

    @Override
    public void showLoginFail(String reason) {

    }
}
