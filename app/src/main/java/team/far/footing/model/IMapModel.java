package team.far.footing.model;

import java.util.List;

import cn.bmob.v3.listener.FindListener;
import team.far.footing.model.bean.MapBean;
import team.far.footing.model.callback.OnUpdateMapListener;
import team.far.footing.model.bean.Userbean;

/**
 * Created by luoyy on 2015/8/14 0014.
 */
public interface IMapModel {


    void save_map_finish(Userbean userbean, String map_url, String map_file_name,
                         List<String> map_list, String all_time, String all_distance,
                         String start_time, OnUpdateMapListener onUpdateMapListener);

    //步行时暂停了再次行走后,相当于更新这条数据。
    //该方法需要objectId  所以暂停时需要得到 id
    void save_map_again(String objectId, List<String> map_list,
                        String all_time, String all_distance, OnUpdateMapListener onUpdateMapListener);

    //得到 用户的 所有mapbean
    void get_map_byuserbean(Userbean userbean, FindListener<MapBean> findListener);


    //删除该mapbean。
    void delete_mapbean(MapBean mapBean);
}
