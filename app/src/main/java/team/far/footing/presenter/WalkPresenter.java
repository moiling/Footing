package team.far.footing.presenter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;

import java.util.ArrayList;
import java.util.List;

import team.far.footing.app.APP;
import team.far.footing.model.bean.MapBean;
import team.far.footing.service.MapService;
import team.far.footing.ui.vu.IWalkVu;
import team.far.footing.util.LogUtils;

/**
 * Created by moi on 2015/8/10.
 */
public class WalkPresenter {

    private IWalkVu v;
    // 定位
    private LocationClient mLocationClient;
    private MyLocationListener mLocationListener;
    private boolean isFirstIn = true;
    // 存放绘制路线的端点
    private ArrayList<LatLng> latLngs = new ArrayList<>();
    private double firstDistance = 0;
    private int span = 5;
    private double acceleration = 0;
    private boolean isWalking = false;
    private double distanceTotal = 0;
    private ServiceConnection serviceConnection;
    private MapService mapService;
    private List<String> list_map = new ArrayList<>();
    private MapBean mapBean;
    private Intent intent;

    public WalkPresenter(IWalkVu v) {
        this.v = v;
        // 定位
        initLocation();
        start_service();
    }

    // 开始定位
    public void startLocation() {
        v.getBaiduMap().setMyLocationEnabled(true);
    }

    // 停止定位
    public void stopLocation() {
        v.getBaiduMap().setMyLocationEnabled(false);
    }

    public void startWalk() {
        isWalking = true;
        start_service();
    }

    // 停止步行这里还有些麻烦，第二段开始走得时候没法保存第一段的数据
    // 之后好友内分享估计会遇到麻烦……
    public void pauseWalk() {
        pause_service();
        isWalking = false;
        // 坐标点清零
        latLngs.clear();
    }

    public void stopWalk() {
        isWalking = false;
        // 坐标点清零
        latLngs.clear();
        // 清除地图上的轨迹
        v.getBaiduMap().clear();
        // 总距离清零
        distanceTotal = 0;
        // 总距离的显示清零
        v.showDistanceTotal(distanceTotal);
    }

    private void initLocation() {
        mLocationClient = APP.getLocationClient();
        mLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mLocationListener);

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
        mLocationClient.start();
        mLocationClient.setLocOption(option);
    }


    //开启service
    public void start_service() {
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mapService = ((MapService.MyBinder) service).getService();
                LogUtils.e("得到service对象的实例");
                //取得service中保存的mapBean。
                mapService.start_Location();
                mapBean = mapService.getLast_mapBean();
                list_map = mapService.getList_map();
                //加个标志来判断是否是刚进入  还是退出后的再次进入
                //latLngs = StringUntils.getLaLngs(mapService.getList_map());
                //展示数据
                //v.startWalk();
                //  v.drawPolyline(latLngs);
                // v.showDistanceTotal(1000.2);


            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mapService = null;

            }
        };
        if (intent == null)
            intent = new Intent(APP.getContext(), MapService.class);
        APP.getContext().startService(intent);
        APP.getContext().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);


    }

    public void end_service() {
        mapService.stop_Location(distanceTotal);
    }

    public void pause_service() {
        mapService.pause_Location(distanceTotal);
    }

    // 解除view的绑定
    public void onRelieveView() {
        v = null;
    }


    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            LogUtils.e(bdLocation.getAltitude() + "  activity " + bdLocation.getLatitude() + "");
            MyLocationData data = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    .latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude())
                    .build();

            // 在view没有刷出来的时候、什么都不要做
            if (v != null) {
                v.getBaiduMap().setMyLocationData(data);
                //MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, )

                LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                // 第一次定位把镜头移向用户当前位置
                if (isFirstIn) {
                    v.moveCamera2Location(latLng);
                    isFirstIn = false;
                }

                if (isWalking) {
                    // 先不管怎么样，加入第一个点再说
                    if (latLngs.isEmpty()) latLngs.add(latLng);
                    // 先从距离和加速度两方面控制某一点是否添加到数组中
                    // 还是有些问题，如果第二点是跳点的话没法判断加速度把它删去，所以要先开启定位再开启绘制工作
                    // 当前距离
                    double secondDistance = 0;
                    if (latLngs.size() > 0) {
                        secondDistance = DistanceUtil
                                .getDistance(latLng, latLngs.get(latLngs.size() - 1));
                    }
                    // 距离大于10
                    if (secondDistance >= 10) {
                        if (firstDistance == 0) {
                            latLngs.add(latLng);
                            v.drawPolyline(latLngs);
                            firstDistance = secondDistance;
                        } else {
                            // 加速度
                            acceleration = (secondDistance - firstDistance) / (span * span);
                            // 加速度小于10
                            if (acceleration <= 10) {
                                latLngs.add(latLng);
                                v.drawPolyline(latLngs);
                                firstDistance = secondDistance;
                                distanceTotal += secondDistance;
                                v.showDistanceTotal(distanceTotal);
                            }
                        }
                    }
                }
            }
        }
    }

}
