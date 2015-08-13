package team.far.footing.model;

import android.graphics.Bitmap;
import android.webkit.DownloadListener;

import com.bmob.btp.callback.DeleteFileListener;
import com.bmob.btp.callback.GetAccessUrlListener;
import com.bmob.btp.callback.LocalThumbnailListener;

import team.far.footing.model.Listener.OnUploadListener;

/**
 * Created by luoyy on 2015/8/12 0012.
 */
public interface IFileModel {


    //注意：文件上传后返回的url是不可以直接访问的。

    /**
     * -------上传图片
     *
     * @param filePath
     * @param onUploadListener
     */
    void uploadPic(String filePath, OnUploadListener onUploadListener);

    /**
     * @param filename         -------------文件上传后得到的filename
     * @param downloadListener
     */
    void downloadPic(String filename, com.bmob.btp.callback.DownloadListener downloadListener);

    /**
     * ------------通过文件名得到可以访问的url
     *
     * @param filename
     * @param filename
     * @param getAccessUrlListener
     */
    void getAccessURL(String filename, GetAccessUrlListener getAccessUrlListener);

    /**
     * ------------通过文件名删除文件
     *
     * @param filename
     * @param deleteFileListener
     */
    void deleteFile(String filename, DeleteFileListener deleteFileListener);

    /**
     * --------------返回文件下载目录
     *
     * @return
     */
    String getDowPicPath();

    /**
     * @return -------------略缩图的保存路经
     */
    String getCacheThumbnailDir();

    /**
     * @return ----获取缓存大小 单位：字节
     */
    String getCacheFormatSize();

    /**
     * --------清除缓存
     */
    void clearCache();

    //生成略缩图
    //指定规格ID
    void getLocalThumbnail(String filepath, int modeId, LocalThumbnailListener localThumbnailListener);

    //指定规格ID、宽、高
    void getLocalThumbnail(String filepath, int modeId, int width, int height, LocalThumbnailListener localThumbnailListener);

    //指定规格ID、宽、高、图片压缩质量
    void getLocalThumbnail(String filepath, int modeId, int width, int height, int quality, LocalThumbnailListener localThumbnailListener);


    //
    Bitmap getLocalPic(String filename);
}