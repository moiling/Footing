package team.far.footing.util.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;

import team.far.footing.model.callback.OnAnimationEndListener;

/**
 * Created by moi on 2015/8/23.
 */
public class ScaleXYAnimation {

    public static void show(View view, final OnAnimationEndListener listener) {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(view, "scaleX",
                0f, 1f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(view, "scaleY",
                0f, 1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(500);
        animSet.setInterpolator(new BounceInterpolator());
        animSet.playTogether(anim1, anim2);
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (listener != null) {
                    listener.onEnd();
                }
            }
        });
        animSet.start();
    }

    public static void hide(View view, final OnAnimationEndListener listener) {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(view, "scaleX",
                1f, 0f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(view, "scaleY",
                1f, 0f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(300);
        animSet.setInterpolator(new AccelerateInterpolator());
        animSet.playTogether(anim1, anim2);
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (listener != null) {
                    listener.onEnd();
                }
            }
        });
        animSet.start();
    }

}
