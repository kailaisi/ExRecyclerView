package com.kale.wfalldemo;

import com.kale.wfalldemo.mode.PhotoData;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import kale.decoration.DividerGridItemDecoration;
import kale.recycler.ExRcvAdapterWrapper;
import kale.recycler.ExRecyclerView;
import kale.recycler.OnRcvScrollListener;

/**
 * @author Jack Tony
 * @date 2015/11/11
 * 
 */
public class DemoFragment extends Fragment{

    private static final String TAG = "DemoFragment";

    private ExRecyclerView mWaterFallRcv;

    private View headerView;

    private Button footerBtn;
    
    private List<PhotoData> mData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.demo_fragment, null);
        mWaterFallRcv = (ExRecyclerView) rootView.findViewById(R.id.waterFall_rcv);
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
        footerBtn.setText("底部");
        footerBtn.getBackground().setAlpha(80);
        
        setRecyclerView();
        return rootView;
    }
    
    private void setRecyclerView() {
        // 设置头部或底部的操作应该在setAdapter之前
        mWaterFallRcv.setHeaderView(headerView);
        mWaterFallRcv.setFooterView(footerBtn);

        //StaggeredGridLayoutManager layoutManager = new ExStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, true);// 可替换
        mWaterFallRcv.setLayoutManager(layoutManager);

        // 添加分割线
        mWaterFallRcv.addItemDecoration(new DividerGridItemDecoration(getContext()));
        //mWaterFallRcv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));//可替换

        // 不显示滚动到顶部/底部的阴影（减少绘制）
        mWaterFallRcv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        //mWaterFallRcv.setClipToPadding(true);
        /**
         * 监听滚动事件，因为这里我确定这个fragment是不会被复用的。所以传入了特定的activity，在实际项目中需要斟酌。推荐在复用的时候传入接口！
         */
        mWaterFallRcv.addOnScrollListener(new OnRcvScrollListener() {
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

        RecyclerView.Adapter adapter;

        mData = new ArrayList<>();
        adapter = new MyAdapter02(mData, ((DemoActivity) getActivity()));
        adapter = new MyAdapter01(mData);
        
        // 任意替换你的adapter
        mWaterFallRcv.setAdapter(new ExRcvAdapterWrapper(adapter));
    }

    public void scrollToPos(int pos, boolean isSmooth) {
        if (isSmooth) {
            mWaterFallRcv.smoothScrollToPosition(pos);
        } else {
            mWaterFallRcv.scrollToPosition(pos);
        }
    }
    
    private int size = 0;
    
    public void updateData(List<PhotoData> data) {
        mData = data;
        ExRcvAdapterWrapper wrapper = ((ExRcvAdapterWrapper) mWaterFallRcv.getAdapter());
        //((MyAdapter02) wrapper.getWrappedAdapter()).updateData(data);
        ((MyAdapter01) wrapper.getWrappedAdapter()).setData(data);
        //mWaterFallRcv.getAdapter().notifyDataSetChanged();
        mWaterFallRcv.getAdapter().notifyItemRangeChanged(size, data.size());
        size = data.size();
    }
    
}
