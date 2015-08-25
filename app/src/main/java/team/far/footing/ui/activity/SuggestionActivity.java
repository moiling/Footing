package team.far.footing.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.balysv.materialripple.MaterialRippleLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import team.far.footing.R;
import team.far.footing.app.BaseActivity;
import team.far.footing.presenter.SuggestionPresenter;
import team.far.footing.ui.vu.ISuggestionVu;
import team.far.footing.util.BmobUtils;

public class SuggestionActivity extends BaseActivity implements ISuggestionVu {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.ed_suggestion)
    EditText mSuggestion;
    @InjectView(R.id.btn_suggestion_send)
    MaterialRippleLayout mSend;
    private SuggestionPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        ButterKnife.inject(this);
        presenter = new SuggestionPresenter(this);
        initToolbar();
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onRelieveView();
    }

    private void init() {
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mSuggestion.getText().toString().isEmpty()) {
                    // 传suggestion内容和用户名（这样可以把信息反馈给用户）
                    presenter.sendSuggestion(mSuggestion.getText().toString() + "--From: " + BmobUtils.getCurrentUser().getUsername());
                } else {
                    Toast.makeText(SuggestionActivity.this, "你还没开始输入哦！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initToolbar() {
        mToolbar.setTitle("意见反馈");
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
    public void onSendProgress() {
        showProgress("发送中");
    }

    @Override
    public void onSendSuccess() {
        dismissProgress();
        new MaterialDialog.Builder(this).title("发送成功").content("我们已经收到你的信息了哦").backgroundColor(getResources().getColor(R.color.white)).theme(Theme.LIGHT).positiveText("知道了").show();
    }

    @Override
    public void onSendFail(int i) {
        dismissProgress();
        new MaterialDialog.Builder(this).title("发送失败").content(BmobUtils.searchCode(i)).backgroundColor(getResources().getColor(R.color.white)).theme(Theme.LIGHT).positiveText("知道了").show();
    }
}
