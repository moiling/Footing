package team.far.footing.model.impl;

import android.content.Context;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import team.far.footing.app.APP;
import team.far.footing.model.IMapModel;
import team.far.footing.model.callback.OnUpdateMapListener;
import team.far.footing.model.bean.MapBean;
import team.far.footing.model.bean.Userbean;
import team.far.footing.util.LogUtils;

/**
 * Created by luoyy on 2015/8/14 0014.
 */
public class MapModel implements IMapModel {
    public static final MapModel instance = new MapModel();

    private MapModel() {
    }

    public static final MapModel getInstance() {
        return instance;
    }

    @Override
    public void save_map_finish(Userbean userbean, String map_url, String map_file_name, List<String> map_list,
                                String all_time, String all_distance, String start_time, OnUpdateMapListener onUpdateUserListener) {
        MapBean mapBean = new MapBean();
        mapBean.setUserbean(userbean);
        mapBean.setAll_distance(all_distance);
        mapBean.setAll_time(all_time);
        if (map_list != null) {
            mapBean.addAll("map_array", map_list);
        }
        mapBean.setStart_time(start_time);
        mapBean.setMap_url(map_url);
        mapBean.setMap_file_name(map_file_name);
        update(mapBean, APP.getContext(), onUpdateUserListener);

    }

    @Override
    public void save_map_again(String objectId, List<String> map_list, String all_time, String all_distance, final OnUpdateMapListener onUpdateMapListener) {
        final MapBean mapBean = new MapBean();

        mapBean.setObjectId(objectId);
        if (map_list != null) {
            mapBean.addAll("map_array", map_list);
        }
        mapBean.setAll_time(all_time);
        mapBean.setAll_distance(all_distance);

        mapBean.update(APP.getContext(), new UpdateListener() {
            @Override
            public void onSuccess() {
                if (onUpdateMapListener != null)
                    onUpdateMapListener.onSuccess(mapBean);
            }

            @Override
            public void onFailure(int i, String s) {
                if (onUpdateMapListener != null) onUpdateMapListener.onFailure(i, s);
            }
        });

    }

    @Override
    public void get_map_byuserbean(Userbean userbean, FindListener<MapBean> findListener) {
        BmobQuery<MapBean> query = new BmobQuery<MapBean>();
        query.addWhereEqualTo("userbean", userbean);
        query.findObjects(APP.getContext(), findListener);
    }

    @Override
    public void delete_mapbean(MapBean mapBean) {
        mapBean.delete(APP.getContext());
    }


    private void update(final MapBean mapBean, Context context, final OnUpdateMapListener onUpdateMapListener) {
        mapBean.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                if (onUpdateMapListener != null)
                    onUpdateMapListener.onSuccess(mapBean);
            }

            @Override
            public void onFailure(int i, String s) {
                LogUtils.e("=============onFailure>>>>>>>" + s);
                if (onUpdateMapListener != null) onUpdateMapListener.onFailure(i, s);
            }
        });


    }
}
