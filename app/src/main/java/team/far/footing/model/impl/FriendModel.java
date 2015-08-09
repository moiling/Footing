package team.far.footing.model.impl;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import team.far.footing.app.APP;
import team.far.footing.model.IFriendModel;
import team.far.footing.model.Listener.OnQueryFriendListener;
import team.far.footing.model.Listener.OnUpdateUserListener;
import team.far.footing.model.bean.Friends;
import team.far.footing.model.bean.Userbean;
import team.far.footing.util.BmobUtils;

/**
 * Created by Luoyy on 2015/8/9 0009.
 */
public class FriendModel implements IFriendModel {

    @Override
    public void addFriend(Userbean userbean, final OnUpdateUserListener onUpdateUserListener) {

        Userbean CurrentUser = BmobUtils.getCurrentUser();
        Friends friends = new Friends();
        friends.setObjectId(BmobUtils.getCurrentUser().getFriendId());
        friends.setUserbean(CurrentUser);
        BmobRelation bmobRelation = new BmobRelation();
        bmobRelation.add(userbean);
        friends.setFriends(bmobRelation);
        update_friend(friends, onUpdateUserListener);

    }
    @Override
    public void getAllFriends(final OnQueryFriendListener onQueryFriendListener) {
        BmobQuery<Userbean> query = new BmobQuery<>();
        Friends friends = new Friends();
        friends.setObjectId(BmobUtils.getCurrentUser().getFriendId());
        query.addWhereRelatedTo("friends", new BmobPointer(friends));
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
    @Override
    public void deleteFriend(Userbean userbean, final OnUpdateUserListener onUpdateUserListener) {
        Userbean CurrentUser = BmobUtils.getCurrentUser();
        Friends friends = new Friends();
        friends.setObjectId(BmobUtils.getCurrentUser().getFriendId());
        friends.setUserbean(CurrentUser);
        BmobRelation bmobRelation = new BmobRelation();
        bmobRelation.remove(userbean);
        friends.setFriends(bmobRelation);
        update_friend(friends, onUpdateUserListener);
    }

    public void update_friend(Friends friends, final OnUpdateUserListener onUpdateUserListener) {

        friends.update(APP.getContext(), new UpdateListener() {
            @Override
            public void onSuccess() {
                if (onUpdateUserListener != null)
                    onUpdateUserListener.onSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                if (onUpdateUserListener != null)
                    onUpdateUserListener.onFailure(i, s);
            }
        });
    }

}
