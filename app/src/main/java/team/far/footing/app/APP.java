package team.far.footing.app;

import android.app.Application;
import android.content.Context;

import cn.bmob.v3.Bmob;
import team.far.footing.R;
import team.far.footing.config.API;

/**
 * Created by moi on 2015/8/7.
 */
public class APP extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        // 初始化 Bmob SDK
        Bmob.initialize(this,context.getString(R.string.Bmob_Key));

    }

    public static Context getContext() {
        return context;
    }
}
