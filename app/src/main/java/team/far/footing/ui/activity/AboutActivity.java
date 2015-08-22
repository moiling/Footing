package team.far.footing.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import team.far.footing.R;
import team.far.footing.app.BaseActivity;
import team.far.footing.util.AppUtils;

public class AboutActivity extends BaseActivity {

    @InjectView(R.id.toolbar) Toolbar mToolbar;
    @InjectView(R.id.tv_about_version) TextView mVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.inject(this);
        initToolbar();
        init();
    }

    private void init() {
        mVersion.setText("Ver." + AppUtils.getVersionName(this));
    }

    private void initToolbar() {
        mToolbar.setTitle("关于足下");
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
