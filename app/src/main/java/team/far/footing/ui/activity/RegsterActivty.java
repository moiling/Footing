package team.far.footing.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import team.far.footing.R;
import team.far.footing.app.BaseActivity;
import team.far.footing.model.bean.Userbean;
import team.far.footing.presenter.RegisterPresenter;
import team.far.footing.ui.vu.IRegsterVu;
import team.far.footing.util.LogUtils;

public class RegsterActivty extends BaseActivity implements IRegsterVu {


    private RegisterPresenter registerPresenter;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        initToolbar();
        //初始化 RegsterPresenter
        registerPresenter = new RegisterPresenter(this);
        //注册
        registerPresenter.Regster();
    }

    private void initToolbar() {
        mToolbar.setTitle(getResources().getString(R.string.login));
        setSupportActionBar(mToolbar);
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
    public void showRegsterLoading() {

    }

    /**
     * @param userbean 当前注册成功的用户
     */
    @Override
    public void showRegsterSuccee(Userbean userbean) {
        LogUtils.i("在LoginActivity中", "注册成功");
    }

    @Override
    public void showRegsterFail(String reason) {
        LogUtils.e("在LoginActivity中",reason);
    }
}
