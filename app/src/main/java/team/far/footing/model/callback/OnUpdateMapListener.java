package team.far.footing.model.callback;

import team.far.footing.model.bean.MapBean;

/**
 * Created by luoyy on 2015/8/14 0014.
 */
public interface OnUpdateMapListener {
    void onSuccess(MapBean mapBean);

    void onFailure(int i, String s);
}
