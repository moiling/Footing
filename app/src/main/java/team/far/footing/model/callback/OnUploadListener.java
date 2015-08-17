package team.far.footing.model.callback;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Luoyy on 2015/8/8 0008.
 */
public interface OnUploadListener {

    /**
     * @param fileName -------文件在服务器上的名称
     * @param url      -------文件在服务器上的url
     * @param file     --------上传的文件
     */
    void onSuccess(String fileName, String url, BmobFile file);

    void onProgress(int progress);

    void onError(int statuscode, String errormsg);

}
