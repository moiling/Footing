package team.far.footing.model;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by luoyy on 2015/8/24 0024.
 */
public interface IFeedbackModel {

    void sendMsg(String msg,SaveListener saveListener);
}
