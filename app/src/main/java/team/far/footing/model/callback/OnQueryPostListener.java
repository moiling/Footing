package team.far.footing.model.callback;

import java.util.List;

import team.far.footing.model.bean.Postbean;

/**
 * Created by luoyy on 2015/8/11 0011.
 */
public interface OnQueryPostListener {
    void onSuccess(List<Postbean> list);

    void onError(int i, String s);

}
