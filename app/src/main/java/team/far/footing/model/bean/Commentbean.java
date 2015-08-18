package team.far.footing.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by luoyy on 2015/8/11 0011.
 */
public class Commentbean extends BmobObject {

    private String content;
    private Postbean postbean;
    private Userbean userbean;

    public Postbean getPostbean() {
        return postbean;
    }

    public void setPostbean(Postbean postbean) {
        this.postbean = postbean;
    }

    public Userbean getUserbean() {
        return userbean;
    }

    public void setUserbean(Userbean userbean) {
        this.userbean = userbean;
    }

    public String getContent() {

        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
