package kale.ui.view.rcv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

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

    /**
     * Set the header view of the adapter.
     */
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        setHeadOrFooter(getAdapter());
    }

    /**
     * 设置底部的视图
     */
    public void setFooterView(View footerView) {
        mFooterView = footerView;
        setHeadOrFooter(getAdapter());
    }

    /**
     * @return recycle的头部视图
     */
    public View getHeaderView() {
        return mHeaderView;
    }

    /**
     * 得到底部的视图
     */
    public View getFooterView() {
        return mFooterView;
    }

    public void removeHeaderView() {
        mHeaderView = null;
        setHeadOrFooter(getAdapter());
    }

    public void removeFooterView() {
        mFooterView = null;
        setHeadOrFooter(getAdapter());
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

    @Override
    public void scrollToPosition(int position) {
        if (mHeaderView != null) {
            position++;
        }
        super.scrollToPosition(position);
    }

    @Override
    public void smoothScrollToPosition(int position) {
        if (mHeaderView != null) {
            position++;
        }
        super.smoothScrollToPosition(position);
    }
}
