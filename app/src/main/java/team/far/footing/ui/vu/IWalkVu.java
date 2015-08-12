package team.far.footing.ui.vu;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;

/**
 * Created by moi on 2015/8/10.
 */
public interface IWalkVu {

    // 显示总距离
    void showDistanceTotal(double distance);
    // 在地图上画线
    void drawPolyline(ArrayList<LatLng> latLngs);
    // 移动镜头到某处
    void moveCamera2Location(LatLng latLng);
    // 得到map
    BaiduMap getBaiduMap();

}
