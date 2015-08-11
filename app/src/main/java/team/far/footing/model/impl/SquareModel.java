package team.far.footing.model.impl;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import team.far.footing.app.APP;
import team.far.footing.model.ISquareModel;
import team.far.footing.model.Listener.OnQueryPostListener;
import team.far.footing.model.Listener.OnUpdateUserListener;
import team.far.footing.model.bean.Postbean;
import team.far.footing.util.BmobUtils;

/**
 * Created by luoyy on 2015/8/11 0011.
 */
public class SquareModel implements ISquareModel {
    @Override
    public void sendPost(String title, String content, String picUrl, final OnUpdateUserListener onUpdateUserListener) {
        //创建 Post 的信息
        Postbean postbean = new Postbean();
        postbean.setAuthor(BmobUtils.getCurrentUser());
        postbean.setContent(content);
        postbean.setPicUrl(picUrl);
        postbean.save(APP.getContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                if (onUpdateUserListener != null) onUpdateUserListener.onSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                if (onUpdateUserListener != null) onUpdateUserListener.onFailure(i, s);
            }
        });
    }

    @Override
    public void getUserPost(final OnQueryPostListener onQueryPostListener) {
        BmobQuery<Postbean> query = new BmobQuery<>();
        query.addWhereEqualTo("author", BmobUtils.getCurrentUser());
        query.order("-updatedAt");
        query.findObjects(APP.getContext(), new FindListener<Postbean>() {
            @Override
            public void onSuccess(List<Postbean> list) {
                onQueryPostListener.onSuccess(list);
            }

            @Override
            public void onError(int i, String s) {
                onQueryPostListener.onError(i, s);
            }
        });
    }
}
