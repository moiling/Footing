package team.far.footing.model.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by Luoyy on 2015/8/9 0009.
 */
public class Friends extends BmobObject {

    private BmobRelation friends;
    private Userbean userbean;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Userbean getUserbean() {
        return userbean;
    }

    public void setUserbean(Userbean userbean) {
        this.userbean = userbean;
    }


    public BmobRelation getFriends() {
        return friends;
    }

    public void setFriends(BmobRelation friends) {
        this.friends = friends;
    }


}
