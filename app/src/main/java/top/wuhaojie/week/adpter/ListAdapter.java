package top.wuhaojie.week.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import top.wuhaojie.week.R;
import top.wuhaojie.week.entities.TaskDetailEntity;

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


    class Holder extends RecyclerView.ViewHolder {

        Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setView(TaskDetailEntity entity) {

        }
    }


}
