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

/**
 * @author Jack Tony
 * @brief
 * @date 2015/4/10
 */
public class OrangeItem implements AdapterItem<PhotoData> {

    private DemoActivity mActivity;

    /** 内容主体的图片 */
    public DynamicHeightSimpleDraweeView contentSdv;

    /** 图片下方的描述文字 */
    public TextView descriptionTv;

    /** 用户的头像 */
    public SimpleDraweeView headPicSdv;
    
    private float mPicRatio;
    
    private View mRootView;
    
    private int mPosition;

    /**
     * 标识位置的textView
     */
    public TextView positionTv;
    
    public OrangeItem(DemoActivity activity) {
        mActivity = activity;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.demo_orange_item;
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
                Toast.makeText(mRootView.getContext(), "on long click", Toast.LENGTH_SHORT).show();
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
        // 必须设置加载的uri
        headPicSdv.setImageURI(Uri.parse("http://wenwen.soso.com/p/20100203/20100203005516-1158326774.jpg"));
        positionTv.setText("No." + position);
    }

}
