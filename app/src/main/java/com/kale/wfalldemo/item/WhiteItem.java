package com.kale.wfalldemo.item;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kale.wfalldemo.DemoActivity;
import com.kale.wfalldemo.R;
import com.kale.wfalldemo.mode.PhotoData;
import com.kale.wfalldemo.extra.view.DynamicHeightSimpleDraweeView;

import android.net.Uri;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import kale.adapter.AdapterItem;


public class WhiteItem implements AdapterItem<PhotoData> {

    private DemoActivity mActivity;
    
    private float mPicRatio;

    private DynamicHeightSimpleDraweeView contentSdv;

    private TextView descriptionTv;

    private SimpleDraweeView headPicSdv;

    private TextView positionTv;

    private View mRootView;

    private int mPosition;

    public WhiteItem(DemoActivity activity) {
        mActivity = activity;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.demo_white_item;
    }

    @Override
    public void onBindViews(View view) {
        mRootView = view;
        contentSdv = (DynamicHeightSimpleDraweeView) view.findViewById(R.id.aaa_wf_item_content_DraweeView);
        descriptionTv = (TextView) view.findViewById(R.id.aaa_wf_item_description_textView);
        headPicSdv = (SimpleDraweeView) view.findViewById(R.id.aaa_wf_item_user_head_draweeView);
        positionTv = (TextView) view.findViewById(R.id.aaa_wf_item_positon_textView);
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
                Toast.makeText(contentSdv.getContext(), "on long click", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public void onUpdateViews(PhotoData data, int position) {
        mPosition = position;
        contentSdv.setImageURI(Uri.parse(data.photo.path));
        mPicRatio = (float) data.photo.height / data.photo.width;
        contentSdv.setHeightRatio(mPicRatio);
        descriptionTv.setText(data.msg);
        headPicSdv.setImageURI(Uri.parse("http://wenwen.soso.com/p/20100203/20100203005516-1158326774.jpg"));
        positionTv.setText("No." + position);
    }

}

