package team.far.footing.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.balysv.materialripple.MaterialRippleLayout;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import team.far.footing.R;
import team.far.footing.model.bean.MapBean;
import team.far.footing.presenter.ShowMapPresenter;
import team.far.footing.ui.vu.IShowMapVu;
import team.far.footing.util.LogUtils;
import team.far.footing.util.TimeUtils;

public class ShowMapActivity extends AppCompatActivity implements IShowMapVu {

    @InjectView(R.id.tv_starttime)
    TextView tvStarttime;
    @InjectView(R.id.tv_distance)
    TextView tvDistance;
    @InjectView(R.id.tv_alltime)
    TextView tvAlltime;
    @InjectView(R.id.ripple)
    MaterialRippleLayout ripple;
    @InjectView(R.id.CV_fg_today)
    CardView CVFgToday;
    @InjectView(R.id.map)
    MapView mMapView;
    @InjectView(R.id.bt_share)
    Button btShare;

    private BaiduMap mBaiduMap;

    private ShowMapPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map);
        ButterKnife.inject(this);
        initMap();
        presenter = new ShowMapPresenter(this, (MapBean) getIntent().getSerializableExtra("map"));
    }

    private void initMap() {
        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16.5f);
        mBaiduMap.setMapStatus(msu);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        presenter.onRelieveView();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void showMap(ArrayList<LatLng> latLngs) {
        if (latLngs.size() > 1) {
            // 得到数组中点
            LatLng middle = latLngs.get(latLngs.size() / 2);
            LogUtils.d(latLngs.get(latLngs.size() / 2).toString());
            // 将地图中心点和数组中点对齐
            MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(middle);
            mBaiduMap.animateMapStatus(msu);
            OverlayOptions polylineOptions = new PolylineOptions().points(latLngs)
                    .color(getResources().getColor(R.color.accent_color)).width(10);
            mBaiduMap.addOverlay(polylineOptions);
        }
    }

    @Override
    public void showWalkInfo(String allTime, String allDistance, String startTime) {
        tvAlltime.setText("总时间： " + TimeUtils.formatTime(Long.parseLong(allTime)));
        tvDistance.setText("总距离： " + allDistance + " m");
        tvStarttime.setText("时间：" + startTime);
    }
}
