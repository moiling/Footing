package team.far.footing.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;

import butterknife.ButterKnife;
import butterknife.InjectView;
import team.far.footing.R;
import team.far.footing.app.BaseActivity;
import team.far.footing.model.bean.Userbean;
import team.far.footing.presenter.UserInfoPresenter;
import team.far.footing.ui.vu.IUserInfoVu;
import team.far.footing.ui.widget.CircleImageView;
import team.far.footing.util.BmobUtils;
import team.far.footing.util.ScreenUtils;

public class UserInfoActivity extends BaseActivity implements IUserInfoVu, Toolbar.OnMenuItemClickListener, View.OnClickListener {

    @InjectView(R.id.toolbar_t) Toolbar mToolbar;
    @InjectView(R.id.user_info_bar) FrameLayout barLayout;
    @InjectView(R.id.iv_user_info_user_pic) CircleImageView mUserPic;
    @InjectView(R.id.tv_user_info_user_lv) TextView mUserLv;
    @InjectView(R.id.tv_user_info_user_name) TextView mUserName;
    @InjectView(R.id.tv_user_info_user_signature) TextView mUserSignature;
    @InjectView(R.id.btn_user_info_camera) Button btnCamera;
    @InjectView(R.id.btn_user_info_photo) Button btnPhoto;

    private UserInfoPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.inject(this);
        noUseBarTint();
        initToolbar();

        init();
        presenter = new UserInfoPresenter(this);
    }

    private void init() {
        btnCamera.setOnClickListener(this);
        btnPhoto.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.refreshUserInformation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.cropDestroy();
        presenter.onRelieveView();
    }

    private void initToolbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            barLayout.setPadding(0, ScreenUtils.getStatusHeight(this), 0, 0);
        mToolbar.setTitle("");
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
        getMenuInflater().inflate(R.menu.menu_user_info, menu);
        return true;
    }

    @Override
    public void showUserInformation(Userbean userbean, Bitmap bitmap) {
        if (!(userbean.getNickName() == null)) {
            mUserName.setText(userbean.getNickName());
        } else {
            mUserName.setText("未取名");
        }
        if (bitmap != null) {
            mUserPic.setImageBitmap(bitmap);
        }
        mUserLv.setText("Lv." + userbean.getLevel());
        mUserSignature.setText(userbean.getSignature());
    }

    @Override
    public void showUserPic(Bitmap bitmap) {
        if (bitmap != null) {
            mUserPic.setImageBitmap(bitmap);
        }
    }

    @Override
    public void showUpdateLoading() {
        showProgress("正在上传");
    }

    @Override
    public void showUpdateSuccess() {
        new MaterialDialog.Builder(this).title("上传成功").content("").positiveText("好的").theme(Theme.LIGHT).callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public void showUpdateFailed(int i) {
        new MaterialDialog.Builder(this).title("上传失败").content(BmobUtils.searchCode(i)).positiveText("好的").theme(Theme.LIGHT).callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                dialog.dismiss();
            }
        }).show();

    }

    @Override
    public void dismissLoading() {
        dismissProgress();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_editor:
                presenter.startEditUserInfoActivity(this);
                break;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CropHelper.handleResult(presenter.getCropHandler(), requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_user_info_camera:
                Intent intent = CropHelper.buildCaptureIntent(new CropParams().uri);
                startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
                break;
            case R.id.btn_user_info_photo:
                Intent intent2 = CropHelper.buildCropFromGalleryIntent(new CropParams());
                startActivityForResult(intent2, CropHelper.REQUEST_CROP);
                break;

        }
    }
}
