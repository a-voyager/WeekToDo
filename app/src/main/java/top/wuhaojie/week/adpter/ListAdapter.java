package top.wuhaojie.week.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.wuhaojie.week.R;
import top.wuhaojie.week.entities.TaskDetailEntity;
import top.wuhaojie.week.entities.TaskState;
import top.wuhaojie.week.utils.DateUtils;

/**
 * Author: wuhaojie
 * E-mail: w19961009@126.com
 * Date: 2017/02/21 22:36
 * Version: 1.0
 */

public class ListAdapter extends RecyclerView.Adapter {

    private List<TaskDetailEntity> mList = new ArrayList<>();

    public void setList(List<TaskDetailEntity> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Inject
    public ListAdapter() {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder h = (Holder) holder;
        h.setView(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    private OnItemClickListener mListener;

    public void setListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, TaskDetailEntity entity);
    }


    class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        @BindView(R.id.tv_date)
        TextView mTvDate;
        @BindView(R.id.tv_state)
        TextView mTvState;

        Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (mListener != null) mListener.onItemClick(position, mList.get(position));
            });
        }

        public void setView(TaskDetailEntity entity) {
            String title = entity.getTitle();
            mTvTitle.setText(title);
            String content = entity.getContent();
            mTvContent.setText(content);
            long timeStamp = entity.getTimeStamp();
            String date = DateUtils.formatDateWeek(timeStamp);
            mTvDate.setText(date);
            int state = entity.getState();
            String sState = (state == TaskState.FINISHED) ? "已完成" : "未完成";
            mTvState.setText(sState);
        }
    }


}
