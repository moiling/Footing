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
import com.baidu.mapapi.utils.DistanceUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import team.far.footing.app.APP;
import team.far.footing.model.Listener.OnUpdateMapListener;
import team.far.footing.model.bean.MapBean;
import team.far.footing.model.impl.MapModel;
import team.far.footing.util.BmobUtils;
import team.far.footing.util.DensityUtils;
import team.far.footing.util.LogUtils;
import team.far.footing.util.StringUntils;
import team.far.footing.util.TimeUtils;

/**
 * Created by luoyy on 2015/8/14 0014.
 */
public class MapService extends Service {

    public static int STATUS = 0;
    private final IBinder myBinder = new MyBinder();
    private LocationClient mLocationClient;
    private List<String> list_map = new ArrayList<>();

    private Date start_date, end_date;

    private MapBean last_mapBean = null;
    private OnUpdateMapListener onUpdateMapListener;
    private Double distance = 0.0;
    private boolean isFirstIn = true;
    private ArrayList<LatLng> latLngs = new ArrayList<>();

    public ArrayList<LatLng> getLatLngs() {
        return latLngs;
    }

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
        start_Location();
        onUpdateMapListener = new OnUpdateMapListener() {
            @Override
            public void onSuccess(MapBean mapBean) {
                last_mapBean = mapBean;
                LogUtils.e("上传成功");
            }

            @Override
            public void onFailure(int i, String s) {
                LogUtils.e("上传成功：", s);
            }
        };
        LogUtils.e("开启service");
        start_date = TimeUtils.getcurrentTime();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.e("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e("Service关闭");
    }

    public Double getDistance() {
        return distance;
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
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                LogUtils.e(bdLocation.getAltitude() + "=" + bdLocation.getLatitude() + "");
                list_map.add(bdLocation.getAltitude() + "=" + bdLocation.getLatitude());
                latLngs.add(new LatLng(bdLocation.getAltitude(), bdLocation.getLatitude()));
                if (isFirstIn) {
                    isFirstIn = false;
                } else {
                    try {
                        distance += DistanceUtil
                                .getDistance(latLngs.get(latLngs.size() - 1), latLngs.get(latLngs.size() - 2));
                    } catch (Exception e) {

                    }

                }
                STATUS = 1;
            }
        });
        mLocationClient.requestLocation();
    }

    public void start_Location() {
        mLocationClient.start();
    }

    public void stop_Location(double distanceTotal) {
        STATUS = 3;
        isFirstIn = true;
        mLocationClient.stop();
        end_date = TimeUtils.getcurrentTime();
        //如果last_mapBean为null,表明service没有暂停过 --->  直接上传
        if (last_mapBean == null) {
            //把数据传到服务器
            MapModel.getInstance().save_map_finish(
                    BmobUtils.getCurrentUser(), "url", "filename", list_map, end_date.getTime() - start_date.getTime() + "",
                    distanceTotal + "", TimeUtils.dateToString(start_date), onUpdateMapListener);
        }
        //如果last_mapBean不为null,表明service暂停过 --->  更新
        else {
            MapModel.getInstance().save_map_again(last_mapBean.getObjectId(), list_map, end_date.getTime() - start_date.getTime() + "",
                    distanceTotal + "", onUpdateMapListener);
            LogUtils.e("更新上传");
        }
        last_mapBean = null;
        latLngs.clear();
        list_map.clear();
        end_date = null;


    }

    public void pause_Location(double distanceTotal) {
        STATUS = 2;
        mLocationClient.stop();
        end_date = TimeUtils.getcurrentTime();
        //把数据传到服务器
        MapModel.getInstance().save_map_finish(
                BmobUtils.getCurrentUser(), "url", "filename", list_map, end_date.getTime() - start_date.getTime() + "",
                distanceTotal + "", TimeUtils.dateToString(start_date), onUpdateMapListener);

    }


    public List<String> getList_map() {
        return list_map;
    }

    public MapBean getLast_mapBean() {
        return last_mapBean;
    }
}
