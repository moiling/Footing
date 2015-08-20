package team.far.footing.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import cn.bmob.v3.datatype.BmobFile;
import team.far.footing.model.IUserModel;
import team.far.footing.model.bean.Userbean;
import team.far.footing.model.callback.OnUploadListener;
import team.far.footing.model.impl.FileModel;
import team.far.footing.model.impl.UserModel;
import team.far.footing.ui.activity.EditUserInfoActivity;
import team.far.footing.ui.vu.IUserInfoVu;
import team.far.footing.util.BmobUtils;

/**
 * Created by moi on 2015/8/12.
 */
public class UserInfoPresenter {

    private IUserInfoVu v;
    private Userbean userbean;
    private IUserModel userModel;
    private FileModel fileModel;

    public UserInfoPresenter(final IUserInfoVu v) {
        this.v = v;
        userModel = UserModel.getInstance();
        fileModel = FileModel.getInstance();
        userbean = BmobUtils.getCurrentUser();
        showUserInformation();
    }

    public void updatePic(Uri uri) {
        v.showUpdateLoading();
        userModel.uploadHeadPortrait(uri.getPath(), new OnUploadListener() {
            @Override
            public void onSuccess(String fileName, String url, BmobFile file) {
                v.dismissLoading();
                v.showUpdateSuccess();
                fileModel.downloadPic(fileName, new com.bmob.btp.callback.DownloadListener() {

                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        v.showUserPic(BitmapFactory.decodeFile(s));
                    }

                    @Override
                    public void onProgress(String s, int i) {

                    }
                });
            }

            @Override
            public void onProgress(int progress) {

            }

            @Override
            public void onError(int statuscode, String errormsg) {
                v.dismissLoading();
                v.showUpdateFailed(statuscode);
            }
        });
    }

    public void refreshUserInformation() {
        userbean = BmobUtils.getCurrentUser();
        showUserInformation();
    }

    public void showUserInformation() {
        Bitmap bitmap = fileModel.getLocalPic(userbean.getHeadPortraitFileName());
        v.showUserInformation(userbean, bitmap);
    }

    public void startEditUserInfoActivity(Context context) {
        Intent intent = new Intent(context, EditUserInfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }

    // 解除view的绑定
    public void onRelieveView() {
        v = null;
    }

}
