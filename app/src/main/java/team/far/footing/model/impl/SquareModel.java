package team.far.footing.model.impl;

import org.w3c.dom.Comment;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import team.far.footing.app.APP;
import team.far.footing.model.IFriendModel;
import team.far.footing.model.ISquareModel;
import team.far.footing.model.callback.OnQueryCommentListener;
import team.far.footing.model.callback.OnQueryFriendListener;
import team.far.footing.model.callback.OnQueryPostListener;
import team.far.footing.model.callback.OnUpdateUserListener;
import team.far.footing.model.bean.Commentbean;
import team.far.footing.model.bean.Postbean;
import team.far.footing.model.bean.Userbean;
import team.far.footing.util.BmobUtils;

/**
 * Created by luoyy on 2015/8/11 0011.
 */
public class SquareModel implements ISquareModel {
    public static final SquareModel instance = new SquareModel();

    final public static SquareModel getInstance() {
        return instance;
    }

    private SquareModel() {
    }


    @Override
    public void sendPost(String title, String content, String picName, String picUrl, final OnUpdateUserListener onUpdateUserListener) {
        //创建 Post 的信息
        Postbean postbean = new Postbean();
        postbean.setAuthor(BmobUtils.getCurrentUser());
        postbean.setContent(content);
        postbean.setPraise(0);
        postbean.setPicName(picName);
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

    @Override
    public void getUserFriendsPost(IFriendModel friendModel, final OnQueryPostListener onQueryPostListener) {
        final BmobQuery<Postbean> query = new BmobQuery<>();
        friendModel.getAllFriends(new OnQueryFriendListener() {
            @Override
            public void onSuccess(List<Userbean> object) {
                for (Userbean userbean : object) {
                    query.addWhereEqualTo("author", userbean);
                }
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

            @Override
            public void onError(int code, String msg) {

            }
        });


    }

    @Override
    public void setPraiseToPost(Postbean postbean, final OnUpdateUserListener onUpdateUserListener) {
        postbean.setPraise(postbean.getPraise() + 1);
        BmobRelation bmobRelation = new BmobRelation();
        bmobRelation.add(BmobUtils.getCurrentUser());
        postbean.setLikes(bmobRelation);
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
    public void publishCommentToPost(Postbean postbean, String conmment, final OnUpdateUserListener onUpdateUserListener) {
        final Commentbean commentbean = new Commentbean();
        commentbean.setPostbean(postbean);
        commentbean.setUserbean(BmobUtils.getCurrentUser());
        commentbean.setContent(conmment);
        commentbean.save(APP.getContext(), new SaveListener() {
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
    public void getCommentForPost(Postbean postbean, final OnQueryCommentListener onQueryCommentListener) {
        BmobQuery<Comment> query = new BmobQuery<Comment>();
        query.addWhereEqualTo("postbean", new BmobPointer(postbean));
        query.include("userbean,postbean.author");
        query.findObjects(APP.getContext(), new FindListener<Comment>() {
            @Override
            public void onSuccess(List<Comment> list) {
                onQueryCommentListener.onSuccess(list);
            }

            @Override
            public void onError(int i, String s) {
                onQueryCommentListener.onError(i, s);
            }
        });
    }

    @Override
    public void getLikePostUser(Postbean postbean, final OnQueryFriendListener onQueryFriendListener) {
        BmobQuery<Userbean> query = new BmobQuery<Userbean>();
        query.addWhereRelatedTo("likes", new BmobPointer(postbean));
        query.findObjects(APP.getContext(), new FindListener<Userbean>() {
            @Override
            public void onSuccess(List<Userbean> list) {
                onQueryFriendListener.onSuccess(list);
            }

            @Override
            public void onError(int i, String s) {
                onQueryFriendListener.onError(i, s);
            }
        });
    }


}
