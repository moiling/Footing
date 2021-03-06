package team.far.footing.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
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
import team.far.footing.util.BmobUtils;
import team.far.footing.util.LogUtils;

public class RegisterActivty extends BaseActivity implements IRegsterVu {

    @InjectView(R.id.ed_register_user_name) AppCompatEditText edRegisterUserName;
    @InjectView(R.id.ed_register_password) AppCompatEditText edRegisterPassword;
    @InjectView(R.id.ed_register_password_repeat) AppCompatEditText edRegisterPasswordRepeat;
    @InjectView(R.id.toolbar) Toolbar mToolbar;
    @InjectView(R.id.ed_register_email) AppCompatEditText edRegisterEmail;
    private RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);
        initToolbar();
        //初始化 RegisterPresenter
        registerPresenter = new RegisterPresenter(this);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
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
        edRegisterEmail.setOnKeyListener(onKey);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerPresenter.onRelieveView();
    }

    private void register() {
        if ((getUserName().isEmpty() || getPassword().isEmpty() || getPasswordRepeat().isEmpty()) || getEmail().isEmpty()) {
            Toast.makeText(RegisterActivty.this, "请填写完整呀！", Toast.LENGTH_SHORT).show();
        } else if (!getPassword().equals(getPasswordRepeat())) {
            Toast.makeText(RegisterActivty.this, "两次密码都不一样！", Toast.LENGTH_SHORT).show();
        } else {
            registerPresenter.Regster();
        }
    }

    private void initToolbar() {
        mToolbar.setTitle(getResources().getString(R.string.register));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edRegisterEmail.getText().toString().isEmpty() ||
                        !edRegisterPassword.getText().toString().isEmpty() ||
                        !edRegisterPasswordRepeat.getText().toString().isEmpty() ||
                        !edRegisterUserName.getText().toString().isEmpty()) {
                    new MaterialDialog.Builder(RegisterActivty.this).theme(Theme.LIGHT).title("放弃注册").backgroundColor(getResources().getColor(R.color.white)).content("是否放弃注册？").positiveText("放弃").negativeText("继续注册").callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            super.onPositive(dialog);
                            finish();
                        }
                    }).show();
                } else {
                    finish();
                }
            }
        });
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                register();
                return false;
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
        new MaterialDialog.Builder(this).title("注册成功").content("是否直接登录").positiveText("登录").negativeText("不了").theme(Theme.LIGHT).callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                registerPresenter.Login();
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public void showRegsterFail(int i) {
        dismissProgress();
        new MaterialDialog.Builder(this).title("注册失败").content(BmobUtils.searchCode(i)).positiveText("好吧").theme(Theme.LIGHT).callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                dialog.dismiss();
            }
        }).show();
    }

    private View.OnKeyListener onKey = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                register();
            }
            return false;
        }
    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            if (!edRegisterEmail.getText().toString().isEmpty() ||
                    !edRegisterPassword.getText().toString().isEmpty() ||
                    !edRegisterPasswordRepeat.getText().toString().isEmpty() ||
                    !edRegisterUserName.getText().toString().isEmpty()) {
                new MaterialDialog.Builder(this).theme(Theme.LIGHT).title("放弃注册").backgroundColor(getResources().getColor(R.color.white)).content("是否放弃注册？").positiveText("放弃").negativeText("继续注册").callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        finish();
                    }
                }).show();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * @param userbean 当前登录成功的用户
     */
    @Override
    public void showLoginSuccee(Userbean userbean) {
        dismissProgress();
        registerPresenter.startHomeActivity(this);
        // 进入了主页之后不应该能够返回到登陆页面
        finish();
    }

    @Override
    public void showLoginFail(int i, String s) {
        dismissProgress();
        new MaterialDialog.Builder(this).title("登录失败").content(BmobUtils.searchCode(i)).positiveText("好的").theme(Theme.LIGHT).callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                dialog.dismiss();
            }
        }).show();
    }
}
