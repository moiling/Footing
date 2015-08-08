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
    //头像
    private Bitmap HeadPortrait;
    //点赞数
    private int  PraiseCount;


}
