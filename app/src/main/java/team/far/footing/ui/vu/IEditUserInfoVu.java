package team.far.footing.ui.vu;

import team.far.footing.model.bean.Userbean;

/**
 * Created by moi on 2015/8/12.
 */
public interface IEditUserInfoVu {

    String getNickName();

    String getEmail();

    String getSignature();

    void showUserInformation(Userbean userbean);

    void showEditLoading();

    void showEditSuccee();

    void showEditFail(int i);
}
