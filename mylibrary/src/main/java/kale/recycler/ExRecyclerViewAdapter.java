package kale.recycler;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import kale.adapter.recycler.CommonRcvAdapter;

/**
 * @author Jack Tony
 * @date 2015/6/2
 */
public abstract class ExRecyclerViewAdapter<T> extends CommonRcvAdapter<T>{

    private View mHeaderView = null;

    private View mFooterView = null;

    protected ExRecyclerViewAdapter(List<T> data) {
        super(data);
    }

    /**
     * view的基本类型，这里只有头/底部/普通，在子类中可以扩展
     */
    class VIEW_TYPES {

        public static final int HEADER = 2;

        public static final int FOOTER = 3;
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
        return headerOrFooter + super.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView != null && position == 0) {
            return VIEW_TYPES.HEADER;
        }else if (mFooterView != null && position == getItemCount() - 1) {
            return VIEW_TYPES.FOOTER;
        } else {
            if (mHeaderView != null) {
                position--;
            }
            return super.getItemViewType(position);
        }
    }
    
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPES.HEADER && mHeaderView != null) {
            return new SimpleViewHolder(mHeaderView);
        } else if (viewType == VIEW_TYPES.FOOTER && mFooterView != null) {
            return new SimpleViewHolder(mFooterView);
        } else {
            return super.onCreateViewHolder(parent, viewType);
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
            Log.d("log", "onBindViewHolder: current viewHolder is head or footer");
        } else {
            if (mHeaderView != null) {
                position--;
            }
            super.onBindViewHolder(viewHolder, position);
        }
    }

    private static class SimpleViewHolder extends RecyclerView.ViewHolder {

        public SimpleViewHolder(View itemView) {
            super(itemView);
        }

    }
    
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
    }

    public void setFooterView(View footerView) {
        mFooterView = footerView;
    }

}
