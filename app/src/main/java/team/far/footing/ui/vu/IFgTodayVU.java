package team.far.footing.ui.vu;

import java.util.List;

import team.far.footing.model.bean.Userbean;

/**
 * Created by luoyy on 2015/8/13 0013.
 */
public interface IFgTodayVU {
    void init(Userbean CurrentUser, List<Userbean> userbeanList);

    void oninit_error(int code, String msg);

    void onclickwork();

    void choose_distance(List<Userbean> userbeanList);

    void choose_alldistance(List<Userbean> userbeanList);

    void choose_level(List<Userbean> userbeanList);
}
