package top.wuhaojie.week.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.wuhaojie.week.R;
import top.wuhaojie.week.adpter.TaskAdapter;
import top.wuhaojie.week.constant.Constants;
import top.wuhaojie.week.entities.TaskDetailEntity;
import top.wuhaojie.week.utils.PreferenceUtils;

/**
 * Created by wuhaojie on 2016/11/29 21:05.
 */

public class PageFragment extends Fragment {

    public static final String TAG = "PageFragment";
    @BindView(R.id.rv)
    RecyclerView mRv;

    private List<TaskDetailEntity> mList = new ArrayList<>();

    public TaskAdapter getAdapter() {
        return mAdapter;
    }

    private TaskAdapter mAdapter;

    public PageFragment() {
        Log.d("PageFragment", "constructor()");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.page_fragment, container, false);
        ButterKnife.bind(this, view);

        initViews();

        return view;
    }


    private void initViews() {
        mAdapter = new TaskAdapter(getActivity(), mList);
        boolean showPriority = PreferenceUtils.getInstance(getActivity()).getBooleanParam(Constants.CONFIG_KEY.SHOW_PRIORITY, true);
        mAdapter.setShowPriority(showPriority);
        mAdapter.setListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TaskDetailEntity entity) {
                mListener.toEditActivity(position, entity);
            }

            @Override
            public void onItemLongClick(int position, TaskDetailEntity entity) {
                mListener.showContextMenu(position, entity);
            }
        });
        mRv.setAdapter(mAdapter);
        String s = PreferenceUtils.getInstance(getActivity()).getStringParam(Constants.CONFIG_KEY.SHOW_AS_LIST, "list");
        if (s.equals("list"))
            mRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        else
            mRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }

    public void insertTask(TaskDetailEntity task) {
        if (!mList.contains(task))
            mList.add(task);
        if (mAdapter != null)
            mAdapter.notifyItemInserted(mList.size() - 1);
    }

//    public void editTask(int index, TaskDetailEntity task) {
//        mList.remove(index);
//        mList.add(task);
//        TaskDetailEntity taskDetailEntity = mList.get(index);
//        taskDetailEntity.setTaskDetailEntity(task);
//        if (mAdapter != null) {
//            mAdapter.notifyDataSetChanged();
//        }
//    }


    public TaskDetailEntity deleteTask(int index) {
        TaskDetailEntity taskDetailEntity = mList.get(index);
        mList.remove(index);
        mAdapter.notifyItemRemoved(index);
        return taskDetailEntity;
    }


    private OnPageFragmentInteractionListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPageFragmentInteractionListener) {
            mListener = (OnPageFragmentInteractionListener) context;
        } else {
            Log.e(TAG, "context is not instanceof OnPageFragmentInteractionListener");
        }
    }

    public interface OnPageFragmentInteractionListener {
        void toEditActivity(int position, TaskDetailEntity entity);

        void showContextMenu(int position, TaskDetailEntity entity);
    }

}
