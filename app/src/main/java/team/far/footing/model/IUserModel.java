package team.far.footing.model;


import android.app.Activity;

import cn.bmob.v3.datatype.BmobFile;
import team.far.footing.model.Listener.OnLoginForQQListener;
import team.far.footing.model.Listener.OnLoginListener;
import team.far.footing.model.Listener.OnRegsterListener;
import team.far.footing.model.Listener.OnUpdateUserListener;
import team.far.footing.model.Listener.OnUploadHeadPortraitListener;

/**
 * Created by moi on 2015/8/7.
 */
public interface IUserModel {
    void Login(String username, String passwrod, OnLoginListener onLoginListener);

    void Regster(String username, String passwrod, String email, OnRegsterListener onRegsterListener);

    void loginForQQ(Activity activity, OnLoginForQQListener onLoginForQQListener);

    /**
     * @param filePath                     ------- 文件路径
     * @param onUploadHeadPortraitListener ----------文件上传监听
     */
    void uploadHeadPortrait(String filePath, OnUploadHeadPortraitListener onUploadHeadPortraitListener);

    /**
     * @param url                          ----------头像保存url
     * @param filename                     -----------头像文件名
     * @param file                         ----------成功更新后返回的file文件
     * @param onUploadHeadPortraitListener ---文件上传监听
     *                                     <p/>
     *                                     ### 注意：改接口不给外调用
     */
    void updateUser_HeadPortraitFilePath(String url, String filename, BmobFile file, OnUploadHeadPortraitListener onUploadHeadPortraitListener);

    /**
     * @param signatrue            ----------更新签名
     * @param onUpdateUserListener ---------更新用户数据的监听器
     */
    void updateUser_Signature(String signatrue, OnUpdateUserListener onUpdateUserListener);

    /**
     * @param level                ------------更新等级
     * @param onUpdateUserListener ------------更新用户数据的监听器
     */
    void updateUser_level(int level, OnUpdateUserListener onUpdateUserListener);

    /**
     * @param nickname             ------------昵称
     * @param onUpdateUserListener --------------更新用户数据的监听器
     */
    void update_NickName(String nickname, OnUpdateUserListener onUpdateUserListener);


    void update_PraiseCount(int PraiseCount,OnUpdateUserListener onUpdateUserListener);
}
