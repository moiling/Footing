package team.far.footing.presenter;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;

import java.util.ArrayList;

import team.far.footing.app.APP;
import team.far.footing.ui.vu.IWalkVu;

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

    public WalkPresenter(IWalkVu v) {
        this.v = v;
        // 定位
        initLocation();
    }

    // 开始定位
    public void startLocation() {
        v.getBaiduMap().setMyLocationEnabled(true);
        mLocationClient.start();
    }

    // 停止定位
    public void stopLocation() {
        v.getBaiduMap().setMyLocationEnabled(false);
        mLocationClient.stop();
    }

    public void startWalk() {
        isWalking = true;
    }

    public void pauseWalk() {
        isWalking = false;
    }

    public void stopWalk() {
        isWalking = false;
        latLngs.clear();
        distanceTotal = 0;
    }

    private void initLocation() {
        mLocationClient = new LocationClient(APP.getContext());
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
        mLocationClient.setLocOption(option);
    }

    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            MyLocationData data = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    .latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude())
                    .build();
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
                // 距离大于15
                if (secondDistance >= 15) {
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

    // 解除view的绑定
    public void onRelieveView() {
        v = null;
    }
}