package team.far.footing.model.impl;

import android.content.Context;

import java.util.List;

import cn.bmob.v3.listener.SaveListener;
import team.far.footing.app.APP;
import team.far.footing.model.IMapModel;
import team.far.footing.model.Listener.OnUpdateUserListener;
import team.far.footing.model.bean.MapBean;
import team.far.footing.model.bean.Userbean;

/**
 * Created by luoyy on 2015/8/14 0014.
 */
public class MapModel implements IMapModel {
    @Override
    public void save_map_finish(Userbean userbean, String map_url, String map_file_name, List<String> map_list,
                                String all_time, String all_distance, String start_time, OnUpdateUserListener onUpdateUserListener) {
        MapBean mapBean = new MapBean();
        mapBean.setUserbean(userbean);
        mapBean.setAll_distance(all_distance);
        mapBean.setAll_time(all_time);
        mapBean.addAll("map_array", map_list);
        mapBean.setStart_time(start_time);
        mapBean.setMap_url(map_url);
        mapBean.setMap_file_name(map_file_name);
        update(mapBean, APP.getContext(), onUpdateUserListener);

    }

    @Override
    public void save_map_again(Userbean userbeans, List<String> map_list, String all_time, String all_distance, OnUpdateUserListener onUpdateUserListener) {
        MapBean mapBean = new MapBean();

    }


    public void update(MapBean mapBean, Context context, final OnUpdateUserListener onUpdateUserListener) {
        mapBean.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                onUpdateUserListener.onSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                onUpdateUserListener.onFailure(i, s);
            }
        });


    }
}
