package team.far.footing.ui.vu;

import android.app.Activity;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;

/**
 * Created by luoyy on 2015/8/20 0020.
 */
public interface IShowMapVu {

    void showMap(ArrayList<LatLng> latLngs);

    void showWalkInfo(String allTime, String allDistance, String startTime,String ct ,String ad, String street);

    BaiduMap getBaiduMap();

    Activity getActivity();

    void show_shareProgress(int progress);

    void show_shareSuccess();

    void show_shareError();

    void show_shareCancel();


}
