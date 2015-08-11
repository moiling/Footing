package team.far.footing.model;

import team.far.footing.model.Listener.OnQueryPostListener;
import team.far.footing.model.Listener.OnUpdateUserListener;

/**
 * Created by luoyy on 2015/8/11 0011.
 */
public interface ISquareModel {
    /**
     * @param title                ---- post 标题
     * @param content              --- 内容
     * @param picUrl               ---  图片的 url
     * @param onUpdateUserListener ----更新用户的Listener
     */
    void sendPost(String title, String content, String picUrl, OnUpdateUserListener onUpdateUserListener);

    /**
     * @param onQueryPostListener ----获取当前用户所有post的监听
     */
    void getUserPost(OnQueryPostListener onQueryPostListener);
}
