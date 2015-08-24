package team.far.footing.model.impl;

import cn.bmob.v3.listener.SaveListener;
import team.far.footing.app.APP;
import team.far.footing.model.IFeedbackModel;
import team.far.footing.model.bean.Feedback;
import team.far.footing.util.BmobUtils;

/**
 * Created by luoyy on 2015/8/24 0024.
 */
public class FeedbackModel implements IFeedbackModel {
    public static final FeedbackModel instance = new FeedbackModel();

    private FeedbackModel() {
    }

    public static FeedbackModel getInstance() {
        return instance;
    }


    @Override
    public void sendMsg(String msg, SaveListener saveListener) {
        Feedback feedback = new Feedback();
        feedback.setUserbean(BmobUtils.getCurrentUser());
        feedback.setMsg(msg);
        feedback.save(APP.getContext(), saveListener);
    }


}
