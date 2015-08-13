package team.far.footing.ui.fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.bmob.btp.callback.DownloadListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import team.far.footing.R;
import team.far.footing.app.APP;
import team.far.footing.model.bean.Userbean;
import team.far.footing.model.impl.FileModel;
import team.far.footing.presenter.TodayPresenter;
import team.far.footing.ui.vu.IFgTodayVU;
import team.far.footing.ui.widget.CircleImageView;
import team.far.footing.ui.widget.DividerItemDecoration;

public class TodayFragment extends Fragment implements IFgTodayVU {

    @InjectView(R.id.tv_tt)
    TextView tvTt;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        ButterKnife.inject(this, view);
        todayPresenter = new TodayPresenter(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void init(Userbean CurrentUser, List<Userbean> userbeanList) {
        tvTodayDistance.setText(CurrentUser.getToday_distance() + "");
        if (CurrentUser.getIs_finish_today() == 1) tvIsFinishToday.setText("你已经完成了今天的任务哟！");
        recyclerview.setLayoutManager(new LinearLayoutManager(APP.getContext()));
        recyclerview.addItemDecoration(new DividerItemDecoration(APP.getContext(),DividerItemDecoration.VERTICAL_LIST));
        this.userbeanList = userbeanList;
        myAdapter = new MyAdapter();
        recyclerview.setAdapter(myAdapter);
    }

    @Override
    public void oninit_error(int code, String msg) {

    }

    @Override
    public void onclickwork() {

    }

    @Override
    public void choose_distance(List<Userbean> userbeanList) {

    }

    @Override
    public void choose_alldistance(List<Userbean> userbeanList) {

    }

    @Override
    public void choose_level(List<Userbean> userbeanList) {

    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, null);
            return new ViewHolder(V);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.tv_name.setText(userbeanList.get(position).getNickName());
            holder.tv_distance.setText(userbeanList.get(position).getAll_distance() + "");
            if (userbeanList.get(position).getHeadPortraitFileName() != null)
                    FileModel.getInstance().downloadPic(userbeanList.get(position).getHeadPortraitFileName(), new DownloadListener() {
                        @Override
                        public void onSuccess(String s) {
                            holder.circleImageView.setImageBitmap(BitmapFactory.decodeFile(s));
                        }

                        @Override
                        public void onProgress(String s, int i) {

                        }

                        @Override
                        public void onError(int i, String s) {

                        }
                    });

        }

        @Override
        public int getItemCount() {
            return userbeanList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private CircleImageView circleImageView;
            private TextView tv_name;
            private TextView tv_distance;

            public ViewHolder(View itemView) {
                super(itemView);
                circleImageView = (CircleImageView) itemView.findViewById(R.id.item_image_friend);
                tv_name = (TextView) itemView.findViewById(R.id.item_tv_name);
                tv_distance = (TextView) itemView.findViewById(R.id.item_tv_distance);
            }
        }
    }

}
