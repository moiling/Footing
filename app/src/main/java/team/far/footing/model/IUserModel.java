package team.far.footing.model;


import android.app.Activity;

import cn.bmob.v3.datatype.BmobFile;
import team.far.footing.model.Listener.OnLoginForQQListener;
import team.far.footing.model.Listener.OnLoginListener;
import team.far.footing.model.Listener.OnRegsterListener;
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
}
