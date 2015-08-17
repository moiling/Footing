package team.far.footing.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bmob.btp.callback.DownloadListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import team.far.footing.R;
import team.far.footing.app.APP;
import team.far.footing.model.Listener.OngetUserPicListener;
import team.far.footing.model.bean.Userbean;
import team.far.footing.model.impl.FileModel;
import team.far.footing.presenter.TodayPresenter;
import team.far.footing.ui.vu.IFgTodayVU;
import team.far.footing.ui.widget.CircleImageView;
import team.far.footing.ui.widget.DividerItemDecoration;
import team.far.footing.util.LogUtils;

public class TodayFragment extends Fragment implements IFgTodayVU {

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

    private List<Userbean> userbeanList = new ArrayList<>();
    private TodayPresenter todayPresenter;
    private MyAdapter myAdapter;
    private int type = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_today, container, false);
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

    private void initRecycler() {
        recyclerview.setLayoutManager(new LinearLayoutManager(APP.getContext()));
        recyclerview.addItemDecoration(new DividerItemDecoration(APP.getContext(), DividerItemDecoration.VERTICAL_LIST));
        myAdapter = new MyAdapter();
        recyclerview.setAdapter(myAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void init(Userbean CurrentUser, List<Userbean> userbeanList) {
        if (CurrentUser.getToday_distance() != null) {
            tvTodayDistance.setText(CurrentUser.getToday_distance() + " m");
        } else {
            tvTodayDistance.setText("0 m");
        }
        if (CurrentUser.getIs_finish_today() != null && CurrentUser.getIs_finish_today() == 1) {
            tvIsFinishToday.setText("已完成");
            tvIsFinishToday.setTextColor(getResources().getColor(R.color.accent_color));
        }
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

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_today_friends_list, null);
            return new ViewHolder(V);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.tv_name.setText(getActivity().getResources().getStringArray(R.array.sort_string)[position] + "  " + userbeanList.get(position).getNickName());
            holder.ripple.setRippleColor(getResources().getColor(R.color.accent_light_color));
            switch (type) {
                case 0:
                    holder.tv_distance.setText(userbeanList.get(position).getAll_distance() + "  m");
                    break;
                case 1:
                    holder.tv_distance.setText(userbeanList.get(position).getToday_distance() + "  m");
                    break;
                case 2:
                    holder.tv_distance.setText(userbeanList.get(position).getLevel() + " 级");
                    break;
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

            public ViewHolder(View itemView) {
                super(itemView);
                ripple = (MaterialRippleLayout) itemView.findViewById(R.id.ripple);
                circleImageView = (CircleImageView) itemView.findViewById(R.id.item_image_friend);
                tv_name = (TextView) itemView.findViewById(R.id.item_tv_name);
                tv_distance = (TextView) itemView.findViewById(R.id.item_tv_distance);
            }
        }
    }
}
