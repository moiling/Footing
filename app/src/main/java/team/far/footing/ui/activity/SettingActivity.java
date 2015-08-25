package team.far.footing.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.balysv.materialripple.MaterialRippleLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import team.far.footing.R;
import team.far.footing.app.APP;
import team.far.footing.app.ActivityCollector;
import team.far.footing.app.BaseActivity;
import team.far.footing.presenter.SettingPresenter;
import team.far.footing.ui.vu.ISettingVu;
import team.far.footing.util.BmobUtils;
import team.far.footing.util.LogUtils;
import team.far.footing.util.SPUtils;
import team.far.footing.util.SPuntils;

public class SettingActivity extends BaseActivity implements ISettingVu {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.btn_setting_logout)
    MaterialRippleLayout btnLogout;
    @InjectView(R.id.btn_setting_GPS_check)
    MaterialRippleLayout btnSettingGPSCheck;
    @InjectView(R.id.btn_setting_clean_cash)
    MaterialRippleLayout btnSettingCleanCash;
    @InjectView(R.id.btn_setting_allow_message)
    MaterialRippleLayout btnSettingAllowMessage;

    private SettingPresenter settingPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.inject(this);
        initToolbar();
        init();
        settingPresenter = new SettingPresenter(this);
    }

    private void init() {
        btnSettingGPSCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnSettingAllowMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.e("点击btn_setting_allow_message");
                showPush();
            }
        });
        btnSettingCleanCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.e("点击btn_setting_clean_cash");
                settingPresenter.cachesize();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.put(APP.getContext(), "isLogin", Boolean.FALSE);
                BmobUtils.LogOutUser();
                Intent intent = new Intent(APP.getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(intent);
                ActivityCollector.finishActivitis();
            }
        });
    }

    private void initToolbar() {
        mToolbar.setTitle("应用设置");
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
    public void showGPS() {


    }

    @Override
    public void showCache(String cachesize) {

        final me.drakeet.materialdialog.MaterialDialog materialDialog = new me.drakeet.materialdialog.MaterialDialog(this);
        materialDialog.setTitle("清理缓存");
        materialDialog.setMessage("缓存大小：" + cachesize + "\n" + "确定清除缓存？");
        materialDialog.setPositiveButton("清除", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingPresenter.cleancache();
                materialDialog.dismiss();
            }
        });

        materialDialog.setNegativeButton("下次吧", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDialog.dismiss();
            }
        });
        materialDialog.show();

    }

    @Override
    public void showPush() {
        final me.drakeet.materialdialog.MaterialDialog materialDialog = new me.drakeet.materialdialog.MaterialDialog(this);
        materialDialog.setTitle("推送设置");
        materialDialog.setMessage("你确定不接受推送了吗？" + "\n" + "推送可是有惊喜的哟！");
        materialDialog.setPositiveButton("接受", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPuntils.setAllow(true);
                materialDialog.dismiss();
            }
        });

        materialDialog.setNegativeButton("不接受", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPuntils.setAllow(false);
                materialDialog.dismiss();
            }
        });
        materialDialog.show();
    }


}
