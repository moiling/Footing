package team.far.footing.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import team.far.footing.R;
import team.far.footing.app.APP;
import team.far.footing.model.bean.Userbean;
import team.far.footing.model.callback.OngetUserPicListener;
import team.far.footing.model.impl.FileModel;
import team.far.footing.presenter.TodayPresenter;
import team.far.footing.ui.activity.FriendInfoActivity;
import team.far.footing.ui.activity.UserInfoActivity;
import team.far.footing.ui.vu.IFgTodayVu;
import team.far.footing.ui.widget.CircleImageView;
import team.far.footing.util.BmobUtils;
import team.far.footing.util.LevelUtils;
import team.far.footing.util.TimeUtils;

public class TodayFragment extends Fragment implements IFgTodayVu {

    @InjectView(R.id.tv_today_distance)
    TextView tvTodayDistance;
    @InjectView(R.id.CV_fg_today)
    CardView CVFgToday;
    @InjectView(R.id.spinner_fg_today)
    Spinner spinnerFgToday;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    @InjectView(R.id.cv_fg_friends)
    CardView cvFgFriends;
    @InjectView(R.id.tv_Is_finish_today)
    TextView tvIsFinishToday;
    @InjectView(R.id.tv_friends)
    TextView tvFriends;
    @InjectView(R.id.progress_wheel)
    ProgressWheel progressWheel;

    private List<Userbean> userbeanList = new ArrayList<>();
    private TodayPresenter todayPresenter;
    private MyAdapter myAdapter;
    private int type = 0;
    private View parentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        parentView = view;
        ButterKnife.inject(this, view);
        initRecycler();
        todayPresenter = new TodayPresenter(this);
        spinnerFgToday.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = position;
                todayPresenter.choose_spinner(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        todayPresenter.refresh(type);
        onProgress();
    }

    private void initRecycler() {
        recyclerview.setLayoutManager(new LinearLayoutManager(APP.getContext()));
        myAdapter = new MyAdapter();
        recyclerview.setAdapter(myAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        todayPresenter.onRelieveView();
        ButterKnife.reset(this);
    }

    @Override
    public void onProgress() {
        progressWheel.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressEnd() {
        progressWheel.setVisibility(View.GONE);
    }

    @Override
    public void init(Userbean CurrentUser, List<Userbean> userbeanList) {
        if (TimeUtils.isToday(CurrentUser.getToday_date())) {
            if (CurrentUser.getToday_distance() != null) {
                if (CurrentUser.getToday_distance() > 1000) {
                    tvTodayDistance.setText(new DecimalFormat(".##").format(CurrentUser.getToday_distance() / 1000.0) + " km");
                } else {
                    tvTodayDistance.setText(CurrentUser.getToday_distance() + " m");
                }
            } else {
                tvTodayDistance.setText("0 m");
            }
        } else {
            tvTodayDistance.setText("0 m");
        }
        // TODO 任务以后再做
/*        if (CurrentUser.getIs_finish_today() != null && CurrentUser.getIs_finish_today() == 1) {
            tvIsFinishToday.setText("已完成");
            tvIsFinishToday.setTextColor(getResources().getColor(R.color.accent_color));
        }*/

        tvIsFinishToday.setText(CurrentUser.getLevel() + " / " + ((LevelUtils.getLevel(CurrentUser.getLevel()) + 1) * (LevelUtils.getLevel(CurrentUser.getLevel()) + 1) * 200 + 40) + " Exp");

        this.userbeanList = userbeanList;
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void oninit_error(int code, String msg) {

    }

    @Override
    public void onclickwork() {

    }


    @Override
    public void choose_distance(List<Userbean> userbeanList) {
        this.userbeanList = userbeanList;
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void choose_alldistance(List<Userbean> userbeanList) {
        this.userbeanList = userbeanList;
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void choose_level(List<Userbean> userbeanList) {
        this.userbeanList = userbeanList;
        myAdapter.notifyDataSetChanged();
    }


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_today_friends_list, null);
            return new ViewHolder(V);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            if (userbeanList.get(position).getNickName() != null) {
                holder.tv_name.setText(userbeanList.get(position).getNickName());
            } else {
                holder.tv_name.setText("未取名");
            }
            holder.tv_rank.setText((position + 1) + "");

            holder.ripple.setRippleColor(getResources().getColor(R.color.accent_light_color));
            switch (type) {
                case 0:
                    holder.tv_distance.setText(new DecimalFormat("0.##").format(userbeanList.get(position).getAll_distance() / 1000.0) + "  km");
                    break;
                case 1:
                    holder.tv_distance.setText(new DecimalFormat("0.##").format(userbeanList.get(position).getToday_distance() / 1000.0)  + "  km");
                    break;
                case 2:
                    holder.tv_distance.setText("Lv." + LevelUtils.getLevel(userbeanList.get(position).getLevel()));
                    break;
            }
            // 自己就跳到自己的个人页面去
            if (userbeanList.get(position).getUsername().equals(BmobUtils.getCurrentUser().getUsername())) {
                holder.ripple.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), UserInfoActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        v.getContext().startActivity(intent);
                    }
                });
            } else {
                holder.ripple.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), FriendInfoActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user", userbeanList.get(position));
                        intent.putExtras(bundle);
                        v.getContext().startActivity(intent);
                    }
                });
            }
            if (userbeanList.get(position).getHeadPortraitFileName() != null) {
                FileModel.getInstance().getUserPic(userbeanList.get(position), new OngetUserPicListener() {
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
            return userbeanList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private CircleImageView circleImageView;
            private TextView tv_name;
            private TextView tv_distance;
            private MaterialRippleLayout ripple;
            private TextView tv_rank;
            public ViewHolder(View itemView) {
                super(itemView);
                ripple = (MaterialRippleLayout) itemView.findViewById(R.id.ripple);
                circleImageView = (CircleImageView) itemView.findViewById(R.id.item_image_friend);
                tv_name = (TextView) itemView.findViewById(R.id.item_tv_name);
                tv_distance = (TextView) itemView.findViewById(R.id.item_tv_distance);
                tv_rank = (TextView) itemView.findViewById(R.id.tv_today_rank);
            }
        }
    }
}
