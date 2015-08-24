package team.far.footing.presenter;

import cn.bmob.v3.listener.SaveListener;
import team.far.footing.model.IFeedbackModel;
import team.far.footing.model.impl.FeedbackModel;
import team.far.footing.ui.vu.ISuggestionVu;

/**
 * Created by moi on 2015/8/24.
 */
public class SuggestionPresenter {

    private ISuggestionVu v;
    private IFeedbackModel feedbackModel;


    public SuggestionPresenter(ISuggestionVu v) {
        this.v = v;
        feedbackModel = FeedbackModel.getInstance();
    }

    public void sendSuggestion(String content) {
        v.onSendProgress();
        // 发出请求，显示进度条,回调成功失败
        feedbackModel.sendMsg(content, new SaveListener() {
            @Override
            public void onSuccess() {
                v.onSendSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                v.onSendFail(i);
            }
        });

    }

}
