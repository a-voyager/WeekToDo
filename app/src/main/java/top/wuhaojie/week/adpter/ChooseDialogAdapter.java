package top.wuhaojie.week.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.wuhaojie.week.R;
import top.wuhaojie.week.data.ImageFactory;
import top.wuhaojie.week.utils.ImageLoader;

/**
 * Created by wuhaojie on 2016/12/6 9:59.
 */

public class ChooseDialogAdapter extends RecyclerView.Adapter {


    public static class Item {
        String uri;

        public Item(String uri) {
            this.uri = uri;
        }
    }

    private final LayoutInflater mInflater;
    private List<Item> mList = new ArrayList<>();
    private Context mContext;


    private int mCheckItem;


    {
        List<String> bgImgs = ImageFactory.createBgImgs();
        for (String s : bgImgs) {
            mList.add(new Item(s));
        }
    }

    public ChooseDialogAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public int getCheckItem() {
        return mCheckItem;
    }

    public void setCheckItem(String checkItem) {
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).uri.equals(checkItem)) {
                mCheckItem = i;
                break;
            }
        }
        notifyItemChanged(mCheckItem);
    }

    public void setCheckItem(int checkItem) {
        mCheckItem = checkItem;
        notifyItemChanged(mCheckItem);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_choose_paper_color, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder h = (Holder) holder;
        ImageLoader.get().load(mList.get(position).uri, h.mIvIcon);
        h.mIvMask.setVisibility(View.INVISIBLE);
        if (mCheckItem == position) {
            h.mIvMask.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_mask)
        ImageView mIvMask;
        @BindView(R.id.sdv_icon)
        ImageView mIvIcon;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> mOnItemClickListener.onClick(v, getAdapterPosition()));
        }
    }

    public interface OnItemClickListener {
        void onClick(View v, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
