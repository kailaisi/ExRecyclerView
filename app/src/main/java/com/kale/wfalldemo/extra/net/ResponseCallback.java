package com.kale.wfalldemo.extra.net;

public interface ResponseCallback {

    void onSuccess(Object object);

    void onError(String msg);
}
