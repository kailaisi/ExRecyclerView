package com.kale.wfalldemo.extra.net;

import com.android.volley.Response;
import com.kale.wfalldemo.extra.MyApplication;


/**
 * @author Jack Tony
 * @brief
 * @date 2015/4/5
 */
public class GsonDecode<T> {

    /**
     * 向服务器请求数据，进行回调
     */
    public void getGsonData(String url, Class<T> cls, Response.Listener<T> listener,
            Response.ErrorListener errorListener) {
        
        GsonRequest<T> gsonRequest = new GsonRequest<>(url, cls, listener, errorListener);
        MyApplication.requestQueue.add(gsonRequest);
    }
}
