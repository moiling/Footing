package team.far.footing.model;

import android.app.Activity;

import com.tencent.tauth.IUiListener;

/**
 * Created by luoyy on 2015/8/20 0020.
 */
public interface IShareModel {

    //分享至微博
    void ShareToWeiBo();

    /***
     * 下面三种方法都是分享qq群和个人
     */
    //图文分享
    void ShareToQQWithPT(Activity activity, IUiListener iUiListener);

    //纯图分享
    void ShareToQQWithP(Activity activity, IUiListener iUiListener);

    //APP 分享
    void ShareAppToQQ(Activity activity, IUiListener iUiListener);

    //分享 至空间
    void shareToQzone(Activity activity, IUiListener iUiListener);
}
