package team.far.footing.model.callback;

/**
 * Created by Luoyy on 2015/8/9 0009.
 */
public interface OnUpdateUserListener {
    void onSuccess();

    void onFailure(int i, String s);
}
