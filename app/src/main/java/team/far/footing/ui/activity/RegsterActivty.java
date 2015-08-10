package team.far.footing.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import butterknife.ButterKnife;
import butterknife.InjectView;
import team.far.footing.R;
import team.far.footing.app.BaseActivity;
import team.far.footing.model.bean.Userbean;
import team.far.footing.presenter.RegisterPresenter;
import team.far.footing.ui.vu.IRegsterVu;

public class RegsterActivty extends BaseActivity implements IRegsterVu, View.OnClickListener {

    @InjectView(R.id.ed_register_user_name) AppCompatEditText edRegisterUserName;
    @InjectView(R.id.ed_register_password) AppCompatEditText edRegisterPassword;
    @InjectView(R.id.ed_register_password_repeat) AppCompatEditText edRegisterPasswordRepeat;
    @InjectView(R.id.btn_register) CardView btnRegister;
    @InjectView(R.id.toolbar) Toolbar mToolbar;
    @InjectView(R.id.ed_register_email) AppCompatEditText edRegisterEmail;
    private RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);
        initToolbar();
        userBarTint();
        //初始化 RegsterPresenter
        registerPresenter = new RegisterPresenter(this);
        init();
    }

    private void init() {
        edRegisterPasswordRepeat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (getPasswordRepeat().length() >= getPassword().length()) {
                    if (!getPassword().equals(getPasswordRepeat())) {
                        edRegisterPasswordRepeat.setError("两次输入的密码不同！");
                    }
                }
            }
        });
        btnRegister.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerPresenter.onRelieveView();
    }

    private void initToolbar() {
        mToolbar.setTitle(getResources().getString(R.string.register));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public String getUserName() {
        return edRegisterUserName.getText().toString();
    }

    @Override
    public String getPassword() {
        return edRegisterPassword.getText().toString();
    }

    @Override
    public String getPasswordRepeat() {
        return edRegisterPasswordRepeat.getText().toString();
    }

    @Override
    public String getEmail() {
        return edRegisterEmail.getText().toString();
    }

    @Override
    public void showRegsterLoading() {
        showProgress("注册中");
    }

    /**
     * @param userbean 当前注册成功的用户
     */
    @Override
    public void showRegsterSuccee(Userbean userbean) {
        dismissProgress();
        new MaterialDialog.Builder(this).title("注册成功").content("现在可以 登录了！").positiveText("好的").theme(Theme.LIGHT).callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public void showRegsterFail(String reason) {
        dismissProgress();
        new MaterialDialog.Builder(this).title("注册失败").content(reason).positiveText("好吧").theme(Theme.LIGHT).callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                //注册
                if ((getUserName().isEmpty() || getPassword().isEmpty() || getPasswordRepeat().isEmpty()) || getEmail().isEmpty()) {
                    Toast.makeText(this, "请填写完整呀！", Toast.LENGTH_SHORT).show();
                } else if (!getPassword().equals(getPasswordRepeat())) {
                    Toast.makeText(this, "两次密码都不一样！", Toast.LENGTH_SHORT).show();
                } else {
                    registerPresenter.Regster();
                }
                break;
        }
    }
}
