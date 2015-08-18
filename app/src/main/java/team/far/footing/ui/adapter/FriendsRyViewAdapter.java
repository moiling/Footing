package team.far.footing.ui.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;

import java.util.List;

import team.far.footing.R;
import team.far.footing.app.APP;
import team.far.footing.model.bean.Userbean;
import team.far.footing.model.callback.OngetUserPicListener;
import team.far.footing.model.impl.FileModel;
import team.far.footing.ui.widget.CircleImageView;

/**
 * Created by luoyy on 2015/8/18 0018.
 */
public class FriendsRyViewAdapter extends RecyclerView.Adapter<FriendsRyViewAdapter.ViewHolder> {


    private List<Userbean> list;

    public FriendsRyViewAdapter(List<Userbean> object) {
        list = object;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_today_friends_list, null);
        return new ViewHolder(V);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.ripple.setRippleColor(APP.getContext().getResources().getColor(R.color.accent_light_color));
        holder.tv_name.setText(list.get(position).getNickName());

        if (list.get(position).getHeadPortraitFileName() != null) {
            FileModel.getInstance().getUserPic(list.get(position), new OngetUserPicListener() {
                @Override
                public void onSucess(Bitmap bitmap) {
                    holder.circleImageView.setImageBitmap(bitmap);
                }

                @Override
                public void onError() {
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView circleImageView;
        private TextView tv_name;
        private TextView tv_distance;
        private MaterialRippleLayout ripple;

        public ViewHolder(View itemView) {
            super(itemView);
            ripple = (MaterialRippleLayout) itemView.findViewById(R.id.ripple);
            circleImageView = (CircleImageView) itemView.findViewById(R.id.item_image_friend);
            tv_name = (TextView) itemView.findViewById(R.id.item_tv_name);
            tv_distance = (TextView) itemView.findViewById(R.id.item_tv_distance);
        }
    }

}
