package com.kale.wfalldemo.extra.net;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kale.wfalldemo.mode.Club;
import com.kale.wfalldemo.mode.PhotoData;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

/**
 * @author Jack Tony
 * @date 2015/5/17
 */
public class DataManager {

    private static final String URL
            = "http://www.duitang.com/napi/blog/list/by_club_id/?club_id=54aa79d9a3101a0f75731c62&limit=0&start=";

    private String TAG = getClass().getSimpleName();

    /** 加载最新数据的起点 */
    public final int LATEST_INDEX = 0;

    /** 下一页的起始数 */
    private int mNextStart;

    /** 数据的list */
    private List<PhotoData> mDataList;

    public void loadNewData(final ResponseCallback callback) {
        loadData(0, callback);
    }

    public void loadData(final int index, @NonNull final ResponseCallback callback) {
        new GsonDecode<Club>()
                .getGsonData(URL + index, Club.class, new Response.Listener<Club>() {
                    @Override
                    public void onResponse(Club club) {
                        mNextStart = club.data.next_start;
                        if (mDataList == null || index == LATEST_INDEX) {
                            mDataList = club.data.object_list;
                        } else {
                            mDataList.addAll(club.data.object_list);
                        }
                        Log.d(TAG, "获取到" + mDataList.size() + "条数据");
                        callback.onSuccess(mDataList);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        callback.onError("Time out");
                    }
                });
    }

    public List<PhotoData> getData() {
        return mDataList;
    }

    public void loadOldData(final ResponseCallback callback) {
        loadData(mNextStart, callback);
    }

}
