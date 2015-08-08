package team.far.footing.util;

import cn.bmob.v3.BmobUser;
import team.far.footing.app.APP;
import team.far.footing.model.bean.Userbean;

/**
 * Created by Luoyy on 2015/8/8 0008.
 */
public class BmobUtils {


    /**
     * @return 需要在登陆后才可以调用
     */
    public static Userbean getCurrentUser() {
        return BmobUser.getCurrentUser(APP.getContext(), Userbean.class);

    }

    /**
     *   退出登录
     */
    public static void LogOutUser() {
        BmobUser.logOut(APP.getContext());
    }
}
