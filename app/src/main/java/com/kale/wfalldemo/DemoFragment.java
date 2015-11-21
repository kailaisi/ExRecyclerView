package com.kale.wfalldemo;

import com.kale.wfalldemo.mode.PhotoData;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import kale.ui.view.rcv.decoration.DividerGridItemDecoration;
import kale.ui.view.rcv.layoutmanager.ExStaggeredGridLayoutManager;
import kale.ui.view.rcv.ExGridSpanSizeLookup;
import kale.ui.view.rcv.ExRcvAdapterWrapper;
import kale.ui.view.rcv.ExRecyclerView;
import kale.ui.view.rcv.OnRcvScrollListener;

/**
 * @author Jack Tony
 * @date 2015/11/11
 */
public class DemoFragment extends Fragment {

    private static final String TAG = "DemoFragment";

    private ExRecyclerView mWaterFallRcv;

    private View headerView;

    private Button footerBtn;

    private List<PhotoData> mPhotoDataList;
    
    private ExRcvAdapterWrapper mAdapter;

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

        setRecyclerView();
        return rootView;
    }

    private void setRecyclerView() {
        mWaterFallRcv.setHeaderView(headerView);
        mWaterFallRcv.setFooterView(footerBtn);

        mPhotoDataList = new ArrayList<>();

        initAdapter();
        initLayoutManager();

        // 添加分割线
        mWaterFallRcv.addItemDecoration(new DividerGridItemDecoration(getContext()));
        //mWaterFallRcv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));//可替换

        // 不显示滚动到顶部/底部的阴影（减少绘制）
        mWaterFallRcv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        //mWaterFallRcv.setClipToPadding(true);
        
        mWaterFallRcv.setAdapter(mAdapter);
        
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
        
        // 模拟加载数据
        initData();
    }

    private void initLayoutManager() {
        ExStaggeredGridLayoutManager layoutManager = new ExStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setSpanSizeLookup(new ExGridSpanSizeLookup(mAdapter, layoutManager.getSpanCount()));
        mWaterFallRcv.setLayoutManager(layoutManager);
    }

    private void initAdapter() {
        RecyclerView.Adapter adapter;
        // 可以自由替换各种adapter的实现
        adapter = new MyAdapter01(mPhotoDataList, ((DemoActivity) getActivity()));
//        adapter = new MyAdapter02(mPhotoDataList, ((DemoActivity) getActivity()));

        mAdapter = new ExRcvAdapterWrapper<>(adapter);
    }

    
    //————————————————————————————————————————————————————————————————
    //                            Jack Tony
    //
    //                    callback start 2015/11/21  
    //                             
    //————————————————————————————————————————————————————————————————
    
    public void scrollToPos(int pos, boolean isSmooth) {
        if (isSmooth) {
            mWaterFallRcv.smoothScrollToPosition(pos);
        } else {
            mWaterFallRcv.scrollToPosition(pos);
        }
    }

    private void initData() {
        List<PhotoData> dataList = new ArrayList<>();
        String[] array = getResources().getStringArray(R.array.country_names);
        for (String arr : array) {
            dataList.add(new PhotoData(arr));
        }
        mPhotoDataList.addAll(0, dataList);
        mAdapter.renderDataInsert(0, dataList.size());
    }

    public void update(int pos, PhotoData data) {
        mPhotoDataList.set(pos, data);
        mAdapter.renderDataUpdate(pos, 1);
    }
    
    public void insert2Top(PhotoData data) {
        insert(0, data);
    }

    public void remove(int pos) {
        mPhotoDataList.remove(pos);
        mAdapter.renderDataRemove(pos);
    }

    public void insert(int pos, PhotoData data) {
        mPhotoDataList.add(pos, data);
        mAdapter.renderDataInsert(pos, 1);
    }

    public void add(PhotoData data) {
        mPhotoDataList.add(data);
        mAdapter.renderDataAdd(1);
    }

}
