package team.far.footing.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.util.ArrayList;
import java.util.List;

import team.far.footing.app.APP;
import team.far.footing.util.LogUtils;

/**
 * Created by luoyy on 2015/8/14 0014.
 */
public class MapService extends Service {
    private final IBinder myBinder = new MyBinder();
    private LocationClient mLocationClient;

    private List<String> list_map = new ArrayList<>();

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
        initLocation();
    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void initLocation() {
        mLocationClient = APP.getLocationClient();
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        // stop时不杀死service
        option.setIgnoreKillProcess(true);
        // 请求的频率
        option.setScanSpan(5 * 1000);
        mLocationClient.setLocOption(option);
        LogUtils.e("监听注册成功");
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                LogUtils.e(bdLocation.getAltitude() + "   " + bdLocation.getLatitude() + "");
                list_map.add(bdLocation.getAltitude() + "   " + bdLocation.getLatitude());
            }
        });
        mLocationClient.requestLocation();
    }


}
