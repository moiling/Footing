package team.far.footing.ui.vu;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.model.LatLng;

import java.util.List;

/**
 * Created by moi on 2015/8/10.
 */
public interface IWalkVu {

    // 显示总距离
    void showDistanceTotal(double distance);

    // 在地图上画线
    void drawPolyline(List<LatLng> latLngs);

    // 在地图上画所有点的线
    void drawAllPolyline(List<LatLng> latLngs);

    // 移动镜头到某处
    void moveCamera2Location(LatLng latLng);

    // 得到map
    BaiduMap getBaiduMap();

    void startWalk();

    void stopWalk();

    //void pauseWalk();

    void showstart();


    //分享
    void show_shareProgress(int progress);

    void show_shareSuccess();

    void show_shareError(String s);

    void show_shareCancel();

}
