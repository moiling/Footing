package team.far.footing.model.bean;

import android.graphics.Bitmap;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;

/**
 * Created by moi on 2015/8/7.
 */
public class Userbean extends BmobUser implements Serializable {

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
    private int  PraiseCount;
    //头像fileame
    private  String HeadPortraitFileName;

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

    public void setSignature(String signature) {
        Signature = signature;
    }
}
