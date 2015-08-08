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
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.extras.toolbar.MaterialMenuIconToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import team.far.footing.R;
import team.far.footing.app.APP;
import team.far.footing.app.BaseActivity;
import team.far.footing.ui.adpter.HomePagerAdapter;
import team.far.footing.ui.fragment.FriendsFragment;
import team.far.footing.ui.fragment.SquareFragment;
import team.far.footing.ui.fragment.WalkFragment;

public class HomeActivity extends BaseActivity {

    @InjectView(R.id.toolbar) Toolbar mToolbar;
    @InjectView(R.id.tabLayout) TabLayout mTabLayout;
    @InjectView(R.id.fabBtn) FloatingActionButton mFabBtn;
    @InjectView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @InjectView(R.id.view_pager) ViewPager mViewPager;
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
        init();
        findViewById(R.id.iv_user_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(APP.getContext(), "测试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initToolbar() {
        mToolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle your drawable state here
                switch (materialMenu.getDrawable().getIconState()) {
                    case BURGER:
                        mDrawerLayout.openDrawer(GravityCompat.START);
                        break;
                    case ARROW:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case X:
                        break;
                    case CHECK:
                        break;
                }

            }
        });
        materialMenu = new MaterialMenuIconToolbar(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN) {
            @Override
            public int getToolbarViewId() {
                return R.id.toolbar;
            }
        }; // or retrieve from your custom view, etc
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

}
