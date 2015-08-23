package team.far.footing.util.animation;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by moi on 2015/8/23.
 */
public class YAnimation {

    public static void topIN(View view) {
        float mY = view.getY();
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(view, "y", -500f -mY, mY);
        anim1.setDuration(300);
        anim1.setInterpolator(new AccelerateDecelerateInterpolator());
        anim1.start();
    }

    public static void buttomIN(View view) {
        float mY = view.getY();
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(view, "y", 500f + mY, mY);
        anim1.setDuration(300);
        anim1.setInterpolator(new AccelerateDecelerateInterpolator());
        anim1.start();
    }
}
