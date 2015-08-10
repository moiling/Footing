package team.far.footing.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import team.far.footing.R;
import team.far.footing.app.BaseActivity;
import team.far.footing.presenter.WalkPresenter;
import team.far.footing.ui.vu.IWalkVu;

public class WalkActivity extends BaseActivity implements IWalkVu {

    @InjectView(R.id.toolbar) Toolbar mToolbar;
    @InjectView(R.id.map_walk) MapView mMapView;
    private BaiduMap mBaiduMap;

    private WalkPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_walk);
        ButterKnife.inject(this);
        initToolbar();
        initMap();
        presenter = new WalkPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 开始定位
        presenter.startLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //停止定位
        presenter.stopLocation();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    private void initMap() {
        mBaiduMap = mMapView.getMap();
        // 隐藏比例尺
        mMapView.showScaleControl(false);
        // 隐藏缩放控件
        mMapView.showZoomControls(false);
        // 事实证明百度地图的logo是可以隐藏的……百度会不会不允许通过呢？先这样吧……
        mMapView.removeViewAt(1);
        // 缩放比例
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(18f);
        mBaiduMap.setMapStatus(msu);
    }

    private void initToolbar() {
        mToolbar.setTitle(getResources().getString(R.string.walk));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void drawPolyline(ArrayList<LatLng> latLngs) {
        if (latLngs.size() > 1) {
            //构建用户绘制多边形的Option对象
            OverlayOptions polylineOptions = new PolylineOptions().points(latLngs).color(getResources().getColor(R.color.accent_color));
            //在地图上添加多边形Option，用于显示
            mBaiduMap.addOverlay(polylineOptions);
        }
    }

    @Override
    public void moveCamera2Location(LatLng latLng) {
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        // 这里不能用动画切换镜头，停留在天安门的时间会变长，体验不好，但是现在也还是会闪屏一下……
        mBaiduMap.setMapStatus(msu);
    }

    @Override
    public BaiduMap getBaiduMap() {
        return mBaiduMap;
    }
}
