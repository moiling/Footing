package team.far.footing.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by luoyy on 2015/8/20 0020.
 */
public class MessageBean extends BmobObject {

    private Userbean getuser;
    private Userbean senduser;
    private String message;
    private String content;
    //  未读 0  已读 1
    private Integer isread;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Userbean getGetuser() {
        return getuser;
    }

    public void setGetuser(Userbean getuser) {
        this.getuser = getuser;
    }

    public Userbean getSenduser() {
        return senduser;
    }

    public void setSenduser(Userbean senduser) {
        this.senduser = senduser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getIsread() {
        return isread;
    }

    public void setIsread(Integer isread) {
        this.isread = isread;
    }
}
