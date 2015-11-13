package com.kale.wfalldemo;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kale.wfalldemo.extra.view.DynamicHeightSimpleDraweeView;
import com.kale.wfalldemo.mode.PhotoData;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * @author Jack Tony
 * @date 2015/11/12
 */
public class MyAdapter01 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "MyAdapter";

    private List<PhotoData> mData;
    
    public MyAdapter01(List<PhotoData> data) {
        mData = data;
    }
    
    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 1 + (int) (Math.random() * 2);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.demo_white_item, null);
        return new WhiteVH(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof WhiteVH) {
            ((WhiteVH) viewHolder).onUpdateViews(mData.get(position), position);
        }
    }

    public void setData(List<PhotoData> data) {
        mData = data;
    }

    public static class WhiteVH extends RecyclerView.ViewHolder {

        private float mPicRatio;

        private DynamicHeightSimpleDraweeView contentSdv;

        private TextView descriptionTv;

        private SimpleDraweeView headPicSdv;

        private TextView positionTv;

        private View mRootView;

        private int mPosition;
        
        public WhiteVH(View itemView) {
            super(itemView);
            onBindViews(itemView);
            onSetViews();
        }

        public void onBindViews(View view) {
            mRootView = view;
            contentSdv = (DynamicHeightSimpleDraweeView) view.findViewById(R.id.aaa_wf_item_content_DraweeView);
            descriptionTv = (TextView) view.findViewById(R.id.aaa_wf_item_description_textView);
            headPicSdv = (SimpleDraweeView) view.findViewById(R.id.aaa_wf_item_user_head_draweeView);
            positionTv = (TextView) view.findViewById(R.id.aaa_wf_item_positon_textView);
        }

        public void onSetViews() {
            mRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //mActivity.onItemClick(mPosition);
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

        public void onUpdateViews(PhotoData data, int position) {
            mPosition = position;
            mPicRatio = (float) data.photo.height / data.photo.width;
            
            contentSdv.setImageURI(Uri.parse(data.photo.path));
            contentSdv.setHeightRatio(mPicRatio);
            
            descriptionTv.setText(data.msg);
            headPicSdv.setImageURI(Uri.parse("http://wenwen.soso.com/p/20100203/20100203005516-1158326774.jpg"));
            positionTv.setText("No." + position);
        }
    }
    
}
