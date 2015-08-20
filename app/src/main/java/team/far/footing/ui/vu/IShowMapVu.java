package team.far.footing.ui.vu;

import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;

/**
 * Created by luoyy on 2015/8/20 0020.
 */
public interface IShowMapVu {

    void showMap(ArrayList<LatLng> latLngs);

    void showWalkInfo(String allTime, String allDistance, String startTime);

}
