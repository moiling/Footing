package team.far.footing.model.impl;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

import java.io.ByteArrayOutputStream;

import team.far.footing.R;
import team.far.footing.app.APP;
import team.far.footing.model.IShareModel;
import team.far.footing.util.LogUtils;

/**
 * Created by luoyy on 2015/8/20 0020.
 */
public class ShareModel implements IShareModel {
    public static final Tencent mTencent = Tencent.createInstance(APP.getContext().getString(R.string.QQ_ID, APP.getContext()),
            APP.getContext());
    ;
    public static final ShareModel instance = new ShareModel();

    private ShareModel() {
    }

    public static Tencent getmTencent() {
        return mTencent;
    }

    public static ShareModel getInstance() {
        return instance;
    }


    @Override
    public void ShareToWeiBo(String content, Bitmap bitmap, IRequestListener iRequestListener) {
        Bundle bundle = new Bundle();
        bundle.putString("format", "json");
        bundle.putString("content", content);
        // 把 bitmap 转换为 byteArray , 用于发送请求
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] buff = baos.toByteArray();
        bundle.putByteArray("pic", buff);
        mTencent.requestAsync(Constants.GRAPH_BASE, bundle,
                Constants.HTTP_POST, iRequestListener, null);
        bitmap.recycle();
    }

    //图文分享
    @Override
    public void ShareToQQWithPT(Activity activity, String pic_url, String net_url, IUiListener iUiListener) {
        final Bundle params = new Bundle();
        //分享的类型。图文分享(普通分享)填Tencent.SHARE_TO_QQ_TYPE_DEFAULT
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        //分享的标题, 最长30个字符。
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "我的足下之行");
        //分享的消息摘要，最长40个字。
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "看看我的足迹吧！");
        //这条分享消息被好友点击后的跳转URL。
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, net_url);
        //分享图片的URL或者本地路径
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, pic_url);
        //手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "足下");
        //分享时自动打开分享到QZone的对话框
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
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
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "我的足下之行");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "要分享的摘要");
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试应用222222");
        mTencent.shareToQQ(activity, params, iUiListener);
    }

    @Override
    public void shareToQzone(Activity activity, IUiListener iUiListener) {
        final Bundle params = new Bundle();
        //SHARE_TO_QZONE_TYPE_IMAGE_TEXT（图文）
        params.putString(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT + "");
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "我的足下之行");//必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "摘要");//选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");//必填
        //分享的图片, 以ArrayList<String>的类型传入
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, null);
        mTencent.shareToQzone(activity, params, iUiListener);
    }
}
