package team.far.footing.presenter;

import java.util.List;

import cn.bmob.v3.listener.FindListener;
import team.far.footing.model.IMapModel;
import team.far.footing.model.bean.MapBean;
import team.far.footing.model.impl.MapModel;
import team.far.footing.ui.adapter.MapRyViewAdapter;
import team.far.footing.ui.vu.IMyMapVu;
import team.far.footing.util.BmobUtils;

/**
 * Created by luoyy on 2015/8/19 0019.
 */
public class MyMapPresenter {
    private IMyMapVu iMyMapVu;
    private IMapModel mapModel;
    private MapRyViewAdapter mapRyViewAdapter;


    public MyMapPresenter(IMyMapVu iMyMapVu) {
        this.iMyMapVu = iMyMapVu;
        mapModel = MapModel.getInstance();
        showmlist();
    }


    private void showmlist() {
        iMyMapVu.showLoading("玩命的加载...");

        mapModel.get_map_byuserbean(BmobUtils.getCurrentUser(), new FindListener<MapBean>() {
            @Override
            public void onSuccess(List<MapBean> list) {
                iMyMapVu.stopLoading();
                mapRyViewAdapter = new MapRyViewAdapter(list, iMyMapVu.getActivity());
                iMyMapVu.showmaplist(mapRyViewAdapter);
            }

            @Override
            public void onError(int i, String s) {

            }
        });

    }

    public void deletmap(MapBean mapBean) {
        mapModel.delete_mapbean(mapBean);
    }
}
