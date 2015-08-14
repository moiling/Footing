package team.far.footing.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * Created by luoyy on 2015/8/14 0014.
 */
public class MapService extends Service {
    private final IBinder myBinder = new MyBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    public class MyBinder extends Binder {
        public MapService getService() {
            return MapService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void start_record() {


    }


}
