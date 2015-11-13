package com.kale.wfalldemo;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kale.wfalldemo.extra.view.DynamicHeightSimpleDraweeView;
import com.kale.wfalldemo.mode.PhotoData;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
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

    private DemoActivity mActivity;
    
    public MyAdapter02(List<PhotoData> data, DemoActivity activity) {
        super(data);
        mActivity = activity;
    }

    @Override
    public Object getItemViewType(PhotoData data) {
        return 1;
    }

    @NonNull
    @Override
    public AdapterItem<PhotoData> getItemView(Object o) {
        Log.d("adapter 02", "getItemView: type = " + (int) o);
        int type = (int) o;
        return new WhiteItem(mActivity);
    }


    public static class WhiteItem implements AdapterItem<PhotoData> {

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

}
