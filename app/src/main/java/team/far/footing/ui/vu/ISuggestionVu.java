package team.far.footing.ui.vu;

/**
 * Created by moi on 2015/8/24.
 */
public interface ISuggestionVu {

    void onSendProgress();

    void onSendSuccess();

    // i是错误码
    void onSendFail(int i);
}
