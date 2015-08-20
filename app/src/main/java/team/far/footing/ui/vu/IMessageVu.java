package team.far.footing.ui.vu;

import java.util.List;

import team.far.footing.model.bean.MessageBean;

/**
 * Created by moi on 2015/8/20.
 */
public interface IMessageVu {

    void showMessage(List<MessageBean> list);

    void onMsgSuccess(String s);

    void onMsgFail(String s);
}
