package team.far.footing.ui.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import team.far.footing.R;
import team.far.footing.app.BaseActivity;
import team.far.footing.presenter.WalkPresenter;
import team.far.footing.service.MapService;
import team.far.footing.ui.vu.IWalkVu;

public class WalkActivity extends BaseActivity implements IWalkVu, View.OnClickListener {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.map_walk)
    MapView mMapView;
    @InjectView(R.id.card_walk_status)
    CardView cardWalkStatus;
    @InjectView(R.id.iv_walk_start)
    ImageView ivWalkStart;
    @InjectView(R.id.iv_walk_pause)
    ImageView ivWalkPause;
    @InjectView(R.id.iv_walk_stop)
    ImageView ivWalkStop;
    @InjectView(R.id.tv_walk_distance)
    TextView tvWalkDistance;
    private BaiduMap mBaiduMap;

    private WalkPresenter presenter;

    private MapService mapService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);
        ButterKnife.inject(this);
        initToolbar();
        initMap();
        init();
        presenter = new WalkPresenter(this);
    }

    private void init() {
        ivWalkStart.setOnClickListener(this);
        ivWalkPause.setOnClickListener(this);
        ivWalkStop.setOnClickListener(this);
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
        presenter.onRelieveView();
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
    public void showDistanceTotal(double distance) {
        if (distance != 0) {
            tvWalkDistance.setText(new DecimalFormat(".##").format(distance) + " m");
        } else {
            tvWalkDistance.setText("请稍微移动一下下哦~");
        }
    }

    @Override
    public void drawPolyline(ArrayList<LatLng> latLngs) {

        // 现在才反应过来……原来画线的方法每一次都重绘了整个图……走得时间长了会变得好卡好卡
        // 现在两点两点一画，但这样图就更抖了、还会出现断层、但是至少比界面卡住要好
        if (latLngs.size() > 1) {
            ArrayList<LatLng> tempLatLngs = new ArrayList<>();
            tempLatLngs.add(latLngs.get(latLngs.size() - 2));
            tempLatLngs.add(latLngs.get(latLngs.size() - 1));
            //构建用户绘制多边形的Option对象
            OverlayOptions polylineOptions = new PolylineOptions().points(tempLatLngs)
                    .color(getResources().getColor(R.color.accent_color));
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

    private void startWalk() {
        presenter.startWalk();
        ivWalkStart.setVisibility(View.GONE);
        cardWalkStatus.setVisibility(View.VISIBLE);
        ivWalkStop.setVisibility(View.VISIBLE);
        ivWalkPause.setVisibility(View.VISIBLE);

    }

    private void stopWalk() {
        presenter.stopWalk();
        cardWalkStatus.setVisibility(View.INVISIBLE);
        ivWalkStop.setVisibility(View.GONE);
        ivWalkPause.setVisibility(View.GONE);
        ivWalkStart.setVisibility(View.VISIBLE);
    }

    private void pauseWalk() {
        presenter.pauseWalk();
        ivWalkPause.setVisibility(View.GONE);
        ivWalkStart.setVisibility(View.VISIBLE);
    }

    // 分享的相关操作
    private void share() {
        mBaiduMap.snapshotScope(null, new BaiduMap.SnapshotReadyCallback() {

            @Override
            public void onSnapshotReady(Bitmap bitmap) {
                Uri uri = Uri.parse(MediaStore.Images.Media
                        .insertImage(getContentResolver(), bitmap, null, null));
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                shareIntent.setType("image/png");
                startActivity(Intent
                        .createChooser(shareIntent, getResources().getText(R.string.send_to)));

                // 停止步行
                startWalk();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_walk_start:
                startWalk();
                break;
            case R.id.iv_walk_pause:
                pauseWalk();
                break;
            case R.id.iv_walk_stop:
                new MaterialDialog.Builder(this)
                        .title("停止步行")
                        .content("是否分享此次步行？")
                        .positiveText("分享")
                        .negativeText("不用了")
                        .theme(Theme.LIGHT)
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                share();
                                dialog.dismiss();
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                stopWalk();
                                dialog.dismiss();
                            }
                        }).show();
                break;
        }
    }
}
