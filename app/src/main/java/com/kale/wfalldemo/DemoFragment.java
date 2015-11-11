package com.kale.wfalldemo;

import com.kale.wfalldemo.item.OrangeItem;
import com.kale.wfalldemo.item.WhiteItem;
import com.kale.wfalldemo.mode.PhotoData;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import kale.adapter.AdapterItem;
import kale.adapter.recycler.CommonRcvAdapter;
import kale.decoration.DividerGridItemDecoration;
import kale.recycler.ExRecyclerView;
import kale.recycler.ExRecyclerViewAdapter;
import kale.recycler.OnRecyclerViewScrollListener;

/**
 * @author Jack Tony
 * @date 2015/11/11
 * 
 */
public class DemoFragment extends Fragment{

    private static final String TAG = "DemoFragment";

    private ExRecyclerView waterFallRcv;

    private View headerView;

    private Button footerBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.demo_fragment, null);
        waterFallRcv = (ExRecyclerView) rootView.findViewById(R.id.waterFall_rcv);
        headerView = LayoutInflater.from(getContext()).inflate(R.layout.demo_header, null);
        getActivity().getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                ((DemoActivity) getActivity()).initHeight(headerView.getHeight());
            }
        });
        headerView.findViewById(R.id.header_imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转到某个位置
                scrollToPos(10, false);
            }
        });
        
        footerBtn = new Button(getContext());
        footerBtn.setText("正在加载...");
        footerBtn.getBackground().setAlpha(80);
        
        setRecyclerView();
        return rootView;
    }
    
    private void setRecyclerView() {
        // 设置头部或底部的操作应该在setAdapter之前
/*        waterFallRcv.setHeaderView(headerView);
        waterFallRcv.setFooterView(footerBtn);*/

        //StaggeredGridLayoutManager layoutManager = new ExStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, true);// 可替换
        waterFallRcv.setLayoutManager(layoutManager);

        // 添加分割线
        waterFallRcv.addItemDecoration(new DividerGridItemDecoration(getContext()));
        //waterFallRcv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));//可替换

        // 不显示滚动到顶部/底部的阴影（减少绘制）
        waterFallRcv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        //waterFallRcv.setClipToPadding(true);
        /**
         * 监听滚动事件
         */
        waterFallRcv.addOnScrollListener(new OnRecyclerViewScrollListener() {
            @Override
            public void onScrollUp() {
                ((DemoActivity) getActivity()).onScrollUp();
            }

            @Override
            public void onScrollDown() {
                ((DemoActivity) getActivity()).onScrollDown();
            }

            @Override
            public void onBottom() {
                ((DemoActivity) getActivity()).onBottom();
            }

            @Override
            public void onMoved(int distanceX, int distanceY) {
                ((DemoActivity) getActivity()).onMoved(distanceX, distanceY);
            }
        });

        // 先放一个空的list
        waterFallRcv.setAdapter(new ExRecyclerViewAdapter<PhotoData>(new ArrayList<PhotoData>()) {

            @Override
            public Object getItemViewType(PhotoData data) {
                return 1 + (int) (Math.random() * 2);
            }

            @NonNull
            @Override
            public AdapterItem<PhotoData> getItemView(Object o) {
                Log.d(TAG, "getItemView: gogogo");
                Log.d(TAG, "getItemView: type = " + (int)o);
                int type = (int) o;
                if (type == 1) {
                    return new OrangeItem(((DemoActivity) getActivity()));
                } else {
                    return new WhiteItem((DemoActivity) getActivity());
                }
            }
        });
    }

    public void scrollToPos(int pos, boolean isSmooth) {
        if (isSmooth) {
            waterFallRcv.smoothScrollToPosition(pos);
        } else {
            waterFallRcv.scrollToPosition(pos);
        }
    }

    public void updateData(List<PhotoData> data) {
        ((CommonRcvAdapter) waterFallRcv.getAdapter()).updateData(data);
    }
    
}
