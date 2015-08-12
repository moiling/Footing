package team.far.footing.presenter;

import team.far.footing.model.IUserModel;
import team.far.footing.model.Listener.OnUpdateUserListener;
import team.far.footing.model.bean.Userbean;
import team.far.footing.model.impl.UserModel;
import team.far.footing.ui.vu.IEditUserInfoVu;
import team.far.footing.util.BmobUtils;

/**
 * Created by moi on 2015/8/12.
 */
public class EditUserInfoPresenter {

    private IUserModel mUserModel;
    private IEditUserInfoVu v;
    private Userbean userbean;

    public EditUserInfoPresenter(IEditUserInfoVu v) {
        this.v = v;
        mUserModel = UserModel.getInstance();
        userbean = BmobUtils.getCurrentUser();
        showUserInformation();
    }

    public void updateUserInformation() {
        v.showEditLoading();
        mUserModel.updataUserInfo(v.getNickName(), v.getSignature(), v.getEmail(), new OnUpdateUserListener() {
            @Override
            public void onSuccess() {
                v.showEditSuccee();
            }

            @Override
            public void onFailure(int i, String s) {
                v.showEditFail(s);
            }
        });
    }

    public void showUserInformation() {
        v.showUserInformation(userbean);
    }

    public void refreshUserInformation() {
        userbean = BmobUtils.getCurrentUser();
        showUserInformation();
    }

    // 解除view的绑定
    public void onRelieveView() {
        v = null;
    }
}
