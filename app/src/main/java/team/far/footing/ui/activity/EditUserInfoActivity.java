package team.far.footing.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import butterknife.ButterKnife;
import butterknife.InjectView;
import team.far.footing.R;
import team.far.footing.app.BaseActivity;
import team.far.footing.model.bean.Userbean;
import team.far.footing.presenter.EditUserInfoPresenter;
import team.far.footing.ui.vu.IEditUserInfoVu;

public class EditUserInfoActivity extends BaseActivity implements IEditUserInfoVu, Toolbar.OnMenuItemClickListener, View.OnClickListener {

    @InjectView(R.id.toolbar) Toolbar mToolbar;
    @InjectView(R.id.tv_edit_nick_name) TextView editNickName;
    @InjectView(R.id.tv_edit_signature) TextView editSignature;
    @InjectView(R.id.tv_edit_email) TextView editEmail;
    @InjectView(R.id.btn_edit_nick_name) LinearLayout btnEditNickName;
    @InjectView(R.id.btn_edit_signature) LinearLayout btnEditSignature;
    @InjectView(R.id.btn_edit_email) LinearLayout btnEditEmail;
    private EditUserInfoPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        ButterKnife.inject(this);

        presenter = new EditUserInfoPresenter(this);
        initToolbar();
        init();
    }

    private void init() {
        btnEditNickName.setOnClickListener(this);
        btnEditSignature.setOnClickListener(this);
        btnEditEmail.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onRelieveView();
    }

    private void initToolbar() {
        mToolbar.setTitle(getResources().getString(R.string.edit_info));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_user_info, menu);
        return true;
    }

    @Override
    public String getNickName() {
        return editNickName.getText().toString();
    }

    @Override
    public String getEmail() {
        return editEmail.getText().toString();
    }

    @Override
    public String getSignature() {
        return editSignature.getText().toString();
    }

    @Override
    public void showUserInformation(Userbean userbean) {
        if (!(userbean.getNickName() == null)) {
            editNickName.setText(userbean.getNickName());
        } else {
            editNickName.setText("未填写昵称");
        }
        if (!(userbean.getSignature() == null)) {
            editSignature.setText(userbean.getSignature());
        } else {
            editSignature.setText("未填写签名");
        }
        if (!(userbean.getEmail() == null)) {
            editEmail.setText(userbean.getEmail());
        } else {
            editEmail.setText("未填写邮箱");
        }
    }

    @Override
    public void showEditLoading() {
        showProgress("正在提交");
    }

    @Override
    public void showEditSuccee() {
        dismissProgress();
        new MaterialDialog.Builder(this).title("修改成功").content("已经更新了你的资料哦~").positiveText("好的")
                .theme(Theme.LIGHT).callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public void showEditFail(String reason) {
        new MaterialDialog.Builder(this).title("提交失败").content(reason).positiveText("好的")
                .theme(Theme.LIGHT).callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_editor_complete:
                presenter.updateUserInformation();
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_edit_nick_name:
                new MaterialDialog.Builder(this).title("修改昵称").theme(Theme.LIGHT)
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .input("请输入要修改的昵称", editNickName.getText().toString(), new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                editNickName.setText(input);
                                dialog.dismiss();
                            }
                        }).show();
                break;
            case R.id.btn_edit_email:
                new MaterialDialog.Builder(this).title("修改邮箱").theme(Theme.LIGHT)
                        .inputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                        .input("请输入要修改的邮箱", editEmail.getText().toString(), new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                editEmail.setText(input);
                                dialog.dismiss();
                            }
                        }).show();
                break;
            case R.id.btn_edit_signature:
                new MaterialDialog.Builder(this).title("修改签名").theme(Theme.LIGHT)
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .input("请输入要修改的签名", editSignature.getText().toString(), new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                editSignature.setText(input);
                                dialog.dismiss();
                            }
                        }).show();
                break;
        }
    }
}
