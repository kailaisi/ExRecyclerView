package com.kale.wfalldemo;

import com.kale.wfalldemo.mode.PhotoData;

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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.demo_item, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof MyViewHolder) {
            ((MyViewHolder) viewHolder).onUpdateViews(mData.get(position), position);
        }
    }

    public void setData(List<PhotoData> data) {
        mData = data;
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView descTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            onBindViews(itemView);
            onSetViews();
        }

        public void onBindViews(View view) {
            descTv = (TextView) view.findViewById(R.id.desc_tv);
        }

        public void onSetViews() {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    mActivity.onItemClick(mPosition);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(itemView.getContext(), "on long click", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }

        public void onUpdateViews(PhotoData data, int position) {
            descTv.setText("No." + position + "     " + data.msg);
        }
    }
    
}
