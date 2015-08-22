package team.far.footing.presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import cn.bmob.v3.datatype.BmobFile;
import team.far.footing.model.IUserModel;
import team.far.footing.model.bean.Userbean;
import team.far.footing.model.callback.OnUpdateUserListener;
import team.far.footing.model.callback.OnUploadListener;
import team.far.footing.model.impl.FileModel;
import team.far.footing.model.impl.UserModel;
import team.far.footing.ui.vu.IEditUserInfoVu;
import team.far.footing.util.BmobUtils;

/**
 * Created by moi on 2015/8/12.
 */
public class EditUserInfoPresenter {

    private IUserModel userModel;
    private IEditUserInfoVu v;
    private Userbean userbean;
    private FileModel fileModel;

    public EditUserInfoPresenter(IEditUserInfoVu v) {
        this.v = v;
        userModel = UserModel.getInstance();
        fileModel = FileModel.getInstance();
        userbean = BmobUtils.getCurrentUser();
        showUserInformation();
    }

    public void updateUserInformation() {
        v.showEditLoading();
        userModel.updataUserInfo(v.getNickName(), v.getSignature(), new OnUpdateUserListener() {
            @Override
            public void onSuccess() {
                v.showEditSuccee();
            }

            @Override
            public void onFailure(int i, String s) {
                v.showEditFail(i);
            }
        });
    }

    public void updatePic(Uri uri) {
        v.showUpdatePicLoading();
        userModel.uploadHeadPortrait(uri.getPath(), new OnUploadListener() {
            @Override
            public void onSuccess(String fileName, String url, BmobFile file) {
                v.dismissPicLoading();
                v.showUpdatePicSuccess();
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
                v.dismissPicLoading();
                v.showUpdatePicFailed(statuscode);
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

    // 解除view的绑定
    public void onRelieveView() {
        v = null;
    }
}
