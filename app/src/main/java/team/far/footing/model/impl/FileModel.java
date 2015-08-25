package team.far.footing.model.impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.bmob.BTPFileResponse;
import com.bmob.BmobPro;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.DeleteFileListener;
import com.bmob.btp.callback.GetAccessUrlListener;
import com.bmob.btp.callback.LocalThumbnailListener;
import com.bmob.btp.callback.UploadListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import cn.bmob.v3.datatype.BmobFile;
import team.far.footing.app.APP;
import team.far.footing.model.IFileModel;
import team.far.footing.model.callback.OnUploadListener;
import team.far.footing.model.callback.OngetUserPicListener;
import team.far.footing.model.bean.Userbean;
import team.far.footing.util.LogUtils;

/**
 * Created by luoyy on 2015/8/12 0012.
 */
public class FileModel implements IFileModel {
    public static final FileModel instance = new FileModel();


    private FileModel() {
    }

    public static final FileModel getInstance() {
        return instance;
    }


    private BlockingQueue<String> queue = new LinkedBlockingDeque<>();

    public void uploadPic(String filePath, final OnUploadListener onUploadListener) {
        BTPFileResponse response = BmobProFile.getInstance(APP.getContext()).upload(filePath, new UploadListener() {

            @Override
            public void onSuccess(String fileName, String url, BmobFile file) {
                // TODO Auto-generated method stub
                LogUtils.i("bmob", "文件上传成功：" + fileName + ",可访问的文件地址：" + file.getUrl());
                onUploadListener.onSuccess(fileName, file.getUrl(), file);
            }

            @Override
            public void onProgress(int progress) {
                // TODO Auto-generated method stub
                LogUtils.i("bmob", "onProgress :" + progress);
                onUploadListener.onProgress(progress);
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                // TODO Auto-generated method stub
                LogUtils.i("bmob", "文件上传失败：" + errormsg+"====>>"+statuscode);
                onUploadListener.onError(statuscode, errormsg);
            }
        });
    }

    @Override
    public void downloadPic(final String filename, final com.bmob.btp.callback.DownloadListener downloadListener) {
        BmobProFile bmobProFile = new BmobProFile();
        bmobProFile.start(APP.getContext());
        bmobProFile.download(filename, downloadListener);
    }

    @Override
    public void getAccessURL(String filename, GetAccessUrlListener getAccessUrlListener) {
        BmobProFile.getInstance(APP.getContext()).getAccessURL(filename, getAccessUrlListener);
    }

    @Override
    public void deleteFile(String filename, DeleteFileListener deleteFileListener) {
        BmobProFile.getInstance(APP.getContext()).deleteFile(filename, deleteFileListener);
    }

    @Override
    public String getDowPicPath() {
        return BmobPro.getInstance(APP.getContext()).getCacheDownloadDir();
    }

    @Override
    public String getCacheThumbnailDir() {
        return BmobPro.getInstance(APP.getContext()).getCacheThumbnailDir();
    }

    @Override
    public String getCacheFormatSize() {
        String cacheSize = String.valueOf(BmobPro.getInstance(APP.getContext()).getCacheFileSize());
        String formatSize = BmobPro.getInstance(APP.getContext()).getCacheFormatSize();
        return formatSize;
    }

    @Override
    public void clearCache() {
        BmobPro.getInstance(APP.getContext()).clearCache();
    }

    @Override
    public void getLocalThumbnail(String filepath, int modeId, LocalThumbnailListener localThumbnailListener) {
        BmobProFile.getInstance(APP.getContext()).getLocalThumbnail(filepath, modeId, localThumbnailListener);
    }

    @Override
    public void getLocalThumbnail(String filepath, int modeId, int width, int height, LocalThumbnailListener localThumbnailListener) {
        BmobProFile.getInstance(APP.getContext()).getLocalThumbnail(filepath, modeId, width, height, localThumbnailListener);
    }

    @Override
    public void getLocalThumbnail(String filepath, int modeId, int width, int height, int quality, LocalThumbnailListener localThumbnailListener) {
        BmobProFile.getInstance(APP.getContext()).getLocalThumbnail(filepath, modeId, width, height, quality, localThumbnailListener);
    }

    @Override
    public Bitmap getLocalPic(String filename) {
        return BitmapFactory.decodeFile(getDowPicPath() + "/" + filename);
    }

    @Override
    public String getLocalPicPath(String filename) {
        return getDowPicPath() + "/" + filename;
    }

    @Override
    public void getUserPic(final Userbean userbean, final OngetUserPicListener ongetUserPicListener) {
        final File file = new File(getLocalPicPath(userbean.getHeadPortraitFileName()));
        if (file.exists()) {
            ongetUserPicListener.onSucess(getLocalPic(userbean.getHeadPortraitFileName()));
            LogUtils.e("本地获取文件");
        } else {
            RequestQueue queue = Volley.newRequestQueue(APP.getContext());
            queue.add(new ImageRequest(userbean.getHeadPortraitFilePath(), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    ongetUserPicListener.onSucess(response);
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        response.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        LogUtils.e("网络获取文件");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    ongetUserPicListener.onError();
                }
            }));
        }
    }


}
