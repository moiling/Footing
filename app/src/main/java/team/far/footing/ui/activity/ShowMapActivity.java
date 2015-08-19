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
import com.balysv.materialripple.MaterialRippleLayout;

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

    private MapBean mapBean;

    private ShowMapPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map);
        ButterKnife.inject(this);
        mapBean = (MapBean) getIntent().getSerializableExtra("map");
        initMap();
        init();
        presenter = new ShowMapPresenter(this);
    }

    private void init() {
        tvAlltime.setText("总时间： " + TimeUtils.formatTime(Long.parseLong(mapBean.getAll_time())));
        tvDistance.setText("总距离： " + mapBean.getAll_distance() + " m");
        tvStarttime.setText("时间：" + mapBean.getStart_time());
    }

    private void initMap() {
       
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
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

}
