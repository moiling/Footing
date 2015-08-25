package team.far.footing.presenter;

import android.graphics.Bitmap;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.model.LatLng;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import team.far.footing.app.APP;
import team.far.footing.model.IFileModel;
import team.far.footing.model.IMapModel;
import team.far.footing.model.IShareModel;
import team.far.footing.model.bean.MapBean;
import team.far.footing.model.callback.OnUploadListener;
import team.far.footing.model.impl.FileModel;
import team.far.footing.model.impl.MapModel;
import team.far.footing.model.impl.ShareModel;
import team.far.footing.ui.vu.IShowMapVu;
import team.far.footing.util.LogUtils;
import team.far.footing.util.StringUntils;

/**
 * Created by luoyy on 2015/8/20 0020.
 */
public class ShowMapPresenter {
    private IShowMapVu iShowMapVu;
    private IMapModel mapModel;
    private MapBean mapBean;
    private ArrayList<LatLng> latLngs = new ArrayList<>();
    private BaiduMap baiduMap;
    private IShareModel shareModel;
    private IFileModel fileModel;

    public ShowMapPresenter(IShowMapVu iShowMapVu, MapBean mapBean) {
        this.iShowMapVu = iShowMapVu;
        mapModel = MapModel.getInstance();
        shareModel = ShareModel.getInstance();
        fileModel = FileModel.getInstance();
        this.mapBean = mapBean;
        baiduMap = iShowMapVu.getBaiduMap();
        showMap();
    }

    private void showMap() {
        latLngs = StringUntils.getLaLngs(Arrays.asList(mapBean.getMap_array()));
        LogUtils.d(latLngs.size() + "");
        iShowMapVu.showWalkInfo(mapBean.getAll_time(), mapBean.getAll_distance(), mapBean.getStart_time(),mapBean.getCity(),mapBean.getAddress(), mapBean.getStreet());
        iShowMapVu.showMap(latLngs);
    }

    public void onRelieveView() {
        iShowMapVu = null;
    }


    public void QQshare() {
        iShowMapVu.show_shareProgress(0);
        baiduMap.snapshot(new BaiduMap.SnapshotReadyCallback() {
            @Override
            public void onSnapshotReady(Bitmap bitmap) {
                String path = saveBitmap(bitmap);
                uploadBitmap(path);
            }
        });
    }

    public String saveBitmap(Bitmap bitmap) {
        final File f = new File(fileModel.getCacheThumbnailDir(), "bitmap.jpg");
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            iShowMapVu.show_shareError();
        } catch (IOException e) {
            e.printStackTrace();
            iShowMapVu.show_shareError();
        }
        return f.getPath();
    }


    public void uploadBitmap(final String path) {
        fileModel.uploadPic(path, new OnUploadListener() {
            @Override
            public void onSuccess(String fileName, String url, BmobFile file) {
                saveMapBean(fileName, url, path);
            }

            @Override
            public void onProgress(int progress) {
                if (progress != 100) iShowMapVu.show_shareProgress(progress);
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                iShowMapVu.show_shareError();
            }
        });
    }


    public void saveMapBean(String fileName, final String url, final String path) {
        mapBean.setMap_file_name(fileName);
        mapBean.setMap_url(url);
        mapBean.update(APP.getContext(), new UpdateListener() {
            @Override
            public void onSuccess() {
                shareMap(url, path);
            }

            @Override
            public void onFailure(int i, String s) {
                iShowMapVu.show_shareError();
            }
        });
    }

    public void shareMap(String url, String path) {
        shareModel.ShareToQQWithPT(iShowMapVu.getActivity(), path, url, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                LogUtils.e("完成分享");
                iShowMapVu.show_shareSuccess();
            }

            @Override
            public void onError(UiError uiError) {
                LogUtils.e("完成失败");
                iShowMapVu.show_shareError();
            }

            @Override
            public void onCancel() {
                LogUtils.e("完成取消");
                iShowMapVu.show_shareCancel();
            }
        });
    }

    public Tencent getTencent(){
        return ShareModel.getmTencent();
    }

}
