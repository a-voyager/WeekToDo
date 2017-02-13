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


        return inflater.inflate(R.layout.page_fragment, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        initViews();
    }

    private void initViews() {
        mAdapter = new TaskAdapter(getActivity(), mList);
        boolean showPriority = PreferenceUtils.getInstance(getActivity()).getBooleanParam(Constants.CONFIG_KEY.SHOW_PRIORITY, true);
        mAdapter.setShowPriority(showPriority);
        if (mListener == null && getActivity() instanceof OnPageFragmentInteractionListener) {
            mListener = (OnPageFragmentInteractionListener) getActivity();
        }
        mAdapter.setListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TaskDetailEntity entity) {
                if (mListener != null)
                    mListener.onListTaskItemClick(position, entity);
            }

            @Override
            public void onItemLongClick(int position, TaskDetailEntity entity) {
                if (mListener != null)
                    mListener.onListTaskItemLongClick(position, entity);
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

    public void clearTasks() {
        mList.clear();
    }

    public interface OnPageFragmentInteractionListener {
        void onListTaskItemClick(int position, TaskDetailEntity entity);

        void onListTaskItemLongClick(int position, TaskDetailEntity entity);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mListener = null;
        mAdapter = null;
        mRv = null;
    }
}
