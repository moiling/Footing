package team.far.footing.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.tencent.tauth.Tencent;

import butterknife.ButterKnife;
import butterknife.InjectView;
import team.far.footing.R;
import team.far.footing.app.APP;
import team.far.footing.app.BaseActivity;
import team.far.footing.model.bean.Userbean;
import team.far.footing.presenter.LoginPresenter;
import team.far.footing.ui.vu.ILoginVu;
import team.far.footing.util.MIUIV6;

/**
 * Created by Luoyy on 2015/8/7 0007.
 */
public class LoginActivity extends BaseActivity implements ILoginVu, View.OnClickListener {

    @InjectView(R.id.ed_login_user_name) AppCompatEditText edUserName;
    @InjectView(R.id.ed_login_password) AppCompatEditText edPassword;
    @InjectView(R.id.btn_login_login) CardView btnLogin;
    @InjectView(R.id.layout_login) LinearLayout layoutLogin;
    @InjectView(R.id.btn_qq_login) CardView btnQQLogin;
    @InjectView(R.id.btn_register) CardView btnRegister;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        loginPresenter = new LoginPresenter(this);

        setBarTintColor(getResources().getColor(R.color.white));
        MIUIV6.setStatusBarTextColor(this, 1);

        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.onRelieveView();
    }

    private void init() {
        btnLogin.setOnClickListener(this);
        btnQQLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public String getUserName() {
        return edUserName.getText().toString();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public String getPassword() {
        return edPassword.getText().toString();
    }

    @Override
    public void clearUserName() {
        edUserName.setText("");
    }

    @Override
    public void clearPassword() {
        edPassword.setText("");
    }

    @Override
    public void showLoginLoading() {
        showProgress("登陆中");
    }

    /**
     * @param userbean 当前登录成功的用户
     */
    @Override
    public void showLoginSuccee(Userbean userbean) {
        dismissProgress();
        new MaterialDialog.Builder(this).title("登陆成功").content("用户名：" + userbean.getUsername()).positiveText("了解了").theme(Theme.LIGHT).callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public void showLoginFail(String reason) {
        dismissProgress();
        new MaterialDialog.Builder(this).title("登陆失败").content(reason).positiveText("了解了").theme(Theme.LIGHT).callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public void loginForQQ() {
        loginPresenter.LoginForQQ();
    }

    //应用调用Andriod_SDK接口时，如果要成功接收到回调，
    // 需要在调用接口的Activity的onActivityResult方法中增加如下代码
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.createInstance(APP.getContext().getString(R.string.QQ_KEY), APP.getContext()).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_login:
                if (getUserName().isEmpty()) {
                    Toast.makeText(this, "用户名都不填", Toast.LENGTH_SHORT).show();
                } else if (getPassword().isEmpty()) {
                    Toast.makeText(this, "你以为，不要密码也能进？！", Toast.LENGTH_SHORT).show();
                } else {
                    loginPresenter.Login();
                }
                break;
            case R.id.btn_qq_login:
                loginForQQ();
                break;
            case R.id.btn_register:
                loginPresenter.startRegisterActivity(this);
                break;
        }
    }
}
