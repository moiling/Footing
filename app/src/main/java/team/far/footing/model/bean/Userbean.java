package team.far.footing.model.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;

/**
 * Created by moi on 2015/8/7.
 */
public class Userbean extends BmobUser implements Serializable {

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
    //角色等级
    private Integer level;
    //今日路程
    private Integer today_distance;
    //总的路程
    private Integer all_distance;
    //今日任务完成没  0表示未完成  1表示完成。
    private Integer is_finish_today;

    public Integer getToday_distance() {
        return today_distance;
    }

    public void setToday_distance(Integer today_distance) {
        this.today_distance = today_distance;
    }

    public Integer getAll_distance() {
        return all_distance;
    }

    public void setAll_distance(Integer all_distance) {
        this.all_distance = all_distance;
    }

    public Integer getIs_finish_today() {
        return is_finish_today;
    }

    public void setIs_finish_today(Integer is_finish_today) {
        this.is_finish_today = is_finish_today;
    }

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
