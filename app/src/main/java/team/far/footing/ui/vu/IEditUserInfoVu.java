package team.far.footing.ui.vu;

import android.graphics.Bitmap;

import team.far.footing.model.bean.Userbean;

/**
 * Created by moi on 2015/8/12.
 */
public interface IEditUserInfoVu {

    String getNickName();

    String getSignature();

    void showUserInformation(Userbean userbean, Bitmap bitmap);

    void showEditLoading();

    void showEditSuccee();

    void showUpdatePicLoading();

    void showUpdatePicSuccess();

    void showUpdatePicFailed(int i);

    void dismissPicLoading();

    void showUserPic(Bitmap bitmap);

    void showEditFail(int i);
}
