package team.far.footing.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.balysv.materialripple.MaterialRippleLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import team.far.footing.R;
import team.far.footing.app.APP;
import team.far.footing.app.ActivityCollector;
import team.far.footing.app.BaseActivity;
import team.far.footing.util.BmobUtils;
import team.far.footing.util.SPUtils;

public class SettingActivity extends BaseActivity {

    @InjectView(R.id.toolbar) Toolbar mToolbar;
    @InjectView(R.id.btn_setting_logout) MaterialRippleLayout btnLogout;
    @InjectView(R.id.btn_setting_GPS_check) MaterialRippleLayout btnSettingGPSCheck;
    @InjectView(R.id.btn_setting_clean_cash) MaterialRippleLayout btnSettingCleanCash;
    @InjectView(R.id.btn_setting_allow_message) MaterialRippleLayout btnSettingAllowMessage;

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

}
