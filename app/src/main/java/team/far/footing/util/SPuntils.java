package team.far.footing.util;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import team.far.footing.app.APP;

/**
 * Created by luoyy on 2015/8/25 0025.
 */
public class SPuntils {
    public static final void setAllow(boolean b) {
        SharedPreferences mySharedPreferences = APP.getContext().getSharedPreferences("set", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putBoolean("allow_msg", b);
        editor.commit();
    }


    public static final boolean gteAllow() {
        SharedPreferences mySharedPreferences = APP.getContext().getSharedPreferences("set", Activity.MODE_PRIVATE);
        boolean isAllow = mySharedPreferences.getBoolean("allow_msg", true);
        return isAllow;
    }


}
