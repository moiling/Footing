package team.far.footing.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import me.drakeet.materialdialog.MaterialDialog;
import team.far.footing.R;
import team.far.footing.app.BaseActivity;
import team.far.footing.model.bean.MapBean;
import team.far.footing.presenter.ShowMapPresenter;
import team.far.footing.ui.vu.IShowMapVu;
import team.far.footing.ui.widget.HorizontalProgressBarWithNumber;
import team.far.footing.util.LogUtils;
import team.far.footing.util.TimeUtils;

public class ShowMapActivity extends BaseActivity implements IShowMapVu {

    @InjectView(R.id.tv_starttime)
    TextView tvStarttime;
    @InjectView(R.id.tv_distance)
    TextView tvDistance;
    @InjectView(R.id.tv_alltime)
    TextView tvAlltime;
    @InjectView(R.id.map)
    MapView mMapView;
    @InjectView(R.id.tv_start_city)
    TextView tvStartCity;
    @InjectView(R.id.tv_start_address)
    TextView tvStartAddress;
    @InjectView(R.id.ll_position)
    LinearLayout llPosition;
    @InjectView(R.id.tv_start_street) TextView tvStartStreet;
    @InjectView(R.id.toolbar) Toolbar mToolbar;
    @InjectView(R.id.layout_item) RelativeLayout layoutItem;

    private BaiduMap mBaiduMap;
    private ShowMapPresenter presenter;
    private HorizontalProgressBarWithNumber barWithNumber;
    private MaterialDialog materialDialog;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map);
        ButterKnife.inject(this);
        initMap();
        presenter = new ShowMapPresenter(this, (MapBean) getIntent().getSerializableExtra("map"));
        initToolbar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show_map, menu);
        return true;
    }

    private void initMap() {
        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16.5f);
        mBaiduMap = mMapView.getMap();
        // 隐藏比例尺
        mMapView.showScaleControl(false);
        // 隐藏缩放控件
        mMapView.showZoomControls(false);
        // 事实证明百度地图的logo是可以隐藏的……百度会不会不允许通过呢？先这样吧……
        // mMapView.removeViewAt(1);
        // 缩放比例
        mBaiduMap.setMapStatus(msu);
    }

    private void initToolbar() {
        mToolbar.setTitle("我的足迹");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                new com.afollestad.materialdialogs.MaterialDialog.Builder(ShowMapActivity.this).title("分享").content("是否分享足迹？")
                        .backgroundColor(getResources().getColor(R.color.white))
                        .neutralText("其他分享")
                        .positiveText("QQ分享").negativeText("取消").theme(Theme.LIGHT)
                        .neutralColor(getResources().getColor(R.color.divider_color))
                        .callback(new com.afollestad.materialdialogs.MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(com.afollestad.materialdialogs.MaterialDialog dialog) {
                                presenter.QQshare();
                                dialog.dismiss();
                            }

                            @Override
                            public void onNeutral(com.afollestad.materialdialogs.MaterialDialog dialog) {
                                share();
                                super.onNeutral(dialog);
                            }
                        }).show();
                return false;
            }
        });
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
        if (materialDialog != null) materialDialog.dismiss();
        materialDialog = null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
        if (materialDialog != null) materialDialog.dismiss();
        materialDialog = null;
    }

    // 其他分享
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
            }
        });
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
    public void showWalkInfo(String allTime, String allDistance, String startTime, String ct, String ad, String street) {
        if (ct != null) {
            tvStartAddress.setText(ct);
        } else {
            tvStartAddress.setText("未知区");
        }
        if (ad != null) {
            tvStartCity.setText(ad);
        } else {
            tvStartCity.setText("未知市");
        }
        if (street != null) {
            tvStartStreet.setText(street);
        } else {
            tvStartStreet.setText("未知路");
        }
        tvStartCity.setText(ct);
        tvStartAddress.setText(ad);
        tvAlltime.setText(TimeUtils.formatTime(Long.parseLong(allTime)));
        tvDistance.setText(new DecimalFormat("0.##").format((Double.parseDouble(allDistance) / 1000)) + "km");
        tvStarttime.setText(startTime);
    }

    @Override
    public BaiduMap getBaiduMap() {
        return mBaiduMap;
    }

    @Override
    public Activity getActivity() {
        return ShowMapActivity.this;
    }

    @Override
    public void show_shareProgress(int progress) {
        if (materialDialog == null) showdialog();
        barWithNumber.setProgress(progress);
    }

    @Override
    public void show_shareSuccess() {
        dismissProgress();
        materialDialog = null;
    }

    @Override
    public void show_shareError() {
        textView.setText("分享失败,请稍后重试。");
        barWithNumber.setVisibility(View.GONE);
        materialDialog = null;
    }

    @Override
    public void show_shareCancel() {
        dismissProgress();
        materialDialog = null;
    }

    private void showdialog() {
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null, false);
        barWithNumber = (HorizontalProgressBarWithNumber) v.findViewById(R.id.progressBar);
        textView = (TextView) v.findViewById(R.id.tv_text);
        materialDialog = new MaterialDialog(ShowMapActivity.this).setView(v);
        materialDialog.show();
    }

    public void dismissProgress() {
        materialDialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.getTencent().onActivityResult(requestCode, resultCode, data);

    }
}
