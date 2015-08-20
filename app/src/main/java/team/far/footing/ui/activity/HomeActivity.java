package team.far.footing.ui.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.listener.FindListener;
import team.far.footing.R;
import team.far.footing.app.BaseActivity;
import team.far.footing.model.bean.MapBean;
import team.far.footing.model.bean.MessageBean;
import team.far.footing.model.bean.Userbean;
import team.far.footing.model.impl.MapModel;
import team.far.footing.presenter.HomePresenter;
import team.far.footing.ui.adapter.HomePagerAdapter;
import team.far.footing.ui.fragment.FriendsFragment;
import team.far.footing.ui.fragment.SquareFragment;
import team.far.footing.ui.fragment.TodayFragment;
import team.far.footing.ui.vu.IHomeVu;
import team.far.footing.ui.widget.CircleImageView;
import team.far.footing.util.BmobUtils;
import team.far.footing.util.LogUtils;
import team.far.footing.util.ScreenUtils;

public class HomeActivity extends BaseActivity implements IHomeVu, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.tabLayout)
    TabLayout mTabLayout;
    @InjectView(R.id.fabBtn_home)
    FloatingActionButton mFabBtn;
    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @InjectView(R.id.view_pager)
    ViewPager mViewPager;
    @InjectView(R.id.tv_home_user_name)
    TextView userName;
    @InjectView(R.id.tv_home_user_lv)
    TextView userLV;
    @InjectView(R.id.iv_home_user_image)
    ImageView userPic;
    @InjectView(R.id.tv_home_user_signature)
    TextView userSignature;
    @InjectView(R.id.navigation)
    NavigationView navigation;
    @InjectView(R.id.home_bar)
    LinearLayout homeBar;
    @InjectView(R.id.iv_home_toolbar_user_image)
    CircleImageView mToolbarUserImage;
    @InjectView(R.id.tv_home_toolbar_user_name)
    TextView mToolbarUserName;
    @InjectView(R.id.home_drawer_head)
    RelativeLayout mDrawerHead;
    @InjectView(R.id.btn_home_drawer)
    LinearLayout mDrawerBtn;
    private HomePresenter presenter;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private HomePagerAdapter fragmentPagerAdapter;
    private boolean isDrawerOpened;
    // 保存page的选择，默认为第一页
    private int pageSelect = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);
        noUseBarTint();
        initToolbar();
        init();
        presenter = new HomePresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onRelieveView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.refreshUserInformation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    private void initToolbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            homeBar.setPadding(0, ScreenUtils.getStatusHeight(this), 0, 0);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        Test();

    }

    private void init() {
        mFabBtn.setOnClickListener(this);
        fragmentList.add(new TodayFragment());
        fragmentList.add(new FriendsFragment());
        fragmentList.add(new SquareFragment());
        fragmentPagerAdapter = new HomePagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(fragmentPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                pageSelect = position;
                switch (position) {
                    case 0:
                        mFabBtn.setImageResource(R.mipmap.ic_run);
                        break;
                    case 1:
                        mFabBtn.setImageResource(R.mipmap.ic_person_add);
                        break;
                    case 2:
                        mFabBtn.setImageResource(R.mipmap.ic_plus);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        // 去死吧！接受我的愤怒吧！tabLayout
        // 这货的绑定viewpager之后的title不是自己的，是adapter给的
        // 没有找到返回图标的方法，字还那么小一个
        // 这里总耗时：48min，先让你呆在这里，之后我不把你换掉我不是人！！！
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(fragmentPagerAdapter);
        mTabLayout.setTabTextColors(getResources().getColor(R.color.white), getResources()
                .getColor(R.color.white));
        mDrawerLayout.setDrawerShadow(getResources()
                .getDrawable(R.drawable.drawer_shadow), GravityCompat.START);
        mDrawerBtn.setOnClickListener(this);
        mDrawerHead.setOnClickListener(this);
        if (navigation != null) {
            navigation.setNavigationItemSelectedListener(this);
        }

        if (ScreenUtils.checkDeviceHasNavigationBar(this)) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM | Gravity.RIGHT);
            layoutParams.setMargins(0, 0, (int) (getResources()
                    .getDimension(R.dimen.codelab_fab_margin_right)), (int) (getResources()
                    .getDimension(R.dimen.codelab_fab_margin_bottom) + ScreenUtils.getNavigationBarHeight(this)));
            mFabBtn.setLayoutParams(layoutParams);
        }
    }

    @Override
    public void showUserInformation(Userbean userbean, List<MessageBean> messageBeanList) {
        LogUtils.d(userbean.getUsername());
        if (!(userbean.getNickName() == null)) {
            userName.setText(userbean.getNickName());
            mToolbarUserName.setText(userbean.getNickName());
        } else {
            userName.setText("未取名");
            mToolbarUserName.setText("未取名");
        }

        userLV.setText("Lv." + userbean.getLevel());
        userSignature.setText(userbean.getSignature());
        /**
         * 展示历史消息
         * */

    }

    private void refreshUserInforimation() {
        presenter.refreshUserInformation();
    }


    /**
     * 任务
     */
    @Override
    public void showMission() {

    }

    @Override
    public void showUserImg(Bitmap bitmap) {
        userPic.setImageBitmap(bitmap);
        mToolbarUserImage.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabBtn_home:
                switch (pageSelect) {
                    case 0:
                        presenter.startWalkActivity(this);
                        break;
                    case 1:
                        presenter.startAddFriendActivity(this);
                        break;
                    case 2:
                        Toast.makeText(this, "广场测试", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case R.id.btn_home_drawer:
                if (isDrawerOpened) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.home_drawer_head:
                presenter.startUserInfoActivity(this);
                break;


        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.item_drawer_my_info:
                presenter.startUserInfoActivity(this);
                break;
            case R.id.item_drawer_setting:
                presenter.startSettingActivity(this);
                break;
            case R.id.item_drawer_my_map:
                presenter.startMyMapActivity(this);
                break;
        }
        return false;
    }


    public void Test() {


    }

}
