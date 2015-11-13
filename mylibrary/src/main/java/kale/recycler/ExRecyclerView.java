package kale.recycler;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import kale.layoutmanager.ExStaggeredGridLayoutManager;

/**
 * 增强型RecyclerView，可以设置头底视图
 *
 * @author Jack Tony
 * @attention 必须在设置adapter前设置header或footer, clickListener
 * @date 2015/4/11
 */
public class ExRecyclerView extends RecyclerView {

    private View mHeaderView;

    private View mFooterView;

    public ExRecyclerView(Context context) {
        super(context);
    }

    public ExRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setLayoutManager(final LayoutManager layoutManager) {
        super.setLayoutManager(layoutManager);
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager lm = (GridLayoutManager) layoutManager;
            lm.setSpanSizeLookup(new GridSpanSizeLookup(lm.getSpanCount()));
        } else if (layoutManager instanceof ExStaggeredGridLayoutManager) {
            ExStaggeredGridLayoutManager lm = (ExStaggeredGridLayoutManager) layoutManager;
            lm.setSpanSizeLookup(new GridSpanSizeLookup(lm.getSpanCount()));
        }
    }

    /**
     * Set the header view of the adapter.
     */
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
    }

    /**
     * 设置底部的视图
     */
    public void setFooterView(View footerView) {
        mFooterView = footerView;
    }

    /**
     * @return recycle的头部视图
     */
    public View setHeaderView() {
        return mHeaderView;
    }

    /**
     * 得到底部的视图
     */
    public View getFooterView() {
        return mFooterView;
    }

    /**
     * 需要在设置头、底、监听器之后再调用setAdapter(Adapter adapter)来设置适配器
     */
    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        setHeadOrFooter(adapter);
    }

    private void setHeadOrFooter(Adapter adapter) {
        if (adapter != null) {
            if (adapter instanceof ExRcvAdapterWrapper) {
                ((ExRcvAdapterWrapper) adapter).setHeaderView(mHeaderView);
                ((ExRcvAdapterWrapper) adapter).setFooterView(mFooterView);
                adapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 平滑滚动到某个位置
     *
     * @param isAbsolute position是否是绝对的，如果是绝对的，那么header的位置就是0
     *                   如果是相对的，那么position就是相对内容的list的位置
     */
    public void smoothScrollToPosition(int position, boolean isAbsolute) {
        if (!isAbsolute && mHeaderView != null) {
            position++;
        }
        smoothScrollToPosition(position);
    }


    /**
     * 设置Grid/StaggeredGrid LayoutManager的布局样式
     */
    private class GridSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

        private int mSpanSize = 1;

        public GridSpanSizeLookup(int spanSize) {
            mSpanSize = spanSize;
        }

        @Override
        public int getSpanSize(int position) {
            if (getAdapter() instanceof ExRcvAdapterWrapper) {
                ExRcvAdapterWrapper adapter = (ExRcvAdapterWrapper) getAdapter();
                // 如果是头或底的类型，那么就设置横跨所有列
                if (adapter.getItemViewType(position) == ExRcvAdapterWrapper.TYPE_HEADER ||
                        adapter.getItemViewType(position) == ExRcvAdapterWrapper.TYPE_FOOTER) {
                    return mSpanSize;
                }
            }
            return 1;
        }
    }
}
