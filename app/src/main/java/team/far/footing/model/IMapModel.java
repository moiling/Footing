package team.far.footing.model;

import java.util.List;

import team.far.footing.model.Listener.OnUpdateUserListener;
import team.far.footing.model.bean.Userbean;

/**
 * Created by luoyy on 2015/8/14 0014.
 */
public interface IMapModel {


    void save_map_finish(Userbean userbean, String map_url, String map_file_name,
                         List<String> map_list, String all_time, String all_distance,
                         String start_time, OnUpdateUserListener onUpdateUserListener);

    //步行时暂停了再次行走后,相当于更新这条数据。
    void save_map_again(Userbean userbeans, List<String> map_list,
                        String all_time, String all_distance,OnUpdateUserListener onUpdateUserListener);

}
