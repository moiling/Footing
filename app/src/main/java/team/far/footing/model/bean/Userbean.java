package team.far.footing.model.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by moi on 2015/8/7.
 */
public class Userbean extends BmobUser {

    /**
     *  继承的BmobUser
     *    ------ username
     *    ------ password
     *    ------ email
     *
     */
    //昵称
    private String NickName;
    //签名
    private String Signature;
    //头像url
    private String HeadPortraitFilePath;
    //点赞数
    private int PraiseCount;
    //头像fileame
    private String HeadPortraitFileName;
    //角色等级 TODO 我不知道是不是这里加了就行……你看看也  --->>>还要在后台加了 ==
    private int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
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

    public int getPraiseCount() {
        return PraiseCount;
    }

    public void setPraiseCount(int praiseCount) {
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
}
