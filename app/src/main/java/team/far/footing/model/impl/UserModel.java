package team.far.footing.model.impl;

import android.app.Activity;

import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.google.gson.Gson;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.OtherLoginListener;
import cn.bmob.v3.listener.ResetPasswordByEmailListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import team.far.footing.R;
import team.far.footing.app.APP;
import team.far.footing.model.IUserModel;
import team.far.footing.model.Listener.OnLoginForQQListener;
import team.far.footing.model.Listener.OnLoginListener;
import team.far.footing.model.Listener.OnQueryFriendListener;
import team.far.footing.model.Listener.OnRegsterListener;
import team.far.footing.model.Listener.OnUpdateUserListener;
import team.far.footing.model.Listener.OnUploadHeadPortraitListener;
import team.far.footing.model.bean.Userbean;
import team.far.footing.util.BmobUtils;
import team.far.footing.util.LogUtils;

/**
 * Created by moi on 2015/8/7.
 */
public class UserModel implements IUserModel {


    @Override
    public void Login(String username, String passwrod, final OnLoginListener onLoginListener) {
        final Userbean loginBean = new Userbean();
        loginBean.setUsername(username);
        loginBean.setPassword(passwrod);

        loginBean.login(APP.getContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                onLoginListener.loginSuccess(BmobUtils.getCurrentUser());
            }

            @Override
            public void onFailure(int i, String s) {
                onLoginListener.loginFailed(s);
            }
        });


    }

    @Override
    public void Regster(String username, String passwrod, String email, final OnRegsterListener onRegsterListener) {

        final Userbean regsterBean = new Userbean();

        regsterBean.setUsername(username);
        regsterBean.setPassword(passwrod);
        regsterBean.setEmail(email);

        regsterBean.signUp(APP.getContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                onRegsterListener.RegsterSuccess(BmobUser.getCurrentUser(APP.getContext(), Userbean.class));
            }

            @Override
            public void onFailure(int i, String s) {
                onRegsterListener.RegsterFail(s);
            }
        });

    }

    @Override
    public void loginForQQ(Activity activity, final OnLoginForQQListener onLoginForQQListener) {

        Tencent mTencent =
                Tencent.createInstance(APP.getContext().getString(R.string.QQ_ID), APP.getContext());
        if (!mTencent.isSessionValid()) {
            mTencent.login(activity, "all", new IUiListener() {
                @Override
                public void onComplete(Object arg0) {
                    if (arg0 != null) {
                        JSONObject jsonObject = (JSONObject) arg0;
                        try {
                            String token = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN);
                            String expires = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_EXPIRES_IN);
                            String openId = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_OPEN_ID);
                            BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth(BmobUser.BmobThirdUserAuth.SNS_TYPE_QQ, token, expires, openId);
                            loginWithAuth(authInfo, onLoginForQQListener);
                        } catch (JSONException e) {
                        }
                    }
                }

                @Override
                public void onError(UiError uiError) {
                    onLoginForQQListener.loginFailed(uiError.toString());
                }

                @Override
                public void onCancel() {
                    onLoginForQQListener.loginCancel();
                }
            });
        }
    }

    @Override
    public void uploadHeadPortrait(String filePath, final OnUploadHeadPortraitListener onUploadHeadPortraitListener) {
        BTPFileResponse response = BmobProFile.getInstance(APP.getContext()).upload(filePath, new UploadListener() {
            @Override
            public void onSuccess(String fileName, String url, BmobFile file) {
                // TODO Auto-generated method stub
                LogUtils.i("bmob", "文件上传成功：" + fileName + ",可访问的文件地址：" + file.getUrl());
                updateUser_HeadPortraitFilePath(url, fileName, file, onUploadHeadPortraitListener);
            }

            @Override
            public void onProgress(int progress) {
                // TODO Auto-generated method stub
                LogUtils.i("bmob", "onProgress :" + progress);
                onUploadHeadPortraitListener.onProgress(progress);
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                // TODO Auto-generated method stub
                LogUtils.e("bmob", "文件上传失败：" + errormsg);
                onUploadHeadPortraitListener.onError(statuscode, errormsg);
            }
        });

    }

    @Override
    public void updateUser_HeadPortraitFilePath(final String url, final String filename, final BmobFile file, final OnUploadHeadPortraitListener onUploadHeadPortraitListener) {
        Userbean newUser = new Userbean();
        newUser.setHeadPortraitFilePath(url);
        newUser.getHeadPortraitFileName(filename);
        BmobUser bmobUser = BmobUser.getCurrentUser(APP.getContext());
        newUser.update(APP.getContext(), bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                onUploadHeadPortraitListener.onSuccess(filename, url, file);
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                onUploadHeadPortraitListener.onError(code, msg);
            }
        });
    }

    @Override
    public void updateUser_Signature(String signature, final OnUpdateUserListener onUpdateUserListener) {
        Userbean newUser = new Userbean();
        newUser.setSignature(signature);
        updateUser(newUser, onUpdateUserListener);
    }

    @Override
    public void updateUser_level(int level, OnUpdateUserListener onUpdateUserListener) {
        Userbean newUser = new Userbean();
        newUser.setLevel(level);
        updateUser(newUser, onUpdateUserListener);

    }

    @Override
    public void update_NickName(String nickname, OnUpdateUserListener onUpdateUserListener) {
        Userbean newUser = new Userbean();
        newUser.setNickName(nickname);
        updateUser(newUser, onUpdateUserListener);
    }

    @Override
    public void update_PraiseCount(int PraiseCount, OnUpdateUserListener onUpdateUserListener) {
        Userbean newUser = new Userbean();
        newUser.setPraiseCount(PraiseCount);
        updateUser(newUser, onUpdateUserListener);
    }

    @Override
    public void queryUserByName(String nickname, OnQueryFriendListener onQueryFriendListener) {
        BmobQuery<BmobUser> query = new BmobQuery<BmobUser>();
        query.addWhereEqualTo("NickName", nickname);
        query_users(query, onQueryFriendListener);
    }

    @Override
    public void queryUserById(String id, OnQueryFriendListener onQueryFriendListener) {
        BmobQuery<BmobUser> query = new BmobQuery<BmobUser>();
        query.addWhereEqualTo("username", id);
        query_users(query, onQueryFriendListener);
    }

    @Override
    public void queryUserByDistance(OnQueryFriendListener onQueryFriendListener) {

    }

    @Override
    public void queryAlluser(OnQueryFriendListener onQueryFriendListener) {
        BmobQuery<BmobUser> query = new BmobQuery<BmobUser>();
        query.addWhereNotEqualTo("username", "00");
        query_users(query, onQueryFriendListener);
    }

    @Override
    public void resetPasswordByEmail(String email, final OnUpdateUserListener onUpdateUserListener) {
        BmobUser.resetPasswordByEmail(APP.getContext(), email, new ResetPasswordByEmailListener() {
            @Override
            public void onSuccess() {
                onUpdateUserListener.onSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                onUpdateUserListener.onFailure(i, s);
            }
        });

    }

    //调用第三方登录  -- 暂时先写qq
    public void loginWithAuth(final BmobUser.BmobThirdUserAuth authInfo, final OnLoginForQQListener onLoginForQQListener) {
        BmobUser.loginWithAuthData(APP.getContext(), authInfo, new OtherLoginListener() {
            @Override
            public void onSuccess(JSONObject userAuth) {
                // TODO Auto-generated method stub
                LogUtils.i("smile", authInfo.getSnsType() + "登陆成功返回:" + userAuth);
                Gson gson = new Gson();
                onLoginForQQListener.loginSuccess(BmobUtils.getCurrentUser());
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                onLoginForQQListener.loginFailed(msg);
            }

        });
    }

    //设置数据完毕后，调用更新服务端数据。
    private void updateUser(Userbean newUser, final OnUpdateUserListener onUpdateUserListener) {

        BmobUser bmobUser = BmobUser.getCurrentUser(APP.getContext());
        newUser.update(APP.getContext(), bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                onUpdateUserListener.onSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                onUpdateUserListener.onFailure(i, s);
            }
        });


    }

    //设置完查询条件后，进行用户查询
    private void query_users(BmobQuery<BmobUser> query, final OnQueryFriendListener onQueryFriendListener) {
        query.findObjects(APP.getContext(), new FindListener<BmobUser>() {
            @Override
            public void onSuccess(List<BmobUser> object) {
                // TODO Auto-generated method stub
                LogUtils.i("查询用户成功");
                onQueryFriendListener.onSuccess(object);
            }

            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                LogUtils.e("查询用户失败", msg);
                onQueryFriendListener.onError(code, msg);

            }
        });


    }


}
