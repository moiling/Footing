package team.far.footing.app;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;

import cn.bmob.v3.Bmob;
import team.far.footing.R;
import team.far.footing.util.LogUtils;

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
        Bmob.initialize(this, context.getString(R.string.Bmob_Key));
        // 初始化百度SDK
        SDKInitializer.initialize(context);
        // 初始化Log工具……是不是把工具放在一个文件里更好一点呢……
        LogUtils.isDebug = true;
    }

    public static Context getContext() {
        return context;
    }
}
