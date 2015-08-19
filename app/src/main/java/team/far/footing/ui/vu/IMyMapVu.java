package team.far.footing.ui.vu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

/**
 * Created by luoyy on 2015/8/19 0019.
 */
public interface IMyMapVu {

    void showmaplist(RecyclerView.Adapter adapter);

    void showLoading(String s);
    void stopLoading();
    Context getActivity();
}
