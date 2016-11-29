package top.wuhaojie.week.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.wuhaojie.week.R;
import top.wuhaojie.week.adpter.TaskAdapter;
import top.wuhaojie.week.entities.TaskEntity;

/**
 * Created by wuhaojie on 2016/11/29 21:05.
 */

public class PageFragment extends Fragment {

    @BindView(R.id.rv)
    RecyclerView mRv;

    private List<TaskEntity> mList;
    private TaskAdapter mAdapter;


    {
        mList = new ArrayList<>();
        mList.add(new TaskEntity());
        mList.add(new TaskEntity());
        mList.add(new TaskEntity());
        mList.add(new TaskEntity());
        mList.add(new TaskEntity());
        mList.add(new TaskEntity());
        mList.add(new TaskEntity());
        mList.add(new TaskEntity());
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
        mRv.setAdapter(mAdapter);
        mRv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
