package team.far.footing.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.extras.toolbar.MaterialMenuIconToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import team.far.footing.R;
import team.far.footing.app.BaseActivity;
import team.far.footing.model.bean.Userbean;
import team.far.footing.presenter.HomePresenter;
import team.far.footing.ui.adpter.HomePagerAdapter;
import team.far.footing.ui.fragment.FriendsFragment;
import team.far.footing.ui.fragment.SquareFragment;
import team.far.footing.ui.fragment.WalkFragment;
import team.far.footing.ui.vu.IHomeVu;
import team.far.footing.util.LogUtils;

public class HomeActivity extends BaseActivity implements IHomeVu {

    @InjectView(R.id.toolbar) Toolbar mToolbar;
    @InjectView(R.id.tabLayout) TabLayout mTabLayout;
    @InjectView(R.id.fabBtn) FloatingActionButton mFabBtn;
    @InjectView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @InjectView(R.id.view_pager) ViewPager mViewPager;
    @InjectView(R.id.tv_home_user_name) TextView userName;
    @InjectView(R.id.tv_home_user_lv) TextView userLV;
    @InjectView(R.id.iv_home_user_image) ImageView userPic;
    @InjectView(R.id.tv_home_user_signature) TextView userSignature;
    private HomePresenter presenter;
    private MaterialMenuIconToolbar materialMenu;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private HomePagerAdapter fragmentPagerAdapter;
    private boolean isDrawerOpened;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);
        initToolbar();
        initNavIcon();
        init();
        presenter = new HomePresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onRelieveView();
    }

    private void initNavIcon() {
        materialMenu = new MaterialMenuIconToolbar(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN) {
            @Override
            public int getToolbarViewId() {
                return R.id.toolbar;
            }
        };
        mDrawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                materialMenu.setTransformationOffset(MaterialMenuDrawable.AnimationState.BURGER_ARROW,
                        isDrawerOpened ? 2 - slideOffset : slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                isDrawerOpened = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                isDrawerOpened = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                if (newState == DrawerLayout.STATE_IDLE) {
                    if (isDrawerOpened) materialMenu.setState(MaterialMenuDrawable.IconState.ARROW);
                    else materialMenu.setState(MaterialMenuDrawable.IconState.BURGER);
                }
            }
        });
    }

    private void initToolbar() {
        mToolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDrawerOpened) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    private void init() {
        fragmentList.add(new WalkFragment());
        fragmentList.add(new FriendsFragment());
        fragmentList.add(new SquareFragment());
        fragmentPagerAdapter = new HomePagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(fragmentPagerAdapter);
        // 去死吧！接受我的愤怒吧！tabLayout
        // 这货的绑定viewpager之后的title不是自己的，是adapter给的
        // 没有找到返回图标的方法，字还那么小一个
        // 这里总耗时：48min，先让你呆在这里，之后我不把你换掉我不是人！！！
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(fragmentPagerAdapter);
        mTabLayout.setTabTextColors(getResources().getColor(R.color.white), getResources().getColor(R.color.white));
    }

    @Override
    public void showUserInformation(Userbean userbean) {
        LogUtils.d(userbean.getUsername());
        // TODO: 完善bean
        userName.setText(userbean.getNickName());
        //userPic.setImageBitmap();
        userLV.setText("Lv." + userbean.getLevel());
        userSignature.setText(userbean.getSignature());
    }

    @Override
    public void refreshUserInforimation() {
        presenter.refreshUserInformation();
    }

    @Override
    public void showMission() {

    }
}
