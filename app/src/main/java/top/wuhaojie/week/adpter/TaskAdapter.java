package top.wuhaojie.week.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.wuhaojie.week.R;
import top.wuhaojie.week.entities.TaskDetailEntity;

/**
 * Created by wuhaojie on 2016/11/29 21:35.
 */

public class TaskAdapter extends RecyclerView.Adapter {


    private Context mContext;
    private List<TaskDetailEntity> mList;

    public TaskAdapter(Context context, List<TaskDetailEntity> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.task_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder h = (Holder) holder;
        TaskDetailEntity entity = mList.get(position);
        h.setEntity(entity);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        TaskDetailEntity entity;
        @BindView(R.id.sdv_icon)
        SimpleDraweeView mSdvIcon;


        Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> mListener.onItemClick(getAdapterPosition(), entity));
            itemView.setOnLongClickListener(v -> {
                mListener.onItemLongClick(getAdapterPosition(), entity);
                return true;
            });
        }

        void setEntity(TaskDetailEntity entity) {
            this.entity = entity;
            mTvTitle.setText(entity.getTitle());
            String content = entity.getContent();
            int length = content.length();
            String s = content.substring(0, Math.min(length, 28));
            if (length >= 28) s += "...";
            mTvContent.setText(s);
            mSdvIcon.setImageURI(entity.getIcon());
        }
    }


    public interface OnItemClickListener {
        void onItemClick(int position, TaskDetailEntity entity);

        void onItemLongClick(int position, TaskDetailEntity entity);
    }

    private OnItemClickListener mListener;

    public void setListener(final OnItemClickListener listener) {
        mListener = listener;
    }
}
