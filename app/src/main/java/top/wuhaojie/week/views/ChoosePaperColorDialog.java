package top.wuhaojie.week.views;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.wuhaojie.week.R;
import top.wuhaojie.week.adpter.ChooseDialogAdapter;
import top.wuhaojie.week.constant.Constants;

/**
 * Created by wuhaojie on 2016/12/6 8:53.
 */

public class ChoosePaperColorDialog extends DialogFragment {


    private ChooseDialogAdapter mChooseDialogAdapter;

    public static ChoosePaperColorDialog newInstance(String currCheckItemUri) {
        ChoosePaperColorDialog dialog = new ChoosePaperColorDialog();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.CHOOSE_PAPER_DIALOG_CHECK_ITEM_BUNDLE_KEY, currCheckItemUri);
        dialog.setArguments(bundle);
        return dialog;
    }


    @BindView(R.id.rv_dl_choose_color)
    RecyclerView mRvDlChooseColor;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("选择背景图片");
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dl_choose_paper_color, container, false);
        ButterKnife.bind(this, view);
        mRvDlChooseColor.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mChooseDialogAdapter = new ChooseDialogAdapter(getActivity());
        mChooseDialogAdapter.setCheckItem(getArguments().getString(Constants.CHOOSE_PAPER_DIALOG_CHECK_ITEM_BUNDLE_KEY));
        mChooseDialogAdapter.setOnItemClickListener((v, position) -> {
            ((NewActivity) getActivity()).loadBgImgWithIndex(position);

            mChooseDialogAdapter.setCheckItem(position);
            dismiss();
        });
        mRvDlChooseColor.setAdapter(mChooseDialogAdapter);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mChooseDialogAdapter = null;
        mRvDlChooseColor = null;
    }
}
