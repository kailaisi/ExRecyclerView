package com.kale.wfalldemo;

import com.kale.wfalldemo.mode.PhotoData;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import kale.adapter.AdapterItem;
import kale.adapter.recycler.CommonRcvAdapter;

/**
 * @author Jack Tony
 * @date 2015/11/12
 */
public class MyAdapter02 extends CommonRcvAdapter<PhotoData>{

    private static DemoActivity mActivity;
    
    public MyAdapter02(List<PhotoData> data, DemoActivity activity) {
        super(data);
        mActivity = activity;
    }

    @NonNull
    @Override
    public AdapterItem<PhotoData> getItemView(Object o) {
        return new AdapterItem<PhotoData>() {
            
            private TextView descTv;

            private View mRootView;

            private int mPosition;

            @Override
            public int getLayoutResId() {
                return R.layout.demo_item;
            }

            @Override
            public void onBindViews(View view) {
                mRootView = view;
                descTv = (TextView) view.findViewById(R.id.desc_tv);
            }

            @Override
            public void onSetViews() {
                mRootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mActivity.onItemClick(mPosition);
                    }
                });
                mRootView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(mRootView.getContext(), "on long click", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
            }

            @Override
            public void onUpdateViews(PhotoData data, int position) {
                mPosition = position;
                descTv.setText("No." + position + " " + data.msg);
            }
        };
    }

}
