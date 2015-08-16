package team.far.footing.ui.vu;

import android.graphics.Bitmap;

import team.far.footing.model.bean.Userbean;

/**
 * Created by moi on 2015/8/12.
 */
public interface IUserInfoVu {

    void showUserInformation(Userbean userbean,Bitmap bitmap);

    void showUserPic(Bitmap bitmap);

    void showUpdateLoading();

    void showUpdateSuccess();

    void showUpdateFailed(String errormsg);

    void dismissLoading();
}
