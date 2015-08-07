package team.far.footing.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by moi on 2015/8/7.
 */
public class APP extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
