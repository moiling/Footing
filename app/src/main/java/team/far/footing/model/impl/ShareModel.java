package team.far.footing.model.impl;

import android.app.Activity;
import android.os.Bundle;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

import team.far.footing.R;
import team.far.footing.app.APP;
import team.far.footing.model.IShareModel;

/**
 * Created by luoyy on 2015/8/20 0020.
 */
public class ShareModel implements IShareModel {
    private Tencent mTencent;
    public static final ShareModel instance = new ShareModel();

    private ShareModel() {
        mTencent = Tencent.createInstance(APP.getContext().getString(R.string.QQ_ID, APP.getContext()),
                APP.getContext());
    }

    public static ShareModel getInstance() {
        return instance;
    }


    @Override
    public void ShareToWeiBo() {


    }

    @Override
    public void ShareToQQWithPT(Activity activity, IUiListener iUiListener) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "要分享的标题");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "要分享的摘要");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://www.qq.com/news/1.html");
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试应用222222");
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, 123);
        mTencent.shareToQQ(activity, params, iUiListener);

    }

    @Override
    public void ShareToQQWithP(Activity activity, IUiListener iUiListener) {
        Bundle params = new Bundle();
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, "imageUrl");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "appName");
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        mTencent.shareToQQ(activity, params, iUiListener);
    }

    @Override
    public void ShareAppToQQ(Activity activity, IUiListener iUiListener) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_APP);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "要分享的标题");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "要分享的摘要");
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试应用222222");
        mTencent.shareToQQ(activity, params, iUiListener);
    }

    @Override
    public void shareToQzone(Activity activity, IUiListener iUiListener) {
        final Bundle params = new Bundle();
        params.putString(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT + "");
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "标题");//必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "摘要");//选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "跳转URL");//必填
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, null);
        mTencent.shareToQzone(activity, params, iUiListener);
    }
}
