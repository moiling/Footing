package team.far.footing.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;

import butterknife.ButterKnife;
import butterknife.InjectView;
import team.far.footing.R;
import team.far.footing.app.BaseActivity;
import team.far.footing.model.bean.Userbean;
import team.far.footing.presenter.EditUserInfoPresenter;
import team.far.footing.ui.vu.IEditUserInfoVu;
import team.far.footing.ui.widget.CircleImageView;
import team.far.footing.util.BmobUtils;

public class EditUserInfoActivity extends BaseActivity implements IEditUserInfoVu, Toolbar.OnMenuItemClickListener, View.OnClickListener, CropHandler {

    @InjectView(R.id.toolbar) Toolbar mToolbar;
    @InjectView(R.id.tv_edit_nick_name) TextView editNickName;
    @InjectView(R.id.tv_edit_signature) TextView editSignature;
    @InjectView(R.id.btn_edit_nick_name) LinearLayout btnEditNickName;
    @InjectView(R.id.btn_edit_signature) LinearLayout btnEditSignature;
    @InjectView(R.id.iv_edit_user_pic) CircleImageView ivEditUserPic;
    @InjectView(R.id.btn_edit_user_pic) RelativeLayout btnEditUserPic;
    private EditUserInfoPresenter presenter;
    private CropParams mCropParams = new CropParams();
    private boolean isEdited = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        ButterKnife.inject(this);
        presenter = new EditUserInfoPresenter(this);
        initToolbar();
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.refreshUserInformation();
    }

    private void init() {
        btnEditNickName.setOnClickListener(this);
        btnEditSignature.setOnClickListener(this);
        btnEditUserPic.setOnClickListener(this);
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
                if (isEdited) {
                    new MaterialDialog.Builder(EditUserInfoActivity.this).theme(Theme.LIGHT).title("放弃修改").content("存在未保存的修改信息，是否放弃本次修改？").positiveText("放弃").negativeText("继续修改").callback(new MaterialDialog.ButtonCallback() {
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
    public String getSignature() {
        return editSignature.getText().toString();
    }

    @Override
    public void showUserInformation(Userbean userbean, Bitmap bitmap) {
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
        showUserPic(bitmap);
    }

    @Override
    public void showEditLoading() {
        showProgress("正在提交");
    }

    @Override
    public void showEditSuccee() {
        dismissProgress();
        isEdited = false;
        new MaterialDialog.Builder(this).title("修改成功").content("已经更新了你的资料哦~").positiveText("好的")
                .theme(Theme.LIGHT).callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public void showUpdatePicLoading() {
        showProgress("正在上传");
    }

    @Override
    public void showUpdatePicSuccess() {
        new MaterialDialog.Builder(this).title("上传成功").content("").positiveText("好的").theme(Theme.LIGHT).callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public void showUpdatePicFailed(int i) {
        new MaterialDialog.Builder(this).title("上传失败").content(BmobUtils.searchCode(i)).positiveText("好的").theme(Theme.LIGHT).callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                dialog.dismiss();
            }
        }).show();

    }

    @Override
    public void dismissPicLoading() {
        dismissProgress();
    }

    @Override
    public void showUserPic(Bitmap bitmap) {
        if (bitmap != null) {
            ivEditUserPic.setImageBitmap(bitmap);
        }
    }

    @Override
    public void showEditFail(int i) {
        dismissProgress();
        new MaterialDialog.Builder(this).title("提交失败").content(BmobUtils.searchCode(i)).positiveText("好的")
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
                                isEdited = true;
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
                                isEdited = true;
                                dialog.dismiss();
                            }
                        }).show();
                break;
            case R.id.btn_edit_user_pic:
                new MaterialDialog.Builder(this)
                        .theme(Theme.LIGHT)
                        .items(new String[]{"图片上传", "拍照上传"})
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                switch (which) {
                                    case 0:
                                        Intent intent2 = CropHelper.buildCropFromGalleryIntent(new CropParams());
                                        CropHelper.clearCachedCropFile(mCropParams.uri);
                                        startActivityForResult(intent2, CropHelper.REQUEST_CROP);
                                        break;
                                    case 1:
                                        Intent intent = CropHelper.buildCaptureIntent(new CropParams().uri);
                                        startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
                                        break;
                                }
                            }
                        })
                        .show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CropHelper.handleResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onPhotoCropped(Uri uri) {
        presenter.updatePic(uri);
    }

    @Override
    public void onCropCancel() {

    }

    @Override
    public void onCropFailed(String s) {

    }

    @Override
    public CropParams getCropParams() {
        return mCropParams;
    }

    @Override
    public Activity getContext() {
        return getContext();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            if (isEdited) {
                new MaterialDialog.Builder(this).theme(Theme.LIGHT).title("放弃修改").backgroundColor(getResources().getColor(R.color.white)).content("存在未保存的修改信息，是否放弃本次修改？").positiveText("放弃").negativeText("继续修改").callback(new MaterialDialog.ButtonCallback() {
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
}
