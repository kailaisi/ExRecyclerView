package kale.ui.view.rcv.layoutmanager;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * @author Jack Tony
 * @brief 不规则排列（类似于瀑布流）的布局管理器
 * @date 2015/4/6
 */
public class ExStaggeredGridLayoutManager extends StaggeredGridLayoutManager {

    private GridLayoutManager.SpanSizeLookup mSpanSizeLookup;

    public ExStaggeredGridLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }

    /**
     * 设置某个位置的item的跨列程度，这里和GridLayoutManager有点不一样，
     * 如果你设置某个位置的item的span>1了，那么这个item会占据所有列
     *
     * @param spanSizeLookup instance to be used to query number of spans
     *                       occupied by each item
     */
    public void setSpanSizeLookup(GridLayoutManager.SpanSizeLookup spanSizeLookup) {
        mSpanSizeLookup = spanSizeLookup;
    }

    /**
     * Returns the current used by the GridLayoutManager.
     *
     * @return The current used by the GridLayoutManager.
     */
    public GridLayoutManager.SpanSizeLookup getSpanSizeLookup() {
        return mSpanSizeLookup;
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        //Log.d(TAG, "item count = " + getItemCount());
        for (int i = 0; i < getItemCount(); i++) {

            if (mSpanSizeLookup.getSpanSize(i) > 1) {
                //Log.d(TAG, "lookup > 1 = " + i);
                try {
                    /** 
                     * fix 动态添加时报IndexOutOfBoundsException
                     * 如果你插入了一个数据，并且通知了适配器做插入的更新，比如：notifyItemRangeInserted
                     * 这时候数组的position还没变，就会出现此异常。你必须继续调用notifyItemRangeChanged才能真正更新下标
                     * 感觉这个是设计上的一大失误，通知了数据改变后竟然不能自己改变下标，不符合逻辑。
                     **/
                    View view = recycler.getViewForPosition(i);
                    if (view != null) {
                        /**
                         *占用所有的列。文档中没说明白。还是谷歌加上面有人指明了方向！
                         * @see https://plus.google.com/+EtienneLawlor/posts/c5T7fu9ujqi
                         */
                        StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
                        lp.setFullSpan(true);
                    }
                    // recycler.recycleView(view);
                } catch (IndexOutOfBoundsException e) {
                    System.err.println("请检查是否调用了notifyItemRangeInserted方法，该方法调用后必须调用notifyItemRangeChanged。" 
                            + "\n如果已经按此顺序调用，那么下列异常可忽略。");
                    e.printStackTrace();
                }
            }
        }
        super.onMeasure(recycler, state, widthSpec, heightSpec);
    }
}
