package team.far.footing.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;

import java.text.DecimalFormat;

import butterknife.ButterKnife;
import butterknife.InjectView;
import team.far.footing.R;
import team.far.footing.app.BaseActivity;
import team.far.footing.model.bean.Userbean;
import team.far.footing.presenter.UserInfoPresenter;
import team.far.footing.ui.vu.IUserInfoVu;
import team.far.footing.ui.widget.CircleImageView;
import team.far.footing.util.BmobUtils;
import team.far.footing.util.LevelUtils;
import team.far.footing.util.LogUtils;
import team.far.footing.util.ScreenUtils;
import team.far.footing.util.TimeUtils;

public class UserInfoActivity extends BaseActivity implements IUserInfoVu, Toolbar.OnMenuItemClickListener, View.OnClickListener, CropHandler {

    @InjectView(R.id.toolbar_t)
    Toolbar mToolbar;
    @InjectView(R.id.user_info_bar)
    FrameLayout barLayout;
    @InjectView(R.id.iv_user_info_user_pic)
    CircleImageView mUserPic;
    @InjectView(R.id.tv_user_info_user_lv)
    TextView mUserLv;
    @InjectView(R.id.tv_user_info_user_name)
    TextView mUserName;
    @InjectView(R.id.tv_user_info_user_signature)
    TextView mUserSignature;
    @InjectView(R.id.tv_my_today_distance)
    TextView tvMyTodayDistance;
    @InjectView(R.id.tv_my_all_distance)
    TextView tvMyAllDistance;
    @InjectView(R.id.tv_my_email)
    TextView tvMyEmail;
    @InjectView(R.id.tv_my_exp)
    TextView tvMyExp;
    @InjectView(R.id.btn_my_signature)
    RelativeLayout btnMySignature;

    private UserInfoPresenter presenter;
    private CropParams mCropParams = new CropParams();

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

    public void cropDestroy() {
        if (this.getCropParams() != null)
            CropHelper.clearCachedCropFile(this.getCropParams().uri);
    }

    private void init() {
        btnMySignature.setOnClickListener(this);
        mUserPic.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.refreshUserInformation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cropDestroy();
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
        mUserLv.setText("Lv." + LevelUtils.getLevel(userbean.getLevel()));
        mUserSignature.setText(userbean.getSignature());
        if (TimeUtils.isToday(userbean.getToday_date())) {
            if (userbean.getToday_distance() != null) {
                if (userbean.getToday_distance() > 1000) {
                    tvMyTodayDistance.setText(new DecimalFormat(".##").format(userbean.getToday_distance() / 1000.0) + " km");
                } else {
                    tvMyTodayDistance.setText(userbean.getToday_distance() + " m");
                }
            } else {
                tvMyTodayDistance.setText("0 m");
            }
        } else {
            tvMyTodayDistance.setText("0 m");
        }
        if (userbean.getAll_distance() > 1000) {
            tvMyAllDistance.setText(new DecimalFormat(".##").format(userbean.getAll_distance() / 1000.0) + " km");
        } else {
            tvMyAllDistance.setText(userbean.getAll_distance() + " m");
        }
        LogUtils.d("下一等级：" + (LevelUtils.getLevel(userbean.getLevel()) + 1) + "下一等级经验： " + (LevelUtils.getLevel(userbean.getLevel()) + 1) * (LevelUtils.getLevel(userbean.getLevel()) + 1) * 200 + 40);
        tvMyExp.setText(userbean.getLevel() + " / " + ((LevelUtils.getLevel(userbean.getLevel()) + 1) * (LevelUtils.getLevel(userbean.getLevel()) + 1) * 200 + 40));
        tvMyEmail.setText(userbean.getEmail());
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
        new MaterialDialog.Builder(this).title("上传成功").backgroundColor(getResources().getColor(R.color.white)).content("").positiveText("好的").theme(Theme.LIGHT).callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public void showUpdateFailed(int i) {
        new MaterialDialog.Builder(this).title("上传失败").backgroundColor(getResources().getColor(R.color.white)).content(BmobUtils.searchCode(i)).positiveText("好的").theme(Theme.LIGHT).callback(new MaterialDialog.ButtonCallback() {
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
        CropHelper.handleResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_user_info_user_pic:
                new MaterialDialog.Builder(this)
                        .theme(Theme.LIGHT)
                        .items(new String[]{"图片上传", "拍照上传"})
                        .backgroundColor(getResources().getColor(R.color.white))
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
            case R.id.btn_my_signature:
                if (mUserSignature.getText() != null) {
                    new MaterialDialog.Builder(this).backgroundColor(getResources().getColor(R.color.white)).content(mUserSignature.getText()).theme(Theme.LIGHT).show();
                }
                break;
        }
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
}
