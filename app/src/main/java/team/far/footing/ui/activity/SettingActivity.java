package team.far.footing.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
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
import team.far.footing.ui.vu.ISettingVu;
import team.far.footing.util.BmobUtils;
import team.far.footing.util.GPSUtils;
import team.far.footing.util.SPUtils;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.inject(this);

        initToolbar();
        init();
    }

    private void init() {
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
        btnSettingGPSCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!GPSUtils.isGpsEnable()) {
                    new MaterialDialog.Builder(SettingActivity.this).title("未开启GPS").content("GPS没有开启哦")
                            .backgroundColor(getResources().getColor(R.color.white))
                            .positiveText("去开启").negativeText("不了").negativeColor(getResources().getColor(R.color.divider_color)).theme(Theme.LIGHT)
                            .callback(new MaterialDialog.ButtonCallback() {
                                @Override
                                public void onPositive(MaterialDialog dialog) {
                                    super.onPositive(dialog);
                                    Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
                                    startActivityForResult(intent, 0);
                                }
                            }).show();
                } else {
                    new MaterialDialog.Builder(SettingActivity.this).title("已开启GPS").content("GPS可以正常使用")
                            .backgroundColor(getResources().getColor(R.color.white))
                            .positiveText("好的").theme(Theme.LIGHT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0){
            if (!GPSUtils.isGpsEnable()) {
                new MaterialDialog.Builder(SettingActivity.this).title("未开启GPS").content("GPS还是没开启哦")
                        .backgroundColor(getResources().getColor(R.color.white))
                        .positiveText("继续去开启").negativeText("不了").negativeColor(getResources().getColor(R.color.divider_color)).theme(Theme.LIGHT)
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                super.onPositive(dialog);
                                Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
                                startActivityForResult(intent, 0);
                            }
                        }).show();
            }  else {
                new MaterialDialog.Builder(SettingActivity.this).title("已开启GPS").content("GPS可以正常使用")
                        .backgroundColor(getResources().getColor(R.color.white))
                        .positiveText("好的").theme(Theme.LIGHT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
        new MaterialDialog.Builder(this)
                .title("清理缓存").
                content("缓存大小：" + cachesize + "\n" + "确定清除缓存？")
                .backgroundColor(getResources().getColor(R.color.white))
                .theme(Theme.LIGHT)
                .positiveText("知道了")
                .show();
    }

    @Override
    public void showPush() {

    }
}
