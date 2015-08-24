package team.far.footing.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import team.far.footing.app.APP;

/**
 * Created by luoyy on 2015/8/23.
 */

public class MapService extends Service {
    private LocationClient mLocationClient;
    private MyLocationListener mLocationListener;
    private int span = 3;
    private boolean isWalking = false;

    public final MyBind myBinder = new MyBind();

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    public class MyBind extends Binder {
        public MapService getMapService() {
            return MapService.this;
        }
    }

    public void initMapService() {
        mLocationClient = new LocationClient(APP.getContext());
        mLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mLocationListener);

        //设置 option
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        // stop时不杀死service
        option.setIgnoreKillProcess(true);
        // 请求的频率
        option.setScanSpan(span * 1000);
        mLocationClient.setLocOption(option);
    }

    public void start_location() {
        mLocationClient.start();
    }

    public void stop_location() {
        mLocationClient.stop();
    }

    public boolean isWalking() {
        return isWalking;
    }

    public void setIsWalking(boolean isWalking) {
        this.isWalking = isWalking;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initMapService();
        start_location();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            Intent intent = new Intent("android.intent.action.MY_BROADCAST");
            intent.putExtra("Latitude", bdLocation.getLatitude());
            intent.putExtra("Longitude", bdLocation.getLongitude());
            intent.putExtra("city", bdLocation.getCity());
            intent.putExtra("address", bdLocation.getAddrStr());
            intent.putExtra("Radius", bdLocation.getRadius());
            sendBroadcast(intent);
        }
    }


}
