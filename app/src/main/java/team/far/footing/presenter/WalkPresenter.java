package team.far.footing.presenter;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;

import team.far.footing.app.APP;
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
        // 1s一次
        option.setScanSpan(5 * 1000);
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
            latLngs.add(latLng);
            LogUtils.d("walk_map", latLng.toString());
            v.drawPolyline(latLngs);
        }
    }

    // 解除view的绑定
    public void onRelieveView() {
        v = null;
    }
}
