package team.far.footing.presenter;

import team.far.footing.ui.vu.ISuggestionVu;

/**
 * Created by moi on 2015/8/24.
 */
public class SuggestionPresenter {

    private ISuggestionVu v;

    public SuggestionPresenter(ISuggestionVu v) {
        this.v = v;
    }

    public void sendSuggestion(String content) {
        // 发出请求，显示进度条,回调成功失败
    }

}
