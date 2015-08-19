package team.far.footing.presenter;

import com.baidu.location.LocationClient;

import team.far.footing.model.IMapModel;
import team.far.footing.model.impl.MapModel;
import team.far.footing.ui.vu.IShowMapVu;

/**
 * Created by luoyy on 2015/8/20 0020.
 */
public class ShowMapPresenter {
    private IShowMapVu iShowMapVu;
    private IMapModel mapModel;
    private LocationClient mLocationClient;

    public ShowMapPresenter(IShowMapVu iShowMapVu) {
        this.iShowMapVu = iShowMapVu;
        mapModel = MapModel.getInstance();
    }



}
