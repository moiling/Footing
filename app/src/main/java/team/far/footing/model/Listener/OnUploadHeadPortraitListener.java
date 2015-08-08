package team.far.footing.model.Listener;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Luoyy on 2015/8/8 0008.
 */
public interface OnUploadHeadPortraitListener {

    void onSuccess(String fileName, String url, BmobFile file);

    void onProgress(int progress);

    void onError(int statuscode, String errormsg);

}
