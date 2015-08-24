package team.far.footing.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import java.util.ArrayList;
import java.util.List;

import team.far.footing.R;
import team.far.footing.model.bean.MessageBean;
import team.far.footing.presenter.MessagePresenter;
import team.far.footing.ui.vu.IMessageVu;

/**
 * Created by moi on 2015/8/20.
 */
public class MessageBoxAdapter extends RecyclerView.Adapter<MessageBoxAdapter.ViewHolder> implements IMessageVu {

    private Context mContext;
    private List<MessageBean> messageBeans = new ArrayList<>();
    private MessagePresenter messagePresenter;
    private int tempPosition;

    public MessageBoxAdapter(Context context, List<MessageBean> messageBeans) {
        mContext = context;
        this.messageBeans = messageBeans;
        messagePresenter = new MessagePresenter(this);
    }

    @Override
    public MessageBoxAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_list, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MessageBoxAdapter.ViewHolder holder, final int position) {
        // 如果为未读则提亮颜色
        if (messageBeans.get(position).getIsread() == 0) {
            holder.tvMessageTitle.setTextColor(mContext.getResources().getColor(R.color.primary_color));
            holder.tvMessageTitle.setText("！未读  " + messageBeans.get(position).getMessage());
        } else {
            holder.tvMessageTitle.setText(messageBeans.get(position).getMessage());
            holder.tvMessageTitle.setTextColor(mContext.getResources().getColor(R.color.secondary_text));
        }
        holder.tvMessageTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempPosition = position;
                messagePresenter.remarkMessage(messageBeans.get(position));
                MessageBoxAdapter.this.notifyDataSetChanged();
                new MaterialDialog.Builder(mContext)
                        .title(messageBeans.get(position).getMessage())
                        .content(messageBeans.get(position).getContent())
                        .backgroundColor(mContext.getResources().getColor(R.color.white))
                        .theme(Theme.LIGHT)
                        .negativeText("删除")
                        .positiveText("关闭")
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                super.onNegative(dialog);
                                messagePresenter.deleteMessage(messageBeans.get(position));
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (messageBeans != null) {
            return messageBeans.size();
        } else {
            return 0;
        }
    }

    @Override
    public void showMessage(List<MessageBean> list) {}

    @Override
    public void onMsgSuccess(String s) {
        new MaterialDialog.Builder(mContext)
                .title("提示")
                .content(s)
                .backgroundColor(mContext.getResources().getColor(R.color.white))
                .theme(Theme.LIGHT)
                .positiveText("好的")
                .show();
        messageBeans.remove(tempPosition);
        this.notifyDataSetChanged();
    }

    @Override
    public void onMsgFail(String s) {
        new MaterialDialog.Builder(mContext)
                .title("提示")
                .content(s)
                .theme(Theme.LIGHT)
                .backgroundColor(mContext.getResources().getColor(R.color.white))
                .positiveText("好的")
                .show();
    }

    @Override
    public void stopRefresh() {

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMessageTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvMessageTitle = (TextView) itemView.findViewById(R.id.tv_message_box_title);
        }
    }
}
