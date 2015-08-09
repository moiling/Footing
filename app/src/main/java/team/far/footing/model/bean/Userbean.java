package team.far.footing.model.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by moi on 2015/8/7.
 */
public class Userbean extends BmobUser {

    /**
     * 继承的BmobUser
     * ------ username
     * ------ password
     * ------ email
     */
    //用于保存 一个用户对应一个Friends中的objectId
    private String FriendId;
    //昵称
    private String NickName;
    //签名
    private String Signature;
    //头像url
    private String HeadPortraitFilePath;
    //点赞数
    private Integer PraiseCount;
    //头像fileame
    private String HeadPortraitFileName;
    //角色等级 TODO 我不知道是不是这里加了就行……你看看也  --->>>还要在后台加了 ==
    private Integer level;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getHeadPortraitFileName(String filename) {
        return HeadPortraitFileName;
    }

    public void setHeadPortraitFileName(String headPortraitFileName) {
        HeadPortraitFileName = headPortraitFileName;
    }

    public String getHeadPortraitFilePath() {
        return HeadPortraitFilePath;
    }

    public void setHeadPortraitFilePath(String headPortraitFilePath) {
        HeadPortraitFilePath = headPortraitFilePath;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public Integer getPraiseCount() {
        return PraiseCount;
    }

    public void setPraiseCount(Integer praiseCount) {
        PraiseCount = praiseCount;
    }

    public String getSignature() {
        return Signature;
    }

    public String getHeadPortraitFileName() {
        return HeadPortraitFileName;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

    public String getFriendId() {
        return FriendId;
    }

    public void setFriendId(String friendId) {
        FriendId = friendId;
    }

}
