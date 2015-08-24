package team.far.footing.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by luoyy on 2015/8/24 0024.
 */
public class Feedback extends BmobObject {

    private Userbean userbean;

    private String msg;


    public Userbean getUserbean() {
        return userbean;
    }

    public void setUserbean(Userbean userbean) {
        this.userbean = userbean;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
