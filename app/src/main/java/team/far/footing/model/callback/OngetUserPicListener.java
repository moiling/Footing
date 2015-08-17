package team.far.footing.model.callback;

import android.graphics.Bitmap;

/**
 * Created by luoyy on 2015/8/17 0017.
 */
public interface OngetUserPicListener {
    void onSucess(Bitmap bitmap);

    void onError();
}
