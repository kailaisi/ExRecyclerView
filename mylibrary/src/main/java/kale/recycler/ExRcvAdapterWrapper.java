package kale.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Jack Tony
 * @date 2015/6/2
 */
public class ExRcvAdapterWrapper<T extends RecyclerView.Adapter> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * view的基本类型，这里只有头/底部/普通，在子类中可以扩展
     */
    public static final int TYPE_HEADER = 99930;

    public static final int TYPE_FOOTER = 99931;

    private View mHeaderView = null;

    private View mFooterView = null;

    private T mWrappedAdapter;

    public ExRcvAdapterWrapper(@NonNull T adapter) {
        mWrappedAdapter = adapter;
        registerAdapterDataObserver(mDataObserver);
    }

    /**
     * 返回adapter中总共的item数目，包括头部和底部
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        int headerOrFooter = 0;
        if (mHeaderView != null) {
            headerOrFooter++;
        }
        if (mFooterView != null) {
            headerOrFooter++;
        }
        return headerOrFooter + mWrappedAdapter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView != null && position == 0) {
            return TYPE_HEADER;
        } else if (mFooterView != null && position == getItemCount() - 1) {
            return TYPE_FOOTER;
        } else {
            if (mHeaderView != null) {
                position--;
            }
            return mWrappedAdapter.getItemViewType(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER && mHeaderView != null) {
            return new SimpleViewHolder(mHeaderView);
        } else if (viewType == TYPE_FOOTER && mFooterView != null) {
            return new SimpleViewHolder(mFooterView);
        } else {
            return mWrappedAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    /**
     * 载入ViewHolder，这里仅仅处理header和footer视图的逻辑
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        boolean isHeader = mHeaderView != null && position == 0;
        boolean isFooter = mFooterView != null && position == getItemCount() - 1;

        if (isHeader || isFooter) {
            // 如果是header或者是footer则不处理
            Log.i("ExRcvAdapterWrapper", "onBindViewHolder: Current viewHolder is head or footer");
        } else {
            if (mHeaderView != null) {
                position--;
            }
            mWrappedAdapter.onBindViewHolder(viewHolder, position);
        }
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
    }

    public void setFooterView(View footerView) {
        mFooterView = footerView;
    }

    public T getWrappedAdapter() {
        return mWrappedAdapter;
    }

    private int getHeaderViewsCount() {
        return mHeaderView != null ? 1 : 0;
    }

    private static class SimpleViewHolder extends RecyclerView.ViewHolder {

        public SimpleViewHolder(View itemView) {
            super(itemView);
        }

    }
    
    private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {

        @Override
        public void onChanged() {
            super.onChanged();
            Log.d("dddd", "onChanged");
            mWrappedAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            Log.d("dddd", "onItemRangeChanged: start = " + positionStart);
            //mWrappedAdapter.notifyItemRangeChanged(positionStart + getHeaderViewsCount(), itemCount);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            Log.d("dddd", "onItemRangeInserted: start = " + positionStart);
            mWrappedAdapter.notifyItemRangeInserted(positionStart + getHeaderViewsCount(), itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            mWrappedAdapter.notifyItemRangeRemoved(positionStart + getHeaderViewsCount(), itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            int headerViewsCountCount = getHeaderViewsCount();
            mWrappedAdapter.notifyItemRangeChanged(fromPosition + headerViewsCountCount, 
                    toPosition + headerViewsCountCount + itemCount);
        }
    };


}
