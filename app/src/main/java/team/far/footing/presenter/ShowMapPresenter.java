package team.far.footing.presenter;

import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;

import team.far.footing.model.IMapModel;
import team.far.footing.model.bean.MapBean;
import team.far.footing.model.impl.MapModel;
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

    public ShowMapPresenter(IShowMapVu iShowMapVu, MapBean mapBean) {
        this.iShowMapVu = iShowMapVu;
        mapModel = MapModel.getInstance();
        this.mapBean = mapBean;
        showMap();
    }

    private void showMap() {
        latLngs = StringUntils.getLaLngs(Arrays.asList(mapBean.getMap_array()));
        LogUtils.d(latLngs.size() + "");
        iShowMapVu.showWalkInfo(mapBean.getAll_time(), mapBean.getAll_distance(), mapBean.getStart_time());
        iShowMapVu.showMap(latLngs);
    }

    public void onRelieveView() {
        iShowMapVu = null;
    }
}
