package team.far.footing.model.Listener;

import team.far.footing.model.bean.Userbean;

/**
 * Created by Luoyy on 2015/8/7 0007.
 */
public interface OnRegsterListener {
    void RegsterSuccess(Userbean userbean);

    void RegsterFail(int i);
}
