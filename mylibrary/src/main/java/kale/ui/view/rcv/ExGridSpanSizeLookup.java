package kale.ui.view.rcv;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * @author Jack Tony
 * @date 2015/11/21
 * 
 * 设置Grid/StaggeredGrid LayoutManager的布局样式
 */
public class ExGridSpanSizeLookup extends GridLayoutManager.SpanSizeLookup{

    private final RecyclerView.Adapter mAdapter;

    private int mSpanCount = 1;

    /**
     * @param spanCount RecyclerView的列数
     */
    public ExGridSpanSizeLookup(RecyclerView.Adapter adapter, int spanCount) {
        mAdapter = adapter;
        mSpanCount = spanCount;
    }

    @Override
    public int getSpanSize(int position) {
        if (mAdapter instanceof ExRcvAdapterWrapper) {
            ExRcvAdapterWrapper adapter = (ExRcvAdapterWrapper) mAdapter;
            // 如果是头或底的类型，那么就设置横跨所有列
            if (adapter.getItemViewType(position) == ExRcvAdapterWrapper.TYPE_HEADER ||
                    adapter.getItemViewType(position) == ExRcvAdapterWrapper.TYPE_FOOTER) {
                return mSpanCount;
            }
        }
        return 1;
    }
}
