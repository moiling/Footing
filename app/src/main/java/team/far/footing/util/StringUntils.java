package team.far.footing.util;

import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luoyy on 2015/8/15 0015.
 */
public class StringUntils {
    public static final ArrayList<LatLng> getLaLngs(List<String> list_map) {

        ArrayList<LatLng> latLngs = new ArrayList<>();
        for (String string : list_map) {
            try {
                String[] array = string.split("=");
                latLngs.add(new LatLng(Double.valueOf(array[0]), Double.valueOf(array[0])));
            } catch (Exception e) {
                LogUtils.e("在StringUntils中：解析出错");
            }

        }
        return latLngs;
    }


}
