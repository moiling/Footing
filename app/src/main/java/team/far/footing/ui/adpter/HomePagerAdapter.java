package team.far.footing.ui.adpter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moi on 2015/8/8.
 */
public class HomePagerAdapter extends BaseFragmentPagerAdapter {

    private ArrayList<String> homeTabs = new ArrayList<>();

    public HomePagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm, fragmentList);
        homeTabs.add("步行");
        homeTabs.add("好友");
        homeTabs.add("广场");
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return homeTabs.get(position);
    }
}
