package team.far.footing.ui.vu;

import android.graphics.Bitmap;

import team.far.footing.model.bean.Userbean;

/**
 * Created by moi on 2015/8/9.
 */
public interface IHomeVu {

    /**
     * 显示用户数据
     * 需要的数据：昵称、头像、签名、等级
     *
     * @param userbean bmob得到登录的用户
     */
    void showUserInformation(Userbean userbean);

    /**
     * 显示任务
     */
    void showMission();

    void showUserImg(Bitmap bitmap);

}
